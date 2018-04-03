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
     * @param view 要作为头部的View
     */
    void addHeaderView(View view);

    boolean removeHeader(View view);

    void addFooterView(View view);

    boolean removeFooter(View view);
}