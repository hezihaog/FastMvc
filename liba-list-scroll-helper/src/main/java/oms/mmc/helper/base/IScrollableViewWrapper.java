package oms.mmc.helper.base;

import oms.mmc.helper.adapter.IListScrollViewAdapter;

/**
 * Package: oms.mmc.android.fast.framwork.base
 * FileName: IScrollableView
 * Date: on 2018/2/9  下午3:46
 * Auther: zihe
 * Descirbe:滚动控件包裹类接口，定义了一些滚动方法
 * Email: hezihao@linghit.com
 */

public interface IScrollableViewWrapper<V extends IScrollableView> {
    /**
     * 滚动代理接口，转接滚动控件的监听器滚动事件
     */
    interface ScrollDelegate {
        /**
         * 当前正在向上滚动
         */
        void onScrolledToUp();

        /**
         * 当前正在向下滚动
         */
        void onScrolledToDown();

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
     * @param delegate       代理对象
     * @param scrollableView 滚动view
     */
    void setup(ScrollDelegate delegate, V scrollableView);

    /**
     * 设置列表控件适配器
     *
     * @param adapter 适配器
     */
    void setAdapter(IListScrollViewAdapter adapter);

    /**
     * 获取当前包裹的滚动控件对象
     *
     * @return 当前包裹的滚动控件对象
     */
    V getScrollableView();

    /**
     * 瞬时滚动到顶部
     */
    void moveToTop();

    /**
     * 缓缓滚动到顶部
     */
    void smoothMoveToTop();
}