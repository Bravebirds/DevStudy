package com.mryu.devstudy.activity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.mryu.devstudy.R;
import com.mryu.devstudy.entity.HitokotoEntity;
import com.mryu.devstudy.utils.NetworkUtils;
import com.mryu.devstudy.utils.ToastUtils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * FileName： AdvertisActivity.java
 * Author : YuYanQing5920
 * Desc: 网络重试
 * Date： 2020-12-19
 */

public class AdvertisActivity extends AppCompatActivity implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {
    private static final String TAG = "AdvertisActivity";
    private static final String NetErrorText = "与服务器失去连接，点击重新连接或检查网络。";

    /**
     * 与服务器失去连接，点击重新连接或检查网络。
     */
    private SwipeRefreshLayout mRefresh;
    private LinearLayout mNetworkError;
    /**
     * 与服务器失去连接，点击重新连接或检查网络。
     */
    private TextView mTvError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advertis);
        initView();
        setView();
        initData();
    }

    private void initData() {
        mRefresh.setRefreshing(false);
        checkNetWork();
        }


    private void initView() {
        mRefresh = (SwipeRefreshLayout) findViewById(R.id.refresh);
        mTvError = (TextView) findViewById(R.id.tv_error);
        mNetworkError = (LinearLayout) findViewById(R.id.network_error);
        mTvError.setText(Html.fromHtml("<u>"+NetErrorText+"</u>"));
    }

    private void setView() {
        mRefresh.setOnClickListener(this);
        mRefresh.setOnRefreshListener(this);
        mNetworkError.setOnClickListener(this);
    }

    private void checkNetWork() {
        boolean isMobileNetwork = NetworkUtils.isMobileNetwork(this);
        boolean isWifiNetwork = NetworkUtils.isWifiNetwork(this);
        boolean isConnected = NetworkUtils.isConnected(this);
        Log.d(TAG, "NetWorkStatus Miblie is " + isMobileNetwork + ",Wifi is " + isWifiNetwork);
        if (isConnected == true) {
            mNetworkError.setVisibility(View.GONE);
        } else {
            mNetworkError.setVisibility(View.VISIBLE);
        }
    }

    private void showToast(String message, int resId) {
        ToastUtils.showKevinToast(this, message, resId);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.network_error:
                NetworkUtils.startNetworkSettingActivity(this);
                break;
            case R.id.refresh:
                break;
            case  R.id.tv_error:
                break;
        }
    }

    @Override
    public void onRefresh() {
        mRefresh.setColorSchemeResources(R.color.colorViolet, R.color.colorChartreuse, R.color.colorSalmon, R.color.colorDarkorange, R.color.colorBluePrimary);
        Thread refreshThread = new Thread() {
            @Override
            public void run() {
                try {
                    sleep(1500);
                    mRefresh.setRefreshing(false);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        refreshThread.start();
        if (mRefresh.isRefreshing() == true) {
            checkNetWork();
            Log.d(TAG, "mNetworkError：" + mNetworkError.getVisibility());
            if (mNetworkError.getVisibility() == View.VISIBLE) {
                showToast("网络异常，请检查网络后重试", R.drawable.icon_remind_yellow);
            }
        }
    }
}
