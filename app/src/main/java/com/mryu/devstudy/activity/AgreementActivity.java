package com.mryu.devstudy.activity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.mryu.devstudy.R;

public class AgreementActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView mBackpage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agreement);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        initView();
        setView();
    }

    private void setView() {
        mBackpage.setOnClickListener(this);
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
}
