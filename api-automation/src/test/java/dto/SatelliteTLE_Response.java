package dto;

import lombok.Data;

@Data
public class SatelliteTLE_Response {

    private String requested_timestamp;

    private String tle_timestamp;

    private String id;

    private String name;

    private String header;

    private String line1;

    private String line2;

}
