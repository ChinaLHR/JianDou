package com.lhr.jiandou.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.bumptech.glide.Glide;
import com.lhr.jiandou.adapter.base.BaseSearchAdapter;
import com.lhr.jiandou.model.bean.SubjectsBean;

import java.util.List;

/**
 * Created by ChinaLHR on 2017/1/11.
 * Email:13435500980@163.com
 */

public class SearchMovieAdapter extends BaseSearchAdapter {
    private Context mContext;
    private List<SubjectsBean> bean;

    public SearchMovieAdapter(Context context, List<SubjectsBean> list) {
        super(context);
        mContext = context;
        bean = list;
    }

    public void addData(List<SubjectsBean> list){
        bean.addAll(list);
        notifyDataSetChanged();
    }

    public int getStart(){
        return bean.size();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (getItemViewType(position) == TYPE_FOOTER) return;
        ((Holder) holder).item_search_tv1.setText("");
        ((Holder) holder).item_search_tv2.setText("");
        Glide.with(mContext)
                .load(bean.get(position).getImages().getLarge())
                .into(((Holder) holder).item_search_iv);

        ((Holder) holder).item_search_title.setText(bean.get(position).getTitle());
        float average = (float) bean.get(position).getRating().getAverage();
        ((Holder) holder).item_search_rating.setRating(average / 2);
        ((Holder) holder).item_search_ratnum.setText(average + "");


        ((Holder) holder).item_search_tv1.append("导演：");
        List<SubjectsBean.DirectorsBean> directors = bean.get(position).getDirectors();
        for (int i = 0; i < directors.size(); i++) {
            if (i == directors.size() - 1) {
                ((Holder) holder).item_search_tv1.append(directors.get(i).getName());
            }else {
                ((Holder) holder).item_search_tv1.append(directors.get(i).getName()+"/");
            }
        }

        ((Holder) holder).item_search_tv2.append("演员：");
        List<SubjectsBean.CastsBean> casts = bean.get(position).getCasts();
        for (int i = 0; i < casts.size(); i++) {
            if (i==casts.size()-1){
                ((Holder) holder).item_search_tv2.append(casts.get(i).getName());
            }else{
                ((Holder) holder).item_search_tv2.append(casts.get(i).getName()+"/");
            }
        }

        ((Holder) holder).item_search_tv3.setText("上映时间："+bean.get(position).getYear());
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
        if (mFooterView==null) {
            return bean.size();
        }else{
            return bean.size()+1;
        }
    }
}
