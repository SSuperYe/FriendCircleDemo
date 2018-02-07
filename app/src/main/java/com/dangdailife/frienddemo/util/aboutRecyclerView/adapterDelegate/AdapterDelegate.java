package com.dangdailife.frienddemo.util.aboutRecyclerView.adapterDelegate;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

/**
 * @author Joy Wong
 * @description
 * @datetime 2016/08/12 9:56
 * @email wjb18814888154@gmail.com
 */
public interface AdapterDelegate<T> {
    int getItemViewType();


    boolean isForViewType(T items, int position);


    RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent);


    void onBindViewHolder(T items, int position, RecyclerView.ViewHolder holder);

    void onBindViewHolder(T items, int position, RecyclerView.ViewHolder holder, List payloads);
}
