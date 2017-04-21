package ServiceEntities;


/**
 * Created by maxedman on 2017-04-20.
 */

public class PCM {

    private String id;
    private Vessel vessel;
    private String portUnLocode;
    private String arrivalDate;
    private String createdAt;
    private String lastUpdate;
    private String startTime;
    private String endTime;

    /**
     * Constructor for creating a new PortCallMessage that includes all class variables
     * @param id
     */
    public PCM(String id, Vessel vessel, String portUnLocode, String arrivalDate, String createdAt
                ,String lastUpdate, String startTime, String endTime){
        setId(id);
        setVessel(vessel);
        setPortUnLocode(portUnLocode);
        setArrivalDate(arrivalDate);
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

    public String getArrivalDate() {
        return arrivalDate;
    }

    private void setArrivalDate(String arrivalDate) {
        this.arrivalDate = arrivalDate;
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
