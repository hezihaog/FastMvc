package oms.mmc.android.fast.framwork.manager.iml;

/**
 * Package: com.hzh.perfmon.inter
 * FileName: PerfMonAtion
 * Date: on 2018/1/13  下午6:06
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */

public interface PerfMonAtion {
    /**
     * 回收方法
     */
    void recycle();

    /**
     * 在application的onTrimMemory时调用该方法
     * @param level 等级
     */
    void trimMemory(int level);

    /**
     * 在application的onTrimMemory时调用该方法
     */
    void trimMemory();

    /**
     * 在application的onLowMemory时调用该方法
     */
    void lowMemory();
}
