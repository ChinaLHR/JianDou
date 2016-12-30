package com.lhr.jiandou.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
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
import com.lhr.jiandou.adapter.LikeMovieAdapter;
import com.lhr.jiandou.adapter.base.BaseRecyclerAdapter;
import com.lhr.jiandou.model.bean.BookDetailsBean;
import com.lhr.jiandou.model.httputils.BookHttpMethods;
import com.lhr.jiandou.utils.ImageUtils;
import com.lhr.jiandou.utils.SnackBarUtils;
import com.lhr.jiandou.utils.StringUtils;
import com.lhr.jiandou.utils.UIUtils;
import com.lhr.jiandou.utils.jsoupUtils.GetBookInfo;

import java.util.List;

import rx.Subscriber;

/**
 * Created by ChinaLHR on 2016/12/25.
 * Email:13435500980@163.com
 */

public class BookDetailsActivity extends AppCompatActivity implements AppBarLayout.OnOffsetChangedListener, View.OnClickListener {

    private android.widget.ImageView atvbookiv;
    private android.support.v7.widget.Toolbar atvbooktoolbar;
    private android.support.design.widget.CollapsingToolbarLayout atvbookcolltl;
    private android.support.design.widget.AppBarLayout atvbookappbar;
    private View atvbookinclude;
    private android.widget.TextView atvbooksummarytitle;
    private android.widget.TextView atvbooksummary;
    private android.widget.TextView atvbooksummarymore;
    private android.widget.TextView atvbookauthorintrotitle;
    private android.widget.TextView atvbookauthorintro;
    private android.widget.TextView atvbookauthorlisttitle;
    private android.widget.TextView atvbooklist;
    private android.widget.TextView atvbookliketitle;
    private android.support.v7.widget.RecyclerView atvbookrvlike;
    private android.widget.LinearLayout atvbookll;
    private android.support.v4.widget.NestedScrollView atvbooknested;
    private android.support.design.widget.FloatingActionButton atvbookfab;
    private android.support.design.widget.CoordinatorLayout atvbookcoorl;
    private android.support.v4.widget.SwipeRefreshLayout atvbookrefresh;
    private LinearLayout atv_book_list_ll;
    private LinearLayout atv_book_author_ll;
    private TextView atv_book_title;
    private TextView atv_book_author;
    private TextView atv_book_subtitle;
    private TextView atv_book_pages;
    private TextView atv_book_ratingnumber;
    private RatingBar atv_book_ratingbar;
    private TextView atv_book_ratings_count;
    private ImageView atv_book_iv_author;
    private ImageView atv_book_iv_list;
    private TextView atv_book_pub;


    private static final String KEY_BOOK_ID = "movie_id";
    private static final String KEY_IMAGE_URL = "image_url";
    private static final String TYPE_AUTHOR = "作者简介";
    private static final String TYPE_LIST = "目录";

    private boolean isOpenSummary = false;
    private boolean isCollection = false;
    private String BookId;
    private String imageUrl;
    private BookDetailsBean mBookBean;
    private Subscriber<BookDetailsBean> mSubscriber;
    private AsyncTask mAsyncTask;
    private List<String> booklist;
    private List<String> ShortComments;
    private List<String> LikeBookTitle;
    private List<String> LikeBookImage;
    private List<String> LikeBookId;
    private LikeMovieAdapter mLikeAdapter;
    private TextView btndialog_title;
    private TextView btndialog_cate;
    private ImageView btndialog_close;
    private TextView btdialog_tv;
    private BottomSheetBehavior behavior;

    public static void toActivity(Activity activity, String id, String img) {
        Intent intent = new Intent(activity, BookDetailsActivity.class);
        intent.putExtra(KEY_BOOK_ID, id);
        intent.putExtra(KEY_IMAGE_URL, img);
        activity.startActivity(intent);
    }

    private void init() {
        this.atvbookrefresh = (SwipeRefreshLayout) findViewById(R.id.atv_book_refresh);
        this.atvbookcoorl = (CoordinatorLayout) findViewById(R.id.atv_book_coorl);
        this.atvbookfab = (FloatingActionButton) findViewById(R.id.atv_book_fab);
        this.atvbooknested = (NestedScrollView) findViewById(R.id.atv_book_nested);
        this.atvbookll = (LinearLayout) findViewById(R.id.atv_book_ll);
        this.atvbookrvlike = (RecyclerView) findViewById(R.id.atv_book_rv_like);
        this.atvbookliketitle = (TextView) findViewById(R.id.atv_book_like_title);
        this.atvbooklist = (TextView) findViewById(R.id.atv_book_list);
        this.atvbookauthorlisttitle = (TextView) findViewById(R.id.atv_book_author_list_title);
        this.atvbookauthorintro = (TextView) findViewById(R.id.atv_book_author_intro);
        this.atvbookauthorintrotitle = (TextView) findViewById(R.id.atv_book_author_intro_title);
        this.atvbooksummarymore = (TextView) findViewById(R.id.atv_book_summary_more);
        this.atvbooksummary = (TextView) findViewById(R.id.atv_book_summary);
        this.atvbooksummarytitle = (TextView) findViewById(R.id.atv_book_summary_title);
        this.atvbookinclude = findViewById(R.id.atv_book_include);
        this.atvbookappbar = (AppBarLayout) findViewById(R.id.atv_book_appbar);
        this.atvbookcolltl = (CollapsingToolbarLayout) findViewById(R.id.atv_book_colltl);
        this.atvbooktoolbar = (Toolbar) findViewById(R.id.atv_book_toolbar);
        this.atvbookiv = (ImageView) findViewById(R.id.atv_book_iv);
        atv_book_author_ll = (LinearLayout) findViewById(R.id.atv_book_author_ll);
        atv_book_list_ll = (LinearLayout) findViewById(R.id.atv_book_list_ll);
        atv_book_title = (TextView) atvbookinclude.findViewById(R.id.atv_book_title);
        atv_book_author = (TextView) atvbookinclude.findViewById(R.id.atv_book_author);
        atv_book_pub = (TextView) atvbookinclude.findViewById(R.id.atv_book_pub);
        atv_book_subtitle = (TextView) atvbookinclude.findViewById(R.id.atv_book_subtitle);
        atv_book_pages = (TextView) atvbookinclude.findViewById(R.id.atv_book_pages);
        atv_book_ratingnumber = (TextView) atvbookinclude.findViewById(R.id.atv_book_ratingnumber);
        atv_book_ratingbar = (RatingBar) atvbookinclude.findViewById(R.id.atv_book_ratingbar);
        atv_book_ratings_count = (TextView) atvbookinclude.findViewById(R.id.atv_book_ratings_count);
        atv_book_iv_author = (ImageView) findViewById(R.id.atv_book_iv_author);
        atv_book_iv_list = (ImageView) findViewById(R.id.atv_book_iv_list);
        atvbookrefresh.setColorSchemeResources(R.color.colorPrimary, R.color.colorAccent, R.color.colorPrimaryDark);
        atvbookrefresh.setProgressViewOffset(false, 0, 48);
        atvbooktoolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        atvbooktoolbar.inflateMenu(R.menu.menu_moviedetails_toolbar);

        btndialog_title = (TextView) findViewById(R.id.btndialog_title);
        btndialog_cate = (TextView) findViewById(R.id.btndialog_cate);
        btndialog_close = (ImageView) findViewById(R.id.btndialog_close);
        btdialog_tv = (TextView) findViewById(R.id.btdialog_tv);

        View bottomSheet = findViewById(R.id.btndialog_nes);
        behavior = BottomSheetBehavior.from(bottomSheet);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookdetails);
        BookId = getIntent().getStringExtra(KEY_BOOK_ID);
        imageUrl = getIntent().getStringExtra(KEY_IMAGE_URL);
        init();
        initView();
        initData();
        initListener();
    }

    private void initData() {
        atvbookrefresh.setRefreshing(true);

        mSubscriber = new Subscriber<BookDetailsBean>() {
            @Override
            public void onCompleted() {
                atvbookrefresh.setRefreshing(false);
            }

            @Override
            public void onError(Throwable e) {
                atvbookrefresh.setRefreshing(false);
                SnackBarUtils.showSnackBar(atvbookcoorl, UIUtils.getString(BookDetailsActivity.this, R.string.error));
            }

            @Override
            public void onNext(BookDetailsBean bookDetailsBean) {
                if (bookDetailsBean != null) {
                    mBookBean = bookDetailsBean;
                    updateView();
                    mAsyncTask = new AsyncTask();
                    mAsyncTask.execute();

                } else {
                    SnackBarUtils.showSnackBar(atvbookcoorl, UIUtils.getString(BookDetailsActivity.this, R.string.error));
                }

            }
        };
        BookHttpMethods.getInstance().getBookById(mSubscriber, BookId);
    }

    /**
     * 加载View
     */
    private void updateView() {
        atvbookll.setVisibility(View.VISIBLE);
        if (mBookBean.getRating() != null) {
            float rate = Float.parseFloat(mBookBean.getRating().getAverage()) / 2;
            atv_book_ratingbar.setRating(rate);
            atv_book_ratingnumber.setText(rate * 2 + "");
            atv_book_ratings_count.setText(mBookBean.getRating().getNumRaters() + UIUtils.getString(this, R.string.md_movie_evaluation));
        }

        atv_book_title.setText(mBookBean.getTitle());
        atv_book_author.setText(UIUtils.getString(this, R.string.book_author));
        StringUtils.addViewString(mBookBean.getAuthor(), atv_book_author);
        atv_book_pub.setText(UIUtils.getString(this, R.string.book_press) + mBookBean.getPublisher() + "/" + mBookBean.getPubdate());

        if (!mBookBean.getSubtitle().trim().equals("")) {
            atv_book_subtitle.setText(UIUtils.getString(this, R.string.book_subtitle) + mBookBean.getSubtitle());
        } else {
            atv_book_subtitle.setVisibility(View.GONE);
        }

        if (!mBookBean.getPages().trim().equals("")) {
            atv_book_pages.setText(UIUtils.getString(this, R.string.book_pages) + mBookBean.getPages());
        } else {
            atv_book_pages.setVisibility(View.GONE);
        }

        if (!mBookBean.getSummary().trim().equals("")) {
            atvbooksummarytitle.setText(UIUtils.getString(this, R.string.md_movie_brief));
            atvbooksummary.setText(mBookBean.getSummary());
            atvbooksummarymore.setText(UIUtils.getString(this, R.string.md_more));
        } else {
            atvbooksummarytitle.setText(UIUtils.getString(this, R.string.ad_nomore));
            atvbooksummary.setVisibility(View.GONE);
            atvbooksummarymore.setVisibility(View.GONE);
        }

        if (!mBookBean.getAuthor_intro().trim().equals("")) {
            atvbookauthorintrotitle.setText(UIUtils.getString(this, R.string.book_actor_info));
            atvbookauthorintro.setText(mBookBean.getAuthor_intro());
        } else {
            atv_book_author_ll.setVisibility(View.GONE);
        }
    }

    private void initView() {
        Glide.with(this)
                .load(imageUrl)
                .asBitmap()
                .listener(new RequestListener<String, Bitmap>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<Bitmap> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Bitmap resource, String model, Target<Bitmap> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        int color = ImageUtils.getColor(resource);
                        atvbookcolltl.setBackgroundColor(color);
                        atvbookcolltl.setContentScrimColor(color);
                        return false;
                    }
                })
                .into(atvbookiv);


    }

    private void initListener() {
        atvbooktoolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        atvbooktoolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
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

        atvbooksummarymore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isOpenSummary) {
                    atvbooksummary.setLines(4);
                    atvbooksummary.setEllipsize(TextUtils.TruncateAt.END);
                    atvbooksummarymore.setText(UIUtils.getString(BookDetailsActivity.this, R.string.md_more));
                    isOpenSummary = false;
                } else {
                    atvbooksummary.setSingleLine(false);
                    atvbooksummary.setEllipsize(null);
                    atvbooksummarymore.setText(UIUtils.getString(BookDetailsActivity.this, R.string.md_put));
                    isOpenSummary = true;
                }

            }
        });
        atvbookappbar.addOnOffsetChangedListener(this);

        atvbookrefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initData();
                initView();
            }
        });

        atv_book_iv_author.setOnClickListener(this);
        atv_book_iv_list.setOnClickListener(this);

    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        //当Appbar完全显示时才启用SwipeRefreshLayout
        atvbookrefresh.setEnabled(verticalOffset == 0);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.atv_book_iv_author:
                showbuttondialog(TYPE_AUTHOR, mBookBean.getAuthor_intro());
                break;
            case R.id.atv_book_iv_list:
                showbuttondialog(TYPE_LIST, mBookBean.getAuthor_intro());
                break;
        }
    }

    /**
     * 显示ButtonDialog
     */
    private void showbuttondialog(String TYPE, String author_intro) {
        atvbookappbar.setExpanded(false);
        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        if (TYPE.equals(TYPE_AUTHOR)) {
            btndialog_cate.setText(TYPE_AUTHOR);
            btndialog_title.setText("");
            StringUtils.addViewString(mBookBean.getAuthor(), btndialog_title);
            btdialog_tv.setText(author_intro);
        } else {
            btndialog_cate.setText(TYPE_LIST);
            btndialog_title.setText("");
            btdialog_tv.setText("");
            for (int i = 0; i < booklist.size(); i++) {
                if (!booklist.get(i).trim().equals("")) {
                    btdialog_tv.append(booklist.get(i));
                }
            }

        }


        btndialog_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (behavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
                    behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
            }
        });


    }


    class AsyncTask extends android.os.AsyncTask {

        @Override
        protected Object doInBackground(Object[] objects) {
            if (isCancelled()) {
                return null;
            } else {
                GetBookInfo getBookInfo = new GetBookInfo(BookId);
                booklist = getBookInfo.getBookList();//章节
                ShortComments = getBookInfo.getShortComments();//短评
                LikeBookTitle = getBookInfo.getLikeBookTitle();//推荐图书title
                LikeBookId = getBookInfo.getLikeBookId();//推荐图书id
                LikeBookImage = getBookInfo.getLikeBookImage();//推荐图书image
                return null;
            }
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            //初始化试读目录
            if (booklist != null) {
                atv_book_list_ll.setVisibility(View.VISIBLE);
                atvbookauthorlisttitle.setText(UIUtils.getString(BookDetailsActivity.this, R.string.book_list));
                for (int i = 0; i < booklist.size(); i++) {
                    if (!booklist.get(i).trim().equals("")) {
                        atvbooklist.append(booklist.get(i));
                    }
                }
            }

            //初始化RecyclerView
            if (LikeBookTitle != null && LikeBookId != null && LikeBookImage != null) {
                atvbookliketitle.setText(UIUtils.getString(BookDetailsActivity.this, R.string.book_like));
                atvbookrvlike.setVisibility(View.VISIBLE);
                atvbookrvlike.setLayoutManager(new LinearLayoutManager(BookDetailsActivity.this,
                        LinearLayoutManager.HORIZONTAL, false));
                mLikeAdapter = new LikeMovieAdapter(BookDetailsActivity.this, LikeBookTitle, LikeBookImage, LikeBookId);
                atvbookrvlike.setAdapter(mLikeAdapter);

                mLikeAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(String id, String url) {
                        if (id != null && url != null) {
                            BookDetailsActivity.toActivity(BookDetailsActivity.this, id, url);
                        } else {
                            SnackBarUtils.showSnackBar(atvbookcoorl, UIUtils.getString(BookDetailsActivity.this, R.string.error));
                        }
                    }
                });
            } else {
                atvbookliketitle.setText(UIUtils.getString(BookDetailsActivity.this, R.string.error));
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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (behavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
                behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                return true;
            } else {
                return super.onKeyDown(keyCode, event);
            }
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }
}
