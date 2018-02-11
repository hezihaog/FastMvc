package oms.mmc.android.fast.framwork.widget.view;

import java.util.ArrayList;
import java.util.List;

import oms.mmc.android.fast.framwork.widget.view.base.IScrollHelper;
import oms.mmc.android.fast.framwork.widget.view.base.IScrollableViewWrapper;
import oms.mmc.android.fast.framwork.widget.view.listener.IListScrollListener;

/**
 * Package: oms.mmc.android.fast.framwork.base
 * FileName: ListScrollHelper
 * Date: on 2018/2/9  下午3:18
 * Auther: zihe
 * Descirbe:列表滚动帮助类
 * Email: hezihao@linghit.com
 */

public class ListScrollHelper implements IScrollHelper {
    private ArrayList<IListScrollListener> listeners = new ArrayList<IListScrollListener>();
    private IScrollableViewWrapper scrollableViewWrapper;

    public ListScrollHelper(IScrollableViewWrapper scrollableViewWrapper) {
        this.scrollableViewWrapper = scrollableViewWrapper;
        this.scrollableViewWrapper.setup(new IScrollableViewWrapper.ScrollDelegate() {
            @Override
            public void onScrolledUp() {
                for (IListScrollListener listener : listeners) {
                    listener.onScrolledUp();
                }
            }

            @Override
            public void onScrolledDown() {
                for (IListScrollListener listener : listeners) {
                    listener.onScrolledDown();
                }
            }

            @Override
            public void onScrolledToTop() {
                for (IListScrollListener listener : listeners) {
                    listener.onScrollTop();
                }
            }

            @Override
            public void onScrolledToBottom() {
                for (IListScrollListener listener : listeners) {
                    listener.onScrollBottom();
                }
            }
        });
    }

    @Override
    public IScrollableViewWrapper getScrollableViewWrapper() {
        return scrollableViewWrapper;
    }

    @Override
    public void addListScrollListener(IListScrollListener listener) {
        listeners.add(listener);
    }

    @Override
    public void removeListScrollListener(IListScrollListener listener) {
        listeners.remove(listener);
    }

    @Override
    public void removeAllScrollListener() {
        listeners.clear();
    }

    @Override
    public List<IListScrollListener> getScrollListeners() {
        return listeners;
    }

    @Override
    public void moveToTop() {
        getScrollableViewWrapper().moveToTop();
    }

    @Override
    public void smoothMoveToTop() {
        getScrollableViewWrapper().smoothMoveToTop();
    }
}