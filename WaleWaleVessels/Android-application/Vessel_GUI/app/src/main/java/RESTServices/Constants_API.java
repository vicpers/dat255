package RESTServices;

/**
 * Created by maxedman on 2017-04-22.
 */
//TODO kommentera
public class Constants_API {

    // Constants for making webRequests for the server. Developer instance
    public static final String API_DEV_BASE_URL                     = "http://dev.portcdm.eu";
//    public static final String API_DEV_BASE_URL                     = "http://sandbox-5.portcdm.eu";
    public static final String API_DEV_PORT1                        = "8080";
    public static final String API_DEV_PORT2                        = "4567";
    public static final String API_DEV_PORT3                        = "1337";
    public static final String API_DEV_USERNAME                     = "viktoria";
    public static final String API_DEV_PASSWORD                     = "vik123";
    public static final String API_DEV_KEY1                         = "eeee";
    public static final String API_DEV_KEY2                         = "dhc";
    public static final String API_ACTUAL_PORT                      = "SEGOT"; // Port of Gothenburg

    // Constants for webRequests headernames
    public static final String API_HEADER_ACCEPT                    = "Accept";
    public static final String API_HEADER_CONTENT_TYPE              = "Content-Type";
    public static final String API_HEADER_USER_ID                   = "X-PortCDM-UserId";
    public static final String API_HEADER_PASSWORD                  = "X-PortCDM-Password";
    public static final String API_HEADER_API_KEY                   = "X-PortCDM-APIKey";
    public static final String API_HEADER_ACCEPT_JSON               = "application/json";
    public static final String API_HEADER_ACCEPT_XML                = "application/xml";

    // Constants for backend services directories
    public static final String API_SERVICE_PORT_CALLS               = "/dmp/port_calls";
    public static final String API_SERVICE_POLL_QUEUE               = "/mb/mqs/";
    public static final String API_SERVICE_AMSS_STATE_UPDATE        = "/amss/state_update";
    public static final String API_SERVICE_MSS_STATE_UPDATE         = "/mb/mss";
    public static final String API_SERVICE_CREATE_QUEUE             = "/mb/mqs";
    public static final String API_SERVICE_GET_VESSEL               = "/vr/vessel/";
    public static final String API_SERVICE_GET_STATE_DEFINITIONS    = "/dmp/state_definition_catalogue";
    public static final String API_SERVICE_GET_PORT                 = "/dmp/ports/";
    public static final String API_SERVICE_FIND_LOCATIONS           = "/location-registry/locations";

    // Constants for building PortCallMessage XML-structure.
    public static final String API_XML_HEADER                       = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
    // Old version
    // public static final String API_XML_PORT_CALL_MESSAGE_HEADER     = "<ns2:portCallMessage xmlns:ns2=\"urn:x-mrn:stm:schema:port-call-message:0.0.16\">";
    public static final String API_XML_PORT_CALL_MESSAGE_HEADER     = "<ns2:portCallMessage xmlns:ns2=\"urn:mrn:stm:schema:port-call-message:0.6\">";
    public static final String API_XML_PORT_CALL_MESSAGE_END        = "</ns2:portCallMessage>";



}
