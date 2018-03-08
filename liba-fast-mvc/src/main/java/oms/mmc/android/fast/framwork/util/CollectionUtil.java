package oms.mmc.android.fast.framwork.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Package: oms.mmc.android.fast.framwork.util
 * FileName: CollectionUtil
 * Date: on 2018/3/2  上午10:08
 * Auther: zihe
 * Descirbe:集合工具类
 * Email: hezihao@linghit.com
 */

public class CollectionUtil {
    /**
     * 将数组转成可变的List
     *
     * @param array 数组
     */
    public static <E> List<E> arrayToList(E[] array) {
        ArrayList<E> list = new ArrayList<E>();
        list.addAll(Arrays.asList(array));
        return list;
    }

    /**
     * 将数组转为可变Set集合
     *
     * @param array 数组
     */
    public static <E> Set<E> arrayToSet(E[] array) {
        HashSet<E> set = new HashSet<E>();
        set.addAll(Arrays.asList(array));
        return set;
    }

    /**
     * 用另外一个List来遍历，读写分离
     */
    public static <T> List<T> getSnapshot(Collection<T> other) {
        List<T> result = new ArrayList<T>(other.size());
        for (T item : other) {
            result.add(item);
        }
        return result;
    }
}