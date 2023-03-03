import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SpaceInvaders extends JFrame
{
    public Scoreboard sb = new Scoreboard();

    public Game game;

    // Store the name of the score file
    public String filename;

    // An integer to store the current play score
    // An integer to store the number of lives the player has
    int playerScore = 0;
    int playerLives = -1;

    // Bool values to check if score file name entered is valid and the file exists
    // Bool value to see if the user wants to make a new scoreboard file or use a already existing one
    public boolean scoreFileExists = false;
    public boolean makeNewScoreFile = false;

    // Panels
    JPanel panel;
    JPanel menuPanel;
    JPanel scoreboardPanel;
    JPanel enterScorePanel;
    JPanel enterFilePanel;
    JPanel controlsPanel;

    // Buttons
    JButton playButton;
    JButton scoreboardButton;
    JButton controlsButton;
    JButton exitButton;
    JButton backButton;
    JButton backButton1;
    JButton quitButton;
    JButton readFileButton;
    JButton newFileButton;

    // Text fields for entering players name after game end or name of score file
    JTextField scoreboardFileNameField;
    JTextField playerNameField;

    // Labels to be used as titles
    JLabel menuTitle;
    JLabel menuTitle1;
    JLabel menuTitle2;
    JLabel scoreboardTitle;
    JLabel gameOverTitle;

    // Labels to be used in the scoreboard to display the top 5 scores
    JLabel score1;
    JLabel score2;
    JLabel score3;
    JLabel score4;
    JLabel score5;

    JLabel playerScoreLabel;
    JLabel playerLivesLabel;
    JLabel gameOverScore;
    JLabel pleaseEnterName;
    JLabel pleaseEnterTxt;

    // Labels used for the controls panel
    JLabel controlsLabel;
    JLabel controlsLabel1;
    JLabel controlsLabel2;
    JLabel controlsLabel3;

    // Spacing labels to make panels look nicer i guess
    JLabel scoreboardSpacingLabel;
    JLabel scoreboardSpacingLabel1;
    JLabel controlsSpacingLabel;
    JLabel controlsSpacingLabel1;
    JLabel controlsSpacingLabel2;
    JLabel enterScoreFileSpacing;
    JLabel enterScoreFileSpacing1;

    // Fonts for labels Monospaced
    Font h1Font = new Font("Arial", Font.BOLD, 90);
    Font h2Font = new Font("Arial", Font.BOLD, 70);
    Font h3Font = new Font("Arial", Font.BOLD, 40);
    Font h4Font = new Font("Arial", Font.BOLD, 30);
    Font h5Font = new Font("Arial", Font.BOLD, 20);

    CardLayout cardLayout = new CardLayout();

    public static void main(String[] args)
    {
        SpaceInvaders frame = new SpaceInvaders();
        frame.setSize(600, 600);
    }

    private SpaceInvaders()
    {
        // Creating the planels for the game
        panel = new JPanel();
        menuPanel = new JPanel();
        scoreboardPanel = new JPanel();
        controlsPanel = new JPanel();
        enterScorePanel = new JPanel();
        enterFilePanel = new JPanel();

        // Creating the buttons for the game
        playButton = new JButton("PLAY");
        scoreboardButton = new JButton("SCOREBOARD");
        controlsButton = new JButton("CONTROLS");
        backButton = new JButton("BACK");
        backButton1 = new JButton("BACK");
        exitButton = new JButton("EXIT");
        quitButton = new JButton("QUIT");
        readFileButton = new JButton("USE EXISTING SCORE FILE");
        newFileButton = new JButton("CREATE NEW SCORE FILE");

        // Creating text fields for the game
        scoreboardFileNameField = new JTextField(20);
        playerNameField = new JTextField(10);

        // Set the container panels layout to cardlayout to allow for different screens
        panel.setLayout(cardLayout);

        // Frame adjustments and setting the title
        this.setSize(600,600);
        this.setMaximumSize(new Dimension(584 , 561));
        this.setTitle("Square Invaders 1805841");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setVisible(true);

        // Adding the game as a JPanel
        game = new Game(this);

        initializeMenu();
        initializeScoreboard();
        initializeGame();
        initializeFilePanel();
        initializeEnterScore();
        initializeControls();

        initializeCardLayout();
    }

    // Add all the panels to the container panel
    private void initializeCardLayout()
    {
        panel.add(menuPanel, "Menu");
        panel.add(game, "Game");
        panel.add(scoreboardPanel, "Scoreboard");
        panel.add(enterScorePanel, "Enter score");
        panel.add(enterFilePanel, "Enter file name");
        panel.add(controlsPanel, "Controls");

        add(panel);

        cardLayout.show(panel, "Enter file name");
        //cardLayout.show(panel, "Game");
    }

    // Add all the necessary buttons to the main menu screen
    // Set the panels layout to box layout to allow for the items on the panel to be centred
    // Also customize all the buttons and labels to make them look nicer I guess
    private void initializeMenu()
    {
        menuPanel.setBackground(Color.DARK_GRAY);
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));

        menuTitle2 = new JLabel(" ");
        menuTitle2.setFont(h4Font);

        menuTitle = new JLabel("SQUARE");
        menuTitle.setFont(h1Font);
        menuTitle.setForeground(Color.RED);

        menuTitle1 = new JLabel("INVADERS");
        menuTitle1.setFont(h1Font);
        menuTitle1.setForeground(Color.YELLOW);

        menuPanel.add(menuTitle2);
        menuPanel.add(menuTitle);
        menuPanel.add(menuTitle1);
        menuPanel.add(playButton);
        menuPanel.add(scoreboardButton);
        menuPanel.add(controlsButton);
        menuPanel.add(exitButton);

        playButton.setFont(h3Font);
        playButton.setForeground(Color.GREEN);
        playButton.setBackground(Color.DARK_GRAY);
        playButton.setBorderPainted(false);
        playButton.addMouseListener(new MouseAdapter()
        {
            public void mouseEntered(MouseEvent e)
            {
                playButton.setForeground(Color.CYAN);
            }

            public void mouseExited(MouseEvent e)
            {
                playButton.setForeground(Color.GREEN);
            }
        });

        scoreboardButton.setFont(h3Font);
        scoreboardButton.setForeground(Color.GREEN);
        scoreboardButton.setBackground(Color.DARK_GRAY);
        scoreboardButton.setBorderPainted(false);
        scoreboardButton.addMouseListener(new MouseAdapter()
        {
            public void mouseEntered(MouseEvent e)
            {
                scoreboardButton.setForeground(Color.CYAN);
            }

            public void mouseExited(MouseEvent e)
            {
                scoreboardButton.setForeground(Color.GREEN);
            }
        });

        controlsButton.setFont(h3Font);
        controlsButton.setForeground(Color.GREEN);
        controlsButton.setBackground(Color.DARK_GRAY);
        controlsButton.setBorderPainted(false);
        controlsButton.addMouseListener(new MouseAdapter()
        {
            public void mouseEntered(MouseEvent e)
            {
                controlsButton.setForeground(Color.CYAN);
            }

            public void mouseExited(MouseEvent e)
            {
                controlsButton.setForeground(Color.GREEN);
            }
        });

        exitButton.setFont(h3Font);
        exitButton.setForeground(Color.GREEN);
        exitButton.setBackground(Color.DARK_GRAY);
        exitButton.setBorderPainted(false);
        exitButton.addMouseListener(new MouseAdapter()
        {
            public void mouseEntered(MouseEvent e)
            {
                exitButton.setForeground(Color.CYAN);
            }

            public void mouseExited(MouseEvent e)
            {
                exitButton.setForeground(Color.GREEN);
            }
        });

        menuTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        menuTitle1.setAlignmentX(Component.CENTER_ALIGNMENT);
        menuTitle2.setAlignmentX(Component.CENTER_ALIGNMENT);
        playButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        scoreboardButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        controlsButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        playButton.addActionListener(new buttonHandler(this, 1));
        scoreboardButton.addActionListener(new buttonHandler(this, 2));
        controlsButton.addActionListener(new buttonHandler(this, 10));
        exitButton.addActionListener(new buttonHandler(this, 3));
    }

    // Add all the parts into the scoreboard panel and customize them
    // display the current top 5 high scores if any are available otherwise set them to default
    // change the layout and centre all the items
    private void initializeScoreboard()
    {
        scoreboardPanel.setBackground(Color.DARK_GRAY);
        scoreboardPanel.setLayout(new BoxLayout(scoreboardPanel, BoxLayout.Y_AXIS));

        scoreboardTitle = new JLabel("SCOREBOARD");
        scoreboardTitle.setFont(h2Font);
        scoreboardTitle.setForeground(Color.GREEN);

        scoreboardSpacingLabel = new JLabel(" ");
        scoreboardSpacingLabel.setFont(h3Font);

        Color firstPlaceColor = new Color(255, 180, 0);
        Color secondPlaceColor = new Color(210, 210, 210);
        Color thirdPlaceColor = new Color(205, 127, 50);

        score1 = new JLabel("1st : ??? 000");
        score1.setFont(h3Font);
        score1.setForeground(firstPlaceColor);

        score2 = new JLabel("2nd : ??? 000");
        score2.setFont(h3Font);
        score2.setForeground(secondPlaceColor);

        score3 = new JLabel("3rd : ??? 000");
        score3.setFont(h3Font);
        score3.setForeground(thirdPlaceColor);

        score4 = new JLabel("4th : ??? 000");
        score4.setFont(h3Font);
        score4.setForeground(Color.WHITE);

        score5 = new JLabel("5th : ??? 000");
        score5.setFont(h3Font);
        score5.setForeground(Color.WHITE);

        scoreboardSpacingLabel1 = new JLabel(" ");
        scoreboardSpacingLabel1.setFont(h2Font);

        backButton.setFont(h3Font);
        backButton.setForeground(Color.GREEN);
        backButton.setBackground(Color.DARK_GRAY);
        backButton.setBorderPainted(false);
        backButton.addMouseListener(new MouseAdapter()
        {
            public void mouseEntered(MouseEvent e)
            {
                backButton.setForeground(Color.CYAN);
            }

            public void mouseExited(MouseEvent e)
            {
                backButton.setForeground(Color.GREEN);
            }
        });

        scoreboardPanel.add(scoreboardTitle);
        scoreboardPanel.add(scoreboardSpacingLabel);
        scoreboardPanel.add(score1);
        scoreboardPanel.add(score2);
        scoreboardPanel.add(score3);
        scoreboardPanel.add(score4);
        scoreboardPanel.add(score5);
        scoreboardPanel.add(scoreboardSpacingLabel1);
        scoreboardPanel.add(backButton);

        // Set position to centre
        scoreboardTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        score1.setAlignmentX(Component.CENTER_ALIGNMENT);
        score2.setAlignmentX(Component.CENTER_ALIGNMENT);
        score3.setAlignmentX(Component.CENTER_ALIGNMENT);
        score4.setAlignmentX(Component.CENTER_ALIGNMENT);
        score5.setAlignmentX(Component.CENTER_ALIGNMENT);
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        backButton.addActionListener(new buttonHandler(this, 4));
    }

    // Add all the items to the game screen like the players current score and lives as well as the quit button to end the game early if need be
    // Also change the items appearance to make them look nicer I guess again (at least they look better than the default buttons and labels in my opinion)
    private void initializeGame()
    {
        game.setBackground(Color.DARK_GRAY);

        playerLivesLabel = new JLabel("LIVES : " + playerLives + "      ");
        playerLivesLabel.setFont(h5Font);
        playerLivesLabel.setForeground(Color.GREEN);

        playerScoreLabel = new JLabel("SCORE : " + playerScore + "      ");
        playerScoreLabel.setFont(h5Font);
        playerScoreLabel.setForeground(Color.GREEN);

        quitButton.setFont(h5Font);
        quitButton.setForeground(Color.GREEN);
        quitButton.setBackground(Color.BLACK);
        quitButton.setBorderPainted(false);
        quitButton.addMouseListener(new MouseAdapter()
        {
            public void mouseEntered(MouseEvent e)
            {
                quitButton.setForeground(Color.CYAN);
            }

            public void mouseExited(MouseEvent e)
            {
                quitButton.setForeground(Color.GREEN);
            }
        });

        game.add(playerLivesLabel);
        game.add(playerScoreLabel);
        game.add(quitButton);

        quitButton.addActionListener(new buttonHandler(this, 5));
    }

    private void initializeFilePanel()
    {
        enterFilePanel.setBackground(Color.DARK_GRAY);
        enterFilePanel.setLayout(new BoxLayout(enterFilePanel, BoxLayout.Y_AXIS));

        enterScoreFileSpacing = new JLabel(" ");
        enterScoreFileSpacing.setFont(h1Font);

        enterScoreFileSpacing1 = new JLabel(" ");
        enterScoreFileSpacing1.setFont(h1Font);

        pleaseEnterTxt = new JLabel("Please make sure to enter .txt at the end of the filename !");
        pleaseEnterTxt.setFont(h5Font);
        pleaseEnterTxt.setForeground(Color.RED);

        readFileButton.setFont(h5Font);
        readFileButton.setForeground(Color.GREEN);
        readFileButton.setBackground(Color.DARK_GRAY);
        readFileButton.setEnabled(false);
        readFileButton.setBorderPainted(false);
        readFileButton.addMouseListener(new MouseAdapter()
        {
            public void mouseEntered(MouseEvent e)
            {
                readFileButton.setForeground(Color.CYAN);
            }

            public void mouseExited(MouseEvent e)
            {
                readFileButton.setForeground(Color.GREEN);
            }
        });

        newFileButton.setFont(h5Font);
        newFileButton.setForeground(Color.GREEN);
        newFileButton.setBackground(Color.DARK_GRAY);
        newFileButton.setBorderPainted(false);
        newFileButton.addMouseListener(new MouseAdapter()
        {
            public void mouseEntered(MouseEvent e)
            {
                newFileButton.setForeground(Color.CYAN);
            }

            public void mouseExited(MouseEvent e)
            {
                newFileButton.setForeground(Color.GREEN);
            }
        });

        scoreboardFileNameField.setFont(h5Font);

        enterFilePanel.add(enterScoreFileSpacing);
        enterFilePanel.add(pleaseEnterTxt);
        enterFilePanel.add(scoreboardFileNameField);
        enterFilePanel.add(enterScoreFileSpacing1);
        enterFilePanel.add(readFileButton);
        enterFilePanel.add(newFileButton);

        pleaseEnterTxt.setAlignmentX(Component.CENTER_ALIGNMENT);
        scoreboardFileNameField.setHorizontalAlignment(JTextField.CENTER);
        scoreboardFileNameField.setMaximumSize(new Dimension(400, 40));
        readFileButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        newFileButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        scoreboardFileNameField.addActionListener(new buttonHandler(this, 6));
        readFileButton.addActionListener(new buttonHandler(this, 8));
        newFileButton.addActionListener(new buttonHandler(this, 9));
    }

    // Add all the items into the enter score screen
    // Change to box layout to allow for the items to be centred and change their appearance
    private void initializeEnterScore()
    {
        enterScorePanel.setBackground(Color.DARK_GRAY);
        enterScorePanel.setLayout(new BoxLayout(enterScorePanel, BoxLayout.Y_AXIS));

        gameOverTitle = new JLabel("GAME OVER");
        gameOverTitle.setFont(h1Font);
        gameOverTitle.setForeground(Color.RED);

        gameOverScore = new JLabel("YOUR SCORE : " + playerScore);
        gameOverScore.setFont(h3Font);
        gameOverScore.setForeground(Color.GREEN);

        pleaseEnterName = new JLabel("PLEASE ENTER YOUR NAME BELOW");
        pleaseEnterName.setFont(h5Font);
        pleaseEnterName.setForeground(Color.CYAN);

        playerNameField.setFont(h5Font);

        enterScorePanel.add(gameOverTitle);
        enterScorePanel.add(gameOverScore);
        enterScorePanel.add(pleaseEnterName);
        enterScorePanel.add(playerNameField);

        gameOverTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        gameOverScore.setAlignmentX(Component.CENTER_ALIGNMENT);
        pleaseEnterName.setAlignmentX(Component.CENTER_ALIGNMENT);
        playerNameField.setHorizontalAlignment(JTextField.CENTER);
        playerNameField.setMaximumSize(new Dimension(400, 40));

        playerNameField.addActionListener(new buttonHandler(this, 7));
    }

    private void initializeControls()
    {
        controlsPanel.setBackground(Color.DARK_GRAY);
        controlsPanel.setLayout(new BoxLayout(controlsPanel, BoxLayout.Y_AXIS));

        controlsLabel = new JLabel("TO CONTROL THE MOVEMENT OF THE PLAYER");
        controlsLabel.setFont(h5Font);
        controlsLabel.setForeground(Color.GREEN);

        controlsLabel1 = new JLabel("YOU CAN USE THE KEYS 'A' AND 'D'");
        controlsLabel1.setFont(h5Font);
        controlsLabel1.setForeground(Color.GREEN);

        controlsLabel2 = new JLabel("AS WELL AS THE RIGHT AND LEFT ARROW KEYS.");
        controlsLabel2.setFont(h5Font);
        controlsLabel2.setForeground(Color.GREEN);

        controlsLabel3 = new JLabel("TO SHOOT YOU USE SPACE BAR");
        controlsLabel3.setFont(h5Font);
        controlsLabel3.setForeground(Color.GREEN);

        controlsSpacingLabel = new JLabel(" ");
        controlsSpacingLabel.setFont(h1Font);

        controlsSpacingLabel1 = new JLabel(" ");
        controlsSpacingLabel1.setFont(h1Font);

        controlsSpacingLabel2 = new JLabel(" ");
        controlsSpacingLabel2.setFont(h3Font);

        backButton1.setFont(h3Font);
        backButton1.setForeground(Color.GREEN);
        backButton1.setBackground(Color.DARK_GRAY);
        backButton1.setBorderPainted(false);
        backButton1.addMouseListener(new MouseAdapter()
        {
            public void mouseEntered(MouseEvent e)
            {
                backButton1.setForeground(Color.CYAN);
            }

            public void mouseExited(MouseEvent e)
            {
                backButton1.setForeground(Color.GREEN);
            }
        });

        controlsPanel.add(controlsSpacingLabel);
        controlsPanel.add(controlsLabel);
        controlsPanel.add(controlsLabel1);
        controlsPanel.add(controlsLabel2);
        controlsPanel.add(controlsSpacingLabel2);
        controlsPanel.add(controlsLabel3);
        controlsPanel.add(controlsSpacingLabel1);
        controlsPanel.add(backButton1);

        controlsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        controlsLabel1.setAlignmentX(Component.CENTER_ALIGNMENT);
        controlsLabel2.setAlignmentX(Component.CENTER_ALIGNMENT);
        controlsLabel3.setAlignmentX(Component.CENTER_ALIGNMENT);
        controlsSpacingLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        controlsSpacingLabel1.setAlignmentX(Component.CENTER_ALIGNMENT);
        backButton1.setAlignmentX(Component.CENTER_ALIGNMENT);

        backButton1.addActionListener(new buttonHandler(this, 4));
    }
}