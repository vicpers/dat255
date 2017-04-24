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

    public ServiceState(JSONObject servStateJsonObj){
        if (servStateJsonObj != null) {
            try {

                String serviceObject        =    servStateJsonObj.getString(Constants_jsonParsing.TAG_SERVICE_STATE_SERVICE_OBJECT);
                String serviceTimeSequence  =    servStateJsonObj.getString(Constants_jsonParsing.TAG_SERVICE_STATE_TIME_SEQUENCE);
                String at                   =    servStateJsonObj.getString(Constants_jsonParsing.TAG_SERVICE_STATE_AT);
                String performingActor      =    servStateJsonObj.getString(Constants_jsonParsing.TAG_SERVICE_STATE_PERFORMING_ACTOR);

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

    public ServiceState(String serviceObject, String timeSequence, String at, Between betweenLocations, String performingActor) {
        this.serviceObject = serviceObject;
        this.timeSequence = timeSequence;
        this.at = at;
        this.betweenLocations = betweenLocations;
        this.performingActor = performingActor;
    }
}
