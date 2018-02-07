package com.dangdailife.frienddemo.util.base;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import com.dangdailife.frienddemo.R;
import com.dangdailife.frienddemo.util.ActManager;
import com.dangdailife.frienddemo.util.LogUtils;
import com.dangdailife.frienddemo.util.eventbus.AsyncEvent;
import com.dangdailife.frienddemo.util.eventbus.BackgroundEvent;
import com.dangdailife.frienddemo.util.eventbus.MainEvent;
import com.dangdailife.frienddemo.util.eventbus.PostEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;

/**
 * @author Joy Wong
 * @description activity基类
 * @datetime 2016/06/22 16:33
 * @email wjb18814888154@gmail.com
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected BaseActivity mActivity;
    private static final String TAG = "BaseActivity";

    /**
     * flag，该活动是否可见
     */
    private boolean isVisible;

//    protected Handler mHandler = new Handler(Looper.getMainLooper());

//    protected ScreenStateReceiver screenReceiver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        mActivity = this;
        LogUtils.d(TAG, getClass().getSimpleName() + ":onCreate");
        ActManager.addActivity(this);
        setContentView();
        ButterKnife.bind(this);
        if (!EventBus.getDefault().isRegistered(mActivity)) {
            EventBus.getDefault().register(mActivity);
        }
        initData();
        initBar();
        initView();
        fetchData();
    }

    protected void setContentView() {
    }


    protected void initBar() {
        Log.e("--------", "initBar");
    }

    protected void initView() {
        Log.e("--------", "initView");
    }

    /**
     * 从intent获取数据，绑定数据
     */
    protected void initData() {

    }

    /**
     * 从网络获取数据
     */
    protected void fetchData() {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogUtils.d(TAG, getClass().getSimpleName() + ":onDestroy");
        ActManager.removeActivity(this);
        EventBus.getDefault().unregister(mActivity);
    }


    @Override
    public void onBackPressed() {
        try {
            super.onBackPressed();
            overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_to_right);
        } catch (Exception e) {
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        LogUtils.d(TAG, getClass().getSimpleName() + ":onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        LogUtils.d(TAG, getClass().getSimpleName() + ":onResume");
        isVisible = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        LogUtils.d(TAG, getClass().getSimpleName() + ":onPause");
        isVisible = false;
    }

    @Override
    protected void onStop() {
        super.onStop();
        LogUtils.d(TAG, getClass().getSimpleName() + ":onStop");
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
    }

    //---------------------分割线--------------------------
    //eventbus

    /**
     * 在发布线程运行
     * 事件的处理在和事件的发送在相同的进程，所以事件处理时间不应太长，不然影响事件的发送线程，而这个线程可能是UI线程.
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.POSTING)
    public void onEvent(PostEvent event) {
    }

    /**
     * 在主线程运行 sticky(粘性)表示可以传给未创建的界面
     * 事件的处理会在UI线程中执行,事件处理不应太长时间
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onEventMainThread(MainEvent event) {
    }

    /**
     * 表示如果事件在UI线程中发布出来的，那么订阅函数onEvent就会在子线程中运行，如果事件本来就是在子线程中发布出来的，那么订阅函数直接在该子线程中执行。
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onEventBackgroundThread(BackgroundEvent event) {
    }

    /**
     * 总是新建子线程
     * 事件处理会在单独的线程中执行，主要用于在后台线程中执行耗时操作，每个事件会开启一个线程（有线程池），但最好限制线程的数目。
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void onEventAsync(AsyncEvent event) {
    }
}
