package ServiceEntities;

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

    public String toString(){
        String toStr = "VesselID: " + getId()
                + "\nVesselName: " + getName();

        return toStr;
    }
}
