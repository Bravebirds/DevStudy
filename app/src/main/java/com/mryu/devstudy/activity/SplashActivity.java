package com.mryu.devstudy.activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import com.mryu.devstudy.MainActivity;
import com.mryu.devstudy.R;
import com.mryu.devstudy.qqlogin.QQLoginManager;
import com.mryu.devstudy.utils.NetworkUtils;
import com.mryu.devstudy.utils.RepeatClickUtils;
import com.mryu.devstudy.utils.ToastUtils;
import com.tencent.connect.UserInfo;
import com.tencent.tauth.IUiListener;
import org.json.JSONObject;
import java.io.IOException;
import androidx.appcompat.app.AppCompatActivity;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import static java.lang.Thread.sleep;
public class SplashActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "SplashActivity";
    private IUiListener userInfoListener; //获取用户信息监听器
    private UserInfo userInfo; //qq用户信息
    private boolean timeFlag = false;
    private ImageView mSplashImage;
    private TextView mSkipTimeText;
    final String url = "https://acg.xydwz.cn/gqapi/gqapi.php";
    private TextView mAuthourText;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);  //无title
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);  //全屏
        setContentView(R.layout.activity_splash);
        initView();
        initData();
        setListenr();
        SetImage(url);
    }

    private void initData() {
        // 初始化appid
        QQLoginManager.init(this, "1110529440");
        // 3s倒计时
        downTimer();
        // 登录态检查
        checkQQLoginStatus();
    }

    private void initView() {
        mSplashImage = (ImageView) findViewById(R.id.splash_image);
        mSkipTimeText = (TextView) findViewById(R.id.skip_time_text);
        mAuthourText = (TextView) findViewById(R.id.authour_text);
        mAuthourText.getBackground().setAlpha(90);
        mSkipTimeText.getBackground().setAlpha(50);
        mAuthourText.setOnClickListener(this);
    }

    private void setListenr() {
        mSplashImage.setOnClickListener(this);
        mSkipTimeText.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (RepeatClickUtils.isFastClick()) {
            switch (v.getId()) {
                default:
                    break;
                case R.id.splash_image:
                    break;
                case R.id.skip_time_text:
                    timeFlag = true;
                    startActivity();
                    Log.d(TAG, "timeFlag is " + timeFlag);
                    break;
                case R.id.authour_text:
                    timeFlag = true;
                    startActivity(new Intent(SplashActivity.this,WebActivity.class));
                    finish();
                    break;
            }
        }else{
            showToast(getString(R.string.repeat_click),R.drawable.icon_waring_yellow,0.03);
        }
    }

    /**
     * 检查QQ登录态
     */
    private void checkQQLoginStatus() {
        boolean isConnected = NetworkUtils.isConnected(this);
        boolean isWifiProxy = NetworkUtils.isWifiProxy(this);
        sharedPreferences = getSharedPreferences("initAppInfoMation", Context.MODE_PRIVATE);
        SharedPreferences.Editor writeFile = sharedPreferences.edit();
        Log.d(TAG,"isConnected：" + isConnected + "  isWifiProxy："+isWifiProxy);
        QQLoginManager.checkLogin(new QQLoginManager.QQCheckCallback() {
            @Override
            public void onCallback(boolean login, JSONObject json) {
                if (login == true) {
                    writeFile.putInt("QQLoginStatus", 1);
                    writeFile.commit();
                } else {
                    writeFile.putInt("QQLoginStatus", 0);
                    writeFile.commit();
                }
            }
        });
    }

    /**
     * 避免网络不好的情况下会卡住
     */
    private void startActivity() {
        sharedPreferences = getSharedPreferences("initAppInfoMation", Context.MODE_PRIVATE);
        SharedPreferences.Editor writeFile = sharedPreferences.edit();
        int qqLoginStatus = sharedPreferences.getInt("QQLoginStatus", 0);
        if (qqLoginStatus ==0) {
            startActivity(new Intent(this,LoginActivity.class));
        }else{
            startActivity(new Intent(this,MainActivity.class));
        }
    }

    private void SetImage(String url) {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                byte[] Picture_bt = response.body().bytes();
                Message message = mImageHandler.obtainMessage();
                message.obj = Picture_bt;
                message.what = 1;
                mImageHandler.sendMessage(message);
            }
        });
    }

    Handler mImageHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                //通过message，拿到字节数组
                byte[] Pi = (byte[]) msg.obj;
                //使用BitmapFactory工厂，把字节数组转化为bitmap
                Bitmap bitmap = BitmapFactory.decodeByteArray(Pi, 0, Pi.length);
                //显示图片
                mSplashImage.setImageBitmap(bitmap);
                //mAuthourText.setVisibility(View.VISIBLE);
            }
        }
    };

    private void downTimer() {
        /**
         * 倒计时线程
         */
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "timeFlag is " + timeFlag);
                for (int i = 3; i >= 0 && timeFlag == false; i--) {
                    try {
                        sleep(1000);
                        Message msg = Message.obtain();
                        msg.what = i;
                        Log.d(TAG, "mTimeHandler：" + mTimeHandler);
                        if (mTimeHandler == null) {
                            break;
                        } else {
                            mTimeHandler.sendMessage(msg);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        thread.start();
    }

    // 倒计时
    Handler mTimeHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Log.d(TAG, "mSkipTimeText ClickableStatus is " + mSkipTimeText.isClickable());
            if (msg.what > 0) {
                mSkipTimeText.setVisibility(View.VISIBLE);
                mSkipTimeText.setText("跳过广告 ( " + msg.what + " )");
                SetImage(url);
            } else if (msg.what == 0 && timeFlag==false) {   //挖个坑必须的加上timeFlag状态不然与点击时重复进入
                mSkipTimeText.setText("即将进入• • • ");
                startActivity();
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timeFlag = true;
    }

    private void showToast(String message, int resId ,double toastHight) {
        ToastUtils.showKevinToast(this, message, resId,toastHight);
    }

    /**
     * 重写返回功能
     * @param event
     * @return
     */
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            finish();
            return true;
        } else {
            return super.dispatchKeyEvent(event);
        }
    }

}
