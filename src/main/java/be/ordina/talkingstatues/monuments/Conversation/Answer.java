package be.ordina.talkingstatues.monuments.Conversation;

import javax.validation.constraints.NotEmpty;

public class Answer {

    @NotEmpty
    private String answer;

    public Answer(@NotEmpty String answer) {
        this.answer = answer;
    }

    public String getAnswer() {
        return answer;
    }

    public boolean matches(String regex) {
        return this.answer.matches(regex);
    }
}
