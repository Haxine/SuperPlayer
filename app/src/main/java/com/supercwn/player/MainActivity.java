package com.supercwn.player;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.tv_demand_play).setOnClickListener(this);//点播
        findViewById(R.id.tv_live_play).setOnClickListener(this);//直播
        findViewById(R.id.tv_listview_player).setOnClickListener(this);//listView
        findViewById(R.id.tv_recycleview_player).setOnClickListener(this);//recycleView
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.tv_demand_play) {
            Intent demandIntent = new Intent(this,SuperVideoDetailsActivity.class);
            demandIntent.putExtra("isLive",false);
            demandIntent.putExtra("url","http://video.1mbz.com/job/201609/prxb4xK6WT.mp4");
            startActivity(demandIntent);

        } else if (view.getId() == R.id.tv_live_play) {
            Intent liveIntent = new Intent(this,SuperVideoDetailsActivity.class);
            liveIntent.putExtra("isLive",true);
            liveIntent.putExtra("url","rtmp://live.hkstv.hk.lxdns.com/live/hks");
            startActivity(liveIntent);

        } else if (view.getId() == R.id.tv_listview_player) {
            Intent listViewIntent = new Intent(this,SuperVideoListViewActivity.class);
            startActivity(listViewIntent);
        } else if (view.getId() == R.id.tv_recycleview_player) {
            Toast.makeText(this, "Super南仔还在努力的开发中", Toast.LENGTH_SHORT).show();
        }
    }
}
