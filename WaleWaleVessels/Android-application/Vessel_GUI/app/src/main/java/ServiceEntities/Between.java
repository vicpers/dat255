package ServiceEntities;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by maxedman on 2017-04-21.
 */

public class Between {
    private Location from;
    private Location to;

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

    public String toXml() {
        String xmlStr = "";
        if (to != null)
            xmlStr += "<ns2:to>" + to.toXml() + "</ns2:to>";
        if (from != null)
            xmlStr += "<ns2:from>" + from.toXml() + "</ns2:from>";
        return xmlStr;
    }
}
