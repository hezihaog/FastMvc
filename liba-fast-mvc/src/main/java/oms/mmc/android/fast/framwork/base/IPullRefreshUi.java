package oms.mmc.android.fast.framwork.base;

import oms.mmc.android.fast.framwork.widget.pull.IPullRefreshLayout;
import oms.mmc.android.fast.framwork.widget.pull.IPullRefreshWrapper;

/**
 * Package: oms.mmc.android.fast.framwork.base
 * FileName: IPullRefreshAction
 * Date: on 2018/3/21  下午9:54
 * Auther: zihe
 * Descirbe:下拉刷新界面需要实现的接口
 * Email: hezihao@linghit.com
 */

public interface IPullRefreshUi {
    /**
     * 回调界面使用对应的下拉刷新控件来生成对应的包裹类
     *
     * @param pullToRefreshLayout 布局中使用的下拉刷新控件
     */
    IPullRefreshWrapper<?> onPullRefreshWrapperReady(IPullRefreshLayout pullToRefreshLayout);
}