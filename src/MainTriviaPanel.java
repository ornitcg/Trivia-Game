import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;

public class MainTriviaPanel extends JPanel {

    Questionnaire _questionaire;
    private Question _question;
    private int _chosenAnswer;

    private JLabel _questionLbl;

    private int _correctCount;
    private int _mistakesCount;
    private int _score;
    private JLabel _scoreLbl;
    private JLabel _correctLbl;
    private JLabel _mistakeLbl;


    private JButton _exitBtn;
    private JButton _startBtn;
    private JButton _nextQuestBtn;
    private JButton _ansButtonsArr[]  = new JButton[4];

    private String _msgToUser;
    private ButtonListener _lis ;


    public MainTriviaPanel(String filePath) throws FileNotFoundException{

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        _lis = new ButtonListener();
        // ******** QUESTIONS SET **************
        try {
            _questionaire = new Questionnaire(filePath);
        }
        catch (FileNotFoundException e){
            throw(e);
        }
        _question = _questionaire.getRandomQuestion();


        // ******** TITLE *************

        JLabel titleLbl = new JLabel("TRIVIA GAME");
        titleLbl.setForeground(Color.decode("#2ec4b6"));
        titleLbl.setFont(new Font("Arial", Font.BOLD, 50));

        JPanel titleP = new JPanel();
        titleP.setPreferredSize(new Dimension(1200, 70));
        titleP.setBackground(Color.decode("#8f2d56"));
        titleP.add(titleLbl);

        // ******** TOP BUTTONS *************

        // EXIT BUTTON
        _exitBtn = new JButton("EXIT");
        _exitBtn.setForeground(Color.white);
        _exitBtn.setBackground(Color.RED);
        _exitBtn.setFont(new Font("Arial", Font.BOLD, 20));
        _exitBtn.addActionListener(_lis);


        // START BUTTON


        _startBtn = new JButton("START GAME");
        _startBtn.setForeground(Color.white);
        _startBtn.setBackground(Color.decode("#245501"));
        _startBtn.setFont(new Font("Arial", Font.BOLD, 20));
        _startBtn.addActionListener(_lis);


        // NEXT QUESTION BUTTON

        _nextQuestBtn = new JButton("NEXT QUESTION");
        _nextQuestBtn.setFont(new Font("Arial", Font.BOLD, 20));
        _nextQuestBtn.addActionListener(_lis);
        _nextQuestBtn.setEnabled(false);



        JPanel topButtons = new JPanel();
        topButtons.setLayout(new GridLayout(1,3, 10, 10));
        topButtons.setPreferredSize(new Dimension(1200, 80));


        topButtons.add(_exitBtn);
        topButtons.add(_startBtn);
        topButtons.add(_nextQuestBtn);


        // TOP PANEL

        JPanel topP = new JPanel();
        topP.setSize(new Dimension(1000, 160));

        topP.add(titleP);
        topP.add(topButtons);

        add(topP);


        // ******** TRIVIA CARD *************
        JButton answer ;
        _questionLbl = new JLabel(_question.get_question());  // get the text of the randomly chosen question
        _questionLbl.setForeground(Color.decode("#0f4c5c"));
        _questionLbl.setFont(new Font("Arial", Font.BOLD, 25));

        _questionLbl.setPreferredSize(new Dimension(1000, 150));

        initializeButtons();

        JPanel answersP = new JPanel();  // answeres in a separate panel
        answersP.setLayout(new GridLayout(4,1,10,10));
        for (int i=0; i<4; i++){
            answer =_ansButtonsArr[i];
            answer.addActionListener(_lis);
            answer.setForeground(Color.decode("#4a5759"));
            answer.setFont(new Font("Arial", Font.BOLD, 20));
            answersP.add(answer);
        }

        JPanel cardP = new JPanel();
        cardP.setBackground(Color.CYAN);

        cardP.setLayout(new BoxLayout(cardP, BoxLayout.Y_AXIS));

        cardP.add(_questionLbl);
        cardP.add(answersP);


        add(cardP);


        // ******************** TIMER **********************


        // ****************** SCORING PANEL******************

        initializeCounters();
        // HANDLE CORRECT ANSWRES DISPLAY
        JPanel correctP = new JPanel();
        _correctLbl = new JLabel("CORRECT: " + _correctCount);
        _correctLbl.setForeground(Color.decode("#2b9348"));
        _correctLbl.setFont(new Font("Arial", Font.BOLD, 30));
        correctP.add(_correctLbl);

        // HANDLE WRONG ANSWERS DISPLAY
        JPanel mistakesP = new JPanel();
        _mistakeLbl = new JLabel("WRONG: " + _mistakesCount);
        _mistakeLbl.setForeground(Color.decode("#e71d36"));
        _mistakeLbl.setFont(new Font("Arial", Font.BOLD, 30));
        mistakesP.add(_mistakeLbl);

        // HANDLE SCORES ANSWRES DISPLAY
        JPanel scoreP = new JPanel();
        _scoreLbl = new JLabel("SCORE: " + _score);

        _scoreLbl.setFont(new Font("Arial", Font.BOLD, 30));
        scoreP.add(_scoreLbl);

        // PUT ALL THREE TOGETHER
        JPanel scoringP = new JPanel();
        scoringP.setLayout(new GridLayout(1,3));

        scoringP.add(mistakesP);
        scoringP.add(correctP);
        scoringP.add(scoreP);

        add(scoringP);

    } // end constructor



    private class ButtonListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            JButton pressedButton = (JButton) e.getSource();
            if (pressedButton == _exitBtn){     //******** EXIT GAME *******
                System.exit(0);
            }
            else if (pressedButton == _startBtn){  //******** START GAME *******
                actionAllowed(true);
                initializeCounters();
                resetButtonsDisplay();
                _questionaire.reset(); // reset all questions to be unused
                setNewQuestion();
                _nextQuestBtn.setEnabled(true);
            }
            else if (pressedButton == _nextQuestBtn){   //******** NEXT QUESTION  *******
                actionAllowed(true);
                resetButtonsDisplay();   // return buttons color to neutral
                setNewQuestion();       // chooses a new question from stock
            }
            else{  // answer button was pressed
                int i;
                actionAllowed(false);

                for ( i=0; i < 4; i++){
                    if( pressedButton == _ansButtonsArr[i] ) {
                        System.out.println("pressed: " + pressedButton.getText()); // i stops at index of chosen answer
                        _chosenAnswer = i;
                        break;
                    }
                }
                if (_chosenAnswer == _question.get_correctIndex() ){ // answer is correct

                    displayCorrect(_chosenAnswer);
                }
                else{      // answer is wrong
                    displayWrong(_chosenAnswer);
                }
                updateScoreDisplay();

            }

        }


    } // end of ButtonListener class

    private void updateScoreDisplay() {
        _correctLbl.setText("CORRECT: " + _correctCount);
        _mistakeLbl.setText("WRONG: " + _mistakesCount);
        _scoreLbl.setText("SCORE: " + _score);

    }

    private void setNewQuestion() {
        _question = _questionaire.getRandomQuestion();
        _questionLbl.setText(_question.get_question());
        reset_ansButtonsArr(); // set buttons with next answers
    }

    /**
     * Sets question to message, ans answer buttons to be empty
     */
    private void initializeButtons(){
        _questionLbl.setText("PRESS START GAME");
        for (int i=0; i<4; i++){
            _ansButtonsArr[i] = new JButton("");
        }
    }


    public void displayCorrect(int i){
        _correctCount ++;
        _score = _score + 10;
        JButton pressedButton = _ansButtonsArr[i];
        System.out.println(pressedButton.getText());
        pressedButton.setForeground(Color.decode("#38B000"));
    }

    private void displayWrong(int i) {
        _mistakesCount ++;
        _score = _score - 5;
        JButton pressedButton = _ansButtonsArr[i];
        System.out.println(pressedButton.getText());
        pressedButton.setForeground(Color.RED);
    }


    private void resetButtonsDisplay() {
        for (int i=0; i<4; i++){
            _ansButtonsArr[i].setBackground(null);
            _ansButtonsArr[i].setForeground(null);
            _ansButtonsArr[i].setEnabled(true);

        }
    }

    /**
     * Resets all counters to 0
     */
    private void initializeCounters() {
        _score = 0;
        _correctCount = 0;
        _mistakesCount = 0;
    }



    private void actionAllowed(boolean allow) {
        for (int i=0; i<4; i++){
                if (allow)
                    _ansButtonsArr[i].addActionListener(_lis);
                else {
                    _ansButtonsArr[i].removeActionListener(_lis);
//                    if (i == _chosenAnswer)
//                        _ansButtonsArr[i].setEnabled(false);
                }
        }
    }

    /**
     * Resets all buttons with next ansers
     */
    private void reset_ansButtonsArr() {
        for (int i=0; i<4; i++){
            _ansButtonsArr[i].setText(_question.get_answerAtIndex(i));

        }
    }

}
