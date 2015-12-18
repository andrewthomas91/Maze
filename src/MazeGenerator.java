import java.util.Random;

public class MazeGenerator {
    private final int sizeX;
    private final int sizeY;
    private final MazeTile[][] maze;
    private Random random = new Random();

    public MazeGenerator(int x, int y) {
        sizeX = x;
        sizeY = y;
        maze = new MazeTile[sizeX][sizeY];
        generateMaze();
    }

    private void generateMaze() {
        for(int x = 0; x < sizeX; x++) {
            for(int y = 0; y < sizeX; y++) {
                maze[x][y] = new MazeTile(x, y);
            }
        }
        int currentX = random.nextInt(sizeX);
        int currentY = random.nextInt(sizeY);
        visitTile(currentX, currentY, Directions.NONE);
    }

    private void visitTile(int x, int y, Directions wallToRemove) {
        maze[x][y].setVisited(true);
        maze[x][y].setIsWallPresent(wallToRemove, false);

        int nextTileToVisit = random.nextInt(4);
        int counter = 0;

        while(counter++ < 4) {
            if(nextTileToVisit == 0) { // Visit North
                if(y != 0 && !maze[x][y-1].getVisited()) {
                    maze[x][y].setIsWallPresent(Directions.NORTH, false);
                    visitTile(x, y-1, Directions.SOUTH);
                }
            }
            if(nextTileToVisit == 1) { // Visit EAST
                if(x != (sizeX - 1) && !maze[x+1][y].getVisited()) {
                    maze[x][y].setIsWallPresent(Directions.EAST, false);
                    visitTile(x+1, y, Directions.WEST);
                }
            }
            if(nextTileToVisit == 2) { // Visit SOUTH
                if(y != (sizeY - 1) && !maze[x][y+1].getVisited()) {
                    maze[x][y].setIsWallPresent(Directions.SOUTH, false);
                    visitTile(x, y+1, Directions.NORTH);
                }
            }
            if(nextTileToVisit == 3) { // Visit WEST
                if(x != 0 && !maze[x-1][y].getVisited()) {
                    maze[x][y].setIsWallPresent(Directions.WEST, false);
                    visitTile(x-1, y, Directions.EAST);
                }
            }
            if(nextTileToVisit == 3) {
                nextTileToVisit = 0;
            }
            else {
                nextTileToVisit++;
            }
        }
    }

    public int getSizeX() {
        return sizeX;
    }

    public int getSizeY() {
        return sizeY;
    }

    public MazeTile getMazeTileAt(int x, int y) {
        return maze[x][y];
    }
}