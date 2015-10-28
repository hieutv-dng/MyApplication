package com.hieugmail.hieu.ui.category;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hieugmail.hieu.BaseAdapter;
import com.hieugmail.hieu.R;

import java.util.ArrayList;

/**
 *
 */
public class KetCuoiAdapter extends BaseAdapter {
    private ArrayList<String> mArrKcs;
    private ArrayList<String> mArrAddressKcs;


    protected KetCuoiAdapter(@NonNull Context context, ArrayList<String> list1, ArrayList<String> list2) {
        super(context);
        mArrKcs = list1;
        mArrAddressKcs = list2;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.item_list_one, parent, false);
        return new KetCuoiHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        onBindHeader((KetCuoiHolder) holder, position);
    }

    private void onBindHeader(KetCuoiHolder holder, int position) {
        holder.mTvKc.setText(mArrKcs.get(position));
        holder.mTvAddressKc.setText(mArrAddressKcs.get(position));
    }

    @Override
    public int getItemCount() {
        return mArrKcs.size();
    }

    /**
     * Define Top's ViewHolder
     */
    class KetCuoiHolder extends RecyclerView.ViewHolder {
        private final TextView mTvKc;
        private final TextView mTvAddressKc;

        public KetCuoiHolder(View itemView) {
            super(itemView);
            mTvKc = (TextView) itemView.findViewById(R.id.mTvKc);
            mTvAddressKc = (TextView) itemView.findViewById(R.id.mTvAddressKc);
        }
    }
}
