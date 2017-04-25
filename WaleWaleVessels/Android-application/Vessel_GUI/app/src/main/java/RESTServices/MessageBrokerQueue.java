package RESTServices;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import HTTPRequest.WebRequest;
import ServiceEntities.PortCallMessage;

import static RESTServices.Constants_API.*;

/**
 * Created by maxedman on 2017-04-21.
 */

public class MessageBrokerQueue {

    private String queueId;
    private ArrayList<PortCallMessage> queue = new ArrayList<PortCallMessage>();

//    Empty constructor
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

    //TODO implement functionality for polling MessageBrokerQueue
    public void pollQueue(){

        String url = API_DEV_BASE_URL + ":" + API_DEV_PORT1 + API_SERVICE_POLL_QUEUE + this.queueId;

        HashMap<String, String> headers = new HashMap<String, String>();

        headers.put(API_HEADER_ACCEPT, API_HEADER_ACCEPT_JSON);
        headers.put(API_HEADER_USER_ID, API_DEV_USERNAME);
        headers.put(API_HEADER_PASSWORD, API_DEV_PASSWORD);
        headers.put(API_HEADER_API_KEY, API_DEV_KEY1);

        String wrResponse = WebRequest.makeWebServiceCall(url, 1, headers, null);

        sortQueueResponse(wrResponse);
    }

    private void sortQueueResponse(String jsonStr){

        // For adding to the queue.
        PortCallMessage tempPcm;
        try {
            JSONArray jsonArr = new JSONArray(jsonStr);
            for (int i = 0 ; i < jsonArr.length(); i++) {
                try {
                    JSONObject jsonObj = jsonArr.getJSONObject(i);
                    this.queue.add(new PortCallMessage(jsonObj));

                } catch (JSONException e1){
                    Log.e("sortQueueResponse_inner", e1.toString());
                }
            }
        } catch (JSONException e2){
            Log.e("sortQueueResponse", e2.toString());
        }
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
