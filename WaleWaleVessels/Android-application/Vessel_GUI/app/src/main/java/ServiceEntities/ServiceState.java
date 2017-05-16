package ServiceEntities;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by maxedman on 2017-04-21.
 */

public class ServiceState {
    private ServiceObject serviceObject;
    private ServiceTimeSequence timeSequence;
    private Location at;
    private Between between;
    private String performingActor;
    private String time;
    private TimeType timeType;

    public ServiceState(JSONObject servStateJsonObj){
        if (servStateJsonObj != null) {
            try {

                ServiceObject serviceObject              =    ServiceObject.valueOf(servStateJsonObj.getString(Constants_jsonParsing.TAG_SERVICE_STATE_SERVICE_OBJECT));
                ServiceTimeSequence serviceTimeSequence  =    ServiceTimeSequence.valueOf(servStateJsonObj.getString(Constants_jsonParsing.TAG_SERVICE_STATE_TIME_SEQUENCE));
                String performingActor                   =    servStateJsonObj.getString(Constants_jsonParsing.TAG_SERVICE_STATE_PERFORMING_ACTOR);
                String time                              =    servStateJsonObj.getString(Constants_jsonParsing.TAG_SERVICE_STATE_TIME);
                TimeType timeType                        =    TimeType.valueOf(servStateJsonObj.getString(Constants_jsonParsing.TAG_SERVICE_STATE_TIME_TYPE));

                Between between;
                try {
                    between = new Between(servStateJsonObj.getJSONObject(Constants_jsonParsing.TAG_SERVICE_STATE_BETWEEN_LOCATIONS));
                } catch (JSONException e){ between = null; }
                Location at;
                try {
                    at = new Location(servStateJsonObj.getJSONObject(Constants_jsonParsing.TAG_SERVICE_STATE_AT));
                } catch (JSONException e){ at = null; }

                this.serviceObject      = serviceObject;
                this.timeSequence       = serviceTimeSequence;
                this.at                 = at;
                this.between            = between;
                this.performingActor    = performingActor;

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
        return "Sequence: " + timeSequence + " - TimeType: " + timeType + " - Time: " + time;
    }

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
}
