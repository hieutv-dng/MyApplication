package com.hieugmail.hieu.service.core;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hieugmail.hieu.service.ApiService;
import com.hieugmail.hieu.service.Environment;
import com.squareup.okhttp.OkHttpClient;

import java.util.concurrent.TimeUnit;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.converter.GsonConverter;

/**
 * Use to create RestAdapter with options in order to request API.
 * <p/>
 * Needs to call "init" in Application{@link com.hieugmail.hieu.BaseApp} before using it.
 */
public final class ApiClient {
    private static final String TAG = ApiClient.class.getSimpleName();
    private static final String HEADER_UA = "User-Agent";
    private static final String HEADER_AUTH = "Authorization";
    private static final String AUTH_PREFIX = "Bearer ";
    private static final int TIMEOUT_CONNECTION = 10000;

    private static ApiClient sInstance;
    private Context context;
    private ApiService service;
    // private GoogleService googleService;

    /**
     * Custom header request.
     */
    private final RequestInterceptor requestInterceptor = new RequestInterceptor() {
        @Override
        public void intercept(RequestFacade request) {
            request.addHeader(HEADER_UA, createUserAgent());
            // add token
//            String accessToken = UserLogin.getCurrentToken();
//            Log.i(TAG, "access_token:" + accessToken);
//            if (!TextUtils.isEmpty(accessToken)) {
//                request.addHeader(HEADER_AUTH, AUTH_PREFIX + accessToken);
//            }
        }
    };

    public static synchronized ApiClient getInstance() {
        if (sInstance == null) {
            sInstance = new ApiClient();
        }
        return sInstance;
    }

    public static ApiService getService() {
        return getInstance().service;
    }

//    public static GoogleService getGoogleService() {
//        if (getInstance().googleService == null) {
//            RestAdapter restAdapter = new RestAdapter.Builder()
//                    .setLogLevel(RestAdapter.LogLevel.FULL)
//                    .setEndpoint(getInstance().context.getString(R.string.url_currency_api))
//                    .build();
//            getInstance().googleService = restAdapter.create(GoogleService.class);
//        }
//        return getInstance().googleService;
//    }

    private ApiClient() {
        // no-op
    }

    public void init(ApiConfig apiConfig) {
        context = apiConfig.getContext();

        // init Host
        // TODO need to change to: apiConfig.getBaseUrl() later
        String host = Environment.DEVELOP.getUrl();

        //BooleanAdapter booleanAdapter = new BooleanAdapter();
        //   IntegerAdapter integerAdapter = new IntegerAdapter();
        // init Gson
        Gson gson = new GsonBuilder()
                //  .registerTypeAdapter(Boolean.class, booleanAdapter)
                // .registerTypeAdapter(boolean.class, booleanAdapter)
                //  .registerTypeAdapter(Integer.class, integerAdapter)
                //  .registerTypeAdapter(int.class, integerAdapter)
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();

        // init OkHttpClient
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setConnectTimeout(TIMEOUT_CONNECTION, TimeUnit.MILLISECONDS);

        // RestAdapter
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(host)
                .setClient(new OkClient(okHttpClient))
                .setRequestInterceptor(requestInterceptor)
                .setConverter(new GsonConverter(gson))
                        // .setLogLevel(BuildConfig.DEBUG ? RestAdapter.LogLevel.FULL : RestAdapter.LogLevel.NONE)
                .build();

        // init Service
        service = restAdapter.create(ApiService.class);
    }

    private String createUserAgent() {
        PackageManager pm = context.getPackageManager();
        String versionName = "";
        try {
            PackageInfo packageInfo = pm.getPackageInfo(context.getPackageName(), 0);
            versionName = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, "occurs error when creating user agent!!!");
        }
        return System.getProperty("http.agent") + " " + context.getPackageName() + "/" + versionName;
    }
}
