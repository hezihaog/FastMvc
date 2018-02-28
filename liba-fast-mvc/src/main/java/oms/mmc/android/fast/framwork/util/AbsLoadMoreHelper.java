package oms.mmc.android.fast.framwork.util;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import oms.mmc.android.fast.framwork.loadview.ILoadMoreViewFactory;
import oms.mmc.android.fast.framwork.widget.rv.adapter.HeaderFooterAdapter;

/**
 * Package: oms.mmc.android.fast.framwork.util
 * FileName: AbsLoadMoreHelper
 * Date: on 2018/2/28  下午6:10
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */

public abstract class AbsLoadMoreHelper implements ILoadMoreViewFactory.ILoadMoreView {
    private View mFooterView;

    private View.OnClickListener onClickRefreshListener;

    @Override
    public void init(RecyclerView list, View.OnClickListener onClickLoadMoreListener) {
        this.onClickRefreshListener = onClickLoadMoreListener;
        mFooterView = onInflateFooterView(LayoutInflater.from(list.getContext()), list, onClickLoadMoreListener);
        onInflateFooterViewAfter(mFooterView);
        //添加加载更多布局到尾部
        ((HeaderFooterAdapter) list.getAdapter()).addFooterView(mFooterView);
        showNormal();
    }

    @Override
    public void showNormal() {
        onShowNormal(mFooterView);
    }

    @Override
    public void showLoading() {
        onShowLoading(mFooterView);
    }

    @Override
    public void showError() {
        onShowError(mFooterView);
    }

    @Override
    public void showNoMore() {
        onShowNoMore(mFooterView);
    }

    @Override
    public View getFootView() {
        return mFooterView;
    }

    /**
     * 子类重写返回尾部布局
     */
    protected abstract View onInflateFooterView(LayoutInflater inflater, RecyclerView list, View.OnClickListener onClickLoadMoreListener);

    /**
     * 实例化尾部布局后回调，通常是查找控件
     * @param footerView
     */
    protected abstract void onInflateFooterViewAfter(View footerView);

    /**
     * 调用showNormal()时回调，子类重写，对尾部布局进行操作
     *
     * @param footerView 尾部布局
     */
    protected abstract void onShowNormal(View footerView);

    /**
     * 调用showNoMore()时回调，子类重写，对尾部布局进行操作
     *
     * @param footerView 尾部布局
     */
    protected abstract void onShowNoMore(View footerView);

    /**
     * 调用showLoading()时回调,子类重写，对尾部布局进行操作
     *
     * @param footerView 尾部布局
     */
    protected abstract void onShowLoading(View footerView);

    /**
     * 调用showError()时回调，子类重写，对尾部布局进行操作
     *
     * @param footerView 尾部布局
     */
    protected abstract void onShowError(View footerView);

    public View getFooterView() {
        return mFooterView;
    }

    public View.OnClickListener getOnClickRefreshListener() {
        return onClickRefreshListener;
    }
}