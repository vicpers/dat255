package ServiceEntities;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by maxedman on 2017-04-20.
 * A class which represents a Vessel.
 */

public class Vessel implements Serializable {
    private String imo;
    private String id;
    private String name;
    private String callSign;
    private String mmsi;
    private VesselType vesselType;
    private String stmVesselId;
    private String photoURL;

    public Vessel(){}

    /**
     * Creates a Vessel from a JSON object
     * @param vesselJsonObj
     */
    public Vessel(JSONObject vesselJsonObj){
        if (vesselJsonObj != null) {
            try {
// All separate try-catch-blocks are because of avoiding method termination.
                try {
                    this.imo            = vesselJsonObj.getString(Constants_jsonParsing.TAG_VESSEL_IMO);
                } catch (JSONException e){ this.imo = null;}
                try {
                    this.id = vesselJsonObj.getString(Constants_jsonParsing.TAG_VESSEL_ID);
                } catch (JSONException e){ this.id = null;}
                this.name         = vesselJsonObj.getString(Constants_jsonParsing.TAG_VESSEL_NAME);
                this.callSign     = vesselJsonObj.getString(Constants_jsonParsing.TAG_VESSEL_CALL_SIGN);
                this.mmsi         = vesselJsonObj.getString(Constants_jsonParsing.TAG_VESSEL_MMSI);
                this.vesselType   = VesselType.valueOf(vesselJsonObj.getString(Constants_jsonParsing.TAG_VESSEL_TYPE));
                try {
                    this.stmVesselId  = vesselJsonObj.getString(Constants_jsonParsing.TAG_VESSEL_STM_VESSEL_ID);
                } catch (JSONException e){ this.stmVesselId = null; }
                this.photoURL     = vesselJsonObj.getString(Constants_jsonParsing.TAG_VESSEL_PHOTOURL);

            } catch (JSONException e) {
                Log.e("Vessel Constructor", "Problem getting data - " + e.toString());
            }
        } else {
            Log.e("Vessel Constructor", "param vesselJsonObj is null");
        }
    }

    /**
     * Creates a Vessel from input data
     * @param imo
     * @param id
     * @param name
     * @param callSign
     * @param mmsi
     * @param type
     * @param stmVesselId
     * @param photoURL
     */
    public Vessel(String imo, String id, String name, String callSign, String mmsi, VesselType type, String stmVesselId, String photoURL){
        setImo(imo);
        setId(id);
        setName(name);
        setCallSign(callSign);
        setMmsi(mmsi);
        setType(type);
        setStmVesselId(stmVesselId);
        setPhotoURL(photoURL);
    }

    public Vessel(String id){
        setId(id);
    }

    public String getImo() {
        return imo;
    }

    private void setImo(String imo) {
        this.imo = imo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public String getMmsi() {
        return mmsi;
    }

    private void setMmsi(String mmsi) {
        this.mmsi = mmsi;
    }

    public VesselType getType() {
        return vesselType;
    }

    private void setType(VesselType type) {
        this.vesselType = type;
    }

    public String getStmVesselId() {
        return stmVesselId;
    }

    private void setStmVesselId(String stmVesselId) {
        this.stmVesselId = stmVesselId;
    }

    public String getPhotoURL() {
        return photoURL;
    }

    private void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
    }


    public VesselType getVesselType() {
        return vesselType;
    }

    public void setVesselType(VesselType vesselType) {
        this.vesselType = vesselType;
    }



    @Override
    public String toString(){
        return "VesselID: " + getId() +
               "\nVesselName: " + getName();
    }


}
