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

public interface IPullRefreshWrapper<T extends IPullRefreshLayout> extends IPullRefreshLayoutOperator {
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
     * 开始刷新，没有带动画，同样用于初次进度界面，直接刷新数据
     */
    void startRefresh();

    /**
     * 开启刷新动画，用于手动控制动画显示，不依赖于刷新动画是 {@link #startRefreshWithAnimation} 来调用
     */
    void startRefreshAnimation();

    /**
     * 停止刷新动画，用于手动控制动画结束，不依赖于结束动画是 {@link #setRefreshComplete} 来调用
     */
    void stopRefreshAnimation();

    /**
     * 开始刷新，并带有动画（例如SwipeRefreshLayout有个加载圈转动）
     */
    void startRefreshWithAnimation();

    /**
     * 给外部刷新时调用，停止刷新（例如停止刷新动画等）
     */
    void setRefreshComplete();

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