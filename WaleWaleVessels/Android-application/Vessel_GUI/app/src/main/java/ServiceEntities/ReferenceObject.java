package ServiceEntities;

import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by maxedman on 2017-04-25.
 */

public enum ReferenceObject {
    AGENT("Agent"),
    ARRIVAL_MOORER("Arrival Moorer"),
    DEPARTURE_MOORER("Departure Moorer"),
    ESCORT_TUG("Escort Tug"),
    PASSENGER("Passenger"),
    PILOT("Pilot"),
    PILOT_BOAT("Pilot Boat"),
    TUG("Tug"),
    VESSEL("Vessel"),
    ICEBREAKER("Icebreaker"),
    SECURITY("Security"),
    PONTOONS_AND_FENDERS("Pontoons and Fenders");

    private String refObjText;

    ReferenceObject(String text) {
        this.refObjText = text;
    }

    public String getText() {
        return this.refObjText;
    }

    public static ReferenceObject fromString(String text) throws IllegalArgumentException {
        for (ReferenceObject refObj : ReferenceObject.values()) {
            if (refObj.refObjText.equalsIgnoreCase(text)) {
                return refObj;
            }
        }
        throw new IllegalArgumentException("No constant of Reference Object with text: " + text);
    }

    public static HashMap<String, ReferenceObject> toMap(){
        HashMap<String, ReferenceObject> resMap = new HashMap<String, ReferenceObject>();
        for (ReferenceObject refObj : Arrays.asList(ReferenceObject.values())) {
            resMap.put(refObj.getText(), refObj);
        }
        return resMap;
    }
}
