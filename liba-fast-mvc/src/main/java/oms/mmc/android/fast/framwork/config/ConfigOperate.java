package oms.mmc.android.fast.framwork.config;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.LruCache;

import java.io.Serializable;

import oms.mmc.android.fast.framwork.config.util.AppDiskCache;

/**
 * Created by Hezihao on 2017/8/4.
 * 配置管理器，使用内存和磁盘缓存用户配置操作
 */

public class ConfigOperate {
    private static final int M = 1024 * 1024;
    private static LruCache<String, Object> mLruCache = new LruCache<String, Object>(1 * M);
    private static AppDiskCache mDiskCache;

    private ConfigOperate() {
        mDiskCache = AppDiskCache.getInstance();
    }

    private static class Singleton {
        private static ConfigOperate instance = new ConfigOperate();
    }

    public static ConfigOperate getInstance() {
        return Singleton.instance;
    }

    public LruCache<String, Object> getLruCache() {
        return mLruCache;
    }

    public AppDiskCache getDiskCache() {
        return mDiskCache;
    }

    /**
     * 存储
     *
     * @param key
     * @param value
     * @param <T>
     */
    public <T extends Serializable> void saveData(@NonNull String key, @NonNull T value) {
        mLruCache.put(key, value);
        mDiskCache.put(key, value);
    }

    /**
     * 取出
     *
     * @param key
     * @param defaultValue 默认值
     * @param <T>
     * @return
     */
    public <T extends Serializable> T getData(@NonNull String key, @Nullable T defaultValue) {
        //先找内存缓存
        T result = (T) mLruCache.get(key);
        if (result != null) {
            return result;
        }
        //内存缓存没有，找本地，找到则返回并且保存到内存缓存
        result = (T) mDiskCache.getSerializable(key, defaultValue);
        if (result != null) {
            mLruCache.put(key, result);
            return result;
        }
        return defaultValue;
    }
}