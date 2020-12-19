package com.mryu.devstudy.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
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
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.mryu.devstudy.MainActivity;
import com.mryu.devstudy.R;
import com.mryu.devstudy.utils.KeyboardLayout;
import com.mryu.devstudy.utils.ModelUtils;
import com.mryu.devstudy.utils.RulesDialog;
import com.mryu.devstudy.utils.SoftKeyInputHidWidget;
import com.mryu.devstudy.utils.ToastUtils;

import static java.lang.Thread.sleep;
/**
 * FileName： LoginActivity.java
 * Author : YuYanQing5920
 * Desc: 登录
 * Date： 2020-12-19
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener, TextWatcher, View.OnFocusChangeListener, CompoundButton.OnCheckedChangeListener {
    private static final String TAG = "LoginActivity";
    private String text = "欢迎使用DevStudy！使用过程中我们会需要访问您的存储权限、录制、相机权限、获取设备信息等权限及信息\n" +
            "• 您可以点击《用户协议》和《隐私政策》了解个人信息类型与用途的对应关系等更加详尽的个人信息处理规则。\n" +
            "• 我们会采用业界领先的安全技术保护好您的个人信息，如您同意，请点击“同意”开始接受我们的服务。";
    private String account = "501893067";
    private String password = "123567";
    private static volatile RulesDialog instance = null;
    private ImageView mQqLogin;
    private ImageView mWxLogin;
    private ImageView mWbLogin;
    /**
     * QQ号/手机号/邮箱
     */
    private EditText mAccount;
    /**
     * 密码
     */
    private EditText mPassword;
    /**
     * →
     */
    private Button mAccountLogin;
    /**
     * 我已阅读并同意
     */
    private CheckBox mCheckAgreement;
    /**
     * 《用户协议》
     */
    private TextView mUserMent;
    private ScrollView mAllLinear;
    private KeyboardLayout mMainlayout;
    /**
     * 《用户协议》
     */
    private TextView mUserPrivacy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        initView();
        setListener();
    }

    private void initView() {
        mAccount = (EditText) findViewById(R.id.account);
        mPassword = (EditText) findViewById(R.id.password);
        mAccountLogin = (Button) findViewById(R.id.account_login);
        mQqLogin = (ImageView) findViewById(R.id.qq_login);
        mWxLogin = (ImageView) findViewById(R.id.wx_login);
        mWbLogin = (ImageView) findViewById(R.id.wb_login);
        mCheckAgreement = (CheckBox) findViewById(R.id.check_agreement);
        mUserMent = (TextView) findViewById(R.id.user_ment);
        mAllLinear = (ScrollView) findViewById(R.id.all_linear);
        mMainlayout = (KeyboardLayout) findViewById(R.id.mainlayout);
        mUserPrivacy = (TextView) findViewById(R.id.user_privacy);
        mCheckAgreement.setChecked(false);

    }


    private void setListener() {
        mAccount.setOnClickListener(this);
        mAccount.setOnFocusChangeListener(this);
        mAccount.addTextChangedListener(this);
        mPassword.setOnClickListener(this);
        mPassword.setOnFocusChangeListener(this);
        mPassword.addTextChangedListener(this);
        mAccountLogin.setOnClickListener(this);
        mQqLogin.setOnClickListener(this);
        mWxLogin.setOnClickListener(this);
        mWbLogin.setOnClickListener(this);
        mCheckAgreement.setOnClickListener(this);
        mCheckAgreement.setOnCheckedChangeListener(this);
        mUserMent.setOnClickListener(this);
        mAllLinear.setOnClickListener(this);
        mUserPrivacy.setOnClickListener(this);
        addLayoutListener();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.account:
                break;
            case R.id.password:
                break;
            case R.id.account_login:
                VerfiyLogin();
                break;
            case R.id.qq_login:
                showToast("QQ注册/登录暂未实现！！！", R.drawable.icon_waring_yellow);
                break;
            case R.id.wx_login:
                showToast("微信注册/登录暂未实现！！！", R.drawable.icon_waring_yellow);
                break;
            case R.id.wb_login:
                showToast("微博注册/登录暂未实现！！！", R.drawable.icon_waring_yellow);
                break;
            case R.id.check_agreement:
                break;
            case R.id.user_ment:
                userClick();
                break;
            case R.id.all_linear:
                break;
            case R.id.user_privacy:
                privacyClick();
                break;
        }
    }

    /**
     * 跳转至用户协议
     */
    private void userClick() {
        Intent intent = new Intent(this, RuleActivity.class);
        intent.putExtra("userRule", false);
        intent.putExtra("url", "file:////android_asset/userRule.html");
        startActivity(intent);
    }

    /**
     * 跳转至隐私政策
     */
    private void privacyClick() {
        Intent intent = new Intent(this, RuleActivity.class);
        intent.putExtra("privateRule", false);
        intent.putExtra("url", "file:////android_asset/privateRule.html");
        startActivity(intent);
    }

    private void VerfiyLogin() {
        String account_context = mAccount.getText().toString();
        String password_context = mPassword.getText().toString();
        if (mCheckAgreement.isChecked() == true) {
            if (account_context.equals("") == true && password_context.equals("") == false) {
                showToast("账号不允许为空！！！", R.drawable.icon_waring_yellow);
            } else if (account_context.equals("") == false && password_context.equals("") == true) {
                showToast("密码不允许为空！！！", R.drawable.icon_waring_yellow);
            } else if (account_context.equals("") == true && password_context.equals("") == true) {
                showToast("账号密码不允许为空！！！", R.drawable.icon_waring_yellow);
            } else if (account_context.equals(account) == true && password_context.equals(password) == true) {
                Log.v(TAG, "登录成功,正在初始化进入首页！！！");
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                intent.putExtra("account", account_context);
                intent.putExtra("password", password_context);
                startActivity(intent);
                finish();
            } else if (account_context.equals(account) == false || password_context.equals(password) == false) {
                showToast("请输入正确的账号及密码！！！", R.drawable.icon_waring_yellow);
            }
        } else {
            if (account_context.equals("") == true && password_context.equals("") == true && mCheckAgreement.isChecked() == false) {
                showToast("账号密码不允许为空！！！", R.drawable.icon_waring_yellow);
            } else if (account_context.equals("") == true && password_context.equals("") == false && mCheckAgreement.isChecked() == false) {
                showToast("账号不允许为空！！！", R.drawable.icon_waring_yellow);
            } else if (account_context.equals("") == false && password_context.equals("") == true && mCheckAgreement.isChecked() == false) {
                showToast("密码不允许为空！！！", R.drawable.icon_waring_yellow);
            } else {
                showToast("请先勾选协议", R.drawable.icon_waring_yellow);
            }
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
         * 判断UserName及Pwd字段是否均已输入对应改变登录入口可点击状态
         */
        String accountText = mAccount.getText().toString();
        String passwordText = mPassword.getText().toString();
        Log.d(TAG, "UserName：" + accountText.isEmpty() + ",PassWord：" + passwordText.isEmpty());
        int maxUserNameLength = 16;
        int maxPwdLength = 20;
        // 用户名长度限制
        if (accountText.length() > maxUserNameLength) {
            String newStr = accountText.substring(0, maxUserNameLength);
            mAccount.setText(newStr);
            mAccount.setSelection(mAccount.getText().length());
            HideKeyInput();
            try {
                sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            showToast("用户名仅支持" + maxUserNameLength + "位输入", R.drawable.icon_waring_yellow);
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
            showToast("密码仅支持" + maxPwdLength + "位输入", R.drawable.icon_waring_yellow);
        }

        if (accountText.equals("") == true || passwordText.equals("") == true) {
            mAccountLogin.setBackgroundResource(R.drawable.shape_nextstep_unselect);
        }
        if (mCheckAgreement.isChecked() == true) {
            if (accountText.equals("") == true && passwordText.equals("") == false) {
                mPassword.setText("");  //清空 掉上个账号遗留的密码！！！
                showToast("请先输入账号", R.drawable.icon_waring_yellow);
            } else if (accountText.equals("") == false && passwordText.equals("") == false) {
                Log.d(TAG, "账号密码均检测有内容，登录按钮高亮状态！！！");
                mAccountLogin.setBackgroundResource(R.drawable.shape_nextstep_selected);
            }
        }
    }

    @Override
    public void onFocusChange(View view, boolean hasFocus) {
        switch (view.getId()) {
            default:
            case R.id.account:
                if (hasFocus) {
                    Log.d(TAG, "以获取UserName输入框的焦点！！！");
                    mAccount.setHint("");
                } else {
                    mAccount.setHint("QQ号/手机号/邮箱");
                    Log.d(TAG, "焦点事件已从UserName输入框中移除！！！");
                }
                break;
            case R.id.password:
                if (hasFocus) {
                    Log.d(TAG, "以获取Password输入框的焦点！！！");
                    mPassword.setHint("");
                } else {
                    Log.d(TAG, "焦点事件已从Password输入框中移除！！！");
                    mPassword.setHint("密码");
                }
                break;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            Log.d(TAG, "用户成功勾选协议");
            if (TextUtils.isEmpty(mAccount.getText()) == false && TextUtils.isEmpty(mPassword.getText()) == false) {
                mAccountLogin.setBackgroundResource(R.drawable.shape_nextstep_selected);
            }
        } else {
            mAccountLogin.setBackgroundResource(R.drawable.shape_nextstep_unselect);
            Log.d(TAG, "用户未勾选协议授权");
        }
    }

    /**
     * Toast调用
     *
     * @param message
     * @param resId
     */
    private void showToast(String message, int resId) {
        ToastUtils.showKevinToast(this, message, resId);
    }


    /**
     * 监听键盘状态，布局有变化时，靠scrollView去滚动界面
     */
    public void addLayoutListener() {
        mMainlayout.setKeyboardListener(new KeyboardLayout.KeyboardLayoutListener() {
            @Override
            public void onKeyboardStateChanged(boolean isActive, int keyboardHeight) {
                Log.e("onKeyboardStateChanged", "isActive:" + isActive + " keyboardHeight:" + keyboardHeight);
                if (isActive) {
                    mMainlayout.setTop(170);
                    scrollToBottom();
                }
            }
        });
    }

    /**
     * 弹出软键盘时将SVContainer滑到底
     */
    private void scrollToBottom() {

        mMainlayout.postDelayed(new Runnable() {

            @Override
            public void run() {
                mAllLinear.smoothScrollTo(0, mAllLinear.getBottom() + SoftKeyInputHidWidget.getStatusBarHeight(LoginActivity.this));
            }
        }, 1000);

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

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

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
     * 禁用掉系统安全键盘 恢复至当前使用的输入法
     */
    private void keyInputType() {
        if (ModelUtils.isEMUI() && Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            mPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL);
            mPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
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
    protected void onPause() {
        super.onPause();
        initView();
        //初始化输入框设置
        onPauseData();
    }

    private void onPauseData() {
        mAccount.setHint("QQ号/手机号/邮箱");
        mPassword.setHint("密码");
        mAccount.clearFocus();
        mPassword.clearFocus();
    }

    /**
     * 重新返回键功能
     */
    private Long mExitTime = 0L;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (System.currentTimeMillis() - mExitTime > 2000) {
                showToast("再按一次退出程序", R.drawable.icon_waring_yellow);
                mExitTime = System.currentTimeMillis();
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}