package be.ordina.talkingstatues.monuments.Conversation;

import javax.validation.constraints.NotEmpty;

public class Question {

    @NotEmpty
    private String question;

    public Question(@NotEmpty String question) {
        this.question = question;
    }

    public String getQuestion() {
        return question;
    }

    public boolean matches(String regex) {
        return this.question.matches(regex);
    }
}
