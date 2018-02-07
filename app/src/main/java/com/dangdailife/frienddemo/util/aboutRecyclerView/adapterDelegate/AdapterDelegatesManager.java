package com.dangdailife.frienddemo.util.aboutRecyclerView.adapterDelegate;

import android.support.annotation.NonNull;
import android.support.v4.util.SparseArrayCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ViewGroup;

import java.util.List;

/**
 * @author Joy Wong
 * @description
 * @datetime 2016/08/12 9:59
 * @email wjb18814888154@gmail.com
 */
public class AdapterDelegatesManager<T> {
    private SparseArrayCompat<AdapterDelegate<T>> delegates = new SparseArrayCompat<>();


    public AdapterDelegatesManager<T> addDelegate( AdapterDelegate<T> delegate) {
        return addDelegate(delegate, false);
    }


    public AdapterDelegatesManager<T> addDelegate( AdapterDelegate<T> delegate,
                                                   boolean allowReplacingDelegate) {

        if (delegate == null) {
            throw new NullPointerException("AdapterDelegate is null!");
        }

        int viewType = delegate.getItemViewType();
        if (!allowReplacingDelegate && delegates.get(viewType) != null) {
            throw new IllegalArgumentException(
                    "An AdapterDelegate is already registered for the viewType = " + viewType
                            + ". Already registered AdapterDelegate is " + delegates.get(viewType));
        }

        delegates.put(viewType, delegate);

        return this;
    }


    public AdapterDelegatesManager<T> removeDelegate( AdapterDelegate<T> delegate) {

        if (delegate == null) {
            throw new NullPointerException("AdapterDelegate is null");
        }

        AdapterDelegate<T> queried = delegates.get(delegate.getItemViewType());
        if (queried != null && queried == delegate) {
            delegates.remove(delegate.getItemViewType());
        }
        return this;
    }


    public AdapterDelegatesManager<T> removeDelegate(int viewType) {
        delegates.remove(viewType);
        return this;
    }


    public int getItemViewType( T items, int position) {

        if (items == null) {
            throw new NullPointerException("Items datasource is null!");
        }

        int delegatesCount = delegates.size();
        for (int i = 0; i < delegatesCount; i++) {
            AdapterDelegate<T> delegate = delegates.valueAt(i);
            if (delegate.isForViewType(items, position)) {
                return delegate.getItemViewType();
            }
        }

        throw new IllegalArgumentException(
                "No AdapterDelegate added that matches tabIndex=" + position + " in data source");
    }


    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d("ss", "onCreateViewHolder: viewType:" + viewType);
        AdapterDelegate<T> delegate = findDelegate(viewType);

        RecyclerView.ViewHolder vh = delegate.onCreateViewHolder(parent);
        if (vh == null) {
            throw new NullPointerException(
                    "ViewHolder returned from AdapterDelegate " + delegate + " for ViewType =" + viewType
                            + " is null!");
        }
        return vh;
    }


    public void onBindViewHolder( T items, int position,  RecyclerView.ViewHolder viewHolder) {

        AdapterDelegate<T> delegate = findDelegate(viewHolder.getItemViewType());

        delegate.onBindViewHolder(items, position, viewHolder);
    }

    public void onBindViewHolder(T items, int position, RecyclerView.ViewHolder viewHolder, List payloads) {

        AdapterDelegate<T> delegate = findDelegate(viewHolder.getItemViewType());

        delegate.onBindViewHolder(items, position, viewHolder,payloads);
    }

    @NonNull
    private AdapterDelegate<T> findDelegate(int viewType) {
        AdapterDelegate<T> delegate = delegates.get(viewType);
        if (delegate == null) {
            throw new NullPointerException(
                    "No AdapterDelegate added for ViewType " + viewType);
        }
        return delegate;
    }


}
