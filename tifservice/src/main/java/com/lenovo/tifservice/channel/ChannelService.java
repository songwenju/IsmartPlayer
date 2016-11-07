package com.lenovo.tifservice.channel;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * songwenju on 16-7-26 : 14 : 02.
 * 邮箱：songwenju@outlook.com
 */
public interface ChannelService {
    @GET("/v3/gw")   //注意这个后面不要写成这样/v3/gw/和这样v3/gw
    Observable<ChannelResult> getResult(@Query("method") String method,
                                        @Query("appKey") String appKey,
                                        @Query("format") String format,
                                        @Query("albumId") String albumId,
                                        @Query("pageNo") String pageNo,
                                        @Query("pageSize") String pageSize
                                        );
}
