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
import com.lhr.jiandou.utils.Constants;

import java.util.List;

/**
 * Created by ChinaLHR on 2016/12/29.
 * Email:13435500980@163.com
 */

public class PageLeaderboardAdapter extends BasePagerAdapter<SubjectsBean>{
    private String type;
    public PageLeaderboardAdapter(Context context, List<SubjectsBean> date,String TYPE) {
        super(context, date);
        this.type = TYPE;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mFooterView != null && viewType == TYPE_FOOTER)
            return new MyViewHolder(mFooterView);
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_basepager, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_FOOTER) return;

        ((MyViewHolder)holder).item_base_tv_title.setText(mDate.get(position).getTitle());

        if (type.equals(Constants.LEADERBOARD[0])) {
            ((MyViewHolder) holder).item_movie_ratingbar.setRating((float) mDate.get(position).getRating().getAverage() / 2);
            ((MyViewHolder)holder).item_base_tv_number.setText(mDate.get(position).getRating().getAverage() + "");
        }else {
            ((MyViewHolder) holder).item_movie_ratingbar.setVisibility(View.GONE);
            ((MyViewHolder)holder).item_base_tv_number.setVisibility(View.GONE);

        }
        Glide.with(mContext)
                .load(mDate.get(position).getImages().getLarge())
                .into(((MyViewHolder)holder).item_base_iv);

        //设置点击事件
        if (mListener != null) {
            ((MyViewHolder)holder).item_movie_cardview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = holder.getLayoutPosition();
                    mListener.ItemClickListener(holder.itemView, mDate.get(position).getId(),mDate.get(position).getImages().getLarge());
                }
            });

            (( MyViewHolder)holder).item_movie_cardview.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    int position = holder.getLayoutPosition();
                    mListener.ItemLongClickListener(holder.itemView, position);
                    return true;
                }
            });
        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView item_base_iv;
        TextView item_base_tv_title;
        TextView item_base_tv_number;
        RatingBar item_movie_ratingbar;
        CardView item_movie_cardview;

        public MyViewHolder(View itemView) {
            super(itemView);
            item_base_iv = (ImageView) itemView.findViewById(R.id.item_base_iv);
            item_base_tv_title = (TextView) itemView.findViewById(R.id.item_base_tv_title);
            item_base_tv_number = (TextView) itemView.findViewById(R.id.item_base_tv_number);
            item_movie_ratingbar = (RatingBar) itemView.findViewById(R.id.item_base_ratingbar);
            item_movie_cardview = (CardView) itemView.findViewById(R.id.item_movie_cardview);
        }
    }
}
