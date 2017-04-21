package HTTPRequest;

/**
 * Created by maxedman on 2017-04-20.
 */

class Vessel {
    private long imo;
    private String id;
    private String name;
    private String callSign;
    private long mmsi;
    private String type;
    private long stmVesselId;
    private String photoURL;

    public long getImo() {
        return imo;
    }

    public void setImo(long imo) {
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

    public void setName(String name) {
        this.name = name;
    }

    public String getCallSign() {
        return callSign;
    }

    public void setCallSign(String callSign) {
        this.callSign = callSign;
    }

    public long getMmsi() {
        return mmsi;
    }

    public void setMmsi(long mmsi) {
        this.mmsi = mmsi;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getStmVesselId() {
        return stmVesselId;
    }

    public void setStmVesselId(long stmVesselId) {
        this.stmVesselId = stmVesselId;
    }

    public String getPhotoURL() {
        return photoURL;
    }

    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
    }
}
