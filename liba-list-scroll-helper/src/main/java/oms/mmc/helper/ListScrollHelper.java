package oms.mmc.helper;


import java.util.ArrayList;
import java.util.List;

import oms.mmc.helper.base.IScrollHelper;
import oms.mmc.helper.base.IScrollableView;
import oms.mmc.helper.base.IScrollableViewWrapper;
import oms.mmc.helper.listener.IListScrollListener;

/**
 * Package: oms.mmc.android.fast.framwork.base
 * FileName: ListScrollHelper
 * Date: on 2018/2/9  下午3:18
 * Auther: zihe
 * Descirbe:列表滚动帮助类
 * Email: hezihao@linghit.com
 */

public class ListScrollHelper<V extends IScrollableView> implements IScrollHelper {
    private ArrayList<IListScrollListener> listeners = new ArrayList<IListScrollListener>();
    private IScrollableViewWrapper<V> scrollableViewWrapper;

    public ListScrollHelper(IScrollableViewWrapper<V> scrollableViewWrapper) {
        this.scrollableViewWrapper = scrollableViewWrapper;
        this.scrollableViewWrapper.setup(new IScrollableViewWrapper.ScrollDelegate() {
            @Override
            public void onScrolledToUp() {
                for (IListScrollListener listener : listeners) {
                    listener.onScrolledUp();
                }
            }

            @Override
            public void onScrolledToDown() {
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
        }, scrollableViewWrapper.getScrollableView());
    }

    @Override
    public IScrollableViewWrapper<V> getScrollableViewWrapper() {
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