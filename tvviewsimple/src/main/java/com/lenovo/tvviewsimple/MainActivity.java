package com.lenovo.tvviewsimple;

import android.content.Context;
import android.media.tv.TvContentRating;
import android.media.tv.TvInputInfo;
import android.media.tv.TvInputManager;
import android.media.tv.TvTrackInfo;
import android.media.tv.TvView;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "swj";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        String inputId = "lenovo.com.ismartvlive/.Ismartvliveservice";
        String inputId = "com.lenovo.tifservice/.TvService";
        TvInputManager tvInputManager = (TvInputManager) getSystemService(Context.TV_INPUT_SERVICE);

        List<TvInputInfo> list = tvInputManager.getTvInputList();
        for(TvInputInfo info:list){
            Log.i(TAG, "id:" + info.getId());
        }
        TvView tvView = (TvView) findViewById(R.id.tv_view);
        tvView.reset();
        tvView.tune(inputId, Uri.parse("content://main/35"));

        tvView.setCallback(new TvView.TvInputCallback() {
            @Override
            public void onConnectionFailed(String inputId) {
                super.onConnectionFailed(inputId);
                LogUtil.i(this,"MainActivity.onConnectionFailed:"+inputId);
            }

            @Override
            public void onDisconnected(String inputId) {
                super.onDisconnected(inputId);
                LogUtil.i(this,"MainActivity.onDisconnected.");
            }

            @Override
            public void onChannelRetuned(String inputId, Uri channelUri) {
                super.onChannelRetuned(inputId, channelUri);
                LogUtil.i(this,"MainActivity.onChannelRetuned.");
            }

            @Override
            public void onTracksChanged(String inputId, List<TvTrackInfo> tracks) {
                super.onTracksChanged(inputId, tracks);
                LogUtil.i(this,"MainActivity.onTracksChanged.");
            }

            @Override
            public void onTrackSelected(String inputId, int type, String trackId) {
                super.onTrackSelected(inputId, type, trackId);
                LogUtil.i(this,"MainActivity.onTrackSelected.");
            }

            @Override
            public void onVideoSizeChanged(String inputId, int width, int height) {
                super.onVideoSizeChanged(inputId, width, height);
                LogUtil.i(this,"MainActivity.onVideoSizeChanged.");
            }

            @Override
            public void onVideoAvailable(String inputId) {
                super.onVideoAvailable(inputId);
                LogUtil.i(this,"MainActivity.onVideoAvailable.inputId:"+inputId);
            }

            @Override
            public void onVideoUnavailable(String inputId, int reason) {
                super.onVideoUnavailable(inputId, reason);
                LogUtil.i(this,"MainActivity.onVideoUnavailable.");
            }

            @Override
            public void onContentAllowed(String inputId) {
                super.onContentAllowed(inputId);
                LogUtil.i(this,"MainActivity.onContentAllowed.");
            }

            @Override
            public void onContentBlocked(String inputId, TvContentRating rating) {
                super.onContentBlocked(inputId, rating);
                LogUtil.i(this,"MainActivity.onContentBlocked.");
            }

            @Override
            public void onTimeShiftStatusChanged(String inputId, int status) {
                super.onTimeShiftStatusChanged(inputId, status);
                LogUtil.i(this,"MainActivity.onTimeShiftStatusChanged.");
            }
        });
    }
}
