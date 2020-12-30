package com.mryu.devstudy.fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.mryu.devstudy.R;
import androidx.fragment.app.Fragment;

public class HomeFragment extends Fragment  {
    private static final String TAG = "HomeFragment";
    final String url = "https://www.wanandroid.com/banner/json";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_home_fragment, null);
        return view;
    }

}
