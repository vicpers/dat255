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
    private String type;

    public Location(JSONObject locJsonObj){
        if (locJsonObj != null) {
            try {

                String locationType = locJsonObj.getString(Constants_jsonParsing.TAG_LOCATION_TYPE);
                String name         = locJsonObj.getString(Constants_jsonParsing.TAG_LOCATION_NAME);

                Position position;
                try {
                    position = new Position(locJsonObj.getJSONObject(Constants_jsonParsing.TAG_LOCATION_POSITION));
                } catch (JSONException e1) { position = null; }

                this.type  = locationType;
                this.position = position;
                this.name = name;

            } catch (JSONException e2) {
                Log.e("Location Constructor", "Problem getting strings - " + e2.toString());
            }
        } else {
            Log.e("Location Constructor", "param locJsonObj is null");
        }
    }

    public Location(String name, Position position, String type){
        setName(name);
        setPosition(position);
        setType(type);
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

    public String getType() {
        return type;
    }

    private void setType(String type) {
        this.type = type;
    }

}
