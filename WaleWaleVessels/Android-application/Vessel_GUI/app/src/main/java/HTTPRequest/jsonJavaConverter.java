package HTTPRequest;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static HTTPRequest.webRequestConstants.*;


/**
 * Created by maxedman on 2017-04-21.
 *
 * Purpose of converting different Java class instances to Json-string and vice versa
 */

public class jsonJavaConverter {

    /**
     * For parsing the PcmJson-string to java PCM-class
     *
     * @param jsonStr  - Json-string to parse
     * @return Reference to a PCM instance.
     */
//TODO Eventuellt implementera att skicka exceptions för olika felparametrar.
    public static PCM jsonToPcm(String jsonStr){

//        Check to confirm that the string contains something
        if (jsonStr != null) {
            try {
//                    PCM instance to be returned.
                PCM pcm;
// Getting JSON Arrays that contains the.
                JSONArray jsonArray = new JSONArray(jsonStr);
                JSONArray portCallMessages = jsonArray.getJSONArray(0);

// Accesing the PCM

                JSONObject pcmJson = portCallMessages.getJSONObject(0);

                String id = pcmJson.getString(TAG_PCM_ID);
                String portUnLocode = pcmJson.getString(TAG_PCM_PORT_UN_LOCODE);
                String arrDate = pcmJson.getString(TAG_PCM_ARRIVAL_DATE);
                String crAt = pcmJson.getString(TAG_PCM_CREATED_AT);
                String lastUpdate = pcmJson.getString(TAG_PCM_LAST_UPDATE);
                String startTime = pcmJson.getString(TAG_PCM_START_TIME);
                String endTime = pcmJson.getString(TAG_PCM_END_TIME);

// Vessel node is JSON Object
                Vessel vessel = jsonToVessel(pcmJson.getJSONObject(TAG_PCM_VESSEL));

// Temporary hashmap for single PCM
                pcm = new PCM(id, vessel, portUnLocode, arrDate, crAt, lastUpdate, startTime, endTime);

// Returns the new PCM
                return pcm;

                } catch (JSONException e) {
                    e.printStackTrace();
                    return null;
                }
            } else {
                Log.e("ServiceHandler", "No data received from HTTP request");
                return null;
            }
        }



    /**
     * For parsing the VesselJson-string to java Vessel-class
     *
     * @param jsonVessel  - JsonObject to parse
     * @return Reference to a Vessel instance.
     */
//TODO Eventuellt implementera att skicka exceptions för olika felparametrar.
    public static Vessel jsonToVessel(JSONObject jsonVessel){

//        Check to confirm that the string contains something
        if (jsonVessel != null) {
            try {
//                    PCM instance to be returned.
                Vessel vessel;
/*// Getting JSON Arrays that contains the.
                JSONArray jsonArray = new JSONArray(jsonStr);
                JSONArray portCallMessages = jsonArray.getJSONArray(0);

// Accesing the PCM

                JSONObject pcmJson = portCallMessages.getJSONObject(0);*/

                long imo = jsonVessel.getLong(TAG_VESSEL_IMO);
                String id = jsonVessel.getString(TAG_VESSEL_ID);
                String name = jsonVessel.getString(TAG_VESSEL_NAME);
                String callSign = jsonVessel.getString(TAG_VESSEL_CALL_SIGN);
                long mmsi = jsonVessel.getLong(TAG_VESSEL_MMSI);
                String type = jsonVessel.getString(TAG_VESSEL_TYPE);
                long stmVesselId = jsonVessel.getLong(TAG_VESSEL_STM_VESSEL_ID);
                String photoURL = jsonVessel.getString(TAG_VESSEL_PHOTOURL);


// Temporary hashmap for single PCM
                vessel = new Vessel(imo, id, name, callSign, mmsi, type, stmVesselId, photoURL);

// Returns the new PCM
                return vessel;

            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
        } else {
            Log.e("ServiceHandler", "No data received from HTTP request");
            return null;
        }
    }

    //TODO Implementera funktion för att konvertera från PCM till json-sträng. När vi vet hur json-strängarna ska se ut för PCM.
    //TODO Implementera funktion för att konvertera från Vessel till json-sträng. När vi vet hur json-strängarna ska se ut för Vessel.

}
