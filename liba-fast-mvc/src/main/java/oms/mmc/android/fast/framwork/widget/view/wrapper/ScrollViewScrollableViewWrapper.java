package oms.mmc.android.fast.framwork.widget.view.wrapper;

import com.hzh.logger.L;

import oms.mmc.android.fast.framwork.widget.view.ScrollableScrollView;
import oms.mmc.android.fast.framwork.widget.view.base.AbsScrollableViewWrapper;

/**
 * Package: oms.mmc.android.fast.framwork.base
 * FileName: ScrollViewScrollableViewWrapper
 * Date: on 2018/2/11  下午10:05
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */

public class ScrollViewScrollableViewWrapper extends AbsScrollableViewWrapper<ScrollableScrollView> {

    public ScrollViewScrollableViewWrapper(ScrollableScrollView scrollingView) {
        super(scrollingView);
    }

    @Override
    public void setup(final ScrollDelegate delegate) {
        getScrollableView().addScrollChangedListener(new ScrollableScrollView.OnScrollChangedListener() {
            @Override
            public void onScrollChanged(int l, int t, int oldl, int oldt) {
                if (delegate != null) {
                    L.d("onScrollChanged ::: " + "l" + l + " t" + t + "oldl " + oldl + "oldt " + oldt);
                }
            }

            @Override
            public void onScrolledToBottom() {
                if (delegate != null) {
                    delegate.onScrolledToBottom();
                }
            }

            @Override
            public void onScrolledToTop() {
                if (delegate != null) {
                    delegate.onScrolledToTop();
                }
            }
        });
    }

    @Override
    public void moveToTop() {
        getScrollableView().scrollTo(0, 0);
    }

    @Override
    public void smoothMoveToTop() {
        getScrollableView().smoothScrollBy(0, 0);
    }
}