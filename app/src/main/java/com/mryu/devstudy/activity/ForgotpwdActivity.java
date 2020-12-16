package com.mryu.devstudy.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.mryu.devstudy.R;

public class ForgotpwdActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView mBackpage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrievepwd);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        initView();
    }

    private void initView() {
        mBackpage = (TextView) findViewById(R.id.backpage);
        mBackpage.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.backpage:
                startActivity(new Intent(ForgotpwdActivity.this,LoginActivity.class));
                finish();
                break;
        }
    }
}
