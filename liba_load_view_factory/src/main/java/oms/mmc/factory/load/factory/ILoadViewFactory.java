package oms.mmc.factory.load.factory;

import android.view.View;

/**
 * Package: oms.mmc.factory.load
 * FileName: ILoadViewFactory
 * Date: on 2018/2/23  下午8:55
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */

public interface ILoadViewFactory {
    /**
     * 构建一个界面切换加载器
     */
    ILoadView madeLoadView();

    /**
     * 切换加载中，加载失败等布局
     */
    interface ILoadView {
        /**
         * 初始化
         */
        void init(View view, View.OnClickListener onClickRefreshListener);

        /**
         * 显示加载中
         */
        void showLoading();

        /**
         * 显示无网络加载失败
         */
        void showError();

        /**
         * 显示空数据布局
         */
        void showEmpty();

        /**
         * 没有数据的时候，toast提示失败
         */
        void tipFail();

        /**
         * 显示原先的布局
         */
        void restore();
    }
}