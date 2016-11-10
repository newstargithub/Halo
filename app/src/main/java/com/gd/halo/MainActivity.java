package com.gd.halo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.gd.halo.base.BaseActivity;
import com.gd.halo.bean.NewsBean;
import com.gd.halo.bean.Posts;
import com.gd.halo.ui.activity.CarrierActivity;
import com.gd.halo.ui.fragment.LoadListFragment;
import com.gd.halo.ui.fragment.NewsFragment;
import com.gd.halo.ui.fragment.TableAnswersFragment;
import com.gd.halo.ui.fragment.TableNewsFragment;
import com.gd.halo.ui.fragment.TableZhiHuFragment;
import com.gd.halo.ui.fragment.ZhiHuFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        NewsFragment.OnNewsItemClickListener,
        ZhiHuFragment.OnZhiHuItemClickListener,
        LoadListFragment.OnLoadListClickListener {

    private static final String FRAGMENT_TAG_NEWS = "news";
    private static final String FRAGMENT_TAG_ZHIHU = "zhihu";
    private static final String FRAGMENT_TAG_ANSWERS = "answers";
    private Context mContext;
    private Fragment mFragment; //当前显示的Fragment

    @BindView(R.id.tab_layout)
    TabLayout tab_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        /*if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }*/

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                Intent intent = new Intent(mContext, CarrierActivity.class);
                startActivity(intent);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
            this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mContext = this;
        ButterKnife.bind(this);

        navigationView.setCheckedItem(R.id.nav_camera);
        //没有通知到onNavigationItemSelected，手动设置默认
        switchFragment(getTableNewsFragment(), FRAGMENT_TAG_NEWS);
    }

    @Override
    protected void initViewsAndEvents() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    @Override
    protected boolean isRegisterEvent() {
        return false;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle action
            switchFragment(getTableNewsFragment(), FRAGMENT_TAG_NEWS);
        } else if (id == R.id.nav_gallery) {
            switchFragment(getTableZhiHuFragment(), FRAGMENT_TAG_ZHIHU);
        } else if (id == R.id.nav_slideshow) {
            switchFragment(getTableAnswersFragment(), FRAGMENT_TAG_ANSWERS);
        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {
            switchTheme();
        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void setTitle(String title) {
        if(getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }
    }

    private Fragment getTableNewsFragment() {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(FRAGMENT_TAG_NEWS);
        if(fragment == null) {
            fragment = TableNewsFragment.newInstance();
        }
        return fragment;
    }

    private Fragment getTableZhiHuFragment() {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(FRAGMENT_TAG_ZHIHU);
        if(fragment == null) {
            fragment = TableZhiHuFragment.newInstance("", "");
        }
        return fragment;
    }

    private Fragment getTableAnswersFragment() {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(FRAGMENT_TAG_ANSWERS);
        if(fragment == null) {
            fragment = TableAnswersFragment.newInstance();
        }
        return fragment;
    }

    private void switchFragment(Fragment fragment, String tag){
//        switchFragmentShow(fragment, tag);        //只是隐藏
//        switchFragmentAttach(fragment, tag);       //不会销毁,但会重新创建view
        switchFragmentReplace(fragment, tag);   //会销毁替换
    }

    /**
     *  只有在Fragment数量大于等于2的时候，调用add()还是replace()的区别才能体现出来。
     *  当通过add()连续两次添加Fragment的时候，每个Fragment生命周期中的onAttach()-onResume()都会被各调用一次，
     *  而且两个Fragment的View会被同时attach到containerView。
     *  但当使用replace()来添加Fragment的时候，第二次添加会导致第一个Fragment被销毁，
     *  即执行第二个Fragment的onAttach()方法之前会先执行第一个Fragment的onPause()-onDetach()方法，
     *  同时containerView会detach第一个Fragment的View。
     * @param fragment
     * @param tag
     */
    private void switchFragmentReplace(Fragment fragment, String tag){
        if(mFragment != fragment) {
            mFragment = fragment;
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.frame_layout, fragment, tag);
            ft.commit();
            setTitle(tag);
        }
    }

    /**
     * 当fragment进行切换时，采用隐藏与显示的方法加载fragment以防止数据的重复加载
     *  hide/show
     *  调用show() & hide()方法时，Fragment的生命周期方法并不会被执行，仅仅是Fragment的View被显示或者​隐藏。
     *  而且，尽管Fragment的View被隐藏，但它在父布局中并未被detach，仍然是作为containerView的childView存在着。
     * @param fragment
     */
    private void switchFragmentShow(Fragment fragment, String tag){
        if (mFragment != fragment) {
            FragmentManager fm = getSupportFragmentManager();
            //添加渐隐渐现的动画
            FragmentTransaction ft = fm.beginTransaction();
            if(mFragment != null) {
                ft.hide(mFragment); //隐藏fragment
            }
            if (!fragment.isAdded()) {    // 先判断是否被add过
                ft.add(R.id.frame_layout, fragment, tag).commit(); //add fragment到Activity中
            } else {
                ft.show(fragment).commit(); // 显示fragment
            }
            mFragment = fragment;
            setTitle(tag);
        }
    }

    /**
     * 相比较下，attach() & detach()做的就更彻底一些。一旦一个Fragment被detach()，它的onPause()-onDestroyView()周期都会被执行。
     * 在重新调用attach()后，onCreateView()-onResume()周期也会被再次执行。
     * 相对应add()方法执行onAttach()-onResume()的生命周期，remove()就是完成剩下的onPause()-onDetach()周期。
     * @param fragment
     * @param tag
     */
    private void switchFragmentAttach(Fragment fragment, String tag){
        if (mFragment != fragment) {
            FragmentManager fm = getSupportFragmentManager();
            //可添加渐隐渐现的动画
            FragmentTransaction ft = fm.beginTransaction();
            if(mFragment != null) {
                ft.detach(mFragment);   //分离fragment
            }
            if (fragment.isDetached()) {    // 先判断是否被add过
                ft.attach(fragment).commit(); // 附着fragment
            } else {
                ft.add(R.id.frame_layout, fragment, tag).commit(); //add fragment到Activity中
            }
            mFragment = fragment;
            setTitle(tag);
        }
    }

    @Override
    public void OnNewsItemClick(NewsBean item) {

    }

    @Override
    public void OnZhiHuItemClick(Posts.PostsBean item) {

    }

    @Override
    public void OnLoadListClick(Object item) {

    }

    public TabLayout getTabLayout() {
        return tab_layout;
    }
}
