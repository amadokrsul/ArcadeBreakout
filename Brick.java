public class Brick {
    private double xPos; // x position of Brick
    private double yPos; // y position of Brick
    private boolean isDestroyed; // stores if the Brick has been destroyed

    // takes two doubles as parameters. creates a Brick object with given position
    public Brick(double xPos, double yPos) {
        this.xPos = xPos;
        this.yPos = yPos;
        isDestroyed = false;
    }

    // returns the x position of the Brick
    public double getXPos() {
        return xPos;
    }

    // returns the y position of the Brick
    public double getYPos() {
        return yPos;
    }

    // sets isDestroyed to true;
    public void setDestroyed() {
        isDestroyed = true;
    }

    // returns whether the Brick has been destroyed
    public boolean hasBeenDestroyed() {
        return isDestroyed;
    }

    // Declares and initializes a Brick object. Tests this class' methods with
    // this Brick object.
    public static void main(String[] args) {
        // Creates a Brick object positioned at (5, 5)
        Brick brick = new Brick(5, 5);
        StdOut.println("Brick x position: " + brick.getXPos());
        StdOut.println("Brick y position: " + brick.getYPos());
        StdOut.println("Has Brick been destroyed? " + brick.hasBeenDestroyed());
        brick.setDestroyed();
        StdOut.println("Has Brick been destroyed now? " + brick.hasBeenDestroyed());
    }
}
