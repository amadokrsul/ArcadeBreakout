public class ArcadeBreakout {
    public static void main(String[] args) {
        // Draws the Main Menu window for Arcade: Breakout
        BreakoutGUI.drawMainMenu();

        // Declares and initializes a BreakoutGUI object.
        BreakoutGUI breakout = new BreakoutGUI();

        // Main game loop that animates each object and detects collisions.
        while (!breakout.isGameOver()) {
            breakout.drawBackground();
            breakout.checkPlatformCollisions();
            breakout.drawPlatform();
            breakout.drawBall();
            breakout.checkBrickCollisions();
            breakout.drawBricks();
            breakout.animate();
        }
    }
}
