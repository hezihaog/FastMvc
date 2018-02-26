package oms.mmc.android.fast.framwork.loadview;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Package: oms.mmc.android.fast.framwork.loadview
 * FileName: ILoadMoreViewFactory
 * Date: on 2018/2/26  下午5:46
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */

public interface ILoadMoreViewFactory {
    /**
     * 构建一个加载更多尾部布局
     */
    ILoadMoreView madeLoadMoreView();

    /**
     * RecyclerView底部加载更多的布局切换
     */
    interface ILoadMoreView {
        /**
         * 初始化
         *
         * @param list
         * @param onClickLoadMoreListener 加载更多的点击事件，需要点击调用加载更多的按钮都可以设置这个监听
         */
        void init(RecyclerView list, View.OnClickListener onClickLoadMoreListener);

        /**
         * 显示普通布局，整个item是空白的
         */
        void showNormal();

        /**
         * 显示已经加载完成，没有更多数据的布局
         */
        void showNoMore();

        /**
         * 显示正在加载中的布局
         */
        void showLoading();

        /**
         * 显示加载失败的布局
         */
        void showFail();

        /**
         * 获取加载更多尾部视图
         */
        View getFootView();
    }
}