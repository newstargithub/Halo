package com.gd.halo.view;

import com.gd.halo.bean.Carrier;

import java.util.List;

/**
 * Created by zhouxin on 2016/11/9.
 * Description:
 */
public interface CarrierListView extends BaseView{
    void showData(List<Carrier> list);
}
