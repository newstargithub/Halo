package com.gd.halo.ui.activity;

import android.os.Bundle;
import android.view.View;

import com.gd.halo.R;
import com.gd.halo.base.BaseToolbarActivity;
import com.gd.halo.ui.fragment.JztkFragment;

public class CarrierActivity extends BaseToolbarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrier);
    }

    @Override
    protected void initViewsAndEvents() {
        super.initViewsAndEvents();
        /*getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_layout, new CarrierListFragment()).commit();
        setActionBarTitle(R.string.carrier);*/
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_layout, new JztkFragment()).commit();
        setActionBarTitle(R.string.jztk);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected View getLoadingTargetView() {
        return findViewById(R.id.frame_layout);
    }

    @Override
    public boolean canBack() {
        return true;
    }

    @Override
    protected boolean isRegisterEvent() {
        return false;
    }

}
