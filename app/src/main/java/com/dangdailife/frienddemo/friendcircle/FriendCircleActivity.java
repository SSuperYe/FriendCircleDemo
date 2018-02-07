package com.dangdailife.frienddemo.friendcircle;

import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dangdailife.frienddemo.R;
import com.dangdailife.frienddemo.bean.FriendBean;
import com.dangdailife.frienddemo.bean.FriendModel;
import com.dangdailife.frienddemo.util.DisplayUtil;
import com.dangdailife.frienddemo.util.LogUtils;
import com.dangdailife.frienddemo.util.ToastHelper;
import com.dangdailife.frienddemo.util.aboutRecyclerView.HeaderAndFooterWrapper;
import com.dangdailife.frienddemo.util.base.BaseToolBarActivity;
import com.dangdailife.frienddemo.util.eventbus.MainEvent;
import com.lzy.ninegrid.ImageInfo;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class FriendCircleActivity extends BaseToolBarActivity {

    @BindView(R.id.recycler)
    RecyclerView recycler;
    LinearLayoutManager manager;

    FriendCircleAdapter adapter;

    HeaderAndFooterWrapper wrapper;

    View headerView;
    ImageView imgBg;
    TextView tvName;
    TextView tvComments;
    TextView tvMsgCount;
    ImageView imgHead;
    ImageView imgMsgHead;
    RelativeLayout layoutMsg;

    List<FriendBean> items = new ArrayList<>();

    FriendModel friendModel;

    private int refreshPosition;//需要局部刷新的位置

    int realKeyboardHeight;

    @Override
    protected void setContentView() {
        super.setContentView();
        setContentView(R.layout.activity_friend_circle);
    }

    @Override
    public void initToolbarTitle() {
        titleName.setText("friend");
    }

    @Override
    protected void initView() {
        super.initView();
        initRecycler();
        initHeaderView();
    }

    @Override
    protected void fetchData() {
        super.fetchData();
        renderData();
    }

    private void initHeaderView() {
        headerView = View.inflate(this, R.layout.layout_friend_circle_header, null);
        imgBg = (ImageView) headerView.findViewById(R.id.img_bg);
        imgHead = (ImageView) headerView.findViewById(R.id.img_head);
        imgMsgHead = (ImageView) headerView.findViewById(R.id.img_msg_head_url);
        tvName = (TextView) headerView.findViewById(R.id.name);
        tvMsgCount = (TextView) headerView.findViewById(R.id.tv_msg_count);
        layoutMsg = (RelativeLayout) headerView.findViewById(R.id.layout_msg);

        layoutMsg.setOnClickListener(v -> ToastHelper.showShort("查看消息"));
    }

    private void initRecycler() {
        manager = new LinearLayoutManager(this);
        recycler.setLayoutManager(manager);
//        recycler.addItemDecoration(new LinearDivider(this, 1));
        recycler.setHasFixedSize(true);
    }

    private void renderHeader() {
        friendModel = new FriendModel();
        friendModel.name = "SuperYe";
        friendModel.headUrl = "https://b2c.hycaichang.com/upload/20161026/8c9c7362d94023f573f4b34b0f9603b8.jpg";
        friendModel.bgUrl = "https://b2c.hycaichang.com/upload/20161026/8c9c7362d94023f573f4b34b0f9603b8.jpg";

        List<FriendModel.MsgBean> msgBeans = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            FriendModel.MsgBean msgBean = new FriendModel.MsgBean();
            msgBean.url = "https://b2c.hycaichang.com/upload/20161026/8c9c7362d94023f573f4b34b0f9603b8.jpg";
            msgBeans.add(msgBean);
        }
        friendModel.msgList = msgBeans;

        tvName.setText(friendModel.name);
        if (friendModel.msgList == null || friendModel.msgList.isEmpty()) {
            layoutMsg.setVisibility(View.GONE);
        } else {
            layoutMsg.setVisibility(View.VISIBLE);
            tvMsgCount.setText("你有" + friendModel.msgList.size() + "条消息");
            //取最后一条消息的图像
            Glide.with(this).load(friendModel.msgList.get(friendModel.msgList.size() - 1).url).into(imgMsgHead);
        }
        Glide.with(this).load(friendModel.bgUrl).into(imgBg);
        Glide.with(this).load(friendModel.headUrl).into(imgHead);
    }

    private void renderData() {

        renderHeader();

        for (int i = 0; i < 10; i++) {
            FriendBean friendBean = new FriendBean();

            friendBean.name = "小明" + i;
            friendBean.address = "杭州";
            friendBean.time = "2小时前";
            friendBean.headUrl = "https://b2c.hycaichang.com/upload/20161026/8c9c7362d94023f573f4b34b0f9603b8.jpg";

            //动态内容
            final String realContent = "testtesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttest" +
                    "testtesttesttesttesttesttesttesttesttesttesttesttes" +
                    "ttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttest";
            //是否点击过折叠
            friendBean.isClickFold = false;
            FriendBean.ContentBean contentBean = new FriendBean.ContentBean();
            contentBean.foldContent = realContent.substring(0, 30);
            contentBean.noFoldContent = realContent;
            friendBean.isShowFold = i % 2 == 1;
            friendBean.contentBean = contentBean;

            //图片
            List<ImageInfo> imageInfos = new ArrayList<>();
            for (int x = 0; x < 9; x++) {
                ImageInfo imageInfo = new ImageInfo();
                imageInfo.setBigImageUrl("https://b2c.hycaichang.com/upload/20161026/8c9c7362d94023f573f4b34b0f9603b8.jpg");
                imageInfo.setThumbnailUrl("https://b2c.hycaichang.com/upload/20161026/8c9c7362d94023f573f4b34b0f9603b8.jpg");
                imageInfos.add(imageInfo);
            }
            friendBean.imageInfoList = imageInfos;


            //点赞
            StringBuilder likeStr = new StringBuilder();
            for (int k = 0; k < (i + 1); k++) {
                likeStr.append("小明" + k)
                        .append("，");
            }
            friendBean.strLike = likeStr.substring(0, likeStr.length() - 1);

            //评论
            List<FriendBean.FriendComment> comments = new ArrayList<>();
            for (int j = 0; j < 3; j++) {
                FriendBean.FriendComment comment = new FriendBean.FriendComment();
                switch (j) {
                    case 0:
                        comment.name = "小明";
                        comment.content = "小明的评论\uD83D\uDC7F";
                        break;
                    case 1:
                        comment.name = "小明";
                        comment.replyName = "小红";
                        comment.content = "小红回复小明的评论\uD83D\uDC7F";
                        break;
                    case 2:
                        comment.name = "小明";
                        comment.replyName = "小刚";
                        comment.content = "小刚回复小明的评论小刚回复小明的评论小刚回复小明的评论\uD83D\uDC7F";
                        break;
                }
                comments.add(comment);
            }
            friendBean.friendList = comments;
            items.add(friendBean);
        }
        adapter = new FriendCircleAdapter(items, this);
        wrapper = new HeaderAndFooterWrapper(adapter);
        wrapper.addHeaderView(headerView);
        recycler.setAdapter(wrapper);
    }

    @Override
    public void onEventMainThread(MainEvent event) {
        if (event instanceof MainEvent) {
            if (event.isScroll) {
                recycler.postDelayed(() -> {
                    int screenHeight = DisplayUtil.getScreenHeightInPx();
                    float itemGetBottom = screenHeight - event.y;//item偏移底部距离
                    Rect r = new Rect();
                    findViewById(R.id.root_layout).getWindowVisibleDisplayFrame(r);
                    int heightDiff = screenHeight - (r.bottom - r.top);
                    realKeyboardHeight = heightDiff - DisplayUtil.getStatusBarHeight();
                    float commentGetBottom = event.layoutCommentsY + realKeyboardHeight;//评论弹窗偏移底部距离

                    float distance = itemGetBottom - commentGetBottom;
                    recycler.smoothScrollBy(0, (int) (-distance));//正数往下滑，反之往上
                    LogUtils.e("-------------", "x:" + event.x + "------" + "y:" + event.y
                            + "---------"
                            + "keybroadY:" + realKeyboardHeight
                            + "\n"
                            + "distance:" + distance);
                }, 100);
            }
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.friend_circle, menu);
//        return super.onCreateOptionsMenu(menu);
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.refresh:
//                if (refreshPosition != 0) {
//                    wrapper.notifyItemChanged(refreshPosition, new FriendPayLoad());
//                } else {
//                    wrapper.notifyDataSetChanged();
//                }
//                break;
//            case R.id.test:
//                List<FriendBean> friendBeans = adapter.getData();
//                friendBeans.get(0).imageInfoList = new ArrayList<>();
//                refreshPosition = 0 + wrapper.getHeadersCount();
//                break;
//        }
//        return super.onOptionsItemSelected(item);
//    }

    public void setRefreshPosition(int refreshPosition, Object payload) {
        this.refreshPosition = refreshPosition + wrapper.getHeadersCount();
        wrapper.notifyItemChanged(this.refreshPosition, payload);
    }

    public String getName() {
        return friendModel.name;
    }
}
