package com.hieugmail.hieu;

import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;

/**
 * Class Fragment parent use to Fragment child extend it.
 */
@EFragment
public abstract class BaseFragment extends Fragment {

    @AfterViews
    protected abstract void init();

    protected void showAlertDialog(@NonNull String msg) {
        new AlertDialog.Builder(getActivity())
                .setMessage(msg)
                .setPositiveButton(android.R.string.ok, null)
                .show();
    }

    protected void showAlertDialog(@StringRes int resId) {
        showAlertDialog(getString(resId));
    }
}
