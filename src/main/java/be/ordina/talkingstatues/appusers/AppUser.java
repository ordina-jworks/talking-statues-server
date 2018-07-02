package be.ordina.talkingstatues.appusers;

import be.ordina.talkingstatues.routes.Route;
import be.ordina.talkingstatues.visits.Visit;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "appusers")
public class AppUser {

    @Id
    private String id;

    @NotEmpty
    private String handle;
    private String name;
    private String lastName;

    @DBRef
    private List<Route> routes;

    @DBRef
    private List<Visit> visits = new ArrayList<>();


    public AppUser(@NotEmpty String handle, String name, String lastName) {
        this.handle = handle;
        this.name = name;
        this.lastName = lastName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHandle() {
        return handle;
    }

    public void setHandle(String handle) {
        this.handle = handle;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<Route> getRoutes() {
        return routes;
    }

    public void setRoutes(List<Route> routes) {
        this.routes = routes;
    }

    public List<Visit> getVisits() {
        return visits;
    }

    public void setVisits(List<Visit> visits) {
        this.visits = visits;
    }

    public void addVisit(Visit newVisit) {
        if (this.visits == null) visits = new ArrayList<>();
        this.visits.add(newVisit);
    }


    public String toString(){
        return this.name + " " + this.lastName;

    }
}