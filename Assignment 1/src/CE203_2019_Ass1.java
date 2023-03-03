import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;

// Created by Kamil Cwigun, kc18960, 1805841

public class CE203_2019_Ass1 extends JFrame {
    ArrayList<String> listOfWords = new ArrayList<>();

    // Create text field and text area used for input and output
    JTextArea display;
    JTextField input;

    // Create new int fields for entering the RGB values
    JTextField red;
    JTextField green;
    JTextField blue;

    public static void main(String[] args) {
        CE203_2019_Ass1 frame = new CE203_2019_Ass1();

        // Frame modification
        frame.setSize(800, 500);
        frame.setTitle("Assignment 1");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    private CE203_2019_Ass1() {
        // Create the 3 panels, PanelN is for buttons at the top, PanelC is for the text area which is used as a display, PanelS is used for text field input
        JPanel panelN = new JPanel();
        JPanel panelC = new JPanel();
        JPanel panelS = new JPanel();

        // Creates buttons with text within them
        JButton Add = new JButton("Add");
        JButton Search = new JButton("Display");
        JButton Remove = new JButton("Remove");
        JButton Clear = new JButton("Clear");

        // This adds the 3 panels to the frame
        add(panelN, BorderLayout.NORTH);
        add(panelC, BorderLayout.CENTER);
        add(panelS, BorderLayout.SOUTH);

        // Sets size of text field and text area
        display = new JTextArea(20,65);
        input = new JTextField(30);

        // Set size of RGB text fields
        red = new JTextField(3);
        green = new JTextField(3);
        blue = new JTextField(3);

        // Create labels for input fields
        JLabel inputLabel = new JLabel("Input :");
        JLabel redLabel = new JLabel("Red :");
        JLabel greenLabel = new JLabel("Green :");
        JLabel blueLabel = new JLabel("Blue :");

        // Prevent users from editing the display in the centre
        display.setEditable(false);

        // Allows us to scroll within the text area if the number of words exceeds the area
        JScrollPane scroll = new JScrollPane(display);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        // Set layout for panel and set spacing
        panelN.setLayout(new FlowLayout(FlowLayout.CENTER,40,10));
        panelC.setLayout(new FlowLayout(FlowLayout.CENTER,40,1));
        panelS.setLayout(new FlowLayout(FlowLayout.CENTER,20,20));

        // Adds the text area with scroll to centre panel
        panelC.add(scroll);

        // Adds Text fields to the bottom panel with labels for each one
        panelS.add(inputLabel);
        panelS.add(input);
        panelS.add(redLabel);
        panelS.add(red);
        panelS.add(greenLabel);
        panelS.add(green);
        panelS.add(blueLabel);
        panelS.add(blue);

        // Set default rgb values to 0
        red.setText("0");
        green.setText("0");
        blue.setText("0");

        // Add the buttons to the panel
        panelN.add(Add);
        panelN.add(Search);
        panelN.add(Remove);
        panelN.add(Clear);


        // This adds a action to each button which then links to the class buttonHandler below
        Add.addActionListener(new buttonHandler(this, 1));
        Search.addActionListener(new buttonHandler(this, 2));
        Remove.addActionListener(new buttonHandler(this, 3));
        Clear.addActionListener(new buttonHandler(this, 4));

    }
}

class buttonHandler implements ActionListener {

    private CE203_2019_Ass1 theApp;
    private int action;

    buttonHandler(CE203_2019_Ass1 theApp, int action) {
        this.theApp = theApp;
        this.action = action;
    }

    public void actionPerformed(ActionEvent e) {

        // CheckRGB block

        // First this block of code will try to assign the input RGB values as integers. If it fails to do so it will catch the error and display a popup error message
        // If the code succeeds then it will check each of the RGB values to make sure they are within range. If not they will display a popup error message and break the try block
        // If all of this succeeds this block will then create the color by using the specified RGB values

        checkRGB: try {

            int RED = Integer.parseInt(theApp.red.getText());
            int GREEN = Integer.parseInt(theApp.green.getText());
            int BLUE = Integer.parseInt(theApp.blue.getText());

            if (0 > RED || RED > 255){
                error();
                break checkRGB;
            }

            if (0 > GREEN || GREEN > 255){
                error();
                break checkRGB;
            }

            if (0 > BLUE || BLUE > 255){
                error();
                break checkRGB;
            }

            Color rgb = new Color(RED, GREEN, BLUE);

            // Add action block

            // This block of code will check if the input field is empty first
            // If the input field is not empty it will check if it only contains the allowed characters
            // If the input is valid then the block of code will set the text color to the specified color which has already been validated and display an appropriate message. It also clear the input field
            // If the input is not valid it will display and appropriate message
            if (this.action == 1) {

                try {
                    String inputText = theApp.input.getText();

                    if (!inputText.isEmpty()) {

                        if (inputText.matches("^[A-Za-z][A-Za-z0-9 -]*$")) {
                            theApp.display.setForeground(rgb);
                            theApp.display.append("Word  ' " + inputText + " '  has been added to the list.\n");
                            theApp.listOfWords.add(inputText);
                            theApp.input.setText("");
                        }

                        else{
                            theApp.display.setForeground(rgb);
                            theApp.display.append("The string  ' " + inputText + " '  has not been added to the list as it is not a valid word.\n");
                        }
                    }

                } catch (NumberFormatException ex) {
                    System.out.println("Error with Add");
                }
            }

            // Search action block

            // This block of code will first check if the input field is empty. Next it will check if the input provided is valid.
            // If the input provided is longer than 1 character or contains invalid characters then a error message will be display (The color can be changed even with invalid input because I wasn't sure if it should change even with invalid input)
            // If the input is valid the block will then get the character turn it into a lower case character.
            // Next the code will use a for loop to check the last character of every word in the array list
            // Once the code gets 1 word from the list it will get the last character turn it into lowercase and compare it to the provided input
            // If the characters match it the word will them be added to a new array list
            // After the code goes through every word in the original array list it will then use if statements to check if there are any words in the new array list
            // If the list is empty it will display an appropriate message but if it contains any words then it will display an appropriate message as well as display all the words from that list

            if (this.action == 2) {
                try {
                    String inputText = theApp.input.getText();

                    if (!inputText.isEmpty()){

                        if (inputText.matches("^[A-Za-z0-9 -]$")) {
                            ArrayList<String> listOfWordsToDisplay = new ArrayList<>();
                            theApp.display.setForeground(rgb);
                            char charInput = Character.toLowerCase(inputText.charAt(0));

                            for (String currentWord : theApp.listOfWords) {
                                char lastChar = Character.toLowerCase(currentWord.charAt(currentWord.length() - 1));

                                if (lastChar == charInput) {
                                    listOfWordsToDisplay.add(currentWord);
                                }
                            }

                            if (listOfWordsToDisplay.size() == 0){
                                theApp.display.append("No words ending with  ' " + charInput + " ' have been found.\n");
                            }
                            else{
                                theApp.display.append("All words that end with the character  ' " + charInput + " ' :\n");
                                for (String currentWordToDisplay : listOfWordsToDisplay) {
                                    theApp.display.append(" - " + currentWordToDisplay + "\n");
                                }
                            }
                            theApp.input.setText("");
                        }
                        else{
                            theApp.display.setForeground(rgb);
                            theApp.display.append("Invalid input had been provided for 'Display' !\n");
                        }

                    }
                } catch (NumberFormatException ex) {
                    System.out.println("Error with Search");
                }
            }

            // Remove action block

            // This block of code will first check if the input field is empty and after that it will check if the input field only contains allowed characters
            // Next the code will change the color of the text to the specified color and clear the input field
            // After this the code will go through the whole array list to check for any occurrences of the word input
            // If the codes doesn't find any occurrences an appropriate message will be display
            // If the codes find any occurrences of the input word it will count how many and remove all of them as well as display an appropriate message

            if (this.action == 3) {
                try {
                    String inputText = theApp.input.getText();

                    if (!inputText.isEmpty()) {

                        if (inputText.matches("^[A-Za-z][A-Za-z0-9 -]*$")) {
                            int numberOfOccurrencesFound = 0;
                            theApp.display.setForeground(rgb);
                            theApp.input.setText("");

                            for (int i = 0; i < theApp.listOfWords.size(); i++) {

                                if (theApp.listOfWords.get(i).equalsIgnoreCase(inputText)) {
                                    theApp.listOfWords.remove(i);
                                    i--;
                                    numberOfOccurrencesFound ++;
                                }
                            }

                            if (numberOfOccurrencesFound == 0){
                                theApp.display.append("No occurrences of the word  ' " + inputText + " '  have been found.\n");
                            }

                            else{
                                theApp.display.append("All occurrences of the word  ' " + inputText + " '  have been removed.\n");
                            }
                        }
                        else{
                            theApp.display.setForeground(rgb);
                            theApp.display.append("Invalid input had been provided for 'Remove' !\n");
                        }
                    }
                } catch (NumberFormatException ex) {
                    System.out.println("Error with Remove");
                }
            }

            // Clear action block

            // This block of code sets the color of text to what is specified and clears the whole list while also displaying an appropriate message

            if (this.action == 4) {
                try {
                    if (theApp.listOfWords.size() == 0){
                        theApp.display.append("The list is already empty.\n");
                    }
                    else{
                        theApp.display.setForeground(rgb);
                        theApp.display.append("The list has been cleared.\n");
                        theApp.listOfWords.clear();
                    }
                } catch (NumberFormatException ex) {
                    System.out.println("Error with Clear");
                }
            }
        }

        catch (NumberFormatException ex)
        {
            error();
        }
    }

    //ERROR block

    // This small block of code is responsible for displaying a popup message when invalid RGB values are input
    // If a invalid RGB values is input it also changes the colour of the text to black and all the rgb values within the input fields are also set to 0

    private void error() {
        JOptionPane.showMessageDialog(new JFrame(), "Invalid RGB values.\nColor has been set to black.","ERROR.",JOptionPane.ERROR_MESSAGE );
        Color rgb = new Color(0, 0, 0);
        theApp.red.setText("0");
        theApp.green.setText("0");
        theApp.blue.setText("0");

        theApp.display.setForeground(rgb);
    }
}