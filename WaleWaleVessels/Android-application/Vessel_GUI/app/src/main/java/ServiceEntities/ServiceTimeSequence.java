package ServiceEntities;

import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by maxedman on 2017-04-25.
 */

public enum ServiceTimeSequence {
    REQUESTED("Requested"),
    REQUEST_RECEIVED("Request Received"),
    CONFIRMED("Confirmed"),
    DENIED("Denied"),
    COMMENCED("Commenced"),
    COMPLETED("Completed");

    private String serviceTimeSeqText;

    ServiceTimeSequence(String text) {
        this.serviceTimeSeqText = text;
    }

    public String getText() {
        return this.serviceTimeSeqText;
    }

    public static ServiceTimeSequence fromString(String text) throws IllegalArgumentException {
        for (ServiceTimeSequence serviceTimeSeq : ServiceTimeSequence.values()) {
            if (serviceTimeSeq.serviceTimeSeqText.equalsIgnoreCase(text)) {
                return serviceTimeSeq;
            }
        }
        throw new IllegalArgumentException("No constant of Service Time Sequence with text: " + text);
    }

    public static HashMap<String, ServiceTimeSequence> toMap(){
        HashMap<String, ServiceTimeSequence> resMap = new HashMap<String, ServiceTimeSequence>();
        for (ServiceTimeSequence servTimeSeqObj : Arrays.asList(ServiceTimeSequence.values())) {
            resMap.put(servTimeSeqObj.getText(), servTimeSeqObj);
        }
        return resMap;
    }
}
