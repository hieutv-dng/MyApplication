package com.hieugmail.hieu.ui.login;

import android.text.TextUtils;
import android.widget.EditText;

import com.hieugmail.hieu.BaseActivity;
import com.hieugmail.hieu.R;
import com.hieugmail.hieu.model.UserLogin;
import com.hieugmail.hieu.ui.menu.MenuActivity_;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

/**
 * Screen using user login app
 */
@EActivity(R.layout.activity_login)
public class LoginActivity extends BaseActivity {
    private static final String TAG = LoginActivity.class.getName();
    private UserLogin mUserLogin;

    @ViewById
    EditText mEdtNameUser;
    @ViewById
    EditText mEdtPassword;

    @Override
    protected void init() {
    }

    @Click(R.id.mBtnLogin)
    void onButtonLoginClick() {
        if (TextUtils.isEmpty(mEdtNameUser.getText().toString())) {
            mEdtNameUser.setError(getString(R.string.login_error_input_name));
            return;
        }
        if (TextUtils.isEmpty(mEdtPassword.getText().toString())) {
            mEdtPassword.setError(getString(R.string.login_error_input_pass));
            return;
        }
        String name = mEdtNameUser.getText().toString();
        String pass = mEdtPassword.getText().toString();
        mUserLogin = new UserLogin(name, pass);
        mUserLogin.save();
        MenuActivity_.intent(this).start();
        finish();
    }
}
