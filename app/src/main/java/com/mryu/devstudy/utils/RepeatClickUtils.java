package com.mryu.devstudy.utils;

public class RepeatClickUtils {
    // 两次点击按钮之间的点击间隔不能少于500毫秒
    private static final int MIN_CLICK_DELAY_TIME = 300;
    private static final int MIN_CLICK_HuanMan_TIME = 500;
    private static long lastClickTime;
    public static boolean isFastClick() {
        boolean flag = false;
        long curClickTime = System.currentTimeMillis();
        if ((curClickTime - lastClickTime) >= MIN_CLICK_DELAY_TIME) {
            flag = true;
        }
        if ((curClickTime - lastClickTime) >= MIN_CLICK_HuanMan_TIME) {
            flag = true;
        }
        lastClickTime = curClickTime;
        return flag;
    }
}
