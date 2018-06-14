package be.ordina.talkingstatues.config;

import be.ordina.talkingstatues.monument.Information;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Document(collection = "configurations")
public class Configuration {

    public static final String GOOGLE_API_KEY = "GOOGLE_API_KEY";

    @Id
    private String id;

    @NotEmpty
    private String value;


    public Configuration(@NotEmpty String id, @NotEmpty String value) {
        this.id = id;
        this.value = value;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
