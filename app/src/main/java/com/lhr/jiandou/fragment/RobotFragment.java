package com.lhr.jiandou.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lhr.jiandou.R;
import com.lhr.jiandou.utils.LogUtils;

/**
 * Created by ChinaLHR on 2016/12/13.
 * Email:13435500980@163.com
 */

public class RobotFragment extends BaseFragment {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        LogUtils.e("初始化RobotFragment");
        super.onCreate(savedInstanceState);
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.test_layout, container, false);
        TextView tv = (TextView) view.findViewById(R.id.test_tv);
        tv.setText("RobotFragment");
        return view;
    }
}
