import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.io.FileNotFoundException;

public class MainTriviaPanel extends JPanel {
    Questionnaire _questionaire;

    private Question _question;
    private JLabel _questionLbl;
    private JButton _ansButtonsArr[];
    private int _correctCount;
    private int _mistakesCount;
    private int _score;
    private JButton _exitBtn;
    private JButton _startBtn;
    private JButton _nextQuestBtn;


    public MainTriviaPanel(String filePath) throws FileNotFoundException{
        // ******** QUESTIOS SET **************
        try {
            _questionaire = new Questionnaire(filePath);
        }
        catch (FileNotFoundException e){
            throw(e);
        }
        _question = _questionaire.getRandomQuestion();


        // ******** TITLE *************

        JPanel titleP = new JPanel();
        JLabel title = new JLabel("TRIVIA");

        // ******** TOP BUTTONS *************

        // EXIT BUTTON
        _exitBtn = new JButton("EXIT");
        _exitBtn.setForeground(Color.white);
        _exitBtn.setBackground(Color.RED);
        _exitBtn.setFont(new Font("Arial", Font.BOLD, 20));

        // START BUTTON

        _startBtn = new JButton("START GAME");

        // NEXT QUESTION BUTTON

        _nextQuestBtn = new JButton("NEXT QUESTION");


        JPanel topButtons = new JPanel();

        topButtons.add(_exitBtn);
        topButtons.add(_startBtn);
        topButtons.add(_nextQuestBtn);


        // ******** TRIVIA CARD *************
        JButton answer ;
        _questionLbl = new JLabel(_question.get_question());  // get the text of the randomly chosen question
        _questionLbl.setForeground(Color.decode("#0f4c5c"));
        _questionLbl.setFont(new Font("Arial", Font.BOLD, 20));
        set_ansArr();

        JPanel answersP = new JPanel();  // answeres in a separate panel
        answersP.setLayout(new GridLayout(4,1,10,10));
        for (int i=0; i<4; i++){
            answer =_ansButtonsArr[i];
            answer.setForeground(Color.decode("#4a5759"));
            answer.setFont(new Font("Arial", Font.BOLD, 20));
            answersP.add(answer);
        }

        JPanel cardP = new JPanel();
        cardP.setLayout(new BorderLayout());

        cardP.add(_questionLbl, BorderLayout.NORTH);
        cardP.add(answersP, BorderLayout.CENTER);


        add(cardP);


        // ******** TIMER *************



        // ****************** SCORING PANEL******************
        // HANDLE CORRECT ANSWRES DISPLAY
        _correctCount = 0;
        JPanel correctP = new JPanel();
        JLabel correctLbl = new JLabel("CORRECT: " + _correctCount);
        correctLbl.setForeground(Color.decode("#2b9348"));
        correctLbl.setFont(new Font("Arial", Font.BOLD, 30));
        correctP.add(correctLbl);

        // HANDLE WRONG ANSWERS DISPLAY
        _mistakesCount = 0;
        JPanel mistakesP = new JPanel();
        JLabel mistakeLbl = new JLabel("WRONG: " + _mistakesCount);
        mistakeLbl.setForeground(Color.decode("#e71d36"));
        mistakeLbl.setFont(new Font("Arial", Font.BOLD, 30));
        mistakesP.add(mistakeLbl);

        // HANDLE SCORES ANSWRES DISPLAY
        _score = 0;
        JPanel scoreP = new JPanel();
        JLabel scoreLbl = new JLabel("SCORE: " + _score);

        scoreLbl.setFont(new Font("Arial", Font.BOLD, 30));
        scoreP.add(scoreLbl);

        // PUT ALL THREE TOGETHER
        JPanel scoringP = new JPanel();
        scoringP.setLayout(new GridLayout(1,3));

        scoringP.add(correctP);
        scoringP.add(mistakesP);
        scoringP.add(scoreP);

        add(scoringP);

    } // end constructor

    private void set_ansArr() {
        _ansButtonsArr = new JButton[4];
        for (int i=0; i<4; i++){
//            System.out.println(_question.get_answerAtIndex(i));
            _ansButtonsArr[i] = new JButton(_question.get_answerAtIndex(i));
        }
    }

}
