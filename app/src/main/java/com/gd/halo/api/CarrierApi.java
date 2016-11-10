package com.gd.halo.api;

import com.gd.halo.bean.CarrierData;
import com.gd.halo.util.Constant;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by zhouxin on 2016/11/9.
 * Description:
 */
public interface CarrierApi {

    @GET("test/getCarriers.php?key=" + Constant.juhe_APPKEY)
    Observable<CarrierData> loadCarriers();

}
