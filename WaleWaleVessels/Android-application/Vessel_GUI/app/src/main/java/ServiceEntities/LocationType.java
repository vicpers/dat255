package ServiceEntities;

import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by maxedman on 2017-04-25.
 */

public enum LocationType {
    ANCHORING_AREA("Anchoring Area"),
    BERTH("Berth"),
    ETUG_ZONE("Tug Zone"),
    LOC("Loc"),
    PILOT_BOARDING_AREA("Pilot Boarding Area"),
    RENDEZV_AREA("Rendez-vous Area"),
    TRAFFIC_AREA("Traffic Area"),
    TUG_ZONE("Tug Zone"),
    NEXT_PORT("Next port"),
    PREVIOUS_PORT("Previous port"),
    VESSEL("Vessel");

    private String locTypeText;

    LocationType(String text) {
        this.locTypeText = text;
    }

    public String getText() {
        return this.locTypeText;
    }

    public static LocationType fromString(String text) throws IllegalArgumentException {
        for (LocationType locType : LocationType.values()) {
            if (locType.locTypeText.equalsIgnoreCase(text)) {
                return locType;
            }
        }
        throw new IllegalArgumentException("No constant of Location Type with text: " + text);
    }

    public static HashMap<String, LocationType> toMap(){
        HashMap<String, LocationType> resMap = new HashMap<String, LocationType>();
        for (LocationType locTypeObj : Arrays.asList(LocationType.values())) {
            resMap.put(locTypeObj.getText(), locTypeObj);
        }
        return resMap;
    }
}
