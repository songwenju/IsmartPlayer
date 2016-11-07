package com.lenovo.vitamiovideo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import io.vov.vitamio.LibsChecker;
import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.widget.MediaController;
import io.vov.vitamio.widget.VideoView;

public class MainActivity extends AppCompatActivity {

    /**
     * path.
     */
    //视频路径
    private String path = "";
    private VideoView mVideoView;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        //对vitamio框架进行初始化。返回值用来做相关操作，通常没有要作的事情，初始化一定要在显示界面之前。
        if (!LibsChecker.checkVitamioLibs(this))
            return;
        //显示界面
        setContentView(R.layout.activity_main);
        //初始化组件
        mVideoView = (VideoView) findViewById(R.id.surface_view);
        //设置的两个网络流媒体地址，大家可以直接进行测试。
//        path = "http://devimages.apple.com/iphone/samples/bipbop/bipbopall.m3u8";
//        path = "http://www.modrails.com/videos/passenger_nginx.mov";
        path = "http://www.tudou.com/a/Lqfme5hSolM/&iid=130959528/v.swf";

        if (path == "") {
            // Tell the user to provide a media file URL/path.
            return;
        } else {
            /*
             * 也可以用这个方法来播放流媒体
             * mVideoView.setVideoURI(Uri.parse(URLstring));
             */
            //设置videoview播放的路径
            mVideoView.setVideoPath(path);
            //创建视频播放时的控制器，这个控制器可以自定义。此处是默认的实现
            mVideoView.setMediaController(new MediaController(this));
            //请求焦点
            mVideoView.requestFocus();
            //设置播放监听
            mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    // optional need Vitamio 4.0
                    //设置重放速度
                    mediaPlayer.setPlaybackSpeed(1.0f);
                }
            });
            //加载结束后开始播放，这行代码可以控制视频的播放。
            mVideoView.start();
        }

    }
}
