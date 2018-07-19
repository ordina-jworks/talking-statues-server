package be.ordina.talkingstatues.monuments;

import be.ordina.talkingstatues.monuments.Conversation.Answer;
import be.ordina.talkingstatues.monuments.Conversation.Question;

public class MonumentTestConstants {

    private MonumentTestConstants() {
    }

    public static final String MON_ID = "monId";
    public static final String NL = "NL";
    public static final String AREA = "randomArea";

    public static final String QUESTION_VALUE = "what's your name?";
    public static final String ANSWER_VALUE = "My name is David.";
    public static final Question QUESTION = new Question(QUESTION_VALUE);
    public static final Answer ANSWER = new Answer(ANSWER_VALUE);

}
