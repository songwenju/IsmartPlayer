package com.lenovo.tifservice;

import android.content.ContentValues;
import android.content.Context;
import android.media.tv.TvContract;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.lenovo.tifservice.channel.ChannelApi;
import com.lenovo.tifservice.channel.ChannelResult;
import com.lenovo.tifservice.channel.ChannelResult.MultiResultBean.ResultsBean;
import com.lenovo.tifservice.channel.ChannelService;
import com.lenovo.tifservice.channel.SimpleChannel;

import java.util.ArrayList;
import java.util.List;

import io.vov.vitamio.LibsChecker;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    private ChannelService mChannelService;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        //初始化API
        ChannelApi channelApi = ChannelApi.getInstance();
        mChannelService = channelApi.getChannelService();
        //初始化Vitamio
        LibsChecker.checkVitamioLibs(this);
        setContentView(R.layout.activity_main);
        addData();
    }

    private void addData() {
        mChannelService.getResult("album.item.get", "myKey", "json", "Lqfme5hSolM", "1", "5")
                .subscribeOn(Schedulers.newThread()) //请求数据在子线程
                .map(new Func1<ChannelResult, List<SimpleChannel>>() {
                    @Override
                    public List<SimpleChannel> call(ChannelResult channelResult) {
                        List<SimpleChannel> simpleChannels = new ArrayList<SimpleChannel>();
                        List<ResultsBean> results
                                = channelResult.getMultiResult().getResults();
                        for (ResultsBean resultBean : results) {
                            SimpleChannel simpleChannel = new SimpleChannel(
                                    resultBean.getSubTitle(), resultBean.getOuterPlayerUrl());

                            simpleChannels.add(simpleChannel);
                        }
                        return simpleChannels;
                    }
                }).subscribeOn(Schedulers.newThread())
                .map(new Func1<List<SimpleChannel>, List<SimpleChannel>>() {
                    @Override
                    public List<SimpleChannel> call(List<SimpleChannel> simpleChannels) {
                        //写入数据库
                        for (SimpleChannel simpleChannel : simpleChannels) {
                            LogUtil.i(this, "MainActivity.call." + simpleChannel.toString());
                            insertChannelsData(mContext, simpleChannel);
                        }
                        return simpleChannels;
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<SimpleChannel>>() {    // 相当于onNext()
                    @Override
                    public void call(List<SimpleChannel> s) {
                        LogUtil.i(this, "MainActivity.endCall.");
                    }
                }, new Action1<Throwable>() {                       // 相当于onError()
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();
                    }
                });


    }


    /**
     * 写入channel到数据库
     *
     * @param context 上下文
     * @param channel channel信息
     */
    public static void insertChannelsData(Context context, SimpleChannel channel) {
        ContentValues value = new ContentValues();
        value.put(TvContract.Channels.COLUMN_INPUT_ID, "com.lenovo.tifservice/.TvService");
        value.put(TvContract.Channels.COLUMN_DISPLAY_NUMBER, channel.playAddress);
        value.put(TvContract.Channels.COLUMN_DISPLAY_NAME, channel.title);
        context.getContentResolver().insert(TvContract.Channels.CONTENT_URI, value);
    }

}
