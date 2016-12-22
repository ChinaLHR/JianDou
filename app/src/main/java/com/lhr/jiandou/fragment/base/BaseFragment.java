package com.lhr.jiandou.fragment.base;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;

/**
 * Created by ChinaLHR on 2016/12/13.
 * Email:13435500980@163.com
 */

public class BaseFragment extends Fragment {
    public Activity mActivity;
    /**
     * 避免getActivity空指针
     * @param context
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
       mActivity = (Activity)context;
    }
}
