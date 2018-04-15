package oms.mmc.android.fast.framwork.base;

import android.os.Handler;

/**
 * Package: oms.mmc.android.fast.framwork.base
 * FileName: IHandlerDispatcher
 * Date: on 2018/3/5  上午11:01
 * Auther: zihe
 * Descirbe:Hanlder发送器
 * Email: hezihao@linghit.com
 */

public interface IHandlerDispatcher {
    /**
     * 初始化Handler
     */
    Handler initHandler();

    /**
     * 获取绑定的Handler
     */
    Handler getHandler();

    /**
     * 将任务发送到主线程执行
     *
     * @param runnable 任务
     */
    void post(Runnable runnable);

    /**
     * 延迟执行任务到主线程
     *
     * @param runnable 任务
     * @param duration 延时毫秒数
     */
    void postDelayed(Runnable runnable, long duration);

    /**
     * 移除指定正在运行的任务
     *
     * @param runnable 任务
     */
    void removeHandlerMessage(Runnable runnable);

    /**
     * 移除所有正在运行的任务
     */
    void removeHandlerAllMessage();
}