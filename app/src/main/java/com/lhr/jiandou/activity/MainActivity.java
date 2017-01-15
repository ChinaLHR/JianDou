package com.lhr.jiandou.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lhr.jiandou.MyApplication;
import com.lhr.jiandou.R;
import com.lhr.jiandou.fragment.MovieFragment;
import com.lhr.jiandou.fragment.SettingFragment;
import com.lhr.jiandou.fragment.factory.FragmentFactory;
import com.lhr.jiandou.utils.Constants;
import com.lhr.jiandou.utils.PreferncesUtils;
import com.lhr.jiandou.utils.SpUtils;
import com.lhr.jiandou.utils.ToastUtils;
import com.lhr.jiandou.utils.UIUtils;
import com.lhr.jiandou.utils.jsoupUtils.GetNavImage;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements Toolbar.OnMenuItemClickListener {

    private android.support.design.widget.NavigationView mainnav;
    private android.support.v4.widget.DrawerLayout maindrawerlayout;
    private RelativeLayout main_container;
    private Toolbar main_toolbar;
    private FragmentManager mFragmentManager;
    private String title;
    private Fragment DefaultFragment;
    private long mExitTime = 0;
    private View headerView;
    private ImageView nav_header_img;
    private TextView nav_header_title;
    public static final String ACTION_LOCAL_SEND = "action.local.send";
    private static final String SAVE_STATE_TITLE = "title";
    private final LocalBroadcastReceiver localReceiver = new LocalBroadcastReceiver();
    private List<String> navList = new ArrayList<>();
    private mAsyncTask mAsy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /**
         *启动Activity,设置主题
         */
        String nowtheme = PreferncesUtils.getString(this, Constants.PREF_KEY_THEME, "1");
        if (nowtheme.equals("1")) {
            setTheme(R.style.AppTheme);
        } else {
            setTheme(R.style.AppTheme_Light);

        }
        setContentView(R.layout.activity_main);
        initView();
        initToolbar();
        setupDrawerContent();
        initFragment(savedInstanceState);
        initreceiver();
        if (MyApplication.isNetworkAvailable(this)) {
            mAsy = new mAsyncTask();
            mAsy.execute();
        } else {
            setDefaultNav();
        }

    }


    /**
     * 初始化广播接收器
     */
    private void initreceiver() {
        LocalBroadcastManager.getInstance(this).registerReceiver(localReceiver, new IntentFilter(ACTION_LOCAL_SEND));
    }

    /**
     * 初始化View
     */
    private void initView() {
        this.maindrawerlayout = (DrawerLayout) findViewById(R.id.main_drawer_layout);
        this.mainnav = (NavigationView) findViewById(R.id.main_nav);
        this.main_toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        this.main_container = (RelativeLayout) findViewById(R.id.main_container);
        main_toolbar.inflateMenu(R.menu.menu_toolbar);
        main_toolbar.setOnMenuItemClickListener(this);
        main_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                maindrawerlayout.openDrawer(GravityCompat.START);
            }
        });
        headerView = mainnav.getHeaderView(0);
        nav_header_img = (ImageView) headerView.findViewById(R.id.nav_header_img);
        nav_header_title = (TextView) headerView.findViewById(R.id.nav_header_title);
    }

    /**
     * 初始化Fragment
     */
    private void initFragment(Bundle savedInstanceState) {
        SpUtils.putBoolean(this, "istheme", true);
        //根据title创建Fragment
        if (savedInstanceState != null) {
            title = savedInstanceState.getString(SAVE_STATE_TITLE);
        }
        if (title == null) {
            title = UIUtils.getString(this, R.string.nav_menu_movie);
        }


        mFragmentManager = getSupportFragmentManager();
        DefaultFragment = mFragmentManager.findFragmentByTag(title);
        if (DefaultFragment == null) {
            Fragment movieFragment = new MovieFragment();
            mFragmentManager.beginTransaction()
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .add(R.id.main_container, movieFragment, title)
                    .commit();
            DefaultFragment = movieFragment;
        }
    }

    /**
     * 清空其他Fragment
     */
    public void removeFragment(String title) {
        mFragmentManager = getSupportFragmentManager();
        List<Fragment> fragments = mFragmentManager.getFragments();
        if (fragments == null) {
            return;
        }

        //保留title的Fragment
        for (Fragment fragment : fragments) {
            if (fragment == null || fragment.getTag().equals(title))
                continue;
            mFragmentManager.beginTransaction().remove(fragment).commit();
        }
    }

    /**
     * 初始化Toolbar
     */
    private void initToolbar() {
        if (main_toolbar.getTitle() == null) {
            main_toolbar.setTitle(UIUtils.getString(this, R.string.nav_menu_movie));
            mainnav.getMenu().getItem(0).setChecked(true);
        }
    }

    /**
     * nav menu点击事件
     */
    private void setupDrawerContent() {
        mainnav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                menuItem.setChecked(true);
                maindrawerlayout.closeDrawers();
                String title = (String) menuItem.getTitle();
                main_toolbar.setTitle(title);
                //根据menu的Title开启Fragment

                switchFragment(title);

                return true;
            }
        });
    }

    /**
     * 根据nav的menu开启Fragment
     *
     * @param title
     */
    private void switchFragment(String title) {
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        if (title.equals(UIUtils.getString(this, R.string.nav_menu_movie)) && Constants.CHANGELABEL_MOVIE) {
            Fragment movieFragment = createFragmentByTitle(title);
            transaction.hide(DefaultFragment);
            transaction.replace(R.id.main_container, movieFragment, title).commit();
            DefaultFragment = movieFragment;
        } else if (title.equals(UIUtils.getString(this, R.string.nav_menu_book)) && Constants.CHANGELABEL_BOOK) {
            Fragment bookFragment = createFragmentByTitle(title);
            transaction.hide(DefaultFragment);
            transaction.replace(R.id.main_container, bookFragment, title).commit();
            DefaultFragment = bookFragment;
        } else {
            //根据Tag判断是否已经开启了Fragment，如果开启了就直接复用，没开启就创建
            Fragment fragment = mFragmentManager.findFragmentByTag(title);
            if (fragment == null) {
                transaction.hide(DefaultFragment);
                fragment = createFragmentByTitle(title);
                transaction.add(R.id.main_container, fragment, title);
                DefaultFragment = fragment;
            } else if (fragment != null) {
                transaction.hide(DefaultFragment).show(fragment);
                DefaultFragment = fragment;
            }
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).
                    commit();
        }
    }

    /**
     * Toolbar menu点击事件
     *
     * @param item
     * @return
     */
    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.toolbar_menu_search:
                Intent mIntent = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(mIntent);
                break;
        }
        return false;
    }

    /**
     * 根据title创建Fragment
     *
     * @param title
     * @return
     */
    private Fragment createFragmentByTitle(String title) {
        if (title.equals(Constants.SETTING)) {
            SettingFragment mSettingFragment = new SettingFragment();
            return mSettingFragment;
        } else {
            Fragment fragment = FragmentFactory.getFragment(title);
            return fragment;
        }


    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (!maindrawerlayout.isDrawerOpen(GravityCompat.START)) {
                if ((System.currentTimeMillis() - mExitTime) > 2000) {
                    ToastUtils.show(MainActivity.this, "再按一次退出程序");
                    mExitTime = System.currentTimeMillis();
                } else {
                    ToastUtils.cancel();
                    finish();
                }
            } else {
                maindrawerlayout.closeDrawers();
            }
        }
        return true;
    }


    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outPersistentState.putString(SAVE_STATE_TITLE, title);

    }

    public class LocalBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Boolean isopen = PreferncesUtils.getBoolean(context, Constants.PREF_KEY_AUTO_IMG, false);
            if (isopen == true) {
                //开启自动更新图片
                if (!navList.isEmpty()) {
                    Glide.with(MainActivity.this)
                            .load(navList.get(1))
                            .into(nav_header_img);
                    nav_header_title.setText("每日一图：" + navList.get(0));
                }
            } else {
                //关闭自动更新图
                setDefaultNav();
            }
        }
    }

    private void setDefaultNav() {
        nav_header_img.setImageDrawable(getDrawable(R.drawable.nav_bg));
        nav_header_title.setText("简豆，简而美的APP");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(localReceiver);
    }

    @Override
    protected void onPause() {
        if (mAsy != null && mAsy.getStatus() == AsyncTask.Status.RUNNING) {
            mAsy.cancel(true);
        }
        super.onPause();
    }

    class mAsyncTask extends AsyncTask<Void,Void,List<String>> {
        @Override
        protected void onPostExecute(List<String> list) {


                if (PreferncesUtils.getBoolean(MainActivity.this, Constants.PREF_KEY_AUTO_IMG, false)&&!list.isEmpty()) {

                    Glide.with(MainActivity.this)
                            .load(list.get(1))
                            .into(nav_header_img);
                    nav_header_title.setText("每日一图：" + list.get(0));
                } else {
                    setDefaultNav();
                }

        }

        @Override
        protected List<String> doInBackground(Void... voids) {
            if (isCancelled()) {
                return null;
            } else {
                GetNavImage getimage = new GetNavImage();
                navList = getimage.getImage();
                return navList;
            }
        }
    }
}
