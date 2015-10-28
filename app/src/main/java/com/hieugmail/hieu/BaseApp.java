package com.hieugmail.hieu;

import com.hieugmail.hieu.service.core.ApiClient;
import com.hieugmail.hieu.service.core.ApiConfig;
import com.orm.SugarApp;

/**
 * Class App main.
 */
public class BaseApp extends SugarApp {
    private static final String TAG = BaseApp.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();
        // setup service
        createService();
    }

    private void createService() {
        ApiConfig apiConfig = ApiConfig.builder()
                .context(getApplicationContext())
                .baseUrl(getString(R.string.url_base))
                .build();
        ApiClient.getInstance().init(apiConfig);
    }
}
