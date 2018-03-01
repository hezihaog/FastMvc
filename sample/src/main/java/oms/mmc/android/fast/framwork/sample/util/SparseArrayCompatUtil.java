package oms.mmc.android.fast.framwork.sample.util;

import android.support.v4.util.SparseArrayCompat;

/**
 * Package: oms.mmc.android.fast.framwork.sample.util
 * FileName: SparseArrayUtil
 * Date: on 2018/3/1  下午6:45
 * Auther: zihe
 * Descirbe:SparseArray工具类
 * Email: hezihao@linghit.com
 */

public class SparseArrayCompatUtil {
    /**
     * 是否为空
     */
    public static boolean isEmpty(SparseArrayCompat sparseArrayCompat) {
        return sparseArrayCompat.size() == 0;
    }

    /**
     * 是否包含指定key
     */
    public static boolean containsKey(SparseArrayCompat sparseArrayCompat, int key) {
        return sparseArrayCompat.indexOfKey(key) != -1;
    }

    /**
     * 是否包含执行value
     */
    public static boolean containsValue(SparseArrayCompat sparseArrayCompat, Object value) {
        return sparseArrayCompat.indexOfValue(value) != -1;
    }
}