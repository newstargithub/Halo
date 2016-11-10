package com.gd.halo.bean;

/**
 * Created by zhouxin on 2016/7/12.
 * Description:
 */
public class LoadState {
    public static int STATE_NONE = 0;
    public static int STATE_LOAD = 1;
    public static int STATE_ERROR = 2;

    private int state;
    private String error;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
