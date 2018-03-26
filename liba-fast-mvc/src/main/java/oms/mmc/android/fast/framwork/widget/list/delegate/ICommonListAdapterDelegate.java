package oms.mmc.android.fast.framwork.widget.list.delegate;

import java.util.ArrayList;


/**
 * Created by wally on 18/3/25.
 * 列表适配器代理接口
 */

public interface ICommonListAdapterDelegate<T, I> {
    /**
     * 获取列表条目总数
     */
    int getListItemCount();

    /**
     * 根据位置，获取条目Id
     *
     * @param position 条目位置
     */
    long getListItemId(int position);

    /**
     * 获取列表条目类型总数
     */
    int getListItemViewTypeCount();

    /**
     * 根据列表的位置，获取条目的ViewTYpe类型
     *
     * @param position 条目位置
     */
    int getListItemViewType(int position);

    /**
     * 根据位置，获取数据源中的数据
     *
     * @param position 位置
     */
    T getItem(int position);

    /**
     * 根据类型创建Tpl
     *
     * @param viewType 条目类型
     */
    I createTpl(int viewType);

    /**
     * 更新Tpl
     *
     * @param listData 列表数据源
     * @param position 条目位置
     */
    void updateTpl(I tpl, ArrayList<T> listData, int position);
}