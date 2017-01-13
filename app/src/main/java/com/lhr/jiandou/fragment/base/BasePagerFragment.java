package com.lhr.jiandou.fragment.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lhr.jiandou.MyApplication;
import com.lhr.jiandou.R;
import com.lhr.jiandou.adapter.base.BasePagerAdapter;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by ChinaLHR on 2016/12/24.
 * Email:13435500980@163.com
 */

public abstract class BasePagerFragment extends Fragment {
    public static final int RECORD_COUNT = 18;
    public int mStart = 0;
    private Subscriber<Integer> mSubscriber;
    public int position;
    public BasePagerAdapter mAdapter;
    public View footer;
    public RecyclerView pagerbaserv;
    public SwipeRefreshLayout pagerbasefresh;
    public android.support.design.widget.FloatingActionButton pagerbasefab;

    public BasePagerFragment(Observable<Integer> observable) {
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
        View view = inflater.inflate(R.layout.pagerfragment_base, container, false);
        this.pagerbasefab = (FloatingActionButton) view.findViewById(R.id.pager_base_fab);
        this.pagerbasefresh = (SwipeRefreshLayout) view.findViewById(R.id.pager_base_fresh);
        this.pagerbaserv = (RecyclerView) view.findViewById(R.id.pager_base_rv);

        initData();
        initListener();
        return view;

    }

    private void initData() {
        pagerbasefresh.setColorSchemeResources(R.color.colorPrimary, R.color.colorAccent, R.color.colorPrimaryDark);
        pagerbasefresh.setProgressViewOffset(false, 0, 48);
        initRecyclerView();
    }


    private void initListener() {
        /**
         * RecyclerView单击与长按监听
         */
        onRecyclerViewListener();
        /**
         * 顶部下拉松开时会调用这个方法
         */
        pagerbasefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadMovieData();
            }
        });

        pagerbasefab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pagerbaserv.getAdapter() != null) {
                    pagerbaserv.scrollToPosition(0);
                }
            }
        });
        /**
         * RecyclerView滑动监听
         */
        pagerbaserv.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
                    pagerbaserv.scrollToPosition(mAdapter.getItemCount() - 1);

                }
            }
        });
    }

    public abstract void onRecyclerViewListener();

    public abstract void updateMovieData();

    public abstract void loadMovieData();

    public abstract void initRecyclerView();

    @Override
    public void onDestroy() {
        super.onDestroy();
        mSubscriber.unsubscribe();
    }
}
