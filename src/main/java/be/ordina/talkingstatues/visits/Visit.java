package be.ordina.talkingstatues.visits;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotEmpty;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Document(collection = "visits")
public class Visit {

    private ObjectId id;

    @NotEmpty
    @Field("user_id")
    private String userId;

    @NotEmpty
    @Field("monument_id")
    private String monumentId;

    @NotEmpty
    @Field("visitation_date")
    private Date now;

    public Visit(@NotEmpty String userId, @NotEmpty String monumentId) {
        this.userId = userId;
        this.monumentId = monumentId;
        this.now = new Date();
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
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
