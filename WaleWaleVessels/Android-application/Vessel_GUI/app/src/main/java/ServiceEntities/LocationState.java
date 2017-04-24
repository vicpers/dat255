package ServiceEntities;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by maxedman on 2017-04-21.
 */

public class LocationState {

    private String referenceObject;
    private String time;
    private String timeType;
    private ArrivalLocation arrivalLocation;
    private DepartureLocation departureLocation;

    public LocationState(JSONObject locStateJsonObj){
        if (locStateJsonObj != null) {
            try {

                String referenceObject              = locStateJsonObj.getString(Constants_jsonParsing.TAG_LOCATION_STATE_REFERENCE_OBJECT);
                String time                         = locStateJsonObj.getString(Constants_jsonParsing.TAG_LOCATION_STATE_TIME);
                String timeType                     = locStateJsonObj.getString(Constants_jsonParsing.TAG_LOCATION_STATE_TIME_TYPE);

                ArrivalLocation arrivalLocation;
                try {
                    arrivalLocation = new ArrivalLocation(locStateJsonObj.getJSONObject(Constants_jsonParsing.TAG_LOCATION_STATE_ARRIVAL_LOCATION));
                } catch (JSONException e1){ arrivalLocation = null; }

                DepartureLocation departureLocation;
                try {
                    departureLocation = new DepartureLocation(locStateJsonObj.getJSONObject(Constants_jsonParsing.TAG_LOCATION_STATE_DEPARTURE_LOCATION));
                } catch (JSONException e2){ departureLocation = null; }

// Initiate new instance of PortCallMessage
                this.referenceObject    = referenceObject;
                this.time               = time;
                this.timeType           = timeType;
                this.arrivalLocation    = arrivalLocation;
                this.departureLocation  = departureLocation;

            } catch (JSONException e3) {
                Log.e("LocationState Constr.", "Problem getting strings - " + e3.toString());
            }
        } else {
            Log.e("LocationState Constr.", "param locStateJsonObj is null");
        }
    }

    public LocationState(String referenceObject, String time, String timeType, ArrivalLocation arrivalLocation, DepartureLocation departureLocation) {
        this.referenceObject = referenceObject;
        this.time = time;
        this.timeType = timeType;
        this.arrivalLocation = arrivalLocation;
        this.departureLocation = departureLocation;
    }
}
