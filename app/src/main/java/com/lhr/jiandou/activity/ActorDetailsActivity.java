package com.lhr.jiandou.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lhr.jiandou.MyApplication;
import com.lhr.jiandou.R;
import com.lhr.jiandou.adapter.ActorMovieAdapter;
import com.lhr.jiandou.adapter.base.BaseRecyclerAdapter;
import com.lhr.jiandou.model.bean.ActorDetailsBean;
import com.lhr.jiandou.model.db.Actor_db;
import com.lhr.jiandou.model.db.GreenDaoUtils;
import com.lhr.jiandou.model.httputils.MovieHttpMethods;
import com.lhr.jiandou.utils.SnackBarUtils;
import com.lhr.jiandou.utils.UIUtils;
import com.lhr.jiandou.utils.jsoupUtils.GetActor;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import rx.Subscriber;

/**
 * Created by ChinaLHR on 2016/12/20.
 * Email:13435500980@163.com
 */

public class ActorDetailsActivity extends AppCompatActivity {
    private static final String KEY_ACTOR_ID = "actor_id";
    private static final String KEY_ACTOR_IMG = "actor_img";
    private String actorId;
    private String imageUrl;
    private boolean isCollection = false;
    private boolean isOpenSummary = false;
    private boolean lockCollection = false;
    private List<String> actorImage;
    private String actorSummary;
    private ImageView[] ivarray;
    private AsyncTask mAsyncTask;
    private Subscriber mSubscriber;
    private ActorDetailsBean mActorBean;
    private ActorMovieAdapter mAdapter;
    private android.widget.ImageView atvactoriv1;
    private android.widget.ImageView atvactoriv2;
    private android.widget.ImageView atvactoriv3;
    private android.widget.ImageView atvactoriv4;
    private android.widget.ImageView atvactoriv5;
    private android.widget.ImageView atvactoriv6;
    private android.support.v7.widget.Toolbar atvactortoolbar;
    private android.support.design.widget.CollapsingToolbarLayout actactorcoll;
    private android.support.design.widget.AppBarLayout atvactorappbar;
    private android.widget.TextView atvactorname;
    private android.widget.TextView atvactornameen;
    private android.widget.TextView atvactorsex;
    private android.widget.TextView atvactorcounty;
    private android.widget.TextView atvactorbrief;
    private android.widget.TextView atvactorsummary;
    private android.widget.TextView atvactorsummarymore;
    private android.widget.TextView atvactormovie;
    private android.support.v7.widget.RecyclerView atvactorrv;
    private android.widget.LinearLayout atvactorll;
    private android.support.v4.widget.NestedScrollView atvactornest;
    private android.support.design.widget.FloatingActionButton atvactorfab;
    private android.support.design.widget.CoordinatorLayout atvactorcoor;
    private GreenDaoUtils mDaoUtils;

    public static void toActivity(Context context, String id, String img) {
        Intent intent = new Intent(context, ActorDetailsActivity.class);
        intent.putExtra(KEY_ACTOR_ID, id);
        intent.putExtra(KEY_ACTOR_IMG, img);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actordetials);
        actorId = getIntent().getStringExtra(KEY_ACTOR_ID);
        imageUrl = getIntent().getStringExtra(KEY_ACTOR_IMG);
        mDaoUtils = MyApplication.getDbUtils();
        init();
        initView();
        initData();
        initListener();
    }

    /**
     * 初始化监听
     */
    private void initListener() {
        atvactortoolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        atvactortoolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (lockCollection) {
                    if (isCollection) {
                        item.setIcon(R.drawable.collection_false);
                        isCollection = false;
                        boolean b = deleteCollection();
                        if (b) {
                            SnackBarUtils.showSnackBar(atvactorcoor, UIUtils.getString(ActorDetailsActivity.this,R.string.collection_cancel));
                        } else {
                            SnackBarUtils.showSnackBar(atvactorcoor,UIUtils.getString(ActorDetailsActivity.this,R.string.collection_cancel_fail));
                        }


                    } else {
                        item.setIcon(R.drawable.collection_true);
                        isCollection = true;
                        boolean b = collectionMovie();
                        if (b) {
                            SnackBarUtils.showSnackBar(atvactorcoor, UIUtils.getString(ActorDetailsActivity.this,R.string.collection_success));
                        } else {
                            SnackBarUtils.showSnackBar(atvactorcoor, UIUtils.getString(ActorDetailsActivity.this,R.string.collection_fail));
                        }
                    }
                    return true;
                } else {
                    return false;
                }
            }
        });

        atvactorsummarymore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isOpenSummary) {
                    atvactorsummary.setLines(5);
                    atvactorsummary.setEllipsize(TextUtils.TruncateAt.END);
                    atvactorsummarymore.setText(UIUtils.getString(ActorDetailsActivity.this, R.string.md_more));
                    isOpenSummary = false;
                } else {
                    atvactorsummary.setSingleLine(false);
                    atvactorsummary.setEllipsize(null);
                    atvactorsummarymore.setText(UIUtils.getString(ActorDetailsActivity.this, R.string.md_put));
                    isOpenSummary = true;
                }

            }
        });
        atvactorfab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WebViewActivity webViewActivity = new WebViewActivity();
                webViewActivity.toWebActivity(ActorDetailsActivity.this, mActorBean.getMobile_url(), mActorBean.getName());
            }
        });

    }

    /**
     * 取消收藏
     *
     * @return
     */
    private boolean deleteCollection() {
        boolean isdelete = mDaoUtils.deletaActor(actorId);
        return isdelete;
    }

    /**
     * 收藏
     *
     * @return
     */
    private boolean collectionMovie() {
        Actor_db actor = new Actor_db();
        actor.setImgurl(imageUrl);
        actor.setActor_id(actorId);
        actor.setTitle(mActorBean.getName());
        actor.setBorn_place(mActorBean.getBorn_place());
        actor.setGender(mActorBean.getGender());
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String time = dateFormat.format(now);
        actor.setTime(time);

        boolean isinsert = mDaoUtils.insertActor(actor);
        return isinsert;
    }

    /**
     * 初始化数据
     */
    private void initData() {
        mSubscriber = new Subscriber<ActorDetailsBean>() {

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                SnackBarUtils.showSnackBar(atvactorcoor, UIUtils.getString(ActorDetailsActivity.this, R.string.error));
                lockCollection = false;
            }

            @Override
            public void onNext(ActorDetailsBean actorDetailsBean) {
                if (actorDetailsBean != null) {
                    mActorBean = actorDetailsBean;
                    lockCollection = true;
                    updateView();
                } else {
                    SnackBarUtils.showSnackBar(atvactorcoor, UIUtils.getString(ActorDetailsActivity.this, R.string.error));
                    lockCollection = false;
                }
            }
        };
        MovieHttpMethods.getInstance().getActorById(mSubscriber, actorId);
    }

    /**
     * 获取到数据，加载View
     */
    private void updateView() {
        atvactorll.setVisibility(View.VISIBLE);
        atvactorname.setText(mActorBean.getName());
        if (mActorBean.getName_en() != null) {
            atvactornameen.setText(mActorBean.getName_en());
        }
        atvactorsex.setText(UIUtils.getString(this, R.string.ad_sex) + mActorBean.getGender());
        if (mActorBean.getBorn_place() != null) {
            atvactorcounty.setText(UIUtils.getString(this, R.string.ad_country) + mActorBean.getBorn_place());
        }
        atvactorbrief.setText(UIUtils.getString(this, R.string.ad_brief));
        atvactormovie.setText(UIUtils.getString(this, R.string.ad_works));

        atvactorrv.setVisibility(View.VISIBLE);
        atvactorrv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mAdapter = new ActorMovieAdapter(this, mActorBean.getWorks());
        atvactorrv.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(String id, String url) {
                MovieDetailsActivity.toActivity(ActorDetailsActivity.this, id, url);
            }
        });
    }

    /**
     * 初始化View
     */
    private void initView() {
        actactorcoll.setContentScrimColor(getResources().getColor(R.color.actorbg));
        Glide.with(this)
                .load(imageUrl)
                .into(atvactoriv1);
        if (MyApplication.isNetworkAvailable(this)) {
            mAsyncTask = new AsyncTask();
            mAsyncTask.execute();
        }

    }

    private void init() {
        this.atvactorcoor = (CoordinatorLayout) findViewById(R.id.atv_actor_coor);
        this.atvactorfab = (FloatingActionButton) findViewById(R.id.atv_actor_fab);
        this.atvactornest = (NestedScrollView) findViewById(R.id.atv_actor_nest);
        this.atvactorll = (LinearLayout) findViewById(R.id.atv_actor_ll);
        this.atvactorrv = (RecyclerView) findViewById(R.id.atv_actor_rv);
        this.atvactormovie = (TextView) findViewById(R.id.atv_actor_movie);
        this.atvactorsummarymore = (TextView) findViewById(R.id.atv_actor_summary_more);
        this.atvactorsummary = (TextView) findViewById(R.id.atv_actor_summary);
        this.atvactorbrief = (TextView) findViewById(R.id.atv_actor_brief);
        this.atvactorcounty = (TextView) findViewById(R.id.atv_actor_county);
        this.atvactorsex = (TextView) findViewById(R.id.atv_actor_sex);
        this.atvactornameen = (TextView) findViewById(R.id.atv_actor_name_en);
        this.atvactorname = (TextView) findViewById(R.id.atv_actor_name);
        this.atvactorappbar = (AppBarLayout) findViewById(R.id.atv_actor_appbar);
        this.actactorcoll = (CollapsingToolbarLayout) findViewById(R.id.act_actor_coll);
        this.atvactortoolbar = (Toolbar) findViewById(R.id.atv_actor_toolbar);
        this.atvactoriv6 = (ImageView) findViewById(R.id.atv_actor_iv6);
        this.atvactoriv5 = (ImageView) findViewById(R.id.atv_actor_iv5);
        this.atvactoriv4 = (ImageView) findViewById(R.id.atv_actor_iv4);
        this.atvactoriv3 = (ImageView) findViewById(R.id.atv_actor_iv3);
        this.atvactoriv2 = (ImageView) findViewById(R.id.atv_actor_iv2);
        this.atvactoriv1 = (ImageView) findViewById(R.id.atv_actor_iv1);

        ivarray = new ImageView[]{atvactoriv2, atvactoriv3, atvactoriv4, atvactoriv5, atvactoriv6};
        atvactortoolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        atvactortoolbar.inflateMenu(R.menu.menu_moviedetails_toolbar);
        //初始化Menu
        Menu menu = atvactortoolbar.getMenu();
        if (mDaoUtils.queryActor(actorId)) {
            menu.getItem(0).setIcon(R.drawable.collection_true);
            isCollection = true;
        } else {
            menu.getItem(0).setIcon(R.drawable.collection_false);
            isCollection = false;
        }
    }


    class AsyncTask extends android.os.AsyncTask {

        @Override
        protected Object doInBackground(Object[] objects) {
            if (isCancelled()) {
                return null;
            } else {
                GetActor mGetActor = new GetActor(actorId);
                actorImage = mGetActor.getActorImage();
                actorSummary = mGetActor.getActorSummary();
                return null;
            }
        }

        @Override
        protected void onPostExecute(Object o) {
            if (actorImage != null) {
                for (int i = 0; i < actorImage.size(); i++) {
                    Glide.with(ActorDetailsActivity.this)
                            .load(actorImage.get(i))
                            .into(ivarray[i]);
                }
            }
            if (actorSummary.trim() == null || actorSummary.length() < 10) {
                atvactorsummary.setText(UIUtils.getString(ActorDetailsActivity.this, R.string.ad_nomore));
                atvactorsummary.setLines(1);
                atvactorsummarymore.setVisibility(View.GONE);
            } else {
                atvactorsummary.setText(actorSummary);
                atvactorsummary.setLines(5);
                atvactorsummarymore.setText(UIUtils.getString(ActorDetailsActivity.this, R.string.md_more));
            }
        }
    }


    @Override
    protected void onPause() {

        if (mAsyncTask != null && mAsyncTask.getStatus() == android.os.AsyncTask.Status.RUNNING) {
            mAsyncTask.cancel(true);
        }
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSubscriber.unsubscribe();
    }
}
