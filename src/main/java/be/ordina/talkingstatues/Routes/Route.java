package be.ordina.talkingstatues.Routes;

import be.ordina.talkingstatues.Monument.Monument;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Document(collection = "routes")
public class Route {
    @Id
    private String id;
    @NotEmpty
    private String name;
    @DBRef
    private List<Monument> monuments;

    public Route() {
    }

    public Route(@NotEmpty String name, List<Monument> monuments) {
        this.name = name;
        this.monuments=monuments;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Monument> getMonuments() {
        return monuments;
    }

    public void setMonuments(List<Monument> monuments) {
        this.monuments = monuments;
    }
}
