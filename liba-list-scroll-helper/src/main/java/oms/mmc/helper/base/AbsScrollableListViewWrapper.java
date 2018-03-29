package oms.mmc.helper.base;

import android.widget.AbsListView;

import oms.mmc.helper.widget.ScrollableListView;

/**
 * Package: oms.mmc.android.fast.framwork.widget.list.wrapper
 * FileName: AbsScrollableListViewWrapper
 * Date: on 2018/3/29  上午11:29
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */

public class AbsScrollableListViewWrapper<V extends ScrollableListView> extends AbsScrollableViewWrapper<V> {
    private int oldVisibleItem = 0;
    //第一次进入界面时也会回调滚动，所以当手动滚动再监听
    private boolean isNotFirst = false;

    public AbsScrollableListViewWrapper(V scrollingView) {
        super(scrollingView);
    }

    @Override
    public void setup(final ScrollDelegate delegate, V scrollableView) {
        scrollableView.addOnListViewScrollListener(new ScrollableListView.OnListViewScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView listView, int scrollState) {
                isNotFirst = true;
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    if (delegate != null) {
                        if (listView.getLastVisiblePosition() + 1 == listView.getCount()) {
                            delegate.onScrolledToBottom();
                        } else if (listView.getFirstVisiblePosition() == 0) {
                            delegate.onScrolledToTop();
                        }
                    }
                }
            }

            @Override
            public void onScroll(AbsListView listView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (delegate != null) {
                    if (firstVisibleItem > oldVisibleItem && isNotFirst) {
                        //上滑
                        delegate.onScrolledToUp();
                    }
                    if (oldVisibleItem > firstVisibleItem && isNotFirst) {
                        //下滑
                        delegate.onScrolledToDown();
                    }
                    oldVisibleItem = firstVisibleItem;
                }
            }
        });
    }

    @Override
    public void moveToTop() {
        getScrollableView().setSelection(0);
    }

    @Override
    public void smoothMoveToTop() {
        getScrollableView().smoothScrollToPosition(0);
    }
}