import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Implements a Menu object and interface using the Swing and AWT libraries.
public class Menu implements ActionListener {
    private static final int TITLE_WIDTH = 600;
    private static final int TITLE_HEIGHT = 200;
    private static final int BUTTON_WIDTH = 300;
    private static final int BUTTON_HEIGHT = 100;
    private static final int AUTHOR_WIDTH = 300;
    private static final int AUTHOR_HEIGHT = 200;
    private static final String FONT = "Sans-Serif";

    private JFrame menu;           // JFrame window object
    private boolean gameStart;     // Stores whether player has hit "PLAY"

    // Takes three integers and a string as parameters. Creates a Menu object
    // by initializing a JFrame window, customizing it, and adding multiple
    // Java Swing components to it.
    public Menu(int screenWidth, int screenHeight, int canvasSize, String title) {
        // Initializes JFrame window with given title.
        menu = new JFrame(title);
        // Sets the location to the middle of the user's screen.
        menu.setLocation(screenWidth / 2 - canvasSize / 2,
                         screenHeight / 2 - canvasSize / 2);
        // Sets the size of the drawing window to the game's size.
        menu.setSize(canvasSize, canvasSize);
        // Makes the drawing window impossible to resize.
        menu.setResizable(false);
        menu.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        gameStart = false;

        // Creates and initializes a JPanel object.
        JPanel gamePanel = new JPanel(null);
        gamePanel.setBackground(Draw.BLACK);

        // Creates and initializes a JLabel object with given title.
        JLabel gameTitle = new JLabel(title);
        // Positions the gameTitle object slightly below the top-center of the window
        gameTitle.setHorizontalAlignment(SwingConstants.CENTER);
        gameTitle.setBounds((canvasSize / 2) - (TITLE_WIDTH / 2),
                            (canvasSize / 8) - (TITLE_HEIGHT / 8),
                            TITLE_WIDTH, TITLE_HEIGHT);
        // Sets the font and the color of the JLabel's text.
        gameTitle.setForeground(Draw.PRINCETON_ORANGE);
        gameTitle.setFont(new Font(FONT, Font.BOLD, 60));
        // Adds the gameTitle object to the JPanel component.
        gamePanel.add(gameTitle);

        // Creates and initializes a JLabel object with the creators' names.
        JLabel authors = new JLabel("by Amado K. and Tyler L.");
        // Positions the authors object slightly above the bottom-center of the window
        authors.setHorizontalAlignment(SwingConstants.CENTER);
        authors.setBounds((canvasSize / 2) - (AUTHOR_WIDTH / 2),
                          canvasSize - AUTHOR_HEIGHT,
                          AUTHOR_WIDTH, AUTHOR_HEIGHT);
        // Sets the font and the color of the gameTitle's text.
        authors.setForeground(Draw.PRINCETON_ORANGE);
        authors.setFont(new Font(FONT, Font.BOLD, 15));
        // Adds the authors object to the JPanel component.
        gamePanel.add(authors);

        // Creates and initializes a JButton object.
        JButton play = new JButton("  PLAY  ");
        // Positions the play object at the center of the window.
        play.setHorizontalAlignment(SwingConstants.CENTER);
        play.setBounds(canvasSize / 2 - BUTTON_WIDTH / 2, canvasSize / 2 - BUTTON_HEIGHT / 2,
                       BUTTON_WIDTH, BUTTON_HEIGHT);
        // Sets the background color, text color, and text font of the play object
        play.setBorderPainted(false);
        play.setBackground(Draw.BLACK);
        play.setForeground(Draw.PRINCETON_ORANGE);
        play.setFont(new Font(FONT, Font.BOLD, 50));
        // Enables the play object to receive user input.
        play.addActionListener(this);

        // Adds gamePanel and play to the menu window.
        menu.add(play);
        menu.add(gamePanel);
        menu.setVisible(true);
    }

    // Returns whether the user has begun the game.
    public boolean getGameStart() {
        return gameStart;
    }

    // Takes an ActionEvent object as a parameter. Detects if the user
    // has pressed a JButton object and starts the game.
    public void actionPerformed(ActionEvent e) {
        StdAudio.playInBackground("victory.wav");
        gameStart = true;
        // Deletes the main menu window.
        menu.dispose();
    }
}
