package ServiceEntities;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.NoSuchElementException;

import RESTServices.PortCDMServices;

/**
 * Created by maxedman on 2017-04-21.
 * A class representing a PortCall Message with all its traits.
 */

public class PortCallMessage{

    private String portCallId;
    private String localPortCallId;
    private String localJobId;
    private Vessel vessel;
    private String messageId;
    private String groupWith;
    private String reportedAt; //TODO Convert to a DateTime format.
    private String reportedBy;
    private String comment = "Mattias";
    private String messageOperation;
    private LocationState locationState;
    private ServiceState serviceState;

    /**Create a PortCall Message from a JSON
     * @param pcmJsonObj JSON representing a PortCall Message
     */
    public PortCallMessage(JSONObject pcmJsonObj) {
        if (pcmJsonObj != null) {
            try {

                String portCallId = pcmJsonObj.getString(Constants_jsonParsing.TAG_PCM_ID2);
                String localPortCallId = pcmJsonObj.getString(Constants_jsonParsing.TAG_PCM_LOCAL_PORT_CALL_ID);
                String localJobId = pcmJsonObj.getString(Constants_jsonParsing.TAG_PCM_LOCAL_JOB_ID);
                String vesselId = pcmJsonObj.getString(Constants_jsonParsing.TAG_PCM_VESSEL_ID);
                String messageId = pcmJsonObj.getString(Constants_jsonParsing.TAG_PCM_MESSAGE_ID);
                String groupWith = pcmJsonObj.getString(Constants_jsonParsing.TAG_PCM_GROUP_WITH);
                String reportedAt = pcmJsonObj.getString(Constants_jsonParsing.TAG_PCM_REPORTED_AT);
                String reportedBy = pcmJsonObj.getString(Constants_jsonParsing.TAG_PCM_REPORTED_BY);
                String comment = pcmJsonObj.getString(Constants_jsonParsing.TAG_PCM_COMMENT);
                String messageOperation = pcmJsonObj.getString(Constants_jsonParsing.TAG_PCM_MESSAGE_OPERATION);

                LocationState locationState;
                try {
                    locationState = new LocationState(pcmJsonObj.getJSONObject(Constants_jsonParsing.TAG_PCM_LOCATION_STATE));
                } catch (JSONException e1) {
                    locationState = null;
                }

                ServiceState serviceState;
                try {
                    serviceState = new ServiceState(pcmJsonObj.getJSONObject(Constants_jsonParsing.TAG_PCM_SERVICE_STATE));
                } catch (JSONException e2) {
                    serviceState = null;
                }

                this.portCallId = portCallId;
                this.localPortCallId = localPortCallId;
                this.localJobId = localJobId;
                this.messageId = messageId;
                this.groupWith = groupWith;
                this.reportedAt = reportedAt;
                this.reportedBy = reportedBy;
                this.comment = comment;
                this.messageOperation = messageOperation;
                this.locationState = locationState;
                this.serviceState = serviceState;

                try {
                    this.vessel = PortCDMServices.getVessel(vesselId);
                } catch (NoSuchElementException e) {
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

    /**
     * Create a PortCall Message
     * @param vesselId String with VesselID
     * @param messageId String with the MessageID
     * @param reportedBy String saying who is the sender of the PortCall Message
     * @param locationState LocationState associated with the PortCall Message
     */
    public PortCallMessage(String vesselId, String messageId, String reportedBy, LocationState locationState) {
        this.vessel = PortCDMServices.getVessel(vesselId);
        this.messageId = messageId;
        this.reportedBy = reportedBy;
        this.locationState = locationState;
    }

    /**
     * Same functionality as the constructor above despite the fact that this constructor also
     * keeps track of the portCallId
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
     * Create a PortCall Message with a Service State
     * @param vesselId
     * @param messageId
     * @param reportedBy
     * @param serviceState
     */
    public PortCallMessage(String vesselId, String messageId, String reportedBy, ServiceState serviceState) {
        this.vessel = PortCDMServices.getVessel(vesselId);
        this.messageId = messageId;
        this.reportedBy = reportedBy;
        this.serviceState = serviceState;
    }

    /**
     * Same functionality as the constructor above despite the fact that this constructor also
     * keeps track of the portCallId
     *
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

    /**
     * Creates a PortCallMessage with all its variables.
     * @param portCallId
     * @param localPortCallId
     * @param localJobId
     * @param vesselId
     * @param messageId
     * @param groupWith
     * @param reportedAt
     * @param reportedBy
     * @param comment
     * @param messageOperation
     * @param locationState
     * @param serviceState
     */
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

    /**
     * @return a String with the PortCallID
     */
    public String getPortCallId() {
        return portCallId;
    }

    /**
     * @param portCallId A String with the PortCallID.
     */
    public void setPortCallId(String portCallId) {
        this.portCallId = portCallId;
    }

    /**
     * @return A string with the local PortCallID
     */
    public String getLocalPortCallId() {
        return localPortCallId;
    }

    /**
     * @param localPortCallId String with local PortCallID
     */
    public void setLocalPortCallId(String localPortCallId) {
        this.localPortCallId = localPortCallId;
    }

    /**
     * @return String with Local Job ID
     */
    public String getLocalJobId() {
        return localJobId;
    }

    /**
     * @param localJobId String with Local Job ID
     */
    public void setLocalJobId(String localJobId) {
        this.localJobId = localJobId;
    }

    /**
     * @return Vessel tied to the PortCallMessage
     */
    public Vessel getVessel() {
        return vessel;
    }

    /**
     * @param vessel Vessel tied to the PortCallMessage
     */
    public void setVessel(Vessel vessel) {
        this.vessel = vessel;
    }

    /**
     * @return String with the ID of the PortCallMessage
     */
    public String getMessageId() {
        return messageId;
    }

    /**
     * @param messageId String with the ID of the PortCallMessage
     */
    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    /**
     *  //TODO kommentera detta
     */
    public String getGroupWith() {
        return groupWith;
    }

    /**
     * @param groupWith //TODO kommentera detta
     */
    public void setGroupWith(String groupWith) {
        this.groupWith = groupWith;
    }

    /**
     * @return The timestamp of the PortCallMessage
     */
    public String getReportedAt() {
        return reportedAt;
    }

    /**
     * @param reportedAt The timestamp of the PortCallMessage
     */
    public void setReportedAt(String reportedAt) {
        this.reportedAt = reportedAt;
    }

    /**
     * @return A String containing the sender of the PortCallMessage
     */
    public String getReportedBy() {
        return reportedBy;
    }

    /**
     * @param reportedBy A String containing the sender of the PortCallMessage
     */
    public void setReportedBy(String reportedBy) {
        this.reportedBy = reportedBy;
    }

    /**
     * @return An optional String with comment if necessary.
     */
    public String getComment() {
        return comment;
    }

    /**
     * @param comment An optional String with comment if necessary.
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * @return A String with the operation of the PortCall Message
     */
    public String getMessageOperation() {
        return messageOperation;
    }

    /**
     * @param messageOperation A String with the operation of the PortCall Message
     */
    public void setMessageOperation(String messageOperation) {
        this.messageOperation = messageOperation;
    }

    /**
     * @return LocationState if the PortCallMessage contains a LocationState, otherwise null.
     */
    public LocationState getLocationState() {
        return locationState;
    }

    /**
     * @param locationState The LocationState if the PortCallMessage should contain one.
     */
    public void setLocationState(LocationState locationState) {
        this.locationState = locationState;
    }

    /**
     * @return ServiceState if the PortCallMessage contains one, otherwise null.
     */
    public ServiceState getServiceState() {
        return serviceState;
    }

    /**
     * @param serviceState The ServiceState if the PortCallMessage should contain one.
     */
    public void setServiceState(ServiceState serviceState) {
        this.serviceState = serviceState;
    }

    @Override
    public String toString() {
        String returnString = "";
        if (serviceState != null)
            returnString = serviceState.toString();
        else if (locationState != null)
            returnString = locationState.toString();
        else
            returnString = "No state found!";

        if ((comment != null) && (!comment.equals("null")) && (!comment.equals("")))
            returnString += "\nComment: " + comment;
        return returnString;
    }

    /**
     * @return A String with an XML representation of the object.
     */
    public String toXml() {
        String xmlStr = "";
        if ((portCallId != null) && (!portCallId.equals("")) && (!portCallId.equals("null")))
            xmlStr += "<ns2:portCallId>" + portCallId + "</ns2:portCallId>";
        if (localPortCallId != null)
            xmlStr += "<ns2:localPortCallId>" + localPortCallId + "</ns2:localPortCallId>";
        if (localJobId != null)
            xmlStr += "<ns2:localJobId>" + localJobId + "</ns2:localJobId>";
        if (vessel != null)
            xmlStr += "<ns2:vesselId>" + vessel.getId() + "</ns2:vesselId>";
        if (messageId != null)
            xmlStr += "<ns2:messageId>" + messageId + "</ns2:messageId>";
        if (groupWith != null)
            xmlStr += "<ns2:groupWith>" + groupWith + "</ns2:groupWith>";
        if (reportedAt != null)
            xmlStr += "<ns2:reportedAt>" + reportedAt + "</ns2:reportedAt>";
        if (reportedBy != null)
            xmlStr += "<ns2:reportedBy>" + reportedBy + "</ns2:reportedBy>";
        if (comment != null)
            xmlStr += "<ns2:comment>" + comment + "</ns2:comment>";
        if (messageOperation != null)
            xmlStr += "<ns2:messageOperation>" + messageOperation + "</ns2:messageOperation>";
        if (locationState != null)
            xmlStr += "<ns2:locationState>" + locationState.toXml() + "</ns2:locationState>";
        if (serviceState != null)
            xmlStr += "<ns2:serviceState>" + serviceState.toXml() + "</ns2:serviceState>";

        return xmlStr;
    }

    /**
     * @return String containing OperationType of the PortCallMessage
     */
    public String getOperationType() {
        ServiceState serviceState = getServiceState();
        if (!(serviceState == null)) {
            return serviceState.getOperationType();
        } else {
            return getLocationState().getOperationType();
        }
    }

    /**
     * @return String containing TimeSequence
     */
    public String getTimeSequence() {
        ServiceState serviceState = getServiceState();
        if (!(serviceState == null)) {
            return serviceState.getTimeSequence();
        } else {
            return null;
        }
    }

    /**
     * @return String containing TimeType
     */
    public String getTimeType(){
        if(isServiceState()){
            return getServiceState().getTimeType();
        }
        else return getLocationState().getTimeType().getText();
    }

    /**
     * @return boolean saying if the PortCallMessage contains a ServiceState or a LocationState
     */
    public boolean isServiceState(){
        ServiceState serviceState = getServiceState();
        if (!(serviceState == null)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * @return String containing the Performing Actor of the PortCall Message
     */
    public String getPerformingActor(){
        if(isServiceState()){
            return serviceState.getPerformingActor();
        }
        else return getLocationState().getReferenceObject().getText();
    }

    /**
     * @return String with the timeStamp of the PortCall Message
     */
    public String getTime(){
        if(isServiceState()){
            return getServiceState().getTime();
        }
        else return getLocationState().getTime();
    }

    /**
     * @return String with the LocationMRN of the PortCall Message
     */
    public String getLocationMRN(){
        if(isServiceState()){
            Log.e("1", "1");
            return getServiceState().getLocationMRN();
        }
        else return getLocationState().getLocationMRN();
    }
}
