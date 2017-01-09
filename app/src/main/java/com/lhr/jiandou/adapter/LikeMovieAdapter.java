package com.lhr.jiandou.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lhr.jiandou.R;
import com.lhr.jiandou.adapter.base.BaseRecyclerAdapter;

import java.util.List;

/**
 * Created by ChinaLHR on 2016/12/21.
 * Email:13435500980@163.com
 */

public class LikeMovieAdapter extends BaseRecyclerAdapter<LikeMovieAdapter.MyHolder>{
    private Context mContext;
    private LayoutInflater mInflater;
    private List<String> mTitle;
    private List<String> mId;
    private List<String> mImg;

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyHolder holder = new MyHolder(mInflater.inflate(R.layout.item_likemovie, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ((MyHolder)holder).item_like_title.setText(mTitle.get(position));
        Glide.with(mContext)
                .load(mImg.get(position))
                .into(((MyHolder)holder).item_like_iv);
        if (mListener != null) {
            ((MyHolder)holder).item_like_iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onItemClick(mId.get(position),mImg.get(position));
                }
            });
        }
    }


    @Override
    public int getItemCount() {
        return mTitle.size();
    }


    public LikeMovieAdapter(Context context, List<String> title, List<String> img, List<String> id) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mTitle = title;
        mImg = img;
        mId = id;
    }


    class MyHolder extends BaseRecyclerAdapter.Holder{
        ImageView item_like_iv;
        TextView item_like_title;

        public MyHolder(View itemView) {
            super(itemView);
            item_like_iv = (ImageView) itemView.findViewById(R.id.item_like_iv);
            item_like_title = (TextView) itemView.findViewById(R.id.item_like_title);
        }
    }
}
