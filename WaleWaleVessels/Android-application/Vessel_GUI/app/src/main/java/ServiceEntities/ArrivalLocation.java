package ServiceEntities;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * A Class representing an ArrivalLocation which is a part of a PortCall Message.
 */

public class ArrivalLocation {
    private Location to;
    private Location from;

    /**
     * Creates an ArrivalLocation from a JSONObject
     * @param arrLocJsonObj
     */
    public ArrivalLocation(JSONObject arrLocJsonObj){
        if (arrLocJsonObj != null) {

            Location from;
            try {
                from  =  new Location(arrLocJsonObj.getJSONObject(Constants_jsonParsing.TAG_LOCATION_FROM));
            } catch (JSONException e){ from = null; }

            Location to;
            try {
                to    =  new Location(arrLocJsonObj.getJSONObject(Constants_jsonParsing.TAG_LOCATION_TO));
            } catch (JSONException e){ to = null; }

            this.from = from;
            this.to = to;

        } else {
            Log.e("ArrivalLocation Constr.", "param arrLocJsonObj is null");
        }
    }

    /**
     * Creates an ArrivalLocation from two Locations
     * @param from
     * @param to
     */
    public ArrivalLocation(Location from, Location to){
        this.to = to;
        this.from = from;
    }

    @Override
    public String toString() {
        if(to != null)
            return to.toString();
        else
            return from.toString();
    }

    /**
     * @return a String with an XML-representation of the class
     */
    public String toXml() {
        String xmlStr = "";
        if (to != null)
            xmlStr += "<ns2:to>" + to.toXml() + "</ns2:to>";
        if (from != null)
            xmlStr += "<ns2:from>" + from.toXml() + "</ns2:from>";
        return xmlStr;
    }

    /**
     * @return a String with OperationType. Looks into both Locations.
     */
    public String getOperationType(){
        if (to != null)
            return to.getOperationType();
        if (from != null)
            return from.getOperationType();
        return null;
    }

    /**
     * @return Returns the locationMRN. Looks into both Locations.
     */
    public String getLocationMRN(){
        if (to != null)
            return to.getLocationMRN();
        if (from != null)
            return from.getLocationMRN();
        return null;
    }


}
