package RESTServices;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import ServiceEntities.ArrivalLocation;
import ServiceEntities.Between;
import ServiceEntities.DepartureLocation;
import ServiceEntities.Location;
import ServiceEntities.LocationState;
import ServiceEntities.PCM;
import ServiceEntities.PortCallMessage;
import ServiceEntities.Position;
import ServiceEntities.ServiceState;
import ServiceEntities.Vessel;


/**
 * Created by maxedman on 2017-04-21.
 *
 * Purpose of converting different Java class instances to Json-string and vice versa
 */

public class jsonJavaConverter {


    /**
     * For parsing the PcmJson-string to java PCM-class
     *
     * @param pcmJson  - Json-PCMObject to parse
     * @return Reference to a PCM instance.
     */
//TODO Eventuellt implementera att skicka exceptions för olika felparametrar.
    public static PCM jsonToPcm(JSONObject pcmJson){

//        Check to confirm that the string contains something
        if (pcmJson != null) {
            try {

// Accessing the PCM
                String id = pcmJson.getString(Constants_jsonParsing.TAG_PCM_ID);
                String portUnLocode = pcmJson.getString(Constants_jsonParsing.TAG_PCM_PORT_UN_LOCODE);
                String arrDate = pcmJson.getString(Constants_jsonParsing.TAG_PCM_ARRIVAL_DATE);
                String crAt = pcmJson.getString(Constants_jsonParsing.TAG_PCM_CREATED_AT);
                String lastUpdate = pcmJson.getString(Constants_jsonParsing.TAG_PCM_LAST_UPDATE);
                String startTime = pcmJson.getString(Constants_jsonParsing.TAG_PCM_START_TIME);
                String endTime = pcmJson.getString(Constants_jsonParsing.TAG_PCM_END_TIME);
                Vessel vessel = jsonToVessel(pcmJson.getJSONObject(Constants_jsonParsing.TAG_PCM_VESSEL));

                return new PCM(id, vessel, portUnLocode, arrDate, crAt, lastUpdate, startTime, endTime);

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
     * For parsing the PortCallMessageJson-string to java PortCallMessage-class
     * Specific for Message Broker Queue
     *
     * @param jsonPortCallMessage  - JsonObject to parse
     * @return Reference to a PortCallMessage instance.
     */
//TODO Eventuellt implementera att skicka exceptions för olika felparametrar.
    public static PortCallMessage jsonToPortCallMessage(JSONObject jsonPortCallMessage){

//        Check to confirm that the string contains something
        if (jsonPortCallMessage != null) {
            try {

                String portCallId           =  jsonPortCallMessage.getString(Constants_jsonParsing.TAG_PCM_ID2);
                String localPortCallId      =  jsonPortCallMessage.getString(Constants_jsonParsing.TAG_PCM_LOCAL_PORT_CALL_ID);
                String localJobId           =  jsonPortCallMessage.getString(Constants_jsonParsing.TAG_PCM_LOCAL_JOB_ID);
                String vesselId             =  jsonPortCallMessage.getString(Constants_jsonParsing.TAG_PCM_VESSEL_ID);
                String messageId            =  jsonPortCallMessage.getString(Constants_jsonParsing.TAG_PCM_MESSAGE_ID);
                String groupWith            =  jsonPortCallMessage.getString(Constants_jsonParsing.TAG_PCM_GROUP_WITH);
                String reportedAt           =  jsonPortCallMessage.getString(Constants_jsonParsing.TAG_PCM_REPORTED_AT);
                String reportedBy           =  jsonPortCallMessage.getString(Constants_jsonParsing.TAG_PCM_REPORTED_BY);
                String comment              =  jsonPortCallMessage.getString(Constants_jsonParsing.TAG_PCM_COMMENT);
                String messageOperation     =  jsonPortCallMessage.getString(Constants_jsonParsing.TAG_PCM_MESSAGE_OPERATION);

                LocationState locationState;
                try {
                    locationState = jsonToLocState(jsonPortCallMessage.getJSONObject(Constants_jsonParsing.TAG_PCM_LOCATION_STATE));
                } catch (JSONException e){ locationState = null; }


                ServiceState serviceState;
                try {
                    serviceState = jsonToServiceState(jsonPortCallMessage.getJSONObject(Constants_jsonParsing.TAG_PCM_SERVICE_STATE));
                } catch (JSONException e){ serviceState = null; }

// Returns the new PCM
                /*return portCallId + " - " + localPortCallId + " - " + vesselId + " - " + messageOperation;*/
                return new PortCallMessage(portCallId, localPortCallId, localJobId, vesselId,
                                           messageId, groupWith, reportedAt, reportedBy, comment,
                                           messageOperation, locationState, serviceState);
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
//                return e.getMessage();
            }
        } else {
            Log.e("ServiceHandler", "No data received from HTTP request");
            return null;
//            return "No data received from HTTP request";
        }
    }


    /**
     * For parsing the VesselJson-string to java Vessel-class
     *
     * @param jsonVessel  - JsonObject to parse
     * @return Reference to a Vessel instance.
     */
//TODO Eventuellt implementera att skicka exceptions för olika felparametrar.
    private static Vessel jsonToVessel(JSONObject jsonVessel){

        if (jsonVessel != null) {
            try {

                long imo            = jsonVessel.getLong(Constants_jsonParsing.TAG_VESSEL_IMO);
                String id           = jsonVessel.getString(Constants_jsonParsing.TAG_VESSEL_ID);
                String name         = jsonVessel.getString(Constants_jsonParsing.TAG_VESSEL_NAME);
                String callSign     = jsonVessel.getString(Constants_jsonParsing.TAG_VESSEL_CALL_SIGN);
                long mmsi           = jsonVessel.getLong(Constants_jsonParsing.TAG_VESSEL_MMSI);
                String type         = jsonVessel.getString(Constants_jsonParsing.TAG_VESSEL_TYPE);
                long stmVesselId    = jsonVessel.getLong(Constants_jsonParsing.TAG_VESSEL_STM_VESSEL_ID);
                String photoURL     = jsonVessel.getString(Constants_jsonParsing.TAG_VESSEL_PHOTOURL);

                return new Vessel(imo, id, name, callSign, mmsi, type, stmVesselId, photoURL);

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
     * For parsing the PortCallMessageJson-string to java PortCallMessage-class
     * Specific for Message Broker Queue
     *
     * @param jsonLocState  - JsonObject to parse
     * @return Reference to a PortCallMessage instance.
     */
//TODO Eventuellt implementera att skicka exceptions för olika felparametrar.
    private static LocationState jsonToLocState(JSONObject jsonLocState){

//        Check to confirm that the string contains something
        if (jsonLocState != null) {
            try {

                String referenceObject              = jsonLocState.getString(Constants_jsonParsing.TAG_LOCATION_STATE_REFERENCE_OBJECT);
                String time                         = jsonLocState.getString(Constants_jsonParsing.TAG_LOCATION_STATE_TIME);
                String timeType                     = jsonLocState.getString(Constants_jsonParsing.TAG_LOCATION_STATE_TIME_TYPE);

                ArrivalLocation arrivalLocation;
                try {
                    arrivalLocation = jsonToArrivalLocation(jsonLocState.getJSONObject(Constants_jsonParsing.TAG_LOCATION_STATE_ARRIVAL_LOCATION));
                } catch (JSONException e){ arrivalLocation = null; }

                DepartureLocation departureLocation;
                try {
                departureLocation = jsonToDepartureLocation(jsonLocState.getJSONObject(Constants_jsonParsing.TAG_LOCATION_STATE_DEPARTURE_LOCATION));
                } catch (JSONException e){ departureLocation = null; }

// Initiate new instance of PortCallMessage
                return new LocationState(referenceObject, time, timeType, arrivalLocation, departureLocation);

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
     * For parsing the PortCallMessageJson-string to java PortCallMessage-class
     * Specific for Message Broker Queue
     *
     * @param jsonServiceState  - JsonObject to parse
     * @return Reference to a PortCallMessage instance.
     */
//TODO Eventuellt implementera att skicka exceptions för olika felparametrar.
    private static ServiceState jsonToServiceState(JSONObject jsonServiceState){

//        Check to confirm that the string contains something
        if (jsonServiceState != null) {
            try {

                String serviceObject        =    jsonServiceState.getString(Constants_jsonParsing.TAG_SERVICE_STATE_SERVICE_OBJECT);
                String serviceTimeSequence  =    jsonServiceState.getString(Constants_jsonParsing.TAG_SERVICE_STATE_TIME_SEQUENCE);
                String at                   =    jsonServiceState.getString(Constants_jsonParsing.TAG_SERVICE_STATE_AT);
                String performingActor      =    jsonServiceState.getString(Constants_jsonParsing.TAG_SERVICE_STATE_PERFORMING_ACTOR);

                Between between;
                try {
                    between = jsonToBetween(jsonServiceState.getJSONObject(Constants_jsonParsing.TAG_SERVICE_STATE_BETWEEN_LOCATIONS));
                } catch (JSONException e){ between = null; }

// Initiate new instance of PortCallMessage
                return new ServiceState(serviceObject, serviceTimeSequence, at, between, performingActor);

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
     * For parsing the PortCallMessageJson-string to java PortCallMessage-class
     * Specific for Message Broker Queue
     *
     * @param jsonArrLoc  - JsonObject to parse
     * @return Reference to a PortCallMessage instance.
     */
//TODO Eventuellt implementera att skicka exceptions för olika felparametrar.
    private static ArrivalLocation jsonToArrivalLocation(JSONObject jsonArrLoc){

//        Check to confirm that the string contains something
        if (jsonArrLoc != null) {

            Location from;
            try {
            from  =  jsonToLocation(jsonArrLoc.getJSONObject(Constants_jsonParsing.TAG_LOCATION_FROM));
            } catch (JSONException e){ from = null; }

            Location to;
            try {
                to    =  jsonToLocation(jsonArrLoc.getJSONObject(Constants_jsonParsing.TAG_LOCATION_TO));
            } catch (JSONException e){ to = null; }

// Initiate new instance of ArrivalLocation
            return new ArrivalLocation(from, to);

        } else {
            Log.e("ServiceHandler", "No data received from HTTP request");
            return null;
        }
    }


    /**
     * For parsing the PortCallMessageJson-string to java PortCallMessage-class
     * Specific for Message Broker Queue
     *
     * @param jsonDepLoc  - JsonObject to parse
     * @return Reference to a PortCallMessage instance.
     */
//TODO Eventuellt implementera att skicka exceptions för olika felparametrar.
    private static DepartureLocation jsonToDepartureLocation(JSONObject jsonDepLoc){

//        Check to confirm that the string contains something
        if (jsonDepLoc != null) {

            Location from;
            try {
                from  =  jsonToLocation(jsonDepLoc.getJSONObject(Constants_jsonParsing.TAG_LOCATION_FROM));
            } catch (JSONException e){ from = null; }

            Location to;
            try {
                to    =  jsonToLocation(jsonDepLoc.getJSONObject(Constants_jsonParsing.TAG_LOCATION_TO));
            } catch (JSONException e){ to = null; }

            // Initiate new instance of DepartureLocation
            return new DepartureLocation(from, to);

        } else {
            Log.e("ServiceHandler", "No data received from HTTP request");
            return null;
        }
    }


    /**
     * For parsing the PortCallMessageJson-string to java PortCallMessage-class
     * Specific for Message Broker Queue
     *
     * @param jsonBetween  - JsonObject to parse
     * @return Reference to a PortCallMessage instance.
     */
//TODO Eventuellt implementera att skicka exceptions för olika felparametrar.
    private static Between jsonToBetween(JSONObject jsonBetween){

//        Check to confirm that the string contains something
        if (jsonBetween != null) {

            Location from;
            try {
                from  =  jsonToLocation(jsonBetween.getJSONObject(Constants_jsonParsing.TAG_LOCATION_FROM));
            } catch (JSONException e){ from = null; }

            Location to;
            try {
                to    =  jsonToLocation(jsonBetween.getJSONObject(Constants_jsonParsing.TAG_LOCATION_TO));
            } catch (JSONException e){ to = null; }

// Returns the new
            return new Between(from, to);

        } else {
            Log.e("ServiceHandler", "No data received from HTTP request");
            return null;
        }
    }


    /**
     * For parsing the PortCallMessageJson-string to java PortCallMessage-class
     * Specific for Message Broker Queue
     *
     * @param jsonLocObj  - JsonObject to parse
     * @return Reference to a PortCallMessage instance.
     */
//TODO Eventuellt implementera att skicka exceptions för olika felparametrar.
    private static Location jsonToLocation(JSONObject jsonLocObj){
//        Check to confirm that the string contains something
        if (jsonLocObj != null) {
            try {

                String locationType = jsonLocObj.getString(Constants_jsonParsing.TAG_LOCATION_TYPE);
                String name         = jsonLocObj.getString(Constants_jsonParsing.TAG_LOCATION_NAME);

                Position position;
                try {
                    position = jsonToPosition(jsonLocObj.getJSONObject(Constants_jsonParsing.TAG_LOCATION_POSITION));
                } catch (JSONException e) { position = null; }

                return new Location(locationType, position, name);

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
     * For parsing the PortCallMessageJson-string to java PortCallMessage-class
     * Specific for Message Broker Queue
     *
     * @param jsonPosObj  - JsonObject to parse
     * @return Reference to a PortCallMessage instance.
     */
//TODO Eventuellt implementera att skicka exceptions för olika felparametrar.
    private static Position jsonToPosition(JSONObject jsonPosObj){
//        Check to confirm that the string contains something
        if (jsonPosObj != null) {
            try {

                long latitude   =  jsonPosObj.getLong(Constants_jsonParsing.TAG_POSITION_LATITUDE);
                long longitude  =  jsonPosObj.getLong(Constants_jsonParsing.TAG_POSITION_LONGITUDE);

// Initiate new instance of PortCallMessage
                return new Position(latitude, longitude);

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

}