import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;


/**
 * This main Panel defines and contain all the subpanels and other components
 */
public class MainTriviaPanel extends JPanel {
    final int TIME_LIMIT = 10;

    Questionnaire _questionaire;
    private Question _question;
    private int _chosenAnswer;

    private JLabel _questionLbl;

    private int _correctCount;
    private int _mistakesCount;
    private int _scoreCount;
    private int _secondsCount;

    private JPanel _timerP;
    private JPanel _correctP;
    private JPanel _mistakesP;

    private JLabel _scoreLbl;
    private JLabel _correctLbl;
    private JLabel _mistakeLbl;
    private JLabel _timerLbl;


    private JButton _exitBtn;
    private JButton _startBtn;
    private JButton _nextQuestBtn;
    private JButton _ansButtonsArr[]  = new JButton[4];

    private JLabel _msgLbl1;
    private JLabel _msgLbl2;

    private TopButtonListener _topBtnLis;
    private AnswerButtonListener _ansBtnLis;

    private TimerListener _timerLis;
    private Timer _timer;


    /**
     * Constructor
     * @param filePath - to build a questionnaire out of
     * @throws FileNotFoundException
     */
    public MainTriviaPanel(String filePath) throws FileNotFoundException{

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        _topBtnLis = new TopButtonListener();
        _ansBtnLis = new AnswerButtonListener();
        _timerLis = new TimerListener();
        _timer = new Timer(1000, _timerLis);

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
        _exitBtn.addActionListener(_topBtnLis);


        // START BUTTON
        _startBtn = new JButton("START GAME");
        _startBtn.setForeground(Color.white);
        _startBtn.setBackground(Color.decode("#245501"));
        _startBtn.setFont(new Font("Arial", Font.BOLD, 20));
        _startBtn.addActionListener(_topBtnLis);


        // NEXT QUESTION BUTTON
        _nextQuestBtn = new JButton("NEXT QUESTION");
        _nextQuestBtn.setFont(new Font("Arial", Font.BOLD, 20));
        _nextQuestBtn.addActionListener(_topBtnLis);
        _nextQuestBtn.setEnabled(false);


        //PANEL OF TOP BOTTONS ONLY
        JPanel topButtons = new JPanel();
        topButtons.setLayout(new GridLayout(1,3, 10, 10));
        topButtons.setPreferredSize(new Dimension(1200, 80));
        topButtons.add(_exitBtn);
        topButtons.add(_startBtn);
        topButtons.add(_nextQuestBtn);


        // TOP PANEL - OF TOP BOTTONS + TITLE
        JPanel topP = new JPanel();
        topP.setPreferredSize(new Dimension(1000, 160));
        topP.add(titleP);
        topP.add(topButtons);

        add(topP);


        // ******** TRIVIA CARD *************
        //QUESTION LABEL - TOP OF CARD
        JButton answer ;
        _questionLbl = new JLabel(_question.get_question(), JLabel.CENTER);  // get the text of the randomly chosen question
        _questionLbl.setForeground(Color.decode("#0f4c5c"));
        _questionLbl.setFont(new Font("Arial", Font.BOLD, 25));
        _questionLbl.setPreferredSize(new Dimension(1000, 80));

        initializeButtons();

        JPanel answersP = new JPanel();  // answeres in a separate panel
        answersP.setLayout(new GridLayout(4,1,10,10));

        //ANSWERS BUTTON - BOTTOM CARD
        for (int i=0; i<4; i++){
            answer =_ansButtonsArr[i];
            answer.setForeground(Color.decode("#4a5759"));
            answer.setFont(new Font("Arial", Font.BOLD, 20));
            answer.addActionListener(_ansBtnLis);
            answersP.add(answer);
        }
        JPanel cardP = new JPanel();
        cardP.setBackground(Color.CYAN);
        cardP.setLayout(new BoxLayout(cardP, BoxLayout.Y_AXIS));
        cardP.add(_questionLbl);
        cardP.add(answersP);


        add(cardP);

        // ******************** TIMER **********************
        //TIMER PANEL

        _timerP = new JPanel();
        _secondsCount = TIME_LIMIT;
        _timerLbl = new JLabel("Time: "+ _secondsCount, JLabel.CENTER);
        _timerLbl.setPreferredSize(new Dimension(1000, 80));
        _timerLbl.setFont(new Font("Arial", Font.BOLD, 50));
        _timerP.add(_timerLbl);

        add(_timerP);

        // ******************** MESSAGE  **********************

        JPanel _msgP = new JPanel();
        _msgLbl1 = new JLabel("WELCOME!", JLabel.CENTER);
        _msgLbl1.setPreferredSize(new Dimension(1000, 80));
        _msgLbl1.setFont(new Font("Arial", Font.BOLD, 50));

        _msgLbl2 = new JLabel("Press START GAME to begin", JLabel.CENTER);
        _msgLbl2.setPreferredSize(new Dimension(1000, 60));
        _msgLbl2.setFont(new Font("Arial", Font.BOLD, 50));
        _msgLbl2.setOpaque(true);
        _msgLbl2.setBackground(Color.decode("#FFB703"));
        _msgP.add(_msgLbl1);
        _msgP.add(_msgLbl2);

        add(_msgP);



        // ****************** SCORING PANEL******************

        //COUNTERS AND SCORES PANEL
        initializeCounters();
        // HANDLE CORRECT ANSWRES DISPLAY
        _correctP = new JPanel();
        _correctLbl = new JLabel("CORRECT: " + _correctCount);

        _correctLbl.setForeground(Color.decode("#2b9348"));
        _correctLbl.setFont(new Font("Arial", Font.BOLD, 30));
        _correctLbl.setSize(new Dimension(1000, 80));
        _correctP.add(_correctLbl);

        // HANDLE WRONG ANSWERS DISPLAY
        _mistakesP = new JPanel();
        _mistakeLbl = new JLabel("WRONG: " + _mistakesCount);
        _mistakeLbl.setForeground(Color.decode("#e71d36"));
        _mistakeLbl.setFont(new Font("Arial", Font.BOLD, 30));
        _mistakeLbl.setSize(new Dimension(1000, 80));
        _mistakesP.add(_mistakeLbl);

        // HANDLE SCORES ANSWRES DISPLAY
        JPanel scoreP = new JPanel();
        _scoreLbl = new JLabel("SCORE: " + _scoreCount);
        _scoreLbl.setSize(new Dimension(1000, 80));
        _scoreLbl.setFont(new Font("Arial", Font.BOLD, 30));
        scoreP.add(_scoreLbl);


        // PUT ALL THREE TOGETHER IN SCORING PANEL
        JPanel scoringP = new JPanel();
        scoringP.setLayout(new GridLayout(1,3));
        scoringP.add(_mistakesP);
        scoringP.add(_correctP);
        scoringP.add(scoreP);
        add(scoringP);

    } ////////////////////////////////////////////////////////////////// END CONSTRUCTOR ////////////////////////////////////////////////////////////////////////


    /**
     * Gets the answer from the pressed button and runs the matching reaction
     * @param pressedButton
     */
    private void getAnswer(JButton pressedButton) {
        _timer.stop();
        resetTimer();
        actionAllowed(false); //disables reanswering
        _chosenAnswer = getAnswerIndex(pressedButton);

        if (_chosenAnswer == _question.get_correctIndex() )                                          // ********** CORRECT ANSWER **********
            displayCorrect(_chosenAnswer);
        else      // answer is wrong
            displayWrong(_chosenAnswer);                                                            // ********** WRONG ANSWER **********

        updateScoreDisplay();
    }

    /**
     * Turns Timer to start time and color
     */
    private void resetTimer() {
        _secondsCount = TIME_LIMIT;
        _timerLbl.setText("Time: "+ _secondsCount);
        _timerLbl.setForeground(Color.BLACK);

    }


    /**
     * Gets the index of the pressed button
     * @param pressedButton
     * @return
     */
    private int getAnswerIndex(JButton pressedButton) {
        int i;
        for (i=0; i < 4; i++)
            if( pressedButton == _ansButtonsArr[i] )
                break;
        return i;
    }


    /**
     * initiallizes a new game
     */
    private void startGame() {
        initializeCounters();
        _questionaire.reset(); // reset all questions to be unused
        _nextQuestBtn.setEnabled(true);
        startNewRound();
        updateScoreDisplay();
    }


    /**
     * Initializes new round (does not initialize counters)
     */
    private void startNewRound() {
        resetTimer();
        _timer.start();
        _msgLbl1.setText("Waiting for answer");
        _msgLbl1.setForeground(Color.BLACK);
        resetDisplay();   // return buttons color to neutral
        setNewQuestion();       // chooses a new question from stock
        actionAllowed(true);
    }


    /**
     * Sets a new question by choosing a random question and
     * setting the game screen to the new question
     */
    private void setNewQuestion() {
        _question = _questionaire.getRandomQuestion();
        _questionLbl.setText(_question.get_question());
        reset_ansButtonsArr(); // set buttons with next answers
    }


    /**
     * Updates display of all scoring
     */
    private void updateScoreDisplay() {
        _correctLbl.setText("CORRECT: " + _correctCount);
        _mistakeLbl.setText("WRONG: " + _mistakesCount);
        _scoreLbl.setText("SCORE: " + _scoreCount);

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

    /**
     * Runs changes that are wanted when a correct answer is pressed
     * @param answerIndex - the index of button pressed
     */
    public void displayCorrect(int answerIndex){
        _correctCount++;
        _scoreCount = _scoreCount + 10;
        _msgLbl1.setText("Correct! you earned 10 points");
        _msgLbl1.setForeground(Color.GREEN);
        _msgLbl2.setText("Press NEXT QUESTION to continue");
        JButton pressedButton = _ansButtonsArr[answerIndex];
        pressedButton.setForeground(Color.decode("#38B000")); // changes the buttons writing color
    }

    /**
     * Runs changes that are wanted when a wrong answer is pressed
     * @param answerIndex - the index of button pressed
     */
    private void displayWrong(int answerIndex) {
        boolean inTime = (_secondsCount>0);
        _mistakesCount ++;
        _scoreCount = _scoreCount - 5;
        _msgLbl1.setText("Wrong answer! you lost 5 points");
        _msgLbl1.setForeground(Color.RED);
        _msgLbl2.setText("Press NEXT QUESTION to continue");
        if (inTime) {
            JButton pressedButton = _ansButtonsArr[answerIndex];
            pressedButton.setForeground(Color.RED);        // changes the buttons writing color
        }
    }

    /**
     * Resets display of buttons and timer
     */
    private void resetDisplay() {
        for (int i=0; i<4; i++){
            _ansButtonsArr[i].setBackground(null);
            _ansButtonsArr[i].setForeground(null);
            _ansButtonsArr[i].setEnabled(true);
        }
        _timerLbl.setText("Time: "+ _secondsCount);
        _msgLbl2.setText("");
    }

    /**
     * Resets all counters to 0
     */
    private void initializeCounters() {
        _scoreCount = 0;
        _correctCount = 0;
        _mistakesCount = 0;
    }


    /**
     * Enables/Disables answer buttons
     * @param allow - defines whether to enable or disable buttons
     */
    private void actionAllowed(boolean allow) {
        for (int i=0; i<4; i++){
            if (allow)
                _ansButtonsArr[i].addActionListener(_ansBtnLis);
            else
                _ansButtonsArr[i].removeActionListener(_ansBtnLis);

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


    /**
     * Listener for the 3 buttons at the top of the frame
     */
    private class TopButtonListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {

            JButton pressedButton = (JButton) e.getSource();

            if (pressedButton == _exitBtn){                                                              //******** EXIT GAME *******
                System.exit(0);
            }
            else if (pressedButton == _startBtn){                                                        //******** START GAME *******
                startGame();
            }
            else if (pressedButton == _nextQuestBtn){                                                     //******** NEXT QUESTION  *******
                try {
                    startNewRound();
                }
                catch (Exception ex){
                    _msgLbl1.setText("NO MORE QUESTIONS");
                    _msgLbl1.setForeground(Color.PINK);
                    _msgLbl2.setText("Press START GAME to play again");
                    _timer.stop();
                }
            }


        }
    } // end of ButtonListener class

    /**
     * Listener to timer
     */
    private class TimerListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            if (_secondsCount >= 0) {

                resetDisplay();
                _secondsCount--;
                if (_secondsCount <=3)
                    _timerLbl.setForeground(Color.RED);

            }
            else{
                _timer.stop();
                _msgLbl1.setText("Time is up! you lost 5 points");
                _msgLbl1.setForeground(Color.RED);
                _msgLbl2.setText("Press NEXT QUESTION to continue");
            }
        }


    }


    // ************* ANSWER **********************

    /**
     * Listener to answers buttons
     */
    private class AnswerButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            JButton pressedButton = (JButton) e.getSource();
            getAnswer(pressedButton);
        }
    }
}
