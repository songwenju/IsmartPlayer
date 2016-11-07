package com.lenovo.ismartplayer;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;

import java.io.IOException;



public class MainActivity extends Activity implements View.OnClickListener {
    private static final String TAG = "swjSmart";
    private Context mContext;
    private SurfaceView surface1;
    private Button start, stop, pre;
    private MediaPlayer mMediaPlayer;
//    public static final String URL_1 = "http://cord.tvxio.com/v1_0/I2/frk/api/live/m3u8/6/ffb5680f-8f15-462b-b6ab-a5b12c041f28/medium/";
    public static final String URL_1 = "/mnt/shell/emulated/0/Movies/2.mp4";
    public static final String URL_2 = "http://cord.tvxio.com/v1_0/I2/frk/api/live/m3u8/10/d5df4d8e-7e6b-4bdd-96de-fcb9e289a6b1/medium/";

//    private long mPosition = 0;
    private int mPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!io.vov.vitamio.LibsChecker.checkVitamioLibs(this))
            return;
        setContentView(R.layout.activity_main);
        mContext = this;
        surface1 = (SurfaceView) findViewById(R.id.surface1);
        start = (Button) findViewById(R.id.start);
        stop = (Button) findViewById(R.id.stop);
        pre = (Button) findViewById(R.id.pre);
//        mMediaPlayer = new MediaPlayer(mContext);
        mMediaPlayer = new MediaPlayer();
        //设置播放时打开屏幕
        surface1.getHolder().setKeepScreenOn(true);
        surface1.getHolder().addCallback(new SurfaceViewLis());
        start.setOnClickListener(this);
        stop.setOnClickListener(this);
        pre.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start:
                LogUtil.i(this, "start.");
                LogUtil.i(this,"MainActivity.onClick.url:"+URL_1);
                play(URL_1);
                break;
            case R.id.pre:
                Log.i(TAG, "pre");
                play(URL_2);
                break;
            case R.id.stop:
                LogUtil.i(this, "stop");
                if (mMediaPlayer.isPlaying())
                    mMediaPlayer.stop();
                break;
            default:
                break;
        }

    }

    public void play(String url) {

        try {
            mMediaPlayer.reset();
            mMediaPlayer.setVolume(AudioManager.STREAM_MUSIC,AudioManager.STREAM_MUSIC);
            mMediaPlayer.setDataSource(url);
            // 把视频输出到SurfaceView上
            mMediaPlayer.setDisplay(surface1.getHolder());
            mMediaPlayer.prepare();
            mMediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class SurfaceViewLis implements SurfaceHolder.Callback {

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width,
                                   int height) {
        }

        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            if (mPosition == 0) {
                play(URL_1);
                mMediaPlayer.seekTo(mPosition);

            }

        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {

        }

    }

    @Override
    protected void onPause() {
        if (mMediaPlayer.isPlaying()) {
            // 保存当前播放的位置
            mPosition = mMediaPlayer.getCurrentPosition();
            mMediaPlayer.stop();
        }
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        if (mMediaPlayer.isPlaying())
            mMediaPlayer.stop();
        mMediaPlayer.release();
        super.onDestroy();
    }

}