package oms.mmc.helper.util;

import oms.mmc.helper.adapter.IListScrollViewAdapter;

/**
 * Package: oms.mmc.helper.util
 * FileName: ListScrollViewAdapterUtil
 * Date: on 2018/3/27  下午11:41
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */

public class ListScrollViewAdapterUtil {
    /**
     * 校验给滚动控件设置的adapter是否实现了{@link IListScrollViewAdapter}接口
     *
     * @param adapter 要设置的adapter
     */
    public static void isValidListAdapter(IListScrollViewAdapter adapter) {
        if (adapter == null) {
            throw new NullPointerException("给滚动控件设置Adapter必须不为null");
        }
        if (!(adapter instanceof IListScrollViewAdapter)) {
            throw new IllegalArgumentException("给滚动控件设置Adapter必须实现了IListScrollViewAdapter接口");
        }
    }
}