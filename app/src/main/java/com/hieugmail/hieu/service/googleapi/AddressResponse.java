package com.hieugmail.hieu.service.googleapi;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Class using covert json to class object in api of google.
 */
@Data
@AllArgsConstructor(suppressConstructorProperties = true)
public class AddressResponse {
    public List<Result> results;
    public String status;

    @Data
    public static class Result {
        public Geometry geometry;
    }

    @Data
    public class Geometry {
        public Location location;

        @Data
        public class Location {
            public double lat;
            public double lng;
        }
    }
}
