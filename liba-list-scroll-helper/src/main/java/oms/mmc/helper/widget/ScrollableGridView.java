package oms.mmc.helper.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.AbsListView;
import android.widget.GridView;
import android.widget.ListAdapter;

import java.util.ArrayList;

import oms.mmc.helper.adapter.IListScrollViewAdapter;
import oms.mmc.helper.base.IScrollableAdapterView;
import oms.mmc.helper.util.ListScrollViewAdapterUtil;


/**
 * Package: oms.mmc.android.fast.framwork.base
 * FileName: ScrollableGridView
 * Date: on 2018/2/11  下午6:11
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */

public class ScrollableGridView extends GridView implements IScrollableAdapterView {
    private final ArrayList<OnListViewScrollListener> mListener =
            new ArrayList<OnListViewScrollListener>();

    public ScrollableGridView(Context context) {
        super(context);
        init();
    }

    public ScrollableGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ScrollableGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        //内部持有监听器，对外开放监听器组
        setOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                for (ScrollableGridView.OnListViewScrollListener listener : mListener) {
                    listener.onScrollStateChanged(view, scrollState);
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                for (ScrollableGridView.OnListViewScrollListener listener : mListener) {
                    listener.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
                }
            }
        });
    }

    @Override
    public void setListAdapter(IListScrollViewAdapter adapter) {
        ListScrollViewAdapterUtil.isValidListAdapter(adapter);
        super.setAdapter((ListAdapter) adapter);
    }

    @Override
    public IListScrollViewAdapter getListAdapter() {
        return (IListScrollViewAdapter) super.getAdapter();
    }

    public interface OnListViewScrollListener {
        void onScrollStateChanged(AbsListView view, int scrollState);

        void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount);
    }

    public void addOnListViewScrollListener(ScrollableGridView.OnListViewScrollListener listener) {
        mListener.add(listener);
    }

    public void removeOnListViewScrollListener(ScrollableGridView.OnListViewScrollListener listener) {
        mListener.remove(listener);
    }

    public void removeAllOnListViewScrollListener() {
        mListener.clear();
    }
}
