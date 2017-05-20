package ServiceEntities;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by maxedman on 2017-04-21.
 * A Class representing an DepartureLocation which is a part of a PortCall Message.
 */

public class DepartureLocation {
    private Location to;
    private Location from;

    /**
     * Creates a DepartureLocation from a JSONObject
     * @param depLocJsonObj
     */
    public DepartureLocation(JSONObject depLocJsonObj){
        if (depLocJsonObj != null) {

            Location from;
            try {
                from  =  new Location(depLocJsonObj.getJSONObject(Constants_jsonParsing.TAG_LOCATION_FROM));
            } catch (JSONException e){ from = null; }

            Location to;
            try {
                to    =  new Location(depLocJsonObj.getJSONObject(Constants_jsonParsing.TAG_LOCATION_TO));
            } catch (JSONException e){ to = null; }

            // Initiate new instance of DepartureLocation
            this.from   = from;
            this.to     = to;

        } else {
            Log.e("DepartureLocation Const", "param depLocJsonObj is null");
        }
    }

    /**
     * Creates a DepartureLocation from two locations.
     * @param from
     * @param to
     */
    public DepartureLocation(Location from, Location to){
        this.from = from;
        this.to = to;
    }

    @Override
    public String toString() {
        if(to != null)
            return to.toString();
        else
            return from.toString();
    }

    /**
     * @return a String with an XML-representation of the class.
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
     * @return a String with the operation type. Looks into both locations.
     */
    public String getOperationType(){
        if (to != null)
        return to.getOperationType();
        else return from.getOperationType();
    }

    /**
     * @return a String with the locationMRN. Looks into both locations. 
     */
    public String getLocationMRN(){
        if (to != null)
            return to.getLocationMRN();
        if (from != null)
            return from.getLocationMRN();
        return null;
    }
}
