package ServiceEntities;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by maxedman on 2017-04-21.
 * Class representing a ServiceState, which is part of a PortCallMessage.
 */

public class ServiceState {
    private ServiceObject serviceObject;
    private ServiceTimeSequence timeSequence;
    private Location at;
    private Between between;
    private String performingActor;
    private String time;
    private TimeType timeType;

    /**
     * Creates a ServiceState out of a JSON-representation of the object.
     * @param servStateJsonObj
     */
    public ServiceState(JSONObject servStateJsonObj){
        if (servStateJsonObj != null) {
            try {

                this.serviceObject              =    ServiceObject.valueOf(servStateJsonObj.getString(Constants_jsonParsing.TAG_SERVICE_STATE_SERVICE_OBJECT));
                this.timeSequence               =    ServiceTimeSequence.valueOf(servStateJsonObj.getString(Constants_jsonParsing.TAG_SERVICE_STATE_TIME_SEQUENCE));
                this.performingActor            =    servStateJsonObj.getString(Constants_jsonParsing.TAG_SERVICE_STATE_PERFORMING_ACTOR);
                this.time                       =    servStateJsonObj.getString(Constants_jsonParsing.TAG_SERVICE_STATE_TIME);
                this.timeType                   =    TimeType.valueOf(servStateJsonObj.getString(Constants_jsonParsing.TAG_SERVICE_STATE_TIME_TYPE));

                Between between;
                try {
                    between = new Between(servStateJsonObj.getJSONObject(Constants_jsonParsing.TAG_SERVICE_STATE_BETWEEN_LOCATIONS));
                } catch (JSONException e){ between = null; }
                Location at;
                try {
                    at = new Location(servStateJsonObj.getJSONObject(Constants_jsonParsing.TAG_SERVICE_STATE_AT));
                } catch (JSONException e){ at = null; }
                
                this.at                 = at;
                this.between            = between;

            } catch (JSONException e) {
                Log.e("ServiceState Constr.", "Problem getting strings: " + e.toString());
            }
        } else {
            Log.e("ServiceState Constr.", "param servStateJsonObj is null");
        }
    }

    /**
     * Constructor for creating ServiceState by data.
     * @param serviceObject ServiceObject
     * @param timeSequence ServiceTimeSequence
     * @param at Location
     * @param performingActor String
     */

    public ServiceState(ServiceObject serviceObject, ServiceTimeSequence timeSequence, TimeType timeType,
                         String time, Location at, String performingActor) {
        this.serviceObject = serviceObject;
        this.timeSequence = timeSequence;
        this.timeType = timeType;
        this.time = time;
        this.at = at;
        this.performingActor = performingActor;
    }

    /**
     * Constructor for creating ServiceState by data.
     * @param serviceObject ServiceObject
     * @param timeSequence ServiceTimeSequence
     * @param between Between
     * @param performingActor String
     */

    public ServiceState(ServiceObject serviceObject, ServiceTimeSequence timeSequence, TimeType timeType,
                        String time, Between between, String performingActor) {
        this.serviceObject = serviceObject;
        this.timeSequence = timeSequence;
        this.timeType = timeType;
        this.time = time;
        this.between = between;
        this.performingActor = performingActor;
    }

    public ServiceObject getServiceObject() {
        return serviceObject;
    }

    public void setServiceObject(ServiceObject serviceObject) {
        this.serviceObject = serviceObject;
    }

    public String toString(){
        return "Service Object: " + serviceObject.getText() +  "\nSequence: " + timeSequence + "\nTimeType: " + timeType + "\nTime: " + time;
    }

    /**
     * @return a String with an XML-representation of the class
     */
    public String toXml() {
        String xmlStr = "";
        if(serviceObject != null)
            xmlStr += "<ns2:serviceObject>" + serviceObject + "</ns2:serviceObject>";
        if(performingActor != null)
            xmlStr += "<ns2:performingActor>" + performingActor + "</ns2:performingActor>";
        if(timeSequence != null)
            xmlStr += "<ns2:timeSequence>" + timeSequence + "</ns2:timeSequence>";
        if(time != null)
            xmlStr += "<ns2:time>" + time + "</ns2:time>";
        if(timeType != null)
            xmlStr += "<ns2:timeType>" + timeType + "</ns2:timeType>";
        if(at != null)
            xmlStr += "<ns2:at>" + at.toXml() + "</ns2:at>";
        if (between != null)
            xmlStr += "<ns2:between>" + between.toXml() + "</ns2:between>";
        return xmlStr;
    }

    /**
     * @return a String with the OperationType of the ServiceState
     */
    public String getOperationType(){
        return getServiceObject().getText();
    }

    /**
     * @return a String with the TimeSequence i.e. "Commenced", "Completed" etc.
     */
    public String getTimeSequence(){
        return timeSequence.getText();
    }

    /**
     * @return a String with the TimeType i.e. if the ServiceState is Actual, Estimated etc.
     */
    public String getTimeType(){ return timeType.toString();}

    /**
     * @return A string with the Actor who performs the ServiceState
     */
    public String getPerformingActor(){
        return performingActor;
    }

    /**
     * @return a String with timestamp from when ServiceState was created.
     */
    public String getTime(){
        return time;
    }


    /**
     * @return String with LocationMRN.
     */
    public String getLocationMRN(){
        if (at != null) {
            return at.getLocationMRN();
        } else if (between != null) {
            return between.getLocationMRN();
        }
        return null;
    }
}
