package com.dangdailife.frienddemo.bean;

import java.util.List;

/**
 * @author Mr.Ye
 * @description
 * @datetime 2018/02/01 15:21
 * @email superrhye@163.com
 */

public class FriendModel {

    public String name;//用户昵称

    public String headUrl;//用户头像

    public String bgUrl;//用户背景

    public List<MsgBean> msgList;//消息集合

    public List<FriendBean> friendBeanList;//该用户的动态集合

    public static class MsgBean {
        public String url;//该消息的图像
    }
}
