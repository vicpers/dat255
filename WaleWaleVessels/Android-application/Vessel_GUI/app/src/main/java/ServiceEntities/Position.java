package ServiceEntities;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by maxedman on 2017-04-21.
 */

public class Position {
    private long latitude;
    private long longitude;

    public Position(JSONObject posJsonObj){
        if (posJsonObj != null) {
            try {

                long latitude   =  posJsonObj.getLong(Constants_jsonParsing.TAG_POSITION_LATITUDE);
                long longitude  =  posJsonObj.getLong(Constants_jsonParsing.TAG_POSITION_LONGITUDE);

// Initiate new instance of PortCallMessage
                this.latitude = latitude;
                this.longitude = longitude;

            } catch (JSONException e) {
                Log.e("Position Constructor", "Problem getting long - " + e.toString());
            }
        } else {
            Log.e("Position Constructor", "param posJsonObj is null");
        }
    }

    public Position(long latitude, long longitude){
        setLatitude(latitude);
        setLongitude(longitude);
    }

    public long getLongitude() {
        return longitude;
    }

    private void setLongitude(long longitude) {
        this.longitude = longitude;
    }

    public long getLatitude() {
        return latitude;
    }

    private void setLatitude(long latitude) {
        this.latitude = latitude;
    }
}
