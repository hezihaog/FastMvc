package oms.mmc.android.fast.framwork.util;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.ListView;

import oms.mmc.android.fast.framwork.loadview.ILoadMoreViewFactory;
import oms.mmc.android.fast.framwork.widget.rv.adapter.HeaderFooterAdapter;
import oms.mmc.android.fast.framwork.widget.rv.adapter.HeaderFooterDataAdapter;
import oms.mmc.helper.adapter.IListScrollViewAdapter;
import oms.mmc.helper.base.IScrollableAdapterView;
import oms.mmc.helper.base.IScrollableListAdapterView;
import oms.mmc.helper.base.IScrollableView;
import oms.mmc.helper.widget.ScrollableRecyclerView;

/**
 * Package: oms.mmc.android.fast.framwork.util
 * FileName: AbsLoadMoreHelper
 * Date: on 2018/2/28  下午6:10
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */

public abstract class AbsLoadMoreHelper implements ILoadMoreViewFactory.ILoadMoreView {
    private ViewGroup mFooterView;
    private IScrollableView mList;

    private View.OnClickListener onClickRefreshListener;
    //一开始设置的尾部布局，用于后续如果子类切换了部分尾部视图，剩余默认视图切换回来的作用
    private View mOriginLoadMoreView;
    private LayoutInflater mInflater;

    /**
     * 显示后的操作。无操作，压缩尾部高度来隐藏、恢复尾部高度
     */
    protected enum AfterAction {
        //无操作
        NO_ACTION,
        //压缩高度
        COMPRESS_HEIGHT,
        //恢复高度
        RESTORE_HEIGHT
    }

    @Override
    public void init(IScrollableAdapterView list, View.OnClickListener onClickLoadMoreListener, boolean enableLoadMoreFooter) {
        mInflater = LayoutInflater.from(list.getContext());
        this.mList = list;
        this.onClickRefreshListener = onClickLoadMoreListener;
        //创建一个布局包裹尾部布局
        this.mFooterView = new FrameLayout(list.getContext());
        if (list instanceof ScrollableRecyclerView) {
            this.mFooterView.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
        } else if (list instanceof IScrollableListAdapterView) {
            this.mFooterView.setLayoutParams(new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.WRAP_CONTENT));
        }
        //获取要使用的尾部布局
        mOriginLoadMoreView = onInflateFooterView(mInflater, list, onClickLoadMoreListener);
        mFooterView.addView(mOriginLoadMoreView, new ViewGroup.LayoutParams(ViewGroup.MarginLayoutParams.MATCH_PARENT, ViewGroup.MarginLayoutParams.WRAP_CONTENT));
        onInflateFooterViewAfter(mFooterView);
        if (enableLoadMoreFooter) {
            //添加加载更多布局到尾部
            IListScrollViewAdapter listAdapter = list.getListAdapter();
            //RecyclerView使用HeaderFooterAdapter进行添加尾部
            if (list instanceof ScrollableRecyclerView) {
                ((HeaderFooterDataAdapter) listAdapter).addFooterView(mFooterView);
            } else if (list instanceof IScrollableListAdapterView) {
                //ListView则直接使用ListView进行添加
                ((ListView) list).addFooterView(mFooterView);
            }
        }
        showNormal();
    }

    /**
     * 由于rv等控件设置布局gone并不能隐藏，所以直接将高度压缩来达到隐藏的目的
     */
    private void compressFooterViewHeight() {
        mFooterView.getLayoutParams().height = 0;
        mFooterView.requestLayout();
    }

    /**
     * 恢复尾部View原来的高度
     */
    private void restoreFooterViewHeight() {
        mFooterView.getLayoutParams().height = ViewGroup.MarginLayoutParams.WRAP_CONTENT;
        mFooterView.requestLayout();
    }

    @Override
    public void showNormal() {
        switchFooterLayout(onSwitchNormalLayout(mInflater));
        AfterAction action = onShowNormal(mFooterView);
        handlerAfterAction(action);
    }

    @Override
    public void showLoading() {
        switchFooterLayout(onSwitchLoadingLayout(mInflater));
        AfterAction action = onShowLoading(mFooterView);
        handlerAfterAction(action);
    }

    @Override
    public void showError() {
        switchFooterLayout(onSwitchErrorLayout(mInflater));
        AfterAction action = onShowError(mFooterView);
        handlerAfterAction(action);
    }

    @Override
    public void showNoMore() {
        switchFooterLayout(onSwitchNoMoreLayout(mInflater));
        AfterAction action = onShowNoMore(mFooterView);
        handlerAfterAction(action);
    }

    @Override
    public View getFooterView() {
        return mFooterView;
    }

    @Override
    public void enableLoadMoreFooter(boolean enableLoadMoreFooter) {
        if (enableLoadMoreFooter) {
            ((HeaderFooterAdapter) ((ScrollableRecyclerView) mList).getAdapter()).addFooterView(mFooterView);
        } else {
            ((HeaderFooterAdapter) ((ScrollableRecyclerView) mList).getAdapter()).removeFooter(mFooterView);
        }
    }

    /**
     * 当切换到Nomal状态视图时，默认返回原来的视图，当视图需要不同时，子类重写对应状态的切换方法
     *
     * @param inflater
     */
    protected View onSwitchNormalLayout(LayoutInflater inflater) {
        return mOriginLoadMoreView;
    }

    /**
     * 当切换到Loading状态视图时
     *
     * @param inflater
     */
    protected View onSwitchLoadingLayout(LayoutInflater inflater) {
        return mOriginLoadMoreView;
    }

    /**
     * 当切换到Error状态视图时
     *
     * @param inflater
     */
    protected View onSwitchErrorLayout(LayoutInflater inflater) {
        return mOriginLoadMoreView;
    }

    /**
     * 当切换到NoMore状态视图时
     *
     * @param inflater
     */
    protected View onSwitchNoMoreLayout(LayoutInflater inflater) {
        return mOriginLoadMoreView;
    }

    /**
     * 处理显示后的操作
     *
     * @param action 显示后的操作种类
     */
    private void handlerAfterAction(AfterAction action) {
        //压缩高度
        if (action.ordinal() == AfterAction.COMPRESS_HEIGHT.ordinal()) {
            compressFooterViewHeight();
        } else if (action.ordinal() == AfterAction.RESTORE_HEIGHT.ordinal()) {
            //恢复高度
            restoreFooterViewHeight();
        }
    }

    /**
     * 切换尾部视图
     *
     * @param newStatusLayout 新的尾部视图
     */
    private void switchFooterLayout(View newStatusLayout) {
        if (newStatusLayout == null) {
            return;
        }
        mFooterView.removeAllViews();
        mFooterView.addView(newStatusLayout, new ViewGroup.MarginLayoutParams(ViewGroup.MarginLayoutParams.MATCH_PARENT, ViewGroup.MarginLayoutParams.WRAP_CONTENT));
    }

    /**
     * 子类重写返回尾部布局
     */
    protected abstract View onInflateFooterView(LayoutInflater inflater, IScrollableView list, View.OnClickListener onClickLoadMoreListener);

    /**
     * 实例化尾部布局后回调，通常是查找控件
     *
     * @param footerView
     */
    protected abstract void onInflateFooterViewAfter(View footerView);

    /**
     * 调用showNormal()时回调，子类重写，对尾部布局进行操作
     *
     * @param footerView 尾部布局
     */
    protected abstract AfterAction onShowNormal(View footerView);

    /**
     * 调用showLoading()时回调,子类重写，对尾部布局进行操作
     *
     * @param footerView 尾部布局
     */
    protected abstract AfterAction onShowLoading(View footerView);

    /**
     * 调用showError()时回调，子类重写，对尾部布局进行操作
     *
     * @param footerView 尾部布局
     */
    protected abstract AfterAction onShowError(View footerView);

    /**
     * 调用showNoMore()时回调，子类重写，对尾部布局进行操作
     *
     * @param footerView 尾部布局
     */
    protected abstract AfterAction onShowNoMore(View footerView);

    public View.OnClickListener getOnClickRefreshListener() {
        return onClickRefreshListener;
    }
}