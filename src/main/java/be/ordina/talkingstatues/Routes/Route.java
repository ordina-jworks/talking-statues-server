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
}
