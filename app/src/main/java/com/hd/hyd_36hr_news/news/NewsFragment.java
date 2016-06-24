package com.hd.hyd_36hr_news.news;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hd.hyd_36hr_news.R;
import com.hd.hyd_36hr_news.news.adapter.MyNewsRecyclerViewAdapter;
import com.hd.hyd_36hr_news.news.entity.ArticleBean;
import com.hd.hyd_36hr_news.utils.Contants;
import com.hd.hyd_36hr_news.utils.entity.CategoryBean;
import com.hd.hyd_36hr_news.utils.entity.MessageEvent;
import com.hd.hyd_36hr_news.utils.okhttputils.OkHttpUtils;
import com.hd.hyd_36hr_news.utils.ui.BaseFragment;
import com.hd.hyd_36hr_news.utils.ui.HomeActivity;
import com.squareup.okhttp.Request;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.List;

/**
 * Created by Administrator on 2016/6/15 0015.
 */

public class NewsFragment extends BaseFragment {
    private ImageView topbarImageView;
    private RecyclerView recyclerView;
    private LinearLayoutManager mManager;
    private MyNewsRecyclerViewAdapter mAdapter;
    private TextView topBarTv;
    private List<ArticleBean> articleBeanList;
    private CategoryBean categoryBean = null;
    private SwipeRefreshLayout swipeRefreshLayout;

    /******/


    /******/

    private int lastItem;
    private boolean isMore = true;//解决上拉重复加载的bug

    @Override
    protected int getLayout() {
        return R.layout.fragment_news;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void receiveEvent(MessageEvent event) {
        categoryBean = event.getCategoryBean();
        topBarTv.setText(categoryBean.getmTitle());
        if (categoryBean.getmType().equals("all")) {
            requestData(Contants.URL);
        } else {
            requestData(categoryBean.getmHref());
        }


    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void initView() {
        topbarImageView = (ImageView) mView.findViewById(R.id.topbar_left_img);
        recyclerView = (RecyclerView) mView.findViewById(R.id.news_fragmeng_recyclerview);
        topBarTv = (TextView) mView.findViewById(R.id.topbar_text);
        swipeRefreshLayout = (SwipeRefreshLayout) mView.findViewById(R.id.id_newsfragment_swiperefreshlayout);
    }

//    private int getColor(int color) {
//        return getResources().getColor(color);
//    }

    @Override
    protected void initVarible() {
        swipeRefreshLayout.setProgressBackgroundColorSchemeColor(Color.WHITE);
        swipeRefreshLayout.setColorSchemeColors(Color.RED, Color.BLUE, Color.BLACK, Color.GREEN);
        swipeRefreshLayout.setProgressViewOffset(false, 0, 50);
        //第三个参数表示结果 显示的顺序
        mManager = new LinearLayoutManager(getActivity(), OrientationHelper.VERTICAL, false);
        recyclerView.setLayoutManager(mManager);
        //设置RecyclerView固定大小
        recyclerView.setHasFixedSize(true);

        //设置动画
//        recyclerView.setItemAnimator(new DefaultItemAnimator());

        /**
         *
         */
        mAdapter = new MyNewsRecyclerViewAdapter(getActivity(),0);
        if (categoryBean != null){
            requestData(categoryBean.getmHref());
        }else {
            requestData(Contants.URL);
        }
    }

    @Override
    protected void initListener() {
        topbarImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((HomeActivity) getActivity()).openDrawrLayout();
            }
        });

        mAdapter.setOnRecyclerViewNewsItemClickListener(new MyNewsRecyclerViewAdapter.OnRecyclerViewNewsItemClickListener() {
            @Override
            public void OnItemClick(View view, ArticleBean articleBean, int position) {
                Toast.makeText(getActivity(), "position =" + position + "" + articleBean.getmTitle(), Toast.LENGTH_SHORT).show();
            }

        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (categoryBean == null){
                    requestData(Contants.URL);
                }else {
                    requestData(categoryBean.getmHref());
                }

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (swipeRefreshLayout.isRefreshing()){
                            swipeRefreshLayout.setRefreshing(false);
                        }
                    }
                }, 5000);
            }
        });

        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastItem +1 == mManager.getItemCount()){
                    if (isMore){
                        isMore = false;
                        String loadMoreURL = null;
                        if (categoryBean != null){
                            loadMoreURL = categoryBean.getmHref() + "?b_url_code=" + articleBeanList.get(articleBeanList.size() - 1).getmId() + "&d=next";
                        }else {
                            loadMoreURL = Contants.URL + "?b_url_code=" + articleBeanList.get(articleBeanList.size() - 1).getmId() + "&d=next";
                        }
                        OkHttpUtils.getAsync(loadMoreURL, new OkHttpUtils.DataCallBack() {
                            @Override
                            public void requestFailure(Request request, IOException e) {

                            }

                            @Override
                            public void requestSuccess(String result) {
                                Document doc = Jsoup.parse(result,Contants.URL);
                                List<ArticleBean> temp = ArticleBean.getArticleBeans(doc);
                                articleBeanList.addAll(temp);
                                mAdapter.notifyDataSetChanged();
                                isMore = true ;
                            }
                        });

                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastItem = mManager.findLastCompletelyVisibleItemPosition();
            }
        });

    }

    @Override
    protected void bindData() {

    }

    private void requestData(String url) {
        OkHttpUtils.getAsync(url, new OkHttpUtils.DataCallBack() {
            @Override
            public void requestFailure(Request request, IOException e) {

            }

            @Override
            public void requestSuccess(String result) {
                Document doc = Jsoup.parse(result, Contants.URL);
                articleBeanList = ArticleBean.getArticleBeans(doc);
                mAdapter.setArticleBeanList(articleBeanList);
                recyclerView.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();
            }
        });
    }
}


//public class NewsFragment extends BaseFragment {
//    private ImageView topbarImageView;
//    private RelativeLayout mRelativeLayout;
//    private FragmentManager mManager;
//    private FragmentTransaction mTransaction;
//    private List<CategoryBean> categoryBeanList = Contants.getCategoryBean();
//    private List<Fragment> fragmentList;
//    @Override
//    protected int getLayout() {
//        return R.layout.fragment_news;
//    }
//
//    @Override
//    protected void initView() {
//        topbarImageView = (ImageView) mView.findViewById(R.id.topbar_left_img);
//        mRelativeLayout = (RelativeLayout) mView.findViewById(R.id.newsfragment_container);
//    }
//
//    @Override
//    protected void initVarible() {
//        mManager = getChildFragmentManager();
//        mTransaction = mManager.beginTransaction();
//        fragmentList = new ArrayList<Fragment>();
//        for (int i=0 ; i<categoryBeanList.size() ; i++){
//            BaseFragment fragment = null ;
//            if (i==0){
//                fragment = new MainFragment().newInstance(categoryBeanList.get(i));
//            }else {
//                fragment = new PagerFragment().newInstance(categoryBeanList.get(i));
//            }
//            fragmentList.add(fragment);
//        }
//
//        for (int i=0 ; i<fragmentList.size(); i++){
//            mTransaction.add(R.id.newsfragment_container,fragmentList.get(i),""+i);
//            mTransaction.hide(fragmentList.get(i));
//        }
//        mTransaction.show(fragmentList.get(0));
//        mTransaction.commit();
//    }
//
//    @Override
//    protected void initListener() {
//        topbarImageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                ((HomeActivity)getActivity()).openDrawrLayout();
//            }
//        });
//    }
//
//    @Override
//    protected void bindData() {
//
//    }
//}
