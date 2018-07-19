package be.ordina.talkingstatues.monuments.Conversation;


import javax.validation.constraints.NotEmpty;

public class Conversation {
    @NotEmpty
    private Question question;
    @NotEmpty
    private Answer answer;


    public Conversation(@NotEmpty Question question, @NotEmpty Answer answer) {
        this.question = question;
        this.answer = answer;
    }

    public Question getQuestion() {
        return question;
    }

    public Answer getAnswer() {
        return answer;
    }
}
