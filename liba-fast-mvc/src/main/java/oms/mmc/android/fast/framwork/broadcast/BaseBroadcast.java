package oms.mmc.android.fast.framwork.broadcast;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import oms.mmc.android.fast.framwork.util.BroadcastHelper;

/**
 * Package: oms.mmc.android.fast.framwork.broadcast
 * FileName: BaseBroadcast
 * Date: on 2018/2/6  下午5:20
 * Auther: zihe
 * Descirbe:广播封装基类
 * Email: hezihao@linghit.com
 */

public abstract class BaseBroadcast {
    private HashMap<String, Serializable> datas;

    public BaseBroadcast() {
        datas = new HashMap<String, Serializable>();
    }

    /**
     * 发送广播
     */
    public void send(Context context) {
        Intent intent = new Intent(onGetBroadcastAction());
        for (Map.Entry<String, Serializable> entry : getDatas().entrySet()) {
            intent.putExtra(entry.getKey(), entry.getValue());
        }
        //限制广播只在当前应用传递
        limit(context, intent);
        BroadcastHelper.sendBroadcast(context, intent, onGetBroadcastAction());
    }

    protected abstract String onGetBroadcastAction();

    public HashMap<String, Serializable> getDatas() {
        return datas;
    }

    public BaseBroadcast put(String key, Serializable value) {
        datas.put(key, value);
        return this;
    }

    public Serializable get(String key, Serializable defaultValue) {
        Serializable value = datas.get(key);
        if (value == null) {
            return defaultValue;
        }
        return value;
    }

    /**
     * 限制广播只在当前应用传递
     */
    private void limit(Context context, Intent intent) {
        String packageName = getAppPackageName(context);
        if (!TextUtils.isEmpty(packageName)) {
            intent.setPackage(packageName);
        }
    }

    /**
     * 获取当前App的包名
     */
    private static String getAppPackageName(Context context) {
        String packageName;
        try {
            PackageManager manager = context.getPackageManager();
            //getPackageName()是当前App的包名，0代表是获取版本信息
            PackageInfo packageInfo = manager.getPackageInfo(context.getPackageName(), 0);
            packageName = packageInfo.packageName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            packageName = "";
        }
        return packageName;
    }
}