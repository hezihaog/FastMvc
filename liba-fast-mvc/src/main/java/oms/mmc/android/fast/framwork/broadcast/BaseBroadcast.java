package oms.mmc.android.fast.framwork.broadcast;

import android.content.Context;
import android.content.Intent;

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
    private final Intent intent;

    public BaseBroadcast() {
        datas = new HashMap<String, Serializable>();
        String action = onGetBroadcastAction();
        intent = new Intent(action);
    }

    /**
     * 发送广播
     */
    public void send(Context context) {
        Intent intent = getIntent();
        for (Map.Entry<String, Serializable> entry : getDatas().entrySet()) {
            intent.putExtra(entry.getKey(), entry.getValue());
        }
        BroadcastHelper.sendBroadcast(context, intent, onGetBroadcastAction());
    }

    protected abstract String onGetBroadcastAction();

    public HashMap<String, Serializable> getDatas() {
        return datas;
    }

    public Intent getIntent() {
        return intent;
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
}