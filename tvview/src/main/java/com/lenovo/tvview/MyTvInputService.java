package com.lenovo.tvview;

import android.content.Context;
import android.media.tv.TvContentRating;
import android.media.tv.TvInputService;
import android.media.tv.TvTrackInfo;
import android.net.Uri;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.View;

import java.util.List;

public class MyTvInputService  extends TvInputService {
    private static String TAG = "TIF";
    
    @Override
    public void onCreate() {
    	super.onCreate();
    	TAG = getClass().getName();
    	Log.d(TAG, " onCreate");
    }
    
    protected String getInputID(){
    	return getPackageName()+"/" + getClass().getName();
    }

	@Override
    public Session onCreateSession(String inputId) {
		Log.e(TAG, "onCreateSession");
        return new MySession(this);
    }

    private class MySession extends Session{

        public MySession(Context context){
            super(context);
            
        }

        @Override
        public boolean onKeyLongPress(int keyCode, KeyEvent event) {
            Log.i(TAG, "Session onKeyLongPress");
            return super.onKeyLongPress(keyCode, event);
        }

        @Override
        public boolean onKeyMultiple(int keyCode, int count, KeyEvent event) {
            Log.i(TAG, "Session onKeyLongPress");
            return super.onKeyMultiple(keyCode, count, event);
        }

        @Override
        public boolean onKeyUp(int keyCode, KeyEvent event) {
            Log.i(TAG, "Session onKeyUp");
            return super.onKeyUp(keyCode, event);
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            Log.i(TAG, "Session onTouchEvent");
            return super.onTouchEvent(event);
        }

        @Override
        public boolean onTrackballEvent(MotionEvent event) {
            Log.i(TAG, "Session onTrackBallEvent");
            return super.onTrackballEvent(event);
        }

        @Override
        public boolean onGenericMotionEvent(MotionEvent event) {
            Log.i(TAG, "Session onGenericMotionEvent");
            return super.onGenericMotionEvent(event);
        }

        @Override
        public View onCreateOverlayView() {
            Log.i(TAG, "Session onCreateOverlayView");
            return super.onCreateOverlayView();
        }

        @Override
        public void onSurfaceChanged(int format, int width, int height) {
            Log.i(TAG, "Session onSurfaceChanged");
            super.onSurfaceChanged(format, width, height);
        }

        @Override
        public void onUnblockContent(TvContentRating unblockedRating) {
            Log.i(TAG, "Session onUnblockContent");
            super.onUnblockContent(unblockedRating);
        }

        @Override
        public boolean onSelectTrack(int type, String trackId) {
            Log.i(TAG, "Session onSelectTrack");
            return super.onSelectTrack(type, trackId);
        }

        @Override
        public void notifyContentBlocked(TvContentRating rating) {
            Log.i(TAG, "Session notifyContentBlocked");
            super.notifyContentBlocked(rating);
        }

        @Override
        public void notifyContentAllowed() {
            Log.i(TAG, "Session notifyContentAllowed");
            super.notifyContentAllowed();
        }

        @Override
        public void notifyVideoUnavailable(int reason) {
            Log.i(TAG, "Session notifyVideoUnavailable");
            super.notifyVideoUnavailable(reason);
        }

        @Override
        public void notifyVideoAvailable() {
            Log.i(TAG, "Session notifyVideoAvailable");
            super.notifyVideoAvailable();
        }

        @Override
        public void notifyTrackSelected(int type, String trackId) {
            Log.i(TAG, "Session notifyTrackSelected");
            super.notifyTrackSelected(type, trackId);
        }

        @Override
        public void notifyTracksChanged(List<TvTrackInfo> tracks) {
            Log.i(TAG, "Session notifyTracksChanged");
            super.notifyTracksChanged(tracks);
        }

        @Override
        public void notifyChannelRetuned(Uri channelUri) {
            Log.i(TAG, "Session onUnblockContent");
            super.notifyChannelRetuned(channelUri);
        }

        @Override
        public void setOverlayViewEnabled(boolean enable) {
            Log.i(TAG, "Session setOverlayViewEnabled " + enable);
            super.setOverlayViewEnabled(enable);
        }

        @Override
        public boolean onKeyDown(int keyCode, KeyEvent event) {
            Log.i(TAG, "Session onKeyDown " + keyCode);
            return super.onKeyDown(keyCode, event);
        }

        @Override
        public void onRelease() {
            Log.i(TAG, "Session onRelease");
        }

        @Override
        public boolean onSetSurface(Surface surface) {
            Log.i(TAG, "Session onSetSurface");
            return true;
        }

        @Override
        public void onSetStreamVolume(float volume) {
            Log.i(TAG, "Session onSetStreamVolume");
        }
        @Override
        public boolean onTune(Uri channelUri) {
        	Log.i(TAG, "Session onTune");
			notifyContentAllowed();
        	return true;
        }

        @Override
        public void onSetCaptionEnabled(boolean enabled) {
            Log.i(TAG, "Session onSetCaptionEnabled");
        }
    }
}
