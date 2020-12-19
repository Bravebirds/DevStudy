package com.mryu.devstudy.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.mryu.devstudy.R;

public class DialogRuleActivity extends AppCompatActivity implements View.OnClickListener {
    /**
     * 暂不使用
     */
    private TextView mTvCancel;
    /**
     * 同意并继续
     */
    private TextView mTvOk;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_rule);
        initView();
    }

    private void initView() {
        mTvCancel = (TextView) findViewById(R.id.tv_cancel);
        mTvCancel.setOnClickListener(this);
        mTvOk = (TextView) findViewById(R.id.tv_ok);
        mTvOk.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.tv_cancel:
                break;
            case R.id.tv_ok:
                break;
        }
    }
}
