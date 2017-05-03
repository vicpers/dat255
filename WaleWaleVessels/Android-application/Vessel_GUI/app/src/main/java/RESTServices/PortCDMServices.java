package RESTServices;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.NoSuchElementException;

import HTTPRequest.WebRequest;
import ServiceEntities.Vessel;

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
import static RESTServices.Constants_API.API_SERVICE_GET_VESSEL;

/**
 * Created by maxedman on 2017-04-27.
 */

public class PortCDMServices {


    // Method for setting the instancevessel based only on a ID.
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
}
