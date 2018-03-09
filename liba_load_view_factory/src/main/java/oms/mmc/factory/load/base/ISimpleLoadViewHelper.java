package oms.mmc.factory.load.base;

import android.content.Context;
import android.view.View;

/**
 * Package: oms.mmc.factory.load.base
 * FileName: ISimpleLoadViewHelper
 * Date: on 2018/3/1  上午10:55
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */

public interface ISimpleLoadViewHelper {
    /**
     * 初始化
     *
     * @param content                要替换的View对象
     * @param onClickRefreshListener 点击刷新的监听
     */
    ISimpleLoadViewHelper init(View content, View.OnClickListener onClickRefreshListener);

    /**
     * 显示加载布局
     */
    void showLoading();

    /**
     * 显示空布局
     */
    void showEmpty();

    /**
     * 显示错误布局
     */
    void showError();

    /**
     * 恢复布局
     */
    void restore();

    /**
     * 获取替换帮助类
     */
    IVaryViewHelper getHelper();

    /**
     * 获取上下文
     */
    Context getContext();

    /**
     * 设置状态更新监听
     *
     * @param listener 监听器
     */
    void setOnStateUpdateListener(OnStateUpdateListener listener);

    /**
     * 当前是否是主线程
     */
    boolean isOnMainThread();

    /**
     * 在主线程上运行
     *
     * @param runnable 任务
     */
    void runOnMainThread(Runnable runnable);

    /**
     * 状态切换监听
     */
    interface OnStateUpdateListener {
        /**
         * 当显示正在加载布局时回调
         */
        void onShowLoading();

        /**
         * 当显示错误布局时回调
         */
        void onShowError();

        /**
         * 当显示空布局时回调
         */
        void onShowEmpty();

        /**
         * 当恢复布局时回调
         */
        void onRestore();
    }

    /**
     * 状态切换监听空实现
     */
    class OnStateUpdateAdapter implements OnStateUpdateListener {

        @Override
        public void onShowLoading() {

        }

        @Override
        public void onShowError() {

        }

        @Override
        public void onShowEmpty() {

        }

        @Override
        public void onRestore() {

        }
    }
}