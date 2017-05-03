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
import static RESTServices.Constants_API.API_SERVICE_GET_STATE_DEFINITIONS;
import static RESTServices.Constants_API.API_SERVICE_GET_VESSEL;

/**
 * Created by maxedman on 2017-04-27.
 */

public class PortCDMServices {

    public static HashMap<LocationTimeSequence, ArrayList<LocationType>> locationStateDefinitions = new HashMap<>();
    public static HashMap<ServiceObject, ArrayList<ServiceTimeSequence>> serviceStateDefinitions = new HashMap<>();


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

    public ArrayList<LocationType> getStateDefinitions(LocationTimeSequence locationTimeSequence){
        return locationStateDefinitions.get(locationTimeSequence);
    }

    public ArrayList<ServiceTimeSequence> getStateDefinitions(ServiceObject serviceObject){
        return serviceStateDefinitions.get(serviceObject);
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
                        locStateDef = jsonObj.getJSONObject(Constants_jsonParsing.TAG_STATE_DEFINITION_LOCATION);
                    } catch (JSONException e1){ }
                    try {
                        servStateDef = jsonObj.getJSONObject(Constants_jsonParsing.TAG_STATE_DEFINITION_SERVICE);
                    } catch (JSONException e1){ }

                    if(locStateDef != null){
                        //System.out.println(locStateDef.toString());
                        try {
                            if (ReferenceObject.VESSEL == ReferenceObject.valueOf(locStateDef.getString(Constants_jsonParsing.TAG_LOCATION_STATE_REFERENCE_OBJECT))) {
                                LocationTimeSequence locTimeSeq = LocationTimeSequence.valueOf(locStateDef.getString(Constants_jsonParsing.TAG_LOCATION_STATE_TIME_SEQUENCE));
                                LocationType locType = LocationType.valueOf(locStateDef.getString(Constants_jsonParsing.TAG_LOCATION_TYPE));
                                ArrayList<LocationType> tempArrLocType = locationStateDefinitions.get(locTimeSeq);
                                if (tempArrLocType == null) {
                                    tempArrLocType = new ArrayList<LocationType>();
                                    tempArrLocType.add(locType);
                                    locationStateDefinitions.put(locTimeSeq, tempArrLocType);
                                } else
                                    tempArrLocType.add(locType);
                            }
                        }catch (Exception e){Log.e("ServStateDef", e.toString());}
                    }

                    if(servStateDef != null) {
                        try {
                            ServiceObject servObj = ServiceObject.valueOf(servStateDef.getString(Constants_jsonParsing.TAG_SERVICE_STATE_SERVICE_OBJECT));
                            ServiceTimeSequence servTimeSeq = ServiceTimeSequence.valueOf(servStateDef.getString(Constants_jsonParsing.TAG_SERVICE_STATE_TIME_SEQUENCE));
                            ArrayList<ServiceTimeSequence> tempArrServTimeSeq = serviceStateDefinitions.get(servObj);
                            if (tempArrServTimeSeq == null) {
                                tempArrServTimeSeq = new ArrayList<ServiceTimeSequence>();
                                tempArrServTimeSeq.add(servTimeSeq);
                                serviceStateDefinitions.put(servObj, tempArrServTimeSeq);
                            } else
                                tempArrServTimeSeq.add(servTimeSeq);
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
}
