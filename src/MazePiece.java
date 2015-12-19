import java.util.Random;

public class MazePiece {
    private Random random = new Random();
    private int locationX, locationY;
    private Directions direction = Directions.NONE;

    public MazePiece() {
        locationX = 0;
        locationY = 0;
    }

    public MazePiece(int locationX, int locationY) {
        this.locationX = locationX;
        this.locationY = locationY;
    }

    public boolean isOnSameLocationAs(MazePiece otherPiece) {
        return this.locationX == otherPiece.getLocationX() && this.locationY == otherPiece.getLocationY();
    }

    public void generateNewFinishLocation(int mazeSize) {
        locationX = random.nextInt(mazeSize / 2) + mazeSize / 4;
        locationY = random.nextInt(mazeSize / 2) + mazeSize / 4;
    }

    public void setLocationX(int locationX) {
        this.locationX = locationX;
    }

    public void setLocationY(int locationY) {
        this.locationY = locationY;
    }

    public void setDirection(Directions direction) {
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
