package com.hieugmail.hieu.ui.maps;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;

public class LocationHelper implements LocationListener {
    private OnLocationUpdate mListener;
    private LocationManager mLocationManageService;
    private Location mLocation;
    private String mBestProvider;
    private final Context mContext;
    private boolean isListening = false;
    // If we set isLocationFound to true, we will stop handler GPS every time.
    private boolean isLocationFound = false;

    /**
     * This class will callback after finish get Location.
     */
    public interface OnLocationUpdate {
        void onLocationUpdated(Location location);
    }

    public LocationHelper(Context context) {
        this.mContext = context;
    }

    public void addRequestListener(@NonNull OnLocationUpdate listener) {
        this.mListener = listener;
        // Get the location manager
        this.mLocationManageService = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        mBestProvider = mLocationManageService.getBestProvider(criteria, true);
        if (mBestProvider == null) {
            mBestProvider = LocationManager.NETWORK_PROVIDER;
        }
        mLocation = mLocationManageService.getLastKnownLocation(mBestProvider);
        startListen(mBestProvider);
    }

    /**
     * Make request Location from Best location to bad location.
     * User can request GPS -> NetWork -> Passive Provider
     * If can not found location, this request will get Location from last History
     * It call {#startListen(provider)}
     */
    private void requestLocation() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!isLocationFound && isListening) {
                    switch (mBestProvider) {
                        case LocationManager.GPS_PROVIDER:
                            stopListen();
                            mBestProvider = LocationManager.NETWORK_PROVIDER;
                            mLocation = mLocationManageService.getLastKnownLocation(mBestProvider);
                            startListen(mBestProvider);  // find by GPS the meantime with GPS_PROVIDER
                            break;
                        case LocationManager.NETWORK_PROVIDER:
                            stopListen();
                            mBestProvider = LocationManager.PASSIVE_PROVIDER;
                            mLocation = mLocationManageService.getLastKnownLocation(mBestProvider);
                            startListen(mBestProvider);  // find by GPS the meantime with NETWORK_PROVIDER
                            break;
                        case LocationManager.PASSIVE_PROVIDER:
                            stopListen();
                            quickFix();                  // get Location from last History
                            break;
                    }
                }
            }
        }, 500);
    }


    /**
     * Make the tracker start listening for location updates
     */
    private void startListen(String provider) {
        if (!this.isListening) {
            this.isListening = true;
            this.mLocationManageService.requestLocationUpdates(provider, 0, 0, this);
        }
        requestLocation();
    }


    /**
     * Make the tracker stops listening for location updates
     */
    private void stopListen() {
        if (isListening) {
            mLocationManageService.removeUpdates(this);
            this.isListening = false;
        }
    }

    /**
     * Best effort, it calls {@link #onLocationChanged(Location)} with static field named {@link #mLocation} if it is not null
     */
    private void quickFix() {
        if (mLocation != null) {
            onLocationChanged(mLocation);
        }
    }

    /**
     * This request will update from here, we can get location and callback onLocationUpdated
     */
    @Override
    public void onLocationChanged(Location location) {
        isLocationFound = true;
        mLocationManageService.removeUpdates(this);     // Remove handler location from #requestLocation
        mListener.onLocationUpdated(location);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
