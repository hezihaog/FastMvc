package oms.mmc.android.fast.framwork.config.base;

import android.util.LruCache;

import oms.mmc.android.fast.framwork.config.ConfigOperate;
import oms.mmc.android.fast.framwork.config.util.AppDiskCache;


/**
 * Created by Hezihao on 2017/8/4.
 * 基础配置，只包含配置控制器
 */

public abstract class BaseConfig implements IConfig {
    private ConfigOperate operate;
    private LruCache<String, Object> mLruCache;
    private AppDiskCache mDiskCache;

    public BaseConfig() {
        operate = getOperate();
        mLruCache = getOperate().getLruCache();
        mDiskCache = getOperate().getDiskCache();
    }

    @Override
    public ConfigOperate getOperate() {
        return ConfigOperate.getInstance();
    }

    public LruCache<String, Object> getLruCache() {
        return mLruCache;
    }

    public AppDiskCache getDiskCache() {
        return mDiskCache;
    }
}
