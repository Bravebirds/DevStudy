package com.mryu.devstudy.activity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.mryu.devstudy.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class ScendActivity extends AppCompatActivity implements View.OnClickListener {
    private CheckBox mHorstiy;
    private CheckBox mTiao;
    private RadioButton mSexMan;
    private RadioButton mSexWman;
    private RadioGroup mSexMain;
    private Spinner mSpinnerArry;
    private List lists;
    private ArrayAdapter<String> arrayAdapter;
    private Button mUpdateData;
    private Button mDeleteData;
    private Button mUpdataImage;
    private ImageView mImages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scend);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        initView();
        setView();
    }

    private void setView() {
        mHorstiy.setOnClickListener(this);
        mTiao.setOnClickListener(this);
        mUpdateData.setOnClickListener(this);
        mDeleteData.setOnClickListener(this);
        mUpdataImage.setOnClickListener(this);
        mSexMain.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.sex_man:
                        Toast.makeText(getApplicationContext(), mSexMan.getText().toString(), Toast.LENGTH_LONG).show();
                    case R.id.sex_wman:
                        Toast.makeText(getApplicationContext(), mSexWman.getText().toString(), Toast.LENGTH_LONG).show();
                }
            }
        });
        mSpinnerArry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), "" + lists.get(position).toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void initView() {
        mHorstiy = (CheckBox) findViewById(R.id.horstiy);
        mTiao = (CheckBox) findViewById(R.id.tiao);
        mSexMan = (RadioButton) findViewById(R.id.sex_man);
        mSexWman = (RadioButton) findViewById(R.id.sex_wman);
        mSexMain = (RadioGroup) findViewById(R.id.sex_main);
        mSpinnerArry = (Spinner) findViewById(R.id.spinner_arry);
        lists = new ArrayList<>();
        lists.add("Test1");
        lists.add("Test2");
        arrayAdapter = new ArrayAdapter<String>(
                ScendActivity.this, android.R.layout.simple_expandable_list_item_1, lists
        );
        mSpinnerArry.setAdapter(arrayAdapter);
        mUpdateData = (Button) findViewById(R.id.update_data);
        mDeleteData = (Button) findViewById(R.id.delete_data);
        mUpdataImage = (Button) findViewById(R.id.updata_image);
        mImages = (ImageView) findViewById(R.id.images);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.horstiy:
                break;
            case R.id.tiao:
                break;
            case R.id.update_data:
                lists.add("添加数据");
                arrayAdapter.notifyDataSetChanged();
                break;
            case R.id.delete_data:
                lists.remove("添加数据");
                arrayAdapter.notifyDataSetChanged();
                break;
            case R.id.updata_image:
                List<String> ImageList = Arrays.asList("https://profile.csdnimg.cn/B/6/E/2_qq_38795430","https://csdnimg.cn/medal/1024@240.png");
                Random random = new Random();
                Glide.with(getApplicationContext()).load(ImageList.get(random.nextInt(ImageList.size()))).into(mImages);
                break;
        }
    }

}
