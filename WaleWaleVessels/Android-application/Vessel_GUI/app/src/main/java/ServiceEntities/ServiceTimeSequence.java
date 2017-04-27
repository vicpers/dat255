package ServiceEntities;

import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by maxedman on 2017-04-25.
 */

public enum ServiceTimeSequence {
    REQUESTED("Requested"),
    REQUEST_RECIEVED("Request Recieved"),
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

    public static HashMap<ServiceTimeSequence, String> toMap(){
        HashMap<ServiceTimeSequence, String> resMap = new HashMap<ServiceTimeSequence, String>();
        for (ServiceTimeSequence servTimeSeqObj : Arrays.asList(ServiceTimeSequence.values())) {
            resMap.put(servTimeSeqObj, servTimeSeqObj.getText());
        }
        return resMap;
    }
}
