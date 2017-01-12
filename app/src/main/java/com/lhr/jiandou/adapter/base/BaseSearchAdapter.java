package com.lhr.jiandou.adapter.base;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.lhr.jiandou.R;

/**
 * Created by ChinaLHR on 2017/1/11.
 * Email:13435500980@163.com
 */

public abstract class BaseSearchAdapter extends RecyclerView.Adapter {

    private Context mContext;
    public static final int TYPE_NORMAL = 1;
    public static final int TYPE_FOOTER = 2;
    public View mFooterView;


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

    public BaseSearchAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mFooterView != null && viewType == TYPE_FOOTER)
            return new Holder(mFooterView);
        Holder holder = new Holder(LayoutInflater.from(mContext).inflate(R.layout.item_search, parent, false));
        return holder;
    }

    /**
     * 设置脚布局
     */
    public void setFooterView(View footerView) {
        mFooterView = footerView;
        notifyItemInserted(getItemCount() - 1);
    }

    @Override
    public int getItemViewType(int position) {
        if (mFooterView == null) return TYPE_NORMAL;
        if (position == getItemCount() - 1) return TYPE_FOOTER;
        return TYPE_NORMAL;
    }


    public class Holder extends RecyclerView.ViewHolder {
        public CardView item_search_card;
        public TextView item_search_detail;
        public ImageView item_search_iv;
        public RatingBar item_search_rating;
        public TextView item_search_ratnum;
        public ImageView item_search_switch;
        public TextView item_search_title;
        public TextView item_search_tv1;
        public TextView item_search_tv2;
        public TextView item_search_tv3;

        public Holder(View itemView) {
            super(itemView);
            item_search_card = (CardView) itemView.findViewById(R.id.item_search_card);
            item_search_detail = (TextView) itemView.findViewById(R.id.item_search_detail);
            item_search_iv = (ImageView) itemView.findViewById(R.id.item_search_iv);
            item_search_rating = (RatingBar) itemView.findViewById(R.id.item_search_rating);
            item_search_ratnum = (TextView) itemView.findViewById(R.id.item_search_ratnum);
            item_search_switch = (ImageView) itemView.findViewById(R.id.item_search_switch);
            item_search_title = (TextView) itemView.findViewById(R.id.item_search_title);
            item_search_tv1 = (TextView) itemView.findViewById(R.id.item_search_tv1);
            item_search_tv2 = (TextView) itemView.findViewById(R.id.item_search_tv2);
            item_search_tv3 = (TextView) itemView.findViewById(R.id.item_search_tv3);

        }
    }
}
