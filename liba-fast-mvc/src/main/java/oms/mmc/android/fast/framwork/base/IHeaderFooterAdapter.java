package oms.mmc.android.fast.framwork.base;

import java.util.ArrayList;

import oms.mmc.android.fast.framwork.bean.BaseItemData;

/**
 * Package: oms.mmc.android.fast.framwork.base
 * FileName: IHeaderFooterAdapter
 * Date: on 2018/2/12  下午2:30
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */

public interface IHeaderFooterAdapter<T> {
    /**
     * 注册头部
     */
    void registerHeader(int viewType, Class headerTplClazz, BaseItemData headerData);

    /**
     * 注销头部
     */
    void unRegisterHeader(int viewType);

    /**
     * 注册尾部
     */
    void registerFooter(int viewType, Class footerTplClazz, BaseItemData footerData);

    /**
     * 注销尾部
     */
    void unRegisterFooter(int viewType);

    /**
     * 是否存在头部
     */
    boolean hasHeader();

    /**
     * 是否存在尾部
     */
    boolean hasFooter();

    /**
     * 获取头部条目的条目类型集合
     */
    ArrayList<Integer> getHeaderTypeList();

    /**
     * 获取尾部条目的条目类型集合
     */
    ArrayList<Integer> getFooterTypeList();
}