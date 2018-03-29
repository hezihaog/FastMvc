package oms.mmc.android.fast.framwork.base;

import oms.mmc.android.fast.framwork.util.ListAbleDelegateHelper;
import oms.mmc.android.fast.framwork.widget.pull.IPullRefreshLayout;
import oms.mmc.helper.base.IScrollableAdapterView;

/**
 * Package: oms.mmc.android.fast.framwork.base
 * FileName: IListAbleDelegateHelperHost
 * Date: on 2018/3/29  上午11:03
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */

public interface IListAbleDelegateHelperHost<P extends IPullRefreshLayout, V extends IScrollableAdapterView> {
    /**
     * 初始化列表代理帮助类
     */
    ListAbleDelegateHelper<P, V> onInitListAbleDelegateHelper();
}