package oms.mmc.android.fast.framwork.util;

import oms.mmc.android.fast.framwork.widget.pull.IPullRefreshLayout;
import oms.mmc.helper.widget.ScrollableRecyclerView;

/**
 * Package: oms.mmc.android.fast.framwork.util
 * FileName: IRecyclerViewListAbleDelegateHelper
 * Date: on 2018/3/29  上午10:11
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */

public interface IRecyclerViewListAbleDelegateHelper<P extends IPullRefreshLayout, V extends ScrollableRecyclerView> extends IListAbleDelegateHelper<P, V> {
    /**
     * 嵌套NestedScrollView时在onListReady()时调用，放弃滚动，将滚动交给上层
     */
    void compatNestedScroll();
}