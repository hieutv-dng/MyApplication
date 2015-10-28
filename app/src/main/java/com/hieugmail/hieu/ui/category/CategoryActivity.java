package com.hieugmail.hieu.ui.category;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import com.hieugmail.hieu.BaseActivity;
import com.hieugmail.hieu.R;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

/**
 * Class using display type category.
 */
@EActivity(R.layout.activity_category)
public class CategoryActivity extends BaseActivity {
    private MyPageAdapter mAdapter;

    @ViewById
    ViewPager mViewPager;
    @ViewById
    TextView mTab0;
    @ViewById
    TextView mTab1;
    @ViewById
    TextView mTab2;

    private TextView mTabCurrent;

    @Override
    protected void init() {
        mAdapter = new MyPageAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mAdapter);
        if (mTabCurrent != null) {
            mTabCurrent.setSelected(false);
        }
        mTabCurrent = mTab0;
        mTabCurrent.setSelected(true);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        mTabCurrent = mTab0;
                        mTabCurrent.setSelected(true);
                        mTab1.setSelected(false);
                        mTab2.setSelected(false);
                        break;
                    case 1:
                        mTabCurrent = mTab1;
                        mTabCurrent.setSelected(true);
                        mTab0.setSelected(false);
                        mTab2.setSelected(false);
                        break;
                    case 2:
                        mTabCurrent = mTab2;
                        mTabCurrent.setSelected(true);
                        mTab0.setSelected(false);
                        mTab1.setSelected(false);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Click(R.id.mImgBack)
    void onImgBackClick() {
        finish();
    }

    @Click(R.id.mTab0)
    void onTab0Click() {
        mViewPager.setCurrentItem(0);
    }

    @Click(R.id.mTab1)
    void onTab1Click() {
        mViewPager.setCurrentItem(1);
    }

    @Click(R.id.mTab2)
    void onTab2Click() {
        mViewPager.setCurrentItem(2);
    }

    private class MyPageAdapter extends FragmentStatePagerAdapter {

        public MyPageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return KetCuoiFragment_.builder().build();
                case 1:
                    return CapFragment_.builder().build();
                case 2:
                    return SplitterFragment_.builder().build();
                default:
                    return KetCuoiFragment_.builder().build();
            }
        }

        @Override
        public int getCount() {
            return 3;
        }
    }
}
