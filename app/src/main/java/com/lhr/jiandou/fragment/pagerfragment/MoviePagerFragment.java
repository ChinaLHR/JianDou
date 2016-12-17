package com.lhr.jiandou.fragment.pagerfragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.lhr.jiandou.MyApplication;
import com.lhr.jiandou.R;
import com.lhr.jiandou.activity.MovieDetailsActivity;
import com.lhr.jiandou.adapter.PageMovieAdapter;
import com.lhr.jiandou.model.bean.SubjectsBean;
import com.lhr.jiandou.model.httputils.MovieHttpMethods;
import com.lhr.jiandou.utils.Constants;
import com.lhr.jiandou.utils.LogUtils;
import com.lhr.jiandou.utils.ToastUtils;
import com.lhr.jiandou.view.mProgressdialog;

import java.util.List;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by ChinaLHR on 2016/12/13.
 * Email:13435500980@163.com
 */

public class MoviePagerFragment extends Fragment {
    private static final int RECORD_COUNT = 18;

    private int position;
    private Subscriber<Integer> mSubscriber;
    private Subscriber<List<SubjectsBean>> mListSubscriber;
    private boolean isFirstRefresh = true;
    private boolean refreshing = false;
    private int mStart = 0;
    private View footer;
    boolean isonCreate;
    private mProgressdialog mprogressdialog = new mProgressdialog();

    private android.support.v7.widget.RecyclerView pagermovierv;
    private android.support.v4.widget.SwipeRefreshLayout pagermoviefresh;
    private android.support.design.widget.FloatingActionButton pagermoviefab;
    private LinearLayout pager_movie_btn;
    private PageMovieAdapter mAdapter;

    public MoviePagerFragment(Observable<Integer> observable) {
        mSubscriber = new Subscriber<Integer>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Integer integer) {
                position = integer;
            }
        };
        observable.subscribe(mSubscriber);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mprogressdialog.isFirst = true;
        isonCreate = true;
        LogUtils.e("OnCreate");
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.pagerfragment_movie, container, false);
        this.pagermoviefab = (FloatingActionButton) view.findViewById(R.id.pager_movie_fab);
        this.pagermoviefresh = (SwipeRefreshLayout) view.findViewById(R.id.pager_movie_fresh);
        this.pagermovierv = (RecyclerView) view.findViewById(R.id.pager_movie_rv);
        this.pager_movie_btn = (LinearLayout) view.findViewById(R.id.pager_movie_btn);
        initData();
        initListener();
        if (!MyApplication.isNetworkAvailable(getActivity())){
            pager_movie_btn.setVisibility(View.VISIBLE);
        }
        return view;

    }


    /**
     * 初始化监听处理
     */
    private void initListener() {
        /**
         * 顶部下拉松开时会调用这个方法
         */
        pagermoviefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (!refreshing) {
                    isFirstRefresh = true;
                    mStart = 0;
                    loadMovieData();
                } else {
                    pagermoviefresh.setRefreshing(false);
                }
            }
        });

        pagermoviefab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pagermovierv.getAdapter() != null) {
                    pagermovierv.scrollToPosition(0);
                }
            }
        });
        /**
         * RecyclerView滑动监听
         */
        pagermovierv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!recyclerView.canScrollVertically(1)) {
                    loadMoreMovieData();
                    pagermovierv.scrollToPosition(mAdapter.getItemCount() - 1);

                }
            }
        });

        pager_movie_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initData();
                if (MyApplication.isNetworkAvailable(getActivity())){
                    pager_movie_btn.setVisibility(View.GONE);
                }
            }
        });
    }

    /**
     * 加载更多数据
     */
    private void loadMoreMovieData() {
        footer.setVisibility(View.VISIBLE);
        if (!refreshing) {
            loadMovieData();
        } else {
            footer.setVisibility(View.GONE);
        }
    }

    /**
     * 初始化View
     */
    private void initData() {
        refreshing = false;
        pagermoviefresh.setColorSchemeResources(R.color.colorPrimary, R.color.colorAccent, R.color.colorPrimaryDark);
        loadMovieData();

    }

    /**
     * 初始化RecyclerView
     *
     * @param subjectsBeen
     */
    private void initRecyclerView(final List<SubjectsBean> subjectsBeen) {


        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(), 3);
        pagermovierv.setLayoutManager(mLayoutManager);
        mAdapter = new PageMovieAdapter(getContext(), subjectsBeen);
        footer = LayoutInflater.from(this.getActivity()).inflate(R.layout.item_footer, pagermovierv, false);
        mAdapter.setFooterView(footer);
        mAdapter.setOnClickListener(new PageMovieAdapter.OnItemClickListener() {
            @Override
            public void ItemClickListener(View view, int postion) {
//                ToastUtils.show(getActivity(), subjectsBeen.get(postion).getTitle() + "单击");
                MovieDetailsActivity.toActivity(getActivity(), subjectsBeen.get(postion).getId(), subjectsBeen.get(postion).getImages().getLarge());

            }

            @Override
            public void ItemLongClickListener(View view, int postion) {
                ToastUtils.show(getActivity(), subjectsBeen.get(postion).getTitle() + "长按");

            }
        });
        pagermovierv.setAdapter(mAdapter);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mSubscriber.unsubscribe();
        mListSubscriber.unsubscribe();
    }

    /**
     * 网络请求,获取数据
     */
    private void loadMovieData() {

        //如果是第一次开启，启动ProgressDialog

        if (isonCreate) {
            mprogressdialog.showProgressdialog(getActivity());
        }

        //刷新中判定，防止下拉刷新与上拉刷新冲突
        refreshing = true;
        mListSubscriber = new Subscriber<List<SubjectsBean>>() {
            @Override
            public void onCompleted() {
                refreshing = false;

                mprogressdialog.cancelProgressdialog();
            }

            @Override
            public void onError(Throwable e) {
                if (MyApplication.isNetworkAvailable(getActivity())) {
                    ToastUtils.show(getActivity(), e.getMessage());
                    ToastUtils.isShow = false;
                }
                footer.setVisibility(View.GONE);
                pagermoviefresh.setRefreshing(false);
                refreshing = false;
                mprogressdialog.cancelProgressdialog();

            }

            @Override
            public void onNext(List<SubjectsBean> subjectsBeen) {

                if (isFirstRefresh) {
                    initRecyclerView(subjectsBeen);
                    isFirstRefresh = false;
                    mStart += RECORD_COUNT;
                    pagermoviefresh.setRefreshing(false);
                } else {
                    mAdapter.addDatas(subjectsBeen);
                    mStart += RECORD_COUNT;

                }
            }
        };

        MovieHttpMethods.getInstance().getMovieByTag(mListSubscriber, Constants.MOVIETITLE[position], mStart, RECORD_COUNT);

    }


}
