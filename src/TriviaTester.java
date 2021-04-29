import javax.swing.*;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class TriviaTester {

    public static void main(String[] args) throws FileNotFoundException {
        String filePath  = "C:\\Users\\tinko\\IdeaProjects\\MAMAN13-Q2\\src\\questions.txt" ;
        System.out.println("Please insert path to questions text file");
//        Scanner sc = new Scanner(System.in);
//        filePath = sc.nextLine();


        JFrame triviaFrame = new JFrame();
        triviaFrame.setSize(1000,1000);
        MainTriviaPanel triviaPanel = new MainTriviaPanel(filePath);


        triviaFrame.add(triviaPanel);




        triviaFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        triviaFrame.setVisible(true);
    }


}
