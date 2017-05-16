package ServiceEntities;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import static RESTServices.Constants_API.API_ACTUAL_PORT;

/**
 * Created by maxedman on 2017-04-21.
 */

public class Location {

    private String name;
    private Position position;
    private LocationType locationType;
    private String URN = null;
    private String shortName;
    private String portUnlocode = API_ACTUAL_PORT;

    public Location(JSONObject locJsonObj){
        if (locJsonObj != null) {
            try {
                this.URN            = locJsonObj.getString(Constants_jsonParsing.TAG_LOCATIONS_URN);
                Position position = null;
                try {
                    position = new Position(locJsonObj.getJSONObject(Constants_jsonParsing.TAG_LOCATION_POSITION));
                } catch (JSONException e1) {}
                try {
                    this.locationType   = LocationType.valueOf(locJsonObj.getString(Constants_jsonParsing.TAG_LOCATION_TYPE));
                } catch (JSONException e1) {}
                try {
                    this.name           = locJsonObj.getString(Constants_jsonParsing.TAG_LOCATION_NAME);
                } catch (JSONException e1) {}
                try {
                    this.shortName      = locJsonObj.getString(Constants_jsonParsing.TAG_PORT_LOCATIONS_SHORT_NAME);
                } catch (JSONException e1) {}

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

    public Location(String name, Position position, String URN){
        setName(name);
        setPosition(position);
        setURN(URN);
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

    public String getURN() {
        return URN;
    }

    private void setURN(String URN) {
        this.URN = URN;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }


    public String toXml() { // May not be in use anymore.
        String xmlStr = "";

        /*if(name != null)
            xmlStr += "<ns2:name>" + name + "</ns2:name>";*/
        /*if(locationType != null)
            xmlStr += "<ns2:locationType>" + locationType + "</ns2:locationType>";*/

        if (position != null)
            xmlStr += "<ns2:position>" + position.toXml() + "</ns2:position>";

        if(URN != null){
            xmlStr += "<ns2:locationMRN>" + URN + "</ns2:locationMRN>";
        } else if(locationType != null){
            xmlStr += "<ns2:locationMRN>urn:mrn:stm:location:" + portUnlocode + ":" + locationType + "</ns2:locationMRN>";
        } else {
            xmlStr += "<ns2:locationMRN>urn:mrn:stm:location:" + portUnlocode + "</ns2:locationMRN>";
        }
        return xmlStr;
    }
}
