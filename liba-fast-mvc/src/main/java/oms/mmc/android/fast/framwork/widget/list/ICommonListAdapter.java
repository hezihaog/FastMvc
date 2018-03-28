package oms.mmc.android.fast.framwork.widget.list;

import java.util.ArrayList;

import oms.mmc.android.fast.framwork.widget.list.helper.IAssistHelper;
import oms.mmc.helper.ListScrollHelper;
import oms.mmc.helper.adapter.IListScrollViewAdapter;

/**
 * Created by wally on 18/3/25.
 * 通用适配器
 */

public interface ICommonListAdapter<T> extends IListScrollViewAdapter, AdapterListenerInterface {
    /**
     * 提醒数据集已改变，提醒刷新数据
     */
    void notifyDataSetChanged();

    /**
     * 设置下拉刷新数据集
     */
    void setRefreshListData(ArrayList<T> data, boolean isReverse, boolean isFirst);

    /**
     * 设置加载更多数据集
     */
    void setLoadMoreListData(ArrayList<T> data, boolean isReverse, boolean isFirst);

    /**
     * 设置数据集
     */
    void setListData(ArrayList<T> listData);

    /**
     * 获取列表数据
     *
     * @return 数据集
     */
    ArrayList<T> getListData();

    /**
     * adapter中数据是否为空
     *
     * @return true则为数据集为空，false为不为空
     */
    boolean isEmpty();

    /**
     * 设置多选、单选等功能帮助类
     *
     * @param assistHelper 功能帮助类
     */
    void setAssistHelper(IAssistHelper assistHelper);

    /**
     * 获取功能帮助类
     */
    IAssistHelper getAssistHelper();

    /**
     * 设置滚动帮助类
     */
    void setListScrollHelper(ListScrollHelper listScrollHelper);

    /**
     * 获取滚动帮助类
     */
    ListScrollHelper getListScrollHelper();
}