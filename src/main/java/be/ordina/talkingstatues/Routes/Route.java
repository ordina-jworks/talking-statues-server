package be.ordina.talkingstatues.Routes;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Document(collection = "routes")
public class Route {
    @Id
    private String id;
    @NotEmpty
    private String name;
    private List<String> locations;

    public Route() {
    }

    public Route(@NotEmpty String name, List<String> locations) {
        this.name = name;
        this.locations = locations;
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

    public List<String> getLocations() {
        return locations;
    }

    public void setLocations(List<String> locations) {
        this.locations = locations;
    }
}
