package com.dangdailife.frienddemo;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.util.ArrayMap;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.dangdailife.frienddemo.util.ToastHelper;
import com.lzy.ninegrid.NineGridView;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * @author Mr.Ye
 * @description
 * @datetime 2017/08/01 19:54
 * @email superrhye@163.com
 */

public class MyApplication extends Application {

    public static Application mApplication;

    //统一处理4.4以下arraymap不能用的情况
    public static Map<String, String> map;

    /**
     * flag 是否已登录
     */
    private static boolean mHasLoggedOn;

    private static ExecutorService cacheThreadPoolService;
    private static ScheduledExecutorService schduleThreadPoolService;

    /**
     * @return 获取线程池接口（无核心线程，没有最大线程限制）
     */
    public static ExecutorService getCacheThreadPool() {
        if (null == cacheThreadPoolService) {
            cacheThreadPoolService = Executors.newCachedThreadPool();
        }
        return cacheThreadPoolService;
    }

    /**
     * @return 获取线程池接口（有核心线程，没有最大线程限制）,可做轮询、延时等操作
     */
    public static ScheduledExecutorService getSchduleThreadPool() {
        if (null == schduleThreadPoolService) {
            schduleThreadPoolService = Executors.newScheduledThreadPool(1);
        }
        return schduleThreadPoolService;
    }

    public static Application getInstance() {
        return mApplication;
    }

    /**
     * 为map设置值
     *
     * @param clean 是否清空map,默认false
     * @param key   key
     * @param value value
     */
    public static void setMap(String key, String value, boolean... clean) {
        if (clean.length == 0) {
            map.put(key, value);
            return;
        }
        if (clean[0]) {
            map.clear();
        }
        map.put(key, value);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            map = new ArrayMap<>();
        } else {
            map = new HashMap<>();
        }
        ToastHelper.prepareContext(this);
        initNineGridview();
    }

    private void initNineGridview() {
        NineGridView.setImageLoader(new NineGridView.ImageLoader() {
            @Override
            public void onDisplayImage(Context context, ImageView imageView, String url) {
                RequestOptions options = new RequestOptions().error(R.drawable.ic_default_image).placeholder(R.drawable.ic_default_image);
                Glide.with(context).load(url).apply(options).into(imageView);
            }

            @Override
            public Bitmap getCacheImage(String url) {
                return null;
            }
        });
    }
}
