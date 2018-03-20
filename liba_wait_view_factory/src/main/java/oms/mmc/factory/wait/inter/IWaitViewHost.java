package oms.mmc.factory.wait.inter;

import oms.mmc.factory.wait.factory.IWaitViewFactory;

/**
 * Package: oms.mmc.factory.wait.inter
 * FileName: IWaitVIewHost
 * Date: on 2018/3/10  下午6:00
 * Auther: zihe
 * Descirbe:WaitView宿主接口
 * Email: hezihao@linghit.com
 */

public interface IWaitViewHost {
    /**
     * 构造WaitView工厂
     */
    IWaitViewFactory onWaitDialogFactoryReady();

    /**
     * WaitView控制器
     */
    IWaitViewController getWaitViewController();
}