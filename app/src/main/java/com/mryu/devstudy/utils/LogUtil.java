package com.mryu.devstudy.utils;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;

import com.mryu.devstudy.BuildConfig;

/**
 * 日志工具
 * 日志目前只是根据DEBUG 状态做区分打印， 正式版本不打印v和d
 * 计时功能，用于调试，计算代码运行时间
 */
public class LogUtil {

    private static final boolean DEBUG = BuildConfig.DEBUG;
    private static final String TAG = "MonkeyLocalScript";
    private static final String TAG_ADD = TAG + "_";

    private static long sLastRecordTime = 0L;

    public static void v(String msg) {
        if (DEBUG) {
            if(!TextUtils.isEmpty(msg))
                Log.v(TAG, msg);
        }
    }

    public static void d(String msg) {
        if (DEBUG) {
            if(!TextUtils.isEmpty(msg))
                Log.d(TAG, msg);
        }
    }

    public static void i(String msg) {
        Log.i(TAG, msg);
    }

    public static void w(String msg) {
        Log.w(TAG, msg);
    }

    public static void e(String msg) {
        Log.e(TAG, msg);
    }

    public static void e(String msg, Throwable e) {
        Log.e(TAG, msg, e);
    }

    public static void v(String tag, String msg) {
        if (DEBUG) {
            if(!TextUtils.isEmpty(msg))
                Log.v(TAG_ADD + tag, msg);
        }
    }

    public static void d(String tag, String msg) {
        if (DEBUG) {
            if(!TextUtils.isEmpty(msg))
                Log.d(TAG_ADD + tag, msg);
        }
    }

    public static void i(String tag, String msg) {
        if(!TextUtils.isEmpty(msg))
            Log.i(TAG_ADD + tag, msg);
    }

    public static void w(String tag, String msg) {
        if(!TextUtils.isEmpty(msg))
            Log.w(TAG_ADD + tag, msg);
    }

    public static void e(String tag, String msg) {
        if(!TextUtils.isEmpty(msg))
            Log.e(TAG_ADD + tag, msg);
    }

    public static void e(String tag, String msg, Throwable e) {
        if(!TextUtils.isEmpty(msg))
            Log.e(TAG_ADD + tag, msg, e);
    }

    /**
     * 打印当前操作距离上一次进入的时间
     *
     * @param mark 时间打印的标记
     */
    public static void recordTime(String mark) {
        if (DEBUG) {
            long current = SystemClock.elapsedRealtime();
            Log.i(TAG, mark + " cost:" + (current - sLastRecordTime));
            sLastRecordTime = current;
        }
    }

    private LogUtil() {
        throw new UnsupportedOperationException("Prohibited!");
    }
    public static void D( String msg) {
        if(!TextUtils.isEmpty(msg))
            D(TAG, msg);
    }
    /**
     * Log数据太长分段输出日志
     */
    public static void D(String tag, String msg) {
        if (DEBUG) {
            if (tag == null || tag.length() == 0
                    || msg == null || msg.length() == 0)
                return;

            int segmentSize = 3 * 1024;
            long length = msg.length();
            if (length <= segmentSize) {// 长度小于等于限制直接打印
                Log.d(tag, msg);
            } else {
                while (msg.length() > segmentSize) {// 循环分段打印日志
                    String logContent = msg.substring(0, segmentSize);
                    msg = msg.replace(logContent, "");
                    Log.d(tag, logContent);
                }
                Log.d(tag, msg);// 打印剩余日志
            }
        }
    }

}
