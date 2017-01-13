package com.lhr.jiandou.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by ChinaLHR on 2016/12/29.
 * Email:13435500980@163.com
 */

public class TopViewPagerAdapter extends PagerAdapter {
    private List<String> mdate;
    private Context mContext;

    public TopViewPagerAdapter(Context context, List<String> imagelist) {
        mdate = imagelist;
        mContext = context;
    }

    @Override
    public int getCount() {
        return mdate.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView view = new ImageView(mContext);
        view.setScaleType(ImageView.ScaleType.FIT_XY);
        Glide.with(mContext)
                .load(mdate.get(position))
                .into(view);
        container.addView(view);
        return view;
    }
}
