package com.lhr.jiandou.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.bumptech.glide.Glide;
import com.lhr.jiandou.adapter.base.BaseCollectionAdapter;
import com.lhr.jiandou.model.db.Book_db;

import java.util.List;

/**
 * Created by ChinaLHR on 2017/1/9.
 * Email:13435500980@163.com
 */

public class CollectionBookAdapter extends BaseCollectionAdapter {
    private Context mContext;
    private List<Book_db> mdate;

    public CollectionBookAdapter(Context context, List<Book_db> list) {
        super(context);
        mdate = list;
        mContext = context;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        Glide.with(mContext)
                .load(mdate.get(position).getImgurl())
                .into(((Holder) holder).item_collection_img);
        ((Holder) holder).item_collection_title.setText(mdate.get(position).getTitle());
        ((Holder) holder).item_collection_ratingbar.setRating(mdate.get(position).getRating()/2);
        ((Holder) holder).item_collection_ratingnum.setText(mdate.get(position).getRating()+"");
        ((Holder) holder).item_collection_tv1.setText("作者："+mdate.get(position).getAuthor());
        ((Holder) holder).item_collection_tv2.setText("出版社："+mdate.get(position).getPublisher());
        ((Holder) holder).item_collection_tv3.setText("收藏时间："+mdate.get(position).getTime());
        if (mListener != null) {
            ((Holder) holder).item_collection_card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onItemClick(mdate.get(position).getBook_id(), mdate.get(position).getImgurl());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mdate.size();
    }
}
