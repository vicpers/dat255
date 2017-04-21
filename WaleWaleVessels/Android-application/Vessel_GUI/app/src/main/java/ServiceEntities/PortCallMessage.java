package ServiceEntities;

/**
 * Created by maxedman on 2017-04-21.
 */

public class PortCallMessage {

    private String portCallId               = "urn:mrn:stm:portcdm:port_call:SEGOT:9319431a-c87b-41df-9392-07c381dd80ee";
    private String localPortCallId          = null;
    private String localJobId               = null;
    private String vesselId                 = "urn:mrn:stm:vessel:IMO:9262089";
    private String messageId                = "urn:mrn:stm:portcdm:message:bbaa74f5-b244-43c8-94c3-be390de4e727";
    private String groupWith                = null;
    private String reportedAt               = "2017-04-21T19:19:37Z"; //TODO Convert to a DateTime format.
    private String reportedBy               = "urn:mrn:legacy:user:SSPA";
    private String comment                  = null;
    private String messageOperation         = null;
    private LocationState locationState;
    private ServiceState serviceState       = null;

}
