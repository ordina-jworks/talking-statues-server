package be.ordina.talkingstatues.routes;

import org.apache.catalina.User;

public class UserLocation {
    Double longitude;
    Double latitude;

    public UserLocation(){

    }
    public UserLocation(Double longitude, Double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }
}
