import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Questionnaire {
    private ArrayList <Question> _questionnaire = new ArrayList<Question>();
    private int _unUsedQuestionsCounter ;


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
            // for sometimes the last empty line is taken as question.
            // assuming valid input file
        }
        _unUsedQuestionsCounter = _questionnaire.size();

    } //end constructor


    /**
     * Chooses a question randomly, out of the unused questions left.
     * @return
     */
    public Question getRandomQuestion() {
        int size = _questionnaire.size();
        int randIndex;
        Question randQuestion;
        while (_unUsedQuestionsCounter>0) {
            randIndex = (int) (Math.random() * 1234) % size;
            randQuestion = _questionnaire.get(randIndex);
            if (!randQuestion.isUsed()) {
                randQuestion.set_isUsed(true);
                _unUsedQuestionsCounter--;
                return randQuestion;
            }
        }// end while
        return null;

    }// end getRandomQuestion


    /**
     * Sets the questionnaire as not used, for a new game
     */
    public void reset() {
        for (Question q:_questionnaire){
            q.set_isUsed(false);
            _unUsedQuestionsCounter = _questionnaire.size();
        }
    }
}