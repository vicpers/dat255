package RESTServices;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.NoSuchElementException;

import HTTPRequest.WebRequest;
import ServiceEntities.Constants_jsonParsing;
import ServiceEntities.Location;
import ServiceEntities.LocationTimeSequence;
import ServiceEntities.LocationType;
import ServiceEntities.ReferenceObject;
import ServiceEntities.ServiceObject;
import ServiceEntities.ServiceTimeSequence;
import ServiceEntities.ServiceType;
import ServiceEntities.Vessel;

import static RESTServices.Constants_API.API_DEV_BASE_URL;
import static RESTServices.Constants_API.API_DEV_BASE_URL_LOCATION_REGISTRY;
import static RESTServices.Constants_API.API_DEV_KEY1;
import static RESTServices.Constants_API.API_DEV_PASSWORD;
import static RESTServices.Constants_API.API_DEV_PORT1;
import static RESTServices.Constants_API.API_DEV_USERNAME;
import static RESTServices.Constants_API.API_HEADER_ACCEPT;
import static RESTServices.Constants_API.API_HEADER_ACCEPT_JSON;
import static RESTServices.Constants_API.API_HEADER_API_KEY;
import static RESTServices.Constants_API.API_HEADER_PASSWORD;
import static RESTServices.Constants_API.API_HEADER_USER_ID;
import static RESTServices.Constants_API.API_SERVICE_FIND_LOCATIONS;
import static RESTServices.Constants_API.API_SERVICE_GET_STATE_DEFINITIONS;
import static RESTServices.Constants_API.API_SERVICE_GET_VESSEL;
import static ServiceEntities.Constants_jsonParsing.TAG_STATE_DEFINITION_LOCATION;
import static ServiceEntities.Constants_jsonParsing.TAG_STATE_DEFINITION_SERVICE;


/**
 * Created by maxedman on 2017-04-27.
 */
//TODO kommentera
public class PortCDMServices {

    private static HashMap<LocationTimeSequence, HashMap<String, LocationType>> locationStateLocationTypes = new HashMap<>();
    private static HashMap<ServiceObject, HashMap<String, ServiceTimeSequence>> serviceStateTimeSequences = new HashMap<>();

    private static HashMap<ServiceObject, ServiceType> serviceStateType = new HashMap<>();

    private static HashMap<LocationType, HashMap<String, Location>> portData;
    private static HashMap<String, Location> locationRegistry = new HashMap<>();


    /**
     * Method for setting the instancevessel based only on a ID.
     * @param vesselID
     * @return
     * @throws NoSuchElementException
     */
    public static Vessel getVessel(String vesselID) throws NoSuchElementException{

        String url = API_DEV_BASE_URL + ":" + API_DEV_PORT1 + API_SERVICE_GET_VESSEL + vesselID;

        HashMap<String, String> headers = new HashMap<String, String>();

        headers.put(API_HEADER_ACCEPT, API_HEADER_ACCEPT_JSON);
        headers.put(API_HEADER_USER_ID, API_DEV_USERNAME);
        headers.put(API_HEADER_PASSWORD, API_DEV_PASSWORD);
        headers.put(API_HEADER_API_KEY, API_DEV_KEY1);

        String wrResponse = WebRequest.makeWebServiceCall(url, 1, headers, null);

        try {
            JSONObject jsonObj = new JSONObject(wrResponse);
            Vessel returnVessel = new Vessel(jsonObj);
            returnVessel.setId(vesselID);
            return  returnVessel;
        } catch (JSONException e){
            throw new NoSuchElementException("Vessel with ID " + vesselID + " not in registry");
//            return new Vessel(vesselID);
        }
    }

    /**
     * @param locationTimeSequence
     * @return
     */
    public static HashMap<String, LocationType> getStateDefinitions(LocationTimeSequence locationTimeSequence){
        return locationStateLocationTypes.get(locationTimeSequence);
    }

    /**
     * @param serviceObject
     * @return
     */
    public static HashMap<String, ServiceTimeSequence> getStateDefinitions(ServiceObject serviceObject){
        return serviceStateTimeSequences.get(serviceObject);
    }

    /**
     * @param serviceObject
     * @return
     */
    public static ServiceType getServiceType(ServiceObject serviceObject){
        return serviceStateType.get(serviceObject);
    }


    /**
     * Fetches all state definitions in PortCDM and saves them in a static hashmap
     * which is used later on to send "Report update"-PortCallMessages.
     */
    public static void getStateDefinitions(){

        String url = API_DEV_BASE_URL + ":" + API_DEV_PORT1 + API_SERVICE_GET_STATE_DEFINITIONS;

        HashMap<String, String> headers = new HashMap<String, String>();

        headers.put(API_HEADER_ACCEPT, API_HEADER_ACCEPT_JSON);
        headers.put(API_HEADER_USER_ID, API_DEV_USERNAME);
        headers.put(API_HEADER_PASSWORD, API_DEV_PASSWORD);
        headers.put(API_HEADER_API_KEY, API_DEV_KEY1);

        String wrResponse = WebRequest.makeWebServiceCall(url, 1, headers, null);

        try {
            JSONArray jsonArr = new JSONArray(wrResponse);
            for (int i = 0 ; i < jsonArr.length(); i++) {
                try {
                    JSONObject jsonObj = jsonArr.getJSONObject(i);

                    JSONObject locStateDef = null;
                    JSONObject servStateDef = null;
                    try {
                        locStateDef = jsonObj.getJSONObject(TAG_STATE_DEFINITION_LOCATION);
                    } catch (JSONException e){ }
                    try {
                        servStateDef = jsonObj.getJSONObject(TAG_STATE_DEFINITION_SERVICE);
                    } catch (JSONException e){ }

                    if(locStateDef != null){
                        //System.out.println(locStateDef.toString());
                        try {
                            if (ReferenceObject.VESSEL == ReferenceObject.valueOf(locStateDef.getString(Constants_jsonParsing.TAG_LOCATION_STATE_REFERENCE_OBJECT))) {
                                LocationTimeSequence locTimeSeq = LocationTimeSequence.valueOf(locStateDef.getString(Constants_jsonParsing.TAG_LOCATION_STATE_TIME_SEQUENCE));
                                LocationType locType = LocationType.valueOf(locStateDef.getString(Constants_jsonParsing.TAG_LOCATION_TYPE));
                                HashMap<String, LocationType> tempMapLocType = locationStateLocationTypes.get(locTimeSeq);
                                if (tempMapLocType == null) {
                                    tempMapLocType = new HashMap<String, LocationType>();
                                    tempMapLocType.put(locType.getText(), locType);
                                    locationStateLocationTypes.put(locTimeSeq, tempMapLocType);
                                } else
                                    tempMapLocType.put(locType.getText(), locType);
                            }
                        }catch (Exception e){Log.e("ServStateDef", e.toString());}
                    }

                    if(servStateDef != null) {
                        try {
                            ServiceObject servObj = ServiceObject.valueOf(servStateDef.getString(Constants_jsonParsing.TAG_SERVICE_STATE_SERVICE_OBJECT));
                            ServiceTimeSequence servTimeSeq = ServiceTimeSequence.valueOf(servStateDef.getString(Constants_jsonParsing.TAG_SERVICE_STATE_TIME_SEQUENCE));
                            HashMap<String, ServiceTimeSequence> tempMapServTimeSeq = serviceStateTimeSequences.get(servObj);
                            if (tempMapServTimeSeq == null) {
                                tempMapServTimeSeq = new HashMap<String, ServiceTimeSequence>();
                                tempMapServTimeSeq.put(servTimeSeq.getText(), servTimeSeq);
                                serviceStateTimeSequences.put(servObj, tempMapServTimeSeq);
                            } else {
                                tempMapServTimeSeq.put(servTimeSeq.getText(), servTimeSeq);
                            }

                            ServiceType servType = ServiceType.valueOf(servStateDef.getString(Constants_jsonParsing.TAG_STATE_DEFINITION_SERVICE_TYPE));
                            serviceStateType.put(servObj, servType);

                        }catch (Exception e){Log.e("ServStateDef", e.toString());}
                    }
                } catch (JSONException e1){
                    Log.e("GetStateDef - Inner", e1.toString());
                }
            }

        } catch (JSONException e2){
            Log.e("GetStateDef - Outer", e2.toString());
        }
        Log.e("ServiceStates", serviceStateTimeSequences.toString());
    }


    /**
     *
     */
    public static void getActualPortData(){
        // Clears previous port data
        portData = new HashMap<>();

        String url = API_DEV_BASE_URL_LOCATION_REGISTRY + ":" + API_DEV_PORT1 + API_SERVICE_FIND_LOCATIONS;

        HashMap<String, String> headers = new HashMap<String, String>();

        headers.put(API_HEADER_ACCEPT, API_HEADER_ACCEPT_JSON);
        headers.put(API_HEADER_USER_ID, API_DEV_USERNAME);
        headers.put(API_HEADER_PASSWORD, API_DEV_PASSWORD);
        headers.put(API_HEADER_API_KEY, API_DEV_KEY1);

        String wrResponse = WebRequest.makeWebServiceCall(url, 1, headers, null);

        try {
//            JSONObject jsonObj  = new JSONObject(wrResponse);
//            JSONArray jsonArr   = jsonObj.getJSONArray(TAG_PORT_LOCATIONS);
            JSONArray jsonArr   = new JSONArray(wrResponse);
            for (int i = 0 ; i < jsonArr.length(); i++) {
                try {
                    JSONObject portLocObj = jsonArr.getJSONObject(i);
                    Location portLocation = new Location(portLocObj);
                    locationRegistry.put(portLocation.getLocationMRN(), portLocation);

                    HashMap<String, Location> tempPortLocMap = portData.get(portLocation.getType());
                    if (tempPortLocMap == null) {
                        tempPortLocMap = new HashMap<String, Location>();
                        tempPortLocMap.put(portLocation.getName(), portLocation);
                        portData.put(portLocation.getType(), tempPortLocMap);
                    } else {
                        tempPortLocMap.put(portLocation.getName(), portLocation);
                    }

                } catch (JSONException e1){
                    Log.e("GetPortData - Inner", e1.toString());
                }
            }

        } catch (JSONException e2){
            Log.e("GetPortData - Outer", e2.toString());
        }
        Log.e("PortData", portData.toString());
    }


    /**
     * @param locationType
     * @return Hashmap or null if LocationType is not found.
     */
    public static HashMap<String, Location> getPortLocations(LocationType locationType){
        return portData.get(locationType);
    }

    public static Location getLocation(String locationMrn){
        return locationRegistry.get(locationMrn);
    }

    public static String stringToDate(String dateString){

        // Converts the date and time from input into date on the form "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
        // which PortCDM requires.
        SimpleDateFormat dateInput = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        SimpleDateFormat dateOutput = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        String formattedTime = "";
        try {
            date = dateInput.parse(dateString);
            formattedTime = dateOutput.format(date);
        } catch (ParseException e1) {
            Log.e("DateProblem Parsing", e1.toString());
            Log.e("DateProblem Parsing", "Trying again");
            try {
                dateInput = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                date = dateInput.parse(dateString);
                formattedTime = dateOutput.format(date);
            } catch (ParseException e2) {
                Log.e("DateProblem Parsing", e2.toString());
            }catch (NullPointerException e3){
                Log.e("DateProblem Null", e3.toString());
            }

        } catch (NullPointerException e2){
            Log.e("DateProblem Null", e2.toString());
        }
        return formattedTime;
    }

    public static String stringToTime(String dateString){

        // Converts the date and time from input into date on the form "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
        // which PortCDM requires.
        SimpleDateFormat dateInput = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        SimpleDateFormat dateOutput = new SimpleDateFormat("HH:mm");
        Date date = null;
        String formattedTime = "";
        try {
            date = dateInput.parse(dateString);
            formattedTime = dateOutput.format(date);
        } catch (ParseException e1) {
            Log.e("DateProblem Parsing", e1.toString());
            Log.e("DateProblem Parsing", "Trying again");
            try {
                dateInput = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                date = dateInput.parse(dateString);
                formattedTime = dateOutput.format(date);
            } catch (ParseException e2) {
                Log.e("DateProblem Parsing", e2.toString());
            }catch (NullPointerException e3){
                Log.e("DateProblem Null", e3.toString());
            }
        } catch (NullPointerException e2){
            Log.e("DateProblem Null", e2.toString());
        }
        return formattedTime;
    }
}
