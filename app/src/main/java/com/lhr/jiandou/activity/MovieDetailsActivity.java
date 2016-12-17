package com.lhr.jiandou.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lhr.jiandou.R;
import com.lhr.jiandou.utils.ToastUtils;

/**
 * Created by ChinaLHR on 2016/12/17.
 * Email:13435500980@163.com
 * <p>
 * 电影详情页
 */

public class MovieDetailsActivity extends AppCompatActivity implements AppBarLayout.OnOffsetChangedListener {
    private android.widget.ImageView activitymdiv;
    private android.support.v7.widget.Toolbar activitymdtoolbar;
    private android.support.design.widget.CollapsingToolbarLayout activitymdcolltl;
    private android.support.design.widget.AppBarLayout activitymdappbar;
    private android.widget.TextView activitymdsummarytitle;
    private android.widget.TextView activitymdsummary;
    private android.widget.TextView activitymdsummartmore;
    private android.widget.TextView activitymdactortitle;
    private android.support.v7.widget.RecyclerView activitymdrvactor;
    private android.widget.TextView activitymdrecommendtitle;
    private android.support.v7.widget.RecyclerView activitymdrvrecommend;
    private android.widget.LinearLayout activitymdll;
    private android.support.v4.widget.NestedScrollView activitymovienested;
    private android.support.design.widget.FloatingActionButton activitymdfab;
    private android.support.design.widget.CoordinatorLayout activitymdcoorl;
    private android.support.v4.widget.SwipeRefreshLayout activitymdrefresh;

    private static final String KEY_MOVIE_ID = "movie_id";
    private static final String KEY_IMAGE_URL = "image_url";

    private String MovieId;

    public static void toActivity(Activity activity, String id, String imageUrl) {
        Intent intent = new Intent(activity, MovieDetailsActivity.class);
        intent.putExtra(KEY_MOVIE_ID, id);
        intent.putExtra(KEY_IMAGE_URL, imageUrl);
        activity.startActivity(intent);
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moviedetails);
        MovieId = getIntent().getStringExtra(KEY_MOVIE_ID);
        ToastUtils.show(this,MovieId);
        initView();
        initListener();

    }

    private void initListener() {
        activitymdappbar.addOnOffsetChangedListener(this);
    }

    private void initView() {
        this.activitymdrefresh = (SwipeRefreshLayout) findViewById(R.id.activity_md_refresh);
        this.activitymdcoorl = (CoordinatorLayout) findViewById(R.id.activity_md_coorl);
        this.activitymdfab = (FloatingActionButton) findViewById(R.id.activity_md_fab);
        this.activitymovienested = (NestedScrollView) findViewById(R.id.activity_movie_nested);
        this.activitymdll = (LinearLayout) findViewById(R.id.activity_md_ll);
        this.activitymdrvrecommend = (RecyclerView) findViewById(R.id.activity_md_rv_recommend);
        this.activitymdrecommendtitle = (TextView) findViewById(R.id.activity_md_recommend_title);
        this.activitymdrvactor = (RecyclerView) findViewById(R.id.activity_md_rv_actor);
        this.activitymdactortitle = (TextView) findViewById(R.id.activity_md_actor_title);
        this.activitymdsummartmore = (TextView) findViewById(R.id.activity_md_summart_more);
        this.activitymdsummary = (TextView) findViewById(R.id.activity_md_summary);
        this.activitymdsummarytitle = (TextView) findViewById(R.id.activity_md_summary_title);
        this.activitymdappbar = (AppBarLayout) findViewById(R.id.activity_md_appbar);
        this.activitymdcolltl = (CollapsingToolbarLayout) findViewById(R.id.activity_md_colltl);
        this.activitymdtoolbar = (Toolbar) findViewById(R.id.activity_md_toolbar);
        this.activitymdiv = (ImageView) findViewById(R.id.activity_md_iv);
    }


    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        //当Appbar完全显示时才启用SwipeRefreshLayout
        activitymdrefresh.setEnabled(verticalOffset == 0);
    }
}
