package com.mryu.devstudy.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mryu.devstudy.MainActivity;
import com.mryu.devstudy.R;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, TextWatcher, View.OnFocusChangeListener, CompoundButton.OnCheckedChangeListener {
    private static final String TAG = "LoginActivity";
    private String account = "501893067";
    private String password = "123567";
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
     * 忘记密码？
     */
    private TextView mForgotpwd;
    /**
     * 哦哦！现在去注册
     */
    private TextView mRegist;
    /**
     * 我已阅读并同意
     */
    private CheckBox mCheckAgreement;
    /**
     * 《用户协议》
     */
    private TextView mMent;
    /**
     * 暂不登录、立即体验
     */
    private TextView mOpen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        initView();
        setView();
        initData();
    }

    private void initData() {
        mAccount.setText("");
        mPassword.setText("");
        mAccount.setHint("QQ号/手机号/邮箱");
        mPassword.setHint("密码");
        mAccount.clearFocus();
        mPassword.clearFocus();
    }

    private void setView() {
        mAccount.setOnClickListener(this);
        mAccount.setOnFocusChangeListener(this);
        mAccount.addTextChangedListener(this);
        mPassword.setOnClickListener(this);
        mPassword.setOnFocusChangeListener(this);
        mPassword.addTextChangedListener(this);
        mForgotpwd.setOnClickListener(this);
        mRegist.setOnClickListener(this);
        mAccountLogin.setOnClickListener(this);
        mQqLogin.setOnClickListener(this);
        mWxLogin.setOnClickListener(this);
        mWbLogin.setOnClickListener(this);
        mCheckAgreement.setOnClickListener(this);
        mCheckAgreement.setOnCheckedChangeListener(this);
        mMent.setOnClickListener(this);

    }

    private void initView() {
        mAccount = (EditText) findViewById(R.id.account);
        mPassword = (EditText) findViewById(R.id.password);
        mAccountLogin = (Button) findViewById(R.id.account_login);
        mQqLogin = (ImageView) findViewById(R.id.qq_login);
        mWxLogin = (ImageView) findViewById(R.id.wx_login);
        mWbLogin = (ImageView) findViewById(R.id.wb_login);
        mForgotpwd = (TextView) findViewById(R.id.forgotpwd);
        mRegist = (TextView) findViewById(R.id.regist);
        mCheckAgreement = (CheckBox) findViewById(R.id.check_agreement);
        mMent = (TextView) findViewById(R.id.ment);
        mOpen = (TextView) findViewById(R.id.experience);
        mOpen.setOnClickListener(this);
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
                Toast.makeText(LoginActivity.this, "微信注册/登录暂未实现！！！", Toast.LENGTH_SHORT).show();
                break;
            case R.id.wx_login:
                Toast.makeText(LoginActivity.this, "微信注册/登录暂未实现！！！", Toast.LENGTH_SHORT).show();
                break;
            case R.id.wb_login:
                Toast.makeText(LoginActivity.this, "微博注册/登录暂未实现！！！", Toast.LENGTH_SHORT).show();
                break;
            case R.id.forgotpwd:
                Intent intent = new Intent(LoginActivity.this, ForgotpwdActivity.class);
                intent.putExtra("account", mAccount.getText().toString());
                intent.putExtra("password", mAccount.getText().toString());
                startActivity(intent);
                break;
            case R.id.regist:
                startActivity(new Intent(LoginActivity.this, RegistActivity.class));
                break;
            case R.id.check_agreement:
                break;
            case R.id.ment:
                startActivity(new Intent(LoginActivity.this, AgreementActivity.class));
                break;
            case R.id.experience:
                Intent experienceIntent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(experienceIntent);
                finish();
                break;
        }
    }

    private void VerfiyLogin() {
        String account_context = mAccount.getText().toString();
        String password_context = mPassword.getText().toString();
        if (mCheckAgreement.isChecked() == true) {
            if (account_context.equals("") == true && password_context.equals("") == false) {
                Toast.makeText(LoginActivity.this, "账号不允许为空！！！", Toast.LENGTH_SHORT).show();
            } else if (account_context.equals("") == false && password_context.equals("") == true) {
                Toast.makeText(LoginActivity.this, "密码不允许为空！！！", Toast.LENGTH_SHORT).show();
            } else if (account_context.equals("") == true && password_context.equals("") == true) {
                Toast.makeText(LoginActivity.this, "账号密码不允许为空！！！", Toast.LENGTH_SHORT).show();
            } else if (account_context.equals(account) == true && password_context.equals(password) == true) {
                Log.v(TAG, "登录成功,正在初始化进入首页！！！");
                Intent intent = new Intent(LoginActivity.this, TestHomeActivity.class);
                intent.putExtra("account", account_context);
                intent.putExtra("password", password_context);
                startActivity(intent);
                finish();
            } else if (account_context.equals(account) == false || password_context.equals(password) == false) {
                Toast.makeText(LoginActivity.this, "请输入正确的账号及密码！！！", Toast.LENGTH_SHORT).show();
            }
        } else {
            if (account_context.equals("") == true && password_context.equals("") == true && mCheckAgreement.isChecked() == false) {
                Toast.makeText(LoginActivity.this, "账号密码不允许为空！！！", Toast.LENGTH_SHORT).show();
            } else if (account_context.equals("") == true && password_context.equals("") == false && mCheckAgreement.isChecked() == false) {
                Toast.makeText(LoginActivity.this, "账号不允许为空！！！", Toast.LENGTH_SHORT).show();
            } else if (account_context.equals("") == false && password_context.equals("") == true && mCheckAgreement.isChecked() == false) {
                Toast.makeText(LoginActivity.this, "密码不允许为空！！！", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(LoginActivity.this, "请同意勾选协议", Toast.LENGTH_SHORT).show();
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
        Log.d(TAG, "UserName：" + mAccount.getText().toString() + ",PassWord：" + mPassword.getText().toString());
        /**
         * 判断UserName及Pwd字段是否均已输入对应改变登录入口可点击状态
         */
        String accountText = mAccount.getText().toString();
        String passwordText = mPassword.getText().toString();
        if (accountText.equals("") == true || passwordText.equals("") == true) {
            Log.d(TAG, "账号或密码未输入登录入口暂时不可点击！！！");
            mAccountLogin.setBackgroundResource(R.drawable.shape_nextstep_unselect);
        }
        if (mCheckAgreement.isChecked() == true) {
            if (accountText.equals("") == true && passwordText.equals("") == false) {
                mPassword.setText("");  //清空掉上个账号遗留的密码
                Toast.makeText(this, "请先输入账号", Toast.LENGTH_SHORT).show();
            } else if (accountText.equals("") == false && passwordText.equals("") == false) {
                Log.d(TAG, "账号密码均检测有内容，登录按钮高亮状态！！！");
                mAccountLogin.setBackgroundResource(R.drawable.shape_nextstep_selected);
            }
        }
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        switch (v.getId()) {
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
    protected void onPause() {
        super.onPause();
        initView();
        //初始化输入框设置
        initData();
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
            Log.d(TAG, "用户取消协议授权");
        }
    }


    /**
     * 重新返回键功能，将返回键功能替换成home功能
     */
    private Long mExitTime = 0L;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (System.currentTimeMillis() - mExitTime > 2000) {
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                mExitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


}

