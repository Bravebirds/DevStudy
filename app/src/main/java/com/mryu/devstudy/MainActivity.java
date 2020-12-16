package com.mryu.devstudy;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.mryu.devstudy.fragment.FindFragment;
import com.mryu.devstudy.fragment.HomeFragment;
import com.mryu.devstudy.fragment.MySettingFragment;
import com.mryu.devstudy.fragment.VideoFragment;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class MainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {

    private static final String TAG =  "MainActivity";
    private ViewPager mViewpager;
    /**
     * 首页
     */
    private RadioButton mHomeTab;
    /**
     * 发现
     */
    private RadioButton mFindTab;
    /**
     * 消息
     */
    private RadioButton mVideoTab;
    /**
     * 我
     */
    private RadioButton mMyTab;
    private RadioGroup mRadioGroupVp;
    private List<Fragment> fragments;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        initView();
        setView();
    }

    private void initView() {
        mViewpager = (ViewPager) findViewById(R.id.viewpager);
        mHomeTab = (RadioButton) findViewById(R.id.home_tab);
        mFindTab = (RadioButton) findViewById(R.id.find_tab);
        mVideoTab = (RadioButton) findViewById(R.id.video_tab);
        mMyTab = (RadioButton) findViewById(R.id.my_tab);
        mRadioGroupVp = (RadioGroup) findViewById(R.id.radio_group_vp);
        mRadioGroupVp.check(R.id.home_tab);
        mRadioGroupVp.setOnCheckedChangeListener(this);
        fragments = new ArrayList<>();
        fragments.add(new HomeFragment());
        fragments.add(new FindFragment());
        fragments.add(new VideoFragment());
        fragments.add(new MySettingFragment());
    }


    private void setView() {
        mViewpager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }
        });
        mViewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            /**
             * ViewPage 事件监听
             * @param position
             * @param positionOffset
             * @param positionOffsetPixels
             */
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        mRadioGroupVp.check(R.id.home_tab);
                        break;
                    case 1:
                        mRadioGroupVp.check(R.id.find_tab);
                        break;
                    case 2:
                        mRadioGroupVp.check(R.id.video_tab);
                        break;
                    case 3:
                        mRadioGroupVp.check(R.id.my_tab);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
        /**
         * 点击底部TabLayout跳转对应的fragment
         */
        switch (checkedId){
            case R.id.home_tab:
                mViewpager.setCurrentItem(0);
                break;
            case R.id.find_tab:
                mViewpager.setCurrentItem(1);
                break;
            case R.id.video_tab:
                mViewpager.setCurrentItem(2);
                break;
            case R.id.my_tab:
                mViewpager.setCurrentItem(3);
                break;
            default:
                mViewpager.setCurrentItem(0);
                break;
        }
    }

    /**
     * 重写返回键功能
     */
    private Long mExitTime = 0L;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (System.currentTimeMillis() - mExitTime > 2000) {
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                mExitTime = System.currentTimeMillis();
            } else {
                finish();
//                System.exit(0);  启用的话会黑屏
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
