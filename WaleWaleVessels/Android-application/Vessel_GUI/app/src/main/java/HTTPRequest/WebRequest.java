package HTTPRequest;

/**
 * Created by maxedman on 2017-04-21.
 */


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import static HTTPRequest.webRequestConstants.TAG_PCM_ID;
import static HTTPRequest.webRequestConstants.TAG_PCM_VESSEL;

public class WebRequest {
    static String response = null;
    public final static int GETRequest = 1;
    public final static int POSTRequest = 2;

    //Constructor with no parameter
    public WebRequest() {
    }
    /**
     * Making web service call
     *
     * @url - url to make web request
     * @requestmethod - http request method
     */
    public String makeWebServiceCall(String url, int requestmethod) {
        return this.makeWebServiceCall(url, requestmethod, null, null);
    }

    /**
     * Making web service call
     *
     * @param urladdress- url to make web request
     * @param requestmethod - http request method
     *                  1 = GET
     *                  2 = POST
     * @param headers - http request headers. e.g login
     * @param params - http request params, depending on the service. Not working atm. Send null
     */
    public String makeWebServiceCall(String urladdress, int requestmethod, HashMap<String, String> headers, HashMap<String, String> params) {
        URL url;
        String response = "";
        try {
            url = new URL(urladdress);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15001);
            conn.setConnectTimeout(15001);
            conn.setDoInput(true);
            conn.setDoOutput(true);
            if (requestmethod == POSTRequest) {
                conn.setRequestMethod("POST");
            } else if (requestmethod == GETRequest) {
                conn.setRequestMethod("GET");
            }

            if (headers != null) {
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    conn.setRequestProperty(entry.getKey(), entry.getValue());
//                    System.out.println(entry.getKey() + " - " + entry.getValue());
                }
            }
//TODO Implement the correct way of adding params. For now it has to be included in the URL-adress string.
            /*if (params != null) {
                OutputStream ostream = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(ostream, "UTF-8"));
                StringBuilder requestresult = new StringBuilder();
                boolean first = true;
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    if (first) {
                        first = false;
                        requestresult.append("?");
                    }
                    else {
                        requestresult.append("&?");
                    }
                    requestresult.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
                    requestresult.append("=");
                    requestresult.append(URLEncoder.encode(entry.getValue(), "UTF-8"));

                }
                writer.write(requestresult.toString());

                writer.flush();
                writer.close();
                ostream.close();
            }*/
            int reqresponseCode = conn.getResponseCode();

            if (reqresponseCode == HttpsURLConnection.HTTP_OK) {
                String line;
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line = br.readLine()) != null) {
                    response += line;
                }
            } else {
                System.out.println("WebRequestHTTPConnection is not OK!");
                System.out.println("ResponseCode: " + reqresponseCode);
                System.out.println("URL: " + urladdress);
                response = "";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }





    /**
     * For parsing the returned JSON string from PortCDM
     *
     * @param json  - JSon-string to parse
     * @return Arraylist of HashMaps that contains the data plus object of a Vessel.
     */
    //TODO Change back to private access. Only for use inside the class. Directly after calling makeWebServiceCall.
    public ArrayList<HashMap<String, Object>> parseJson(String json) {

        if (json != null) {
            try {
// Hashmap for presenting as ListView
                ArrayList<HashMap<String, Object>> pcmList = new ArrayList<HashMap<String, Object>>();
                //System.out.println(json);
                //JSONArray jsonObject = new JSONArray(json);
// Getting JSON Array that contains all the data.
                JSONArray portCallMessages = new JSONArray(json);

// looping through all PCM:s
                for (int i = 0; i < portCallMessages.length(); i++) {
                    JSONObject c = portCallMessages.getJSONObject(i);

                    String id = c.getString(TAG_PCM_ID);

// Vessel node is JSON Object
                    JSONObject vessel = c.getJSONObject(TAG_PCM_VESSEL);

// Temporary hashmap for single PCM
                    HashMap<String, Object> singlePcm = new HashMap<String, Object>();

// adding every child node to HashMap key => value
                    singlePcm.put(TAG_PCM_ID, id);
                    singlePcm.put(TAG_PCM_VESSEL, vessel);

// adding singe PCM to total list
                    pcmList.add(singlePcm);
                }

// returning the complete pcmList
                return pcmList;
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
        } else {
//            Log.e("ServiceHandler", "No data received from HTTP request");
            return null;
        }
    }
}

