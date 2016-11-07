package com.lenovo.tifservice;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.media.tv.TvContract;
import android.media.tv.TvInputService;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.view.Surface;

import com.lenovo.tifservice.channel.SimpleChannel;

import java.io.IOException;

import static android.content.ContentValues.TAG;

/**
 * songwenju on 16-10-26 : 17 : 14.
 * 邮箱：songwenju@outlook.com
 */

public class TvService extends TvInputService {
    private SimpleSessionImpl mSimpleSession;
    private Context mContext;
    @Nullable
    @Override
    public Session onCreateSession(String inputId) {
        LogUtil.i(this,"TvService.onCreateSession.inputId:"+inputId);
        mContext = this;
        mSimpleSession = new SimpleSessionImpl(this);
        return mSimpleSession;
    }


    public class SimpleSessionImpl extends Session {
        private MediaPlayer mMediaPlayer;
        private Surface mSurface ;

        /**
         * Creates a new Session.
         *
         * @param context The context of the application
         */
        public SimpleSessionImpl(Context context) {
            super(context);
            LogUtil.i(this,"SimpleSessionImpl.SimpleSessionImpl.");
        }

        @Override
        public void onRelease() {
            LogUtil.i(this,"SimpleSessionImpl.onRelease.");
        }

        @Override
        public boolean onSetSurface(Surface surface) {
            LogUtil.i(this,"SimpleSessionImpl.onSetSurface."+surface);
            mSurface = surface;
            return true;
        }

        @Override
        public void onSetStreamVolume(float volume) {
            LogUtil.i(this,"SimpleSessionImpl.onSetStreamVolume.");
        }

        @Override
        public boolean onTune(Uri channelUri) {
            LogUtil.i(this,"SimpleSessionImpl.onTune.");
            Long channelId = ContentUris.parseId(channelUri);
            LogUtil.d(this, "channelId:" + channelId);
            return setChannelIdAndPlay(channelId);
        }

        /**
         * 设置ChannelId并播放
         * @return
         * @param channelId
         */
        private boolean setChannelIdAndPlay(Long channelId) {
            SimpleChannel dbChannel = getDbChannel(mContext, channelId);
            LogUtil.i(this,"SimpleSessionImpl.setChannelIdAndPlay."+dbChannel.toString());
            mMediaPlayer = new MediaPlayer();
            try {
                mMediaPlayer.reset();
                mMediaPlayer.setDataSource(dbChannel.playAddress);
                mMediaPlayer.setSurface(mSurface);
                mMediaPlayer.prepare();
                mMediaPlayer.start();

            } catch (IOException e) {
                e.printStackTrace();
            }

            return false;
        }

        /**
         * 查询数据库，获得相应的值，如：播放地址，播放名称等
         *
         * @param channelId channelID
         * @return 数据集合
         */
        public  SimpleChannel getDbChannel(Context context, long channelId) {
            LogUtil.i(TAG, "DbUtil.getDbChannel channelId:" + channelId);
            SimpleChannel channel = new SimpleChannel();
            Uri queryUri = ContentUris.withAppendedId(TvContract.Channels.CONTENT_URI, channelId);
            Cursor channelsCursor = context.getContentResolver().query(queryUri, null, null, null, null);

            if (channelsCursor != null && channelsCursor.moveToNext()) {
                channel.playAddress = channelsCursor.getString(channelsCursor.getColumnIndex(TvContract.Channels.COLUMN_DISPLAY_NUMBER));
                channel.title = channelsCursor.getString(channelsCursor.getColumnIndex(TvContract.Channels.COLUMN_DISPLAY_NAME));

            }
            if (channelsCursor != null) {
                channelsCursor.close();
            }

            return channel;
        }


        @Override
        public void onSetCaptionEnabled(boolean enabled) {
            LogUtil.i(this,"SimpleSessionImpl.onSetCaptionEnabled.");
        }
    }

}
