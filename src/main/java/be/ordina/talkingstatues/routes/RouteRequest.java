package be.ordina.talkingstatues.routes;

import javax.validation.constraints.NotEmpty;
import java.util.List;

public class RouteRequest {
    @NotEmpty
    private String name;
    private List<String> locations;

    public RouteRequest() {
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
