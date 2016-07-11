package com.gd.halo.net;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.lzy.okhttputils.callback.AbsCallback;

import org.json.JSONObject;

import java.lang.reflect.Type;

import okhttp3.Response;

/**
 * Created by zhouxin on 2016/6/2.
 * Description: 解析json格式的响应
 */
public abstract class ZhihuJsonCallback<T> extends AbsCallback<T> {

    private Class<T> clazz;
    private Type type;

    public ZhihuJsonCallback(Class<T> clazz) {
        this.clazz = clazz;
    }

    public ZhihuJsonCallback(Type type) {
        this.type = type;
    }

    @Override
    public T parseNetworkResponse(Response response) throws Exception {
        String responseData = response.body().string();
        if (TextUtils.isEmpty(responseData)) return null;
        /**
         * 返回数据：json
         *  正确：{error : “”, data: … }
         *  错误：{error : “…”}
         *  服务器返回值之后首先检查error字段，如果不为空说明出错，为空再处理返回数据。
         */
        JSONObject jsonObject = new JSONObject(responseData);
        final String error = jsonObject.optString("error", "");
        if(TextUtils.isEmpty(error)) {
            if (clazz == String.class) return (T) responseData;
            if (clazz != null) return new Gson().fromJson(responseData, clazz);
            if (type != null) return new Gson().fromJson(responseData, type);
        } else {
            throw new IllegalStateException("错误信息：" + error);
        }
        return null;
    }

}
