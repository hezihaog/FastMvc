package oms.mmc.android.fast.framwork.widget.rv.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import oms.mmc.android.fast.framwork.base.IDataSource;
import oms.mmc.android.fast.framwork.util.RecyclerViewViewHelper;
import oms.mmc.android.fast.framwork.widget.rv.base.BaseTpl;
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
     * rv的条目点击事件
     */
    interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, BaseTpl clickTpl, int position);
    }

    /**
     * rv的条目长按事件
     */
    interface OnRecyclerViewItemLongClickListener {
        boolean onItemLongClick(View view, BaseTpl longClickTpl, int position);
    }

    /**
     * 获取列表数据
     */
    ArrayList<T> getListData();

    /**
     * 获取position对应的条目
     */
    T getItem(int position);

    /**
     * 获取原始数据
     */
    List<T> getOriginData();

    IDataSource<T> getListViewDataSource();

    /**
     * 添加rv条目点击监听
     */
    void addOnItemClickListener(OnRecyclerViewItemClickListener onItemClickListeners);

    /**
     * 添加rv条目长按监听
     */
    void addOnItemLongClickListener(OnRecyclerViewItemLongClickListener onItemLongClickListener);

    /**
     * 获取rv
     */
    RecyclerView getRecyclerView();

    /**
     * 获取rv帮助类
     */
    RecyclerViewViewHelper<T> getRecyclerViewHelper();

    /**
     * 获取类型映射Map
     */
    HashMap<Integer, Class> getViewTypeClassMap();

    /**
     * 设置列表数据
     */
    void setListData(ArrayList<T> listData);

    /**
     * 设置rv帮助类
     *
     * @param helper 帮助类实例
     */
    void setRecyclerViewHelper(RecyclerViewViewHelper helper);

    /**
     * 设置滚动帮助类
     *
     * @param listScrollHelper 滚动帮助类实例
     */
    void setListScrollHelper(ListScrollHelper listScrollHelper);
}