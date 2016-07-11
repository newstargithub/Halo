package com.gd.halo.ui.fragment;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.View;

import com.gd.halo.MainActivity;
import com.gd.halo.R;
import com.gd.halo.util.L;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TableAnswersFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TableAnswersFragment extends BaseFragment {
    private static final String TAG = TableAnswersFragment.class.getSimpleName();

    public TableAnswersFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment.
     */
    public static TableAnswersFragment newInstance() {
        L.d(TAG, "newInstance");
        TableAnswersFragment fragment = new TableAnswersFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int layoutResID() {
        return R.layout.fragment_table_zhi_hu;
    }

    @Override
    protected void initViewsAndEvents() {

    }

    @Override
    protected void initData() {
        setTab();
        getChildFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_layout, PostAnswersFragment.newInstance())
                .commit();
    }

    private void setTab() {
        if(getActivity() instanceof MainActivity) {
            TabLayout tab_layout = ((MainActivity) getActivity()).getTabLayout();
            tab_layout.removeAllTabs();
            tab_layout.setVisibility(View.GONE);
            tab_layout.setupWithViewPager(null);
        }
    }
}
