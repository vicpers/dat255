package RESTServices;

/**
 * Created by maxedman on 2017-04-22.
 */

public class Constants_API {

    // Constants for making webRequests for the server. Developer instance
    public static final String API_DEV_BASE_URL                     = "http://dev.portcdm.eu";
    public static final String API_DEV_PORT1                        = "8080";
    public static final String API_DEV_PORT2                        = "4567";
    public static final String API_DEV_PORT3                        = "1337";
    public static final String API_DEV_USERNAME                     = "viktoria";
    public static final String API_DEV_PASSWORD                     = "vik123";
    public static final String API_DEV_KEY1                         = "eeee";
    public static final String API_DEV_KEY2                         = "dhc";

    // Constants for webRequests headernames
    public static final String API_HEADER_ACCEPT                    = "Accept";
    public static final String API_HEADER_USER_ID                   = "X-PortCDM-UserId";
    public static final String API_HEADER_PASSWORD                  = "X-PortCDM-Password";
    public static final String API_HEADER_API_KEY                   = "X-PortCDM-APIKey";
    public static final String API_HEADER_ACCEPT_JSON               = "application/json";
    public static final String API_HEADER_ACCEPT_XML                = "application/XML";

    // Constants for backend services directories
    public static final String API_SERVICE_PORT_CALLS               = "/dmp/port_calls";
    public static final String API_SERVICE_POLL_QUEUE               = "/mb/mqs/";


}
