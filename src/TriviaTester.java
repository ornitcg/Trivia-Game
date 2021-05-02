import javax.swing.*;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * 1. Sorry for the ugliness
 * 2. I hope this is what they meant.
 * 3. question panel does not wrap the text so it's recommended to use short questions (questions file is attached)
 */

public class TriviaTester {

    public static void main(String[] args) throws FileNotFoundException {
//        String filePath  = "C:\\Users\\tinko\\IdeaProjects\\MAMAN13-Q2\\src\\questions.txt" ;
        System.out.println("Please insert path to questions text file");
        Scanner sc = new Scanner(System.in);
        String filePath = sc.nextLine();


        JFrame triviaFrame = new JFrame();
        triviaFrame.setSize(1200,1000);
        triviaFrame.setResizable(false);
        MainTriviaPanel triviaPanel = new MainTriviaPanel(filePath);
        triviaFrame.add(triviaPanel);

        triviaFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        triviaFrame.setVisible(true);
    }


}
