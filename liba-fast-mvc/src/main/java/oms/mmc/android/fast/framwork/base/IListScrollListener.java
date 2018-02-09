package oms.mmc.android.fast.framwork.base;

import android.view.View;

/**
 * Package: oms.mmc.android.fast.framwork.base
 * FileName: IListScrollListener
 * Date: on 2018/2/9  下午3:24
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */

public interface IListScrollListener {
    /**
     * 当前正在向上滚动
     */
    void onScrolledUp();

    /**
     * 当前正在向下滚动
     */
    void onScrolledDown();

    /**
     * 已经滚动到顶部
     */
    void onScrollTop();

    /**
     * 已近滚动到底部
     */
    void onScrollBottom();

    /**
     * 滚动状态改变时回调
     *
     * @param view     滚动控件
     * @param newState 当前状态
     */
    void onScrollStateChanged(View view, int newState);

    /**
     * 当滚动时回调
     *
     * @param view 滚动控件
     * @param dx   x轴滚动的距离
     * @param dy   y轴滚动的距离
     */
    void onScrolled(View view, int dx, int dy);
}