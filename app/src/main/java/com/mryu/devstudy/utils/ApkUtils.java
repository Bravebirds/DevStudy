package com.mryu.devstudy.utils;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;
import com.mryu.devstudy.entity.LocalAppInfo;
import java.util.ArrayList;
import java.util.List;

/**
 * 扫描本地安装的应用,工具类
 */
public class ApkUtils {
    static  String TAG = "ApkTool";
    public static List<LocalAppInfo> mLocalInstallApps = null;

    public static List<LocalAppInfo> scanLocalInstallAppList(PackageManager packageManager) {
        List<LocalAppInfo> LocalAppInfos = new ArrayList<LocalAppInfo>();
        try {
            List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);
            for (int i = 0; i < packageInfos.size(); i++) {
                PackageInfo packageInfo = packageInfos.get(i);
                //过滤掉系统app
                if ((ApplicationInfo.FLAG_SYSTEM & packageInfo.applicationInfo.flags) != 0) {
                    continue;
                }else{
                    LocalAppInfo LocalAppInfo = new LocalAppInfo();
                    LocalAppInfo.setpackageName(packageInfo.packageName);
                    if (packageInfo.applicationInfo.loadIcon(packageManager) == null) {
                        continue;
                    }
                    LocalAppInfo.setImage(packageInfo.applicationInfo.loadIcon(packageManager));
                    LocalAppInfos.add(LocalAppInfo);
                }
            }

        }catch (Exception e){
            Log.e(TAG,"===============获取应用包信息失败");
        }
        return LocalAppInfos;
    }
}
