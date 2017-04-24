package ServiceEntities;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by maxedman on 2017-04-21.
 */

public class PortCallMessage {

    private String portCallId;
    private String localPortCallId;
    private String localJobId;
    private String vesselId; //TODO Eventuellt ändra detta till ett VesselObjekt. Kan däremot skapa problem vid konvertering till json.
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
                this.vesselId           = vesselId;
                this.messageId          = messageId;
                this.groupWith          = groupWith;
                this.reportedAt         = reportedAt;
                this.reportedBy         = reportedBy;
                this.comment            = comment;
                this.messageOperation   = messageOperation;
                this.locationState      = locationState;
                this.serviceState       = serviceState;
            } catch (JSONException e3) {
                Log.e("PortCallMessage Constr.", "Problem getting data - " + e3.toString());
            }
        } else {
            Log.e("PortCallMessage Constr.", "param pcmJsonObj is null");
        }
    }

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
