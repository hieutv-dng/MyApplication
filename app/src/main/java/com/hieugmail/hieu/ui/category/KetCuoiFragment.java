package com.hieugmail.hieu.ui.category;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.hieugmail.hieu.BaseFragment;
import com.hieugmail.hieu.R;

import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;

/**
 * This screen tap0
 */
@EFragment(R.layout.fragment_one)
public class KetCuoiFragment extends BaseFragment {
    @ViewById
    RecyclerView mRecyclerView;

    private KetCuoiAdapter mAdapter;
    private Context mContext;
    private ArrayList<String> mArrKcs = new ArrayList<>();
    private ArrayList<String> mArrAddressKcs = new ArrayList<>();

    @Override
    protected void init() {
        mContext = getActivity();
        String[] itemKc = mContext.getResources().getStringArray(R.array.namekc);
        String[] itemAddKc = mContext.getResources().getStringArray(R.array.addresskc);
        for (int i = 0; i < itemAddKc.length; i++) {
            mArrKcs.add(itemKc[i]);
            mArrAddressKcs.add(itemAddKc[i]);
        }
        mAdapter = new KetCuoiAdapter(mContext, mArrKcs, mArrAddressKcs);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);

    }
}
