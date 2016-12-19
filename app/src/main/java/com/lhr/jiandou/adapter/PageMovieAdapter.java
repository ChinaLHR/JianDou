package com.lhr.jiandou.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lhr.jiandou.R;
import com.lhr.jiandou.model.bean.SubjectsBean;

import java.util.List;

/**
 * Created by ChinaLHR on 2016/12/15.
 * Email:13435500980@163.com
 */

public class PageMovieAdapter extends RecyclerView.Adapter<PageMovieAdapter.MyViewHolder> {
    private List<SubjectsBean> mDate;
    private Context mContext;
    private OnItemClickListener mListener;

    public static final int TYPE_NORMAL = 1;
    public static final int TYPE_FOOTER = 2;
    private View mFooterView;

    public PageMovieAdapter(Context context, List<SubjectsBean> date) {
        mContext = context;
        mDate = date;
    }

    /**
     * 设置脚布局
     */
    public void setFooterView(View footerView) {
        mFooterView = footerView;
        notifyItemInserted(getItemCount() - 1);
    }

    public View getFooterView() {
        return mFooterView;
    }

    /**
     * 获取不同类型的item
     */
    @Override
    public int getItemViewType(int position) {
        if (mFooterView == null) return TYPE_NORMAL;
        if (position == getItemCount() - 1) return TYPE_FOOTER;
        return TYPE_NORMAL;
    }

    /**
     * 添加数据
     *
     * @param date
     */
    public void addDatas(List<SubjectsBean> date) {
        mDate.addAll(date);
        notifyDataSetChanged();
    }

    /**
     * 升级数据
     */
    public void upDates(List<SubjectsBean> date){
        mDate = date;
        notifyDataSetChanged();
    }

    /**
     * 获得start
     */
    public int getStart(){
        return mDate.size();
    }
    /**
     * 点击与长按的回调
     */
    public interface OnItemClickListener {
        void ItemClickListener(View view, int postion,SubjectsBean bean);

        void ItemLongClickListener(View view, int postion);
    }

    public void setOnClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mFooterView != null && viewType == TYPE_FOOTER)
            return new MyViewHolder(mFooterView);
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_movie, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_FOOTER) return;

        holder.item_movie_tv_title.setText(mDate.get(position).getTitle());
        holder.item_movie_tv_number.setText(mDate.get(position).getRating().getAverage() + "");
        holder.item_movie_ratingbar.setRating((float) mDate.get(position).getRating().getAverage() / 2);

        Glide.with(mContext)
                .load(mDate.get(position).getImages().getLarge())
                .error(R.mipmap.movie_error)
                .into(holder.item_movie_iv);

        //设置点击事件
        if (mListener != null) {
            holder.item_movie_cardview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = holder.getLayoutPosition();
                    mListener.ItemClickListener(holder.itemView, position,mDate.get(position));
                }
            });

            holder.item_movie_cardview.setOnLongClickListener(new View.OnLongClickListener() {
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
        ImageView item_movie_iv;
        TextView item_movie_tv_title;
        TextView item_movie_tv_number;
        RatingBar item_movie_ratingbar;
        CardView item_movie_cardview;

        public MyViewHolder(View itemView) {
            super(itemView);
            item_movie_iv = (ImageView) itemView.findViewById(R.id.item_movie_iv);
            item_movie_tv_title = (TextView) itemView.findViewById(R.id.item_movie_tv_title);
            item_movie_tv_number = (TextView) itemView.findViewById(R.id.item_movie_tv_number);
            item_movie_ratingbar = (RatingBar) itemView.findViewById(R.id.item_movie_ratingbar);
            item_movie_cardview = (CardView) itemView.findViewById(R.id.item_movie_cardview);
        }
    }

    /**
     * 解决StaggeredGridLayoutManager布局添加脚布局的问题
     *
     * @param holder
     */
    @Override
    public void onViewAttachedToWindow(MyViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
        if (lp != null && lp instanceof StaggeredGridLayoutManager.LayoutParams && holder.getLayoutPosition() == getItemCount() - 1) {
            StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) lp;
            p.setFullSpan(true);
        }
    }

    /**
     * 解决GridLayoutManager添加脚布局的问题
     */
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = ((GridLayoutManager) manager);
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                //这个方法的返回值决定了我们每个position上的item占据的单元格个数
                @Override
                public int getSpanSize(int position) {
                    if (getItemViewType(position) == TYPE_FOOTER) {
                        return gridManager.getSpanCount();
                    } else {
                        return 1;
                    }
                }
            });
        }
    }


    /**
     * 获取item数量
     */
    @Override
    public int getItemCount() {
        if (mFooterView == null) {
            return mDate.size();
        } else {
            return mDate.size()+1;
        }
    }

}
