package com.lenovo.tvview;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.media.tv.TvContentRating;
import android.media.tv.TvContract;
import android.media.tv.TvInputInfo;
import android.media.tv.TvInputManager;
import android.media.tv.TvTrackInfo;
import android.media.tv.TvView;
import android.media.tv.TvView.TvInputCallback;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

import java.util.List;

public class MainActivity extends Activity {
	private static String TAG = "TIF";
	private Uri mResultUri;
	private TvInputManager mTvInputManager;
	private TvView mTvView;
	private TvInputCallback mCallBack = new TvInputCallback() {
		@Override
		public void onContentBlocked(String inputId, TvContentRating rating) {
			Log.i(TAG, "TvView onContent block");
			super.onContentBlocked(inputId, rating);
		}
		
		public void onContentAllowed(String inputId) {
			Log.i(TAG, "TvView onContent block:" + inputId);
		};
		
		public void onVideoAvailable(String inputId) {
			Log.i(TAG, "TvView onVideoAvailable");
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mTvView = (TvView) findViewById(R.id.tv_view);
		mTvView.setCallback(mCallBack);
	}
	
	public void onClick(View v){
		Log.i(TAG, "onClick");
		int id = v.getId();
		if(id == R.id.btn_play_tv){
			playTv();
		}else if(id == R.id.btn_start_service){
			startService();
		}else if(id == R.id.btn_insert_channel){
			insertChannel();
		}else if(id == R.id.btn_dispatch_key){
			dispatcheKeyEvent();
		}else if(id == R.id.btn_release){
			resetTvView();
		}
		
	}

	private void resetTvView() {
		Log.i(TAG, "resetTvView");
		mTvView.reset();
	}

	private void dispatcheKeyEvent() {
		mTvView.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_MEDIA_PAUSE));
		mTvView.setStreamVolume(0.5f);
		mTvView.setCaptionEnabled(false);
		mTvView.selectTrack(TvTrackInfo.TYPE_VIDEO,"aa");
	}

	private void insertChannel() {
		Log.i(TAG, "insertChannel");
		ContentValues values = new ContentValues();
		values.put(TvContract.Channels.COLUMN_DISPLAY_NAME, "ABC");
		values.put(TvContract.Channels.COLUMN_INPUT_ID, getInputId());
		mResultUri = getContentResolver().insert(TvContract.Channels.CONTENT_URI,values );
		Log.i(TAG, "insert channel:" + mResultUri);
	}

	private void playTv() {
		Log.i(TAG, "playTv");
		Log.e(TAG, "tune----------------------------------:" + mResultUri);
		mTvView.tune(getInputId(), mResultUri);
		
	}
	
	private String getInputId(){
		if(mTvInputManager == null){
		    mTvInputManager = (TvInputManager) getSystemService(Context.TV_INPUT_SERVICE);
		}
		List<TvInputInfo> list = mTvInputManager.getTvInputList();

		return list.get(list.size() - 1).getId();
	}

	private void startService() {
		Log.i(TAG, "startService");
		Intent i = new Intent(this,MyTvInputService.class);
		startService(i);
	}
}
