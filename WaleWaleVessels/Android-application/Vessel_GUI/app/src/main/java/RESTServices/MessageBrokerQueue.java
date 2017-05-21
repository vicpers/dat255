package RESTServices;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import HTTPRequest.WebRequest;
import ServiceEntities.LocationType;
import ServiceEntities.PortCallMessage;
import ServiceEntities.ServiceObject;
import ServiceEntities.ServiceState;
import ServiceEntities.TimeType;
import ServiceEntities.Vessel;

import static RESTServices.Constants_API.API_DEV_BASE_URL;
import static RESTServices.Constants_API.API_DEV_KEY1;
import static RESTServices.Constants_API.API_DEV_PASSWORD;
import static RESTServices.Constants_API.API_DEV_PORT1;
import static RESTServices.Constants_API.API_DEV_USERNAME;
import static RESTServices.Constants_API.API_HEADER_ACCEPT;
import static RESTServices.Constants_API.API_HEADER_ACCEPT_JSON;
import static RESTServices.Constants_API.API_HEADER_API_KEY;
import static RESTServices.Constants_API.API_HEADER_CONTENT_TYPE;
import static RESTServices.Constants_API.API_HEADER_PASSWORD;
import static RESTServices.Constants_API.API_HEADER_USER_ID;
import static RESTServices.Constants_API.API_SERVICE_CREATE_QUEUE;
import static RESTServices.Constants_API.API_SERVICE_POLL_QUEUE;

/**
 * Class for handling instances of MessageBrokerQueues as defined by the PortCDM services.
 */

public class MessageBrokerQueue{
    // Date strings for retrieving either old messages or only new.
    private static final String date = "";// "?fromTime=2017-05-16T14:20:21Z";
    private static final String portCallIDdate = "?fromTime=2017-05-16T14:20:21Z";

    // queueId is the id returned from the server when the queue is created.
    private String queueId;
    // queue contains all PortCallMessages for the specified filter.
    private ArrayList<PortCallMessage> queue = new ArrayList<PortCallMessage>();
    // tempQueue contains all new PortCallMessages for the specified filter.
    private ArrayList<PortCallMessage> tempQueue = new ArrayList<PortCallMessage>();

    // If the instance is a queue with ServiceObject then the sortQueueResponse funktion is going to be
    // handled in a different way with respect to the serviceObject. NULL by default.
    private ServiceObject serviceObject = null;

    /**
     *  Empty constructor
     */
    public MessageBrokerQueue(){
    }

    /** Fetches the queue of PortCallMessages.
     * @return an ArrayList of PortCallMessages.
     */
    public ArrayList<PortCallMessage> getQueue(){
        return this.queue;
    }

    /** Creates a queue based on the Vessel
     * @param vessel Vessel to be included in the filter, by vesselID.
     */
    public void createUnfilteredQueue(Vessel vessel){
        String url = API_DEV_BASE_URL + ":" + API_DEV_PORT1 + API_SERVICE_CREATE_QUEUE;
        url += date;

        HashMap<String, String> headers = new HashMap<String, String>();

        headers.put(API_HEADER_CONTENT_TYPE, API_HEADER_ACCEPT_JSON);
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

    /** Creates a queue based on the portCallID
     * @param portCallId PortCallID to be included in the filter
     */
    public void createUnfilteredQueue(String portCallId){
        String url = API_DEV_BASE_URL + ":" + API_DEV_PORT1 + API_SERVICE_CREATE_QUEUE;
        url += portCallIDdate;

        HashMap<String, String> headers = new HashMap<String, String>();

        headers.put(API_HEADER_CONTENT_TYPE, API_HEADER_ACCEPT_JSON);
        headers.put(API_HEADER_USER_ID, API_DEV_USERNAME);
        headers.put(API_HEADER_PASSWORD, API_DEV_PASSWORD);
        headers.put(API_HEADER_API_KEY, API_DEV_KEY1);

        String body = "[" +
                "  {" +
                "    \"type\": \"PORT_CALL\"," +
                "    \"element\": \"" + portCallId + "\"" +
                "  }" +
                "]";

        this.queueId = WebRequest.makeWebServicePost(url, headers, null, body);
        Log.e("queueID", queueId);
    }

    /** Creates a queue based on the portCallID and the LocationType
     * @param portCallID PortCallID to be included in the filter
     * @param locationType LocationType enum to be included in the filter
     */
    public void createUnfilteredQueue(String portCallID, LocationType locationType){
        String url = API_DEV_BASE_URL + ":" + API_DEV_PORT1 + API_SERVICE_CREATE_QUEUE;
        url += portCallIDdate;

        HashMap<String, String> headers = new HashMap<String, String>();

        headers.put(API_HEADER_CONTENT_TYPE, API_HEADER_ACCEPT_JSON);
        headers.put(API_HEADER_USER_ID, API_DEV_USERNAME);
        headers.put(API_HEADER_PASSWORD, API_DEV_PASSWORD);
        headers.put(API_HEADER_API_KEY, API_DEV_KEY1);

        String body = "[" +
                "  {" +
                "    \"type\": \"PORT_CALL\"," +
                "    \"element\": \"" + portCallID + "\"" +
                "  }," +
                "  {" +
                "    \"type\": \"LOCATION_TYPE\"," +
                "    \"element\": \"" + locationType + "\"" +
                "  }" +
                "]";

        this.queueId = WebRequest.makeWebServicePost(url, headers, null, body);
        Log.e("queueID", queueId);
    }

    /** Creates a queue based on the portCallID and the TimeType
     * @param portCallID PortCallID to be included in the filter
     * @param timeType TimeType enum to be included in the filter
     */
    public void createUnfilteredQueue(String portCallID, TimeType timeType){
        String url = API_DEV_BASE_URL + ":" + API_DEV_PORT1 + API_SERVICE_CREATE_QUEUE;
        url += portCallIDdate;

        HashMap<String, String> headers = new HashMap<String, String>();

        headers.put(API_HEADER_CONTENT_TYPE, API_HEADER_ACCEPT_JSON);
        headers.put(API_HEADER_USER_ID, API_DEV_USERNAME);
        headers.put(API_HEADER_PASSWORD, API_DEV_PASSWORD);
        headers.put(API_HEADER_API_KEY, API_DEV_KEY1);

        String body = "[" +
                "  {" +
                "    \"type\": \"PORT_CALL\"," +
                "    \"element\": \"" + portCallID + "\"" +
                "  }," +
                "  {" +
                "    \"type\": \"TIME_TYPE\"," +
                "    \"element\": \"" + timeType + "\"" +
                "  }" +
                "]";

        this.queueId = WebRequest.makeWebServicePost(url, headers, null, body);
        Log.e("queueID", queueId);
    }

    /** Creates a queue based on the portCallID and the serviceObject
     * @param portCallID PortCallID to be included in the filter
     * @param serviceObject ServiceObject enum to be included in the filter, handled on client-side.
     */
    public void createUnfilteredQueue(String portCallID, ServiceObject serviceObject){
        this.serviceObject = serviceObject;

        String url = API_DEV_BASE_URL + ":" + API_DEV_PORT1 + API_SERVICE_CREATE_QUEUE;
        url += portCallIDdate;

        HashMap<String, String> headers = new HashMap<String, String>();

        headers.put(API_HEADER_CONTENT_TYPE, API_HEADER_ACCEPT_JSON);
        headers.put(API_HEADER_USER_ID, API_DEV_USERNAME);
        headers.put(API_HEADER_PASSWORD, API_DEV_PASSWORD);
        headers.put(API_HEADER_API_KEY, API_DEV_KEY1);

        String body = "[" +
                "  {" +
                "    \"type\": \"PORT_CALL\"," +
                "    \"element\": \"" + portCallID + "\"" +
                "  }" +
                "]";

        this.queueId = WebRequest.makeWebServicePost(url, headers, null, body);
        Log.e("queueID", queueId);
    }

    /** Fetches all new messages from the queue
     * @return ArrayList of all new PortCallMessages.
     */
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

    /** Stores all PortCallMessages from jsonStr in the queue
     * @param jsonStr Json-String that includes PortCallMessages.
     */
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
        //Log.e("Queue", queue.toString());
    }

    /** Internal method for filtering PortCallMessages based on the ServiceObject.
     * @param pcm PortCallMessage to be filtered.
     * @return True if the PortCallMessage ServiceObject equals the one specified in the filter.
     */
    private boolean isCorrectServiceObject(PortCallMessage pcm){
        ServiceState serviceState = pcm.getServiceState();
        if(serviceState != null){
            if(serviceState.getServiceObject() == this.serviceObject)
                return true;
        }
        return false;
    }

    /** Represent the MessageBrokerQueue as a string.
     * @return The id of the queue
     */
    @Override
    public String toString(){
        return queue.toString();
    }

}
