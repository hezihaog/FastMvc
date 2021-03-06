package oms.mmc.helper.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AbsListView;
import android.widget.HeaderViewListAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import oms.mmc.helper.adapter.IListScrollViewAdapter;
import oms.mmc.helper.base.IScrollableListAdapterView;
import oms.mmc.helper.util.ListScrollViewAdapterUtil;

/**
 * Package: oms.mmc.android.fast.framwork.widget.view
 * FileName: ScrollableListView
 * Date: on 2018/2/11  下午6:10
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */

public class ScrollableListView extends ListView implements IScrollableListAdapterView {
    private final ArrayList<IScrollableListAdapterView.OnListViewScrollListener> mListener
            = new ArrayList<OnListViewScrollListener>();

    public ScrollableListView(Context context) {
        super(context);
        init();
    }

    public ScrollableListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ScrollableListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        //内部持有监听器，对外开放监听器组
        setOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                for (IScrollableListAdapterView.OnListViewScrollListener listener : mListener) {
                    listener.onScrollStateChanged(view, scrollState);
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                for (IScrollableListAdapterView.OnListViewScrollListener listener : mListener) {
                    listener.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
                }
            }
        });
    }

    @Override
    public void setListAdapter(IListScrollViewAdapter adapter) {
        ListScrollViewAdapterUtil.isValidListAdapter(adapter);
        setAdapter((ListAdapter) adapter);
    }

    @Override
    public IListScrollViewAdapter getListAdapter() {
        ListAdapter adapter = super.getAdapter();
        //在ListView加了尾部或者头部时，adapter会被动态HeaderViewListAdapter包裹一层，所以这时候需要拿到包裹的那个adapter才是最原始的adapter
        if (adapter instanceof HeaderViewListAdapter) {
            return (IListScrollViewAdapter) ((HeaderViewListAdapter) adapter).getWrappedAdapter();
        } else {
            //在未包裹之前，直接转换即可。
            return (IListScrollViewAdapter) adapter;
        }
    }

    @Override
    public void addOnListViewScrollListener(IScrollableListAdapterView.OnListViewScrollListener listener) {
        mListener.add(listener);
    }

    @Override
    public void removeOnListViewScrollListener(IScrollableListAdapterView.OnListViewScrollListener listener) {
        mListener.remove(listener);
    }

    @Override
    public void removeAllOnListViewScrollListener() {
        mListener.clear();
    }

    /**
     * 根据指定位置，查找View
     */
    @Override
    public View getViewByPosition(int position) {
        int firstListItemPosition = this.getFirstVisiblePosition();
        int lastListItemPosition = firstListItemPosition
                + this.getChildCount() - 1;

        if (position < firstListItemPosition || position > lastListItemPosition) {
            return this.getAdapter().getView(position, null, this);
        } else {
            final int childIndex = position - firstListItemPosition;
            return this.getChildAt(childIndex);
        }
    }
}
