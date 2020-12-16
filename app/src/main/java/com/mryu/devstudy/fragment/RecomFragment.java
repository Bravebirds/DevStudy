package com.mryu.devstudy.fragment;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mryu.devstudy.R;

public class RecomFragment extends Fragment {
    /**
     * 热门推荐Fragment
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */

    @Override
    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_recom_fragment, container, false);
    }
}
