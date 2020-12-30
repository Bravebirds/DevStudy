package com.mryu.devstudy.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import com.mryu.devstudy.R;
import com.mryu.devstudy.utils.ToastUtils;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import static java.lang.Thread.sleep;
public class RegisterActivity extends AppCompatActivity implements View.OnClickListener, TextWatcher {

    private static final String TAG = "RegisterActivity";
    private Toolbar mRegisterBack;
    /**
     * 请输入用户名
     */
    private EditText mUsernameEdit;
    /**
     * 请输入密码
     */
    private EditText mPasswordEdit;
    /**
     * 注册
     */
    private Button mRegisterBtn;
    private ImageView mShowPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
        setListener();
    }

    private void initView() {
        mRegisterBack = (Toolbar) findViewById(R.id.register_back);
        mUsernameEdit = (EditText) findViewById(R.id.username_edit);
        mPasswordEdit = (EditText) findViewById(R.id.password_edit);
        mRegisterBtn = (Button) findViewById(R.id.register_btn);
        mShowPassword = (ImageView) findViewById(R.id.show_password);
        mShowPassword.setOnClickListener(this);
    }

    private void setListener() {
        mRegisterBack.setOnClickListener(this);
        mUsernameEdit.setOnClickListener(this);
        mUsernameEdit.addTextChangedListener(this);
        mPasswordEdit.setOnClickListener(this);
        mPasswordEdit.addTextChangedListener(this);
        mRegisterBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.register_back:
                startActivity(new Intent(this, LoginActivity.class));
                finish();
                showToast("" + getString(R.string.disregister), R.drawable.icon_waring_yellow, 0.03);
                break;
            case R.id.username_edit:
                break;
            case R.id.password_edit:
                break;
            case R.id.register_btn:
                break;
            case R.id.show_password:
                mShowPassword.setBackgroundResource(R.mipmap.icon_show_password);
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
         * 判断UserName及Pwd字段是否均已输入对应改变入口可点击状态
         */
        String accountText = mUsernameEdit.getText().toString();
        String passwordText = mPasswordEdit.getText().toString();
        Log.d(TAG, "UserName：" + accountText.isEmpty() + ",PassWord：" + passwordText.isEmpty());
        int maxUserNameLength = 16;
        int maxPwdLength = 20;
        // 用户名长度限制
        if (accountText.length() > maxUserNameLength) {
            String newStr = accountText.substring(0, maxUserNameLength);
            mUsernameEdit.setText(newStr);
            mUsernameEdit.setSelection(mUsernameEdit.getText().length());
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
            showToast("密码仅支持" + maxPwdLength + "位输入", R.drawable.icon_waring_yellow, 0.03);
        }
        if (accountText.equals("") == false && passwordText.equals("") == false) {
            Log.d(TAG, "账号密码均检测有内容，登录按钮高亮状态！！！");
            mRegisterBtn.setBackgroundResource(R.drawable.shape_nextstep_selected);
        }
        if ((passwordText.equals("")==false)) {
            mShowPassword.setVisibility(View.VISIBLE);
        }else{
            mShowPassword.setVisibility(View.GONE);
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
            finish();
            showToast("" + getString(R.string.disregister), R.drawable.icon_waring_yellow, 0.03);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


}
