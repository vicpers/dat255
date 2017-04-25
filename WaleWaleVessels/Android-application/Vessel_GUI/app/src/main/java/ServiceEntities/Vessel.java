package ServiceEntities;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by maxedman on 2017-04-20.
 */

public class Vessel {
    private long imo;
    private String id;
    private String name;
    private String callSign;
    private long mmsi;
    private String type;
    private long stmVesselId;
    private String photoURL;

    public Vessel(JSONObject vesselJsonObj){
        if (vesselJsonObj != null) {
            try {

                long imo            = vesselJsonObj.getLong(Constants_jsonParsing.TAG_VESSEL_IMO);
                String id           = vesselJsonObj.getString(Constants_jsonParsing.TAG_VESSEL_ID);
                String name         = vesselJsonObj.getString(Constants_jsonParsing.TAG_VESSEL_NAME);
                String callSign     = vesselJsonObj.getString(Constants_jsonParsing.TAG_VESSEL_CALL_SIGN);
                long mmsi           = vesselJsonObj.getLong(Constants_jsonParsing.TAG_VESSEL_MMSI);
                String type         = vesselJsonObj.getString(Constants_jsonParsing.TAG_VESSEL_TYPE);
                long stmVesselId    = vesselJsonObj.getLong(Constants_jsonParsing.TAG_VESSEL_STM_VESSEL_ID);
                String photoURL     = vesselJsonObj.getString(Constants_jsonParsing.TAG_VESSEL_PHOTOURL);

                this.imo            = imo;
                this.id             = id;
                this.name           = name;
                this.callSign       = callSign;
                this.mmsi           = mmsi;
                this.type           = type;
                this.stmVesselId    = stmVesselId;
                this.photoURL       = photoURL;

            } catch (JSONException e) {
                Log.e("Vessel Constructor", "Problem getting data - " + e.toString());
            }
        } else {
            Log.e("Vessel Constructor", "param vesselJsonObj is null");
        }
    }

    public Vessel(long imo, String id, String name, String callSign, long mmsi, String type, long stmVesselId, String photoURL){
        setImo(imo);
        setId(id);
        setName(name);
        setCallSign(callSign);
        setMmsi(mmsi);
        setType(type);
        setStmVesselId(stmVesselId);
        setPhotoURL(photoURL);
    }

    public long getImo() {
        return imo;
    }

    private void setImo(long imo) {
        this.imo = imo;
    }

    public String getId() {
        return id;
    }

    private void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    private void setName(String name) {
        this.name = name;
    }

    public String getCallSign() {
        return callSign;
    }

    private void setCallSign(String callSign) {
        this.callSign = callSign;
    }

    public long getMmsi() {
        return mmsi;
    }

    private void setMmsi(long mmsi) {
        this.mmsi = mmsi;
    }

    public String getType() {
        return type;
    }

    private void setType(String type) {
        this.type = type;
    }

    public long getStmVesselId() {
        return stmVesselId;
    }

    private void setStmVesselId(long stmVesselId) {
        this.stmVesselId = stmVesselId;
    }

    public String getPhotoURL() {
        return photoURL;
    }

    private void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
    }

    @Override
    public String toString(){
        return "VesselID: " + getId() +
               "\nVesselName: " + getName();
    }

    //TODO Ordna upp toJson(). Kan skapa Fatyal Exception med Null-pointer exception
    public String toJson() {
        return "{" +
                "\"imo\":" + imo +
                ", \"id\":" + id +
                ", \"name\":" + name +
                ", \"callSign\":" + callSign +
                ", \"mmsi\":" + mmsi +
                ", \"type\":" + type +
                ", \"stmVesselId\":" + stmVesselId +
                ", \"photoURL\":" + photoURL +
                '}';
    }
}
