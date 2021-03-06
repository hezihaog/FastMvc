package oms.mmc.android.fast.framwork.widget.rv.base;

import android.support.v7.widget.RecyclerView;

import oms.mmc.android.fast.framwork.widget.rv.adapter.HeaderFooterAdapter;

/**
 * Package: oms.mmc.android.fast.framwork.widget.rv.base
 * FileName: RecyclerViewListConfigCallback
 * Date: on 2018/3/24  下午11:25
 * Auther: zihe
 * Descirbe:rv配置回调
 * Email: hezihao@linghit.com
 */

public interface RecyclerViewListConfigCallback extends IListConfigCallback {
    /**
     * 获取RecyclerView的LayoutManager
     */
    RecyclerView.LayoutManager onGetListLayoutManager();

    /**
     * 获取rv适配器
     */
    HeaderFooterAdapter getRecyclerViewAdapter();
}