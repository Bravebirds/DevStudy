package com.mryu.devstudy.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.mryu.devstudy.R;

public class SwipeRefreshActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * 请求
     */
    private Button mBtnTest1;
    private Button mBtnTest2;
    private SwipeRefreshLayout mRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe_refresh);
        initView();
        setView();
    }
    private void initView() {
        mBtnTest1 = (Button) findViewById(R.id.btn_test1);
        mBtnTest2 = (Button) findViewById(R.id.btn_test2);
        mRefresh = (SwipeRefreshLayout) findViewById(R.id.refresh);
    }

    private void setView() {
        mBtnTest1.setOnClickListener(this);
        mBtnTest2.setOnClickListener(this);
        mRefresh.setOnClickListener(this);
        mRefresh.setRefreshing(true);
        mRefresh.setColorSchemeResources(R.color.colorHotpink,R.color.colorChocolate);
        // 每次冷启动APP时自动刷新下
        new Thread(){
            @Override
            public void run() {
                try {
                    sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                mRefresh.setRefreshing(false);
            }
            // 获取数据成功
        }.start();

        mRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // 下拉触发

                // 获取数据
                new Thread(){
                    @Override
                    public void run() {
                        try {
                            sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        mRefresh.setRefreshing(false);
                    }
                    // 获取数据成功
                }.start();

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.btn_test1:
                break;
            case R.id.btn_test2:
                break;
            case R.id.refresh:
                break;
        }
    }
}
