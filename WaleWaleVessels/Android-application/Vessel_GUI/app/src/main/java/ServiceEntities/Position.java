package ServiceEntities;

/**
 * Created by maxedman on 2017-04-21.
 */

public class Position {
    private long latitude;
    private long longitude;

    public Position(long latitude, long longitude){
        setLatitude(latitude);
        setLongitude(longitude);
    }

    public long getLongitude() {
        return longitude;
    }

    private void setLongitude(long longitude) {
        this.longitude = longitude;
    }

    public long getLatitude() {
        return latitude;
    }

    private void setLatitude(long latitude) {
        this.latitude = latitude;
    }
}
