package com.hieugmail.hieu.ui.menu;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.widget.TextView;

import com.hieugmail.hieu.BaseFragment;
import com.hieugmail.hieu.R;
import com.hieugmail.hieu.model.UserLogin;
import com.hieugmail.hieu.ui.category.CategoryActivity_;
import com.hieugmail.hieu.ui.login.LoginActivity_;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

/**
 * Screen uses to display list menu
 */
@EFragment(R.layout.fragment_menu)
public class MenuFragment extends BaseFragment {
    private Context mContext;
    private UserLogin mUserLogin;

    @ViewById
    TextView mTvUserName;

    @Override
    protected void init() {
        mContext = getActivity();
        mUserLogin = UserLogin.first(UserLogin.class);
        if (mUserLogin != null) {
            mTvUserName.setText("Chào bạn " + mUserLogin.getName());
        }
    }

    @Click(R.id.mTvCategory)
    void onTvCategoryClick() {
        CategoryActivity_.intent(mContext).start();
    }

    @Click(R.id.mTvLogout)
    void onTvLogoutClick() {
        new AlertDialog.Builder(mContext)
                .setMessage(mContext.getString(R.string.menu_tv_msg_logout))
                .setNegativeButton(android.R.string.cancel, null)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        UserLogin.deleteAll(UserLogin.class);
                        LoginActivity_.intent(mContext).start();
                        getActivity().finish();
                    }
                }).show();
    }
}
