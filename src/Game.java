import javax.swing.JFrame;
import java.awt.*;
import java.awt.image.BufferStrategy;
import javax.swing.JPanel;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

public class Game extends Canvas {

    private BufferStrategy strategy;
    boolean gameRunning = true;

    private int mazeSize = 25;
    MazePiece runner = new MazePiece(0, 0);
    MazePiece finish = new MazePiece();
    int finishLocationX = 12;
    int finishLocationY = 12;

    ArrayList<Arrow> arrows = new ArrayList();
    MazeGenerator maze;
    Display display = new Display();

    public Game() {
        maze = new MazeGenerator(25, 25);
        JFrame container = new JFrame("Maze");

        JPanel panel = (JPanel) container.getContentPane();
        panel.setPreferredSize(new Dimension(750, 750));
        panel.setLayout(null);

        setBounds(0, 0, 750, 750);
        panel.add(this);

        setIgnoreRepaint(true);

        container.pack();
        container.setResizable(false);
        container.setVisible(true);

        container.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        addKeyListener(new KeyInputHandler());

        requestFocus();


        createBufferStrategy(2);
        strategy = getBufferStrategy();
    }

    public void run() {
        while (gameRunning) {
            if(currantLocationX == finishLocationX && currantLocationY == finishLocationY) {
                arrows.clear();
                currantLocationX = 0;
                currantLocationY = 0;
                maze = new MazeGenerator(25, 25);
            }
            Graphics2D g2 = (Graphics2D) strategy.getDrawGraphics();
            display.clear(g2);
            display.drawMaze(maze, g2);
            for(Arrow arrow : arrows) {
                display.drawArrow(arrow.getLocationX(), arrow.getLocationY(), arrow.getDirection(), g2);
            }
            display.drawFinish(finishLocationX, finishLocationY, g2);
            display.drawRunner(currantLocationX, currantLocationY, g2);

            g2.dispose();
            strategy.show();

            try {
                Thread.sleep(10);
            } catch (Exception e) {
                System.out.println("Error in running thread " + e);
            }
        }
    }

    private class KeyInputHandler extends KeyAdapter {
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                if(currantLocationX != 0 && !maze.getMazeTileAt(currantLocationX, currantLocationY).getIsWallPresent(Directions.WEST)) {
                    currantLocationX--;
                    currantDirection = Directions.WEST;
                }
            }
            if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                if(currantLocationX != maze.getSizeX() - 1 && !maze.getMazeTileAt(currantLocationX, currantLocationY).getIsWallPresent(Directions.EAST)) {
                    currantLocationX++;
                    currantDirection = Directions.EAST;
                }
            }
            if (e.getKeyCode() == KeyEvent.VK_UP) {
                if(currantLocationY != 0 && !maze.getMazeTileAt(currantLocationX, currantLocationY).getIsWallPresent(Directions.NORTH)) {
                    currantLocationY--;
                    currantDirection = Directions.NORTH;
                }
            }
            if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                if(currantLocationY != maze.getSizeY() - 1 && !maze.getMazeTileAt(currantLocationX, currantLocationY).getIsWallPresent(Directions.SOUTH)) {
                    currantLocationY++;
                    currantDirection = Directions.SOUTH;
                }
            }
            if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                arrows.add(new Arrow(currantLocationX, currantLocationY, currantDirection));
            }
        }

        public void keyReleased(KeyEvent e) {
        }

        public void keyTyped(KeyEvent e) {
            // if we hit escape, then quit the game
            if (e.getKeyChar() == 27) {
                System.exit(0);
            }
        }
    }

    public static void main(String argv[]) {
        Game game = new Game();
        game.run();
    }
}