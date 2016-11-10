package com.gd.halo.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gd.halo.R;
import com.gd.halo.bean.Carrier;

import java.util.List;

/**
 * Created by zhouxin on 2016/11/10.
 * Description:
 */
public class CarrierListAdapter extends BaseQuickAdapter<Carrier>{
    public CarrierListAdapter(List<Carrier> data) {
        super(R.layout.item_fragment_news, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, Carrier item) {
        baseViewHolder.setText(R.id.title, item.getCarrier_name());
        baseViewHolder.setText(R.id.content, item.getCarrier_phone())
        .setText(R.id.time, item.getCarrier_code());
    }
}
