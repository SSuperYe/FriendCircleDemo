package com.dangdailife.frienddemo.friendcircle;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.dangdailife.frienddemo.bean.FriendBean;
import com.dangdailife.frienddemo.util.aboutRecyclerView.adapterDelegate.AdapterDelegatesManager;
import com.dangdailife.frienddemo.util.base.BaseRecyclerAdapter;

import java.util.List;

/**
 * @author Mr.Ye
 * @description
 * @datetime 2018/01/31 10:57
 * @email superrhye@163.com
 */

public class FriendCircleAdapter extends BaseRecyclerAdapter<FriendBean> {

    private static final int FRIEND_CONTENT = 1;

    private AdapterDelegatesManager<List<FriendBean>> delegatesManager;

    public FriendCircleAdapter(List<FriendBean> items, Activity activity) {
        super(items);
        delegatesManager = new AdapterDelegatesManager<>();
        ContentDelegate contentDelegate = new ContentDelegate(FRIEND_CONTENT, activity, items);
        delegatesManager.addDelegate(contentDelegate);
    }

    @Override
    public int getItemViewType(int position) {
        return delegatesManager.getItemViewType(items, position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return delegatesManager.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        delegatesManager.onBindViewHolder(items, position, holder);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position, List payloads) {
        delegatesManager.onBindViewHolder(items, position, holder, payloads);
    }


}
