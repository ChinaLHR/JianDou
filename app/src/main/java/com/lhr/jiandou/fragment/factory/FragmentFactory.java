package com.lhr.jiandou.fragment.factory;

import com.lhr.jiandou.fragment.BookFragment;
import com.lhr.jiandou.fragment.ChangeLabelFragment;
import com.lhr.jiandou.fragment.CollectionFragment;
import com.lhr.jiandou.fragment.LeaderboardFragment;
import com.lhr.jiandou.fragment.MovieFragment;
import com.lhr.jiandou.fragment.base.BaseFragment;
import com.lhr.jiandou.utils.Constants;

/**
 * Created by ChinaLHR on 2016/12/13.
 * Email:13435500980@163.com
 */

public class FragmentFactory {
    public static BaseFragment getFragment(String title) {
        BaseFragment fragment = null;
        switch (title) {
            case Constants.MOVIE:
                fragment = new MovieFragment();
                break;
            case Constants.BOOK:
                fragment = new BookFragment();
                break;
            case Constants.LIST:
                fragment = new LeaderboardFragment();
                break;
            case Constants.COLLECTION:
                fragment = new CollectionFragment();
                break;
            case Constants.CHANGESECTION:
                fragment = new ChangeLabelFragment();
                break;
        }
        return fragment;
    }
}
