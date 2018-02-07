package com.dangdailife.frienddemo.util.base;

import android.support.v7.widget.RecyclerView;

import java.util.List;

/**
 * @author Joy Wong
 * @description
 * @datetime 2016/08/12 11:12
 * @email wjb18814888154@gmail.com
 */
public abstract class BaseRecyclerAdapter<T> extends RecyclerView.Adapter {
    protected List<T> items;//实时数据

    protected List<T> oldItems;//旧数据

    private boolean mNotifyOnChange = true;

    public BaseRecyclerAdapter(List<T> items, List<T> oldItems) {
        this.items = items;
        this.oldItems = oldItems;
    }

    public BaseRecyclerAdapter(List<T> items) {
        this.items = items;
    }

    public boolean getNotifyOnChange() {
        return mNotifyOnChange;
    }

    public void setNotifyOnChange(boolean notifyOnChange) {
        mNotifyOnChange = notifyOnChange;
    }

    @Override
    public int getItemCount() {
        return items == null ? 0 : items.size();
    }

    public List<T> getData() {
        return items;
    }

    public List<T> getOldData() {
        return oldItems;
    }

    public void setOldItems(List<T> oldItems) {
        this.oldItems = oldItems;
    }

    /**
     * 在尾部添加一串数据
     *
     * @param items
     */
    public void addAll(List<? extends T> items, List<? extends T> oldItems) {
        if (items != null && !items.isEmpty()) {
            int positionStart = getItemCount();
            this.oldItems.addAll(oldItems);
            this.items.addAll(items);
            if (mNotifyOnChange) {
                notifyItemRangeInserted(positionStart, items.size());
            }
        }

    }

    /**
     * 在尾部加入一条数据
     *
     * @param object
     */
    public void add(T object) {
        if (items != null) {
            items.add(object);
            if (mNotifyOnChange) {
                notifyItemInserted(items.size() - 1);
            }
        }
    }

    /**
     * 在指定位置加入一条数据
     *
     * @param position
     * @param object
     */
    public void add(int position, T object) {
        if (items != null) {
            this.items.add(position, object);
            if (mNotifyOnChange) {
                notifyItemInserted(position);
            }
        }
    }

    public void clear() {
        if (items != null) {
            items.clear();
            if (mNotifyOnChange) {
                notifyDataSetChanged();
            }
        }
    }

    public boolean isEmpty() {
        return getItemCount() == 0;
    }
}
