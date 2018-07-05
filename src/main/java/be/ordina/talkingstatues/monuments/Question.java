package be.ordina.talkingstatues.monuments;


import javax.validation.constraints.NotEmpty;

public class Question {
    @NotEmpty
    private String question;
    @NotEmpty
    private String answer;

    public Question() {

    }

    public Question(@NotEmpty String question, @NotEmpty String answer) {
        this.question = question;
        this.answer = answer;
    }
    public String getQuestion() {
        return question;
    }


    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
