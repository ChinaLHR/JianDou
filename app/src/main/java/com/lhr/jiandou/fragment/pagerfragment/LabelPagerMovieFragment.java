package com.lhr.jiandou.fragment.pagerfragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lhr.jiandou.R;
import com.lhr.jiandou.adapter.LabelAdapter;
import com.lhr.jiandou.adapter.helper.ItemDragHelperCallback;
import com.lhr.jiandou.utils.Constants;
import com.lhr.jiandou.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ChinaLHR on 2016/12/23.
 * Email:13435500980@163.com
 */

public class LabelPagerMovieFragment extends Fragment {
    private RecyclerView pager_f_labelmovie;
    private List<String> mLabel;
    private List<String> oLabel;
    private LabelAdapter adapter;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pagerfragment_labelm, container, false);
        pager_f_labelmovie = (RecyclerView) view.findViewById(R.id.pager_f_labelmovie);
        initdata();
        init();
        return view;
    }

    private void init() {

        GridLayoutManager manager = new GridLayoutManager(getActivity(), 4);
        pager_f_labelmovie.setLayoutManager(manager);
        ItemDragHelperCallback callback = new ItemDragHelperCallback();
        final ItemTouchHelper helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(pager_f_labelmovie);

        adapter = new LabelAdapter(getActivity(), helper, mLabel, oLabel);
        adapter.setOnMyChannelItemClickListener(new LabelAdapter.OnMyChannelItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                ToastUtils.show(getActivity(),"请长按或者点击编辑更换标签");
            }
        });
        //设置头布局占四个span，其他占一个
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                int viewType = adapter.getItemViewType(position);
                return viewType == adapter.TYPE_MY || viewType == adapter.TYPE_OTHER ? 1 : 4;
            }
        });

        pager_f_labelmovie.setAdapter(adapter);
    }

    private void initdata() {
        mLabel = new ArrayList<>();
        oLabel = new ArrayList<>();
        for (int i = 0; i < Constants.MOVIETITLE.length; i++) {
            mLabel.add(Constants.MOVIETITLE[i]);
        }
        boolean flag = true;
        for(String s:Constants.ALLMOVIE){
            for(int i=0;i<Constants.MOVIETITLE.length;i++)
            {
                if(s.equals(Constants.MOVIETITLE[i])) {
                    flag = false;
                    break;
                }
                flag = true;
            }
            if (flag){oLabel.add(s);}
        }

    }

}
