package ServiceEntities;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by maxedman on 2017-04-21.
 * Class for representing a Position, which contains coordinates, name and shortName.
 */

public class Position {
    private double latitude;
    private double longitude;
    private String name;
    private String shortName;

    /**
     * Creates a Position from a JSON object
     * @param posJsonObj
     */
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

    /**
     * Creates a Position from coordinates and name
     * @param latitude
     * @param longitude
     * @param name
     */
    public Position(double latitude, double longitude, String name){
        setLatitude(latitude);
        setLongitude(longitude);
        setName(name);
    }

    /** Creates Position from coordinates
     * @param latitude
     * @param longitude
     */
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


    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    /**
     * @return a String containing an XML-representation of the object
     */
    public String toXml() {
         String xmlStr = "<ns2:latitude>" + latitude + "</ns2:latitude>" +
                        "<ns2:longitude>" + longitude + "</ns2:longitude>";
//        Not included in version 0.6 of portCallMessage XML
//        if(name != null)
//            xmlStr += "<ns2:name>" + name + "</ns2:name>";
        return xmlStr;

    }
}
