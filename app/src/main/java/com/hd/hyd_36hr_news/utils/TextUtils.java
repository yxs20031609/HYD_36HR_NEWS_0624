package com.hd.hyd_36hr_news.utils;

/**
 * Created by Administrator on 2016/6/17 0017.
 */
public class TextUtils {

    public static String getArticleId(String href){
        String temp = href.substring(href.lastIndexOf("/")+1);
        if (temp.contains(".")){
            String []Id = temp.split("\\.");
            return Id[0];
        }
        return href;
    }

    public static String getArticleHref(String href){
        String links = "http://www.36kr.com";
        if (href != null&& !href.contains("http://www.36kr.com")){
            return links + href;
        }
        return href;
    }
}
