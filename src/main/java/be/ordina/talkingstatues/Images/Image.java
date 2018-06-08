package be.ordina.talkingstatues.Images;

import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSInputFile;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.gridfs.GridFsResource;

import javax.validation.constraints.NotEmpty;
@Document(collection = "images")
public class Image {
    @Id
    private String id;
    @NotEmpty
    @Field("monument_id")
    private String monumentId;
    @NotEmpty
    private GridFSInputFile image;

    public Image(@NotEmpty String monumentId, GridFSInputFile image) {
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

    public GridFSInputFile getImage() {
        return image;
    }

    public void setImage(GridFSInputFile image) {
        this.image = image;
    }
}
