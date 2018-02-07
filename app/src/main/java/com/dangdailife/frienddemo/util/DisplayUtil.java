package com.dangdailife.frienddemo.util;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

/**
 * @author Joy Wong
 * @description 显示帮助类
 * @datetime 2016/06/27 14:10
 * @email wjb18814888154@gmail.com
 */
public class DisplayUtil {
    private static DisplayMetrics dm= Resources.getSystem().getDisplayMetrics();


    /**
     * px转dp
     * @param px
     * @return
     */
    public static int px2dp(int px){
        return (int)(px/dm.density+0.5f);
    }

    /**
     * dp转px
     * @param dp
     * @return
     */
    public static int dp2px(float dp){
        return (int)(dp*dm.density+0.5f);
    }

    /**
     * 获取屏幕宽度，以px为单位
     * @return
     */
    public static int getScreenWidthInPx(){
        return dm.widthPixels;
    }

    /**
     * 获取屏幕宽度，以dp为单位
     * @return
     */
    public static int getScreenWidthInDp(){
        return px2dp(getScreenWidthInPx());
    }

    /**
     * 获取屏幕高度，以px为单位
     * @return
     */
    public static int getScreenHeightInPx(){
        return dm.heightPixels;
    }


    /**
     * 获取屏幕高度，以dp为单位
     * @return
     */
    public static int getScreenHeightInDp(){
        return px2dp(getScreenHeightInPx());
    }

    /**
     * @param context
     * @param multiple 几分之几
     * @return
     */
    public static int getWindowHeight(Context context, double multiple) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int height = windowManager.getDefaultDisplay().getHeight();
        Log.e("====height===", height + "");
        return (int) (height * multiple);
    }

    /**
     * 设置背景透明度
     *
     * @param context
     * @param alpha
     */
    public static void setBackgroundAlpha(Context context, float alpha) {
        WindowManager.LayoutParams layoutParams = ((Activity) context).getWindow().getAttributes();
        layoutParams.alpha = alpha;
        ((Activity) context).getWindow().setAttributes(layoutParams);
    }

    public static void setWindowAlpha(Activity activity, float windowAlpha){
        View view=activity.getWindow().getDecorView().getRootView();
        if (view!=null){
            view.setAlpha(windowAlpha);
        }

    }

    public static int getStatusBarHeight() {
        int result = 0;
        int resourceId = Resources.getSystem().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = Resources.getSystem().getDimensionPixelSize(resourceId);
        }
        return result;
    }


}
