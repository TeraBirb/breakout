import objectdraw.*;
import java.awt.*;

public class BreakoutController extends WindowController {

    public static final int BRICK_X_OFFSET = (int) ((BreakoutProgram.CANVAS_WIDTH) -
            (BreakoutProgram.BRICK_WIDTH * BreakoutProgram.NBRICK_ROWS) -
            (BreakoutProgram.BRICK_SEP * (BreakoutProgram.NBRICK_ROWS - 1)))
            / 2;

    public FilledRect paddle;
    public Ball ball;
    public Text stats;
    public Location scoreLives = new Location(0, BreakoutProgram.NTURNS); // Passing by reference is hard...
    private boolean paddleExists = false;
    private boolean ballExists = true;

    public void begin() {

        // score and lives display on top left
        stats = new Text("Score: 0, Lives: 3" , BreakoutProgram.CANVAS_WIDTH / 15, BreakoutProgram.CANVAS_HEIGHT / 12, canvas);
        stats.setFont(BreakoutProgram.SCREEN_FONT);

        if (!paddleExists) {
            paddle = new FilledRect(BreakoutProgram.CANVAS_HEIGHT * 1.2,
                    BreakoutProgram.CANVAS_HEIGHT - BreakoutProgram.PADDLE_Y_OFFSET,
                    BreakoutProgram.PADDLE_WIDTH, BreakoutProgram.PADDLE_HEIGHT, canvas);
            paddleExists = true;

        }

        ball = new Ball(paddle, stats, scoreLives, canvas);

        // BRICK
        Location brickPoint = new Location(BRICK_X_OFFSET, BreakoutProgram.BRICK_Y_OFFSET);
        Color color = Color.red;  // assignment of red is just for initialization

        for (int i = 1; i < BreakoutProgram.NBRICK_COLUMNS + 1; i++) {

            if (i <= 2 || i == 11 || i == 12) {
                color = Color.red;
            } else if (i <= 4 || i == 13 || i == 14) {
                color = Color.orange;
            } else if (i <= 6|| i == 15 || i == 16) {
                color = Color.yellow;
            } else if (i <= 8 || i == 17 || i == 18) {
                color = Color.green;
            } else if (i <= 10 || i == 19 || i == 20) {
                color = Color.cyan;
            }

            // creating a row of 10 bricks, spaced apart by brick_width + separation
            for (int j = 0; j < BreakoutProgram.NBRICK_ROWS; j++) {
                new Brick(brickPoint, ball, ballExists, stats, scoreLives, canvas, color);
                brickPoint.translate(BreakoutProgram.BRICK_WIDTH + BreakoutProgram.BRICK_SEP, 0);
            }

            // reset brick point then increment y
            brickPoint = new Location(BRICK_X_OFFSET, BreakoutProgram.BRICK_Y_OFFSET + (10 * i));
        }

        if (!paddleExists) {
            paddle = new FilledRect(BreakoutProgram.CANVAS_HEIGHT * 1.2,
                    BreakoutProgram.CANVAS_HEIGHT - BreakoutProgram.PADDLE_Y_OFFSET,
                    BreakoutProgram.PADDLE_WIDTH, BreakoutProgram.PADDLE_HEIGHT, canvas);
            paddleExists = true;
        }

    }

    public void onMouseClick(Location point) {
        if (paddleExists) {
            paddle.moveTo(point.getX() - BreakoutProgram.PADDLE_WIDTH / 2,
                    BreakoutProgram.CANVAS_HEIGHT - BreakoutProgram.PADDLE_Y_OFFSET);
        }
    }

    public void onMouseDrag(Location point) {
        if (paddleExists) {
            paddle.moveTo(point.getX() - BreakoutProgram.PADDLE_WIDTH / 2, BreakoutProgram.CANVAS_HEIGHT - BreakoutProgram.PADDLE_Y_OFFSET);
            if (paddle.getX() <= 0) {
                paddle.moveTo(0.65, BreakoutProgram.CANVAS_HEIGHT - BreakoutProgram.PADDLE_Y_OFFSET);
            } else if (paddle.getX() >= BreakoutProgram.CANVAS_WIDTH - BreakoutProgram.PADDLE_WIDTH) {
                paddle.moveTo(BreakoutProgram.CANVAS_WIDTH - BreakoutProgram.PADDLE_WIDTH - 0.65, BreakoutProgram.CANVAS_HEIGHT - BreakoutProgram.PADDLE_Y_OFFSET);
            }

            if (ball.overlaps(paddle)) {
                ball.setY(-1);
            }
        }
    }

    public static void main (String[]args){
        new BreakoutController().startController(BreakoutProgram.CANVAS_WIDTH, BreakoutProgram.CANVAS_HEIGHT);
    }

}
