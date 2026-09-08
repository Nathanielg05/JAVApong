import java.awt.Color;
import java.awt.Graphics2D;

public class Ball {
    private int x = 200;
    private int y = 100;
    private int size = 20;
    public int xVelocity, yVelocity;

    private long respawnTime;
    private boolean timerStarted;
    private boolean missedTop;

    public Ball(int xVelocity, int yVelocity) {
        this.xVelocity = xVelocity;
        this.yVelocity = yVelocity;
    }

    public void move() {
        if (!timerStarted) {
            this.y += yVelocity;
            this.x += xVelocity;
            if (this.y >= 390 || y <= -30) {
                timerStarted = true;
                respawnTime = System.currentTimeMillis() + 3000;
            }
            //reversing the direction for hitting the sides
            if (this.x >= 390) {
                xVelocity = -xVelocity;
            } else if (x <= 0) {
                xVelocity = -xVelocity;
            }
            if (this.y >= 390){ //check to see if the ball went through the top
                missedTop = true;
            } else {
                missedTop = false;
            }

        }else{
            if (System.currentTimeMillis() >= respawnTime) {
                resetPosition(); // Reset the ball's position
                timerStarted = false; // Reset the timer
            }
        }
    }

    public void draw(Graphics2D g) {
        g.setColor(Color.WHITE);
        g.fillOval(this.x, this.y, this.size, this.size);
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public void resetPosition() {
       if(missedTop){
           this.x = 200;
           this.y = 100;
           Pong.increaseOpponentScore();
       } else if (!missedTop) {
           this.x = 200;
           this.y = 300;
           Pong.increasePlayerScore();
       }
    }
}
