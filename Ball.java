public class Ball {
    private static final String COLLISION_SOUND = "pipebang.wav";

    private double xPos; // x position of Ball
    private double yPos; // y position of Ball
    private double radius; // radius of Ball
    private double xVel; // x velocity of Ball
    private double yVel; // y velocity of Ball

    // Takes three double as parameters. creates a Ball object with the given
    // x position, y position, and radius
    public Ball(double xPos, double yPos, double radius) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.radius = radius;
        // chooses the initial x velocity randomly.
        this.xVel = 0.01 * StdRandom.uniformDouble() - 0.005;
        // sets initial y velocity to an arbitrary negative number.
        this.yVel = -0.005;
    }

    // returns the x position of the Ball
    public double getXPos() {
        return xPos;
    }

    // returns the y position of the Ball
    public double getYPos() {
        return yPos;
    }

    // takes a double as a parameter. Sets the Ball's y position to y.
    public void setYPos(double y) {
        yPos = y;
    }

    // returns the x velocity of the Ball
    public double getXVel() {
        return xVel;
    }

    // returns the y velocity of the Ball
    public double getYVel() {
        return yVel;
    }

    // Takes a double as a parameter. Sets the Ball's x velocity to x.
    public void setXVel(double x) {
        xVel = x;
    }

    // Takes a double as a parameter. Sets the Ball's y velocity to y.
    public void setYVel(double y) {
        yVel = y;
    }

    // Checks if the Ball collides with the edge of the window. Adjusts velocities
    // and plays an audio effect accordingly.
    public void checkWallCollisions() {
        // Checks collision with right border
        if (xPos + radius >= 1.0) {
            xPos = 1.0 - radius;
            xVel = -xVel;
            StdAudio.playInBackground(COLLISION_SOUND);
        }
        // Checks collision with left border.
        if (xPos - radius <= 0.0) {
            xPos = 0.0 + radius;
            xVel = -xVel;
            StdAudio.playInBackground(COLLISION_SOUND);
        }
        // Checks collision with top border.
        if (yPos + radius >= 1.0) {
            yPos = 1.0 - radius;
            yVel = -yVel;
            StdAudio.playInBackground(COLLISION_SOUND);
        }
    }

    // increments the position of the ball by the velocity, checks for wall collisions
    public void move() {
        checkWallCollisions();
        xPos += xVel;
        yPos += yVel;
    }

    // Declares and initializes a Ball object. Tests this class' methods
    // with this Ball object.
    public static void main(String[] args) {
        Ball ball = new Ball(0.5, 0.3, 0.01);
        ball.setXVel(0.01);
        StdOut.println("Ball x velocity: " + ball.getXVel());
        ball.setYVel(-0.01);
        StdOut.println("Ball y velocity: " + ball.getYVel());
        ball.move();
        ball.checkWallCollisions();
        StdOut.println("Ball x position: " + ball.getXPos());
        ball.setYPos(0.4);
        StdOut.println("Ball y position: " + ball.getYPos());
    }
}
