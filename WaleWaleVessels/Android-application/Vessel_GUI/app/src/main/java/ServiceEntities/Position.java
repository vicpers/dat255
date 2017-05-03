package ServiceEntities;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by maxedman on 2017-04-21.
 */

public class Position {
    private double latitude;
    private double longitude;
    private String name;

    public Position(JSONObject posJsonObj){
        if (posJsonObj != null) {
            try {

                double latitude   =  posJsonObj.getDouble(Constants_jsonParsing.TAG_POSITION_LATITUDE);
                double longitude  =  posJsonObj.getDouble(Constants_jsonParsing.TAG_POSITION_LONGITUDE);

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

    public Position(double latitude, double longitude, String name){
        setLatitude(latitude);
        setLongitude(longitude);
        setName(name);
    }

    public Position(double latitude, double longitude){
        setLatitude(latitude);
        setLongitude(longitude);
    }

    public double getLongitude() {
        return longitude;
    }

    private void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    private void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getName(){return name;}

    public void setName(String name){this.name = name;}

    public String toXml() {
         String xmlStr = "<ns2:latitude>" + latitude + "</ns2:latitude>" +
                        "<ns2:longitude>" + longitude + "</ns2:longitude>";
        if(name != null)
            xmlStr += "<ns2:name>" + name + "</ns2:name>";
        return xmlStr;

    }
}
