package com.lhr.jiandou.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lhr.jiandou.R;
import com.lhr.jiandou.fragment.base.BaseFragment;

import java.util.List;

/**
 * Created by ChinaLHR on 2016/12/25.
 * Email:13435500980@163.com
 */

public class SettingFragment extends BaseFragment {
    private RecyclerView mRecyclerView;
    private List<String> datas;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("LHR","初始化Fragment");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.test_layout,container,false);

        return view;
    }
}
