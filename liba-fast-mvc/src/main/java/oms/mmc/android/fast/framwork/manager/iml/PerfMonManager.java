package oms.mmc.android.fast.framwork.manager.iml;

import java.util.Timer;
import java.util.TimerTask;

import oms.mmc.android.fast.framwork.manager.base.BaseManager;
import oms.mmc.android.fast.framwork.manager.inter.IManager;
import oms.mmc.android.fast.framwork.manager.inter.IManagerFactory;

/**
 * Created by Hezihao on 2017/7/7.
 * 性能管理类
 */

public class PerfMonManager extends BaseManager implements PerfMonAtion {
    private PerfMonManager() {
    }

    private static class Singleton {
        private static final PerfMonManager INSTANCE = new PerfMonManager();
    }

    public static PerfMonManager getInstance() {
        return Singleton.INSTANCE;
    }

    @Override
    public IManager init(IManagerFactory factory) {
        return getInstance();
    }

    @Override
    public void clearEnv() {
        super.clearEnv();
        //2分钟检测一次，70%时回收内存
        getInstance().initParams(60 * 1000 * 2, 0.7f);
    }

    /**
     * 初始化
     *
     * @param period       检测周期，多小毫秒进行检测一次
     * @param recycleRatio 回收百分比阈值，例如60%，就是0.6
     */
    public void initParams(long period, float recycleRatio) {
        if (period <= 0) {
            throw new IllegalArgumentException("period argument must greater than zero");
        }
        startMemoryTimeTask(period, recycleRatio);
    }

    /**
     * 检测内存定时器
     */
    private void startMemoryTimeTask(long period, final float recycleRatio) {
        try {
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    //当前已申请的内存：程序使用内存+多挖的内存
                    long totalMemory = Runtime.getRuntime().totalMemory();
                    //app自身耗的内存
                    long usedMemory = totalMemory - Runtime.getRuntime().freeMemory();
                    //最大内存总量
                    long maxMemory = Runtime.getRuntime().maxMemory();
                    //剩余内存
                    //                long totalFree = maxMemory - usedMemory;
                    float ratio = (1.0f * usedMemory) / (maxMemory * 1.0f);
                    //占用内存在60%时回收
                    if (ratio >= recycleRatio) {
                        recycle();
                    }
                }
            }, 1000, period);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    /**
     * 执行垃圾回收
     */
    private static void gc() {
        System.gc();
        System.runFinalization();
        System.gc();
    }

    @Override
    public void recycle() {
        gc();
    }

    @Override
    public void trimMemory(int level) {
        recycle();
    }

    @Override
    public void trimMemory() {
        recycle();
    }

    @Override
    public void lowMemory() {
        recycle();
    }
}
