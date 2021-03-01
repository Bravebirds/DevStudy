package com.mryu.devstudy.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.mryu.devstudy.MainActivity;
import com.mryu.devstudy.R;
import com.mryu.devstudy.entity.LocalAppInfo;
import com.mryu.devstudy.utils.ApkUtils;
import com.mryu.devstudy.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

public class SanAllActivity extends AppCompatActivity implements View.OnClickListener {
    private ListView mLvAppList;
    private AppAdapter mAppAdapter;
    public Handler mHandler = new Handler();
    private Toolbar mSanBack;
    private String TAG = "SanAllActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_san_all_app);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        initView();
        mAppAdapter = new AppAdapter();
        mLvAppList.setAdapter(mAppAdapter);
        initAppList();
    }

    private void initAppList() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                //扫描得到APP列表
                final List<LocalAppInfo> apkUtils = ApkUtils.scanLocalInstallAppList(SanAllActivity.this.getPackageManager());
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mAppAdapter.setData(apkUtils);
                    }
                });
            }
        }.start();
    }

    private void initView() {
        mSanBack = (Toolbar) findViewById(R.id.san_back);
        mLvAppList = (ListView) findViewById(R.id.lv_app_list);
        mSanBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.san_back:
                startActivity(new Intent(this, MainActivity.class));
                finish();
                break;
        }
    }

    class AppAdapter extends BaseAdapter {
        List<LocalAppInfo> localAppInfo = new ArrayList<LocalAppInfo>();
        public void setData(List<LocalAppInfo> localAppInfo) {
            this.localAppInfo = localAppInfo;
            notifyDataSetChanged();
        }

        public List<LocalAppInfo> getData() {
            return localAppInfo;
        }

        @Override
        public int getCount() {
            if (localAppInfo != null && localAppInfo.size() > 0) {
                return localAppInfo.size();
            }
            return 0;
        }

        @Override
        public Object getItem(int position) {
            if (localAppInfo != null && localAppInfo.size() > 0) {
                return localAppInfo.get(position);
            }
            return null;
        }

        @Override
        public long getItemId(int position) {
            startActivityForResult(new Intent(getApplicationContext(), MainActivity.class),1);
            return position;
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            ViewHolder mViewHolder;
            LocalAppInfo mlocalAppInfo = localAppInfo.get(position);
            if (view == null) {
                mViewHolder = new ViewHolder();
                view = LayoutInflater.from(getBaseContext()).inflate(R.layout.activity_appinfo_list, null);
                mViewHolder.mAppIcon = (ImageView) view.findViewById(R.id.iv_app_icon);
                mViewHolder.mPackageName = (TextView) view.findViewById(R.id.iv_package_name);
                view.setTag(mViewHolder);
            } else {
                mViewHolder = (ViewHolder) view.getTag();
            }
            mViewHolder.mAppIcon.setImageDrawable(mlocalAppInfo.getImage());
            mViewHolder.mPackageName.setText(mlocalAppInfo.getpackageName());
            return view;
        }

        class ViewHolder {
            ImageView mAppIcon;
            TextView mPackageName;
        }
    }
    /**
     * Toast调用
     *
     * @param message
     * @param resId
     */
    private void showToast(String message, int resId, double toastHight) {
        ToastUtils.showKevinToast(this, message, resId, toastHight);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == 2) {
            setResult(2);
            finish();
        }
    }
}
