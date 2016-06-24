package com.hd.hyd_36hr_news.news.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hd.hyd_36hr_news.R;
import com.hd.hyd_36hr_news.news.entity.ArticleBean;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Administrator on 2016/6/16 0016.
 */
public class MyNewsRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int NORMAL = 0;//新闻列表视图
    private static final int OTHER = 1;
    private static final int FOOTER = 2;//上拉加载更多布局
    private LayoutInflater mInflater;
    private int mType = 0;//默认是新闻列表视图
    private Context context;
    private OnRecyclerViewNewsItemClickListener onRecyclerViewNewsItemClickListener;
    private List<ArticleBean> articleBeanList;

    public void setArticleBeanList(List<ArticleBean> articleBeanList) {
        this.articleBeanList = articleBeanList;
    }

    public MyNewsRecyclerViewAdapter(Context context, int mType) {
        this.mType = mType;
        this.context = context;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = null;
        if (viewType == NORMAL){
            if (itemView == null){
                itemView = mInflater.inflate(R.layout.item_news_list,parent,false);
                NewsItemViewHolder itemViewHolder = new NewsItemViewHolder(itemView);
                return  itemViewHolder;
            }
        }else if(viewType == FOOTER){
            //上拉加载更多布局
            View footerView = mInflater.inflate(R.layout.footer_loadmore,parent,false);
            FootItemViewHolder footItemViewHolder = new FootItemViewHolder(footerView);
            return footItemViewHolder;
        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof NewsItemViewHolder){
            ArticleBean articleBean = articleBeanList.get(position);
            holder.itemView.setTag(articleBean);

            Picasso.with(context).load(articleBean.getmImgUrl()).into(((NewsItemViewHolder) holder).mImg);
            ((NewsItemViewHolder) holder).mTitle.setText(articleBean.getmTitle());
            ((NewsItemViewHolder) holder).mTag.setText(articleBean.getmMask());
            ((NewsItemViewHolder) holder).mAuther.setText(articleBean.getmAuthor().getmName());
            ((NewsItemViewHolder) holder).mTime.setText(articleBean.getmDateText());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onRecyclerViewNewsItemClickListener != null){
                        onRecyclerViewNewsItemClickListener.OnItemClick(view, (ArticleBean) view.getTag(),position);
                    }
                }
            });
        } else if (holder instanceof FootItemViewHolder){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onRecyclerViewNewsItemClickListener != null){
                        onRecyclerViewNewsItemClickListener.OnItemClick(view, (ArticleBean) view.getTag(),position);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return articleBeanList.size();
    }

    @Override
    public int getItemViewType(int position) {
       if (position + 1 == getItemCount()){
           return FOOTER;
       }else {
           return NORMAL;
       }
    }


    private class NewsItemViewHolder extends RecyclerView.ViewHolder{

        private TextView mTitle,mTag,mAuther,mTime;
        private ImageView mImg;

        public NewsItemViewHolder(View itemView) {
            super(itemView);
            mTitle = (TextView) itemView.findViewById(R.id.news_item_title);
            mTag = (TextView) itemView.findViewById(R.id.news_item_tag);
            mAuther = (TextView) itemView.findViewById(R.id.news_item_auther);
            mTime = (TextView) itemView.findViewById(R.id.news_item_time);
            mImg = (ImageView) itemView.findViewById(R.id.news_item_img);
        }
    }


    /**
     * 上拉刷新加载更多布局
     */
    private class FootItemViewHolder extends RecyclerView.ViewHolder{

        public FootItemViewHolder(View itemView) {
            super(itemView);
        }
    }

    public interface OnRecyclerViewNewsItemClickListener{
        public void OnItemClick(View view , ArticleBean articleBean, int position);


    }

    public void setOnRecyclerViewNewsItemClickListener(OnRecyclerViewNewsItemClickListener onRecyclerViewNewsItemClickListener) {
        this.onRecyclerViewNewsItemClickListener = onRecyclerViewNewsItemClickListener;
    }
}
