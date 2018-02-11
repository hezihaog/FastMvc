package oms.mmc.android.fast.framwork.sample.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * 时间格式化
 */
public class TimeUtil {
    private TimeUtil() {
        throw new RuntimeException("工具类不能实例化");
    }

    public static String getTime(String str) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        try {
            Date date = sdf.parse(str);
            return getTime(date.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }

    public static String getTimeChina(String str) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy年MM月dd日", Locale.getDefault());
        try {
            Date date = sdf.parse(str);
            return sdf2.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }

    public static String getTime(long timestamp) {
        String time;
        Calendar calendar = Calendar.getInstance();
        Calendar today = Calendar.getInstance();
        calendar.setTimeInMillis(timestamp);

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
//        int day_of_week = calendar.get(Calendar.DAY_OF_WEEK);
//        int week_of_month = calendar.get(Calendar.WEEK_OF_MONTH);
        int day_of_month = calendar.get(Calendar.DAY_OF_MONTH);
//        int hour_of_day = calendar.get(Calendar.HOUR_OF_DAY);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        int tyear = today.get(Calendar.YEAR);
        int tmonth = today.get(Calendar.MONTH) + 1;
//        int tweek_of_month = today.get(Calendar.WEEK_OF_MONTH);
        int tday_of_month = today.get(Calendar.DAY_OF_MONTH);

        if (year == tyear && month == tmonth && day_of_month == tday_of_month) {// 今天
            if (minute < 10) {
                time = hour + ":0" + minute;
            } else {
                time = hour + ":" + minute;
            }
        } else if (year == tyear && month == tmonth && tday_of_month - day_of_month == 1) {// 昨天
            time = "昨天";
        } else if (year == tyear) {// 本年
            time = month + "月" + day_of_month + "日";
        } else if (year < tyear) {// 去年以前
            time = year + "/" + month + "/" + day_of_month;
        } else {// 今天以后
            time = year + "/" + month + "/" + day_of_month;
        }
        return time;
    }
}