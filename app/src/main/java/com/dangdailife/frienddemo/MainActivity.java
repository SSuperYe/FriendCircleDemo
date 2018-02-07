package com.dangdailife.frienddemo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.dangdailife.frienddemo.friendcircle.FriendCircleActivity;
import com.dangdailife.frienddemo.util.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {


    @BindView(R.id.btn_friend)
    Button btnFriend;

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_main);
    }

    @OnClick(R.id.btn_friend)
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_friend:
                startActivity(new Intent(mActivity, FriendCircleActivity.class));
                break;
        }
    }
}
