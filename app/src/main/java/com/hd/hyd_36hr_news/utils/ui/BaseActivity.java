package com.hd.hyd_36hr_news.utils.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Administrator on 2016/6/14 0014.
 *
 * 基类Activity
 */
public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        initView();
        initVarible();
        initListener();

    }


    /**
     * 获取子类布局
     * @return
     */
    protected abstract int getLayout();

    /**
     * 初始化控件
     */
    protected abstract void initView();


    /**
     * 初始化变量
     */
    protected abstract void initVarible();


    /**
     * 初始化事件处理
     */
    protected abstract void initListener();


    /**
     * 绑定数据
     */
    protected abstract void bindData();
}
