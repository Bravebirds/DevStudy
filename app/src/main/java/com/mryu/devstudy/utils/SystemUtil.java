package com.mryu.devstudy.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Point;
import android.text.TextUtils;
import android.view.Display;
import android.view.WindowManager;

import java.util.List;
public class SystemUtil {

    private final static String TAG = SystemUtil.class.getSimpleName();
    private static String displaySize;
    private static String homePkgName;
    private static int width = -1;
    private static int height = -1;

    private SystemUtil() {
    }

    /**
     * 获取手机的android版本
     */
    public static String getOsVersion() {
        return android.os.Build.VERSION.RELEASE;
    }

    /**
     * 获取手机厂商
     */
    public static String getDeviceBrand() {
        return android.os.Build.BRAND;
    }

    /**
     * 获取手机的设备型号
     */
    public static String getDeviceModel() {
        return android.os.Build.MODEL;
    }

    /**
     * 获取手机的设备ID
     */
    public static String getSerialNumber() {
        return android.os.Build.SERIAL;
    }

    /**
     * 获取手机的分辨率
     */
    public static String getDisplaySize(Context context) {
        if (context == null) return "unKonwn";
        if (displaySize == null) {
            WindowManager windowManager = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE));
            if (windowManager != null) {
                Display display = windowManager.getDefaultDisplay();
                Point p = new Point();
                display.getSize(p);
                displaySize = p.x + "*" + p.y;
            }
        }
        return displaySize;
    }

    /**
     * 获取屏幕的宽度
     */
    public static int getDisplayWidth(Context context) {
        if (context == null) return -1;
        if (width == -1) {
            WindowManager windowManager = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE));
            if (windowManager != null) {
                Display display = windowManager.getDefaultDisplay();
                Point p = new Point();
                display.getSize(p);
                width = p.x;
            }
        }
        return width;
    }

    /**
     * 获取屏幕的高度
     */
    public static int getDisplayHeight(Context context) {
        if (context == null) return -1;
        if (height == -1) {
            WindowManager windowManager = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE));
            if (windowManager != null) {
                Display display = windowManager.getDefaultDisplay();
                Point p = new Point();
                display.getSize(p);
                height = p.y;
            }
        }
        return height;
    }

    /**
     * 获取应用版本号
     */
    public static String getProductVersion(Context context) {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            return info.versionName;
        } catch (Exception e) {
            e.printStackTrace();
            return "unKnown";
        }
    }

    /**
     * 获取手机桌面的包名
     */
    public static String getHomePkgName(Context context) {
        if (context == null) return "unKonwn";
        if (homePkgName == null) {
            PackageManager packageManager = context.getPackageManager();
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            List<ResolveInfo> resolveInfo = packageManager.queryIntentActivities(intent,
                    PackageManager.MATCH_DEFAULT_ONLY);
            for (ResolveInfo ri : resolveInfo) {
                String packageName = ri.activityInfo.packageName;
                if (!TextUtils.isEmpty(packageName) && (packageName.contains("launcher") ||
                        packageName.contains("home"))) {
                    homePkgName = packageName;
                    break;
                }
            }
        }
        return homePkgName;
    }

}
