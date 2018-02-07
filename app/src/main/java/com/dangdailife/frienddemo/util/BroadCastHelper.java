package com.dangdailife.frienddemo.util;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;

import com.dangdailife.frienddemo.MyApplication;

/**
 * @author Joy Wong
 * @description
 * @datetime 2016/09/14 9:44
 * @email wjb18814888154@gmail.com
 */
public class BroadCastHelper {

    private static final LocalBroadcastManager LOCAL_BROADCAST_MANAGER = LocalBroadcastManager.getInstance(MyApplication.getInstance());

    private static final String PACKAGE_NAME = MyApplication.getInstance().getPackageName();


    /**
     * 微信支付结果
     */
    public static final String WXPAY = PACKAGE_NAME + ".WXPAY";

    /**
     * 用户购物车中商品数量变化
     */
    public static final String CART_CHANGED = PACKAGE_NAME + ".CART_CHANGED";

    /**
     * 操作类型，增加、减少、删除、清空
     */
    public static final String CART_CHANGED_TYPE = "type";
    /**
     * 改变的商品id
     */
    public static final String CART_CHANGED_ID = "goods_id";

    /**
     * 商品数量改变后的数量，仅在GoodsDelegate这个类，即非购物车界面中增加物品才传递数量
     */
    public static final String CART_CHANGED_NUMBER = "goods_number";


    /**
     * 地址变更的广播
     */
    public static final String ADDRESS_CHANGED = PACKAGE_NAME + ".store";

    /**
     * 变更后的地址
     */
    public static final String KEY_ADDRESS_CHANGED = "address";


    /**
     * 订单状态改变的广播
     */
    public static final String ORDER_STATUS_CHANGED = PACKAGE_NAME + ".order";

    /**
     * 是否刷新团购订单列表
     */
    public static final String KEY_GROUP_ORDER = "group_order";


    /**
     * 变更的订单编号
     */
    public static final String KEY_ORDER_ID = "order_id";

    /**
     * 变更的订单状态
     */
    public static final String KEY_ORDER_STATUS = "order_status";

    /**
     * 变更的订单状态
     */
    public static final String KEY_DELIVERY_STATUS = "delivery_status";

    /**
     * 改变根视图当前tab的广播
     */
    public static final String MAIN_TAB = PACKAGE_NAME + ".tab";

    /**
     * tab的序号
     */
    public static final String KEY_TAB_INDEX = "tab_index";

    /**
     * 改变根视图上购物车tab的数量显示的广播
     */
    public static final String MAIN_CART_NUM = PACKAGE_NAME + ".cartNum";

    /**
     * 购物车内商品总数
     */
    public static final String KEY_CART_NUM = "CART_NUM";


    /**
     * 登录成功的广播
     */
    public static final String LOGIN_SUCCEED = "LOGIN_SUCCEED";

    /**
     * 登出的广播
     */
    private static final String LOG_OUT = PACKAGE_NAME + "log_out";

    /**
     * 取消支付的广播
     */
    public static final String CANCEL_PAY = PACKAGE_NAME + "cancel_pay";

    /**
     * 取消团购支付的广播
     */
    public static final String CANCEL_GROUP_PAY = PACKAGE_NAME + "cancel_group_pay";

    /**
     * 判断地址从预购处重新登录后的添加地址是否需要校验配送范围的广播
     */
    public static final String ADDRESS_PREORDER_LOGIN_IS_CHECK_POSITION = "address_pre_login_is_check_position";
    public static final String ADDRESS_PREORDER_LOGIN_IS_CHECK_POSITION_TYPE = "address_pre_login_is_check_position_type";

    /**
     * 是否刷新个人中心
     */
    public static final String PERSON_DATA_REFRESH = "person_data_refresh";
    public static final String KEY_PERSON_DATA = "person_data";

    public static void sendBroadcast(Intent intent) {
        LOCAL_BROADCAST_MANAGER.sendBroadcast(intent);
    }

    public static void registerReceiver(BroadcastReceiver receiver, IntentFilter filter) {
        LOCAL_BROADCAST_MANAGER.registerReceiver(receiver, filter);
    }

    public static void unregisterReceiver(BroadcastReceiver receiver) {
        LOCAL_BROADCAST_MANAGER.unregisterReceiver(receiver);
    }

    public static void sendLogoutMessage() {
        Intent intent = new Intent(LOG_OUT);
        LOCAL_BROADCAST_MANAGER.sendBroadcast(intent);
    }

    public static void registerLogoutReceiver(BroadcastReceiver receiver) {
        IntentFilter intentFilter = new IntentFilter(LOG_OUT);
        LOCAL_BROADCAST_MANAGER.registerReceiver(receiver, intentFilter);
    }

}
