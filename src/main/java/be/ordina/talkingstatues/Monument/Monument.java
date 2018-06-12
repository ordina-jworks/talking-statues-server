package be.ordina.talkingstatues.Monument;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Document(collection = "monuments")
public class Monument {
    @Id
    private String id;
    @NotEmpty
    private List<Information> information;
    @NotEmpty
    private Double longitude;
    @NotEmpty
    private Double latitude;
    @NotEmpty
    private String area;
    private String imageRef;

    private String picture;

    public Monument() {
    }

    public Monument(@NotEmpty List<Information> information,@NotEmpty Double latitude,@NotEmpty Double longitude, @NotEmpty String area, String picture) {
        this.information = information;
        this.latitude = latitude;
        this.longitude = longitude;
        this.area = area;
        this.picture = picture;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Information> getInformation() {
        return information;
    }

    public void setInformation(List<Information> information) {
        this.information = information;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}
