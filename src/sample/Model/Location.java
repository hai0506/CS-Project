package sample.Model;

public abstract class Location {
    protected String name;
    protected Coordinate coordinate;

    public Location(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }
}
