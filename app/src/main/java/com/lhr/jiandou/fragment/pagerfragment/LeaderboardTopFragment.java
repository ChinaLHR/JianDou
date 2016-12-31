package com.lhr.jiandou.fragment.pagerfragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.lhr.jiandou.MyApplication;
import com.lhr.jiandou.R;
import com.lhr.jiandou.activity.MovieDetailsActivity;
import com.lhr.jiandou.adapter.PageTopAdapter;
import com.lhr.jiandou.adapter.base.BasePagerAdapter;
import com.lhr.jiandou.model.bean.SubjectsBean;
import com.lhr.jiandou.model.httputils.MovieHttpMethods;
import com.lhr.jiandou.utils.CacheUtils;
import com.lhr.jiandou.utils.Constants;
import com.lhr.jiandou.utils.SnackBarUtils;
import com.lhr.jiandou.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by ChinaLHR on 2016/12/29.
 * Email:13435500980@163.com
 */

public class LeaderboardTopFragment extends Fragment {
    private android.support.v7.widget.RecyclerView pagertop250rv;
    private android.support.design.widget.FloatingActionButton pagertop250fab;
    private CoordinatorLayout pager_top250_coor;
    private ProgressBar top_progressbar;
    private List<SubjectsBean> mdate;
    private Subscriber<List<SubjectsBean>> mListSubscriber;
    private BasePagerAdapter mAdapter;
    private static final int RECORD_COUNT = 20;
    private int mStart = 0;
    public int position;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.pagefragment_top250, container, false);
        this.pagertop250fab = (FloatingActionButton) view.findViewById(R.id.pager_top250_fab);
        this.pagertop250rv = (RecyclerView) view.findViewById(R.id.pager_top250_rv);
        top_progressbar = (ProgressBar) view.findViewById(R.id.top_progressbar);
        pager_top250_coor = (CoordinatorLayout) view.findViewById(R.id.pager_top250_coor);
        initRecyclerView();
        initListener();
        return view;
    }

    /**
     * 初始化监听
     */
    private void initListener() {
        /**
         * RecyclerView滑动监听
         */
        pagertop250rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!recyclerView.canScrollVertically(1) && MyApplication.isNetworkAvailable(getActivity())) {
                    updateMovieData();
                    pagertop250rv.scrollToPosition(mAdapter.getItemCount() - 1);
                }

                if (dy > 0) {
                    pagertop250fab.setVisibility(View.GONE);
                } else {
                    pagertop250fab.setVisibility(View.VISIBLE);
                }
            }


        });
        mAdapter.setOnClickListener(new PageTopAdapter.OnItemClickListener() {


            @Override
            public void ItemClickListener(View view, String id, String img) {
                MovieDetailsActivity.toActivity(getActivity(), id, img);
            }

            @Override
            public void ItemLongClickListener(View view, int postion) {
            }
        });


        pagertop250fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pagertop250rv.getAdapter() != null) {
                    pagertop250rv.scrollToPosition(0);
                }
            }
        });

    }

    /**
     * 加载更多
     */
    private void updateMovieData() {
        if (mAdapter.getStart() == mStart) return;
        mStart = mAdapter.getStart();
        top_progressbar.setVisibility(View.VISIBLE);
        mListSubscriber = new Subscriber<List<SubjectsBean>>() {
            @Override
            public void onCompleted() {
                top_progressbar.setVisibility(View.GONE);
                pagertop250rv.scrollToPosition(mStart);
            }

            @Override
            public void onError(Throwable e) {
                top_progressbar.setVisibility(View.GONE);
                SnackBarUtils.showSnackBar(pager_top250_coor, UIUtils.getString(getActivity(),R.string.error));
            }

            @Override
            public void onNext(List<SubjectsBean> subjectsBeen) {
                if (subjectsBeen != null) {
                    mAdapter.addDatas(subjectsBeen);
                }
            }
        };
        MovieHttpMethods.getInstance().getMovieTop250(mListSubscriber, mStart, RECORD_COUNT);
    }

    /**
     * 初始化RecyclerView
     */
    private void initRecyclerView() {
        List<SubjectsBean> mList = new ArrayList<>();
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        pagertop250rv.setLayoutManager(mLayoutManager);
        mAdapter = new PageTopAdapter(getActivity(), mList);
//        footer = LayoutInflater.from(this.getActivity()).inflate(R.layout.item_footer, pagertop250rv, false);

        Observable.just(mdate = CacheUtils.readbean(getActivity(), CacheUtils.DataCache_movie, Constants.LEADERBOARD[2]))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<SubjectsBean>>() {
                    @Override
                    public void call(List<SubjectsBean> subjectsBeen) {
                        if (mdate != null) {
                            mAdapter.upDates(mdate);
                        } else {
                            loadMovieData();
                        }
                    }
                });
        pagertop250rv.setAdapter(mAdapter);

    }

    private void loadMovieData() {
        mListSubscriber = new Subscriber<List<SubjectsBean>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(List<SubjectsBean> subjectsBeen) {
                mAdapter.upDates(subjectsBeen);
                Observable.just(subjectsBeen)
                        .subscribeOn(AndroidSchedulers.mainThread())
                        .observeOn(Schedulers.io())
                        .subscribe(new Action1<List<SubjectsBean>>() {
                            @Override
                            public void call(List<SubjectsBean> subjectsBeen) {
                                CacheUtils.savebean(getActivity(), subjectsBeen, CacheUtils.DataCache_movie, Constants.LEADERBOARD[2]);
                            }
                        });
            }
        };

        MovieHttpMethods.getInstance().getMovieTop250(mListSubscriber, 0, RECORD_COUNT);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mListSubscriber != null) {
            mListSubscriber.unsubscribe();
        }
    }
}
