package com.hd.hyd_36hr_news.utils.ui;


import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;


import com.hd.hyd_36hr_news.R;
import com.hd.hyd_36hr_news.utils.Contants;
import com.hd.hyd_36hr_news.utils.adapter.FixedPagerAdapter;
import com.hd.hyd_36hr_news.utils.entity.CategoryBean;
import com.hd.hyd_36hr_news.utils.entity.MessageEvent;
import com.hd.hyd_36hr_news.utils.okhttputils.OkHttpUtils;
import com.squareup.okhttp.Request;

import org.greenrobot.eventbus.EventBus;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.List;

/**
 * Created by Administrator on 2016/6/14 0014.
 *
 * 侧滑fragment
 */
public class CategoryFragment extends BaseFragment {
    private ImageView mBack;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private FixedPagerAdapter mAdapter;
    private List<CategoryBean> mCategoryBeanList;
    @Override
    protected int getLayout() {
        return R.layout.fragment_category_tab;
    }

    @Override
    protected void initView() {
        mBack = (ImageView) mView.findViewById(R.id.category_back);
        mRecyclerView = (RecyclerView) mView.findViewById(R.id.recycleview);
    }

    @Override
    protected void initVarible() {
        mLayoutManager = new LinearLayoutManager(getActivity(), OrientationHelper.VERTICAL,false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mAdapter = new FixedPagerAdapter(getActivity(),0);

        OkHttpUtils.getAsync(Contants.URL, new OkHttpUtils.DataCallBack() {
            @Override
            public void requestFailure(Request request, IOException e) {

            }

            @Override
            public void requestSuccess(String result) {
                Document document = Jsoup.parse(result,Contants.URL);
                mCategoryBeanList = CategoryBean.getCategoryBeanData(document);
                Log.d("-------","" +mCategoryBeanList.get(0));
                mAdapter.setCategoryBeanList(mCategoryBeanList);
                mRecyclerView.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();
            }
        });


    }

    @Override
    protected void initListener() {
        mAdapter.setRecycleViewItemClickListener(new FixedPagerAdapter.OnRecycleViewItemClickListener() {
            @Override
            public void onItemClick(View view, CategoryBean categoryBean) {
                EventBus.getDefault().post(new MessageEvent(categoryBean));
                ((HomeActivity)getActivity()).closeDrawrLayout();
            }
        });
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((HomeActivity)getActivity()).closeDrawrLayout();
            }
        });
    }

    @Override
    protected void bindData() {

    }
}
