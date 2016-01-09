public class Arrow {
    private double locationX, locationY;
    private Directions direction;

    public Arrow(double locationX, double locationY, Directions direction) {
        this.locationX = locationX;
        this.locationY = locationY;
        this.direction = direction;
    }

    public double getLocationX() {
        return locationX;
    }

    public double getLocationY() {
        return locationY;
    }

    public Directions getDirection() {
        return direction;
    }
}
