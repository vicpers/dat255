package HTTPRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import RESTServices.jsonJavaConverter;
import ServiceEntities.*;


public class HttpUrlConnectionPortCDM {

    private static String testQueueJson = "[{\"portCallId\":\"urn:mrn:stm:portcdm:port_call:SEGOT:a07913a3-04a1-43db-9ca1-4c12bb86646a\",\"localPortCallId\":null,\"localJobId\":null,\"vesselId\":\"urn:mrn:stm:vessel:IMO:9259501\",\"messageId\":\"urn:mrn:stm:portcdm:message:936740d3-13c1-4250-8c7c-144ca1eff7d0\",\"groupWith\":null,\"reportedAt\":\"2017-04-21T19:44:26Z\",\"reportedBy\":\"urn:mrn:legacy:user:SSPA\",\"comment\":null,\"messageOperation\":null,\"locationState\":{\"referenceObject\":\"VESSEL\",\"time\":\"2017-04-21T19:44:10Z\",\"timeType\":\"ACTUAL\",\"arrivalLocation\":null,\"departureLocation\":{\"from\":{\"locationType\":\"TRAFFIC_AREA\",\"position\":{\"latitude\":0.0,\"longitude\":0.0},\"name\":\"Port of Gothenburg's traffic area\"},\"to\":null}},\"serviceState\":null},{\"portCallId\":\"urn:mrn:stm:portcdm:port_call:SEGOT:9319431a-c87b-41df-9392-07c381dd80ee\",\"localPortCallId\":null,\"localJobId\":null,\"vesselId\":\"urn:mrn:stm:vessel:IMO:9262089\",\"messageId\":\"urn:mrn:stm:portcdm:message:4bf0e334-ea5c-4e19-89e6-616b99f2879a\",\"groupWith\":null,\"reportedAt\":\"2017-04-21T19:55:06Z\",\"reportedBy\":\"urn:mrn:legacy:user:SSPA\",\"comment\":null,\"messageOperation\":null,\"locationState\":{\"referenceObject\":\"VESSEL\",\"time\":\"2017-04-21T19:51:50Z\",\"timeType\":\"ACTUAL\",\"arrivalLocation\":{\"from\":null,\"to\":{\"locationType\":\"BERTH\",\"position\":{\"latitude\":0.0,\"longitude\":0.0},\"name\":\"Älvsborg Harbour 712\"}},\"departureLocation\":null},\"serviceState\":null},{\"portCallId\":\"urn:mrn:stm:portcdm:port_call:SEGOT:5a378dfc-1d82-4d1d-a8ec-2f6db9656eb1\",\"localPortCallId\":null,\"localJobId\":null,\"vesselId\":\"urn:mrn:stm:vessel:IMO:9125944\",\"messageId\":\"urn:mrn:stm:portcdm:message:5a465dff-4211-434c-b435-23d183a6c479\",\"groupWith\":null,\"reportedAt\":\"2017-04-21T20:14:35Z\",\"reportedBy\":\"urn:mrn:legacy:user:SSPA\",\"comment\":null,\"messageOperation\":null,\"locationState\":{\"referenceObject\":\"VESSEL\",\"time\":\"2017-04-21T20:14:18Z\",\"timeType\":\"ACTUAL\",\"arrivalLocation\":{\"from\":null,\"to\":{\"locationType\":\"TRAFFIC_AREA\",\"position\":{\"latitude\":0.0,\"longitude\":0.0},\"name\":\"Port of Gothenburg's traffic area\"}},\"departureLocation\":null},\"serviceState\":null},{\"portCallId\":\"urn:mrn:stm:portcdm:port_call:SEGOT:9319431a-c87b-41df-9392-07c381dd80ee\",\"localPortCallId\":null,\"localJobId\":null,\"vesselId\":\"urn:mrn:stm:vessel:IMO:9262089\",\"messageId\":\"urn:mrn:stm:portcdm:message:d639b0cc-02ce-4846-b4cc-aedff0b982c2\",\"groupWith\":null,\"reportedAt\":\"2017-04-21T20:21:46Z\",\"reportedBy\":\"urn:mrn:legacy:user:SSPA\",\"comment\":null,\"messageOperation\":null,\"locationState\":{\"referenceObject\":\"TUG\",\"time\":\"2017-04-21T20:21:29Z\",\"timeType\":\"ACTUAL\",\"arrivalLocation\":null,\"departureLocation\":{\"from\":{\"locationType\":\"VESSEL\",\"position\":{\"latitude\":0.0,\"longitude\":0.0},\"name\":\"VESSEL\"},\"to\":null}},\"serviceState\":null}]";

    public static void main(String[] args){



        HttpUrlConnectionPortCDM portCdmCon = new HttpUrlConnectionPortCDM();


//        String wrTest = portCdmCon.getLatestPortCalls(1);
        String wrTest = portCdmCon.pollQueueTest();

        System.out.println(wrTest);
    }

    public static String getLatestPortCalls(int count){


        String ipAdress = "http://dev.portcdm.eu";
        String port = "8080";
        String service = "/dmp/port_calls";
        String url = ipAdress + ":" + port + service;
//        url += "?count=1";

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("count", "" + count);

        HashMap<String, String> headers = new HashMap<String, String>();
        String username = "viktoria";
        String password = "vik123";
        String apiKey = "eeee";

        headers.put("Accept", "application/json");
        headers.put("X-PortCDM-UserId", username);
        headers.put("X-PortCDM-Password", password);
        headers.put("X-PortCDM-APIKey", apiKey);

        String wrResponse = WebRequest.makeWebServiceCall(url, 1, headers, params);


        String jsonString = "";
        PCM tempPcm;
        try {
            JSONArray jsonArr = new JSONArray(wrResponse);
            for (int i = 0 ; i < jsonArr.length(); i++) {
                try {
                    JSONObject jsonObj = jsonArr.getJSONObject(i);
                    tempPcm = jsonJavaConverter.jsonToPcm(jsonObj);
                    jsonString += tempPcm.toString() + "\n" + tempPcm.getVessel().toString();
                } catch (JSONException e){
                    jsonString = e.toString();
                }
                jsonString += "\n\n";
            }

        } catch (JSONException e1){
            jsonString = e1.toString();
        }

        // Calling method for parsing JSon-string
/*
        PCM testPcm = jsonJavaConverter.jsonToPcm(wrResponse);

        return testPcm.toString() + "\n" + testPcm.getVessel().toString();*/
        return jsonString;
    }

    public static String pollQueueTest(){

        String jsonString = "";
        PortCallMessage tempPcm;
        try {
            JSONArray jsonArr = new JSONArray(testQueueJson);
            for (int i = 0 ; i < jsonArr.length(); i++) {
                try {
                    JSONObject jsonObj = jsonArr.getJSONObject(i);
                    tempPcm = jsonJavaConverter.jsonToPortCallMessage(jsonObj);
                    jsonString += tempPcm.toString();

                } catch (JSONException e){
                    jsonString = e.toString();
                }
                jsonString += "\n\n";
            }

            /*JSONObject jsonObj = jsonArr.getJSONObject(0);
            jsonString = jsonJavaConverter.jsonToPortCallMessage(jsonObj);
*/
        } catch (JSONException e1){
            jsonString = e1.toString();
        }
        return jsonString;
    }
}
