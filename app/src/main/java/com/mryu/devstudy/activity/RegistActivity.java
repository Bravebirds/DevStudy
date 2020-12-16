package com.mryu.devstudy.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mryu.devstudy.R;
import com.mryu.devstudy.utils.MobileUtil;
import com.mryu.devstudy.utils.ToastUtils;

import androidx.appcompat.app.AppCompatActivity;

import static java.lang.Thread.sleep;

public class RegistActivity extends AppCompatActivity implements View.OnClickListener, View.OnFocusChangeListener, TextWatcher, CompoundButton.OnCheckedChangeListener {
    private static final String TAG = "RegistActivity";
    private ImageView mBackpage;
    private boolean timeFlag = true;
    /**
     * 免密一键登录
     */
    private TextView mPwdFreeAccess;
    /**
     * 请输入手机号
     */
    private EditText mIphone;
    /**
     * 请输入收到的验证码
     */
    private EditText mVerifi;
    /**
     * 获取验证码
     */
    private Button mGetVerifi;
    /**
     * 请设置您的登录密码
     */
    private EditText mPassword;
    /**
     * 我已阅读并同意
     */
    private CheckBox mCheckAgreement;
    /**
     * 《用户协议》
     */
    private TextView mRegistMent;
    /**
     * →
     */
    private Button mAccountRegist;
    private LinearLayout mAllLinear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        initView();
        setListener();
    }

    private void initView() {

        mBackpage = (ImageView) findViewById(R.id.backpage);
        mPwdFreeAccess = (TextView) findViewById(R.id.pwd_free_access);
        mIphone = (EditText) findViewById(R.id.iphone);
        mVerifi = (EditText) findViewById(R.id.verifi);
        mGetVerifi = (Button) findViewById(R.id.get_verifi);
        mPassword = (EditText) findViewById(R.id.password);
        mCheckAgreement = (CheckBox) findViewById(R.id.check_agreement);
        mRegistMent = (TextView) findViewById(R.id.regist_ment);
        mAccountRegist = (Button) findViewById(R.id.account_regist);
        mAllLinear = (LinearLayout) findViewById(R.id.all_linear);
    }

    private void setListener() {
        mBackpage.setOnClickListener(this);
        mPwdFreeAccess.setOnClickListener(this);
        mIphone.setOnClickListener(this);
        mVerifi.setOnClickListener(this);
        mGetVerifi.setOnClickListener(this);
        mPassword.setOnClickListener(this);
        mCheckAgreement.setOnClickListener(this);
        mRegistMent.setOnClickListener(this);
        mAccountRegist.setOnClickListener(this);
        mAllLinear.setOnClickListener(this);
        mIphone.addTextChangedListener(this);
        mVerifi.addTextChangedListener(this);
        mPassword.addTextChangedListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.backpage:
                finish();
                break;
            case R.id.iphone:
                break;
            case R.id.verifi:
                break;
            case R.id.get_verifi:
                final String phoneNumber = mIphone.getText().toString();
                // CheckPhone是否符合正则表达式 true则高亮获取 false 置灰
                if (MobileUtil.checkPhone(phoneNumber) == false) {
                    mIphone.setText("");
                    mVerifi.setText("");
                    mPassword.setText("");
                    showToast("请输入正确的手机号再获取验证码", R.drawable.toast_ic_ship);
                } else {
                    mGetVerifi.setBackgroundResource(R.drawable.shape_send_btnstatus);
                    showToast("走到发送验证码逻辑，但该功能还未实现", R.drawable.toast_ic_ship);
                    mGetVerifi.setEnabled(false);//在发送数据的时候设置为不能点击
                    mGetVerifi.setBackgroundResource(R.drawable.shape_send_btnstatus);//背景色设为灰色
                    /**
                     * 获取验证码倒计时
                     */
                    timeFlag = true;
                    Thread thread = new Thread() {
                        @Override
                        public void run() {
                            for (int i = 59; i >= 0 && timeFlag; i--) {
                                try {
                                    sleep(1000);
                                    Message msg = Message.obtain();
                                    msg.what = i;
                                    if (mHandler == null) {
                                        break;
                                    } else {
                                        mHandler.sendMessage(msg);
                                    }
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    };
                    thread.start();
                }
                break;
            case R.id.password:
                break;
            case R.id.check_agreement:
                break;
            case R.id.account_regist:
                // 手机、验证码、密码均不为空且协议已勾选时注册流程通过
                if (TextUtils.isEmpty(mIphone.getText()) == false && TextUtils.isEmpty(mVerifi.getText()) == false && TextUtils.isEmpty(mPassword.getText()) == false) {
                    if (mCheckAgreement.isChecked() == true) {
                        showToast("注册成功", R.drawable.toast_ic_ship);
                        Intent intent = new Intent(this, LoadingActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                }
                // 手机号、验证码、密码其中一项为空
                if (TextUtils.isEmpty(mIphone.getText()) == true || TextUtils.isEmpty(mVerifi.getText()) == true || TextUtils.isEmpty(mPassword.getText()) == true) {
                    showToast("请确定注册信息均已填写", R.drawable.toast_ic_ship);
                }
                break;
            case R.id.ment:
                startActivity(new Intent(this, AgreementActivity.class));
                break;
            case R.id.pwd_free_access:
                Intent  pwdfreeLogin = new Intent(this, PhoneLoginActivity.class);
                startActivity(pwdfreeLogin);
                break;
            case R.id.regist_ment:
                break;
            case R.id.all_linear:
                break;
        }
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what > 0) {
                mGetVerifi.setText("(" + msg.what + ")秒后重试");
            } else {
                mGetVerifi.setText("获取验证码");
                mGetVerifi.setEnabled(true);//恢复可点击
                final String phoneNumber = mIphone.getText().toString();
                if (MobileUtil.checkPhone(phoneNumber) == false) {
                    mGetVerifi.setBackgroundResource(R.drawable.shape_send_btn_unselect);
                } else if (MobileUtil.checkPhone(phoneNumber) == true){
                    mGetVerifi.setBackgroundResource(R.drawable.shape_send_btn_selected);
                }
            }
        }
    };

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        switch (v.getId()) {
            default:
            case R.id.iphone:
                break;
            case R.id.verifi:
                break;
            case R.id.password:
                break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        /**
         /**
         * 判断mIphone、mVerifi、mPassword字段是否均已输入对应改变注册入口可点击状态
         */
        final String phoneNumber = mIphone.getText().toString();
        final String verifiNumber = mVerifi.getText().toString();
        final String passwordText = mPassword.getText().toString();
        int maxPhone = 11;
        int maxVerifi = 6;
        int maxPwdLength = 20;

        // 实时判断手机号是否符合正则激活获取验证码高亮
        if (MobileUtil.checkPhone(phoneNumber) == false) {
            mGetVerifi.setBackgroundResource(R.drawable.shape_send_btn_unselect);
        } else if (MobileUtil.checkPhone(phoneNumber) == true){
            mGetVerifi.setBackgroundResource(R.drawable.shape_send_btn_selected);
        }
        // 手机号长度限制
        if (phoneNumber.length() > maxPhone) {
            String newStr = phoneNumber.substring(0, maxPhone);
            mIphone.setText(newStr);
            mIphone.setSelection(mIphone.getText().length());
            HideKeyInput();
            showToast("不支持输入长度>" + maxPhone + "位的手机号", R.drawable.toast_ic_ship);
        }

        // 验证码长度限制
        if (verifiNumber.length() > maxVerifi) {
            String newStr = verifiNumber.substring(0, maxVerifi);
            mVerifi.setText(newStr);
            mVerifi.setSelection(mVerifi.getText().length());
            HideKeyInput();
            showToast("不支持输入长度>" + maxVerifi + "位的验证码", R.drawable.toast_ic_ship);
        }

        // 密码长度限制
        if (passwordText.length() > maxPwdLength) {
            String newStr = passwordText.substring(0, maxPwdLength);
            mPassword.setText(newStr);
            mPassword.setSelection(mPassword.getText().length());
            HideKeyInput();
            try {
                sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            showToast("不支持" + maxPwdLength + "位密码输入", R.drawable.toast_ic_ship);
        }

        if (phoneNumber.equals("") == true || verifiNumber.equals("") == true || passwordText.equals("") == true) {
            Log.d(TAG, "mIphone and mVerifi and mPassword iS No");
            mAccountRegist.setBackgroundResource(R.drawable.shape_nextstep_unselect);
        } else {
            if (mCheckAgreement.isChecked() == true) {
                // 协议是否被勾选
                if (phoneNumber.equals("") == false && verifiNumber.equals("") == false && passwordText.equals("") == false) {
                    Log.d(TAG, "mIphone and mVerifi and mPassword and Agreement iS Yes");
                    mAccountRegist.setBackgroundResource(R.drawable.shape_nextstep_selected);
                }
            }
        }
    }


    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        /**
         * 检测协议是否勾选
         */

        if (isChecked) {
            Log.d(TAG, "用户成功勾选协议");
            if (TextUtils.isEmpty(mIphone.getText()) == false && TextUtils.isEmpty(mVerifi.getText()) == false && TextUtils.isEmpty(mPassword.getText()) == false) {
                mAccountRegist.setBackgroundResource(R.drawable.shape_nextstep_selected);
            }
        } else {
            mAccountRegist.setBackgroundResource(R.drawable.shape_nextstep_unselect);
            Log.d(TAG, "用户取消协议授权");
        }
    }


    private void showToast(String message, int resId) {
        ToastUtils.showKevinToast(this, message, resId);
    }
    /**
     * Date 2020-12-16
     * 修复华为等机型的安全键盘唤起时将toast遮挡以及点击非键盘区域无法收起键盘
     * 增加友好性
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
        InputMethodManager imm =  (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        if(imm != null) {

            imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(),
                    0);
        }
    }

    /**
     * 重新返回键功能
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            finish();
        }
        return true;
    }

    /**
     * removeCallbacksAndMessages移除消息队列中所有消息(/所有的Runnable/)
     * Activity destroy的时候注意把所有引用置为空，防止发生内存泄漏
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
        mHandler = null;
        timeFlag = false;
    }

    @Override
    protected void onPause() {
        super.onPause();
//        mCheckAgreement.setChecked(false);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

}
