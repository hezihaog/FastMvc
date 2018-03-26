package oms.mmc.android.fast.framwork.widget.rv.adapter;

import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import oms.mmc.android.fast.framwork.base.IDataSource;
import oms.mmc.android.fast.framwork.util.RecyclerViewViewHelper;
import oms.mmc.helper.ListScrollHelper;

/**
 * Package: oms.mmc.android.fast.framwork.base
 * FileName: IMultiTypeAdapter
 * Date: on 2018/2/12  下午2:54
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */

public interface IMultiTypeAdapter<T> {
    /**
     * 获取列表数据
     */
    ArrayList<T> getListData();

    /**
     * 获取原始数据集
     */
    IDataSource<T> getListDataSource();

    /**
     * 获取rv
     */
    RecyclerView getScrollableView();

    /**
     * 获取rv帮助类
     */
    RecyclerViewViewHelper<T> getRecyclerViewHelper();

    /**
     * 设置列表数据
     */
    void setListData(ArrayList<T> listData);

    /**
     * 设置滚动帮助类
     *
     * @param listScrollHelper 滚动帮助类实例
     */
    void setListScrollHelper(ListScrollHelper listScrollHelper);
}