import java.util.Random;

public class MazePiece {
    private Random random = new Random();
    private int locationX, locationY;
    private Directions direction = Directions.NONE;

    public MazePiece(int locationX, int locationY) {
        this.locationX = locationX;
        this.locationY = locationY;
    }

    public MazePiece(int locationX, int locationY) {
        this.locationX = locationX;
        this.locationY = locationY;
    }

    public void generateNewFinishLocation(int mazeSize) {
        locationX = random.nextInt(mazeSize / 2) + mazeSize / 4;
        locationY = random.nextInt(mazeSize / 2) + mazeSize / 4;
    }

    public int setLocationX(int locationX) {
        this.locationX = locationX;
    }

    public int setLocationY(int locationY) {
        this.locationY = locationY;
    }

    public int setDirection(Directions direction) {
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
