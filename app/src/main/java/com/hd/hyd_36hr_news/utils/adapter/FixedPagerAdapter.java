package com.hd.hyd_36hr_news.utils.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hd.hyd_36hr_news.R;
import com.hd.hyd_36hr_news.utils.entity.CategoryBean;

import java.util.List;

/**
 * Created by Administrator on 2016/6/14 0014.
 */
public class FixedPagerAdapter extends RecyclerView.Adapter implements View.OnClickListener{
    private static final int CATEGORY = 0;
    private static final int OTHER = 1;
    private LayoutInflater mInflater;
    private List<CategoryBean> categoryBeanList;
    private int mType;
    private OnRecycleViewItemClickListener recycleViewItemClickListener;



    public void setCategoryBeanList(List<CategoryBean> categoryBeanList) {
        this.categoryBeanList = categoryBeanList;
    }

    public FixedPagerAdapter(Context context, int Type) {
        this.mType = Type;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        if (viewType == CATEGORY){
            itemView =  mInflater.inflate(R.layout.items_category,parent,false);
            itemView.setOnClickListener(this);
            ItemViewHolder itemViewHolder = new ItemViewHolder(itemView);
            return itemViewHolder;
        }else if (viewType == OTHER){

        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder){
            CategoryBean categoryBean = categoryBeanList.get(position);
            holder.itemView.setTag(categoryBean);
            ((ItemViewHolder) holder).mItemType.setBackgroundColor(Color.RED);
            ((ItemViewHolder) holder).mItemName.setText(categoryBean.getmTitle());
        }
    }

    @Override
    public int getItemCount() {
        return categoryBeanList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (mType == 0){
            return CATEGORY;
        }else {
            return OTHER;
        }

    }

    class ItemViewHolder extends RecyclerView.ViewHolder{

        private View mItemType;
        private TextView mItemName;

        public ItemViewHolder(View itemView) {
            super(itemView);
            mItemName = (TextView) itemView.findViewById(R.id.category_items_text);
            mItemType = itemView.findViewById(R.id.category_items_type);
        }
    }

    public static interface OnRecycleViewItemClickListener{
        public void onItemClick(View view,CategoryBean categoryBean);
    }

    @Override
    public void onClick(View view) {
        if (recycleViewItemClickListener != null){
            recycleViewItemClickListener.onItemClick(view, (CategoryBean) view.getTag());
        }

    }

    public void setRecycleViewItemClickListener(OnRecycleViewItemClickListener recycleViewItemClickListener){
        this.recycleViewItemClickListener = recycleViewItemClickListener;
    }
}
