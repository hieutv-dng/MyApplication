package com.hieugmail.hieu.ui.maps;

import android.app.Activity;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

/**
 * This function will check Google Play Service already install in device or not yet.
 *
 * @return function will return true/false have google service in device
 */
public final class GoogleServiceUtil {

    private GoogleServiceUtil() {
    }

    public static boolean isGooglePlayServicesAvailable(Activity activity) {
        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(activity);
        if (ConnectionResult.SUCCESS == status) {
            return true;
        } else {
            GooglePlayServicesUtil.getErrorDialog(status, activity, 0).show();
            return false;
        }
    }
}
