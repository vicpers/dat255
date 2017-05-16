package ServiceEntities;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.NoSuchElementException;

import RESTServices.PortCDMServices;

/**
 * Created by maxedman on 2017-04-21.
 */

public class PortCallMessage {

    private String portCallId;
    private String localPortCallId;
    private String localJobId;
    private Vessel vessel;
    private String messageId;
    private String groupWith;
    private String reportedAt; //TODO Convert to a DateTime format.
    private String reportedBy;
    private String comment;
    private String messageOperation;
    private LocationState locationState;
    private ServiceState serviceState;

    public PortCallMessage(JSONObject pcmJsonObj){
        if (pcmJsonObj != null) {
            try {

                String portCallId           =  pcmJsonObj.getString(Constants_jsonParsing.TAG_PCM_ID2);
                String localPortCallId      =  pcmJsonObj.getString(Constants_jsonParsing.TAG_PCM_LOCAL_PORT_CALL_ID);
                String localJobId           =  pcmJsonObj.getString(Constants_jsonParsing.TAG_PCM_LOCAL_JOB_ID);
                String vesselId             =  pcmJsonObj.getString(Constants_jsonParsing.TAG_PCM_VESSEL_ID);
                String messageId            =  pcmJsonObj.getString(Constants_jsonParsing.TAG_PCM_MESSAGE_ID);
                String groupWith            =  pcmJsonObj.getString(Constants_jsonParsing.TAG_PCM_GROUP_WITH);
                String reportedAt           =  pcmJsonObj.getString(Constants_jsonParsing.TAG_PCM_REPORTED_AT);
                String reportedBy           =  pcmJsonObj.getString(Constants_jsonParsing.TAG_PCM_REPORTED_BY);
                String comment              =  pcmJsonObj.getString(Constants_jsonParsing.TAG_PCM_COMMENT);
                String messageOperation     =  pcmJsonObj.getString(Constants_jsonParsing.TAG_PCM_MESSAGE_OPERATION);

                LocationState locationState;
                try {
                    locationState = new LocationState(pcmJsonObj.getJSONObject(Constants_jsonParsing.TAG_PCM_LOCATION_STATE));
                } catch (JSONException e1){ locationState = null; }

                ServiceState serviceState;
                try {
                    serviceState = new ServiceState(pcmJsonObj.getJSONObject(Constants_jsonParsing.TAG_PCM_SERVICE_STATE));
                } catch (JSONException e2){ serviceState = null; }

                this.portCallId         = portCallId;
                this.localPortCallId    = localPortCallId;
                this.localJobId         = localJobId;
                this.messageId          = messageId;
                this.groupWith          = groupWith;
                this.reportedAt         = reportedAt;
                this.reportedBy         = reportedBy;
                this.comment            = comment;
                this.messageOperation   = messageOperation;
                this.locationState      = locationState;
                this.serviceState       = serviceState;

                try {
                    this.vessel = PortCDMServices.getVessel(vesselId);
                } catch (NoSuchElementException e){
                    this.vessel = new Vessel(vesselId);
                }
            } catch (JSONException e3) {
                Log.e("PortCallMessage Constr.", "Problem getting data - " + e3.toString() +
                        "\n" + pcmJsonObj.toString());
            }
        } else {
            Log.e("PortCallMessage Constr.", "param pcmJsonObj is null");
        }
    }

    public PortCallMessage(String vesselId, String messageId, String reportedBy, LocationState locationState) {
        this.vessel = PortCDMServices.getVessel(vesselId);
        this.messageId = messageId;
        this.reportedBy = reportedBy;
        this.locationState = locationState;
    }

    /** Same functionality as the constructor above despite the fact that this constructor also
     *  keeps track of the portCallId
     *
     * @param portCallId
     * @param vesselId
     * @param messageId
     * @param reportedBy
     * @param locationState
     */
    public PortCallMessage(String portCallId, String vesselId, String messageId, String reportedBy, LocationState locationState) {
        this.portCallId = portCallId;
        this.vessel = PortCDMServices.getVessel(vesselId);
        this.messageId = messageId;
        this.reportedBy = reportedBy;
        this.locationState = locationState;
    }

    /**
     * Lekt och lattjat lite för att få en pcmkonstruktur för att skicka service states.
     * Bra lekt och lattjat.
     */
    public PortCallMessage(String vesselId, String messageId, String reportedBy, ServiceState serviceState) {
        this.vessel = PortCDMServices.getVessel(vesselId);
        this.messageId = messageId;
        this.reportedBy = reportedBy;
        this.serviceState = serviceState;
    }

    /** Same functionality as the constructor above despite the fact that this constructor also
     *  keeps track of the portCallId
     * @param portCallId
     * @param vesselId
     * @param messageId
     * @param reportedBy
     * @param serviceState
     */
    public PortCallMessage(String portCallId, String vesselId, String messageId, String reportedBy, ServiceState serviceState) {
        this.portCallId = portCallId;
        this.vessel = PortCDMServices.getVessel(vesselId);
        this.messageId = messageId;
        this.reportedBy = reportedBy;
        this.serviceState = serviceState;
    }

    public PortCallMessage(String portCallId, String localPortCallId, String localJobId,
                           String vesselId, String messageId, String groupWith, String reportedAt,
                           String reportedBy, String comment, String messageOperation,
                           LocationState locationState, ServiceState serviceState) {
        this.portCallId = portCallId;
        this.localPortCallId = localPortCallId;
        this.localJobId = localJobId;
        this.vessel = PortCDMServices.getVessel(vesselId);
        this.messageId = messageId;
        this.groupWith = groupWith;
        this.reportedAt = reportedAt;
        this.reportedBy = reportedBy;
        this.comment = comment;
        this.messageOperation = messageOperation;
        this.locationState = locationState;
        this.serviceState = serviceState;


    }

    public String getPortCallId() {
        return portCallId;
    }

    public void setPortCallId(String portCallId) {
        this.portCallId = portCallId;
    }

    public String getLocalPortCallId() {
        return localPortCallId;
    }

    public void setLocalPortCallId(String localPortCallId) {
        this.localPortCallId = localPortCallId;
    }

    public String getLocalJobId() {
        return localJobId;
    }

    public void setLocalJobId(String localJobId) {
        this.localJobId = localJobId;
    }

    public Vessel getVessel() {
        return vessel;
    }

    public void setVessel(Vessel vessel) {
        this.vessel = vessel;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getGroupWith() {
        return groupWith;
    }

    public void setGroupWith(String groupWith) {
        this.groupWith = groupWith;
    }

    public String getReportedAt() {
        return reportedAt;
    }

    public void setReportedAt(String reportedAt) {
        this.reportedAt = reportedAt;
    }

    public String getReportedBy() {
        return reportedBy;
    }

    public void setReportedBy(String reportedBy) {
        this.reportedBy = reportedBy;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getMessageOperation() {
        return messageOperation;
    }

    public void setMessageOperation(String messageOperation) {
        this.messageOperation = messageOperation;
    }

    public LocationState getLocationState() {
        return locationState;
    }

    public void setLocationState(LocationState locationState) {
        this.locationState = locationState;
    }

    public ServiceState getServiceState() {
        return serviceState;
    }

    public void setServiceState(ServiceState serviceState) {
        this.serviceState = serviceState;
    }

    @Override
    public String toString() {
        return "PortCallID : " + portCallId + "\n" +
                "Vessel : " + vessel.getId();
    }

    public String toXml() {
        String xmlStr = "";
        if((portCallId != null) && (portCallId != "") && (portCallId != "null"))
            xmlStr += "<ns2:portCallId>"        + portCallId        + "</ns2:portCallId>";
        if(localPortCallId != null)
            xmlStr += "<ns2:localPortCallId>"   + localPortCallId   + "</ns2:localPortCallId>";
        if(localJobId != null)
            xmlStr += "<ns2:localJobId>"        + localJobId        + "</ns2:localJobId>";
        if(vessel != null)
            xmlStr += "<ns2:vesselId>"          + vessel.getId()    + "</ns2:vesselId>";
        if(messageId != null)
            xmlStr += "<ns2:messageId>"         + messageId         + "</ns2:messageId>";
        if(groupWith != null)
            xmlStr += "<ns2:groupWith>"         + groupWith         + "</ns2:groupWith>";
        if(reportedAt != null)
            xmlStr += "<ns2:reportedAt>"        + reportedAt        + "</ns2:reportedAt>";
        if(reportedBy != null)
            xmlStr += "<ns2:reportedBy>"        + reportedBy        + "</ns2:reportedBy>";
        if(comment != null)
            xmlStr += "<ns2:comment>"           + comment           + "</ns2:comment>";
        if(messageOperation != null)
            xmlStr += "<ns2:messageOperation>"  + messageOperation + "</ns2:messageOperation>";
        if (locationState != null)
            xmlStr += "<ns2:locationState>"     + locationState.toXml() + "</ns2:locationState>";
        if (serviceState != null)
            xmlStr += "<ns2:serviceState>"      + serviceState.toXml() + "</ns2:serviceState>";

        return xmlStr;
    }
}
