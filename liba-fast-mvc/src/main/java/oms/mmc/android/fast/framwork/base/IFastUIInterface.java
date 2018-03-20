package oms.mmc.android.fast.framwork.base;

import oms.mmc.factory.wait.inter.IWaitViewHost;

/**
 * Package: oms.mmc.android.fast.framwork.base
 * FileName: IFastUIInterface
 * Date: on 2018/3/20  下午5:45
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */

public interface IFastUIInterface extends LayoutCallback,
        IWaitViewHandler, IHandlerDispatcher, IWaitViewHost, IInstanceState, IStatusBarOperate {

    /**
     * 创建UI代理
     */
    IFastUIDelegate createFastUIDelegate();

    /**
     * 获取UI代理
     */
    IFastUIDelegate getFastUIDelegate();
}