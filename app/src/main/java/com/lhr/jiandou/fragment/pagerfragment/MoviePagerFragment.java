package com.lhr.jiandou.fragment.pagerfragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lhr.jiandou.R;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by ChinaLHR on 2016/12/13.
 * Email:13435500980@163.com
 */

public class MoviePagerFragment extends Fragment{
    private static final String TITLE_KEY = "title";
    private static final int RECORD_COUNT = 20;


    private int position;
    private Subscriber<Integer> mSubscriber;
    private boolean isFirstRefresh = true;
    private int mStart = 0;


    private android.support.v7.widget.RecyclerView pagermovierv;
    private android.support.v4.widget.SwipeRefreshLayout pagermoviefresh;
    private android.support.design.widget.FloatingActionButton pagermoviefab;

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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pagerfragment_movie, container, false);
        this.pagermoviefab = (FloatingActionButton) view.findViewById(R.id.pager_movie_fab);
        this.pagermoviefresh = (SwipeRefreshLayout) view.findViewById(R.id.pager_movie_fresh);
        this.pagermovierv = (RecyclerView) view.findViewById(R.id.pager_movie_rv);
        initData();
        initListener();
        return view;
    }

    /**
     * 初始化监听处理
     */
    private void initListener() {
        /**
         * 顶部下拉松开时会调用这个方法，在里面实现请求数据的逻辑，设置下拉进度条消失等等
         */
        pagermoviefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mStart = 0;
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
    }

    /**
     * 初始化View
     */
    private void initData() {
        pagermoviefresh.setColorSchemeResources(R.color.colorPrimary, R.color.colorAccent, R.color.colorPrimaryDark);
        initRecyclerView();
    }

    /**
     * 初始化RecyclerView
     */
    private void initRecyclerView() {


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mSubscriber.unsubscribe();
    }


    /**
     * 网络请求,获取数据
     */
    private void loadMovieData() {

    }



}
