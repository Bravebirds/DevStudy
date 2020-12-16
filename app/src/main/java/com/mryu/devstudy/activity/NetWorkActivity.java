package com.mryu.devstudy.activity;

import android.Manifest;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.mryu.devstudy.R;
import com.tbruyelle.rxpermissions3.RxPermissions;

import java.io.File;

public class NetWorkActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "NetWorkActivity";
    /**
     * 获取权限
     */
    private Button mGetPermission;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_net_work);
        initView();
        setView();
    }

    private void setView() {
        File file = new File(getFilesDir().getAbsolutePath(),"text.txt");
        if (file.exists()){
            Log.d(TAG,"存在");
        }else{
            Log.d(TAG,"不存在");

        }
    }

    private void initView() {
        mGetPermission = (Button) findViewById(R.id.get_permission);
        mGetPermission.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.get_permission:
                break;
        }
    }
}
