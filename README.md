# 超级播放器 #

**SuperPlayer** 是一个基于IjkPlayer的控制器，支持手势操作，滑动快进，快退，支持，上滑音量亮度的变化，支持指点位置播放，播放源的切换，亲测可用，废话少说，先上图

# Screenshots #
![change](https://github.com/supercwn/SuperPlayer/blob/master/gif/super_player1.gif)
![change](https://github.com/supercwn/SuperPlayer/blob/master/gif/super_player2.gif)
![change](https://github.com/supercwn/SuperPlayer/blob/master/gif/super_player3.gif)

#How to User#

gradle

	ps：还有时间上传到maven，过几天在上传上去，喜欢的可以直接Clone or download

#视频点播(直播)功能(播放网络视频，或者视频直播)#
设置视频源(视频地址)是否是直播还是点播
	
	player.setLive(true);//true：表示直播地址；false表示点播地址

设置播放过程中是否监听网络的变化

	player.setNetChangeListener(true);//true ： 表示监听网络的变化；false ： 播放的过程中不监听网络的变化

设置播放过程中的视频开始播放
	
	player.onPrepared(new SuperPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared() {
                        /**
                         * 监听视频是否已经准备完成开始播放。（可以在这里处理视频封面的显示跟隐藏）
                         */
                    }
                })

设置视频是否已经播放完成

	player.onComplete(new Runnable() {
                    @Override
                    public void run() {
                        /**
                         * 监听视频是否已经播放完成了。（可以在这里处理视频播放完成进行的操作）
                         */
                    }
                })

设置视频播放失败的监听

	player.onError(new SuperPlayer.OnErrorListener() {
                    @Override
                    public void onError(int what, int extra) {
                        /**
                         * 监听视频播放失败的回调
                         */

                    }
                })
	
设置视频播放的信息

	player.onInfo(new SuperPlayer.OnInfoListener() {
                    @Override
                    public void onInfo(int what, int extra) {
                        /**
                         * 监听视频的相关信息。
                         */

                    }
                })

设置视频播放的标题
	
	player.setTitle(url)//设置视频的titleName

设置视频播放大小

	player.setScaleType(SuperPlayer.SCALETYPE_FITXY);
	其中可以设置
	/**
	 * fitParent:scale the video uniformly (maintain the video's aspect ratio)
	 * so that both dimensions (width and height) of the video will be equal to
	 * or **less** than the corresponding dimension of the view. like
	 * ImageView's `CENTER_INSIDE`.等比缩放,画面填满view。
	 */
	public static final String SCALETYPE_FITPARENT = "fitParent";
	/**
	 * fillParent:scale the video uniformly (maintain the video's aspect ratio)
	 * so that both dimensions (width and height) of the video will be equal to
	 * or **larger** than the corresponding dimension of the view .like
	 * ImageView's `CENTER_CROP`.等比缩放,直到画面宽高都等于或小于view的宽高。
	 */
	public static final String SCALETYPE_FILLPARENT = "fillParent";
	/**
	 * wrapContent:center the video in the view,if the video is less than view
	 * perform no scaling,if video is larger than view then scale the video
	 * uniformly so that both dimensions (width and height) of the video will be
	 * equal to or **less** than the corresponding dimension of the view.
	 * 将视频的内容完整居中显示，如果视频大于view,则按比例缩视频直到完全显示在view中。
	 */
	public static final String SCALETYPE_WRAPCONTENT = "wrapContent";
	/**
	 * fitXY:scale in X and Y independently, so that video matches view
	 * exactly.不剪裁,非等比例拉伸画面填满整个View
	 */
	public static final String SCALETYPE_FITXY = "fitXY";
	/**
	 * 16:9:scale x and y with aspect ratio 16:9 until both dimensions (width
	 * and height) of the video will be equal to or **less** than the
	 * corresponding dimension of the view.不剪裁,非等比例拉伸画面到16:9,并完全显示在View中。
	 */
	public static final String SCALETYPE_16_9 = "16:9";
	/**
	 * 4:3:scale x and y with aspect ratio 4:3 until both dimensions (width and
	 * height) of the video will be equal to or **less** than the corresponding
	 * dimension of the view.不剪裁,非等比例拉伸画面到4:3,并完全显示在View中。
	 */
	public static final String SCALETYPE_4_3 = "4:3";

设置视频播放的手势操作

	/**
	 * 设置小屏幕是否支持手势操作（默认false）
	 * @param isSupportGesture
	 * 				true : 支持（小屏幕支持，大屏幕支持）
	 * 				false ：不支持（小屏幕不支持,大屏幕支持）
	 * @return
     */
	public SuperPlayer setSupportGesture(boolean isSupportGesture){
		this.isSupportGesture = isSupportGesture;
		return this;
	}

还有很多的相关设置，就不一一的列出。具体的请看[SuperPlayer.class](https://github.com/supercwn/SuperPlayer/blob/master/superplayerlibrary/src/main/java/com/superplayer/library/SuperPlayer.java)

#列表播放器#
很多的设置跟点播直播的一致。但是比较应该注意的地方

其一：初始化适配器

	private void initAdapter() {
        mAdapter = new SuperVideoAdapter(this,dataList);
        superRecyclerView.setAdapter(mAdapter);
        mAdapter.setPlayClick(new SuperVideoAdapter.onPlayClick() {
            @Override
            public void onPlayclick(int position, RelativeLayout image) {
                image.setVisibility(View.GONE);
                if (player.isPlaying() && lastPostion == position){
                    return;
                }

                postion = position;
                if (player.getVideoStatus() == IjkVideoView.STATE_PAUSED) {

                    if (position != lastPostion) {
                        player.stopPlayVideo();
                        player.release();
                    }
                }
                if (lastPostion != -1) {
                    player.showView(R.id.adapter_player_control);
                }

                View view = superRecyclerView.findViewHolderForAdapterPosition(position).itemView;
                FrameLayout frameLayout = (FrameLayout) view.findViewById(R.id.adapter_super_video);
                frameLayout.removeAllViews();
                player.showView(R.id.adapter_player_control);
                frameLayout.addView(player);
                player.play(dataList.get(position).getVideoUrl());
                lastPostion = position;
            }
        });
        /**
         * 播放完设置还原播放界面
         */
        player.onComplete(new Runnable() {
            @Override
            public void run() {
                ViewGroup last = (ViewGroup) player.getParent();//找到videoitemview的父类，然后remove
                if (last != null && last.getChildCount() > 0) {
                    last.removeAllViews();
                    View itemView = (View) last.getParent();
                    if (itemView != null) {
                        itemView.findViewById(R.id.adapter_player_control).setVisibility(View.VISIBLE);
                    }
                }
            }
        });
        /***
         * 监听列表的下拉滑动
         */
        superRecyclerView.addOnChildAttachStateChangeListener(new SuperRecyclerView.OnChildAttachStateChangeListener() {
            @Override
            public void onChildViewAttachedToWindow(View view) {
                int index = superRecyclerView.getChildAdapterPosition(view);
                View controlview = view.findViewById(R.id.adapter_player_control);
                if (controlview == null) {
                    return;
                }
                view.findViewById(R.id.adapter_player_control).setVisibility(View.VISIBLE);
                if (index == postion) {
                    FrameLayout frameLayout = (FrameLayout) view.findViewById(R.id.adapter_super_video);
                    frameLayout.removeAllViews();
                    if (player != null &&
                            ((player.isPlaying()) || player.getVideoStatus() == IjkVideoView.STATE_PAUSED)) {
                        view.findViewById(R.id.adapter_player_control).setVisibility(View.GONE);
                    }
                    if (player.getVideoStatus() == IjkVideoView.STATE_PAUSED) {
                        if (player.getParent() != null)
                            ((ViewGroup) player.getParent()).removeAllViews();
                        frameLayout.addView(player);
                        return;
                    }
                }
            }

            @Override
            public void onChildViewDetachedFromWindow(View view) {
                int index = superRecyclerView.getChildAdapterPosition(view);
                if ((index) == postion) {
                    if (true) {
                        if (player != null) {
                            player.stop();
                            player.release();
                            player.showView(R.id.adapter_player_control);
                        }
                    }
                }
            }
        });
    }
	
其二：重写onConfigurationChanged

	@Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (player != null) {
            player.onConfigurationChanged(newConfig);
            if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
                fullScreen.setVisibility(View.GONE);
                fullScreen.removeAllViews();
                superRecyclerView.setVisibility(View.VISIBLE);
                if (postion <= mLayoutManager.findLastVisibleItemPosition()
                        && postion >= mLayoutManager.findFirstVisibleItemPosition()) {
                    View view = superRecyclerView.findViewHolderForAdapterPosition(postion).itemView;
                    FrameLayout frameLayout = (FrameLayout) view.findViewById(R.id.adapter_super_video);
                    frameLayout.removeAllViews();
                    ViewGroup last = (ViewGroup) player.getParent();//找到videoitemview的父类，然后remove
                    if (last != null) {
                        last.removeAllViews();
                    }
                    frameLayout.addView(player);
                }
                int mShowFlags =
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
                fullScreen.setSystemUiVisibility(mShowFlags);
            } else {
                ViewGroup viewGroup = (ViewGroup) player.getParent();
                if (viewGroup == null)
                    return;
                viewGroup.removeAllViews();
                fullScreen.addView(player);
                fullScreen.setVisibility(View.VISIBLE);
                int mHideFlags =
                        View.SYSTEM_UI_FLAG_LOW_PROFILE
                                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                | View.SYSTEM_UI_FLAG_FULLSCREEN
                                | View.SYSTEM_UI_FLAG_IMMERSIVE
                                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        ;
                fullScreen.setSystemUiVisibility(mHideFlags);
            }
        } else {
            fullScreen.setVisibility(View.GONE);
        }
    }

#如果对该库有疑问或者有bug

欢迎大家提Issues，或者加QQ。楼主很乐意为你们解决问题。
	
#关于我

我是个流浪在广东珠海的小开发贼，喜欢玩暗影岛的虚拟世界中寻找自我（实则是个坑，如果有时间可以加个好友带带我）

联系方式：QQ 953267615 微信：super南仔

喜欢楼主的都可以加个朋友（ps：加的时候记得加上备注，为什么要加楼主，否则莫怪我残忍拒绝了）

#我的另外个开源库，喜欢的给个star

[SuperRecycleView](https://github.com/supercwn/SuperRecycleView)

#打赏支持
如果该库帮助到了你，为你减少了开发的时间，请随意打赏。您的支持将鼓励我继续创作！

![change](https://github.com/supercwn/SuperPlayer/blob/master/gif/wechat.png)


#License

	Copyright 2016 supercwn

	Licensed under the Apache License, Version 2.0 (the "License");
	you may not use this file except in compliance with the License.
	You may obtain a copy of the License at
	
	   http://www.apache.org/licenses/LICENSE-2.0
	
	Unless required by applicable law or agreed to in writing, software
	distributed under the License is distributed on an "AS IS" BASIS,
	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
	See the License for the specific language governing permissions and
	limitations under the License.*
