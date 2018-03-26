package oms.mmc.android.fast.framwork.widget.list;

import android.view.View;

import oms.mmc.android.fast.framwork.widget.list.helper.IAssistHelper;
import oms.mmc.android.fast.framwork.widget.rv.base.BaseTpl;

/**
 * Created by wally on 18/3/25.
 * 通用适配器
 */

public interface ICommonListAdapter {
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

    void notifyDataSetChanged();

    void setAssistHelper(IAssistHelper assistHelper);

    IAssistHelper getAssistHelper();
}