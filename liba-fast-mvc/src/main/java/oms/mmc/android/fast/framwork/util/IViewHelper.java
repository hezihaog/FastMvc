package oms.mmc.android.fast.framwork.util;

/**
 * Rv帮助类的接口
 */
public interface IViewHelper {
    /**
     * 刷新
     */
    void refresh();

    /**
     * 获取界面切换的加载布局
     */
    ILoadViewFactory.ILoadView getLoadView();
}