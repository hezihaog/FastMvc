package oms.mmc.cache.base;

import java.io.Serializable;

import oms.mmc.cache.interf.ICache;

/**
 * Created by Hezihao on 2017/8/4.
 */

public abstract class BaseCache<P extends BaseCacheParams> implements ICache {
    private String versionSymbol;

    @Override
    public CacheOperate getOperate() {
        return CacheOperate.getInstance();
    }

    @Override
    public int getVersionCode() {
        return getOperate().getVersionCode();
    }

    @Override
    public String getVersionSymbol() {
        if (versionSymbol != null) {
            return versionSymbol;
        } else {
            versionSymbol = new StringBuilder()
                    .append("v")
                    .append(getVersionCode())
                    .append("_").toString();
            return versionSymbol;
        }
    }

    public abstract void removeCache(P params);

    public abstract void put(P params, Serializable target);

    public abstract <T extends Serializable> T get(P params);
}