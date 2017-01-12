package com.lhr.jiandou.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.bumptech.glide.Glide;
import com.lhr.jiandou.adapter.base.BaseSearchAdapter;
import com.lhr.jiandou.model.bean.BooksBean;
import com.lhr.jiandou.utils.StringUtils;

import java.util.List;

/**
 * Created by ChinaLHR on 2017/1/12.
 * Email:13435500980@163.com
 */

public class SearchBookAdapter extends BaseSearchAdapter {
    private Context mContext;
    private List<BooksBean> bean;
    private boolean isOpen = false;

    public SearchBookAdapter(Context context, List<BooksBean> list) {
        super(context);
        mContext = context;
        bean = list;
    }

    public void addData(List<BooksBean> list) {
        bean.addAll(list);
        notifyDataSetChanged();
    }

    public int getStart() {
        return bean.size();
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (getItemViewType(position) == TYPE_FOOTER) return;
        Glide.with(mContext)
                .load(bean.get(position).getImages().getLarge())
                .into(((Holder) holder).item_search_iv);
        ((Holder) holder).item_search_title.setText(bean.get(position).getTitle());
        float average = Float.parseFloat(bean.get(position).getRating().getAverage());
        ((Holder) holder).item_search_rating.setRating(average / 2);
        ((Holder) holder).item_search_ratnum.setText(average + "");

        ((Holder) holder).item_search_tv1.setText("作者：");
        List<String> author = bean.get(position).getAuthor();
        StringUtils.addViewString(author, ((Holder) holder).item_search_tv1);

        ((Holder) holder).item_search_tv2.setText("出版社：" + bean.get(position).getPublisher());

        ((Holder) holder).item_search_tv3.setText("出版时间：" + bean.get(position).getPubdate());
        ((Holder) holder).item_search_detail.setVisibility(View.GONE);
        ((Holder) holder).item_search_switch.setVisibility(View.GONE);

        if (mListener!=null){
            ((Holder) holder).item_search_card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onItemClick(bean.get(position).getId(),bean.get(position).getImages().getLarge());
                }
            });
        }


    }

    @Override
    public int getItemCount() {
        if (mFooterView == null) {
            return bean.size();
        } else {
            return bean.size() + 1;
        }
    }
}
