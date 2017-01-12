package com.lhr.jiandou.fragment.pagerfragment;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.lhr.jiandou.R;
import com.lhr.jiandou.activity.MovieDetailsActivity;
import com.lhr.jiandou.adapter.PageMovieAdapter;
import com.lhr.jiandou.fragment.base.BasePagerFragment;
import com.lhr.jiandou.model.bean.SubjectsBean;
import com.lhr.jiandou.model.httputils.MovieHttpMethods;
import com.lhr.jiandou.utils.CacheUtils;
import com.lhr.jiandou.utils.Constants;
import com.lhr.jiandou.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by ChinaLHR on 2016/12/13.
 * Email:13435500980@163.com
 */

public class MoviePagerFragment extends BasePagerFragment {
    private List<SubjectsBean> mdate;
    private Subscriber<List<SubjectsBean>> mListSubscriber;
    public MoviePagerFragment(Observable<Integer> observable) {
        super(observable);
    }

    @Override
    public void onRecyclerViewListener() {
        mAdapter.setOnClickListener(new PageMovieAdapter.OnItemClickListener() {


            @Override
            public void ItemClickListener(View view, String id, String img) {
                MovieDetailsActivity.toActivity(getActivity(), id, img);
            }

            @Override
            public void ItemLongClickListener(View view, int postion) {
            }
        });
    }

    @Override
    public void updateMovieData() {
        if (mAdapter.getStart() == mStart) return;
        mStart = mAdapter.getStart();

        mListSubscriber = new Subscriber<List<SubjectsBean>>() {
            @Override
            public void onCompleted() {
                pagerbasefresh.setRefreshing(false);
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

        MovieHttpMethods.getInstance().getMovieByTag(mListSubscriber, Constants.MOVIETITLE[position], mStart, RECORD_COUNT);

    }


    @Override
    public void loadMovieData() {

        pagerbasefresh.setRefreshing(true);
        mListSubscriber = new Subscriber<List<SubjectsBean>>() {
            @Override
            public void onCompleted() {
                pagerbasefresh.setRefreshing(false);
            }

            @Override
            public void onError(Throwable e) {
                pagerbasefresh.setRefreshing(false);
            }

            @Override
            public void onNext(List<SubjectsBean> subjectsBeen) {
                if (subjectsBeen != null) {
                    LogUtils.e("获取数据成功");
                    mAdapter.upDates(subjectsBeen);
                   Observable.just(subjectsBeen)
                           .subscribeOn(AndroidSchedulers.mainThread())
                           .observeOn(Schedulers.io())
                           .subscribe(new Action1<List<SubjectsBean>>() {
                               @Override
                               public void call(List<SubjectsBean> subjectsBeen) {
                                   CacheUtils.savebean(getActivity(),subjectsBeen,CacheUtils.DataCache_movie,Constants.MOVIETITLE[position]);
                               }
                           });
                }

            }
        };


        MovieHttpMethods.getInstance().getMovieByTag(mListSubscriber, Constants.MOVIETITLE[position], 0, RECORD_COUNT);
    }

    @Override
    public void initRecyclerView() {
        List<SubjectsBean> mSubjectbean = new ArrayList<>();
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(), 3);
        pagerbaserv.setLayoutManager(mLayoutManager);
        mAdapter = new PageMovieAdapter(getContext(), mSubjectbean);
        footer = LayoutInflater.from(this.getActivity()).inflate(R.layout.item_footer, pagerbaserv, false);

        Observable.just(mdate = CacheUtils.readbean(getActivity(), CacheUtils.DataCache_movie, Constants.MOVIETITLE[position]))
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

        if (mdate != null) {
            mAdapter.upDates(mdate);
        }


        mAdapter.setFooterView(footer);
        pagerbaserv.setAdapter(mAdapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mListSubscriber!=null){
            mListSubscriber.unsubscribe();
        }

    }
}
