import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.util.*;

public class buttonHandler implements ActionListener
{
    private SpaceInvaders space;
    private int action;

    buttonHandler(SpaceInvaders space, int action)
    {
        this.space = space;
        this.action = action;
    }

    public void actionPerformed(ActionEvent e)
    {
        // Play button action
        if (this.action == 1)
        {
            space.playerLives = 3;
            space.playerLivesLabel.setText("LIVES : " + space.playerLives + "      ");
            space.playerScoreLabel.setText("SCORE : " + space.playerScore + "      ");
            space.cardLayout.show(space.panel, "Game");
        }

        // Scoreboard button action
        if (this.action == 2)
        {
            refreshScoreboard();
        }

        // Exit button action
        if (this.action == 3)
        {
            System.exit(0);
        }

        // Back button action
        if (this.action == 4)
        {
            space.cardLayout.show(space.panel, "Menu");
        }

        // Quit button action
        if (this.action == 5)
        {
            space.playerLives = -1;
        }

        // Action for entering text into the file name text field
        // Checks if the player decided to make a new scoreboard file
        // If a new file is created the scoreboard will be empty and not empty if already existing one is used
        // Once the file option has been made it will then allow the player to access the main menu
        if (this.action == 6)
        {
            space.filename = space.scoreboardFileNameField.getText();
            space.scoreFileExists = space.sb.checkExists(space.filename);

            if (!space.makeNewScoreFile)
            {
                if (space.scoreFileExists)
                {
                    space.filename = space.scoreboardFileNameField.getText();
                    space.scoreboardFileNameField.setText("");
                    space.cardLayout.show(space.panel, "Menu");
                }
                else{
                    JOptionPane.showMessageDialog(new JFrame(), "Invalid file name\nPlease make sure you enter a valid name!","ERROR.",JOptionPane.ERROR_MESSAGE );
                }

            }
            else
                {
                    space.filename = space.scoreboardFileNameField.getText();
                    space.sb.createNewScoreFile(space.filename);
                    space.scoreboardFileNameField.setText("");
                    space.cardLayout.show(space.panel, "Menu");
                }

        }

        // Action for entering play name at end of game
        // Enters the players score and name into the scoreboard file and display the scoreboard to see the top 5 highscores
        if (this.action == 7)
        {
            space.sb.addScore(space.filename, space.playerNameField.getText(), space.playerScore );
            space.playerNameField.setText("");
            space.playerScore = 0;
            refreshScoreboard();
            space.cardLayout.show(space.panel, "Scoreboard");
        }

        // Action for button to select already existing score file
        if (this.action == 8)
        {
            space.readFileButton.setEnabled(false);
            space.newFileButton.setEnabled(true);
            space.makeNewScoreFile = false;
        }

        // Action for button to make new score file
        if (this.action == 9)
        {
            space.newFileButton.setEnabled(false);
            space.readFileButton.setEnabled(true);
            space.makeNewScoreFile = true;
        }

        if (this.action == 10)
        {
            space.cardLayout.show(space.panel, "Controls");
        }
    }

    // Updates the scoreboard by checking the current high scores and choosing the top 5 also displays the scoreboard
    public void refreshScoreboard()
    {
        space.cardLayout.show(space.panel, "Scoreboard");

        space.sb = new Scoreboard();

        ArrayList<String> top5Scores = space.sb.getScores(space.filename);

        for (int i = 0; i < top5Scores.size(); i ++)
        {
            if (i == 0)
            {
                space.score1.setText("1st : " + top5Scores.get(0));
            }

            if (i == 1)
            {
                space.score2.setText("2nd : " + top5Scores.get(1));
            }

            if (i == 2)
            {
                space.score3.setText("3rd : " + top5Scores.get(2));
            }

            if (i == 3)
            {
                space.score4.setText("4th : " + top5Scores.get(3));
            }

            if (i == 4)
            {
                space.score5.setText("5th : " + top5Scores.get(4));
            }
        }
    }
}