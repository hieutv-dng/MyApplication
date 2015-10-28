package com.hieugmail.hieu.service.googleapi;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Class
 *
 * @author BinhDT
 */
public interface GoogleService {
    /**
     * Response location
     */
    @GET("/maps/api/geocode/json")
    void getLocation(@Query("address") String address,
                     Callback<AddressResponse> callback);
    /**
     * Get Nearby places with location current of user
     */
}
