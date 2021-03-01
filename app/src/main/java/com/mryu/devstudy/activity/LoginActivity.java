package com.mryu.devstudy.activity;
import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.PowerManager;
import android.provider.Settings;
import android.text.Editable;
import android.text.InputType;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.LinkMovementMethod;
import android.text.method.PasswordTransformationMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
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
import android.widget.ToggleButton;
import com.mryu.devstudy.MainActivity;
import com.mryu.devstudy.R;
import com.mryu.devstudy.layout.KeyboardLayout;
import com.mryu.devstudy.layout.SoftKeyInputHidWidget;
import com.mryu.devstudy.permission.PermissionListener;
import com.mryu.devstudy.permission.PermissionUtils;
import com.mryu.devstudy.qqlogin.QQLoginManager;
import com.mryu.devstudy.utils.ModelUtils;
import com.mryu.devstudy.utils.NetworkUtils;
import com.mryu.devstudy.utils.RepeatClickUtils;
import com.mryu.devstudy.utils.ToastUtils;
import com.tencent.tauth.UiError;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import okhttp3.Call;

import static java.lang.Thread.sleep;
/**
 * FileName： LoginActivity.java
 * Author : YuYanQing5920
 * Desc: 登录
 * Date： 2020-12-19
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener, TextWatcher, View.OnFocusChangeListener, CompoundButton.OnCheckedChangeListener, QQLoginManager.QQLoginListener, DialogInterface.OnKeyListener {
    private static final String TAG = "LoginActivity";
    private static final String loginUrl = "https://www.wanandroid.com/user/login";
    private SharedPreferences sharedPreferences;
    private ImageView mQqLogin;
    private ImageView mWxLogin;
    private ImageView mWbLogin;
    /**
     * QQ号/手机号/邮箱
     */
    private EditText mUserNameEdit;
    /**
     * 密码
     */
    private EditText mPasswordEdit;
    /**
     * →
     */
    private Button mAccountLoginBtn;
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
    /**
     * 忘记密码
     */
    private TextView mForgetPwd;
    /**
     * 去注册
     */
    private TextView mStartRegist;
    private ToggleButton mShowPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        initView();
        setListener();
        initConfig();
    }

    /**
     * 初始化权限获取、协议授权弹窗
     */
    private void initConfig() {
        sharedPreferences = getSharedPreferences("initAppInfoMation", Context.MODE_PRIVATE);
        SharedPreferences.Editor writeFile = sharedPreferences.edit();
        int dialogStatus = sharedPreferences.getInt("ruleStatus", 0);
        int PermissionStatus = sharedPreferences.getInt("initAppPermission", 0);
        Log.d(TAG, "CheckBoxStatus：" + mCheckAgreement.isChecked() + "  isDialogStatus：" + sharedPreferences.getInt("ruleStatus", 0));
        if (dialogStatus == 0) {
            addDiaolog();
        } else {
            mCheckAgreement.setChecked(true);
            writeFile.putInt("ruleStatus", 1);
            writeFile.commit();
        }

        if (PermissionStatus == 0) {
            initAppPermission();
        }
    }

    private void initView() {
        QQLoginManager.init(this, "1110529440");
        mUserNameEdit = (EditText) findViewById(R.id.username_edit);
        mPasswordEdit = (EditText) findViewById(R.id.password_edit);
        mAccountLoginBtn = (Button) findViewById(R.id.account_login);
        mQqLogin = (ImageView) findViewById(R.id.qq_login);
        mWxLogin = (ImageView) findViewById(R.id.wx_login);
        mWbLogin = (ImageView) findViewById(R.id.wb_login);
        mCheckAgreement = (CheckBox) findViewById(R.id.check_agreement);
        mUserMent = (TextView) findViewById(R.id.user_ment);
        mAllLinear = (ScrollView) findViewById(R.id.all_linear);
        mMainlayout = (KeyboardLayout) findViewById(R.id.mainlayout);
        mUserPrivacy = (TextView) findViewById(R.id.user_privacy);
        mForgetPwd = (TextView) findViewById(R.id.forget_pwd);
        mStartRegist = (TextView) findViewById(R.id.start_regist);
        mShowPassword = (ToggleButton) findViewById(R.id.show_password);
    }

    private void setListener() {
        mUserNameEdit.setOnClickListener(this);
        mUserNameEdit.setOnFocusChangeListener(this);
        mUserNameEdit.addTextChangedListener(this);
        mPasswordEdit.setOnClickListener(this);
        mPasswordEdit.setOnFocusChangeListener(this);
        mPasswordEdit.addTextChangedListener(this);
        mAccountLoginBtn.setOnClickListener(this);
        mQqLogin.setOnClickListener(this);
        mWxLogin.setOnClickListener(this);
        mWbLogin.setOnClickListener(this);
        mCheckAgreement.setOnClickListener(this);
        mCheckAgreement.setOnCheckedChangeListener(this);
        mUserMent.setOnClickListener(this);
        mAllLinear.setOnClickListener(this);
        mUserPrivacy.setOnClickListener(this);
        addLayoutListener();
        mForgetPwd.setOnClickListener(this);
        mStartRegist.setOnClickListener(this);
        // QQ互联登录监听
        QQLoginManager.setQQLoginListener(this);
        mShowPassword.setOnClickListener(this);
        mShowPassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.d(TAG,"Toggle Button isChecked ："+isChecked);
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

    private void initAppPermission() {
        PermissionUtils.with(this) // 若是fragment则换成getActivity()
                .addPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .addPermissions(Manifest.permission.ACCESS_FINE_LOCATION)
                .addPermissions(Manifest.permission.CALL_PHONE)
                .addPermissions(Manifest.permission.ACCESS_WIFI_STATE)
                .addPermissions(Manifest.permission.CAMERA)
                .addPermissions(Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS)
                .addPermissions(Manifest.permission.ACCESS_NETWORK_STATE)
                .addPermissions(Manifest.permission.ACCESS_WIFI_STATE)
                .addPermissions(Manifest.permission.READ_PHONE_STATE)
                .addPermissions(Manifest.permission.RECORD_AUDIO)
                .addPermissions(Manifest.permission.REQUEST_IGNORE_BATTERY_OPTIMIZATIONS)
                .setPermissionsCheckListener(new PermissionListener() {
                    @Override
                    public void permissionRequestSuccess() {
                        showToast("所有权限都授权成功", R.drawable.icon_security_green, 0.03);
                    }

                    @Override
                    public void permissionRequestFail(String[] grantedPermissions, String[] deniedPermissions, String[] forceDeniedPermissions) {
                        StringBuilder result = new StringBuilder("\n");
                        sharedPreferences = getSharedPreferences("initAppInfoMation", Context.MODE_PRIVATE);
                        SharedPreferences.Editor writeFile = sharedPreferences.edit();
                        result.append("授权通过的权限：\n");
                        for (String grantedPermission : grantedPermissions) {
                            result.append(grantedPermission + "\n");
                            writeFile.putInt(grantedPermission, 1);
                            writeFile.commit();
                        }
                        result.append("临时拒绝的权限：\n");
                        for (String deniedPermission : deniedPermissions) {
                            result.append(deniedPermission + "\n");
                            writeFile.putInt(deniedPermission, 2);
                            writeFile.commit();
                        }
                        result.append("永久拒绝的权限：\n");
                        for (String forceDeniedPermission : forceDeniedPermissions) {
                            result.append(forceDeniedPermission + "\n");
                            writeFile.putInt(forceDeniedPermission, 0);
                            writeFile.commit();
                        }
                        writeFile.putInt("initAppPermission", 1);
                        writeFile.commit();
                        Log.d(TAG, "Permission：" + result);
                    }
                })
                .createConfig()
                .setForceAllPermissionsGranted(false) //配置是否强制用户授权才可以使用，当设置为true的时候，如果用户拒绝授权，会一直弹出授权框让用户授权
                .setForceDeniedPermissionTips("请前往设置->应用->【" + PermissionUtils.getAppName(this) + "】->权限中打开相关权限，否则功能无法正常运行！")
                .buildConfig()
                .startCheckPermission();
    }

    @Override
    public void onQQLoginSuccess(JSONObject jsonObject) {
        showToast("用户登录成功", R.drawable.icon_security_green, 0.03);
        Log.d(TAG, "QQLogin：" + jsonObject.toString());
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override
    public void onQQLoginCancel() {
        showToast("用户取消登录", R.drawable.icon_warning_blue, 0.03);
        Log.d(TAG, "QQLogin：登录取消");
    }

    @Override
    public void onQQLoginError(UiError uiError) {
        showToast("登录出错：" + uiError.toString(), R.drawable.icon_security_green, 0.03);
        Log.d(TAG, "登录出错：" + uiError.toString());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        QQLoginManager.onActivityResultData(requestCode, resultCode, data);
    }

    /**
     * 用户权限弹窗
     */
    private void addDiaolog() {
        View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.dialog_rule, null);
        TextView mTvCancel = view.findViewById(R.id.tv_cancel);
        TextView mTvOk = view.findViewById(R.id.tv_ok);
        TextView mText = view.findViewById(R.id.tv_text);
        mText.setText(R.string.all_rule_tint);
        final AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setView(view)
                .show();
        mTvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.cancel();
                showToast("" + getString(R.string.deselect), R.drawable.icon_waring_yellow, 0.03);
            }
        });
        mTvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.cancel();
                mCheckAgreement.setChecked(true);
                showToast("" + getString(R.string.agree_rule), R.drawable.icon_security_green, 0.03);
            }
        });
        alertDialog.setCancelable(false); // 禁用掉返回按钮
        final SpannableStringBuilder style = new SpannableStringBuilder();
        //设置文字
        String str = mText.getText().toString();
        style.append(str);

        //设置部分文字点击事件
        ClickableSpan ClickuserRule = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                userClick();
            }
        };
        ClickableSpan ClickprivateRule = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                privacyClick();
            }
        };
        alertDialog.setOnKeyListener(this);
        style.setSpan(ClickuserRule, 67, 71, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        style.setSpan(ClickprivateRule, 74, 78, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        //设置部分文字颜色
        ForegroundColorSpan userRulesColor = new ForegroundColorSpan(Color.parseColor("#FF1493"));
        ForegroundColorSpan privateRulesColor = new ForegroundColorSpan(Color.parseColor("#FF1493"));
        style.setSpan(userRulesColor, 67, 71, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        style.setSpan(privateRulesColor, 74, 78, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        //配置给TextView
        mText.setText(style);
        mText.setMovementMethod(LinkMovementMethod.getInstance());
    }

    @Override
    public void onClick(View v) {
        boolean isConnected = NetworkUtils.isConnected(this);
        boolean isWifiProxy = NetworkUtils.isWifiProxy(this);
        Log.d(TAG, "isConnected：" + isConnected + "  isWifiProxy：" + isWifiProxy);
            if (RepeatClickUtils.isFastClick()) {
                if (isConnected == true && isWifiProxy == false) {
                    switch (v.getId()) {
                        default:
                            break;
                        case R.id.account_login:
                            VerfiyLogin();
                            break;
                        case R.id.qq_login:
                            if (mCheckAgreement.isChecked() == false) {
                                addDiaolog();
                            } else {
                                QQLoginManager.login(this);
                            }
                            break;
                        case R.id.wx_login:
                            if (mCheckAgreement.isChecked() == false) {
                                addDiaolog();
                            } else {
                                showToast("微信注册/登录暂未实现！！！", R.drawable.icon_waring_yellow, 0.03);
                            }
                            break;
                        case R.id.wb_login:
                            if (mCheckAgreement.isChecked() == false) {
                                addDiaolog();
                            } else {
                                showToast("微博注册/登录暂未实现！！！", R.drawable.icon_waring_yellow, 0.03);
                            }
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
                        case R.id.forget_pwd:
                            showToast("测试用的api没有忘记密码功能",R.drawable.icon_waring_yellow,0.03);
//                            startActivity(new Intent(this,ForgetActivity.class));
//                            finish();
                            break;
                        case R.id.start_regist:
                            if (mCheckAgreement.isChecked() == false) {
                                addDiaolog();
                            } else {
                                startActivity(new Intent(this,RegisterActivity.class));
                                finish();
                            }
                            break;
                    }
                } else {
                    showToast(getString(R.string.checkNetWork), R.drawable.icon_waring_yellow, 0.03);
                }
            }
//            } else {
//                showToast(getString(R.string.repeat_click), R.drawable.icon_waring_yellow, 0.03);
//            }
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
        String username = mUserNameEdit.getText().toString();
        String password = mPasswordEdit.getText().toString();
        boolean isConnected = NetworkUtils.isConnected(this);
        if (isConnected == true) {
            if (mCheckAgreement.isChecked() == true) {
                if (username.equals("") == true && password.equals("") == false) {
                    showToast("账号不允许为空！！！", R.drawable.icon_waring_yellow, 0.03);
                } else if (username.equals("") == false && password.equals("") == true) {
                    showToast("密码不允许为空！！！", R.drawable.icon_waring_yellow, 0.03);
                } else if (username.equals("") == true && password.equals("") == true) {
                    showToast("账号密码不允许为空！！！", R.drawable.icon_waring_yellow, 0.03);
                } else {
                    OkHttpUtils
                            .post()
                            .url(loginUrl)
                            .addParams("username", username)
                            .addParams("password", password)
                            .build()
                            .execute(new StringCallback() {
                                @Override
                                public void onError(Call call, Exception e, int id) {
                                    showToast("服务器请求失败",R.drawable.icon_security_green,0.03);
                                }

                                @Override
                                public void onResponse(String response, int id) {
                                    try {
                                        JSONObject jsonObject = new JSONObject(response);
                                        Log.d(TAG,"JSON"+jsonObject.toString());
                                        /**
                                         * {"data":null,"errorCode":-1,"errorMsg":"用户名已经被注册！"}
                                         * {"data":null,"errorCode":-1,"errorMsg":"账号密码不匹配！"}
                                         */
                                        int errorCode = jsonObject.getInt("errorCode");
                                        String errorMsg = jsonObject.getString("errorMsg");
                                        if (errorCode==0) {
                                            Log.v(TAG, "登录成功,正在初始化进入首页！！！");
                                            showToast("登录成功",R.drawable.icon_security_green,0.03);
                                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                            startActivity(intent);
                                            finish();
                                        } else {
                                            showToast(""+errorMsg,R.drawable.icon_waring_yellow,0.03);
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        showToast("登录失败",R.drawable.icon_waring_yellow,0.03);
                                    }
                                }
                            });
                } 
            } else if (mCheckAgreement.isChecked() == false) {
                addDiaolog();
            } else {
                showToast(getString(R.string.checkNetWork), R.drawable.icon_waring_yellow, 0.03);
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
        String usernameText = mUserNameEdit.getText().toString();
        String passwordText = mPasswordEdit.getText().toString();
        int maxUserNameLength = 16;
        int maxPwdLength = 20;
        // 用户名长度限制
        if (usernameText.length() > maxUserNameLength) {
            String newStr = usernameText.substring(0, maxUserNameLength);
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
            HideKeyInput();
            try {
                sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            showToast("密码仅支持" + maxPwdLength + "位输入", R.drawable.icon_waring_yellow, 0.03);
        }

        if (usernameText.equals("") == true || passwordText.equals("") == true) {
            mAccountLoginBtn.setBackgroundResource(R.drawable.shape_nextstep_unselect);
        }else{
            mAccountLoginBtn.setBackgroundResource(R.drawable.shape_nextstep_selected);
        }
        if (mCheckAgreement.isChecked() == true) {
//            if (usernameText.equals("") == true && passwordText.equals("") == false) {
//                mPasswordEdit.setText("");  //清空 掉上个账号遗留的密码！！！
//                showToast("请先输入账号", R.drawable.icon_waring_yellow, 0.03);
//            } else
        }
        if ((passwordText.equals("")==false)) {
            mShowPassword.setVisibility(View.VISIBLE);
        }else{
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
                    mUserNameEdit.setHint(""+getString(R.string.user_edit_hint));
                    Log.d(TAG, "焦点事件已从UserName输入框中移除！！！");
                }
                break;
            case R.id.password_edit:
                if (hasFocus) {
                    mPasswordEdit.setHint("");
                } else {
                    mPasswordEdit.setHint(""+getString(R.string.password_edit_hint));
                }
                break;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        sharedPreferences = getSharedPreferences("initAppInfoMation", Context.MODE_PRIVATE);
        SharedPreferences.Editor writeFile = sharedPreferences.edit();
        if (isChecked) {
            Log.d(TAG, "用户成功勾选协议");
            if (TextUtils.isEmpty(mUserNameEdit.getText()) == false && TextUtils.isEmpty(mPasswordEdit.getText()) == false) {
                mAccountLoginBtn.setBackgroundResource(R.drawable.shape_nextstep_selected);
            }
        } else {
            mAccountLoginBtn.setBackgroundResource(R.drawable.shape_nextstep_unselect);
            Log.d(TAG, "用户未勾选协议授权");
            writeFile.putInt("ruleStatus", 0);
            writeFile.commit();
        }
        if (mCheckAgreement.isChecked() == true) {
            writeFile.putInt("ruleStatus", 1);
            writeFile.commit();
        }
        Log.d(TAG, "CheckBoxStatus：" + mCheckAgreement.isChecked());
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
     * 监听键盘状态，布局有变化时，靠scrollView去滚动界面
     */
    public void addLayoutListener() {
        mMainlayout.setKeyboardListener(new KeyboardLayout.KeyboardLayoutListener() {
            @Override
            public void onKeyboardStateChanged(boolean isActive, int keyboardHeight) {
                Log.e("onKeyboardStateChanged", "isActive:" + isActive + " keyboardHeight:" + keyboardHeight);
                if (isActive) {
                    mMainlayout.setTop(10);
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
            mPasswordEdit.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL);
            mPasswordEdit.setTransformationMethod(PasswordTransformationMethod.getInstance());
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
        mUserNameEdit.setHint("QQ号/手机号/邮箱");
        mPasswordEdit.setHint("密码");
        mUserNameEdit.clearFocus();
        mPasswordEdit.clearFocus();
    }

    /**
     * 重写返回键功能
     */
    private Long mExitTime = 0L;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (System.currentTimeMillis() - mExitTime > 1000) {
                showToast("再按一次退出" + getString(R.string.app_name), R.drawable.icon_waring_yellow, 0.03);
                mExitTime = System.currentTimeMillis();
            } else {
                finish();
//                System.exit(0);  启用的话会黑屏
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onKey(DialogInterface dialogInterface, int keyCode, KeyEvent keyEvent) {
        if (keyCode == KeyEvent.KEYCODE_BACK && keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
            showToast("" + getString(R.string.please_cheack), R.drawable.icon_waring_yellow, 0.03);
        }
        return false;
    }
}