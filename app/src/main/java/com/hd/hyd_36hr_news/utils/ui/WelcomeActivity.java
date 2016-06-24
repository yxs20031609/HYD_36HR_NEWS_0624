package com.hd.hyd_36hr_news.utils.ui;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;


import com.hd.hyd_36hr_news.R;

public class WelcomeActivity extends BaseActivity {


    @Override
    protected int getLayout() {
        return R.layout.activity_welcome;
    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
           switch (msg.what){
               case 0:
                   Intent intent = new Intent(WelcomeActivity.this,HomeActivity.class);
                   startActivity(intent);
                   finish();
                   break;
           }
        }
    };

    @Override
    protected void initView() {
        handler.sendEmptyMessageDelayed(0,3000);
    }

    @Override
    protected void initVarible() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void bindData() {

    }
}
