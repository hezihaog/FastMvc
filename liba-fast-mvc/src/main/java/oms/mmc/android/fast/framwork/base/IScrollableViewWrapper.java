package oms.mmc.android.fast.framwork.base;

import android.view.View;

/**
 * Package: oms.mmc.android.fast.framwork.base
 * FileName: IScrollableView
 * Date: on 2018/2/9  下午3:46
 * Auther: zihe
 * Descirbe:滚动控件包裹类接口，定义了一些滚动方法
 * Email: hezihao@linghit.com
 */

public interface IScrollableViewWrapper<T extends View & IScrollableView> {
    /**
     * 滚动代理接口，转接滚动控件的监听器滚动事件
     */
    interface ScrollDelegate<T extends View & IScrollableView> {
        /**
         * 当前正在向上滚动
         */
        void onScrolledUp();

        /**
         * 当前正在向下滚动
         */
        void onScrolledDown();

        /**
         * 滚动到顶部
         */
        void onScrolledToTop();

        /**
         * 滚动到底部
         */
        void onScrolledToBottom();
    }

    /**
     * 设置滚动代理
     *
     * @param delegate 代理对象
     */
    void setup(ScrollDelegate delegate);

    /**
     * 获取当前包裹的滚动控件对象
     *
     * @return 当前包裹的滚动控件对象
     */
    T getScrollableView();

    /**
     * 滚动到顶部
     */
    void moveToTop();
}