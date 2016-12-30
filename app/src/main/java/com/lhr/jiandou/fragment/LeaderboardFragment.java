package com.lhr.jiandou.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.lhr.jiandou.R;
import com.lhr.jiandou.adapter.TopViewPagerAdapter;
import com.lhr.jiandou.fragment.base.BaseFragment;
import com.lhr.jiandou.fragment.pagerfragment.LeaderboardNewFragment;
import com.lhr.jiandou.fragment.pagerfragment.LeaderboardTopFragment;
import com.lhr.jiandou.utils.Constants;
import com.lhr.jiandou.utils.jsoupUtils.GetVPImage;

import java.util.List;

import rx.Observable;

/**
 * Created by ChinaLHR on 2016/12/13.
 * Email:13435500980@163.com
 */

public class LeaderboardFragment extends BaseFragment {

    private android.support.v4.view.ViewPager leaderboardtopvp;
    private android.widget.LinearLayout leaderboardpointll;
    private android.widget.RelativeLayout leaderboardtopview;
    private android.support.design.widget.TabLayout leaderboardtabLayout;
    private android.support.v4.view.ViewPager leaderboardvp;
    private android.widget.LinearLayout leaderboardll;

    private List<String> mList;
    private Handler mHandler;
    private int OldSelectedPosition = 0;
    private MyAsyncTask myAsyncTask;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_leaderboard, container, false);
        this.leaderboardll = (LinearLayout) view.findViewById(R.id.leaderboard_ll);
        this.leaderboardvp = (ViewPager) view.findViewById(R.id.leaderboard_vp);
        this.leaderboardtabLayout = (TabLayout) view.findViewById(R.id.leaderboard_tabLayout);
        this.leaderboardtopview = (RelativeLayout) view.findViewById(R.id.leaderboard_topview);
        this.leaderboardpointll = (LinearLayout) view.findViewById(R.id.leaderboard_point_ll);
        this.leaderboardtopvp = (ViewPager) view.findViewById(R.id.leaderboard_topvp);
        initView();
        initListener();
        return view;
    }

    private void initListener() {
        leaderboardtopvp.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // 停止广告自动轮播
                        // 删除handler的所有消息
                        if (mHandler!=null){
                        mHandler.removeCallbacksAndMessages(null);}
                        break;
                    case MotionEvent.ACTION_UP:
                        //启动广告
                        if (mHandler!=null){
                        mHandler.sendEmptyMessageDelayed(0, 3000);}
                        break;
                }
                return false;
            }
        });

        leaderboardtopvp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                leaderboardpointll.getChildAt(OldSelectedPosition).setEnabled(false);
                leaderboardpointll.getChildAt(position).setEnabled(true);
                OldSelectedPosition = position;
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initView() {
        leaderboardvp.setAdapter(new FragmentAdapter(getChildFragmentManager()));
        leaderboardvp.setOffscreenPageLimit(Constants.LEADERBOARD.length);
        leaderboardtabLayout.setupWithViewPager(leaderboardvp);
        myAsyncTask = new MyAsyncTask();
        myAsyncTask.execute();

    }

    class FragmentAdapter extends FragmentPagerAdapter {
        public FragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position==2){
                return new LeaderboardTopFragment();
            }else{
                return new LeaderboardNewFragment(Observable.just(position));
            }
        }

        @Override
        public int getCount() {
            return Constants.LEADERBOARD.length;
        }

        //返回Tag的标题
        @Override
        public CharSequence getPageTitle(int position) {
            return Constants.LEADERBOARD[position];
        }
    }

    class MyAsyncTask extends AsyncTask {

        @Override
        protected Object doInBackground(Object[] params) {
            if (isCancelled()) {
                return null;
            } else {
                GetVPImage getVPImage = new GetVPImage();
                mList = getVPImage.getImage();
                return null;
            }
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            if (mList != null) {
                initViews();
                TopViewPagerAdapter adapter = new TopViewPagerAdapter(getActivity(), mList);
                leaderboardtopvp.setAdapter(adapter);
                leaderboardpointll.getChildAt(0).setEnabled(true);
                if (mHandler == null) {
                    mHandler = new Handler() {
                        @Override
                        public void handleMessage(Message msg) {
                            int currentItem = leaderboardtopvp.getCurrentItem();
                            currentItem++;
                            if (currentItem > mList.size() - 1) {
                                //跳到第一页
                                currentItem = 0;
                            }
                            leaderboardtopvp.setCurrentItem(currentItem);
                            mHandler.sendEmptyMessageDelayed(0, 3000);//内循环
                        }

                    };
                    // 保证启动自动轮播逻辑只执行一次
                    mHandler.sendEmptyMessageDelayed(0, 3000);
                }
            }
        }
    }

    /**
     * 初始化指示器
     */
    private void initViews() {
        LinearLayout.LayoutParams layoutparams;
        for (int i = 0; i < mList.size(); i++) {
            View pointView = new View(getActivity());
            pointView.setBackgroundResource(R.drawable.selector_bg_point);
            layoutparams = new LinearLayout.LayoutParams(20, 20);
            if (i != 0) {
                layoutparams.leftMargin = 20;
            }
            pointView.setEnabled(false);
            leaderboardpointll.addView(pointView, layoutparams);
        }
    }

    @Override
    public void onPause() {

        if (myAsyncTask != null && myAsyncTask.getStatus() == android.os.AsyncTask.Status.RUNNING) {
            myAsyncTask.cancel(true);
        }
        super.onPause();
    }
}
