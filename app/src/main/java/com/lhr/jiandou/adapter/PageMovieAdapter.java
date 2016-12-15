package com.lhr.jiandou.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lhr.jiandou.R;
import com.lhr.jiandou.model.bean.SubjectsBean;

import java.util.List;

/**
 * Created by ChinaLHR on 2016/12/15.
 * Email:13435500980@163.com
 */

public class PageMovieAdapter extends RecyclerView.Adapter<PageMovieAdapter.MyViewHolder> {
    private List<SubjectsBean> mDate;
    private Context mContext;
    private OnItemClickListener mListener;

    public PageMovieAdapter(Context context, List<SubjectsBean> date) {
        mContext = context;
        mDate = date;
    }

    /**
     * 点击与长按的回调
     */
    public interface OnItemClickListener {
        void ItemClickListener(View view, int postion);

        void ItemLongClickListener(View view, int postion);
    }

    public void setOnClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_movie, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        holder.item_movie_tv_title.setText(mDate.get(position).getTitle());
        holder.item_movie_tv_number.setText(mDate.get(position).getRating().getAverage()+"");
        holder.item_movie_ratingbar.setRating((float) mDate.get(position).getRating().getAverage()/2);

        Glide.with(mContext)
                .load(mDate.get(position).getImages().getLarge())
                .into(holder.item_movie_iv);

        //设置点击事件
        if (mListener!=null){
            holder.item_movie_iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = holder.getLayoutPosition();
                    mListener.ItemClickListener(holder.itemView,position);
                }
            });

            holder.item_movie_iv.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    int position = holder.getLayoutPosition();
                    mListener.ItemLongClickListener(holder.itemView,position);
                    return true;
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return mDate.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView item_movie_iv;
        TextView item_movie_tv_title;
        TextView item_movie_tv_number;
        RatingBar item_movie_ratingbar;

        public MyViewHolder(View itemView) {
            super(itemView);
            item_movie_iv = (ImageView) itemView.findViewById(R.id.item_movie_iv);
            item_movie_tv_title = (TextView) itemView.findViewById(R.id.item_movie_tv_title);
            item_movie_tv_number = (TextView) itemView.findViewById(R.id.item_movie_tv_number);
            item_movie_ratingbar = (RatingBar) itemView.findViewById(R.id.item_movie_ratingbar);
        }
    }
}
