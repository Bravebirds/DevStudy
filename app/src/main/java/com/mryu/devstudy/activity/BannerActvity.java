package com.mryu.devstudy.activity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.mryu.devstudy.R;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoaderInterface;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;

public class BannerActvity extends AppCompatActivity implements OnBannerListener {
    private Banner banner;
    private ArrayList<String> list_path;
    private ArrayList<String> list_title;
    private Banner mMyBanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner);
        //初始化控件
        initView();
    }

    private void initView() {
        mMyBanner = (Banner) findViewById(R.id.my_banner);
        //放图片地址的集合
        list_path = new ArrayList<>();
        //放标题的集合
        list_title = new ArrayList<>();
        list_path.add("https://ww4.sinaimg.cn/large/006uZZy8jw1faic21363tj30ci08ct96.jpg");
        list_path.add("https://ww4.sinaimg.cn/large/006uZZy8jw1faic259ohaj30ci08c74r.jpg");
        list_path.add("https://ww4.sinaimg.cn/large/006uZZy8jw1faic2b16zuj30ci08cwf4.jpg");
        list_path.add("https://ww4.sinaimg.cn/large/006uZZy8jw1faic2e7vsaj30ci08cglz.jpg");
        list_title.add("好好学习");
        list_title.add("天天向上");
        list_title.add("热爱劳动");
        list_title.add("不搞对象");
        //设置内容样式  ,共有六种可以逐一体验使用
        mMyBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);
        //设置图片加载器
        mMyBanner.setImageLoader(new MyLoader());
        //设置图片网址或地址的集合
        mMyBanner.setImages(list_path);
        //设置轮播的动画效果,内含多种特效,点入方法内查找后内逐一体验
        mMyBanner.setBannerAnimation(Transformer.Default);
        //设置轮播图的的标题集合
        mMyBanner.setBannerTitles(list_title);
        //设置轮播间隔时间
        mMyBanner.setDelayTime(3000);
        //设置是否为自动轮播,默认是"是
        mMyBanner.isAutoPlay(true);
        //设置指示器的位置, 圆圈的  左  中  右
        mMyBanner.setIndicatorGravity(BannerConfig.LEFT)
                //以上内容都可写成链式布局,这是轮播图的监听.比较重要,方法在下面
                .setOnBannerListener(this)
                //必须最后调用的方法,启用轮播图
                .start();
    }

    //轮播图的监听方法
    @Override
    public void OnBannerClick(int position) {
        Log.i("tag", "你点了" + position + "张轮播图");
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    private class MyLoader implements ImageLoaderInterface {
        @Override
        public void displayImage(Context context, Object path, View imageView) {
            Glide.with(context).load(path).into((ImageView) imageView);
        }

        @Override
        public View createImageView(Context context) {
            return null;
        }
    }

}