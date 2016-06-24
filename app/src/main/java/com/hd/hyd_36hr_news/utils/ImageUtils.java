package com.hd.hyd_36hr_news.utils;

/**
 * Created by Administrator on 2016/6/17 0017.
 */
public class ImageUtils {

    public static String getCutImageURL(String url){
        if (url.contains("!")){
            String []urlString = url.split("!");
            return urlString[0];
        }
        return url;
    }
}
