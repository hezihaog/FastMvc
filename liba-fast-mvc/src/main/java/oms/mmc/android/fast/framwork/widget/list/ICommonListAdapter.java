package oms.mmc.android.fast.framwork.widget.list;

import android.view.View;

import java.util.ArrayList;

import oms.mmc.android.fast.framwork.widget.list.helper.IAssistHelper;
import oms.mmc.android.fast.framwork.widget.rv.base.BaseTpl;
import oms.mmc.helper.ListScrollHelper;

/**
 * Created by wally on 18/3/25.
 * 通用适配器
 */

public interface ICommonListAdapter<T> {
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

    void setListScrollHelper(ListScrollHelper listScrollHelper);

    ListScrollHelper getListScrollHelper();

    /**
     * 列表的条目点击事件
     */
    interface OnScrollableViewItemClickListener {
        void onItemClick(View view, BaseTpl clickTpl, int position);
    }

    /**
     * 列表的条目长按事件
     */
    interface OnScrollableViewItemLongClickListener {
        boolean onItemLongClick(View view, BaseTpl longClickTpl, int position);
    }

    /**
     * 添加列表条目点击监听
     */
    void addOnItemClickListener(OnScrollableViewItemClickListener onItemClickListener);

    /**
     * 移除指定的列表条目点击监听
     *
     * @param onItemClickListener 列表条目点击监听
     */
    void removeOnItemClickListener(OnScrollableViewItemClickListener onItemClickListener);

    /**
     * 添加列表条目长按监听
     */
    void addOnItemLongClickListener(OnScrollableViewItemLongClickListener onItemLongClickListener);

    /**
     * 移除列表条目长按监听
     *
     * @param onItemLongClickListener 列表条目长按监听
     */
    void removeOnItemLongClickListener(OnScrollableViewItemLongClickListener onItemLongClickListener);
}