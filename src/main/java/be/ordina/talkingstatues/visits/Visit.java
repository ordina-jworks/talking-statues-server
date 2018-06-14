package be.ordina.talkingstatues.visits;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotEmpty;

@Document(collection = "visits")
public class Visit {
    @Id
    private String id;
    @NotEmpty
    @Field("user_id")
    private String userId;
    @NotEmpty
    @Field("monument_id")
    private String monumentId;

    public Visit(@NotEmpty String userId, @NotEmpty String monumentId) {
        this.userId = userId;
        this.monumentId = monumentId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMonumentId() {
        return monumentId;
    }

    public void setMonumentId(String monumentId) {
        this.monumentId = monumentId;
    }
}
