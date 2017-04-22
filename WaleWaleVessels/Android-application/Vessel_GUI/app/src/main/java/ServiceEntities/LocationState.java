package ServiceEntities;

/**
 * Created by maxedman on 2017-04-21.
 */

public class LocationState {

    private String referenceObject;
    private String time;
    private String timeType;
    private ArrivalLocation arrivalLocation;
    private DepartureLocation departureLocation;

    public LocationState(String referenceObject, String time, String timeType, ArrivalLocation arrivalLocation, DepartureLocation departureLocation) {
        this.referenceObject = referenceObject;
        this.time = time;
        this.timeType = timeType;
        this.arrivalLocation = arrivalLocation;
        this.departureLocation = departureLocation;
    }
}
