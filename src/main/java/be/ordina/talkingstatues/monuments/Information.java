package be.ordina.talkingstatues.monuments;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Information {
    @NotEmpty
    private Language language;
    @NotEmpty @JsonView(Aspects.MinimalMonumentAspect.class)
    private String name;
    @NotEmpty
    private String description;
    private List<Question> questions;

    public Information() {
    }

    public Information(@NotEmpty Language language, @NotEmpty String name, @NotEmpty String description, List<Question> questions) {

        this.language = language;
        this.name = name;
        this.description = description;
        this.questions = questions;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Question> getQuestion() {
        return questions;
    }

    public void setQuestion(List<Question> questions) {
        this.questions = questions;
    }
}