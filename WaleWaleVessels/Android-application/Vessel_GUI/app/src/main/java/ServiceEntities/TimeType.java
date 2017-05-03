package ServiceEntities;

import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by maxedman on 2017-04-21.
 */

public enum TimeType {
    RECOMMENDED("Recommended"),
    TARGET("Target"),
    CANCELLED("Cancelled"),
    ESTIMATED("Estimated"),
    ACTUAL("Actual");

    private String timeTypeText;

    TimeType(String text) { this.timeTypeText = text; }

    public String getText() {
        return this.timeTypeText;
    }

    public static TimeType fromString(String text) throws IllegalArgumentException {
        for (TimeType timeType : TimeType.values()) {
            if (timeType.timeTypeText.equalsIgnoreCase(text)) {
                return timeType;
            }
        }
        throw new IllegalArgumentException("No constant of Time Type with text: " + text);
    }

    public static HashMap<String, TimeType> toMap(){
        HashMap<String, TimeType> resMap = new HashMap<String, TimeType>();
        for (TimeType timeTypeObj : Arrays.asList(TimeType.values())) {
            resMap.put(timeTypeObj.getText(), timeTypeObj);
        }
        return resMap;
    }
}
