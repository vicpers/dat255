package ServiceEntities;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by maxedman on 2017-04-21.
 */

public class ArrivalLocation {
    private Location to;
    private Location from;

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

    public String toXml() {
        String xmlStr = "";
        if (to != null)
            xmlStr += "<ns2:to>" + to.toXml() + "</ns2:to>";
        if (from != null)
            xmlStr += "<ns2:from>" + from.toXml() + "</ns2:from>";
        return xmlStr;
    }


}
