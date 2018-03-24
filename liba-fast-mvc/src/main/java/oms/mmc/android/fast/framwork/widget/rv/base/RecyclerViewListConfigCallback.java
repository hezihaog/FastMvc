package oms.mmc.android.fast.framwork.widget.rv.base;

import android.support.v7.widget.RecyclerView;

/**
 * Package: oms.mmc.android.fast.framwork.widget.rv.base
 * FileName: RecyclerViewListConfigCallback
 * Date: on 2018/3/24  下午11:25
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */

public interface RecyclerViewListConfigCallback {
    /**
     * 获取RecyclerView的LayoutManager
     */
    RecyclerView.LayoutManager onGetListLayoutManager();
}