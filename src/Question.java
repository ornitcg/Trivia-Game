public class Question {
    private String _question;
    private String _answers[] = new String[4];
    private boolean _isUsed;



    private int _correctIndex;


    /**
     * Constructor
     * @param q
     * @param ans1
     * @param ans2
     * @param ans3
     * @param ans4
     */
    public Question(String q, String ans1, String ans2, String ans3, String ans4){
        _question = q;
        _isUsed = false;

        String answers[] = new String[4]; // temporary array
        int displayOrder[] = new int[4];

        answers[0] = ans1;
        answers[1] = ans2;
        answers[2] = ans3;
        answers[3] = ans4;
        set_displayOrder(displayOrder);
        setAnswersAtDisplayOrder(answers, displayOrder);
//        System.out.println(displayOrderToString(displayOrder)); // for debugging

    }

    /**
     * arranges the answers at their defined display order, in the class variable
     * @param answers - the initial order of answere as given in file, assuming the first one is the correct answer
     * @param displayOrder - the order in which the answers will be displayed on screen
     */
    private void setAnswersAtDisplayOrder(String[] answers, int[] displayOrder) {
        for(int i=0 ; i < 4 ; i++){
            _answers[i] = answers[displayOrder[i]];
            if (displayOrder[i] == 0)
                _correctIndex = i;
        }
    }

    /**
     * Used for debugging
     * @return
     */
    public String toString(){
        return _question + "\n" + _answers[0] + "\n" +_answers[1]+"\n"+_answers[2] +"\n" +_answers[3];
    }

    /**
     * Defines the order of answers on display of each question
     * @param displayOrder - array of display order. to fill in with indices of answers
     */
    private void set_displayOrder(int[] displayOrder) {
        int rand = (int)(Math.random() * 1000 ) % 4;
        int displayIndex;
        for(int i=0 ; i<4 ; i++){
            displayIndex = (i + rand) % 4;
            displayOrder[i] = displayIndex;
        }
    }


    /**
     * Gets the String of an answer at a specific index of answers array
     * @param ind
     * @return
     */
    public String get_answerAtIndex(int ind) {
        return _answers[ind];
    }


    public String get_question() {
        return _question;
    }


    public boolean isUsed() {
        return _isUsed;
    }


    public void set_isUsed(boolean _isUsed) {
        this._isUsed = _isUsed;
    }

    public int get_correctIndex() {
        return _correctIndex;
    }
}
