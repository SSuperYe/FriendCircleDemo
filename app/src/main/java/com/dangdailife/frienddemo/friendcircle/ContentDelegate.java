package com.dangdailife.frienddemo.friendcircle;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dangdailife.frienddemo.R;
import com.dangdailife.frienddemo.bean.FriendBean;
import com.dangdailife.frienddemo.util.LogUtils;
import com.dangdailife.frienddemo.util.SpannableStringUtil;
import com.dangdailife.frienddemo.util.ToastHelper;
import com.dangdailife.frienddemo.util.aboutRecyclerView.adapterDelegate.AbsAdapterDelegate;
import com.dangdailife.frienddemo.util.eventbus.MainEvent;
import com.lzy.ninegrid.NineGridView;
import com.lzy.ninegrid.preview.NineGridViewClickAdapter;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.jzvd.JZVideoPlayerStandard;

/**
 * @author Mr.Ye
 * @description
 * @datetime 2018/01/31 13:58
 * @email superrhye@163.com
 */

public class ContentDelegate extends AbsAdapterDelegate<List<FriendBean>> {

    Activity activity;
    List<FriendBean> data;

    public ContentDelegate(int viewType, Activity activity, List<FriendBean> data) {
        super(viewType);
        this.activity = activity;
        this.data = data;
    }

    @Override
    public boolean isForViewType(List<FriendBean> items, int position) {
        return items.get(position) instanceof FriendBean;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new ViewHolder(inflater.inflate(R.layout.layout_item_friend_content, parent, false));
    }

    @Override
    public void onBindViewHolder(List<FriendBean> items, int position, RecyclerView.ViewHolder holder) {
        if (holder instanceof ViewHolder) {
            final FriendBean item = items.get(position);
            final ViewHolder viewHolder = (ViewHolder) holder;

            Glide.with(activity).load(item.headUrl).into(viewHolder.ivHead);
            viewHolder.tvName.setText(item.name);
            viewHolder.tvAddress.setText(item.address);
            viewHolder.tvTime.setText(item.time);

            //动态内容
            viewHolder.tvFold.setVisibility(item.isShowFold ? View.VISIBLE : View.GONE);
            if (item.isShowFold) {
                if (item.isClickFold) {
                    viewHolder.tvFold.setText("收起");
                    viewHolder.tvContent.setText(item.contentBean.noFoldContent);
                } else {
                    viewHolder.tvFold.setText("全文");
                    viewHolder.tvContent.setText(item.contentBean.foldContent);
                }
            } else {
                viewHolder.tvContent.setText(item.contentBean.noFoldContent);
            }


            //图片
            NineGridViewClickAdapter adapter = new NineGridViewClickAdapter(activity, item.imageInfoList);
            viewHolder.nineGrid.setVisibility(adapter.getImageInfo() != null && !adapter.getImageInfo().isEmpty() ? View.VISIBLE : View.GONE);
            viewHolder.nineGrid.setAdapter(adapter);


            //点赞
            String finalStr = item.strLike;
            String[] strings = finalStr.split("，");
            SpannableStringUtil spannableStringUtil = SpannableStringUtil.getInstance()
                    .setStrRes(finalStr);
            int start = 0;
            int end = 0;
            for (int i = 0; i < strings.length; i++) {
                if (i == 0) {
                    start = 0;
                    end += (strings[i].length());

                } else {
                    start += (1 + strings[i - 1].length());
                    end += (1 + strings[i].length());
                }
                final int finalI = i;
                spannableStringUtil = spannableStringUtil.setForegroundColor(
                        activity.getResources().getColor(R.color.blueLight)
                        , start, end)
                        .setClickable(viewHolder.tvLike, false, start, end, () -> ToastHelper.showShort("click___第" + (finalI + 1) + "个"));
            }
            viewHolder.tvLike.setText(spannableStringUtil.getSpannableBuilder());

            //评论
            loadCommentLayout(viewHolder, item, position);
        }
    }

    @Override
    public void onBindViewHolder(List<FriendBean> items, int position, RecyclerView.ViewHolder holder, List payloads) {
        if (payloads.isEmpty()) {
            super.onBindViewHolder(items, position, holder, payloads);
        } else {
            for (Object o : payloads) {
                ViewHolder viewHolder = (ViewHolder) holder;
                FriendBean item = items.get(position);
                if (o instanceof FriendPayLoad) {
//                    NineGridViewClickAdapter adapter = new NineGridViewClickAdapter(activity, item.imageInfoList);
//                    viewHolder.nineGrid.setVisibility(adapter.getImageInfo() != null && !adapter.getImageInfo().isEmpty() ? View.VISIBLE : View.GONE);
//                    viewHolder.nineGrid.setAdapter(adapter);
                } else if (o instanceof FriendCommentRemovePayload) {
                    //删除评论，重新加载评论区域
                    loadCommentLayout(viewHolder, item, position);
                } else if (o instanceof FriendCommentAddPayload) {
                    loadCommentLayout(viewHolder, item, position, true);
                }
            }
        }
    }

    /**
     * item内点击弹出评论
     *
     * @param txt           回复内容前的拼接(hint)
     * @param tv
     * @param position
     * @param friendComment
     */
    private void showEditPP(String txt, TextView tv, int position, FriendBean.FriendComment friendComment) {
        int[] ints = new int[2];
        tv.getLocationOnScreen(ints);
        tv.postDelayed(() -> {
            MainEvent event = new MainEvent();
            CommentPopup commentPopup = new CommentPopup(activity);
            commentPopup.show(
                    comment -> {
                        if (!comment.isEmpty()) {
                            //局部刷新，先改data的值，在刷
                            FriendBean.FriendComment fc = new FriendBean.FriendComment();
                            fc.name = TextUtils.isEmpty(friendComment.replyName) ? friendComment.name : friendComment.replyName;
                            fc.replyName = ((FriendCircleActivity) activity).getName();
                            fc.content = comment;
                            data.get(position).friendList.add(fc);

                            FriendCommentAddPayload payload = new FriendCommentAddPayload();
                            ((FriendCircleActivity) activity).setRefreshPosition(position, payload);
                        }
                    }, y1 -> {
                        event.isScroll = true;
                        event.x = ints[0];
                        event.y = ints[1] + tv.getHeight() + tv.getLineSpacingExtra();
                        event.layoutCommentsY = y1;
                        EventBus.getDefault().post(event);

                        commentPopup.setSendContentHint(txt);
                    });
        }, 100);
    }

    /**
     * 图标添加评论
     *
     * @param position item's position
     */
    private void showCommentPP(int position, ImageView divider) {
        MainEvent event = new MainEvent();
        int[] ints = new int[2];
        divider.getLocationOnScreen(ints);
        CommentPopup commentPopup = new CommentPopup(activity);
        commentPopup.show(comment -> {
            if (!comment.isEmpty()) {
                //局部刷新，先改data的值，在刷
                FriendBean.FriendComment fc = new FriendBean.FriendComment();
                fc.name = ((FriendCircleActivity) activity).getName();
                fc.content = comment;
                data.get(position).friendList.add(fc);

                FriendCommentAddPayload payload = new FriendCommentAddPayload();
                ((FriendCircleActivity) activity).setRefreshPosition(position, payload);
            }
        }, y -> {
            event.isScroll = true;
            event.x = ints[0];
            event.y = ints[1];
            event.layoutCommentsY = y;
            EventBus.getDefault().post(event);
        });
        commentPopup.setSendContentHint("回复" + data.get(position).name);
    }

    /**
     * 删除评论
     *
     * @param index    评论layout里面的position
     * @param position 哪一个item
     */
    private void showDeleteDialog(int index, int position) {
        //局部刷新，先改data的值，在刷
        data.get(position).friendList.remove(index);
        FriendCommentRemovePayload payload = new FriendCommentRemovePayload();
        payload.index = index;
        ((FriendCircleActivity) activity).setRefreshPosition(position, payload);
    }

    private void loadCommentLayout(ViewHolder viewHolder, FriendBean item, int position) {
        loadCommentLayout(viewHolder, item, position, false);
    }

    /**
     * 加载评论区域
     *
     * @param viewHolder
     * @param item
     * @param position
     * @param isAddOne   是否是增加一条评论
     */
    private void loadCommentLayout(ViewHolder viewHolder, FriendBean item, int position, boolean isAddOne) {
        //添加评论不移除评论区域
        if (!isAddOne) {
            viewHolder.layoutComments.removeAllViews();
        }
        int length = item.friendList.size();
        for (int i = 0; i < length; i++) {
            final FriendBean.FriendComment comment = item.friendList.get(i);
            TextView textView = new TextView(activity);
            textView.setTextColor(activity.getResources().getColor(R.color.textColorNormal));
            textView.setBackground(activity.getResources().getDrawable(R.drawable.selector_bar_bg_color));
            textView.setGravity(Gravity.START);
            final StringBuilder sb = new StringBuilder();
            SpannableStringUtil util = SpannableStringUtil.getInstance();
            int startIndex = 0;
            int endIndex = 0;
            int nextStartIndex = 0;
            int nextEndIndex = 0;
            startIndex = 0;
            if (comment.replyName != null && !comment.replyName.isEmpty()) {
                endIndex = comment.replyName.length();
                sb.append(comment.replyName)
                        .append("回复")
                        .append(comment.name)
                        .append(":")
                        .append(comment.content);
                nextStartIndex = comment.replyName.length() + "回复".length();
                nextEndIndex = nextStartIndex + comment.name.length();
            } else {
                endIndex = comment.name.length();
                sb.append(comment.name)
                        .append(":")
                        .append(comment.content);
            }
            util.setStrRes(sb.toString());
            int commentIndex = i;
            if (nextStartIndex != 0 && nextEndIndex != 0) {
                //单有人回复时的
                util.setForegroundColor(activity.getResources().getColor(R.color.blueLight), startIndex, endIndex);
                util.setForegroundColor(activity.getResources().getColor(R.color.blueLight), nextStartIndex, nextEndIndex);
                util.setClickable(textView, false, startIndex, endIndex
                        , () -> ToastHelper.showShort("click___" + comment.replyName));
                util.setClickable(textView, false, nextStartIndex, nextEndIndex
                        , () -> ToastHelper.showShort("click___" + comment.name));
                util.setClickable(textView, false, nextEndIndex + 1, sb.length()
                        , () -> {
                            if (TextUtils.equals(comment.replyName, ((FriendCircleActivity) activity).getName())) {
                                //删除自己的评论
                                showDeleteDialog(commentIndex, position);
                            } else {
                                showEditPP(("回复" + comment.replyName + ":"), textView, position, comment);
                            }
                        });
            } else {
                //当单挑评论时（无回复）
                util.setForegroundColor(activity.getResources().getColor(R.color.blueLight), startIndex, endIndex);
                util.setClickable(textView, false, startIndex, endIndex
                        , () -> ToastHelper.showShort("click___" + comment.name))
                        .setClickable(textView, false, endIndex + 1, sb.length()
                                , () -> {
                                    if (TextUtils.equals(comment.name, ((FriendCircleActivity) activity).getName())) {
                                        //删除自己的评论
                                        showDeleteDialog(commentIndex, position);
                                    } else {
                                        showEditPP(("回复" + comment.name + ":"), textView, position, comment);
                                    }
                                });
            }
            textView.setText(util.getSpannableBuilder());
            //作为加评论的前置条件
            if (isAddOne) {
                if (i == length - 1) {
                    //评论是加在最后一条的，判断是否是最后一条，其余情况不加评论
                    viewHolder.layoutComments.addView(textView);
                    break;
                }
            } else {
                viewHolder.layoutComments.addView(textView);
            }
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.iv_head)
        ImageView ivHead;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_content)
        TextView tvContent;
        @BindView(R.id.tv_fold)
        TextView tvFold;
        @BindView(R.id.nineGrid)
        NineGridView nineGrid;
        @BindView(R.id.vv)
        JZVideoPlayerStandard vv;
        @BindView(R.id.layout_imgorvideo)
        RelativeLayout layoutImgorvideo;
        @BindView(R.id.tv_address)
        TextView tvAddress;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.img_comments)
        ImageView imgComments;
        @BindView(R.id.tv_like)
        TextView tvLike;
        @BindView(R.id.img_rowline)
        ImageView imgRowline;
        @BindView(R.id.divider)
        ImageView divider;
        @BindView(R.id.layout_comments)
        LinearLayout layoutComments;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            tvFold.setOnClickListener(this);
            imgComments.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            FriendBean item = data.get(getLayoutPosition() - 1);
            switch (v.getId()) {
                case R.id.tv_fold:
                    if (item.isShowFold) {
                        item.isClickFold = !(item.isClickFold);//先更改状态
                        if (item.isClickFold) {
                            tvFold.setText("收起");
                            tvContent.setText(item.contentBean.noFoldContent);
                        } else {
                            tvFold.setText("全文");
                            tvContent.setText(item.contentBean.foldContent);
                        }
                    }
                    break;
                case R.id.img_comments:
                    showCommentPP(getLayoutPosition() - 1, divider);
                    break;
            }
        }
    }
}
