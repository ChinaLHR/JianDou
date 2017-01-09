package com.lhr.jiandou.adapter.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by ChinaLHR on 2016/12/22.
 * Email:13435500980@163.com
 */

public abstract class BaseRecyclerAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public OnItemClickListener mListener;

    public void setOnItemClickListener(OnItemClickListener li) {
        mListener = li;
    }

    /**
     * 点击回调接口
     */
    public interface OnItemClickListener {
        void onItemClick(String id, String url);
    }


    public class Holder extends RecyclerView.ViewHolder {
        public Holder(View itemView) {
            super(itemView);
        }
    }

}
