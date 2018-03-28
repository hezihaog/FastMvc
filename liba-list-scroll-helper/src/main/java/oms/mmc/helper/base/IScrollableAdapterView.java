package oms.mmc.helper.base;

import android.view.View;

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

    /**
     * 添加依附的监听器，其实就是View上面的，由于View是类，而不是接口，这里做同名覆盖给外部调用
     *
     * @param listener 监听器
     */
    void addOnAttachStateChangeListener(View.OnAttachStateChangeListener listener);

    /**
     * 根据位置，查找出条目View
     *
     * @param position 位置
     */
    View getViewByPosition(int position);
}