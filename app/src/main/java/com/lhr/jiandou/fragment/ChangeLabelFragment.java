package com.lhr.jiandou.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lhr.jiandou.R;
import com.lhr.jiandou.fragment.base.BaseFragment;
import com.lhr.jiandou.fragment.pagerfragment.LabelPagerBookFragment;
import com.lhr.jiandou.fragment.pagerfragment.LabelPagerMovieFragment;

/**
 * Created by ChinaLHR on 2016/12/22.
 * Email:13435500980@163.com
 */

public class ChangeLabelFragment extends BaseFragment {
    private String[] title = new String[]{"电影标签", "图书标签"};
    private ViewPager label_vp;
    private TabLayout label_tab;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_changelabel, container, false);
        label_vp = (ViewPager) view.findViewById(R.id.label_vp);
        label_tab = (TabLayout) view.findViewById(R.id.label_tab);
        label_vp.setAdapter(new FragmentAdapter(getChildFragmentManager()));
        label_tab.setupWithViewPager(label_vp);
        return view;
    }


    class FragmentAdapter extends FragmentPagerAdapter {

        public FragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                return new LabelPagerMovieFragment();
            } else {
                return new LabelPagerBookFragment();
            }
        }

        @Override
        public int getCount() {
            return 2;
        }

        //返回Tag的标题
        @Override
        public CharSequence getPageTitle(int position) {
            return title[position];
        }
    }
}
