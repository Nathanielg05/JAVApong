import java.awt.Color;
import java.awt.Graphics2D;

public class PlayerTwo {
    private int x = 180;
    private int y = 10;
    private int direction = 0;

    public PlayerTwo() {
    }

    public void move() {
        this.x += this.direction * 5;
        if (this.x < 0) {
            this.x = 0;
        }

        if (this.x > 350) {
            this.x = 350;
        }

    }

    public void draw(Graphics2D g) {
        g.setColor(Color.WHITE);
        g.fillRect(this.x, this.y, 70, 10);
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }


    public int catchBall(Ball ball) {
        int ballRadius = 20; // Assuming radius of 20 for the ball
        int ballTopY = ball.getY() - ballRadius; // Top of the ball
        int ballBottomY = ball.getY() + ballRadius; // Bottom of the ball
        int ballLeftX = ball.getX() - ballRadius; // Left of the ball
        int ballRightX = ball.getX() + ballRadius; // Right of the ball

        boolean hitTopOrBottom = ballBottomY >= this.y && ballTopY <= this.y - 10;
        boolean hitLeftOrRight = ballRightX >= this.x && ballLeftX <= this.x + 50;

        if (hitTopOrBottom && hitLeftOrRight) {
            // Calculate distances to each side to determine collision type
            int distanceToTop = Math.abs(ballBottomY - this.y);
            int distanceToBottom = Math.abs(ballTopY - (this.y - 10));
            int distanceToLeft = Math.abs(ballRightX - this.x);
            int distanceToRight = Math.abs(ballLeftX - (this.x + 50));

            // Determine the closest side and return corresponding collision type
            if (distanceToTop < distanceToBottom && distanceToTop < distanceToLeft && distanceToTop < distanceToRight) {
                return 1; // Top or bottom collision
            } else if (distanceToBottom < distanceToTop && distanceToBottom < distanceToLeft && distanceToBottom < distanceToRight) {
                return 1; // Top or bottom collision
            } else if (distanceToLeft < distanceToTop && distanceToLeft < distanceToBottom && distanceToLeft < distanceToRight) {
                return 2; // Left or right collision
            } else if (distanceToRight < distanceToTop && distanceToRight < distanceToBottom && distanceToRight < distanceToLeft) {
                return 2; // Left or right collision
            }
        }
        return 0; // No collision
    }
}