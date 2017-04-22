package ServiceEntities;

/**
 * Created by maxedman on 2017-04-21.
 */

public class Location {

    private String name;
    private Position position;
    private String type;

    public Location(String name, Position position, String type){
        setName(name);
        setPosition(position);
        setType(type);
    }

    public String getName() {
        return name;
    }

    private void setName(String name) {
        this.name = name;
    }

    public Position getPosition() {
        return position;
    }

    private void setPosition(Position position) {
        this.position = position;
    }

    public String getType() {
        return type;
    }

    private void setType(String type) {
        this.type = type;
    }

}
