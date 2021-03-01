package com.mryu.devstudy.fragment;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.mryu.devstudy.R;
import com.mryu.devstudy.activity.SanAllActivity;
import com.mryu.devstudy.utils.ToastUtils;

public class HomeFragment extends Fragment {
    private static final String TAG = "HomeFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_home_fragment, null);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        LinearLayout mAddPackage = getActivity().findViewById(R.id.add_package);
        ImageView mSetAppIcon = getActivity().findViewById(R.id.set_app_icon);
        TextView mSetPackageName = getActivity().findViewById(R.id.set_package_name);
        mAddPackage.setOnClickListener(v -> {
            switch (v.getId()) {
                default:
                    break;
                case R.id.add_package:
                    Intent san_intent = new Intent(getActivity(), SanAllActivity.class);
                    startActivity(san_intent);
                    break;
            }
        });
    }

    /**
     * Toast调用
     *
     * @param message
     * @param resId
     */
    private void showToast(String message, int resId, double toastHight) {
        ToastUtils.showKevinToast(getActivity(), message, resId, toastHight);
    }

}
