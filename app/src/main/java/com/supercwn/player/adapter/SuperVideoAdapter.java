package com.supercwn.player.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.supercwn.player.R;
import com.supercwn.player.bean.VideoListBean;
import com.superplayer.library.utils.SuperPlayerUtils;

import java.util.List;


/**
 *
 * @author Super南仔
 * @time 2016-9-19
 */
public class SuperVideoAdapter extends RecyclerView.Adapter<SuperVideoAdapter.VideoViewHolder> {
    private final Context mContext;
    private List<VideoListBean> dataList;

    public SuperVideoAdapter(Context context,List<VideoListBean> dataList) {
        this.mContext = context;
        this.dataList = dataList;
    }

    @Override
    public SuperVideoAdapter.VideoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(mContext, R.layout.adapter_listview_layout, null);
        VideoViewHolder holder = new VideoViewHolder(view);
        view.setTag(holder);
        return new VideoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SuperVideoAdapter.VideoViewHolder holder, int position) {
        holder.update(position);
    }

    @Override
    public int getItemCount() {
        return dataList == null ? 0 : dataList.size();
    }

    public class VideoViewHolder extends RecyclerView.ViewHolder {
        public RelativeLayout rlayPlayerControl;
        private RelativeLayout rlayPlayer;

        public VideoViewHolder(View itemView) {
            super(itemView);
            rlayPlayerControl = (RelativeLayout) itemView.findViewById(R.id.adapter_player_control);
            rlayPlayer = (RelativeLayout) itemView.findViewById(R.id.adapter_super_video_layout);
            if (rlayPlayer!=null){
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) rlayPlayer.getLayoutParams();
                layoutParams.height = (int) (SuperPlayerUtils.getScreenWidth((Activity) mContext) * 0.5652f);//这值是网上抄来的，我设置了这个之后就没有全屏回来拉伸的效果，具体为什么我也不太清楚
                rlayPlayer.setLayoutParams(layoutParams);
            }
        }

        public void update(final int position) {
            //点击回调 播放视频
            rlayPlayerControl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (playclick != null)
                        playclick.onPlayclick(position, rlayPlayerControl);
                }
            });
        }
    }

    private onPlayClick playclick;

    public void setPlayClick(onPlayClick playclick) {
        this.playclick = playclick;
    }

    public interface onPlayClick {
        void onPlayclick(int position, RelativeLayout image);
    }

}
