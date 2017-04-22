package ServiceEntities;

/**
 * Created by maxedman on 2017-04-21.
 */

public class ArrivalLocation {
    private Location to;
    private Location from;

    public ArrivalLocation(Location from, Location to){
        this.to = to;
        this.from = from;
    }

}
