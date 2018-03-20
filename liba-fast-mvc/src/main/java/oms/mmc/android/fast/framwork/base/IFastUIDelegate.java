package oms.mmc.android.fast.framwork.base;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import oms.mmc.android.fast.framwork.util.ViewFinder;
import oms.mmc.factory.wait.inter.IWaitViewController;

/**
 * Package: oms.mmc.android.fast.framwork.base
 * FileName: IFastUIInterface
 * Date: on 2018/3/20  下午2:54
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */

public interface IFastUIDelegate extends IHandlerDispatcher {
    /**
     * 代理onCreate()
     */
    void onCreate(Bundle savedInstanceState);

    /**
     * 代理onDestroy()
     */
    void onDestroy();

    /**
     * 设置UI实现载体
     *
     * @param uiIml UI载体
     */
    void attachUIIml(IFastUIInterface uiIml);

    /**
     * 代理执行界面载体的onLayoutBefore()
     */
    void performLayoutBefore();

    /**
     * 代理执行界面载体的onLayoutView()
     */
    void performLayoutView(ViewGroup container);

    /**
     * 代理执行界面载体的onFindView()
     */
    void performFindView();

    /**
     * 代理执行界面载体的onLayoutAfter()
     */
    void performLayoutAfter();

    /**
     * 获取Activity
     */
    Activity getActivity();

    /**
     * 获取Activity的ContentView
     */
    View getContentView();

    /**
     * 获取视图查找器
     */
    ViewFinder getViewFinder();

    /**
     * 获取等待弹窗控制器
     */
    IWaitViewController getWaitViewController();

    /**
     * 获取根布局
     */
    View getRootView();

    /**
     * 获取界面Window
     */
    Window getWindow();
}