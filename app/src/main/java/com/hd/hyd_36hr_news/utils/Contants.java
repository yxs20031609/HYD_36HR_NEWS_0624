package com.hd.hyd_36hr_news.utils;

import com.hd.hyd_36hr_news.utils.entity.CategoryBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/6/14 0014.
 *
 * 系统里常量设置
 */
public class Contants {

    public static final String URL = "http://36kr.com/columns/starding";


    public static List<CategoryBean> getCategoryBean(){
        List<CategoryBean> categoryBeenList = new ArrayList<CategoryBean>();
        categoryBeenList.add(new CategoryBean("全部","http://www.36kr.com/columns/starding","all"));
        categoryBeenList.add(new CategoryBean("早期项目","http://www.36kr.com/columns/starding","starding"));
        categoryBeenList.add(new CategoryBean("B轮后","http://www.36kr.com/columns/bplus","bplus"));
        categoryBeenList.add(new CategoryBean("大公司","http://www.36kr.com/columns/company","company"));
        categoryBeenList.add(new CategoryBean("资本","http://www.36kr.com/columns/deep","deep"));
        categoryBeenList.add(new CategoryBean("深度","http://www.36kr.com/columns/deep","deep"));
        categoryBeenList.add(new CategoryBean("研究","http://www.36kr.com/columns/research","research"));
        return categoryBeenList;
    };
}
