import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Questionnaire {
    private ArrayList <Question> _questionnaire = new ArrayList<Question>();


    public Questionnaire(String filePath) throws FileNotFoundException {

        File file = new File(filePath);
        Scanner sc = new Scanner(file);
        String ans1, ans2, ans3, ans4;

        try {
            while (sc.hasNextLine()) {

                String q = sc.nextLine();
                ans1 = sc.nextLine();
                ans2 = sc.nextLine();
                ans3 = sc.nextLine();
                ans4 = sc.nextLine();
                Question question = new Question(q, ans1, ans2, ans3, ans4);
                _questionnaire.add(question);
            }// end while
        }
        catch (NoSuchElementException e){
            // Do nothing
        }

    } //end constructor




    public Question getRandomQuestion() {
        int size = _questionnaire.size();
        int randIndex;
        Question randQuestion;
        while (true) {
            randIndex = (int) (Math.random() * 1234) % size;
            randQuestion = _questionnaire.get(randIndex);
            if (!randQuestion.isUsed()) {
                randQuestion.set_isUsed(true);
                return randQuestion;
            }
        }// end while


    }// end getRandomQuestion


    public void print() {
        int size = _questionnaire.size();
        for (int i = 0; i < size; i++) {
            System.out.println(_questionnaire.get(i).toString());
        }
    } // end print


    public void reset() {
        for (Question q:_questionnaire){
            q.set_isUsed(false);
            System.out.println("q has been reset to false");
        }
    }
}