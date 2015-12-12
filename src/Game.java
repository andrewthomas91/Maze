import javax.swing.JFrame;
import java.awt.Graphics;
import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Game extends JFrame implements Runnable {
    boolean gameRunning = true;
    int currantLocationX = 0;
    int currantLocationY = 0;
    JFrame frame = new JFrame("Maze");
    MazeGenerator maze;
    private Display display;
    Thread bounceBall = null;//Thread variable which will be taking care of the ball animation

    Image offscreenImage;//The image off screen that is used for double buffering
    Graphics offscr;//What is drawn onto that is then used to create the off screen image

    public Game() {
        this.setSize(500,500);
        this.setTitle("Maze");//Title of application
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBackground(Color.white);
        this.setVisible(true);
        offscreenImage = createImage(this.getWidth(), this.getHeight());//Creates the off screen image using this frames dimensions
        offscr = offscreenImage.getGraphics();
        display = new Display(offscreenImage);

        bounceBall = new Thread(this);
        bounceBall.start();//Starts the bounceBall thread, which tells the ball to move and updates the graphics
    }

    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(offscreenImage, 0, 0, this);//Draws the off screen image to the screen (double buffering)
    }

    public void run() {
        maze = new MazeGenerator(10, 10);
        offscr = display.drawMaze(maze);
        this.repaint();

//        while(gameRunning){//This thread loops until the application is closed
//                try{
//                    Thread.sleep(50);//Sleeps for 20 milliseconds
//                }
//                catch(Exception e){
//                    System.out.println("Error in running thread " + e);
//                }
//        }
    }
}