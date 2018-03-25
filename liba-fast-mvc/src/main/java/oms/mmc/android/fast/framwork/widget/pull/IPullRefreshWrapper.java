package oms.mmc.android.fast.framwork.widget.pull;

import android.content.Context;

/**
 * Package: oms.mmc.android.fast.framwork.widget.pull
 * FileName: IPullRefreshWrapper
 * Date: on 2018/3/21  下午3:07
 * Auther: zihe
 * Descirbe:下拉刷新布局包裹接口，可操作刷新布局的操作
 * Email: hezihao@linghit.com
 */

public interface IPullRefreshWrapper<T extends IPullRefreshLayout> extends IPullRefreshLayoutOperator, IPullRefreshWithDelayAction {
    /**
     * 依附上刷新布局
     *
     * @param refreshLayout 刷新布局
     */
    void attachRefreshLayout(T refreshLayout);

    /**
     * 获取可刷新视图
     */
    T getPullRefreshAbleView();

    /**
     * 获取上下文
     */
    Context getContext();

    /**
     * 设置可以下拉刷新
     */
    void setCanPullToRefresh();

    /**
     * 设置为下拉刷新为禁用状态
     */
    void setNotPullToRefresh();

    /**
     * 当前下拉刷新是否为可用状态
     */
    boolean isCanPullToRefresh();

    /**
     * 下拉刷新是否不使用
     *
     * @return true为可用，false为不使用
     */
    boolean isNotPullToRefresh();

    /**
     * 设置下拉刷新回调监听
     *
     * @param listener 监听器
     */
    void setOnRefreshListener(OnRefreshListener listener);

    /**
     * post一个任务到主线程执行
     *
     * @param runnable 任务对象
     */
    void post(Runnable runnable);

    /**
     * post一定延时时间后，在主线程执行任务
     *
     * @param runnable    任务对象
     * @param delayMillis 延时时间毫秒数
     */
    void postDelayed(Runnable runnable, long delayMillis);

    /**
     * 下拉刷新回调监听器
     */
    interface OnRefreshListener {
        /**
         * 调用刷新时回调
         */
        void onRefresh();
    }

    /**
     * 获取设置的刷新监听器
     */
    OnRefreshListener getRefreshListener();
}