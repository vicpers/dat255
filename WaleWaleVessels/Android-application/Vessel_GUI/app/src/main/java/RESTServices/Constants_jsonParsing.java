package RESTServices;

/**
 * Created by maxedman on 2017-04-21.
 */

public class Constants_jsonParsing {


    // Constants for handling the tags in a PCM Json-response from port_calls-service.
    public static final String TAG_PCM_ID                               = "id";
    public static final String TAG_PCM_VESSEL                           = "vessel";
    public static final String TAG_PCM_PORT_UN_LOCODE                   = "portUnLocode";
    public static final String TAG_PCM_ARRIVAL_DATE                     = "arrivalDate";
    public static final String TAG_PCM_CREATED_AT                       = "createdAt";
    public static final String TAG_PCM_LAST_UPDATE                      = "lastUpdate";
    public static final String TAG_PCM_START_TIME                       = "startTime";
    public static final String TAG_PCM_END_TIME                         = "endTime";

    // Constants for handling the tags in a PCM Json-response from the Message Broker Queue
    public static final String TAG_PCM_ID2                              = "portCallId";
    public static final String TAG_PCM_LOCAL_PORT_CALL_ID               = "localPortCallId";
    public static final String TAG_PCM_LOCAL_JOB_ID                     = "localJobId";
    public static final String TAG_PCM_VESSEL_ID                        = "vesselId";
    public static final String TAG_PCM_MESSAGE_ID                       = "messageId";
    public static final String TAG_PCM_GROUP_WITH                       = "groupWith";
    public static final String TAG_PCM_REPORTED_AT                      = "reportedAt";
    public static final String TAG_PCM_REPORTED_BY                      = "reportedBy";
    public static final String TAG_PCM_COMMENT                          = "comment";
    public static final String TAG_PCM_MESSAGE_OPERATION                = "messageOperation";
    public static final String TAG_PCM_LOCATION_STATE                   = "locationState";
    public static final String TAG_PCM_SERVICE_STATE                    = "serviceState";


    // Constants for handling the tags in a Vessel Json-response.
    public static final String TAG_VESSEL_IMO                           = "imo";
    public static final String TAG_VESSEL_ID                            = "id";
    public static final String TAG_VESSEL_NAME                          = "name";
    public static final String TAG_VESSEL_CALL_SIGN                     = "callSign";
    public static final String TAG_VESSEL_MMSI                          = "mmsi";
    public static final String TAG_VESSEL_TYPE                          = "type";
    public static final String TAG_VESSEL_STM_VESSEL_ID                 = "stmVesselId";
    public static final String TAG_VESSEL_PHOTOURL                      = "photoURL";

    // Constants for handling the tags in a LocationState Json-response.
    public static final String TAG_LOCATION_STATE_REFERENCE_OBJECT      = "referenceObject";
    public static final String TAG_LOCATION_STATE_TIME                  = "time";
    public static final String TAG_LOCATION_STATE_TIME_TYPE             = "timeType";
    public static final String TAG_LOCATION_STATE_ARRIVAL_LOCATION      = "arrivalLocation";
    public static final String TAG_LOCATION_STATE_DEPARTURE_LOCATION    = "departureLocation";

    // Constants for handling the tags in a ServiceState Json-response.
    public static final String TAG_SERVICE_STATE_SERVICE_OBJECT         = "serviceObject";
    public static final String TAG_SERVICE_STATE_TIME_SEQUENCE          = "timeSequence";
    public static final String TAG_SERVICE_STATE_AT                     = "at";
    public static final String TAG_SERVICE_STATE_BETWEEN_LOCATIONS      = "betweenLocations";
    public static final String TAG_SERVICE_STATE_PERFORMING_ACTOR       = "performingActor";

    // Constants for handling the tags in a Arrival, Departure and Between Json-response.
    public static final String TAG_LOCATION_TO                          = "to";
    public static final String TAG_LOCATION_FROM                        = "from";

    // Constants for handling the tags in a Location Json-response.
    public static final String TAG_LOCATION_TYPE                        = "locationType";
    public static final String TAG_LOCATION_POSITION                    = "position";
    public static final String TAG_LOCATION_NAME                        = "name";

    // Constants for handling the tags in a Arrival, Departure and Between Json-response.
    public static final String TAG_POSITION_LATITUDE                    = "latitude";
    public static final String TAG_POSITION_LONGITUDE                   = "longitude";
}
