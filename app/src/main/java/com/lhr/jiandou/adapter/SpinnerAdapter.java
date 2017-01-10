package com.lhr.jiandou.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lhr.jiandou.R;
import com.lhr.jiandou.model.bean.SpinnerBean;

import java.util.List;

/**
 * Created by ChinaLHR on 2017/1/10.
 * Email:13435500980@163.com
 */

public class SpinnerAdapter extends BaseAdapter {
    private List<SpinnerBean> mList;
    private Context mContext;

    public SpinnerAdapter(Context context, List<SpinnerBean> bean) {
        mList = bean;
        mContext = context;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int i) {
        return mList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_spinner, null);
        }
        ImageView item_spinner_iv = (ImageView) view.findViewById(R.id.item_spinner_iv);
        TextView item_spinner_tv = (TextView) view.findViewById(R.id.item_spinner_tv);
        item_spinner_iv.setImageResource(mList.get(i).getId());
        item_spinner_tv.setText(mList.get(i).getName());
        return view;
    }


}
