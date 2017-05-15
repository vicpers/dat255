package RESTServices;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import HTTPRequest.WebRequest;
import ServiceEntities.*;

import static RESTServices.Constants_API.API_DEV_BASE_URL;
import static RESTServices.Constants_API.API_DEV_KEY1;
import static RESTServices.Constants_API.API_DEV_PASSWORD;
import static RESTServices.Constants_API.API_DEV_PORT1;
import static RESTServices.Constants_API.API_DEV_USERNAME;
import static RESTServices.Constants_API.API_HEADER_ACCEPT;
import static RESTServices.Constants_API.API_HEADER_ACCEPT_JSON;
import static RESTServices.Constants_API.API_HEADER_ACCEPT_XML;
import static RESTServices.Constants_API.API_HEADER_API_KEY;
import static RESTServices.Constants_API.API_HEADER_CONTENT_TYPE;
import static RESTServices.Constants_API.API_HEADER_PASSWORD;
import static RESTServices.Constants_API.API_HEADER_USER_ID;
import static RESTServices.Constants_API.API_SERVICE_CREATE_QUEUE;
import static RESTServices.Constants_API.API_SERVICE_POLL_QUEUE;

/**
 * Created by maxedman on 2017-04-21.
 */

public class MessageBrokerQueue {

    private String queueId;
    private ArrayList<PortCallMessage> queue = new ArrayList<PortCallMessage>();
    private ArrayList<PortCallMessage> tempQueue = new ArrayList<PortCallMessage>();

    // If the instance is a queue with ServiceObject then the sortQueueResponse funktion is going to be
    // handled in a different way with respect to the serviceObject. NULL by default.
    private ServiceObject serviceObject = null;

    /**
     *  Empty constructor
     */
    public MessageBrokerQueue(){
    }

    public MessageBrokerQueue(String queueId){
        setQueueId(queueId);
    }

    private void setQueueId(String queueId){
        this.queueId = queueId;
    }

    public String getQueueId(){
        return this.queueId;
    }

    public ArrayList<PortCallMessage> getQueue(){
        return this.queue;
    }

    /**
     *
     */
    public void createUnfilteredQueue(){
        String url = API_DEV_BASE_URL + ":" + API_DEV_PORT1 + API_SERVICE_CREATE_QUEUE;

        HashMap<String, String> headers = new HashMap<String, String>();

        headers.put(API_HEADER_CONTENT_TYPE, API_HEADER_ACCEPT_XML);
        headers.put(API_HEADER_USER_ID, API_DEV_USERNAME);
        headers.put(API_HEADER_PASSWORD, API_DEV_PASSWORD);
        headers.put(API_HEADER_API_KEY, API_DEV_KEY1);

        this.queueId = WebRequest.makeWebServicePost(url, headers, null, "");
        System.out.println(queueId);
    }

    /**
     * @param vessel
     */
    public void createUnfilteredQueue(Vessel vessel){
        String url = API_DEV_BASE_URL + ":" + API_DEV_PORT1 + API_SERVICE_CREATE_QUEUE;

        HashMap<String, String> headers = new HashMap<String, String>();

        headers.put(API_HEADER_CONTENT_TYPE, API_HEADER_ACCEPT_XML);
        headers.put(API_HEADER_USER_ID, API_DEV_USERNAME);
        headers.put(API_HEADER_PASSWORD, API_DEV_PASSWORD);
        headers.put(API_HEADER_API_KEY, API_DEV_KEY1);

        String body = "[" +
                      "  {" +
                      "    \"type\": \"VESSEL\"," +
                      "    \"element\": \"" + vessel.getId() + "\"" +
                      "  }" +
                      "]";

        this.queueId = WebRequest.makeWebServicePost(url, headers, null, body);
        Log.e("queueID", queueId);
    }

    /**
     * @param vessel
     * @param locationType
     */
    public void createUnfilteredQueue(Vessel vessel, LocationType locationType){
        String url = API_DEV_BASE_URL + ":" + API_DEV_PORT1 + API_SERVICE_CREATE_QUEUE;

        HashMap<String, String> headers = new HashMap<String, String>();

        headers.put(API_HEADER_CONTENT_TYPE, API_HEADER_ACCEPT_XML);
        headers.put(API_HEADER_USER_ID, API_DEV_USERNAME);
        headers.put(API_HEADER_PASSWORD, API_DEV_PASSWORD);
        headers.put(API_HEADER_API_KEY, API_DEV_KEY1);

        String body = "[" +
                "  {" +
                "    \"type\": \"VESSEL\"," +
                "    \"element\": \"" + vessel.getId() + "\"" +
                "  }," +
                "  {" +
                "    \"type\": \"LOCATION_TYPE\"," +
                "    \"element\": \"" + locationType + "\"" +
                "  }" +
                "]";

        this.queueId = WebRequest.makeWebServicePost(url, headers, null, body);
        Log.e("queueID", queueId);
    }

    /**
     * @param vessel
     * @param serviceObject
     */
    public void createUnfilteredQueue(Vessel vessel, ServiceObject serviceObject){
        this.serviceObject = serviceObject;

        String url = API_DEV_BASE_URL + ":" + API_DEV_PORT1 + API_SERVICE_CREATE_QUEUE;

        HashMap<String, String> headers = new HashMap<String, String>();

        headers.put(API_HEADER_CONTENT_TYPE, API_HEADER_ACCEPT_XML);
        headers.put(API_HEADER_USER_ID, API_DEV_USERNAME);
        headers.put(API_HEADER_PASSWORD, API_DEV_PASSWORD);
        headers.put(API_HEADER_API_KEY, API_DEV_KEY1);

        String body = "[" +
                "  {" +
                "    \"type\": \"VESSEL\"," +
                "    \"element\": \"" + vessel.getId() + "\"" +
                "  }," +
                "]";

        this.queueId = WebRequest.makeWebServicePost(url, headers, null, body);
        Log.e("queueID", queueId);
    }

    public ArrayList<PortCallMessage> pollQueue(){
        tempQueue = new ArrayList<>(); // Sets the temporary queue to an empty one.

        String url = API_DEV_BASE_URL + ":" + API_DEV_PORT1 + API_SERVICE_POLL_QUEUE + this.queueId;

        HashMap<String, String> headers = new HashMap<String, String>();

        headers.put(API_HEADER_ACCEPT, API_HEADER_ACCEPT_JSON);
        headers.put(API_HEADER_USER_ID, API_DEV_USERNAME);
        headers.put(API_HEADER_PASSWORD, API_DEV_PASSWORD);
        headers.put(API_HEADER_API_KEY, API_DEV_KEY1);

        String wrResponse = WebRequest.makeWebServiceCall(url, 1, headers, null);

        sortQueueResponse(wrResponse);
        return tempQueue;
    }

    private void sortQueueResponse(String jsonStr){
        // For adding to the queue.
        PortCallMessage tempPcm;
        try {
            JSONArray jsonArr = new JSONArray(jsonStr);
            for (int i = 0; i < jsonArr.length(); i++) {
                try {
                    JSONObject jsonObj = jsonArr.getJSONObject(i);
                    tempPcm = new PortCallMessage(jsonObj);
                    if(serviceObject != null){
                        if(isCorrectServiceObject(tempPcm))
                            this.queue.add(tempPcm);
                            this.tempQueue.add(tempPcm);
                    } else {
                        this.queue.add(tempPcm);
                        this.tempQueue.add(tempPcm);
                    }


                } catch (JSONException e1) {
                    Log.e("sortQueueResponse_inner", e1.toString());
                }
            }
        } catch (JSONException e2) {
            Log.e("sortQueueResponse", e2.toString());
        }
    }

    private boolean isCorrectServiceObject(PortCallMessage pcm){
        ServiceState serviceState = pcm.getServiceState();
        if(serviceState != null){
            if(serviceState.getServiceObject() == this.serviceObject)
                return true;
        }
        return false;
    }

    public String toString(){
        String returnStr = "";
        for (PortCallMessage pcmObj : this.queue) {
            returnStr += pcmObj.toString() + "\n\n";
            System.out.println(pcmObj.toXml());
        }
        return returnStr;
    }

    public String testToJson(){
        return this.queue.get(0).toXml();
    }
}
