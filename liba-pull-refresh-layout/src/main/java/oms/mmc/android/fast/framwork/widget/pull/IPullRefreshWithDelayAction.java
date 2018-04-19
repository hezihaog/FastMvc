package oms.mmc.android.fast.framwork.widget.pull;

/**
 * Package: oms.mmc.android.fast.framwork.widget.pull
 * FileName: IPullRefreshWithDelayAction
 * Date: on 2018/3/25  上午10:35
 * Auther: zihe
 * Descirbe:刷新操作带延时
 * Email: hezihao@linghit.com
 */

public interface IPullRefreshWithDelayAction {
    /**
     * 延迟指定毫秒数执行刷新
     *
     * @param delayMillis 延迟毫秒数
     */
    void startRefresh(long delayMillis);

    /**
     * 延迟指定毫秒数执行带有动画的刷新
     *
     * @param delayMillis 延迟毫秒数
     */
    void startRefreshWithAnimation(long delayMillis);

    /**
     * 延迟指定毫秒数后结束刷新
     *
     * @param delayMillis 延迟毫秒数
     */
    void completeRefresh(long delayMillis);
}