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

    /**
     * Constructor for creating LocationState by data.
     * @param referenceObject
     * @param time
     * @param timeType
     * @param arrivalLocation
     * @param departureLocation
     */
    public LocationState(String referenceObject, String time, String timeType, ArrivalLocation arrivalLocation, DepartureLocation departureLocation) {
        this.referenceObject = referenceObject;
        this.time = time;
        this.timeType = timeType;
        this.arrivalLocation = arrivalLocation;
        this.departureLocation = departureLocation;
    }

    public String toXml() {
        String xmlStr = "";
        if(referenceObject != null)
            xmlStr += "<ns2:referenceObject>" + referenceObject   + "</ns2:referenceObject>";
        if(time != null)
            xmlStr += "<ns2:time>"            + time              + "</ns2:time>";
        if(timeType != null)
            xmlStr += "<ns2:timeType>"        + timeType          + "</ns2:timeType>";
        if (arrivalLocation != null)
            xmlStr += "<ns2:arrivalLocation>" + arrivalLocation.toXml() + "</ns2:arrivalLocation>";
        if (departureLocation != null)
            xmlStr += "<ns2:departureLocation>" + departureLocation.toXml() + "</ns2:departureLocation>";

        return xmlStr;

    }
}
