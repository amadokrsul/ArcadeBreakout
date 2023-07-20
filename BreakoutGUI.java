import javax.swing.WindowConstants;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;

// implements the game's GUI by using the Draw and DrawListener libraries
public class BreakoutGUI implements DrawListener {
    private static final String GAME_TITLE = "Arcade: Breakout";
    // gets the user's screen resolution using the Toolkit library
    private static final Dimension RESOLUTION = Toolkit.getDefaultToolkit().getScreenSize();
    private static final double SCREEN_WIDTH = RESOLUTION.getWidth();
    private static final double SCREEN_HEIGHT = RESOLUTION.getHeight();
    private static final double CANVAS_SIZE = SCREEN_HEIGHT / 1.3;
    private static final Color BACKGROUND_COLOR = Draw.BLACK;

    private static final double PLATFORM_XPOS = 0.5;
    private static final double PLATFORM_YPOS = 0.1;
    private static final double PLATFORM_VELOCITY = 0.03;
    private static final double PLATFORM_WIDTH = 0.040;
    private static final double PLATFORM_HEIGHT = 0.005;
    private static final Color PLATFORM_COLOR = Draw.WHITE;
    private static final String COLLISION_SOUND = "pipebang.wav";

    private static final double BALL_XPOS = 0.5;
    private static final double BALL_YPOS = 0.3;
    private static final double BALL_RADIUS = 0.01;
    private static final Color BALL_COLOR = Draw.PRINCETON_ORANGE;

    private static final int NUM_BRICKS = 30;
    private static final int NUM_BRICKS_PER_ROW = 6;
    private static final int NUM_BRICKS_PER_COL = 5;
    private static final double BRICK_WIDTH = 1.0 / NUM_BRICKS_PER_ROW;
    private static final double BRICK_HEIGHT = 0.1;
    private int bricksDestroyed = 0;

    private Draw userInterface; // main Game window
    private Platform platform;  // main Platform object
    private Ball ball;          // main Ball object
    private Brick[] bricks;     //

    // creates a BreakoutGUI object using draw
    public BreakoutGUI() {
        userInterface = new Draw();
        userInterface.enableDoubleBuffering();
        // adapts canvas size to fit the dimensions of the user's screen
        userInterface.setCanvasSize((int) CANVAS_SIZE,
                                    (int) CANVAS_SIZE);
        userInterface.setLocationOnScreen((int) (SCREEN_WIDTH / 2 - CANVAS_SIZE / 2),
                                          (int) (SCREEN_HEIGHT / 2 - CANVAS_SIZE / 2));
        userInterface.setTitle(GAME_TITLE);
        userInterface.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        userInterface.addListener(this);

        // creates a new platform object at the specified position
        platform = new Platform(PLATFORM_XPOS, PLATFORM_YPOS, PLATFORM_VELOCITY);
        userInterface.addListener(platform);

        // creates a new ball object at the specified initial position and radius
        ball = new Ball(BALL_XPOS, BALL_YPOS, BALL_RADIUS);

        // creates a new array of brick objects of the specified length
        bricks = new Brick[NUM_BRICKS];
        // sets locations for brick objects, going from top left to bottom right
        double yPos = 1.0 - BRICK_HEIGHT / 2;
        for (int i = 0; i < NUM_BRICKS_PER_COL; i++) {
            double xPos = 0.0 + BRICK_WIDTH / 2;
            for (int j = 0; j < NUM_BRICKS_PER_ROW; j++) {
                bricks[i * NUM_BRICKS_PER_ROW + j] = new Brick(xPos, yPos);
                xPos += BRICK_WIDTH;
            }
            yPos -= BRICK_HEIGHT;
        }
    }

    // creates a new Menu object, continues showing until game is started
    public static void drawMainMenu() {
        Menu gameMenu = new Menu((int) SCREEN_WIDTH, (int) SCREEN_HEIGHT,
                                 (int) CANVAS_SIZE, GAME_TITLE);
        while (true) {
            StdOut.print();
            if (gameMenu.getGameStart()) {
                return;
            }
        }
    }

    // draws a black background in the Draw window
    public void drawBackground() {
        userInterface.setPenColor(BACKGROUND_COLOR);
        userInterface.filledSquare(0.5, 0.5, 0.5);
    }

    // draws the platform object into the Draw window
    public void drawPlatform() {
        double xPos = platform.getXPos();
        double yPos = platform.getYPos();
        userInterface.setPenColor(PLATFORM_COLOR);
        userInterface.filledRectangle(xPos, yPos, PLATFORM_WIDTH, PLATFORM_HEIGHT);
    }

    // draws the ball object into the draw window
    public void drawBall() {
        double xPos = ball.getXPos();
        double yPos = ball.getYPos();
        userInterface.setPenColor(BALL_COLOR);
        userInterface.filledCircle(xPos, yPos, BALL_RADIUS);
    }

    // draws the brick objects into the draw window
    public void drawBricks() {
        // iterates through the array of brick objects
        // only draws bricks that have not been destroyed yet
        for (int i = 0; i < NUM_BRICKS; i++) {
            if (bricks[i].hasBeenDestroyed()) {
                continue;
            }

            userInterface.filledRectangle(bricks[i].getXPos(), bricks[i].getYPos(),
                                          BRICK_WIDTH / 2, BRICK_HEIGHT / 2);
        }
    }

    // checks if the ball has collided with a brick
    public void checkBrickCollisions() {
        double ballXPos = ball.getXPos();
        double ballYPos = ball.getYPos();
        double ballXVel = ball.getXVel();
        double ballYVel = ball.getYVel();

        // iterates through each brick object in the array
        for (int i = 0; i < NUM_BRICKS; i++) {

            // if the brick has already been destroyed, does nothing
            if (bricks[i].hasBeenDestroyed()) {
                continue;
            }

            // if the brick has not yet been destroyed, determines whether the
            // ball is currently colliding with the brick
            if (Math.abs(bricks[i].getXPos() - ballXPos) <
                    BRICK_WIDTH / 2 + BALL_RADIUS &&
                    Math.abs(bricks[i].getYPos() - ballYPos) <
                            BRICK_HEIGHT / 2 + BALL_RADIUS) {
                // if the ball is colliding with the top or bottom of a brick,
                // adjusts x velocity accordingly, marks brick as destroyed, plays sound
                if (Math.abs(bricks[i].getXPos() - ballXPos) / BRICK_WIDTH >
                        Math.abs(bricks[i].getYPos() - ballYPos) / BRICK_HEIGHT) {
                    ball.setXVel(-ballXVel);
                    bricksDestroyed++;
                    bricks[i].setDestroyed();
                    StdAudio.playInBackground(COLLISION_SOUND);
                }
                // if the ball is colliding with the left or right side of a brick,
                // adjusts y velocity accordingly, marks brick as destroyed, plays sound
                else {
                    ball.setYVel(-ballYVel);
                    bricksDestroyed++;
                    bricks[i].setDestroyed();
                    StdAudio.playInBackground(COLLISION_SOUND);
                }
            }
        }
    }

    // checks if the platform has hit the edge of the draw window,
    // and if the ball has collided with the platform
    public void checkPlatformCollisions() {
        double platformXPos = platform.getXPos();
        // ensures that the platform does not go beyond the edges of the draw window
        if (platformXPos + PLATFORM_WIDTH >= 1.0) {
            platform.setXPos(1.0 - PLATFORM_WIDTH);
        }
        if (platformXPos - PLATFORM_WIDTH <= 0.0) {
            platform.setXPos(0.0 + PLATFORM_WIDTH);
        }

        double ballXPos = ball.getXPos();
        double ballYPos = ball.getYPos();
        // first determines whether the ball is at the same vertical position
        // as the platform;
        // also includes the getYVel method in order to avoid a bug that caused
        // the ball to go through bricks without destroying them
        if (ballYPos - BALL_RADIUS <= PLATFORM_YPOS + PLATFORM_HEIGHT - ball.getYVel()
                && ballYPos - BALL_RADIUS >= PLATFORM_YPOS - PLATFORM_HEIGHT + ball.getYVel()) {
            // determines whether the ball has hit the right side of the platform
            // uses a buffer of 0.005 so that the ball does not go through the platform
            if (ballXPos < platformXPos + PLATFORM_WIDTH + 0.005 && ballXPos > platformXPos) {
                // slightly increases the vertical velocity
                ball.setYVel(-ball.getYVel() + 0.001);
                // deflects the ball to the right at a random angle
                ball.setXVel(0.01 * StdRandom.uniformDouble());
                // ensures that the ball does not go through the platform
                ball.setYPos(PLATFORM_YPOS + PLATFORM_HEIGHT + BALL_RADIUS);
                // plays collision sound to standard audio
                StdAudio.playInBackground(COLLISION_SOUND);
            }
            // determines whether the ball has hit the right side of the platform
            // uses a buffer of 0.005 so that the ball does not go through the platform
            if (ballXPos > platformXPos - PLATFORM_WIDTH - 0.005 && ballXPos < platformXPos) {
                // slightly increases the vertical velocity
                ball.setYVel(-ball.getYVel() + 0.001);
                // deflects the ball to the right at a random angle
                ball.setXVel(-0.01 * StdRandom.uniformDouble());
                // ensures that the ball does not go through the platform
                ball.setYPos(PLATFORM_YPOS + PLATFORM_HEIGHT + BALL_RADIUS);
                // plays collision sound to standard audio
                StdAudio.playInBackground(COLLISION_SOUND);
            }
        }
    }

    // determines whether the game has ended, returns a boolean
    public boolean isGameOver() {
        // if the ball hits the bottom of the screen, play the defeat sound, return true
        if (ball.getYPos() - BALL_RADIUS <= 0.0) {
            StdAudio.play("defeat.wav");
            return true;
        }

        // if all bricks have been destroyed, play the victory sound, return true
        if (bricksDestroyed == NUM_BRICKS) {
            StdAudio.play("victory.wav");
            return true;
        }
        // if the game has not been won nor lost yet, return false
        return false;
    }

    // moves the ball and shows it in the Draw window
    public void animate() {
        ball.move();
        userInterface.show();
        userInterface.pause(20);
    }

    // takes keystroke as a parameter, exits the game if 'q'
    public void keyTyped(char c) {
        if (c == 'q') {
            System.exit(0);
        }
    }
}
