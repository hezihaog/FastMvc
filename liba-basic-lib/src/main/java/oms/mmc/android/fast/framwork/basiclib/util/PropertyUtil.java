package oms.mmc.android.fast.framwork.basiclib.util;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.v4.util.ArrayMap;

import java.util.Map;

/**
 * Created by Hezihao on 2017/7/13.
 * SharedPreference工具类
 */

public class PropertyUtil {
    private static Context applicationContext;
    private static String fileName = "app.config";
    private static boolean isAtLeastGingerbread;

    static {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            isAtLeastGingerbread = true;
        }
    }

    private PropertyUtil() {
    }

    public static void init(Context context, String fileName) {
        applicationContext = context.getApplicationContext();
        PropertyUtil.fileName = fileName;
    }

    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    public static void apply(SharedPreferences.Editor editor) {
        if (isAtLeastGingerbread) {
            editor.apply();
        } else {
            editor.commit();
        }
    }

    private static Context context() {
        return applicationContext;
    }

    public static SharedPreferences getPreferences() {
        SharedPreferences pre = context().getSharedPreferences(fileName, Context.MODE_MULTI_PROCESS);
        return pre;
    }

    public static void setProperty(String key, int value) {
        SharedPreferences.Editor editor = getPreferences().edit();
        editor.putInt(key, value);
        apply(editor);
    }

    public static void setProperty(String key, boolean value) {
        SharedPreferences.Editor editor = getPreferences().edit();
        editor.putBoolean(key, value);
        apply(editor);
    }

    public static void setProperty(String key, String value) {
        SharedPreferences.Editor editor = getPreferences().edit();
        editor.putString(key, value);
        apply(editor);
    }

    public static void setProperty(String key, long value) {
        SharedPreferences.Editor editor = getPreferences().edit();
        editor.putLong(key, value);
        apply(editor);
    }

    public static void setProperty(String key, float value) {
        SharedPreferences.Editor editor = getPreferences().edit();
        editor.putFloat(key, value);
        apply(editor);
    }

    public static void setProperty(ArrayMap<String, Object> map) {
        SharedPreferences.Editor editor = getPreferences().edit();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if (value instanceof String) {
                editor.putString(key, (String) value);
            } else if (value instanceof Boolean) {
                editor.putBoolean(key, ((Boolean) value).booleanValue());
            } else if (value instanceof Float) {
                editor.putFloat(key, ((Float) value).floatValue());
            } else if (value instanceof Integer) {
                editor.putInt(key, ((Integer) value).intValue());
            } else if (value instanceof Long) {
                editor.putLong(key, ((Long) value).longValue());
            }
        }
        apply(editor);
    }

    public static void removeProperty(String key) {
        SharedPreferences.Editor editor = getPreferences().edit();
        editor.remove(key);
        editor.apply();
    }

    public static boolean getProperty(String key, boolean defValue) {
        return getPreferences().getBoolean(key, defValue);
    }

    public static String getProperty(String key, String defValue) {
        return getPreferences().getString(key, defValue);
    }

    public static int getProperty(String key, int defValue) {
        return getPreferences().getInt(key, defValue);
    }

    public static long getProperty(String key, long defValue) {
        return getPreferences().getLong(key, defValue);
    }

    public static float getProperty(String key, float defValue) {
        return getPreferences().getFloat(key, defValue);
    }
}
