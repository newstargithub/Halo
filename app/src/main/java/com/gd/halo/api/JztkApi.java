package com.gd.halo.api;

import com.gd.halo.bean.data.JztkData;
import com.gd.halo.util.Constant;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by zhouxin on 2016/11/10.
 * Description:
 */
public interface JztkApi {
    @GET("jztk/query?key=" + Constant.juhe.APPKEY_Jztk)
    Observable<JztkData> query(@Query("subject") String subject, @Query("model") String model, @Query("testType") String testType);
}
