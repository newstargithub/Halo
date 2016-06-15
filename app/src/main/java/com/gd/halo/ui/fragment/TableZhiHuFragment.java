package com.gd.halo.ui.fragment;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.View;

import com.gd.halo.R;
import com.gd.halo.util.L;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TableZhiHuFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TableZhiHuFragment extends BaseFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = TableZhiHuFragment.class.getSimpleName();

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private TabLayout tab_layout;

    public TableZhiHuFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TableZhiHuFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TableZhiHuFragment newInstance(String param1, String param2) {
        L.d(TAG, "newInstance");
        TableZhiHuFragment fragment = new TableZhiHuFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    protected int layoutResID() {
        return R.layout.fragment_table_zhi_hu;
    }

    @Override
    protected void initViewsAndEvents() {
        tab_layout = (TabLayout) getActivity().findViewById(R.id.tab_layout);
    }

    @Override
    protected void initData() {
        setTab();
        getChildFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_layout, ZhiHuFragment.newInstance())
                .commit();
    }

    private void setTab() {
        tab_layout.removeAllTabs();
        tab_layout.setVisibility(View.GONE);
        tab_layout.setupWithViewPager(null);
    }
}
