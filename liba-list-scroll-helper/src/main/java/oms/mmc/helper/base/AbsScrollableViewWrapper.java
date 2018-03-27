package oms.mmc.helper.base;

import oms.mmc.helper.adapter.IListScrollViewAdapter;

/**
 * Package: oms.mmc.android.fast.framwork.base
 * FileName: AbsScrollableViewWrapper
 * Date: on 2018/2/9  下午5:08
 * Auther: zihe
 * Descirbe:抽象的ScrollingView包裹类，子类有RecyclerView等
 * Email: hezihao@linghit.com
 */

public abstract class AbsScrollableViewWrapper<T extends IScrollableView> implements IScrollableViewWrapper<T> {
    private T scrollingView;

    public AbsScrollableViewWrapper(T scrollingView) {
        this.scrollingView = scrollingView;
    }

    @Override
    public T getScrollableView() {
        return scrollingView;
    }

    @Override
    public void setAdapter(IListScrollViewAdapter adapter) {
        if (getScrollableView() instanceof IScrollableAdapterView) {
            ((IScrollableAdapterView) getScrollableView()).setListAdapter(adapter);
        }
    }
}