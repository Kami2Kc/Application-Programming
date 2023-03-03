import java.io.*;
import java.util.*;

public class Scoreboard
{
    private ArrayList<String> Scores;
    private ArrayList<String> top5Scores;
    int currentHighestScore = 0;

    // Adds all the scores stored in the file into an array list
    public ArrayList<String> getScores(String filename)
    {
        Scores = new ArrayList<>();

        try
        {
            File scoresFile  = new File(filename);
            BufferedReader reader = new BufferedReader(new FileReader(scoresFile));

            String currentLine;

            while ( (currentLine = reader.readLine()) != null)
            {
                if (!(currentLine.equals("")))
                {
                    Scores.add(currentLine);
                }
            }

        } catch (IOException e)
        {
            e.printStackTrace();
        }

        getTop5Scores();

        return top5Scores;
    }

    // Gets the top 5 scores from the Scores array list and saves them in a new array list called top5Scores
    public void getTop5Scores()
    {
        top5Scores = new ArrayList<>();

        int min = 5;
        int length = Scores.size();
        int topScoreIndex = 0;

        if (Scores.size() < 5)
        {
            min = Scores.size();
        }

        for (int j = 0; j < min ; j++)
        {
            for (int i = 0; i < length; i ++)
            {
                String[] currentScoreLine = Scores.get(i).split(" ", 2);
                int currentScore = Integer.parseInt(currentScoreLine[1]);
                if (currentScore > currentHighestScore)
                {
                    currentHighestScore = currentScore;
                    topScoreIndex = i;
                }
            }
            currentHighestScore = 0;
            top5Scores.add(Scores.get(topScoreIndex));
            Scores.remove(topScoreIndex);
            length = Scores.size();

        }
    }

    // Check if the file entered exists and return boolean result
    public boolean checkExists(String filename)
    {
        try
        {
            File scoresFile  = new File(filename);
            BufferedReader reader = new BufferedReader(new FileReader(scoresFile));
            return true;
        }catch(FileNotFoundException e)
        {
            return false;
        }
    }

    // Adds a new score to the current score file
    public void addScore(String filename, String playerName, int score)
    {
        playerName = playerName.replace(" ", "");
        playerName = playerName.toUpperCase();
        String newScore = "\n" + playerName + " " + score;

        try
        {
            File scoresfile = new File(filename);
            FileWriter filewriter = new FileWriter(scoresfile, true);
            BufferedWriter bufferedwriter = new BufferedWriter(filewriter);
            bufferedwriter.write(newScore);
            bufferedwriter.close();
            filewriter.close();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    // Creates a new score file and runs addScore method
    public void createNewScoreFile(String filename)
    {
        try
        {
            File file = new File(filename);
            file.createNewFile();

        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}