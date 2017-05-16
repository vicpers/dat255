package ServiceEntities;


import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by maxedman on 2017-04-20.
 */
//TODO Göra om klassen så att den passar så som ett PortCall nu faktiskt ser ut.
public class PortCall{

    private String id;
    private Vessel vessel;
    private String portUnLocode;
    private String arrDate;
    private String createdAt;
    private String lastUpdate;
    private String startTime;
    private String endTime;

    public PortCall(JSONObject portCallJsonObj){
        if (portCallJsonObj != null) {
            try {
                String id               = portCallJsonObj.getString(Constants_jsonParsing.TAG_PCM_ID);
                String portUnLocode     = portCallJsonObj.getString(Constants_jsonParsing.TAG_PCM_PORT_UN_LOCODE);
                String arrDate          = portCallJsonObj.getString(Constants_jsonParsing.TAG_PCM_ARRIVAL_DATE);
                String crAt             = portCallJsonObj.getString(Constants_jsonParsing.TAG_PCM_CREATED_AT);
                String lastUpdate       = portCallJsonObj.getString(Constants_jsonParsing.TAG_PCM_LAST_UPDATE);
                String startTime        = portCallJsonObj.getString(Constants_jsonParsing.TAG_PCM_START_TIME);
                String endTime          = portCallJsonObj.getString(Constants_jsonParsing.TAG_PCM_END_TIME);

                Vessel vessel;
                try {
                    vessel = new Vessel(portCallJsonObj.getJSONObject(Constants_jsonParsing.TAG_PCM_VESSEL));
                } catch (JSONException e1) {vessel = null;}

                this.id             = id;
                this.vessel         = vessel;
                this.portUnLocode   = portUnLocode;
                this.arrDate        = arrDate;
                this.createdAt      = crAt;
                this.lastUpdate     = lastUpdate;
                this.startTime      = startTime;
                this.endTime        = endTime;

            } catch (JSONException e2) {
                Log.e("PortCall Constructor", "Problem getting data - " + e2.toString());
            }
        } else {
            Log.e("PortCall Constructor", "param portCallJsonObj is null");
        }
    }

    /**
     * Constructor for creating a new PortCall that includes all class variables
     * @param id
     */
    public PortCall(String id, Vessel vessel, String portUnLocode, String arrivalDate, String createdAt
                ,String lastUpdate, String startTime, String endTime){
        setId(id);
        setVessel(vessel);
        setPortUnLocode(portUnLocode);
        setArrDate(arrivalDate);
        setCreatedAt(createdAt);
        setLastUpdate(lastUpdate);
        setStartTime(startTime);
        setEndTime(endTime);
    }


    public String getId() {
        return id;
    }

    private void setId(String id) {
        this.id = id;
    }

    public Vessel getVessel() {
        return vessel;
    }

    private void setVessel(Vessel vessel) {
        this.vessel = vessel;
    }

    public String getPortUnLocode() {
        return portUnLocode;
    }

    private void setPortUnLocode(String portUnLocode) {
        this.portUnLocode = portUnLocode;
    }

    public String getArrDate() {
        return arrDate;
    }

    private void setArrDate(String arrDate) {
        this.arrDate = arrDate;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    private void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getLastUpdate() {
        return lastUpdate;
    }

    private void setLastUpdate(String lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String getStartTime() {
        return startTime;
    }

    private void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    private void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String toString(){
        String toStr = "PortCallMessage ID - " + getId()
                + "\nCreated at: " + getCreatedAt();

        return toStr;
    }

}
