package com.mryu.devstudy.fragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import com.mryu.devstudy.R;
import com.mryu.devstudy.utils.NetworkUtils;

import java.util.ArrayList;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

public class HomeFragment extends Fragment  {
    private static final String TAG = "HomeFragment";
    final String url = "https://acg.xydwz.cn/gqapi/gqapi.php";
    private Toolbar mToobar;
    private Button mRetryButton;
    private LinearLayout mRetryLinearLayout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_home_fragment, null);
        mToobar = (Toolbar) view.findViewById(R.id.toolbar);
        mRetryLinearLayout = (LinearLayout) view.findViewById(R.id.retry_linear);
        mRetryButton = (Button) view.findViewById(R.id.retry_button);
        initToobar();
        mHandlerWifi.sendEmptyMessageDelayed(0, 1000);
        return view;
    }

    //Wifi网络监测线程  然后在onCreate方法里面开启
    private Handler mHandlerWifi = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (getActivity() != null) {
                boolean isConnected = NetworkUtils.isConnected(getActivity());
                boolean isWifiProxy = NetworkUtils.isWifiProxy(getActivity());
                Log.d(TAG, "isConnected：" + isConnected + "  isWifiProxy：" + isWifiProxy);
                if (msg.what == 0) {
                    if (isConnected == false || isWifiProxy == true) {
                        mToobar.setVisibility(View.GONE);
                        mRetryLinearLayout.setVisibility(View.VISIBLE);
                    } else {
                        mToobar.setVisibility(View.VISIBLE);
                        mRetryLinearLayout.setVisibility(View.GONE);
                    }
                    sendEmptyMessageDelayed(0, 1000);
                }
            }
        }
    };

    private void initToobar() {
        ArrayList<String> titles = new ArrayList<>();
        titles.add("精选");
        titles.add("体育");
        titles.add("巴萨");
        titles.add("购物");
        titles.add("明星");
        titles.add("视屏");
        titles.add("健康");
        titles.add("健康");
        ArrayList<Fragment> fragments = new ArrayList<>();
    }

}
