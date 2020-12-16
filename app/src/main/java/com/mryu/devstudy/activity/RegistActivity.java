package com.mryu.devstudy.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.huantansheng.easyphotos.EasyPhotos;
import com.mryu.devstudy.R;
import com.mryu.devstudy.utils.GlideEngine;
import com.mryu.devstudy.utils.MobileUtil;
import com.mryu.devstudy.utils.PhotoChioceDialog;


public class RegistActivity extends AppCompatActivity implements View.OnClickListener, View.OnFocusChangeListener, TextWatcher, CompoundButton.OnCheckedChangeListener, PhotoChioceDialog.ClickCallback {
    private static final String TAG = "RegistActivity";
    private ImageView mBackpage;
    /**
     * 请输入手机号
     */
    private EditText mIphone;
    /**
     * 请输入收到的验证码
     */
    private EditText mVerifi;
    /**
     * 发送验证码
     */
    private Button mGetVerifi;
    /**
     * 请设置您的登录密码
     */
    private EditText mPassword;
    /**
     * 我已阅读并同意《用户协议》
     */
    private CheckBox mCheckAgreement;
    /**
     * →
     */
    private Button mAccountRegist;
    /**
     * 《用户协议》
     */
    private TextView mMent;
    private LinearLayout mUploadphoto;
    private LinearLayout mHeadPortrait;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        initView();
        setView();
    }

    private void setView() {

        mUploadphoto.setOnClickListener(this);
        mHeadPortrait.setOnClickListener(this);

        mBackpage.setOnClickListener(this);
        mIphone.setOnClickListener(this);
        mIphone.addTextChangedListener(this);
        mIphone.setOnFocusChangeListener(this);

        mVerifi.setOnClickListener(this);
        mVerifi.addTextChangedListener(this);
        mGetVerifi.setOnClickListener(this);

        mPassword.setOnClickListener(this);
        mPassword.addTextChangedListener(this);

        mCheckAgreement.setOnClickListener(this);
        mCheckAgreement.setOnCheckedChangeListener(this);

        mAccountRegist.setOnClickListener(this);
        mMent.setOnClickListener(this);
        mBackpage.setOnClickListener(this);
    }

    private void initView() {
        mBackpage = (ImageView) findViewById(R.id.backpage);
        mIphone = (EditText) findViewById(R.id.iphone);
        mVerifi = (EditText) findViewById(R.id.verifi);
        mGetVerifi = (Button) findViewById(R.id.get_verifi);
        mPassword = (EditText) findViewById(R.id.password);
        mCheckAgreement = (CheckBox) findViewById(R.id.check_agreement);
        mAccountRegist = (Button) findViewById(R.id.account_regist);
        mMent = (TextView) findViewById(R.id.ment);
        mUploadphoto = (LinearLayout) findViewById(R.id.uploadphoto);
        mHeadPortrait = (LinearLayout) findViewById(R.id.head_portrait);

        mHeadPortrait.setBackgroundResource(R.mipmap.default_headportrait);
        mUploadphoto.getBackground().setAlpha(200);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.backpage:
                startActivity(new Intent(this, LoginActivity.class));
                finish();
                break;
            case R.id.iphone:
                break;
            case R.id.verifi:
                break;
            case R.id.get_verifi:
                final String phoneNumber = mIphone.getText().toString();
                if (MobileUtil.checkPhone(phoneNumber) == false) {
                    mIphone.setText("");
                    mVerifi.setText("");
                    mPassword.setText("");
                    Toast.makeText(this, "请输入正确的手机号再获取验证码", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "走到发送验证码逻辑，但该功能还未实现", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.password:
                break;
            case R.id.check_agreement:
                break;
            case R.id.account_regist:
                if (TextUtils.isEmpty(mIphone.getText()) == false && TextUtils.isEmpty(mVerifi.getText()) == false && TextUtils.isEmpty(mPassword.getText()) == false) {
                    if (mCheckAgreement.isChecked() == true) {
                        Toast.makeText(this, "注册成功", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(this, LoadingActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                }
                break;
            case R.id.ment:
                startActivity(new Intent(this, AgreementActivity.class));
                break;
            case R.id.uploadphoto:
                mUploadphotos();
                break;
            case R.id.head_portrait:
                mUploadphotos();
                break;
        }
    }

    private void mUploadphotos() {
        PhotoChioceDialog dialog = new PhotoChioceDialog(this);
        dialog.setClickCallback(this);
        dialog.show();
    }

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
        Log.d(TAG, phoneNumber + verifiNumber + passwordText);
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



    @Override
    public void doCamera() {
        EasyPhotos.createCamera(this)//参数说明：上下文
                .setFileProviderAuthority(".activity.RegistActivity")//参数说明：见下方`FileProvider的配置`
                .start(102);

    }

    @Override
    public void doAlbum() {
        EasyPhotos.createAlbum(this, true, GlideEngine.getInstance())
                .setFileProviderAuthority(".activity.RegistActivity")
                .setMinWidth(500)//参数说明：最小宽度500px
                .setMinHeight(500)//参数说明：最小高度500px
                .setMinFileSize(1024 * 10)//参数说明：最小文件大小10K
                .start(101);
    }

    @Override
    public void doCancel() {

    }
}


