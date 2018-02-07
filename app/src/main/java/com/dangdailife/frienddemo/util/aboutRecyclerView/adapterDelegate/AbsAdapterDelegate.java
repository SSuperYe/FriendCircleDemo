package com.dangdailife.frienddemo.util.aboutRecyclerView.adapterDelegate;

import android.support.v7.widget.RecyclerView;

import java.util.List;

/**
 * @author Joy Wong
 * @description
 * @datetime 2016/08/12 9:56
 * @email wjb18814888154@gmail.com
 */
public abstract class AbsAdapterDelegate<T> implements AdapterDelegate<T>{
    protected int viewType;

    public AbsAdapterDelegate(int viewType) {
        this.viewType = viewType;
    }

    @Override
    public int getItemViewType() {
        return viewType;
    }

    @Override
    public void onBindViewHolder(T items, int position, RecyclerView.ViewHolder holder, List payloads) {
        if (payloads==null||payloads.isEmpty()){
            onBindViewHolder(items,position,holder);
        }
    }
}
