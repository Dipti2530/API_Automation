package dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;


@Data
public class SatellitePositionResponse {

    private String name;

    private String id;

    private String latitude;

    private String longitude;

    private String altitude;

    private String velocity;

    private String visibility;

    private String footprint;

    private String timestamp;

    private String daynum;

    private String solar_lat;

    private String solar_lon;

    private String units;

}


