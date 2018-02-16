package oms.mmc.android.fast.framwork.widget.rv.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import oms.mmc.android.fast.framwork.widget.rv.base.BaseTpl;
import oms.mmc.android.fast.framwork.util.IDataSource;
import oms.mmc.android.fast.framwork.util.RecyclerViewViewHelper;

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
    ArrayList<T> getListViewData();

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

    RecyclerView getRecyclerView();

    RecyclerViewViewHelper<T> getRecyclerViewHelper();

    HashMap<Integer, Class> getViewTypeClassMap();

    void setListData(ArrayList<T> listViewData);

    void setRecyclerViewHelper(RecyclerViewViewHelper helper);
}