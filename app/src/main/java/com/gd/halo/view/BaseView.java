package com.gd.halo.view;

/**
 * Created by zhouxin on 2016/11/7.
 * Description: View接口，提供一些列简洁的方法函数，比如showError( )或者showProgressIndicator( )
 */
public interface BaseView {

    void showProgressIndicator(boolean show);

    void showError();


}
