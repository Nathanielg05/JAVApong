import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import javax.swing.*;

//Nathaniel Gayle
//JAVA Pong

public class Pong extends JPanel {
    private final Player player;
    private final PlayerTwo opponent;
    private final ArrayList<Ball> balls;
    private static int scoreplayer = 0;
    private static int scoreopponent = 0;
    private ImageIcon background;
    
    public Pong() {
        player = new Player();
        opponent = new PlayerTwo();
        balls = new ArrayList<>();

        KeyListener listener = new KeyListener() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    player.setDirection(-1);
                } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    player.setDirection(1);
                }
                if (e.getKeyCode() == KeyEvent.VK_A) {
                    opponent.setDirection(-1);
                } else if (e.getKeyCode() == KeyEvent.VK_D) {
                    opponent.setDirection(1);
                }
            }

            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_A) {
                    opponent.setDirection(0);
                } else if (e.getKeyCode() == KeyEvent.VK_D) {
                    opponent.setDirection(0);
                }
                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    player.setDirection(0);
                } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    player.setDirection(0);
                }
            }

            @Override
            public void keyTyped(KeyEvent e) {}
        };

        addKeyListener(listener);
        setFocusable(true);
    }

    public void update() {
        player.move();
        opponent.move();

        

        for (Ball ball : balls) {
            ball.move();
            int playerCollision = player.catchBall(ball);
            int opponentCollision = opponent.catchBall(ball);

            // Check player collision
            if (playerCollision == 1 || opponentCollision == 1) { // Top/bottom collision
                ball.yVelocity = -ball.yVelocity;
            } else if (playerCollision == 2 || opponentCollision == 2) { // Left/right collision
                ball.xVelocity = -ball.xVelocity;
            }
        }

        // Add a new ball if the conditions are met
        if (Math.random() < 0.01 && balls.size() < 1) {
            balls.add(new Ball(5, 5));
        }
    }

    public static void increasePlayerScore() { //internal score for player
        scoreplayer++;
    }
    public static void increaseOpponentScore() { //internal score for opponent
        scoreopponent++;
    }


    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;

        background = new ImageIcon(getClass().getResource("/Pong.png"));
        g.drawImage(background.getImage(), 0, 190, null);

        player.draw(g2d);
        opponent.draw(g2d);
        for (Ball ball : balls) {
            ball.draw(g2d);
        }
        g2d.drawString("Score: " + scoreplayer, 10, 210);
        g2d.drawString("Score: " + scoreopponent, 10, 180);
    }

    public static void main(String[] args) throws InterruptedException {
        JFrame frame = new JFrame("Pong");
        Pong game = new Pong();
        frame.add(game);
        // double size window to make game more visually friendly
        game.setBackground(Color.BLACK);
        frame.setSize(400, 400);



        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        while (true) {
            game.repaint();
            game.update();
            Thread.sleep(30);
        }
    }
}
