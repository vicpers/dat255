package ServiceEntities;

/**
 * Created by maxedman on 2017-04-21.
 */

public class Between {
    private Location from;
    private Location to;

    public Between(Location from, Location to){
        this.from = from;
        this.to = to;
    }

    public Location getFrom() {
        return from;
    }

    private void setFrom(Location from) {
        this.from = from;
    }

    public Location getTo() {
        return to;
    }

    private void setTo(Location to) {
        this.to = to;
    }
}
