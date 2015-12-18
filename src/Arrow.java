public class Arrow {
    private int locationX, locationY;
    private Directions direction;

    public Arrow(int locationX, int locationY, Directions direction) {
        this.locationX = locationX;
        this.locationY = locationY;
        this.direction = direction;
    }

    public int getLocationX() {
        return locationX;
    }

    public int getLocationY() {
        return locationY;
    }

    public Directions getDirection() {
        return direction;
    }
}
