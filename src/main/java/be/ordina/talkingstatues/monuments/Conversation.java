package be.ordina.talkingstatues.monuments;


import javax.validation.constraints.NotEmpty;

public class Conversation {
    @NotEmpty
    private String question;
    @NotEmpty
    private String answer;


    public Conversation(@NotEmpty String question, @NotEmpty String answer) {
        this.question = question;
        this.answer = answer;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }
}
