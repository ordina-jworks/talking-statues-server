package be.ordina.talkingstatues.monuments;

import be.ordina.talkingstatues.monuments.Conversation.Answer;
import be.ordina.talkingstatues.monuments.Conversation.Conversation;
import be.ordina.talkingstatues.monuments.Conversation.Question;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.Collections.singletonList;

public class MonumentTestUtils {

    private MonumentTestUtils() {
    }

    public static final String MON_ID = "monId";
    public static final String NL = "NL";
    public static final String AREA = "randomArea";

    public static final String QUESTION_VALUE = "what's your name?";
    public static final String ANSWER_VALUE = "My name is David.";
    public static final Question QUESTION = new Question(QUESTION_VALUE);
    public static final Answer ANSWER = new Answer(ANSWER_VALUE);

    public static List<Monument> buildRandomMonuments(String area, Language language, int number) {
        ArrayList<Monument> monuments = new ArrayList<>();
        for (int i = 0; i < number; i++) {
            monuments.add(new Monument(getInfo(language), 5.00, 5.00, area, "picture"));
        }
        return monuments;
    }

    public static List<Monument> getMonumentsInDifferentAreas() {
        Monument monument1 = new Monument(getInfo(Language.NL), 5.00, 5.00, "hier", "pic");
        Monument monument2 = new Monument(getInfo(Language.NL), 5.00, 5.00, "daar", "pic");
        Monument monument3 = new Monument(getInfo(Language.NL), 5.00, 5.00, "hier", "pic");
        return Arrays.asList(monument1, monument2, monument3);
    }

    public static List<Information> getInfo(Language language) {
        List<Information> infList = new ArrayList<>();
        infList.add(new Information(language, "name", "description", singletonList(new Conversation(QUESTION, ANSWER))));
        return infList;
    }

}
