package oms.mmc.android.fast.framwork.util;

import oms.mmc.android.fast.framwork.tpl.LoadMoreFooterTpl;

/**
 * Package: oms.mmc.android.fast.framwork.util
 * FileName: ILoadMoreHelper
 * Date: on 2018/2/12  下午3:50
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */

public interface ILoadMoreHelper {
    /**
     * 添加加载更多尾部条目
     */
    void addLoadMoreTpl();

    /**
     * 移除加载更多尾部条目
     */
    void removeLoadMoreTpl();

    /**
     * 获取加载更多尾部条目的位置
     */
    int getLoadMoreTplPosition();

    /**
     * 设置加载更多
     */
    void setLoadMoreTplPosition(int position);

    /**
     * 是否已经添加了加载更多条目
     */
    boolean isAddLoadMoreItem();

    LoadMoreFooterTpl findLoaderMoreFootTpl();
}