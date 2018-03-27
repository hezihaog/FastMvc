package oms.mmc.helper.base;

import oms.mmc.helper.adapter.IListScrollViewAdapter;

/**
 * Package: oms.mmc.helper.base
 * FileName: IScrollableAdapterView
 * Date: on 2018/3/27  下午8:54
 * Auther: zihe
 * Descirbe:适配器类型的ScrollableView
 * Email: hezihao@linghit.com
 */

public interface IScrollableAdapterView extends IScrollableView {
    /**
     * 设置滚动控件的Adapter
     *
     * @param adapter 适配器
     */
    void setListAdapter(IListScrollViewAdapter adapter);

    /**
     * 获取滚动控件的适配器
     */
    IListScrollViewAdapter getListAdapter();
}