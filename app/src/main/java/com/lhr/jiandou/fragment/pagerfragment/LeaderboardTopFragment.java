package com.lhr.jiandou.fragment.pagerfragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lhr.jiandou.R;
import com.lhr.jiandou.adapter.base.BasePagerAdapter;
import com.lhr.jiandou.model.bean.SubjectsBean;
import com.lhr.jiandou.model.httputils.MovieHttpMethods;
import com.lhr.jiandou.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;

/**
 * Created by ChinaLHR on 2016/12/29.
 * Email:13435500980@163.com
 */

public class LeaderboardTopFragment extends Fragment {
    private android.support.v7.widget.RecyclerView pagertop250rv;
    private android.support.design.widget.FloatingActionButton pagertop250fab;
    private List<SubjectsBean> mdate;
    private Subscriber<List<SubjectsBean>> mListSubscriber;
    private BasePagerAdapter mAdapter;
    private static final int RECORD_COUNT = 20;
    private int mStart = 0;
    public View footer;
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
        initRecyclerView();
        initListener();
        loadMovieData();
        return view;
    }

    /**
     * 初始化监听
     */
    private void initListener() {
    }

    /**
     * 初始化RecyclerV
     */
    private void initRecyclerView() {
        List<SubjectsBean> mList = new ArrayList<>();

    }

    private void loadMovieData(){
        mListSubscriber = new Subscriber<List<SubjectsBean>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                LogUtils.e("加载失败");
            }

            @Override
            public void onNext(List<SubjectsBean> subjectsBeen) {
                if (subjectsBeen!=null){
                    for (int i = 0; i < subjectsBeen.size(); i++) {
                        LogUtils.e(subjectsBeen.get(i).getTitle());
                    }
                }
            }
        };

        MovieHttpMethods.getInstance().getMovieTop250(mListSubscriber,0, RECORD_COUNT);
    }
}
