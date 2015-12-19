import javax.swing.JFrame;
import java.awt.*;
import java.awt.image.BufferStrategy;
import javax.swing.JPanel;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Game extends Canvas {
    private BufferStrategy strategy;
    private int mazeSize = 25;
    private MazePiece runner = new MazePiece(0, 0);
    private MazePiece finish = new MazePiece();
    private ArrayList<Arrow> arrows = new ArrayList<>();
    private MazeGenerator maze;
    private Display display = new Display();

    public Game() {
        int panelSize = 750;
        maze = new MazeGenerator(mazeSize, mazeSize);
        finish.generateNewFinishLocation(mazeSize);

        JFrame container = new JFrame("Maze");
        JPanel panel = (JPanel) container.getContentPane();
        panel.setPreferredSize(new Dimension(panelSize, panelSize));
        panel.setLayout(null);
        setBounds(0, 0, panelSize, panelSize);
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
        while (true) {
            if(runner.isOnSameLocationAs(finish)) {
                arrows.clear();
                runner.setLocationX(0);
                runner.setLocationY(0);
                maze = new MazeGenerator(mazeSize, mazeSize);
            }

            Graphics2D g2 = (Graphics2D) strategy.getDrawGraphics();
            display.clear(g2);
            display.drawMaze(maze, g2);
            for(Arrow arrow : arrows) {
                display.drawArrow(arrow.getLocationX(), arrow.getLocationY(), arrow.getDirection(), g2);
            }
            display.drawFinish(finish.getLocationX(), finish.getLocationY(), g2);
            display.drawRunner(runner.getLocationX(), runner.getLocationY(), g2);
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
                if(runner.getLocationX() != 0 && !maze.getMazeTileAt(runner.getLocationX(), runner.getLocationY()).getIsWallPresent(Directions.WEST)) {
                    runner.setLocationX(runner.getLocationX() - 1);
                    runner.setDirection(Directions.WEST);
                }
            }
            if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                if(runner.getLocationX() != maze.getSizeX() - 1 && !maze.getMazeTileAt(runner.getLocationX(), runner.getLocationY()).getIsWallPresent(Directions.EAST)) {
                    runner.setLocationX(runner.getLocationX() + 1);
                    runner.setDirection(Directions.EAST);
                }
            }
            if (e.getKeyCode() == KeyEvent.VK_UP) {
                if(runner.getLocationY() != 0 && !maze.getMazeTileAt(runner.getLocationX(), runner.getLocationY()).getIsWallPresent(Directions.NORTH)) {
                    runner.setLocationY(runner.getLocationY() - 1);
                    runner.setDirection(Directions.NORTH);
                }
            }
            if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                if(runner.getLocationY() != maze.getSizeY() - 1 && !maze.getMazeTileAt(runner.getLocationX(), runner.getLocationY()).getIsWallPresent(Directions.SOUTH)) {
                    runner.setLocationY(runner.getLocationY() + 1);
                    runner.setDirection(Directions.SOUTH);
                }
            }
            if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                arrows.add(new Arrow(runner.getLocationX(), runner.getLocationY(), runner.getDirection()));
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