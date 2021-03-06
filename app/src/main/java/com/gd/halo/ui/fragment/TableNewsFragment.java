package com.gd.halo.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.gd.halo.MainActivity;
import com.gd.halo.R;
import com.gd.halo.bean.NewsLabel;
import com.gd.halo.support.xml.PullParser;
import com.gd.halo.util.L;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TableNewsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TableNewsFragment extends BaseFragment {
    private static final String TAG = TableNewsFragment.class.getSimpleName();
    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     * 为每一个部分提供fragment，使用FragmentPagerAdapter派生类，它将每一个加载过的fragment
     * 保存在内存。如果这变得过于占据内存，也许更好是切换到FragmentStatePagerAdapter
     */
    private NewsPagerAdapter mNewsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    private Context mContext;

    public TableNewsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment TableNewsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TableNewsFragment newInstance() {
        L.d(TAG, "newInstance");
        TableNewsFragment fragment = new TableNewsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int layoutResID() {
        return R.layout.fragment_table_news;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    protected void initViewsAndEvents() {
        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
    }

    @Override
    protected void initData() {
        List<NewsLabel> list = PullParser.parseNewsLabel(mContext);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mNewsPagerAdapter = new NewsPagerAdapter(getChildFragmentManager(), list);
        mViewPager.setAdapter(mNewsPagerAdapter);
        setTab();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private void setTab() {
        if(getActivity() instanceof MainActivity) {
            TabLayout tab_layout = ((MainActivity) getActivity()).getTabLayout();
            tab_layout.removeAllTabs();
            tab_layout.setVisibility(View.VISIBLE);
            //要先给ViewPager设置Adapter，否则异常：ViewPager does not have a PagerAdapter set
            //java.lang.IllegalArgumentException: Tab belongs to a different TabLayout.
            tab_layout.setupWithViewPager(mViewPager);
        }
    }

    public class NewsPagerAdapter extends FragmentPagerAdapter {

        private final List<NewsLabel> mList;

        public NewsPagerAdapter(FragmentManager fm, List<NewsLabel> list) {
            super(fm);
            mList = list;
        }

        @Override
        public Fragment getItem(int position) {
            //调用getItem实例化fragment为给定页面
            NewsLabel newsLabel = mList.get(position);
            return NewsFragment.newInstance(1, newsLabel.getTitle(), newsLabel.getUrl());
        }

        @Override
        public int getCount() {
            // 显示的页面总数.
            return mList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mList.get(position).getTitle();
        }
    }
}
