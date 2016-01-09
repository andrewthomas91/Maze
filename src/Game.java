import javax.swing.JFrame;
import java.awt.*;
import java.awt.image.BufferStrategy;
import javax.swing.JPanel;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class Game extends Canvas {
    private BufferStrategy strategy;
    private int mazeSize = 25;
    private ArrayList<Arrow> arrows = new ArrayList<>();
    private MazeGenerator maze;
    private MazePiece finish = new MazePiece();
    private MazePiece runner;
    private Display display = new Display();

    public Game() {
        int panelSize = 750;
        maze = new MazeGenerator(mazeSize, mazeSize);
        runner = new MazePiece(0, 0, maze.getMazeTileWidthAndHeight());
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
        addMouseListener(new GameMouseListener());
        addMouseMotionListener(new GameMouseMotionListener());
        requestFocus();
        createBufferStrategy(2);
        strategy = getBufferStrategy();
    }

    public void run() {
        while (true) {
            if(runner.isOnSameLocationAs(finish)) {
                arrows.clear();
                runner = new MazePiece(0, 0, maze.getMazeTileWidthAndHeight());
                maze = new MazeGenerator(mazeSize, mazeSize);
            }

            Graphics2D g2 = (Graphics2D) strategy.getDrawGraphics();
            display.clear(g2);
            display.drawMaze(maze, g2);
            for(Arrow arrow : arrows) {
                display.drawArrow(arrow.getLocationX(), arrow.getLocationY(), arrow.getDirection(), maze.getMazeTileWidthAndHeight(), g2);
            }
            display.drawFinish(finish.getLocationX(), finish.getLocationY(), maze.getMazeTileWidthAndHeight(), g2);
            display.drawRunner(runner.getCoordinateX(), runner.getCoordinateY(), g2);
            g2.dispose();
            strategy.show();

            try {
                Thread.sleep(10);
            } catch (Exception e) {
                System.out.println("Error in running thread " + e);
            }
        }
    }

    private class GameMouseListener implements MouseListener {
        public void mousePressed(MouseEvent e) {
        }

        public void mouseClicked(MouseEvent e) {
        }

        public void mouseReleased(MouseEvent e) {
        }

        public void mouseEntered(MouseEvent e) {
        }

        public void mouseExited(MouseEvent e) {
        }
    }

    private class GameMouseMotionListener implements MouseMotionListener {
        public void mouseDragged(MouseEvent e) {

        }

        public void mouseMoved(MouseEvent e) {
            int tileSize = maze.getMazeTileWidthAndHeight();
            double x = e.getX();
            double y = e.getY();

            int oldLocationX = runner.getLocationX();
            int oldLocationY = runner.getLocationY();

            int newLocationX = runner.convertCoordinateToLocation(x, tileSize);
            int newLocationY = runner.convertCoordinateToLocation(y, tileSize);

            if(oldLocationX == newLocationX && oldLocationY == newLocationY) {
                runner.setCoordinateX(x);
                runner.setCoordinateY(y);
            }
            else if(Math.abs(oldLocationX - newLocationX) <= 1 && Math.abs(oldLocationY - newLocationY) <= 1){
                boolean moved = false;
                if(newLocationX > oldLocationX) {
                    moved = runner.move(maze, Directions.EAST);
                }
                else if(newLocationX < oldLocationX) {
                    moved = runner.move(maze, Directions.WEST);
                }

                if(newLocationY > oldLocationY) {
                    moved = runner.move(maze, Directions.SOUTH);
                }
                else if(newLocationY < oldLocationY) {
                    moved = runner.move(maze, Directions.NORTH);
                }
                if(moved) {
                    runner.setCoordinateX(x);
                    runner.setCoordinateY(y);
                }
            }
        }
    }

    private class KeyInputHandler extends KeyAdapter {
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                if(runner.move(maze, Directions.WEST)) {
                    runner.setCoordinateX(runner.getCoordinateX() - maze.getMazeTileWidthAndHeight());
                }
            }
            if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                if(runner.move(maze, Directions.EAST)) {
                    runner.setCoordinateX(runner.getCoordinateX() + maze.getMazeTileWidthAndHeight());
                }
            }
            if (e.getKeyCode() == KeyEvent.VK_UP) {
                if(runner.move(maze, Directions.NORTH)) {
                    runner.setCoordinateY(runner.getCoordinateY() - maze.getMazeTileWidthAndHeight());
                }
            }
            if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                if(runner.move(maze, Directions.SOUTH)) {
                    runner.setCoordinateY(runner.getCoordinateY() + maze.getMazeTileWidthAndHeight());
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