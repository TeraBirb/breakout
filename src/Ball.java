import objectdraw.*;
import java.awt.*;
// import java.util.Random; Apparently the random object doesnt work too well with other IDEs. Refactored with Math.random
import java.lang.Math;

public class Ball extends ActiveObject {

    private FilledOval ball;
    private DrawingCanvas canvas;
    //private Paddle paddle;
    public FilledRect paddle;
    public FilledRect brick;
    public Location scoreLives;
    public Text stats;

//    private Random rng; From old code, Random object problem
    private double xSpeed, ySpeed;

    public Ball(FilledRect rect, Text text, Location sL, DrawingCanvas aCanvas) {
        paddle = rect;
        stats = text;
        scoreLives = sL;

        canvas = aCanvas;
        // rng = new Random(); !! From old code.
        //xSpeed = rng.nextDouble(BreakoutProgram.VELOCITY_X_MIN, BreakoutProgram.VELOCITY_X_MAX);
        xSpeed = (Math.random() * (BreakoutProgram.VELOCITY_X_MAX - BreakoutProgram.VELOCITY_X_MIN))
                + BreakoutProgram.VELOCITY_X_MIN;
        ySpeed = BreakoutProgram.VELOCITY_Y;

        //int negativeX = rng.nextInt(2);
        int negativeX = (int)(Math.round(Math.random()));
        if (negativeX == 1) {
            xSpeed *= -1;
        }

        ball = new FilledOval( BreakoutProgram.CANVAS_WIDTH / 2, BreakoutProgram.CANVAS_HEIGHT / 2,
                BreakoutProgram.BALL_RADIUS, BreakoutProgram.BALL_RADIUS, canvas);

        start();


    }


    public double getX() {
        return ball.getX();
    }
    public double getY() {
        return ball.getY();
    }

    public void moveTo(double x, double y) { ball.moveTo(x, y); }
    public void move(double x, double y) { ball.move(x, y); }

    public void reverseX() {
        xSpeed *= -1;
    }

    public void setX(int x) {
        xSpeed *= x;
    }
    public void setY(int y) { ySpeed *= y; }

    public boolean overlaps(FilledRect paddle) {
        if (ball.overlaps(paddle)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean overlapsPaddle() {
        if (ball.overlaps(paddle)) {
            return true;
        } else {
            return false;
        }
    }

    public void stopGame() {
        xSpeed = 0;
        ySpeed = 0;
        ball.hide();
        paddle.hide();
    }

    public void run() {
        while (true) {

            //reversing direction by multiplying the speed of X and Y with -1 since it produces desired results

            ball.move(xSpeed, ySpeed);

            if (overlapsPaddle() || ball.getY() < 0 ) {
                ySpeed = ySpeed * -1;
            }
            if (ball.getX() + BreakoutProgram.BALL_RADIUS >= canvas.getWidth() || ball.getX() <= 0) {
                xSpeed = xSpeed * -1;
            }

            if (ball.getY() + BreakoutProgram.BALL_RADIUS >= canvas.getHeight()) {
                scoreLives.translate(0, -1);
                stats.setText("Score: " + scoreLives.getX() + ", Lives: " + scoreLives.getY());

                xSpeed = (Math.random() * (BreakoutProgram.VELOCITY_X_MAX - BreakoutProgram.VELOCITY_X_MIN))
                        + BreakoutProgram.VELOCITY_X_MIN;

                int negativeX = (int)(Math.round(Math.random()));
                if (negativeX == 1) {
                    xSpeed *= -1;
                }

                ball.moveTo(BreakoutProgram.CANVAS_WIDTH / 2, BreakoutProgram.CANVAS_HEIGHT / 2);

            }

            if (scoreLives.getY() == 0) {
                stopGame();
                new Text("GAME OVER", BreakoutProgram.CANVAS_WIDTH / 2.5, BreakoutProgram.CANVAS_HEIGHT / 2, canvas);
            }

            pause(BreakoutProgram.DELAY);

        }
    }
}