package com.mryu.devstudy.utils;
import java.text.SimpleDateFormat;
import java.util.Date;
public class TimeUtil {

    /**
     * 获取系统时间
     */
    public static String getSystemTime()
    {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        String dataStr = df.format(new Date());
        return dataStr;
    }

    /**
     * 获取时间戳
     */
    public static String getTimestamp() {
        return String.valueOf(new Date().getTime() / 1000);
    }

    /**
     * 将日期从data类型转换为String类型
     * @param data
     */
    public static String getDataFormat(Date data)
    {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        String dataStr = df.format(data);
        return dataStr;
    }

}
