package oms.mmc.android.fast.framwork.util;

import android.view.View;

/**
 * Package: oms.mmc.android.fast.framwork.util
 * FileName: IScrollableHeaderFooter
 * Date: on 2018/4/3  上午9:56
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */
public interface IScrollableHeaderFooter {
    /**
     * 添加一个View作为列表头部
     *
     * @param view 要作为头部的View
     */
    void addHeaderView(View view);

    /**
     * 移除一个指定的头部
     *
     * @param view 头部View
     */
    boolean removeHeader(View view);

    /**
     * 添加一个View作为尾部
     *
     * @param view 要作为尾部的View
     */
    void addFooterView(View view);

    /**
     * 移除一个指定的尾部
     *
     * @param view 尾部View
     */
    boolean removeFooter(View view);
}