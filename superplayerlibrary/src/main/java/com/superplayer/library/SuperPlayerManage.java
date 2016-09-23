package com.superplayer.library;

import android.content.Context;

/**
 *
 * 类描述：获取唯一的视频控制器
 *
 * @author Super南仔
 * @time 2016-9-19
 */
public class SuperPlayerManage {
    public static SuperPlayerManage videoPlayViewManage;
    private SuperPlayer videoPlayView;

    private SuperPlayerManage() {

    }

    public static SuperPlayerManage getSuperManage() {
        if (videoPlayViewManage == null) {
            videoPlayViewManage = new SuperPlayerManage();
        }
        return videoPlayViewManage;
    }

    public SuperPlayer initialize(Context context) {
        if (videoPlayView == null) {
            videoPlayView = new SuperPlayer(context);
        }
        return videoPlayView;
    }
}
