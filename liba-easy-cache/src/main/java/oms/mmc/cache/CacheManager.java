package oms.mmc.cache;

import android.content.Context;
import android.content.pm.PackageManager;

import oms.mmc.cache.base.CacheFactory;
import oms.mmc.cache.base.CacheOperate;
import oms.mmc.cache.config.CacheConfig;

public class CacheManager {
    private CacheManager() {
    }

    private static final class SingletonHolder {
        private static final CacheManager instance = new CacheManager();
    }

    public static CacheManager getInstance() {
        return SingletonHolder.instance;
    }

    /**
     * 初始化
     *
     * @param context 上下文
     */
    public void init(Context context) {
        if (context == null) {
            throw new NullPointerException("context 不能为空");
        }
        int versionCode = 0;
        try {
            versionCode = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        CacheOperate.init(CacheConfig.newBuilder(context, versionCode).build());
    }

    /**
     * 移除所有内存中的缓存实例
     */
    public void removeAllCache() {
        CacheFactory.removeAllCache();
    }
}