package oms.mmc.android.fast.framwork.widget.pull;

/**
 * Package: oms.mmc.android.fast.framwork.widget.pull
 * FileName: IPullRefreshLayoutOperator
 * Date: on 2018/3/21  下午4:24
 * Auther: zihe
 * Descirbe:刷新布局控制接口，对外控制刷新布局的可用状态的控制接口
 * Email: hezihao@linghit.com
 */

public interface IPullRefreshLayoutOperator {
    /**
     * 开始刷新，没有带动画，同样用于初次进度界面，直接刷新数据
     */
    void startRefresh();

    /**
     * 开始刷新，并带有动画（例如SwipeRefreshLayout有个加载圈转动）
     */
    void startRefreshWithAnimation();

    /**
     * 给外部刷新时调用，停止刷新（例如停止刷新动画等）
     */
    void completeRefresh();

    /**
     * 设置下拉刷新为可用
     */
    void setRefreshEnable();

    /**
     * 设置下拉刷新为不可用
     */
    void setRefreshDisable();

    /**
     * 当前下拉刷新是否为可用状态
     */
    boolean isRefreshEnable();

    /**
     * 当前下拉刷新是否是禁用的
     *
     * @return true为禁用状态，false为不是禁用
     */
    boolean isRefreshDisable();

    /**
     * 获取当前是否正在刷新
     *
     * @return true为正在刷新，false为不是在刷新
     */
    boolean isRefurbishing();
}