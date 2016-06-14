package com.gd.halo.bean;

/**
 * Created by zhouxin on 2016/6/7.
 * Description:
 */
public class ZhuHuResponse<T> {

    /**
     * count : 10
     * error :
     */

    private int count;
    private String error;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
