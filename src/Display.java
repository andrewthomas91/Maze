import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.JFrame;

public class Display extends JFrame{
    Graphics offscr;//What is drawn onto that is then used to create the off screen image
    Image offscreenImage;//The image off screen that is used for double buffering
    Font boldFont;
    Font boldHuge;

    /**
     Constructor for the Display object.
     @param i The image of the jFrame.
     */
    public Display(Image i){
        boldFont = new Font("SansSerif", Font.BOLD, 20);
        boldHuge = new Font("SansSerif", Font.BOLD, 40);
        offscreenImage = i;
        offscr = offscreenImage.getGraphics();
        offscr.setFont(boldFont);
    }

    public Graphics drawMaze(MazeGenerator maze){
        for(int x = 0; x < maze.getSizeX(); x++) {
            for (int y = 0; y < maze.getSizeY(); y++) {
                drawMazeTile(maze.getMazeTileAt(x,y));
            }
        }
        return offscr;
    }

    public void drawMazeTile(MazeTile mazeTile) {
        int x = mazeTile.getLocationX() + 2;
        int y = mazeTile.getLocationY() + 2;
        int tileWidthAndHeight = 20;
        int wallThickness = 1;

        int topLeftX = (x * tileWidthAndHeight) - (tileWidthAndHeight / 2);
        int topLeftY = (y * tileWidthAndHeight) - (tileWidthAndHeight / 2);

        int topRightX = (x * tileWidthAndHeight) + (tileWidthAndHeight / 2);
        int topRightY = (y * tileWidthAndHeight) - (tileWidthAndHeight / 2);

        int bottomLeftX = (x * tileWidthAndHeight) - (tileWidthAndHeight / 2);
        int bottomLeftY = (y * tileWidthAndHeight) + (tileWidthAndHeight / 2);

        offscr.setColor(Color.BLACK);
        if(mazeTile.getIsWallPresent(Directions.NORTH)) {
            offscr.fillRect(topLeftX, topLeftY, tileWidthAndHeight, wallThickness);
        }
        if(mazeTile.getIsWallPresent(Directions.EAST)) {
            offscr.fillRect(topRightX, topRightY, wallThickness, tileWidthAndHeight);
        }
        if(mazeTile.getIsWallPresent(Directions.SOUTH)) {
            offscr.fillRect(bottomLeftX, bottomLeftY, tileWidthAndHeight, wallThickness);
        }
        if(mazeTile.getIsWallPresent(Directions.WEST)) {
            offscr.fillRect(topLeftX, topLeftY, wallThickness, tileWidthAndHeight);
        }
    }

    /**
     Clears the screen.
     */
    public Graphics clear(){
        offscr.setColor(Color.white);
        offscr.fillRect(0, 0, 500, 500);
        return offscr;
    }
}