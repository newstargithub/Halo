package com.gd.halo.ui.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gd.halo.R;
import com.gd.halo.base.GMVPBaseFragment;
import com.gd.halo.bean.Carrier;
import com.gd.halo.presenter.CarrierPresenter;
import com.gd.halo.ui.adapter.CarrierListAdapter;
import com.gd.halo.util.L;
import com.gd.halo.view.CarrierListView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zhouxin on 2016/11/9.
 * Description: 快递列表
 */
public class CarrierListFragment extends GMVPBaseFragment<CarrierListView, CarrierPresenter> implements CarrierListView, SwipeRefreshLayout.OnRefreshListener {

    private static final long DELAY_MILLIS = 1000;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    private CarrierListAdapter mAdapter;
    private boolean mIsRequestDataRefresh;

    @Override
    protected int getContentView() {
        return R.layout.fragment_list;
    }

    @Override
    protected void initViewsAndEvents() {
        swipeRefreshLayout.setColorSchemeResources(R.color.refresh_progress_1,
                R.color.refresh_progress_2, R.color.refresh_progress_3);
        swipeRefreshLayout.setOnRefreshListener(this);
        setupRecyclerView();
        initData();
    }

    private void setupRecyclerView() {
//        final StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2,
//                StaggeredGridLayoutManager.VERTICAL);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new CarrierListAdapter(null);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void initData() {
        /*Android support library 24.2.0存在问题
        http://stackoverflow.com/questions/26858692/swiperefreshlayout-setrefreshing-not-showing-indicator-initially
        swipeRefreshLayout.getViewTreeObserver()
                .addOnGlobalLayoutListener(
                        new ViewTreeObserver.OnGlobalLayoutListener() {
                            @Override
                            public void onGlobalLayout() {
                                swipeRefreshLayout.getViewTreeObserver()
                                        .removeGlobalOnLayoutListener(this);
                                setRefresh(true);
                                onRefresh();
                            }
                        });*/
        setRefresh(true);
        onRefresh();
    }

    @Override
    protected View getLoadingTargetView() {
        return findViewById(R.id.ll_content);
    }

    @Override
    protected boolean isRegisterEvent() {
        return false;
    }

    @Override
    protected CarrierPresenter createPresenter() {
        return new CarrierPresenter(this);
    }

    @Override
    public void showData(List<Carrier> list) {
        mAdapter.setNewData(list);
    }

    @Override
    public void showProgressIndicator(boolean show) {
        setRefresh(show);
        /*if(show) {
            showLoading(null);
        } else {
            hideLoading();
        }*/
    }

    @Override
    public void showError() {
        L.d("showError");
        showError(null);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onRefresh() {
        mPresenter.getCarriers();
    }

    public void setRefresh(boolean requestDataRefresh) {
        if (swipeRefreshLayout == null) {
            return;
        }
        if (!requestDataRefresh) {
            mIsRequestDataRefresh = false;
            // 防止刷新消失太快，让子弹飞一会儿.
            swipeRefreshLayout.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (swipeRefreshLayout != null) {
                        L.d("swipeRefreshLayout.setRefreshing(false)");
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }
            }, 1000);
        } else {
            L.d("swipeRefreshLayout.setRefreshing(true)");
            swipeRefreshLayout.setRefreshing(true);
        }
    }
}
