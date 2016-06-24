package com.hd.hyd_36hr_news.news.ui;

import android.os.Bundle;
import android.widget.TextView;

import com.hd.hyd_36hr_news.R;
import com.hd.hyd_36hr_news.utils.entity.CategoryBean;
import com.hd.hyd_36hr_news.utils.ui.BaseFragment;

/**
 * Created by Administrator on 2016/6/15 0015.
 */
public class PagerFragment extends BaseFragment {
    private static final String KEY = "PagerFragment";
    private CategoryBean mCategoryBean;
    private TextView textView;
    @Override
    protected int getLayout() {
        return R.layout.fragment_news_pagerfragment;
    }

    @Override
    protected void initView() {
        Bundle bundle = getArguments();
        if (bundle != null){
            mCategoryBean = (CategoryBean) bundle.getSerializable(KEY);
        }
        textView = (TextView) mView.findViewById(R.id.tv_news_pagerfragment);
    }

    @Override
    protected void initVarible() {
        textView.setText(mCategoryBean.getmTitle());
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void bindData() {

    }

    public static PagerFragment newInstance(CategoryBean categoryBean){
        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY,categoryBean);
        PagerFragment pagerFragment = new PagerFragment();
        pagerFragment.setArguments(bundle);
        return pagerFragment;
    }
}
