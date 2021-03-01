package com.mryu.devstudy.entity;
import android.graphics.drawable.Drawable;

public class LocalAppInfo {

    private Drawable image;
    private String packageName;

    public LocalAppInfo(Drawable image, String packageName) {
        this.image = image;
        this.packageName = packageName;
    }
    public LocalAppInfo() {

    }
    public Drawable getImage() {
        return image;
    }

    public void setImage(Drawable image) {
        this.image = image;
    }

    public String getpackageName() {
        return packageName;
    }

    public void setpackageName(String packageName) {
        this.packageName = packageName;
    }
}