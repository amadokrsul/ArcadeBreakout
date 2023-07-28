public class Platform implements DrawListener {
    private double xPos; // x position of Platform
    private double yPos; // y position of Platform
    private double velocity; // horizontal velocity of Platform

    // takes three doubles as parameters. creates a Platform object with the given
    // x position, y position, and horizontal velocity.
    public Platform(double xPos, double yPos, double velocity) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.velocity = velocity;
    }

    // returns the x position of the Platform.
    public double getXPos() {
        return this.xPos;
    }

    // returns the y position of the Platform.
    public double getYPos() {
        return this.yPos;
    }

    // Takes a double as a parameter. Sets the xPos of the Platform to x.
    public void setXPos(double x) {
        this.xPos = x;
    }

    // Shifts the Platform to the right by an amount 'velocity'.
    public void moveRight() {
        xPos += velocity;
    }

    // Shifts the Platform to the left by an amount 'velocity'
    public void moveLeft() {
        xPos -= velocity;
    }

    // Takes a keystroke as a parameter. Detects if the user has pressed 'd' or 'a'
    // with DrawListener and moves the Platform accordingly.
    public void keyTyped(char c) {
        if (c == 'd') {
            moveRight();
        }
        if (c == 'a') {
            moveLeft();
        }
    }

    // Takes the mouse cursor's x and y position as a parameter. Detects if the user
    // has dragged their mouse with DrawListener, moving the Platform to the cursor's
    // location.
    public void mouseDragged(double x, double y) {
        xPos = x;
    }

    // Declares and initializes a Platform object. Tests most of this class'
    // methods with the Platform object.
    public static void main(String[] args) {
        // Creates a Platform object position at (5, 5) with velocity 5.
        Platform platform = new Platform(5, 5, 5);

        StdOut.println("Platform x position: " + platform.getXPos());
        StdOut.println("Brick y position: " + platform.getYPos());
        platform.setXPos(10);
        StdOut.println("Platform x position now: " + platform.getXPos());
        platform.moveRight();
        platform.moveRight();
        StdOut.println("Platform x position after moving right twice: "
                               + platform.getXPos());
        platform.moveLeft();
        StdOut.println("Platform x position after moving left once: "
                               + platform.getXPos());

    }
}
