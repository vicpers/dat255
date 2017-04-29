package ServiceEntities;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by maxedman on 2017-04-21.
 */

public class ServiceState {
    private String serviceObject;
    private String timeSequence;
    private String at; //TODO När ServiceState dyker upp i något PCM så se om at är en Location
    private Between betweenLocations;
    private String performingActor;
    private String time;
    private String timeType;

    public ServiceState(JSONObject servStateJsonObj){
        if (servStateJsonObj != null) {
            try {

                String serviceObject        =    servStateJsonObj.getString(Constants_jsonParsing.TAG_SERVICE_STATE_SERVICE_OBJECT);
                String serviceTimeSequence  =    servStateJsonObj.getString(Constants_jsonParsing.TAG_SERVICE_STATE_TIME_SEQUENCE);
                String at                   =    servStateJsonObj.getString(Constants_jsonParsing.TAG_SERVICE_STATE_AT);
                String performingActor      =    servStateJsonObj.getString(Constants_jsonParsing.TAG_SERVICE_STATE_PERFORMING_ACTOR);
                String time                 =    servStateJsonObj.getString(Constants_jsonParsing.TAG_SERVICE_STATE_TIME);
                String timeType             =    servStateJsonObj.getString(Constants_jsonParsing.TAG_SERVICE_STATE_TIME_TYPE);

                Between between;
                try {
                    between = new Between(servStateJsonObj.getJSONObject(Constants_jsonParsing.TAG_SERVICE_STATE_BETWEEN_LOCATIONS));
                } catch (JSONException e){ between = null; }

                this.serviceObject      = serviceObject;
                this.timeSequence       = serviceTimeSequence;
                this.at                 = at;
                this.betweenLocations   = between;
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
     * @param String serviceObject
     * @param String serviceTimeSequence
     * @param String at
     * @param Between betweenLocations
     * @param String performingActor
     */

    // TODO TROR att service states ska ha antingen ett "at" för stationary states, ELLER ett "between" för nautical states
    public ServiceState(String serviceObject, String timeSequence, String at, Between betweenLocations, String performingActor) {
        this.serviceObject = serviceObject;
        this.timeSequence = timeSequence;
        this.at = at;
        this.betweenLocations = betweenLocations;
        this.performingActor = performingActor;
    }


    public String toXml() {
        String xmlStr = "";
        if(serviceObject != null)
            xmlStr += "<ns2:serviceObject>" + serviceObject + "</ns2:serviceObject>";
        if(timeSequence != null)
            xmlStr += "<ns2:timeSequence>" + timeSequence + "</ns2:timeSequence>";
        if(at != null)
            xmlStr += "<ns2:at>" + at + "</ns2:at>";
        if (betweenLocations != null)
            xmlStr += "<ns2:betweenLocations>" + betweenLocations.toXml() + "</ns2:betweenLocations>";
        if(performingActor != null)
            xmlStr += "<ns2:performingActor>" + performingActor + "</ns2:performingActor>";
        return xmlStr;
    }
}
