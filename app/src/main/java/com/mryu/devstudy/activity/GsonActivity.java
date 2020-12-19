package com.mryu.devstudy.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.mryu.devstudy.R;
import com.mryu.devstudy.entity.HitokotoEntity;
import com.mryu.devstudy.utils.GsonUtils;

import org.json.JSONObject;

import java.util.Map;

public class GsonActivity extends AppCompatActivity implements View.OnClickListener {
    private final static  String TAG ="GsonActivity";
    private TextView mTvTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gson);
        initView();
        setData();
    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            if (msg.what ==001) {
                mTvTest.setText(msg.obj+"");
            }
        }
    };

    private void setData() {
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url = "https://international.v1.hitokoto.cn/";
        GsonUtils.get(url, getApplicationContext(), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                Log.d(TAG, jsonObject.toString());
                Gson gson = new Gson();
                HitokotoEntity hitokotoEntity = gson.fromJson(jsonObject.toString(),HitokotoEntity.class);
                Message message = new Message();
                message.obj = hitokotoEntity.getHitokoto();
                message.what =001;
                handler.sendMessage(message);
            }
        });
    }

    private void initView() {
        mTvTest = (TextView) findViewById(R.id.tv_test);
        mTvTest.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.tv_test:
                break;
        }
    }
}
