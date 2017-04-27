package ServiceEntities;

import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by maxedman on 2017-04-25.
 */

public enum LocationTimeSequence {
    ARRIVAL_TO("Arrival to"), DEPAPRTURE_FROM("Departure from");

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

    public static HashMap<LocationTimeSequence, String> toMap(){
        HashMap<LocationTimeSequence, String> resMap = new HashMap<LocationTimeSequence, String>();
        for (LocationTimeSequence locTimeSeqObj : Arrays.asList(LocationTimeSequence.values())) {
            resMap.put(locTimeSeqObj, locTimeSeqObj.getText());
        }
        return resMap;
    }
}
