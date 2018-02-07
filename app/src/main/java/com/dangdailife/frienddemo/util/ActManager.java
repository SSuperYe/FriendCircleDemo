package com.dangdailife.frienddemo.util;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Joy Wong
 * @description activity的管理类
 * @datetime 2016/06/22 16:12
 * @email wjb18814888154@gmail.com
 */
public class ActManager {

    private static List<Activity> activityList = new ArrayList<>();

    public static void addActivity(Activity activity) {
        if (!activityList.contains(activity)) {
            activityList.add(activity);
        }
    }

    public static void removeActivity(Activity activity) {
        if (activityList.contains(activity)) {
            activityList.remove(activity);
        }
    }

//    /**
//     * 获取当前activity数量
//     * @return
//     */
//    public static int getActivityNumber(){
//        return activityList.size();
//    }

    /**
     * 关闭指定的activity
     *
     * @param clz
     */
    public static void finish(Class<? extends Activity> clz) {
        if (activityList.size() > 0) {
            for (Activity activity : activityList) {
                if (clz.isInstance(activity) && !activity.isFinishing()) {
                    activity.finish();
                }
            }
        }
    }

    /**
     * 检查指定的activity是否存在
     *
     * @param clz
     * @return
     */
    public static boolean check(Class<? extends Activity> clz) {
        if (activityList.size() > 0) {
            for (Activity activity : activityList) {
                if (clz.isInstance(activity) && !activity.isFinishing()) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 关闭所有activity
     */
    public static void finishAll() {
        if (activityList.size() > 0) {
            for (Activity activity : activityList) {
                if (!activity.isFinishing()) {
                    activity.finish();
                }
            }
        }
    }

    /**
     * 获取当前栈顶的活动
     *
     * @return
     */
    public static Activity getCurrentActivity() {
        if (activityList.size() > 0) {
            return activityList.get(activityList.size() - 1);
        }
        return null;
    }

    /**
     * 获取前一个活动
     *
     * @return
     */
    public static Activity getPreviousActivity() {
        if (activityList.size() > 0) {
            return activityList.get(activityList.size() - 2);
        }
        return null;
    }

    /**
     * 获取某一个活动
     *
     * @return
     */
    public static Activity getDesignActivity(Class<? extends Activity> clz) {
        if (activityList.size() > 0) {
            for (Activity activity : activityList) {
                if (clz.isInstance(activity) && !activity.isFinishing()) {
                    return activity;
                }
            }
        }
        return null;
    }
}
