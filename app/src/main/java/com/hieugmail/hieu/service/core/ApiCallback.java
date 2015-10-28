package com.hieugmail.hieu.service.core;

import android.util.Log;

import retrofit.Callback;
import retrofit.RetrofitError;

/**
 * A custom Callback.
 */
public abstract class ApiCallback<T> implements Callback<T> {
    private static final String TAG = ApiCallback.class.getSimpleName();

    public abstract void failure(RetrofitError retrofitError, ApiError apiError);

    @Override
    public void failure(RetrofitError error) {
        Log.e(TAG, "error:" + error.getKind());
        switch (error.getKind()) {
            case NETWORK:
                failure(error, ApiError.ERROR_NETWORK);
                break;
            case CONVERSION:
                failure(error, ApiError.ERROR_CONVERSION);
                break;
            default:
                failure(error, ApiError.ERROR_PLEASE_TRY_LATER);
        }
    }
}
