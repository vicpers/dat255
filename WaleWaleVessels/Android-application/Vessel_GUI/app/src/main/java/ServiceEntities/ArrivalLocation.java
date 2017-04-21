package ServiceEntities;

/**
 * Created by maxedman on 2017-04-21.
 */

public class ArrivalLocation extends Location {
    private Location to;
    private Location from;

    public ArrivalLocation(Location to){
        this.to = to;
    }

    public ArrivalLocation(Location to, Location from){
        this.to = to;
        this.from = from;
    }

}
