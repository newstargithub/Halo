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
public abstract class JsonCallback<T> extends AbsCallback<T> {

    private Class<T> clazz;
    private Type type;

    public JsonCallback(Class<T> clazz) {
        this.clazz = clazz;
    }

    public JsonCallback(Type type) {
        this.type = type;
    }

    @Override
    public T parseNetworkResponse(Response response) throws Exception {
        String responseData = response.body().string();
        if (TextUtils.isEmpty(responseData)) return null;

        /**
         * 一般来说，服务器返回的响应码都包含 code，msg，data 三部分，在此根据自己的业务需要完成相应的逻辑判断
         * 以下只是一个示例，具体业务具体实现
         */
        JSONObject jsonObject = new JSONObject(responseData);
        final String msg = jsonObject.optString("msg", "");
        final int code = jsonObject.optInt("code", 0);
        String data = jsonObject.optString("data", "");
        switch (code) {
            case 0:
                /**
                 * code = 0 代表成功，默认实现了Gson解析成相应的实体Bean返回，可以自己替换成fastjson等
                 * 对于返回参数，先支持 String，然后优先支持class类型的字节码，最后支持type类型的参数
                 */
                if (clazz == String.class) return (T) data;
                if (clazz != null) return new Gson().fromJson(data, clazz);
                if (type != null) return new Gson().fromJson(data, type);
                break;
            default:
                throw new IllegalStateException("错误代码：" + code + "，错误信息：" + msg);
        }
        return null;
    }

}
