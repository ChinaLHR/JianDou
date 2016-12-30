package com.lhr.jiandou.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lhr.jiandou.R;
import com.lhr.jiandou.adapter.base.BasePagerAdapter;
import com.lhr.jiandou.model.bean.SubjectsBean;

import java.util.List;

/**
 * Created by ChinaLHR on 2016/12/30.
 * Email:13435500980@163.com
 */

public class PageTopAdapter extends BasePagerAdapter<SubjectsBean> {

    public PageTopAdapter(Context context, List<SubjectsBean> date) {
        super(context, date);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_top250, parent, false);
        return new TopHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_FOOTER) return;
        Glide.with(mContext)
                .load(mDate.get(position).getImages().getLarge())
                .into( ((TopHolder)holder).top_img);
        ((TopHolder)holder).top_movie_title.setText(mDate.get(position).getTitle());
        ((TopHolder)holder).top_num.setText(position);
        ((TopHolder)holder).top_movie_ratingnum.setText(mDate.get(position).getRating().getAverage() + "");
        ((TopHolder)holder).top_movie_ratingbar.setRating((float) mDate.get(position).getRating().getAverage() / 2);

    }

    class TopHolder extends RecyclerView.ViewHolder {
        TextView top_num;
        ImageView top_img;
        TextView top_movie_title;
        RatingBar top_movie_ratingbar;
        TextView top_movie_ratingnum;
        TextView top_movie_brief;
        CardView top_card;

        public TopHolder(View itemView) {
            super(itemView);
            top_card = (CardView) itemView.findViewById(R.id.top_card);
            top_num = (TextView) itemView.findViewById(R.id.top_num);
            top_img = (ImageView) itemView.findViewById(R.id.top_img);
            top_movie_title = (TextView) itemView.findViewById(R.id.top_movie_title);
            top_movie_ratingbar = (RatingBar) itemView.findViewById(R.id.top_movie_ratingbar);
            top_movie_ratingnum = (TextView) itemView.findViewById(R.id.top_movie_ratingnum);
            top_movie_brief = (TextView) itemView.findViewById(R.id.top_movie_brief);
        }
    }
}
