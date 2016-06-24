package com.hd.hyd_36hr_news.utils.ui;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.widget.AdapterView;
import android.widget.RadioGroup;
import android.widget.TimePicker;
import android.widget.Toast;

import com.hd.hyd_36hr_news.R;
import com.hd.hyd_36hr_news.equity.EquityFragment;
import com.hd.hyd_36hr_news.find.FindFragment;
import com.hd.hyd_36hr_news.my.MyFragment;
import com.hd.hyd_36hr_news.news.NewsFragment;
import com.hd.hyd_36hr_news.utils.adapter.MyFragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener,ViewPager.OnPageChangeListener {
    private RadioGroup tabRadioGroup;
    private ViewPager viewPager;
    private List<Fragment> fragmentList;
    private MyFragmentStatePagerAdapter fragmentStatePagerAdapter;
    private DrawerLayout drawerLayout;
    @Override
    protected int getLayout() {
        return R.layout.activity_home;
    }

    @Override
    protected void initView() {
        drawerLayout = (DrawerLayout) this.findViewById(R.id.main_content);
        tabRadioGroup = (RadioGroup) this.findViewById(R.id.id_tab);
        viewPager = (ViewPager) this.findViewById(R.id.viewpager);
        viewPager.setOffscreenPageLimit(3);//侧滑超过3个fragment，字不重叠
        fragmentList = new ArrayList<Fragment>();
        fragmentList.add(new NewsFragment());
        fragmentList.add(new EquityFragment());
        fragmentList.add(new FindFragment());
        fragmentList.add(new MyFragment());
        fragmentStatePagerAdapter = new MyFragmentStatePagerAdapter(getSupportFragmentManager());
        fragmentStatePagerAdapter.setFragmentList(fragmentList);
        viewPager.setAdapter(fragmentStatePagerAdapter);


    }

    @Override
    protected void initVarible() {
        tabRadioGroup.check(R.id.radiogroup_news);
    }

    @Override
    protected void initListener() {
        viewPager.setOnPageChangeListener(this);
        tabRadioGroup.setOnCheckedChangeListener(this);
    }

    public void closeDrawrLayout(){
        drawerLayout.closeDrawer(Gravity.LEFT);
    }

    public void openDrawrLayout(){
        drawerLayout.openDrawer(Gravity.LEFT);
    }

    @Override
    protected void bindData() {

    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        switch (i){
            case R.id.radiogroup_news:
                viewPager.setCurrentItem(0,true);
                break;
            case R.id.radiogroup_equity:
                viewPager.setCurrentItem(1,true);
                break;
            case R.id.radiogroup_find:
                viewPager.setCurrentItem(2,true);
                break;
            case R.id.radiogroup_my:
                viewPager.setCurrentItem(3,true);
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        
    }

    @Override
    public void onPageSelected(int position) {

        switch (position){
            case 0:
                tabRadioGroup.check(R.id.radiogroup_news);
                break;
            case 1:
                tabRadioGroup.check(R.id.radiogroup_equity);
                break;
            case 2:
                tabRadioGroup.check(R.id.radiogroup_find);
                break;
            case 3:
                tabRadioGroup.check(R.id.radiogroup_my);
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
