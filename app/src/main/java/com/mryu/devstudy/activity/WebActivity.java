package com.mryu.devstudy.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.mryu.devstudy.MainActivity;
import com.mryu.devstudy.R;
import com.mryu.devstudy.qqlogin.QQLoginManager;
import com.mryu.devstudy.utils.NetworkUtils;
import com.mryu.devstudy.utils.ToastUtils;

import org.json.JSONObject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class WebActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "SplashActivity";
    private static final String url = "http://tv.hzwdd.cn/";
    private WebView mWebpage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webpage);
        initView();
        setListener(url);
    }

    /**
     * 检查QQ登录态
     */
    private void checkQQLoginStatus() {
        boolean isConnected = NetworkUtils.isConnected(this);
        Log.d(TAG, "isConnected " + isConnected);
        if (isConnected == true) {
            QQLoginManager.checkLogin(new QQLoginManager.QQCheckCallback() {
                @Override
                public void onCallback(boolean login, JSONObject json) {
                    if (login == true) {
                        startActivity(new Intent(WebActivity.this, MainActivity.class));
                        finish();
                    } else {
                        startActivity(new Intent(WebActivity.this, LoginActivity.class));
                        finish();
                    }
                }

            });
        } else {
            Log.d(TAG, "NetWork is Eroor");
            showToast(getString(R.string.network_error_login), R.drawable.icon_waring_gray);
            startActivity(new Intent(WebActivity.this, LoginActivity.class));
            finish();
        }
    }

    private void showToast(String message, int resId) {
        ToastUtils.showKevinToast(this, message, resId);
    }

    private void initView() {
        mWebpage = (WebView) findViewById(R.id.webpage);
        mWebpage.setOnClickListener(this);
    }

    private void setListener(String url) {
        mWebpage.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;// 返回false
            }
        });
        WebSettings webSettings = mWebpage.getSettings();
        // 让WebView能够执行javaScript
        webSettings.setJavaScriptEnabled(true);
        // 让JavaScript可以自动打开windows
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        // 设置缓存
        webSettings.setAppCacheEnabled(true);
        // 设置缓存模式,一共有四种模式
        webSettings.setCacheMode(webSettings.LOAD_CACHE_ELSE_NETWORK);
        // 设置缓存路径
//        webSettings.setAppCachePath("");
        // 支持缩放(适配到当前屏幕)
        webSettings.setSupportZoom(true);
        // 将图片调整到合适的大小
        webSettings.setUseWideViewPort(true);
        // 支持内容重新布局,一共有四种方式
        // 默认的是NARROW_COLUMNS
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        // 设置可以被显示的屏幕控制
        webSettings.setDisplayZoomControls(true);
        // 设置默认字体大小
        webSettings.setDefaultFontSize(12);

        // 设置WebView属性，能够执行Javascript脚本
        // mWebView.getSettings().setJavaScriptEnabled(true);
        //3、 加载需要显示的网页
        mWebpage.loadUrl(url);
        ///4、设置响应超链接，在安卓5.0系统，不使用下面语句超链接也是正常的，但在MIUI中安卓4.4.4中需要使用下面这条语句，才能响应超链接
        // mWebView.setWebViewClient(new HelloWebViewClient());

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.webpage:
                break;
        }
    }


    /**
     * 重写返回键功能
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) ) {
            if (mWebpage.canGoBack()) {
                mWebpage.goBack(); //goBack()表示返回WebView的上一页面
                return true;
            }else {
                checkQQLoginStatus();
                return true;
            }
        }
        return false;
    }

}
