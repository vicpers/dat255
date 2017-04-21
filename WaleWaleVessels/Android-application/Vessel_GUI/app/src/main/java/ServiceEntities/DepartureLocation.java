package ServiceEntities;

/**
 * Created by maxedman on 2017-04-21.
 */

public class DepartureLocation extends Location {
    private Location to;
    private Location from;

    public DepartureLocation(Location from){
        this.from = from;
    }

    public DepartureLocation(Location from, Location to){
        this.from = from;
        this.to = to;
    }
}
