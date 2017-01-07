package com.lhr.jiandou.fragment;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lhr.jiandou.R;
import com.lhr.jiandou.fragment.base.BaseFragment;
import com.lhr.jiandou.fragment.pagerfragment.CollectionPageFragment;
import com.lhr.jiandou.utils.Constants;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import rx.Observable;

import static com.lhr.jiandou.utils.Constants.COLLECTION_TYPE;


/**
 * Created by ChinaLHR on 2016/12/13.
 * Email:13435500980@163.com
 */

public class CollectionFragment extends BaseFragment {
    private BottomBar collection_tab;
    private ViewPager collection_vp;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_collection, container, false);
        collection_vp = (ViewPager) view.findViewById(R.id.collection_vp);
        collection_tab = (BottomBar) view.findViewById(R.id.collection_tab);
        initView();
        initListener();
        return view;
    }

    private void initView() {
        collection_vp.setAdapter(new CollectionAdapter(getChildFragmentManager()));
        collection_vp.setOffscreenPageLimit(Constants.COLLECTION_TYPE.length);
    }

    /**
     * 初始化监听
     */
    private void initListener() {
        collection_tab.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                switch (tabId) {
                    case R.id.tab_movie:
                        collection_vp.setCurrentItem(0, false);
                        break;
                    case R.id.tab_book:
                        collection_vp.setCurrentItem(1, false);
                        break;
                    case R.id.tab_actor:
                        collection_vp.setCurrentItem(2, false);
                        break;
                }
            }
        });
    }

    class CollectionAdapter extends FragmentStatePagerAdapter {

        public CollectionAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return new CollectionPageFragment(Observable.just(COLLECTION_TYPE[position]));
        }

        @Override
        public int getCount() {
            return Constants.COLLECTION_TYPE.length;
        }
    }
}
