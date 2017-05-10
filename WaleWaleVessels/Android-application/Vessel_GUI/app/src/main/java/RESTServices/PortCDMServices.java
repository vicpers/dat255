package RESTServices;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.NoSuchElementException;

import HTTPRequest.WebRequest;
import ServiceEntities.*;

import static RESTServices.Constants_API.API_ACTUAL_PORT;
import static RESTServices.Constants_API.API_DEV_BASE_URL;
import static RESTServices.Constants_API.API_DEV_KEY1;
import static RESTServices.Constants_API.API_DEV_PASSWORD;
import static RESTServices.Constants_API.API_DEV_PORT1;
import static RESTServices.Constants_API.API_DEV_USERNAME;
import static RESTServices.Constants_API.API_HEADER_ACCEPT;
import static RESTServices.Constants_API.API_HEADER_ACCEPT_JSON;
import static RESTServices.Constants_API.API_HEADER_API_KEY;
import static RESTServices.Constants_API.API_HEADER_PASSWORD;
import static RESTServices.Constants_API.API_HEADER_USER_ID;
import static RESTServices.Constants_API.API_SERVICE_GET_PORT;
import static RESTServices.Constants_API.API_SERVICE_GET_STATE_DEFINITIONS;
import static RESTServices.Constants_API.API_SERVICE_GET_VESSEL;
import static ServiceEntities.Constants_jsonParsing.TAG_LOCATION_TYPE;
import static ServiceEntities.Constants_jsonParsing.TAG_PORT_LOCATIONS;
import static ServiceEntities.Constants_jsonParsing.TAG_PORT_LOCATIONS_NAME;
import static ServiceEntities.Constants_jsonParsing.TAG_PORT_LOCATIONS_POSITION;
import static ServiceEntities.Constants_jsonParsing.TAG_PORT_LOCATIONS_SHORT_NAME;
import static ServiceEntities.Constants_jsonParsing.TAG_STATE_DEFINITION_LOCATION;
import static ServiceEntities.Constants_jsonParsing.TAG_STATE_DEFINITION_SERVICE;

/**
 * Created by maxedman on 2017-04-27.
 */

public class PortCDMServices {

    public static HashMap<LocationTimeSequence, ArrayList<LocationType>> locationStateLocationTypes = new HashMap<>();
    public static HashMap<ServiceObject, ArrayList<ServiceTimeSequence>> serviceStateTimeSequences = new HashMap<>();

    public static HashMap<ServiceObject, ServiceType> serviceStateType = new HashMap<>();

    public static HashMap<LocationType, HashMap<String, Position>> portData = new HashMap<>();


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
    public static ArrayList<LocationType> getStateDefinitions(LocationTimeSequence locationTimeSequence){
        return locationStateLocationTypes.get(locationTimeSequence);
    }


    /**
     * @param serviceObject
     * @return
     */
    public static ArrayList<ServiceTimeSequence> getStateDefinitions(ServiceObject serviceObject){
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
                                ArrayList<LocationType> tempArrLocType = locationStateLocationTypes.get(locTimeSeq);
                                if (tempArrLocType == null) {
                                    tempArrLocType = new ArrayList<LocationType>();
                                    tempArrLocType.add(locType);
                                    locationStateLocationTypes.put(locTimeSeq, tempArrLocType);
                                } else
                                    tempArrLocType.add(locType);
                            }
                        }catch (Exception e){Log.e("ServStateDef", e.toString());}
                    }

                    if(servStateDef != null) {
                        try {
                            ServiceObject servObj = ServiceObject.valueOf(servStateDef.getString(Constants_jsonParsing.TAG_SERVICE_STATE_SERVICE_OBJECT));
                            ServiceTimeSequence servTimeSeq = ServiceTimeSequence.valueOf(servStateDef.getString(Constants_jsonParsing.TAG_SERVICE_STATE_TIME_SEQUENCE));
                            ArrayList<ServiceTimeSequence> tempArrServTimeSeq = serviceStateTimeSequences.get(servObj);
                            if (tempArrServTimeSeq == null) {
                                tempArrServTimeSeq = new ArrayList<ServiceTimeSequence>();
                                tempArrServTimeSeq.add(servTimeSeq);
                                serviceStateTimeSequences.put(servObj, tempArrServTimeSeq);
                            } else {
                                tempArrServTimeSeq.add(servTimeSeq);
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
    }


    /**
     *
     */
    public static void getActualPortData(){

        String url = API_DEV_BASE_URL + ":" + API_DEV_PORT1 + API_SERVICE_GET_PORT + API_ACTUAL_PORT;

        HashMap<String, String> headers = new HashMap<String, String>();

        headers.put(API_HEADER_ACCEPT, API_HEADER_ACCEPT_JSON);
        headers.put(API_HEADER_USER_ID, API_DEV_USERNAME);
        headers.put(API_HEADER_PASSWORD, API_DEV_PASSWORD);
        headers.put(API_HEADER_API_KEY, API_DEV_KEY1);

        String wrResponse = WebRequest.makeWebServiceCall(url, 1, headers, null);

        try {
            JSONObject jsonObj  = new JSONObject(wrResponse);
            JSONArray jsonArr   = jsonObj.getJSONArray(TAG_PORT_LOCATIONS);
            for (int i = 0 ; i < jsonArr.length(); i++) {
                try {
                    JSONObject portLoc = jsonArr.getJSONObject(i);

                    LocationType locationType       = LocationType.valueOf(portLoc.getString(TAG_LOCATION_TYPE));
                    String portLocationName         = portLoc.getString(TAG_PORT_LOCATIONS_NAME);
                    String portLocationShortName    = portLoc.getString(TAG_PORT_LOCATIONS_SHORT_NAME);
                    Position portLocPosition        = null;
                    try {
                        portLocPosition = new Position(portLoc.getJSONObject(TAG_PORT_LOCATIONS_POSITION));
                        portLocPosition.setName(portLocationName);
                        portLocPosition.setShortName(portLocationShortName);
                    } catch (JSONException e){}


                    HashMap<String, Position> tempPortLocMap = portData.get(locationType);
                    if (tempPortLocMap == null) {
                        tempPortLocMap = new HashMap<String, Position>();
                        tempPortLocMap.put(portLocationName, portLocPosition);
                        portData.put(locationType, tempPortLocMap);
                    } else {
                        tempPortLocMap.put(portLocationName, portLocPosition);
                    }


                } catch (JSONException e1){
                    Log.e("GetPortData - Inner", e1.toString());
                }
            }

        } catch (JSONException e2){
            Log.e("GetPortData - Outer", e2.toString());
        }
    }


    /**
     * @param locationType
     * @return
     */
    public static HashMap<String, Position> getPortLocations(LocationType locationType){
        return portData.get(locationType);
    }
}
