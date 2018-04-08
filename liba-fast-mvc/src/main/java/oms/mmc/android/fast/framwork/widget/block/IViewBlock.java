package oms.mmc.android.fast.framwork.widget.block;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;

import oms.mmc.android.fast.framwork.base.IHandlerDispatcher;
import oms.mmc.android.fast.framwork.base.IWaitViewHandler;
import oms.mmc.android.fast.framwork.base.LayoutCallback;

/**
 * Package: oms.mmc.android.fast.framwork.widget.block
 * FileName: IViewBlock
 * Date: on 2018/4/6  下午9:12
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */
public interface IViewBlock extends LayoutCallback, IWaitViewHandler, IHandlerDispatcher {
    /**
     * 获取Activity
     */
    Activity getActivity();

    /**
     * 初始化View
     */
    void initView();

    /**
     * 获取根布局
     */
    View getRoot();

    /**
     * 移除自己
     */
    void removeSelf();

    /**
     * 获取要添加进的位置，默认是添加进布局的尾部
     *
     * @param parent 布局
     */
    int onGetAddViewIndex(ViewGroup parent);

    /**
     * 获取添加进父布局时使用的布局参数
     */
    ViewGroup.LayoutParams onGetLayoutParams();
}