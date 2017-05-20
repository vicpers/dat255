package RESTServices;

import android.util.Log;

import java.util.HashMap;

import HTTPRequest.WebRequest;
import ServiceEntities.PortCallMessage;

import static RESTServices.Constants_API.API_DEV_BASE_URL;
import static RESTServices.Constants_API.API_DEV_KEY1;
import static RESTServices.Constants_API.API_DEV_PASSWORD;
import static RESTServices.Constants_API.API_DEV_PORT1;
import static RESTServices.Constants_API.API_DEV_USERNAME;
import static RESTServices.Constants_API.API_HEADER_ACCEPT_XML;
import static RESTServices.Constants_API.API_HEADER_API_KEY;
import static RESTServices.Constants_API.API_HEADER_CONTENT_TYPE;
import static RESTServices.Constants_API.API_HEADER_PASSWORD;
import static RESTServices.Constants_API.API_HEADER_USER_ID;
import static RESTServices.Constants_API.API_SERVICE_AMSS_STATE_UPDATE;
import static RESTServices.Constants_API.API_SERVICE_MSS_STATE_UPDATE;
import static RESTServices.Constants_API.API_XML_HEADER;
import static RESTServices.Constants_API.API_XML_PORT_CALL_MESSAGE_END;
import static RESTServices.Constants_API.API_XML_PORT_CALL_MESSAGE_HEADER;

/**
 * Created by maxedman on 2017-04-24.
 */
//TODO kommentera
public class AMSS /*implements Runnable*/{

    private PortCallMessage pcmObj;
    String url;
    HashMap<String, String> headers;

    public AMSS(PortCallMessage pcmObj){
        this.pcmObj = pcmObj;
    }

    public String submitStateUpdate(){

        // If the portCallMessage includes a PortCallID then send the message to MSS. If the
        // portCallMessage does not include a PortCallID then it is sent to the AMSS for pairing to
        // other portCallMessages.
        if(pcmObj.getPortCallId() == null) {
            this.url = API_DEV_BASE_URL + ":" + API_DEV_PORT1 + API_SERVICE_AMSS_STATE_UPDATE;
            Log.e("AMSS", "Sent to AMSS - No portCallID");
        }else{
            this.url = API_DEV_BASE_URL + ":" + API_DEV_PORT1 + API_SERVICE_MSS_STATE_UPDATE;
            Log.e("MSS", "Sent to MSS - Included portCallID: " + pcmObj.getPortCallId());
        }

        headers = new HashMap<String, String>();

        headers.put(API_HEADER_CONTENT_TYPE, API_HEADER_ACCEPT_XML);
        headers.put(API_HEADER_USER_ID, API_DEV_USERNAME);
        headers.put(API_HEADER_PASSWORD, API_DEV_PASSWORD);
        headers.put(API_HEADER_API_KEY, API_DEV_KEY1);

        String xmlPost = API_XML_HEADER + API_XML_PORT_CALL_MESSAGE_HEADER;
        xmlPost += pcmObj.toXml();
        xmlPost += API_XML_PORT_CALL_MESSAGE_END;

        return WebRequest.makeWebServicePost(url, headers, null, xmlPost);
    }
}
