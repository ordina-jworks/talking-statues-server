package be.ordina.talkingstatues.monuments;

import be.ordina.talkingstatues.monuments.Conversation.Conversation;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Information {
    @NotEmpty
    private Language language;
    @NotEmpty
    @JsonView(Aspects.MinimalMonumentAspect.class)
    private String name;
    @NotEmpty
    private String description;

    private List<Conversation> conversations;

    public Information() {
    }

    public Information(@NotEmpty Language language, @NotEmpty String name, @NotEmpty String description, List<Conversation> conversations) {

        this.language = language;
        this.name = name;
        this.description = description;
        this.conversations = conversations;
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

    public List<Conversation> getConversations() {
        return conversations;
    }

    public void setConversations(List<Conversation> conversations) {
        this.conversations = conversations;
    }

    public String toString() {
        return this.name;
    }
}