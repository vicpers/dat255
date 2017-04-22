package ServiceEntities;

/**
 * Created by maxedman on 2017-04-21.
 */

public class PortCallMessage {

    private String portCallId;
    private String localPortCallId;
    private String localJobId;
    private String vesselId;
    private String messageId;
    private String groupWith;
    private String reportedAt; //TODO Convert to a DateTime format.
    private String reportedBy;
    private String comment;
    private String messageOperation;
    private LocationState locationState;
    private ServiceState serviceState;

    public PortCallMessage(String portCallId, String localPortCallId, String localJobId,
                           String vesselId, String messageId, String groupWith, String reportedAt,
                           String reportedBy, String comment, String messageOperation,
                           LocationState locationState, ServiceState serviceState) {
        this.portCallId = portCallId;
        this.localPortCallId = localPortCallId;
        this.localJobId = localJobId;
        this.vesselId = vesselId;
        this.messageId = messageId;
        this.groupWith = groupWith;
        this.reportedAt = reportedAt;
        this.reportedBy = reportedBy;
        this.comment = comment;
        this.messageOperation = messageOperation;
        this.locationState = locationState;
        this.serviceState = serviceState;


    }

    @Override
    public String toString() {
        return "PortCallMessage{" +
                "portCallId='" + portCallId + "'}";
    }
}
