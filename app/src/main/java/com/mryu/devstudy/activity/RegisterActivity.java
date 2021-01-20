package com.mryu.devstudy.activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ToggleButton;
import com.mryu.devstudy.R;
import com.mryu.devstudy.utils.RepeatClickUtils;
import com.mryu.devstudy.utils.ToastUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import org.json.JSONException;
import org.json.JSONObject;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import okhttp3.Call;
import static java.lang.Thread.sleep;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener, TextWatcher, View.OnFocusChangeListener {
    private static final String TAG = "RegisterActivity";
    private static final String RegistUrl = "https://www.wanandroid.com/user/registerV2";
    private Toolbar mRegisterBack;
    /**
     * 请输入用户名
     */
    private EditText mUserNameEdit;
    /**
     * 请输入密码
     */
    private EditText mPasswordEdit;
    /**
     * 注册
     */
    private Button mRegisterBtn;
    private ToggleButton mShowPassword;
    /**
     * 请输入验证码
     */
    private EditText mVerifiEdit;
    /**
     * 获取验证码
     */
    private Button mGetVerifi;
    private boolean timeFlag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
        setListener();
    }

    private void initView() {
        mRegisterBack = (Toolbar) findViewById(R.id.register_back);
        mUserNameEdit = (EditText) findViewById(R.id.username_edit);
        mPasswordEdit = (EditText) findViewById(R.id.password_edit);
        mRegisterBtn = (Button) findViewById(R.id.register_btn);
        mShowPassword = (ToggleButton) findViewById(R.id.show_password);
        mVerifiEdit = (EditText) findViewById(R.id.verifi_edit);
        mGetVerifi = (Button) findViewById(R.id.get_verifi);
    }
    private void setListener() {
        mRegisterBack.setOnClickListener(this);
        mUserNameEdit.setOnClickListener(this);
        mUserNameEdit.addTextChangedListener(this);
        mPasswordEdit.setOnClickListener(this);
        mPasswordEdit.addTextChangedListener(this);
        mRegisterBtn.setOnClickListener(this);
        mShowPassword.setOnClickListener(this);
        mVerifiEdit.setOnClickListener(this);
        mGetVerifi.setOnClickListener(this);
        mVerifiEdit.addTextChangedListener(this);
        mUserNameEdit.setOnFocusChangeListener(this);
        mPasswordEdit.setOnFocusChangeListener(this);
        mVerifiEdit.setOnFocusChangeListener(this);
        mRegisterBtn.setOnClickListener(this);
        mShowPassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.d(TAG, "Toggle Button isChecked ：" + isChecked);
                if (isChecked) {
                    //如果选中，显示密码
                    mPasswordEdit.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    // 光标位置
                    mPasswordEdit.setSelection(mPasswordEdit.getText().length());
                    mShowPassword.setBackgroundResource(R.mipmap.icon_show_password);
                    mShowPassword.setTextOff("");
                } else {
                    //否则隐藏密码
                    mPasswordEdit.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    // 光标位置
                    mPasswordEdit.setSelection(mPasswordEdit.getText().length());
                    mShowPassword.setBackgroundResource(R.mipmap.icon_close_password);
                    mShowPassword.setTextOn("");
                }
            }
        });

    }

    @Override
    public void onClick(View v) {
        if (RepeatClickUtils.isFastClick()) {
            switch (v.getId()) {
                default:
                    break;
                case R.id.register_back:
                    startActivity(new Intent(this, LoginActivity.class));
                    timeFlag = true;
                    finish();
                    showToast("" + getString(R.string.disregister), R.drawable.icon_waring_yellow, 0.03);
                    break;
                case R.id.get_verifi:
                    downTimer();
                    mVerifiEdit.setText("2020");
                    mGetVerifi.setEnabled(false);
                    mVerifiEdit.setSelection(mVerifiEdit.getText().length());
                    mGetVerifi.setBackgroundResource(R.drawable.shape_send_btn_unselect);
                    showToast("自动填充万能码", R.drawable.icon_security_green, 0.03);
                    break;
                case R.id.register_btn:
                    RegisterId();
            }
        }
    }

    private void RegisterId() {
        String accountText = mUserNameEdit.getText().toString();
        String passwordText = mPasswordEdit.getText().toString();
        String verifiText = mVerifiEdit.getText().toString();
        Log.d(TAG, "UserName：" + accountText.isEmpty() + " PassWord：" + passwordText.isEmpty() + " verifiText：" + verifiText);
        OkHttpUtils
                .post()
                .url(RegistUrl)
                .addParams("username", accountText)
                .addParams("repassword", passwordText)
                .addParams("password", passwordText)
                .addParams("verifyCode", verifiText)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        showToast("服务器请求失败", R.drawable.icon_security_green, 0.03);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Log.d(TAG, "JSON" + jsonObject.toString());
                            int errorCode = jsonObject.getInt("errorCode");
                            String errorMsg = jsonObject.getString("errorMsg");
                            String data = jsonObject.getString("data");
                            if (errorCode == 0) {
                                Log.v(TAG, "register data ：" + data);
                                showToast("注册成功", R.drawable.icon_security_green, 0.03);
                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                showToast("" + errorMsg, R.drawable.icon_waring_yellow, 0.03);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            showToast("注册失败", R.drawable.icon_waring_yellow, 0.03);
                        }
                    }
                });
    }

    private void downTimer() {
        /**
         * 倒计时线程
         */
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "timeFlag is " + timeFlag);
                for (int i = 60; i >= 0 && timeFlag == false; i--) {
                    try {
                        sleep(1000);
                        Message msg = Message.obtain();
                        msg.what = i;
                        Log.d(TAG, "mGetVerifiHandler：" + mGetVerifiHandler);
                        if (mGetVerifiHandler == null) {
                            break;
                        } else {
                            mGetVerifiHandler.sendMessage(msg);
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
    Handler mGetVerifiHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what > 0) {
                mGetVerifi.setText(" ( " + msg.what + " s)");
            } else if (msg.what == 0 && timeFlag == false) {   //挖个坑必须的加上timeFlag状态不然与点击时重复进入
                mGetVerifi.setText(getString(R.string.get_verifi_tint));
                mGetVerifi.setEnabled(true);
            }
        }
    };

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void afterTextChanged(Editable s) {

        /**
         * 判断UserName及Pwd字段是否均已输入对应改变入口可点击状态
         */
        String accountText = mUserNameEdit.getText().toString();
        String passwordText = mPasswordEdit.getText().toString();
        String verifiText = mVerifiEdit.getText().toString();
        Log.d(TAG, "UserName：" + accountText.isEmpty() + ",PassWord：" + passwordText.isEmpty() + ",verifiText：" + verifiText);
        int maxUserNameLength = 16;
        int maxPwdLength = 20;
        // 用户名长度限制
        if (accountText.length() > maxUserNameLength) {
            String newStr = accountText.substring(0, maxUserNameLength);
            mUserNameEdit.setText(newStr);
            mUserNameEdit.setSelection(mUserNameEdit.getText().length());
            try {
                sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            showToast("用户名仅支持" + maxUserNameLength + "位输入", R.drawable.icon_waring_yellow, 0.03);
        }

        // 密码长度限制
        if (passwordText.length() > maxPwdLength) {
            String newStr = passwordText.substring(0, maxPwdLength);
            mPasswordEdit.setText(newStr);
            mPasswordEdit.setSelection(mPasswordEdit.getText().length());
            try {
                sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            HideKeyInput();
            showToast("密码仅支持" + maxPwdLength + "位输入", R.drawable.icon_waring_yellow, 0.03);
        }
        if ((passwordText.equals("") == false)) {
            mShowPassword.setVisibility(View.VISIBLE);
        } else {
            mShowPassword.setVisibility(View.GONE);
        }

    }

    @Override
    public void onFocusChange(View view, boolean hasFocus) {
        switch (view.getId()) {
            default:
            case R.id.username_edit:
                if (hasFocus) {
                    mUserNameEdit.setHint("");
                } else {
                    mUserNameEdit.setHint("" + getString(R.string.account_tint));
                    Log.d(TAG, "焦点事件已从UserName输入框中移除！！！");
                }
                break;
            case R.id.password_edit:
                if (hasFocus) {
                    mPasswordEdit.setHint("");
                } else {
                    mPasswordEdit.setHint("" + getString(R.string.password_edit_hint));
                }
                break;
            case R.id.verifi_edit:
                if (hasFocus) {
                    mVerifiEdit.setHint("");
                } else {
                    mVerifiEdit.setHint("" + getString(R.string.verifi_tint));
                }
                break;
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

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

    /**
     * 重写返回键功能
     */
    private Long mExitTime = 0L;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            startActivity(new Intent(this, LoginActivity.class));
            timeFlag = true;
            finish();
            showToast("" + getString(R.string.disregister), R.drawable.icon_waring_yellow, 0.03);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    /**
     * Date 2020-12-16
     * 修复华为等机型的安全键盘唤起时将toast遮挡以及点击非键盘区域无法收起键盘
     * 增加友好性
     *
     * @param ev
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View view = getCurrentFocus();
            if (isHideInput(view, ev)) {
                HideSoftInput(view.getWindowToken());
                view.clearFocus();
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 判定是否需要隐藏
     */
    private boolean isHideInput(View v, MotionEvent ev) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0], top = l[1], bottom = top + v.getHeight(), right = left + v.getWidth();
            if (ev.getX() > left && ev.getX() < right && ev.getY() > top && ev.getY() < bottom) {
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    /**
     * 隐藏软键盘
     */
    private void HideSoftInput(IBinder token) {
        if (token != null) {
            InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            manager.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * 收起键盘
     */
    private void HideKeyInput() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {

            imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(),
                    0);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timeFlag = true;
    }
}
