package be.ordina.talkingstatues.Monument.Model;

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

    public Monument() {
    }

    public Monument(@NotEmpty List<Information> information, @NotEmpty Double longitude, @NotEmpty Double latitude,@NotEmpty String area) {
        this.information = information;
        this.longitude = longitude;
        this.latitude = latitude;
        this.area = area;
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
}
