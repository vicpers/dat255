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
    private String locationMRN = null;
    private String shortName;
    private String subLocationName;
    private String portUnlocode = API_ACTUAL_PORT;

    public Location(JSONObject locJsonObj){
        if (locJsonObj != null) {

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
            try {
                this.locationMRN = locJsonObj.getString(Constants_jsonParsing.TAG_LOCATIONS_URN);
            } catch (JSONException e1) {
                try {
                    this.locationMRN = locJsonObj.getString(Constants_jsonParsing.TAG_LOCATIONS_MRN);
                }catch (JSONException e3) {}
            }

            // Splits the locationMRN to LocationType and sublocation.
            if(locationType == null){
                String[] locationStrings = this.locationMRN.split(":");
                if(locationStrings.length > 5){
                    try {
                        locationType = LocationType.valueOf(locationStrings[5]);
                        subLocationName = locationStrings[6];
                    } catch (ArrayIndexOutOfBoundsException e){}
                }
            }

            //TODO Eventuellt ta bort om MRN för TrafficArea blir korrekt från registret.
            if(locationType == LocationType.TRAFFIC_AREA){
                this.locationMRN = "urn:mrn:stm:location:segot:TRAFFIC_AREA";
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

    public Location(String name, Position position, String locationMRN){
        setName(name);
        setPosition(position);
        setLocationMRN(locationMRN);
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

    public String getLocationMRN() {
        return locationMRN;
    }

    private void setLocationMRN(String locationMRN) {
        this.locationMRN = locationMRN;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    @Override
    public String toString() {
        if(locationMRN != null)
            return locationMRN;
        else if(name != null)
            return name;
        else
            return locationType.getText();
    }

    public String toXml() { // May not be in use anymore.
        String xmlStr = "";

        /*if(name != null)
            xmlStr += "<ns2:name>" + name + "</ns2:name>";*/
        /*if(locationType != null)
            xmlStr += "<ns2:locationType>" + locationType + "</ns2:locationType>";*/

        if (position != null)
            xmlStr += "<ns2:position>" + position.toXml() + "</ns2:position>";

        if(locationMRN != null){
            xmlStr += "<ns2:locationMRN>" + locationMRN + "</ns2:locationMRN>";
        } else if(locationType != null){
            xmlStr += "<ns2:locationMRN>urn:mrn:stm:location:" + portUnlocode + ":" + locationType + "</ns2:locationMRN>";
        } else {
            xmlStr += "<ns2:locationMRN>urn:mrn:stm:location:" + portUnlocode + "</ns2:locationMRN>";
        }
        return xmlStr;
    }
    public String getOperationType(){
        return locationType.getText();
    }
}
