package mirai.checkwork.common;

import lombok.Data;

@Data
public class Geolocation {
    private double longitude;
    private double latitude;

    public Geolocation(double longitude, double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }
}
