package com.hieugmail.hieu.service.googleapi;

import retrofit.RestAdapter;

/**
 * Class using run api of google support.
 */
public class ApiGoogle {
    private static final String GOOGLE_MAP_HOST = "https://maps.googleapis.com/";

    private static ApiGoogle sApi;

    private GoogleService mGoogleService;

    private ApiGoogle() {
    }

    public static ApiGoogle getInstance() {
        if (sApi == null) {
            sApi = new ApiGoogle();
        }
        return sApi;
    }

    public GoogleService getRequestGoogleMap() {
        if (mGoogleService == null) {
            RestAdapter restAdapter = new RestAdapter.Builder().setLogLevel(RestAdapter.LogLevel.NONE)
                    .setEndpoint(GOOGLE_MAP_HOST).build();
            mGoogleService = restAdapter.create(GoogleService.class);
        }
        return mGoogleService;
    }
}
