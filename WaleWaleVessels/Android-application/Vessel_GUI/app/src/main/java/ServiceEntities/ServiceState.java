package ServiceEntities;

/**
 * Created by maxedman on 2017-04-21.
 */

public class ServiceState {
    private String serviceObject;
    private String timeSequence;
    private String at; //TODO När ServiceState dyker upp i något PCM så se om at är en Location
    private Between betweenLocations;
    private String performingActor;

    public ServiceState(String serviceObject, String timeSequence, String at, Between betweenLocations, String performingActor) {
        this.serviceObject = serviceObject;
        this.timeSequence = timeSequence;
        this.at = at;
        this.betweenLocations = betweenLocations;
        this.performingActor = performingActor;
    }
}
