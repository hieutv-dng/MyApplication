package com.hieugmail.hieu;

import android.support.annotation.StringRes;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;

import lombok.NonNull;

/**
 * Class activity parent use to activity child extend it.
 */
@EActivity
public abstract class BaseActivity extends AppCompatActivity {

    @AfterViews
    protected abstract void init();

    protected void showAlertDialog(@NonNull String msg) {
        new AlertDialog.Builder(this)
                .setMessage(msg)
                .setPositiveButton(android.R.string.ok, null)
                .show();
    }

    protected void showAlertDialog(@StringRes int resId) {
        showAlertDialog(getString(resId));
    }
}
