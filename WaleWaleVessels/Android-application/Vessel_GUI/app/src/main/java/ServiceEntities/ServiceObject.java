package ServiceEntities;


import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by maxedman on 2017-04-25.
 */

public enum ServiceObject {
    ANCHORING("Anchoring"),
    ARRIVAL_ANCHORING_OPERATION("Arrival to anchoring operation"),
    ARRIVAL_BERTH("Arrival to berth"),
    ARRIVAL_PORTAREA("Arrival to port area"),
    ARRIVAL_VTSAREA("Arrival to VTS-area"),
    BERTH_SHIFTING("Shift of berth"),
    BUNKERING_OPERATION("Bunkering operation"),
    CARGO_OPERATION("Cargo operation"),
    DEPARTURE_ANCHORING_AREA("Departure from anchoring area"),
    DEPARTURE_BERTH("Departure from berth"),
    DEPARTURE_PORTAREA("Departure from port area"),
    DEPARTURE_VTSAREA("Departure from VTS-area"),
    ESCORT_TOWAGE("Escort towage"),
    GARBARGE_OPERATION("Garbage operation"),//TODO Dubbelkolla så att det är verkligen är GARBARGE i portCDM
    ICEBREAKING_OPERATION("Icebreaking operation"),
    LUBEOIL_OPERATION("Lube-oil operation"),
    ARRIVAL_MOORING_OPERATION("Arrival to mooring operation"),
    DEPARTURE_MOORING_OPERATION("Departure from mooring operation"),
    PILOTAGE("Pilotage"),
    PORTCARGOOPERATION("Port cargo operation"),
    PRECARGOOPERATION("Pre-cargo operation"),
    PROVISIONING_OPERATION("Provisioning operation"),
    SLOP_OPERATION("Slop operation"),
    SLUDGE_OPERATION("Sludge operation"),
    TOWAGE("Towage"),
    WATER_OPERATION("Water operation"),
    GANGWAY("Gangway"),
    EMBARKING("Embarking"),
    PILOT_BOAT("Pilot boat"),
    PONTOONS_AND_FENDERS("Pontoons and fenders"),
    SECURITY("Security"),
    TOURS("Tours"),
    FORKLIFT("Forklift")
    ;

    private String serviceObjText;

    ServiceObject(String text) {
        this.serviceObjText = text;
    }

    public String getText() {
        return this.serviceObjText;
    }

    public static ServiceObject fromString(String text) throws IllegalArgumentException {
        for (ServiceObject serviceObject : ServiceObject.values()) {
            if (serviceObject.serviceObjText.equalsIgnoreCase(text)) {
                return serviceObject;
            }
        }
        throw new IllegalArgumentException("No constant of Service Object with text: " + text);
    }

    public static HashMap<ServiceObject, String> toMap(){
        HashMap<ServiceObject, String> resMap = new HashMap<ServiceObject, String>();
        for (ServiceObject serviceObj : Arrays.asList(ServiceObject.values())) {
            resMap.put(serviceObj, serviceObj.getText());
        }
        return resMap;
    }

}
