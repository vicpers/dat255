package ServiceEntities;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by maxedman on 2017-04-21.
 * A Class representing a Between, i.e. two Locations, which is part of a Service State.
 */

public class Between {
    private Location from;
    private Location to;

    /**
     * Creates a Between from a JSON Object
     * @param betwJsonObj
     */
    public Between(JSONObject betwJsonObj){
        if (betwJsonObj != null) {

            Location from;
            try {
                from  =  new Location(betwJsonObj.getJSONObject(Constants_jsonParsing.TAG_LOCATION_FROM));
            } catch (JSONException e1){ from = null; }

            Location to;
            try {
                to    =  new Location(betwJsonObj.getJSONObject(Constants_jsonParsing.TAG_LOCATION_TO));
            } catch (JSONException e2){ to = null; }

            this.from = from;
            this.to = to;

        } else {
            Log.e("Between Constructor", "param betwJsonObj is null");
        }
    }

    /**
     * Creates a Between from two locations
     * @param from
     * @param to
     */
    public Between(Location from, Location to){
        this.from = from;
        this.to = to;
    }

    public Location getFrom() {
        return from;
    }

    private void setFrom(Location from) {
        this.from = from;
    }

    public Location getTo() {
        return to;
    }

    private void setTo(Location to) {
        this.to = to;
    }

    /**
     * @return An XML-representation of the Between class
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
     * @return a String containing the LocationMRN. Looks into both locations.
     */
    public String getLocationMRN(){
        if (from != null && to != null) {
            Log.e("4", "4");
            StringBuilder sb = new StringBuilder();
            sb.append(from.getLocationMRN());
            sb.append("/");
            sb.append(to.getLocationMRN());
            Log.e("Test", "Sjofräs");
            return sb.toString();
        } else if (to != null) {
            Log.e("5", "5");
            return to.getLocationMRN();
        } else if (from != null) {
            Log.e("6", "6");
            return from.getLocationMRN();
        }
        return null;
    }
}
