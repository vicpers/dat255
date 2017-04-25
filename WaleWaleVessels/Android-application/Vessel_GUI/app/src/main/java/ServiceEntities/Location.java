package ServiceEntities;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by maxedman on 2017-04-21.
 */

public class Location {

    private String name;
    private Position position;
    private LocationType locationType;

    public Location(JSONObject locJsonObj){
        if (locJsonObj != null) {
            try {

                LocationType locationType   = LocationType.valueOf(locJsonObj.getString(Constants_jsonParsing.TAG_LOCATION_TYPE));
                String name                 = locJsonObj.getString(Constants_jsonParsing.TAG_LOCATION_NAME);

                Position position;
                try {
                    position = new Position(locJsonObj.getJSONObject(Constants_jsonParsing.TAG_LOCATION_POSITION));
                } catch (JSONException e1) { position = null; }

                this.locationType  = locationType;
                this.position = position;
                this.name = name;

            } catch (JSONException e2) {
                Log.e("Location Constructor", "Problem getting strings - " + e2.toString());
            }
        } else {
            Log.e("Location Constructor", "param locJsonObj is null");
        }
    }

    public Location(String name, Position position, LocationType locationType){
        setName(name);
        setPosition(position);
        setLocationType(locationType);
    }

    public String getName() {
        return name;
    }

    private void setName(String name) {
        this.name = name;
    }

    public Position getPosition() {
        return position;
    }

    private void setPosition(Position position) {
        this.position = position;
    }

    public LocationType getType() {
        return locationType;
    }

    private void setLocationType(LocationType locationType) {
        this.locationType = locationType;
    }

    public String toXml() {
        String xmlStr = "";
        if(name != null)
            xmlStr += "<ns2:name>" + name + "</ns2:name>";
        if (position != null)
            xmlStr += "<ns2:position>" + position.toXml() + "</ns2:position>";
        if(locationType != null)
            xmlStr += "<ns2:locationType>" + locationType + "</ns2:locationType>";
        return xmlStr;
    }
}
