package oms.mmc.helper.base;

import android.view.View;
import android.widget.AbsListView;

/**
 * Package: oms.mmc.helper.base
 * FileName: IScrollableListView
 * Date: on 2018/3/28  下午3:34
 * Auther: zihe
 * Descirbe:滚动控件，List需要实现的接口
 * Email: hezihao@linghit.com
 */

public interface IScrollableListAdapterView extends IScrollableAdapterView {
    /**
     * 滚动监听
     */
    interface OnListViewScrollListener {
        void onScrollStateChanged(AbsListView view, int scrollState);

        void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount);
    }

    void addOnListViewScrollListener(IScrollableListAdapterView.OnListViewScrollListener listener);

    void removeOnListViewScrollListener(IScrollableListAdapterView.OnListViewScrollListener listener);

    void removeAllOnListViewScrollListener();

    void addOnAttachStateChangeListener(View.OnAttachStateChangeListener listener);

    View getViewByPosition(int pos);
}