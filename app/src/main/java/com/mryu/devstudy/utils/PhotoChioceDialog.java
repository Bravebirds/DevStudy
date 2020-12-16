package com.mryu.devstudy.utils;

import android.content.Context;
import android.view.View;

import com.mryu.devstudy.R;

public class PhotoChioceDialog extends BaseDialog{
    private ClickCallback clickCallback;
    public PhotoChioceDialog(Context context){
        super(context);
        dialog.setContentView(R.layout.dialog_uploaddcim);
        dialog.findViewById(R.id.photograph).setOnClickListener(this);
        dialog.findViewById(R.id.photoalbum).setOnClickListener(this);
        dialog.findViewById(R.id.cancel).setOnClickListener(this);
        setDialogLocation(mContext, dialog);
    }

    public void setClickCallback(ClickCallback clickCallback) {
        this.clickCallback = clickCallback;
    }

    public interface ClickCallback {
        /**
         * 进入相机
         */
        void doCamera();

        /**
         * 进入相册
         */
        void doAlbum();

        /**
         * 取消
         */
        void doCancel();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.photoalbum:
                if (clickCallback!=null){
                    clickCallback.doAlbum();
                }
                break;
            case R.id.photograph:
                if (clickCallback!=null){
                    clickCallback.doCamera();
                }
                break;
            case R.id.cancel:
                if (clickCallback!=null){
                    clickCallback.doCancel();
                }
                break;
        }
    }
}