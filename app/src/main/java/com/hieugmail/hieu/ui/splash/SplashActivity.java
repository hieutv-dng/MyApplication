package com.hieugmail.hieu.ui.splash;

import android.os.Handler;

import com.hieugmail.hieu.BaseActivity;
import com.hieugmail.hieu.R;
import com.hieugmail.hieu.model.UserLogin;
import com.hieugmail.hieu.ui.login.LoginActivity_;
import com.hieugmail.hieu.ui.menu.MenuActivity_;

import org.androidannotations.annotations.EActivity;

/**
 * Screen display first when user open app.
 */
@EActivity(R.layout.activity_splash)
public class SplashActivity extends BaseActivity {
    private static final int TIME_DISPLAY = 2000;

    @Override
    protected void init() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (UserLogin.first(UserLogin.class) != null) {
                    MenuActivity_.intent(SplashActivity.this).start();
                    finish();
                } else {
                    LoginActivity_.intent(SplashActivity.this).start();
                    finish();
                }
            }
        }, TIME_DISPLAY);
    }
}
