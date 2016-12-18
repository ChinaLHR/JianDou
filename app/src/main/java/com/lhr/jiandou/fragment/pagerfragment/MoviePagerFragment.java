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

import com.lhr.jiandou.MyApplication;
import com.lhr.jiandou.R;
import com.lhr.jiandou.adapter.PageMovieAdapter;
import com.lhr.jiandou.model.bean.SubjectsBean;
import com.lhr.jiandou.model.httputils.MovieHttpMethods;
import com.lhr.jiandou.utils.CacheUtils;
import com.lhr.jiandou.utils.Constants;
import com.lhr.jiandou.utils.ToastUtils;

import java.util.ArrayList;
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
    private int mStart = 0;
    private View footer;
    private List<SubjectsBean> mdate;
    private boolean isFirstonResume = true;

    private android.support.v7.widget.RecyclerView pagermovierv;
    private android.support.v4.widget.SwipeRefreshLayout pagermoviefresh;
    private android.support.design.widget.FloatingActionButton pagermoviefab;
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

    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.pagerfragment_movie, container, false);
        this.pagermoviefab = (FloatingActionButton) view.findViewById(R.id.pager_movie_fab);
        this.pagermoviefresh = (SwipeRefreshLayout) view.findViewById(R.id.pager_movie_fresh);
        this.pagermovierv = (RecyclerView) view.findViewById(R.id.pager_movie_rv);
        initData();
        initListener();
        if (MyApplication.isNetworkAvailable(getActivity())) {
            loadMovieData();
        }
        return view;

    }


    /**
     * 初始化View
     */
    private void initData() {
        pagermoviefresh.setColorSchemeResources(R.color.colorPrimary, R.color.colorAccent, R.color.colorPrimaryDark);
        initRecyclerView();

    }


    /**
     * 初始化监听处理
     */
    private void initListener() {
        /**
         * RecyclerView单击与长按监听
         */
        mAdapter.setOnClickListener(new PageMovieAdapter.OnItemClickListener() {
            @Override
            public void ItemClickListener(View view, int postion, String id) {
                ToastUtils.show(getActivity(), id);
            }

            @Override
            public void ItemLongClickListener(View view, int postion) {
            }
        });
        /**
         * 顶部下拉松开时会调用这个方法
         */
        pagermoviefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadMovieData();
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
                if (!recyclerView.canScrollVertically(1) && MyApplication.isNetworkAvailable(getActivity())) {
                    updateMovieData();
                    footer.setVisibility(View.VISIBLE);
                    pagermovierv.scrollToPosition(mAdapter.getItemCount() - 1);

                }
            }
        });
    }


    /**
     * 初始化RecyclerView
     */
    private void initRecyclerView() {


        List<SubjectsBean> mSubjectbean = new ArrayList<>();
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(), 3);
        pagermovierv.setLayoutManager(mLayoutManager);
        mAdapter = new PageMovieAdapter(getContext(), mSubjectbean);
        footer = LayoutInflater.from(this.getActivity()).inflate(R.layout.item_footer, pagermovierv, false);
        mdate = CacheUtils.readbean(getActivity(), Constants.MOVIETITLE[position]);
        if (mdate != null) {
            mAdapter.upDates(mdate);
        }
        mAdapter.setFooterView(footer);
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
        pagermoviefresh.setRefreshing(true);
        mListSubscriber = new Subscriber<List<SubjectsBean>>() {
            @Override
            public void onCompleted() {
                pagermoviefresh.setRefreshing(false);
            }

            @Override
            public void onError(Throwable e) {
                pagermoviefresh.setRefreshing(false);
                ToastUtils.show(getActivity(), "获取数据失败");
            }

            @Override
            public void onNext(List<SubjectsBean> subjectsBeen) {
                if (subjectsBeen != null) {
                    mAdapter.upDates(subjectsBeen);
                    CacheUtils.savebean(getActivity(), subjectsBeen, Constants.MOVIETITLE[position]);
                }
            }
        };

        MovieHttpMethods.getInstance().getMovieByTag(mListSubscriber, Constants.MOVIETITLE[position], 0, RECORD_COUNT);

    }

    /**
     * 上拉加载更多数据
     */
    private void updateMovieData() {
        if (mAdapter.getStart() == mStart) return;
        mStart = mAdapter.getStart();

        mListSubscriber = new Subscriber<List<SubjectsBean>>() {
            @Override
            public void onCompleted() {
                pagermoviefresh.setRefreshing(false);
                footer.setVisibility(View.GONE);
            }

            @Override
            public void onError(Throwable e) {
                ToastUtils.show(getActivity(), "获取数据失败");
                footer.setVisibility(View.GONE);
            }

            @Override
            public void onNext(List<SubjectsBean> subjectsBeen) {
                if (subjectsBeen != null) {
                    mAdapter.addDatas(subjectsBeen);
                }
            }
        };

        MovieHttpMethods.getInstance().getMovieByTag(mListSubscriber, Constants.MOVIETITLE[position], mStart, RECORD_COUNT);

    }
}
