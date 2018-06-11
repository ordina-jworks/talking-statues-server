package be.ordina.talkingstatues.Monument;

public class SwipeMonument {
    String id;
    String name;

    public SwipeMonument(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public SwipeMonument() {
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
}
