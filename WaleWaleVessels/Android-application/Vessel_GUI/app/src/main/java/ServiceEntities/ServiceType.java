package ServiceEntities;

/**
 * Created by maxedman on 2017-05-10.
 * Enums for ServiceType i.e. Stationary or Nautical,
 */

public enum ServiceType {
    STATIONARY("Stationary"),
    NAUTICAL("Nautical");

    private String serviceTypeText;

    ServiceType(String text) {
        this.serviceTypeText = text;
    }

    public String getText() {
        return this.serviceTypeText;
    }

    public static ServiceType fromString(String text) throws IllegalArgumentException {
        for (ServiceType serviceType : ServiceType.values()) {
            if (serviceType.serviceTypeText.equalsIgnoreCase(text)) {
                return serviceType;
            }
        }
        throw new IllegalArgumentException("No constant of Service Time Sequence with text: " + text);
    }
}
