package com.lhr.jiandou.fragment.pagerfragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lhr.jiandou.MyApplication;
import com.lhr.jiandou.R;
import com.lhr.jiandou.activity.MovieDetailsActivity;
import com.lhr.jiandou.adapter.PageLeaderboardAdapter;
import com.lhr.jiandou.adapter.PageMovieAdapter;
import com.lhr.jiandou.adapter.base.BasePagerAdapter;
import com.lhr.jiandou.model.bean.SubjectsBean;
import com.lhr.jiandou.model.httputils.MovieHttpMethods;
import com.lhr.jiandou.utils.CacheUtils;
import com.lhr.jiandou.utils.Constants;

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

public class LeaderboardNewFragment extends Fragment {
    private List<SubjectsBean> mdate;
    private Subscriber<List<SubjectsBean>> mListSubscriber;
    private BasePagerAdapter mAdapter;
    private static final int RECORD_COUNT = 18;
    private int mStart = 0;
    public View footer;
    public int position;
    private Subscriber mSubscriber;
    private RecyclerView pagerleadeaderboardrv;
    private android.support.design.widget.FloatingActionButton pagerleadeaderboardfab;

    public LeaderboardNewFragment(Observable<Integer> observable) {
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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pagefragment_leaderboard, container, false);
        this.pagerleadeaderboardfab = (FloatingActionButton) view.findViewById(R.id.pager_leadeaderboard_fab);
        this.pagerleadeaderboardrv = (RecyclerView) view.findViewById(R.id.pager_leadeaderboard_rv);
        initRecyclerView();
        initListener();
        if (MyApplication.isNetworkAvailable(getActivity())) {
            loadMovieData();
        }
        return view;

    }

    /**
     * 初始化监听
     */
    private void initListener() {
        /**
         * RecyclerView滑动监听
         */
        pagerleadeaderboardrv.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
                    pagerleadeaderboardrv.scrollToPosition(mAdapter.getItemCount() - 1);
                }

                if (dy>0){
                    pagerleadeaderboardfab.setVisibility(View.GONE);
                }else {
                    pagerleadeaderboardfab.setVisibility(View.VISIBLE);
                }
            }
        });
        mAdapter.setOnClickListener(new PageMovieAdapter.OnItemClickListener() {


            @Override
            public void ItemClickListener(View view, String id, String img) {
                MovieDetailsActivity.toActivity(getActivity(), id, img);
            }

            @Override
            public void ItemLongClickListener(View view, int postion) {
            }
        });
        pagerleadeaderboardfab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pagerleadeaderboardrv.getAdapter() != null) {
                    pagerleadeaderboardrv.scrollToPosition(0);
                }
            }
        });

    }


    public void updateMovieData() {
        if (mAdapter.getStart() == mStart) return;
        mStart = mAdapter.getStart();

        mListSubscriber = new Subscriber<List<SubjectsBean>>() {
            @Override
            public void onCompleted() {
                footer.setVisibility(View.GONE);
            }

            @Override
            public void onError(Throwable e) {
                footer.setVisibility(View.GONE);
            }

            @Override
            public void onNext(List<SubjectsBean> subjectsBeen) {
                if (subjectsBeen != null) {
                    mAdapter.addDatas(subjectsBeen);

                }
            }
        };

        if (position == 0) {
            MovieHttpMethods.getInstance().getMovieHot(mListSubscriber, mStart, RECORD_COUNT);
        } else {
            MovieHttpMethods.getInstance().getMovieNew(mListSubscriber, mStart, RECORD_COUNT);
        }
    }


    public void loadMovieData() {
        mListSubscriber = new Subscriber<List<SubjectsBean>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(List<SubjectsBean> subjectsBeen) {
                if (subjectsBeen != null) {
                    mAdapter.upDates(subjectsBeen);
                    Observable.just(subjectsBeen)
                            .subscribeOn(AndroidSchedulers.mainThread())
                            .observeOn(Schedulers.io())
                            .subscribe(new Action1<List<SubjectsBean>>() {
                                @Override
                                public void call(List<SubjectsBean> subjectsBeen) {
                                    CacheUtils.savebean(getActivity(), subjectsBeen, CacheUtils.DataCache_movie, Constants.LEADERBOARD[position]);
                                }
                            });
                }

            }
        };

        if (position == 0) {
            MovieHttpMethods.getInstance().getMovieHot(mListSubscriber, 0, RECORD_COUNT);
        } else {
            MovieHttpMethods.getInstance().getMovieNew(mListSubscriber, 0, RECORD_COUNT);
        }
    }

    public void initRecyclerView() {
        List<SubjectsBean> mSubjectbean = new ArrayList<>();
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(), 3);
        pagerleadeaderboardrv.setLayoutManager(mLayoutManager);
        mAdapter = new PageLeaderboardAdapter(getContext(), mSubjectbean, Constants.LEADERBOARD[position]);
        footer = LayoutInflater.from(this.getActivity()).inflate(R.layout.item_footer, pagerleadeaderboardrv, false);

        Observable.just(mdate = CacheUtils.readbean(getActivity(), CacheUtils.DataCache_movie, Constants.LEADERBOARD[position]))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<SubjectsBean>>() {
                    @Override
                    public void call(List<SubjectsBean> subjectsBeen) {
                        if (mdate != null) {
                            mAdapter.upDates(mdate);
                        }
                    }
                });
        mAdapter.setFooterView(footer);
        pagerleadeaderboardrv.setAdapter(mAdapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mSubscriber != null) {
            mSubscriber.unsubscribe();
        }
        if (mListSubscriber != null) {
            mListSubscriber.unsubscribe();
        }

    }
}
