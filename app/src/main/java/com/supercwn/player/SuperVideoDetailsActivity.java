package com.supercwn.player;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.superplayer.library.mediaplayer.SuperPlayer;

/**
 * Created by Administrator on 2016-09-12.
 */
public class SuperVideoDetailsActivity extends AppCompatActivity implements View.OnClickListener, SuperPlayer.OnNetChangeListener {

    private SuperPlayer player;
    private boolean isLive;

    /**
     * 测试地址
     */
    private String url;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_super_video_layout);
        initData();
        initView();
        initPlayer();
    }

    /**
     * 初始化相关的信息
     */
    private void initData(){
        isLive = getIntent().getBooleanExtra("isLive",false);
        url = getIntent().getStringExtra("url");
    }

    /**
     * 初始化视图
     */
    private void initView(){
        findViewById(R.id.tv_replay).setOnClickListener(this);
    }

    /**
     * 初始化播放器
     */
    private void initPlayer(){
        player = new SuperPlayer(this);
        if(isLive){
            player.setLive(true);//设置该地址是直播的地址
        }
        player.setNetChangeListener(true)//设置监听手机网络的变化
                .setOnNetChangeListener(this)//实现网络变化的回调
                .onPrepared(new SuperPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared() {
                        /**
                         * 监听视频是否已经准备完成开始播放。（可以在这里处理视频封面的显示跟隐藏）
                         */
                    }
                }).onComplete(new Runnable() {
                    @Override
                    public void run() {
                        /**
                         * 监听视频是否已经播放完成了。（可以在这里处理视频播放完成进行的操作）
                         */

                    }
                }).onInfo(new SuperPlayer.OnInfoListener() {
                    @Override
                    public void onInfo(int what, int extra) {
                        /**
                         * 监听视频的相关信息。
                         */

                    }
                }).onError(new SuperPlayer.OnErrorListener() {
                    @Override
                    public void onError(int what, int extra) {
                        /**
                         * 监听视频播放失败的回调
                         */

                    }
                }).setTitle(url)//设置视频的titleName
                .play(url);//开始播放视频

    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.tv_replay){
            if(player != null){
                player.play(url);
            }
        }
    }

    /**
     * 网络链接监听类
     */
    @Override
    public void onWifi() {
        Toast.makeText(this,"当前网络环境是WIFI",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onMobile() {
        Toast.makeText(this,"当前网络环境是手机网络",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDisConnect() {
        Toast.makeText(this,"网络链接断开",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNoAvailable() {
        Toast.makeText(this,"无网络链接",Toast.LENGTH_SHORT).show();
    }


    /**
     * 下面的这几个Activity的生命状态很重要
     */
    @Override
    protected void onPause() {
        super.onPause();
        if (player != null) {
            player.onPause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (player != null) {
            player.onResume();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (player != null) {
            player.onDestroy();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (player != null) {
            player.onConfigurationChanged(newConfig);
        }
    }

    @Override
    public void onBackPressed() {
        if (player != null && player.onBackPressed()) {
            return;
        }
        super.onBackPressed();
    }

}
