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
import com.lhr.jiandou.model.bean.ActorDetailsBean;

import java.util.List;

/**
 * Created by ChinaLHR on 2016/12/22.
 * Email:13435500980@163.com
 */

public class ActorMovieAdapter extends BaseRecyclerAdapter<ActorMovieAdapter.MyHolder> {
    private LayoutInflater mInflater;
    private Context mContext;
    private List<ActorDetailsBean.WorksBean> mdate;

    public ActorMovieAdapter(Context context, List<ActorDetailsBean.WorksBean> bean) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mdate = bean;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        Glide.with(mContext)
                .load(mdate.get(position).getSubject().getImages().getLarge())
                .into(((MyHolder) holder).item_actor_movie_iv);
        if (mdate.get(position).getSubject().getTitle() != null) {
            ((MyHolder) holder).item_actor__movie_title.setText(mdate.get(position).getSubject().getTitle());
        }
        if (mdate.get(position).getRoles().get(0) != null) {
            ((MyHolder) holder).item_actor_movie_work.setText(mdate.get(position).getRoles().get(0));
        }

        if (mListener != null) {
            ((MyHolder) holder).item_actor_movie_iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onItemClick(mdate.get(position).getSubject().getId(), mdate.get(position).getSubject().getImages().getLarge());
                }
            });
        }

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyHolder mHolder = new MyHolder(mInflater.inflate(R.layout.item_actor_movie, parent, false));
        return mHolder;
    }

    @Override
    public int getItemCount() {
        return mdate.size();
    }

    class MyHolder extends BaseRecyclerAdapter.Holder {
        ImageView item_actor_movie_iv;
        TextView item_actor_movie_work;
        TextView item_actor__movie_title;

        public MyHolder(View itemView) {
            super(itemView);
            item_actor_movie_iv = (ImageView) itemView.findViewById(R.id.item_actor_movie_iv);
            item_actor_movie_work = (TextView) itemView.findViewById(R.id.item_actor_movie_work);
            item_actor__movie_title = (TextView) itemView.findViewById(R.id.item_actor__movie_title);
        }
    }
}
