package com.lhr.jiandou.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.lhr.jiandou.adapter.base.BaseCollectionAdapter;
import com.lhr.jiandou.model.db.Actor_db;

import java.util.List;

/**
 * Created by ChinaLHR on 2017/1/9.
 * Email:13435500980@163.com
 */

public class CollectionActorAdapter extends BaseCollectionAdapter {
    private Context mContext;
    private List<Actor_db> mdate;
    public CollectionActorAdapter(Context context,List<Actor_db> list) {
        super(context);
        mdate = list;
        mContext = context;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        Glide.with(mContext)
                .load(mdate.get(position).getImgurl())
                .asBitmap()
                .centerCrop()
                .into(new BitmapImageViewTarget(((Holder)holder).item_collection_img){
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable circularBitmapDrawable =
                                RoundedBitmapDrawableFactory.create(mContext.getResources(), resource);
                        circularBitmapDrawable.setCircular(true);
                        ((Holder)holder).item_collection_img.setImageDrawable(circularBitmapDrawable);
                    }
                });
        ((Holder) holder).item_collection_ratll.setVisibility(View.GONE);
        ((Holder) holder).item_collection_title.setText(mdate.get(position).getTitle());
        ((Holder) holder).item_collection_tv1.setText("性别："+mdate.get(position).getGender());
        ((Holder) holder).item_collection_tv2.setText("出生地："+mdate.get(position).getBorn_place());
        ((Holder) holder).item_collection_tv3.setText("收藏时间："+mdate.get(position).getTime());
        if (mListener!=null){
            ((Holder) holder).item_collection_card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onItemClick(mdate.get(position).getActor_id(),mdate.get(position).getImgurl());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mdate.size();
    }
}
