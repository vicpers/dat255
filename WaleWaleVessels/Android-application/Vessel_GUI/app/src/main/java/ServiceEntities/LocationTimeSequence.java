package ServiceEntities;

import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by maxedman on 2017-04-25.
 */

public enum LocationTimeSequence {
    ARRIVAL_TO("Arrival to"),
    DEPARTURE_FROM("Departure from");

    private String locTimeSeqText;

    LocationTimeSequence(String text) {
        this.locTimeSeqText = text;
    }

    public String getText() {
        return this.locTimeSeqText;
    }

    public static LocationTimeSequence fromString(String text) throws IllegalArgumentException {
        for (LocationTimeSequence locTimeSeq : LocationTimeSequence.values()) {
            if (locTimeSeq.locTimeSeqText.equalsIgnoreCase(text)) {
                return locTimeSeq;
            }
        }
        throw new IllegalArgumentException("No constant of Location Time Sequence with text: " + text);
    }

    public static HashMap<String, LocationTimeSequence> toMap(){
        HashMap<String, LocationTimeSequence> resMap = new HashMap<String, LocationTimeSequence>();
        for (LocationTimeSequence locTimeSeqObj : Arrays.asList(LocationTimeSequence.values())) {
            resMap.put(locTimeSeqObj.getText(), locTimeSeqObj);
        }
        return resMap;
    }
}
