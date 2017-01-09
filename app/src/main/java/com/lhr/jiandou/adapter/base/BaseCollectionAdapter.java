package com.lhr.jiandou.adapter.base;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.lhr.jiandou.R;

/**
 * Created by ChinaLHR on 2017/1/9.
 * Email:13435500980@163.com
 */

public abstract class BaseCollectionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    /**
     * 点击回调接口
     */
    public OnItemClickListener mListener;

    public void setOnItemClickListener(OnItemClickListener li) {
        mListener = li;
    }

    public interface OnItemClickListener {
        void onItemClick(String id, String url);
    }

    public BaseCollectionAdapter(Context context){
        this.mContext = context;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Holder holder = new Holder(LayoutInflater.from(mContext).inflate(R.layout.item_collection, parent, false));
        return holder;
    }

    public class Holder extends RecyclerView.ViewHolder {
        public CardView item_collection_card;
        public ImageView item_collection_img;
        public RatingBar item_collection_ratingbar;
        public TextView item_collection_ratingnum;
        public LinearLayout item_collection_ratll;
        public TextView item_collection_title;
        public TextView item_collection_tv1;
        public TextView item_collection_tv2;
        public TextView item_collection_tv3;

        public Holder(View itemView) {
            super(itemView);
             item_collection_card = (CardView) itemView.findViewById(R.id.item_collection_card);
             item_collection_img = (ImageView) itemView.findViewById(R.id.item_collection_img);
             item_collection_ratingbar = (RatingBar) itemView.findViewById(R.id.item_collection_ratingbar);
             item_collection_ratingnum = (TextView) itemView.findViewById(R.id.item_collection_ratingnum);
             item_collection_ratll = (LinearLayout) itemView.findViewById(R.id.item_collection_ratll);
             item_collection_title = (TextView) itemView.findViewById(R.id.item_collection_title);
             item_collection_tv1 = (TextView) itemView.findViewById(R.id.item_collection_tv1);
             item_collection_tv2 = (TextView) itemView.findViewById(R.id.item_collection_tv2);
             item_collection_tv3 = (TextView) itemView.findViewById(R.id.item_collection_tv3);
        }
    }


}
