package com.hd.hyd_36hr_news.utils.entity;

/**
 * Created by Administrator on 2016/6/16 0016.
 */
public class MessageEvent {

    private CategoryBean categoryBean;

    public MessageEvent(CategoryBean categoryBean) {
        this.categoryBean = categoryBean;
    }

    public CategoryBean getCategoryBean() {
        return categoryBean;
    }

    public void setCategoryBean(CategoryBean categoryBean) {
        this.categoryBean = categoryBean;
    }
}
