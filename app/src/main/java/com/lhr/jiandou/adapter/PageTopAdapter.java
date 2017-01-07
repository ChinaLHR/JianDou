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
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (mDate.size()>1) {
            if (getItemViewType(position) == TYPE_FOOTER) return;
            ((TopHolder)holder).top_movie_brief.setText("");
            Glide.with(mContext)
                    .load(mDate.get(position).getImages().getLarge())
                    .into(((TopHolder) holder).top_img);
            ((TopHolder) holder).top_movie_title.setText(mDate.get(position).getTitle());
            ((TopHolder) holder).top_num.setText(position+1+"");
            ((TopHolder) holder).top_movie_ratingnum.setText(mDate.get(position).getRating().getAverage() + "");
            ((TopHolder) holder).top_movie_ratingbar.setRating((float) mDate.get(position).getRating().getAverage() / 2);

            /**
             * 上映时间
             */
            if (!mDate.get(position).getYear().trim().equals("")) {
                ((TopHolder) holder).top_movie_brief.append("上映时间：");
                ((TopHolder) holder).top_movie_brief.append(mDate.get(position).getYear() + "\n");
            }
            /**
             * 导演信息
             */
            if (mDate.get(position).getDirectors() != null) {
                ((TopHolder) holder).top_movie_brief.append("导演：");
                for (int i = 0; i < mDate.get(position).getDirectors().size(); i++) {

                    if (i == mDate.get(position).getDirectors().size() - 1) {
                        ((TopHolder) holder).top_movie_brief.append(mDate.get(position).getDirectors().get(i).getName());
                    } else {
                        ((TopHolder) holder).top_movie_brief.append(mDate.get(position).getDirectors().get(i).getName() + "/");
                    }
                }

                ((TopHolder) holder).top_movie_brief.append("\n");

            }
            /**
             * 演员信息
             */
            if (mDate.get(position).getCasts() != null) {
                ((TopHolder) holder).top_movie_brief.append("演员：");
                for (int i = 0; i < mDate.get(position).getCasts().size(); i++) {
                    if (i == mDate.get(position).getCasts().size()-1) {
                        ((TopHolder) holder).top_movie_brief.append(mDate.get(position).getCasts().get(i).getName());
                    } else {
                        ((TopHolder) holder).top_movie_brief.append(mDate.get(position).getCasts().get(i).getName() + "/");
                    }
                }

                ((TopHolder) holder).top_movie_brief.append("\n");
            }
            /**
             * 类型信息
             */
            if (mDate.get(position).getCasts() != null) {
                ((TopHolder) holder).top_movie_brief.append("类型：");
                for (int i = 0; i < mDate.get(position).getGenres().size(); i++) {
                    if (i == mDate.get(position).getGenres().size() - 1) {
                        ((TopHolder) holder).top_movie_brief.append(mDate.get(position).getGenres().get(i));
                    } else {
                        ((TopHolder) holder).top_movie_brief.append(mDate.get(position).getGenres().get(i) + "/");
                    }
                }
                ((TopHolder) holder).top_movie_brief.append("\n");
            }

        }
        if (mListener!=null){
            ((TopHolder)holder).top_card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = holder.getLayoutPosition();
                    mListener.ItemClickListener(holder.itemView, mDate.get(position).getId(),mDate.get(position).getImages().getLarge());
                }
            });
        }
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
