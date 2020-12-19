package com.mryu.devstudy.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.mryu.devstudy.R;

public class TestActivity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

//获取SharedPreferences对象
        Context ctx = this;
        SharedPreferences sp = ctx.getSharedPreferences("SP", MODE_PRIVATE);
//存入数据
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("STRING_KEY", "string");
        editor.putInt("INT_KEY", 0);
        editor.putBoolean("BOOLEAN_KEY", true);
        editor.commit();

//返回STRING_KEY的值
        Log.d("SP", sp.getString("STRING_KEY", "none"));
//如果NOT_EXIST不存在，则返回值为"none"
        Log.d("SP", sp.getString("NOT_EXIST", "none"));
    }
}