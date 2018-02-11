package oms.mmc.android.fast.framwork.widget.view.wrapper;

import android.widget.AbsListView;

import oms.mmc.android.fast.framwork.widget.view.ScrollableListView;
import oms.mmc.android.fast.framwork.widget.view.base.AbsScrollableViewWrapper;

/**
 * Package: oms.mmc.android.fast.framwork.base
 * FileName: ListViewScrollableViewWrapper
 * Date: on 2018/2/11  下午6:30
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */

public class ListViewScrollableViewWrapper extends AbsScrollableViewWrapper<ScrollableListView> {
    private int oldVisibleItem = 0;
    //第一次进入界面时也会回调滚动，所以当手动滚动再监听
    private boolean isFirst = true;

    public ListViewScrollableViewWrapper(ScrollableListView scrollingView) {
        super(scrollingView);
    }

    @Override
    public void setup(final ScrollDelegate delegate) {
        getScrollableView().addOnListViewScrollListener(new ScrollableListView.OnListViewScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView listView, int scrollState) {
                isFirst = true;
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
                    if (firstVisibleItem > oldVisibleItem && isFirst) {
                        //上滑
                        delegate.onScrolledUp();
                    }
                    if (oldVisibleItem > firstVisibleItem && isFirst) {
                        //下滑
                        delegate.onScrolledDown();
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