package ServiceEntities;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by maxedman on 2017-04-21.
 * A class representing a LocationState, which is part of a PortCall Message
 */

public class LocationState {

    private ReferenceObject referenceObject;
    private String time;
    private TimeType timeType;
    private ArrivalLocation arrivalLocation;
    private DepartureLocation departureLocation;

    /**
     * Creates a LocationState from a JSON object.
     * @param locStateJsonObj
     */
    public LocationState(JSONObject locStateJsonObj) {
        if (locStateJsonObj != null) {
            try {

                ReferenceObject referenceObject = ReferenceObject.valueOf(locStateJsonObj.getString(Constants_jsonParsing.TAG_LOCATION_STATE_REFERENCE_OBJECT));
                String time = locStateJsonObj.getString(Constants_jsonParsing.TAG_LOCATION_STATE_TIME);
                TimeType timeType = TimeType.valueOf(locStateJsonObj.getString(Constants_jsonParsing.TAG_LOCATION_STATE_TIME_TYPE));

                ArrivalLocation arrivalLocation;
                try {
                    arrivalLocation = new ArrivalLocation(locStateJsonObj.getJSONObject(Constants_jsonParsing.TAG_LOCATION_STATE_ARRIVAL_LOCATION));
                } catch (JSONException e1) {
                    arrivalLocation = null;
                }

                DepartureLocation departureLocation;
                try {
                    departureLocation = new DepartureLocation(locStateJsonObj.getJSONObject(Constants_jsonParsing.TAG_LOCATION_STATE_DEPARTURE_LOCATION));
                } catch (JSONException e2) {
                    departureLocation = null;
                }

// Initiate new instance of PortCallMessage
                this.referenceObject = referenceObject;
                this.time = time;
                this.timeType = timeType;
                this.arrivalLocation = arrivalLocation;
                this.departureLocation = departureLocation;

            } catch (JSONException e3) {
                Log.e("LocationState Constr.", "Problem getting strings - " + e3.toString());
            }
        } else {
            Log.e("LocationState Constr.", "param locStateJsonObj is null");
        }
    }

    /**
     * Constructor for creating LocationState by data with arrivalLocation.
     *
     * @param referenceObject
     * @param time
     * @param timeType
     * @param arrivalLocation
     */
    public LocationState(ReferenceObject referenceObject, String time, TimeType timeType, ArrivalLocation arrivalLocation) {
        this.referenceObject = referenceObject;
        this.time = time;
        this.timeType = timeType;
        this.arrivalLocation = arrivalLocation;
    }

    /**
     * Constructor for creating LocationState by data with depLocation.
     *
     * @param referenceObject
     * @param time
     * @param timeType
     * @param departureLocation
     */
    public LocationState(ReferenceObject referenceObject, String time, TimeType timeType, DepartureLocation departureLocation) {
        this.referenceObject = referenceObject;
        this.time = time;
        this.timeType = timeType;
        this.departureLocation = departureLocation;
    }

    public ReferenceObject getReferenceObject() {
        return referenceObject;
    }

    /**
     * @return a String with timestamp from when LocationState was created.
     */
    public String getTime() {
        return time;
    }

    /**
     * @return a String with the TimeType i.e. if the ServiceState is Actual, Estimated etc.
     */
    public TimeType getTimeType() {
        return timeType;
    }

    public ArrivalLocation getArrivalLocation() {
        return arrivalLocation;
    }

    public DepartureLocation getDepartureLocation() {
        return departureLocation;
    }

    public String toString() {
        String returnString = "TimeType: " + timeType + "\nTime: " + time;
        if (arrivalLocation != null)
            returnString += "\nArrivalLocation: " + arrivalLocation.toString();
        else if (departureLocation != null)
            returnString += "\nDepartureLocation: " + departureLocation.toString();
        else
            returnString += "\nLocation: No location found!";
        return returnString;
    }

    /**
     * @return a String containing a XML-representation of the class.
     */
    public String toXml() {
        String xmlStr = "";
        if (referenceObject != null)
            xmlStr += "<ns2:referenceObject>" + referenceObject + "</ns2:referenceObject>";
        if (time != null)
            xmlStr += "<ns2:time>" + time + "</ns2:time>";
        if (timeType != null)
            xmlStr += "<ns2:timeType>" + timeType + "</ns2:timeType>";
        if (arrivalLocation != null)
            xmlStr += "<ns2:arrivalLocation>" + arrivalLocation.toXml() + "</ns2:arrivalLocation>";
        if (departureLocation != null)
            xmlStr += "<ns2:departureLocation>" + departureLocation.toXml() + "</ns2:departureLocation>";

        return xmlStr;

    }

    /**
     * @return a String with the OperationType of the LocationState.
     */
    public String getOperationType() {
        String operationType;
        if (arrivalLocation != null) {
            operationType = arrivalLocation.getOperationType();
            return operationType + " Arrival" ;
        } else if (departureLocation != null) {
            operationType =  departureLocation.getOperationType();
            return operationType + " Departure";
        }
        return null;

    }

    /**
     * @return a String the LoationMRN of the LocationState.
     */
    public String getLocationMRN(){
        if (arrivalLocation != null) {
            return arrivalLocation.getLocationMRN();
        } else if (departureLocation != null) {
            return departureLocation.getLocationMRN();
        }
        return null;
    }


}
