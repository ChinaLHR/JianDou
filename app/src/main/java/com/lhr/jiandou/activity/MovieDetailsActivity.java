package com.lhr.jiandou.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
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
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.lhr.jiandou.R;
import com.lhr.jiandou.model.bean.MovieDetailsBean;
import com.lhr.jiandou.model.httputils.MovieHttpMethods;
import com.lhr.jiandou.utils.ImageUtils;
import com.lhr.jiandou.utils.ToastUtils;

import java.util.List;

import rx.Subscriber;

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
    private android.widget.LinearLayout activitymdll;
    private android.support.v4.widget.NestedScrollView activitymovienested;
    private android.support.design.widget.FloatingActionButton activitymdfab;
    private android.support.design.widget.CoordinatorLayout activitymdcoorl;
    private android.support.v4.widget.SwipeRefreshLayout activitymdrefresh;
    private TextView activity_md_subject_title;
    private TextView activity_md_subject_aka;
    private TextView activity_md_subject_countries;
    private TextView activity_md_subject_genres;
    private TextView activity_md_subject_year;
    private RatingBar activity_md_ratingbar;
    private TextView activity_md_ratings_count;
    private TextView activity_md_ratingnumber;

    private MovieDetailsBean mSubject;
    private Subscriber<MovieDetailsBean> mSubscriber;
    private static final String KEY_MOVIE_ID = "movie_id";
    private static final String KEY_IMAGE_URL = "image_url";

    private String MovieId;
    private String imageUrl;

    private boolean isCollection = false;
    private float titleDy = Float.MAX_VALUE;
    boolean isOpenSummary = false;

    public static void toActivity(Activity activity, String id, String imageUrl) {
        Intent intent = new Intent(activity, MovieDetailsActivity.class);
        intent.putExtra(KEY_MOVIE_ID, id);
        intent.putExtra(KEY_IMAGE_URL, imageUrl);
        activity.startActivity(intent);
    }

    private void init() {
        this.activitymdrefresh = (SwipeRefreshLayout) findViewById(R.id.activity_md_refresh);
        this.activitymdcoorl = (CoordinatorLayout) findViewById(R.id.activity_md_coorl);
        this.activitymdfab = (FloatingActionButton) findViewById(R.id.activity_md_fab);
        this.activitymovienested = (NestedScrollView) findViewById(R.id.activity_movie_nested);
        this.activitymdll = (LinearLayout) findViewById(R.id.activity_md_ll);
        this.activitymdrvactor = (RecyclerView) findViewById(R.id.activity_md_rv_actor);
        this.activitymdactortitle = (TextView) findViewById(R.id.activity_md_actor_title);
        this.activitymdsummartmore = (TextView) findViewById(R.id.activity_md_summart_more);
        this.activitymdsummary = (TextView) findViewById(R.id.activity_md_summary);
        this.activitymdsummarytitle = (TextView) findViewById(R.id.activity_md_summary_title);
        this.activitymdappbar = (AppBarLayout) findViewById(R.id.activity_md_appbar);
        this.activitymdcolltl = (CollapsingToolbarLayout) findViewById(R.id.activity_md_colltl);
        this.activitymdtoolbar = (Toolbar) findViewById(R.id.activity_md_toolbar);
        this.activitymdiv = (ImageView) findViewById(R.id.activity_md_iv);
        View activity_md_include = findViewById(R.id.activity_md_include);
        activity_md_subject_title = (TextView) activity_md_include.findViewById(R.id.activity_md_subject_title);
        activity_md_subject_aka = (TextView) activity_md_include.findViewById(R.id.activity_md_subject_aka);
        activity_md_subject_countries = (TextView) activity_md_include.findViewById(R.id.activity_md_subject_countries);
        activity_md_subject_genres = (TextView) activity_md_include.findViewById(R.id.activity_md_subject_genres);
        activity_md_subject_year = (TextView) activity_md_include.findViewById(R.id.activity_md_subject_year);
        activity_md_ratingbar = (RatingBar) activity_md_include.findViewById(R.id.activity_md_ratingbar);
        activity_md_ratings_count = (TextView) activity_md_include.findViewById(R.id.activity_md_ratings_count);
        activity_md_ratingnumber = (TextView) activity_md_include.findViewById(R.id.activity_md_ratingnumber);

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moviedetails);
        MovieId = getIntent().getStringExtra(KEY_MOVIE_ID);
        imageUrl = getIntent().getStringExtra(KEY_IMAGE_URL);
        init();
        initView();
        initListener();
        initData();
    }

    /**
     * 初始化View
     */
    private void initView() {
        activitymdrefresh.setColorSchemeResources(R.color.colorPrimary, R.color.colorAccent, R.color.colorPrimaryDark);
        activitymdrefresh.setProgressViewOffset(false, 0, 48);
        activitymdtoolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        activitymdtoolbar.inflateMenu(R.menu.menu_moviedetails_toolbar);

        Glide.with(this)
                .load(imageUrl)
                .asBitmap()
                .error(R.mipmap.movie_error)
                .listener(new RequestListener<String, Bitmap>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<Bitmap> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Bitmap resource, String model, Target<Bitmap> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        int color = ImageUtils.getColor(resource);
                        activitymdcolltl.setBackgroundColor(color);
                        return false;
                    }
                })
                .into(activitymdiv);

    }

    private void initData() {
        activitymdrefresh.setRefreshing(true);
        mSubscriber = new Subscriber<MovieDetailsBean>() {
            @Override
            public void onCompleted() {
                activitymdrefresh.setRefreshing(false);

            }

            @Override
            public void onError(Throwable e) {
                activitymdrefresh.setRefreshing(false);
                ToastUtils.show(MovieDetailsActivity.this, "加载出错");
            }

            @Override
            public void onNext(MovieDetailsBean movieDetailsBean) {
                if (movieDetailsBean != null) {
                    mSubject = movieDetailsBean;
                    updateView();
                } else {
                    ToastUtils.show(MovieDetailsActivity.this, "加载出错");
                }
            }
        };

        MovieHttpMethods.getInstance().getMovieById(mSubscriber, MovieId);
    }

    /**
     * 获取到数据，加载View
     */
    private void updateView() {
        activitymdll.setVisibility(View.VISIBLE);

        if (mSubject.getRating() != null) {
            float rate = (float) (mSubject.getRating().getAverage() / 2);
            activity_md_ratingbar.setRating(rate);
            activity_md_ratingnumber.setText(rate * 2 + "");
            activity_md_ratings_count.setText(mSubject.getRatings_count() + "人评价");
        }

        activity_md_subject_title.setText(mSubject.getTitle());
        if (mSubject.getGenres() != null) {
            List<String> genres = mSubject.getGenres();
            activity_md_subject_genres.append("电影类型：");
            addViewString(genres, activity_md_subject_genres);
        }
        if (mSubject.getCountries() != null) {
            List<String> countries = mSubject.getCountries();
            activity_md_subject_countries.append("上映国家：");
            addViewString(countries, activity_md_subject_countries);

        }
        activity_md_subject_year.setText("上映时间：" + mSubject.getYear());
        if (mSubject.getAka() != null) {
            List<String> aka = mSubject.getAka();
            activity_md_subject_aka.append("原名：");
            addViewString(aka, activity_md_subject_aka);
        }
        if (mSubject.getSummary() != null) {
            activitymdsummarytitle.setText("简介");
            activitymdsummary.setText(mSubject.getSummary());
            activitymdsummartmore.setText("更多");
        }
    }

    private void addViewString(List<String> list, TextView view) {
        for (int i = 0; i < list.size(); i++) {
            if (i == list.size() - 1) {
                view.append(list.get(i));
            } else {
                view.append(list.get(i) + "/");
            }
        }
    }

    private void initListener() {

        activitymdappbar.addOnOffsetChangedListener(this);

        activitymdtoolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        activitymdtoolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {


                if (isCollection) {
                    item.setIcon(R.drawable.collection_false);
                    isCollection = false;

                } else {
                    item.setIcon(R.drawable.collection_true);
                    isCollection = true;
                }

                return true;
            }
        });

        activitymdsummartmore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isOpenSummary) {
                    activitymdsummary.setLines(5);
                    activitymdsummary.setEllipsize(TextUtils.TruncateAt.END);
                    activitymdsummartmore.setText("更多");
                    isOpenSummary = false;
                } else {
                    activitymdsummary.setSingleLine(false);
                    activitymdsummary.setEllipsize(null);
                    activitymdsummartmore.setText("收起");
                    isOpenSummary = true;
                }

            }
        });
    }


    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        //当Appbar完全显示时才启用SwipeRefreshLayout
        activitymdrefresh.setEnabled(verticalOffset == 0);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSubscriber.unsubscribe();
    }
}
