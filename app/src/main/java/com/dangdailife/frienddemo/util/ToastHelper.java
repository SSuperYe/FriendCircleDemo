package com.dangdailife.frienddemo.util;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

/**
 * @author Joy Wong
 * @description toast帮助类
 * @datetime 2016/06/22 17:41
 * @email wjb18814888154@gmail.com
 */
public class ToastHelper {
    private static final String TAG = "ToastHelper";

    private static Context mContext;

    private static Toast toastShort;
    private static Toast toastLong;

    public static void prepareContext(Context context) {
        mContext = context;
    }

    public static void showShort(final String message) {
        LogUtils.d(TAG, message);

        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                if (toastShort == null) {
                    toastShort = Toast.makeText(mContext, message, Toast.LENGTH_SHORT);
                } else {
                    toastShort.setText(message);
                }
                toastShort.show();
            }
        });
    }

    public static void showShort(final int id) {
        LogUtils.d(TAG, id + "");
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                if (toastShort == null) {
                    toastShort = Toast.makeText(mContext, id, Toast.LENGTH_SHORT);
                } else {
                    toastShort.setText(id);
                }
                toastShort.show();
            }
        });
    }

    public static void showLong(final String message) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                if (toastLong == null) {
                    toastLong = Toast.makeText(mContext, message, Toast.LENGTH_LONG);
                } else {
                    toastLong.setText(message);
                }
                toastLong.show();
            }
        });
    }

    public static void showLong(final int id) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                if (toastLong == null) {
                    toastLong = Toast.makeText(mContext, id, Toast.LENGTH_LONG);
                } else {
                    toastLong.setText(id);
                }
                toastLong.show();
            }
        });
    }
}
