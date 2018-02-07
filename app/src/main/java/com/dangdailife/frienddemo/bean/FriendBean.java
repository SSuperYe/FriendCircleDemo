package com.dangdailife.frienddemo.bean;

import com.lzy.ninegrid.ImageInfo;

import java.util.List;

/**
 * @author Mr.Ye
 * @description
 * @datetime 2018/01/31 14:01
 * @email superrhye@163.com
 */

public class FriendBean {

    public String name;//动态所有者

    public String headUrl;//所有者的头像

    public String time;//发布的时间

    public String address;//发布时候的地址

    public boolean isClickFold;//动态内容是否点击过折叠
    public boolean isShowFold;//是否展示折叠
    public ContentBean contentBean;//内容

    public List<ImageInfo> imageInfoList;//照片集合

    public List<VideoBean> videoBeanList;//视频集合

    public String strLike;//点赞字段

    public List<FriendComment> friendList;//朋友评论集合

    //每个动态下的评论
    public static class FriendComment {
        public String name;//发起评论的人
        public String replyName;//回复你的人（可能没有）
        public String content;//回复的内容（可能是自己的评论，也可能是别人对你的回复）
    }

    //内容类
    public static class ContentBean {
        public String noFoldContent;//没有折叠的内容
        public String foldContent;//折叠后的内容
    }

    //视频类
    public static class VideoBean {

    }
}
