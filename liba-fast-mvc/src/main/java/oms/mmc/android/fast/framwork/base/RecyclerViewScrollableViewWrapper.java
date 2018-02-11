package oms.mmc.android.fast.framwork.base;

import android.support.v7.widget.RecyclerView;

/**
 * Package: oms.mmc.android.fast.framwork.base
 * FileName: RecyclerViewScrollableViewWapper
 * Date: on 2018/2/9  下午3:54
 * Auther: zihe
 * Descirbe:RecyclerView的滚动控件包裹类
 * Email: hezihao@linghit.com
 */

public class RecyclerViewScrollableViewWrapper extends AbsScrollableViewWrapper<RecyclerView> {
    public RecyclerViewScrollableViewWrapper(RecyclerView scrollingView) {
        super(scrollingView);
    }

    @Override
    public void setup(final ScrollDelegate delegate) {
        getScrollableView().addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (delegate != null) {
                    delegate.onScrollStateChanged(recyclerView, newState);
                    //如果滚动到最后一行，RecyclerView.canScrollVertically(1)的值表示是否能向上滚动，false表示已经滚动到底部
                    if (newState == RecyclerView.SCROLL_STATE_IDLE &&
                            !recyclerView.canScrollVertically(1)) {
                        delegate.onScrolledToBottom();
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (delegate != null) {
                    delegate.onScrolled(recyclerView, dx, dy);
                    //RecyclerView.canScrollVertically(-1)的值表示是否能向下滚动，false表示已经滚动到顶部
                    if (!recyclerView.canScrollVertically(-1)) {
                        delegate.onScrolledToTop();
                    }
                    if (dy < 0) {
                        delegate.onScrolledDown();
                    }
                    if (dy > 0) {
                        delegate.onScrolledUp();
                    }
                }
            }
        });
    }

    @Override
    public void moveToTop() {
        RecyclerView recyclerView = getScrollableView();
        if (recyclerView != null) {
            recyclerView.smoothScrollToPosition(0);
        }
    }
}
