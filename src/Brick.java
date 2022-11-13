import objectdraw.*;
import java.awt.*;

public class Brick extends ActiveObject {

    private FilledRect brick;
    private DrawingCanvas canvas;

    public Location scoreLives;

    public Ball ball;
    public boolean ballExists;
    public Text stats;
    public static final Location OBJECT_HELL = new Location(-300, -300);
    public static final int INIT_BRICK_COUNT = 100;

    private FilledRect colliderLeft;
    private FilledRect colliderRight;


    // one filledRect for left and right. if collider rect hits,

    public Brick(Location point, Ball b, boolean aBallExists, Text text, Location sL, DrawingCanvas aCanvas, Color color) {

        ballExists = aBallExists;
        ball = b;
        stats = text;
        scoreLives = sL;
        canvas = aCanvas;

        brick = new FilledRect(point, BreakoutProgram.BRICK_WIDTH, BreakoutProgram.BRICK_HEIGHT, color, canvas);
        colliderLeft = new FilledRect(point, 1, BreakoutProgram.BRICK_HEIGHT, canvas);
        colliderLeft.move(1, 0);
        colliderLeft.sendToBack();

        colliderRight = new FilledRect(point, 1, BreakoutProgram.BRICK_HEIGHT, canvas);
        colliderRight.move(BreakoutProgram.BRICK_WIDTH - 1, 0);
        colliderRight.sendToBack();


        start();
    }

    public boolean overlapsBall() {
        if (brick.getY() <= ball.getY()) {
            System.out.println("TRUE");
            return true;
        } else {
            System.out.println("FALSE");
            return false;
        }
    }

    public static void incrScore(Location x) {
        x.translate(1, 0);
    }

    public void run() {
        while (ballExists) {

            if (ball.overlaps(brick)) {
                ball.setY(-1);
                brick.moveTo(OBJECT_HELL);
                colliderLeft.moveTo(OBJECT_HELL);
                colliderRight.moveTo(OBJECT_HELL);

                scoreLives.translate(1,0);
                stats.setText("Score: " + scoreLives.getX() + ", Lives: " + scoreLives.getY());
            }

            if (ball.overlaps(colliderLeft) || ball.overlaps(colliderRight)) {
                ball.setX(-1);
                brick.moveTo(OBJECT_HELL);
                colliderLeft.moveTo(OBJECT_HELL);
                colliderRight.moveTo(OBJECT_HELL);

                scoreLives.translate(1,0);
                stats.setText("Score + " + scoreLives.getX() + ", Lives: " + scoreLives.getY());
            }

                if(scoreLives.getX() == 100) {
                    ball.stopGame();
                    new Text("YOU WIN!", BreakoutProgram.CANVAS_WIDTH / 2.5, BreakoutProgram.CANVAS_HEIGHT / 2, canvas);
                    break;
                }

            pause(BreakoutProgram.DELAY);

        }
    }
}

