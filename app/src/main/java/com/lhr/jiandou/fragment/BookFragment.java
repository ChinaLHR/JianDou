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
import com.lhr.jiandou.fragment.pagerfragment.BookPagerFragment;
import com.lhr.jiandou.utils.Constants;
import com.lhr.jiandou.utils.SpUtils;
import com.lhr.jiandou.view.animation.DepthPageTransformer;

import rx.Observable;

/**
 * Created by ChinaLHR on 2016/12/13.
 * Email:13435500980@163.com
 */

public class BookFragment extends BaseFragment {

    private TabLayout fragmentbooktab;
    private ViewPager fragmentbookvp;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String[] stringArray = SpUtils.getStringArray(getActivity(), Constants.BOOKKEY);
        if (stringArray != null && stringArray.length > 1) {
            Constants.BOOKTITLE = SpUtils.getStringArray(getActivity(), Constants.BOOKKEY);
            Constants.CHANGELABEL_BOOK = false;
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_book, container, false);
        this.fragmentbookvp = (ViewPager) view.findViewById(R.id.fragment_book_vp);
        this.fragmentbooktab = (TabLayout) view.findViewById(R.id.fragment_book_tab);
        if (Constants.BOOKTITLE.length <= 5) {
            fragmentbooktab.setTabMode(TabLayout.MODE_FIXED);
        } else {
            fragmentbooktab.setTabMode(TabLayout.MODE_SCROLLABLE);
        }
        initData();
        return view;
    }

    public void initData() {
        fragmentbookvp.setAdapter(new BookPagerAdapter(getChildFragmentManager()));
        //viewpager的初始化长度
        fragmentbookvp.setOffscreenPageLimit(Constants.BOOKTITLE.length);
        fragmentbookvp.setPageTransformer(true,new DepthPageTransformer());
        fragmentbooktab.setupWithViewPager(fragmentbookvp);
    }

    class BookPagerAdapter extends FragmentStatePagerAdapter {

        public BookPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return new BookPagerFragment(Observable.just(position));
        }

        @Override
        public int getCount() {

            return Constants.BOOKTITLE.length;

        }

        @Override
        public CharSequence getPageTitle(int position) {

            return Constants.BOOKTITLE[position];

        }
    }


}
