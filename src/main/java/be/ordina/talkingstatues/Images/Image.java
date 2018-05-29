package be.ordina.talkingstatues.Images;

import com.mongodb.gridfs.GridFS;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotEmpty;
@Document(collection = "images")
public class Image {
    @Id
    private String id;
    @NotEmpty
    @Field("monument_id")
    private String monumentId;
    @NotEmpty
    private GridFS image;

    public Image(@NotEmpty String monumentId, @NotEmpty GridFS image) {
        this.monumentId = monumentId;
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMonumentId() {
        return monumentId;
    }

    public void setMonumentId(String monumentId) {
        this.monumentId = monumentId;
    }

    public GridFS getImage() {
        return image;
    }

    public void setImage(GridFS image) {
        this.image = image;
    }
}
