package com.mryu.devstudy.activity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.mryu.devstudy.R;
import com.mryu.devstudy.fragment.BookFragment;
import com.mryu.devstudy.fragment.GameFragment;
import com.mryu.devstudy.fragment.RecomFragment;
import com.mryu.devstudy.fragment.NewPeopleFragment;
import com.mryu.devstudy.fragment.FriendsFragment;
import com.mryu.devstudy.fragment.VideoFragment;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class TestHomeActivity extends AppCompatActivity {
    private ViewPager mHomeViewpage;
    private TabLayout mHomeTabs;

    private List<Fragment> fragments;
    private List<String> titles;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initView();
        setView();
    }

    private void setView() {
        mHomeViewpage.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                return titles.get(position);
            }
        });
        mHomeTabs.setupWithViewPager(mHomeViewpage);
    }

    private void initView() {
        mHomeViewpage = (ViewPager) findViewById(R.id.home_viewpage);
        fragments = new ArrayList<>();
        fragments.add(new RecomFragment());
        fragments.add(new FriendsFragment());
        fragments.add(new NewPeopleFragment());
        fragments.add(new GameFragment());
        fragments.add(new VideoFragment());
        fragments.add(new BookFragment());

        mHomeTabs = (TabLayout) findViewById(R.id.home_tabs);
        titles = new ArrayList<>();
        titles.add("热门");
        titles.add("交友");
        titles.add("新人");
        titles.add("游戏");
        titles.add("视频");
        titles.add("书籍");
    }

    /**
     * 重新返回键功能，将返回键功能替换成home功能
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
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
