package com.mryu.devstudy.utils;
import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mryu.devstudy.R;
import com.mryu.devstudy.fragment.HomeFragment;

import static android.widget.Toast.LENGTH_LONG;

/**
 * Created by Liquor on 2020/11/17.
 * Description: the utils for toast.
 */

public class ToastUtils {

    private static final String TAG = "ToastUtils";
    private static Toast mShortToast;
    private static Toast mLongToast;
    private static Toast mKevinShortToast;

    public static void showToast(Context context, String message) {
        if (mShortToast == null) {
            mShortToast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        }else {
            // 考虑Activity切换时，Toast依然显示
            if (!mShortToast.getClass().getName().endsWith(context.getClass().getName())) {
                mShortToast = Toast.makeText(context,message,Toast.LENGTH_SHORT);
            }
        }
        mShortToast.setText(message);
        mShortToast.show();
    }

    public static void showToast(String message, Context context) {
        if (mShortToast == null) {
            mShortToast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        }else {
            // 考虑Activity切换时，Toast依然显示
            if (!mShortToast.getClass().getName().endsWith(context.getClass().getName())) {
                mShortToast = Toast.makeText(context,message,Toast.LENGTH_SHORT);
            }
        }
        mShortToast.setText(message);
        mShortToast.show();
    }

    public static void showLongToast(Context context, String message) {
        if (mLongToast == null) {
            mLongToast = Toast.makeText(context, message, LENGTH_LONG);
        }
        mLongToast.setText(message);
        mLongToast.show();
    }

    public static void showLongToast(String message, Context context) {
        if (mLongToast == null) {
            mLongToast = Toast.makeText(context, message, LENGTH_LONG);
        }
        mLongToast.setText(message);
        mLongToast.show();
    }

    /**
     * customize short toast.
     *
     * @param context
     * @param message
     * @param resId   the resource id for imageView
     */
    public static void showKevinToast(Context context, String message, int resId,double toastHight) {
        mKevinShortToast = new Toast(context);
        LayoutInflater inflate = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflate.inflate(R.layout.activity_toast_style, null);
        mKevinShortToast.setView(v);
        int height = context.getResources().getDisplayMetrics().heightPixels;
        mKevinShortToast.setGravity(Gravity.TOP, 0, (int) (height * toastHight));
        TextView tv = (TextView) v.findViewById(R.id.toast_text);
        ImageView iv = (ImageView) v.findViewById(R.id.toast_image);
        tv.setText(message);
        iv.setImageResource(resId);
        mKevinShortToast.setDuration(Toast.LENGTH_SHORT);
        mKevinShortToast.show();
    }
}
