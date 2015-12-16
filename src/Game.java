import javax.swing.JFrame;
import java.awt.image.BufferStrategy;
import java.awt.Graphics2D;
import java.awt.Canvas;
import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Game extends Canvas {
    private BufferStrategy strategy;
    boolean gameRunning = true;
    int currantLocationX = 0;
    int currantLocationY = 0;
    MazeGenerator maze;
    Display display = new Display();

    public Game() {
        maze = new MazeGenerator(50, 50);
        JFrame container = new JFrame("Maze");

        JPanel panel = (JPanel) container.getContentPane();
        panel.setPreferredSize(new Dimension(500, 500));
        panel.setLayout(null);

        setBounds(0, 0, 500, 500);
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
            Graphics2D g2 = (Graphics2D) strategy.getDrawGraphics();
            display.clear(g2);
            display.drawMaze(maze, g2);
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
                }
            }
            if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                if(currantLocationX != maze.getSizeX() - 1 && !maze.getMazeTileAt(currantLocationX, currantLocationY).getIsWallPresent(Directions.EAST)) {
                    currantLocationX++;
                }
            }
            if (e.getKeyCode() == KeyEvent.VK_UP) {
                if(currantLocationY != 0 && !maze.getMazeTileAt(currantLocationX, currantLocationY).getIsWallPresent(Directions.NORTH)) {
                    currantLocationY--;
                }
            }
            if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                if(currantLocationY != maze.getSizeY() - 1 && !maze.getMazeTileAt(currantLocationX, currantLocationY).getIsWallPresent(Directions.SOUTH)) {
                    currantLocationY++;
                }
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