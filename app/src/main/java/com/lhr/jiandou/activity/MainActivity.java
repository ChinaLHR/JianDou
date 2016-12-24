package com.lhr.jiandou.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

import com.lhr.jiandou.R;
import com.lhr.jiandou.fragment.MovieFragment;
import com.lhr.jiandou.fragment.factory.FragmentFactory;
import com.lhr.jiandou.utils.Constants;
import com.lhr.jiandou.utils.SpUtils;
import com.lhr.jiandou.utils.UIUtils;

import java.util.List;

public class MainActivity extends AppCompatActivity implements Toolbar.OnMenuItemClickListener {

    private android.support.design.widget.NavigationView mainnav;
    private android.support.v4.widget.DrawerLayout maindrawerlayout;
    private RelativeLayout main_container;
    private Toolbar main_toolbar;
    private FragmentManager mFragmentManager;
    private String title;
    private Fragment DefaultFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initToolbar();
        setupDrawerContent();
        initFragment();
        String[] stringArray = SpUtils.getStringArray(this, Constants.MOVIEKEY);
        if (stringArray != null && stringArray.length > 1) {
            Constants.MOVIETITLE = SpUtils.getStringArray(this, Constants.MOVIEKEY);
        }

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

    }

    /**
     * 初始化Fragment
     */
    private void initFragment() {
        //根据title创建Fragment
        title = main_toolbar.getTitle().toString();
        if (title == null) {
            title = UIUtils.getString(this, R.string.nav_menu_movie);
        }
        //清空Fragment
//        removeFragment(title);

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
        if (title.equals(UIUtils.getString(this,R.string.nav_menu_movie))&&Constants.CHANGELABEL_MOVIE) {
            Fragment movieFragment = createFragmentByTitle(title);
            transaction.hide(DefaultFragment);
            transaction.replace(R.id.main_container, movieFragment, title).commit();
            DefaultFragment = movieFragment;
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
        Fragment fragment = FragmentFactory.getFragment(title);
        return fragment;
    }

    /**
     * 监听返回按钮
     */
    @Override
    public void onBackPressed() {
        if (!maindrawerlayout.isDrawerOpen(GravityCompat.START)) {
            super.onBackPressed();
        } else {
            maindrawerlayout.closeDrawers();
        }

    }
}
