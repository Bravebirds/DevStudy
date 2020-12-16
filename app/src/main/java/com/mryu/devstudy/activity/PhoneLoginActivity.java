package com.mryu.devstudy.activity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;

import com.mryu.devstudy.R;
import com.mryu.devstudy.utils.ToastUtils;

import androidx.appcompat.app.AppCompatActivity;

/**
 * 待接入极光认证
 */
public class PhoneLoginActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "PhoneLoginActivity";
    private ImageView mBackpage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phonelogin);
        initView();
    }

    private void initView() {
        mBackpage = (ImageView) findViewById(R.id.backpage);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.backpage:
                finish();
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            finish();
        }
        return true;
    }

    private void showToast(String message, int resId) {
        ToastUtils.showKevinToast(this, message, resId);
    }


}
