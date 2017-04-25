package RESTServices;

import java.util.HashMap;

import HTTPRequest.WebRequest;
import ServiceEntities.PortCallMessage;

import static RESTServices.Constants_API.*;

/**
 * Created by maxedman on 2017-04-24.
 */

public class AMSS implements Runnable{

    private PortCallMessage pcmObj;
    String url;
    HashMap<String, String> headers;

    public AMSS(PortCallMessage pcmObj){
        this.pcmObj = pcmObj;
    }

    public void submitStateUpdate(){
        this.url = API_DEV_BASE_URL + ":" + API_DEV_PORT1 + API_SERVICE_AMSS_STATE_UPDATE;

        headers = new HashMap<String, String>();

        headers.put(API_HEADER_CONTENT_TYPE, API_HEADER_ACCEPT_XML);
        headers.put(API_HEADER_USER_ID, API_DEV_USERNAME);
        headers.put(API_HEADER_PASSWORD, API_DEV_PASSWORD);
        headers.put(API_HEADER_API_KEY, API_DEV_KEY1);

        // Runs the webrequest on a different Thread.
        this.run();
    }


    @Override
    public void run() {
        String xmlPost = API_XML_HEADER + API_XML_PORT_CALL_MESSAGE_HEADER;
        xmlPost += pcmObj.toXml();
        xmlPost += API_XML_PORT_CALL_MESSAGE_END;

        String wrResponse = WebRequest.makeWebServicePost(url, headers, null, xmlPost);
    }
}
