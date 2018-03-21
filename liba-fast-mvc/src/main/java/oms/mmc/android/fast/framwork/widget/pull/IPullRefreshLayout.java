package oms.mmc.android.fast.framwork.widget.pull;

import android.content.Context;

/**
 * Package: oms.mmc.android.fast.framwork.widget.pull
 * FileName: IPullRefreshLayout
 * Date: on 2018/3/21  下午2:41
 * Auther: zihe
 * Descirbe:刷新布局控件需要实现的接口
 * Email: hezihao@linghit.com
 */

public interface IPullRefreshLayout extends IPullRefreshLayoutOperator {
    /**
     * 获取上下文
     */
    Context getContext();
}