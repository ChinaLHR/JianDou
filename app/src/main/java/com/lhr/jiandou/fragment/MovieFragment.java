package com.lhr.jiandou.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lhr.jiandou.R;
import com.lhr.jiandou.fragment.base.BaseFragment;
import com.lhr.jiandou.fragment.pagerfragment.MoviePagerFragment;
import com.lhr.jiandou.utils.Constants;
import com.lhr.jiandou.utils.SpUtils;

import rx.Observable;

/**
 * Created by ChinaLHR on 2016/12/13.
 * Email:13435500980@163.com
 */

public class MovieFragment extends BaseFragment {
    private android.support.design.widget.TabLayout fragmentmovietab;
    private android.support.v4.view.ViewPager fragmentmovievp;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String[] stringArray = SpUtils.getStringArray(getActivity(), Constants.MOVIEKEY);
        if(stringArray!=null&&stringArray.length>1){
            Constants.MOVIETITLE = SpUtils.getStringArray(getActivity(),Constants.MOVIEKEY);
            Constants.CHANGELABEL = false;
        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_movie, container, false);
        this.fragmentmovievp = (ViewPager) view.findViewById(R.id.fragment_movie_vp);
        this.fragmentmovietab = (TabLayout) view.findViewById(R.id.fragment_movie_tab);
        if (Constants.MOVIETITLE.length<=5){
            fragmentmovietab.setTabMode(TabLayout.MODE_FIXED);
        }else {
            fragmentmovietab.setTabMode(TabLayout.MODE_SCROLLABLE);
        }

        initData();
        return view;
    }

    public void initData() {
        fragmentmovievp.setAdapter(new MoviePagerAdapter(getChildFragmentManager()));
        //viewpager的初始化长度
        fragmentmovievp.setOffscreenPageLimit(Constants.MOVIETITLE.length);
        fragmentmovietab.setupWithViewPager(fragmentmovievp);

    }

    class MoviePagerAdapter extends FragmentStatePagerAdapter {

        public MoviePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return new MoviePagerFragment(Observable.just(position));
        }

        @Override
        public int getCount() {

            return Constants.MOVIETITLE.length;

        }

        @Override
        public CharSequence getPageTitle(int position) {

            return Constants.MOVIETITLE[position];

        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);

    }
}
