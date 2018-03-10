package oms.mmc.android.fast.framwork.widget.rv.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import java.util.List;

import oms.mmc.android.fast.framwork.base.IDataSource;
import oms.mmc.android.fast.framwork.base.IWaitViewHandler;
import oms.mmc.android.fast.framwork.base.LayoutCallback;
import oms.mmc.android.fast.framwork.util.RecyclerViewViewHelper;
import oms.mmc.android.fast.framwork.util.ToastOperator;
import oms.mmc.android.fast.framwork.util.ViewFinder;
import oms.mmc.android.fast.framwork.widget.rv.adapter.IAssistRecyclerAdapter;
import oms.mmc.factory.wait.inter.IWaitViewHost;
import oms.mmc.helper.ListScrollHelper;

/**
 * 列表条目基础模板，条目类
 *
 * @author 子和
 */
public abstract class BaseTpl<T> extends CommonOperationDelegateTpl implements LayoutCallback, IWaitViewHandler, View.OnAttachStateChangeListener {
    private Activity mActivity;
    private RecyclerViewViewHelper mRecyclerViewHelper;
    private ListScrollHelper mListScrollHelper;
    private IAssistRecyclerAdapter mListAdapter;
    private IDataSource<? extends BaseItemData> mListDataSource;
    private List<? extends BaseItemData> mListData;
    private RecyclerView mRecyclerView;
    private IWaitViewHost mWaitViewHost;
    private int mItemViewType = -1;
    private View mRoot;
    private int mPosition;
    private T mBean;
    private BaseTpl.ViewHolder mInnerViewHolder;
    private ViewFinder mViewFinder;

    public BaseTpl() {
    }

    public void init(Activity activity, RecyclerView recyclerView, ToastOperator toastOperator, IWaitViewHost waitViewHost, int itemViewType) {
        this.mWaitViewHost = waitViewHost;
        this.mItemViewType = itemViewType;
        this.mActivity = activity;
        this.mRecyclerView = recyclerView;
        setToastOperator(toastOperator);
        setBundle(mActivity.getIntent().getExtras());
        initView();
    }

    public void config(IAssistRecyclerAdapter adapter, List<? extends BaseItemData> data
            , IDataSource<? extends BaseItemData> dataSource, RecyclerViewViewHelper recyclerViewHelper, ListScrollHelper listScrollHelper) {
        this.mListAdapter = adapter;
        this.mListDataSource = dataSource;
        this.mListData = data;
        this.mRecyclerViewHelper = recyclerViewHelper;
        this.mListScrollHelper = listScrollHelper;
    }

    private void initView() {
        onCreate();
        onLayoutBefore();
        mRoot = onLayoutView(LayoutInflater.from(mActivity), mRecyclerView);
        mRoot.addOnAttachStateChangeListener(this);
        mViewFinder = new ViewFinder(getActivity(), mRoot);
        onFindView(mViewFinder);
        onBindContent();
        onLayoutAfter();
    }

    public BaseTpl.ViewHolder getViewHolder() {
        if (mInnerViewHolder == null) {
            mInnerViewHolder = new ViewHolder(getRootView());
        }
        return mInnerViewHolder;
    }

    public View getRoot() {
        if (mRoot == null) {
            mRoot = onLayoutView(LayoutInflater.from(mActivity), mRecyclerView);
        }
        return mRoot;
    }

    @Override
    public ViewFinder getViewFinder() {
        if (mViewFinder == null) {
            mViewFinder = new ViewFinder(getActivity(), getRoot());
        }
        return mViewFinder;
    }

    /**
     * 当创建时回调
     */
    protected void onCreate() {
    }

    /**
     * 销毁时回调
     */
    protected void onDestroy() {

    }

    @Override
    public void onLayoutBefore() {
    }

    @Override
    public void onLayoutAfter() {
    }

    @Override
    public Activity getActivity() {
        return mActivity;
    }

    public void onItemClick(View view, int position) {

    }

    public void onItemLongClick(View view, int position) {

    }

    /**
     * 内部持有Rv的ViewHolder
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

    public T getItemDataBean() {
        return mBean;
    }

    public int getPosition() {
        return mPosition;
    }

    public RecyclerView getRecyclerView() {
        return mRecyclerView;
    }

    public RecyclerViewViewHelper getRecyclerViewHelper() {
        return mRecyclerViewHelper;
    }

    public ListScrollHelper getListScrollHelper() {
        return mListScrollHelper;
    }

    public IAssistRecyclerAdapter getListAdapter() {
        return mListAdapter;
    }

    public void setBeanPosition(List<? extends BaseItemData> listViewData, T item, int position) {
        this.mListData = listViewData;
        this.mBean = item;
        this.mPosition = position;
    }

    /**
     * 每次条目onBindView时，重新设置数据
     */
    public final void render() {
        T bean = getItemDataBean();
        onRender(bean);
    }

    @Override
    public void onFindView(ViewFinder finder) {

    }

    /**
     * onFindView查找控件后，初始化控件时使用，例如ViewPager的adapter，如条目类不需要，则不需要重写
     */
    protected void onBindContent() {

    }

    /**
     * 渲染函数，rv的onBindView时会调用
     *
     * @param itemData 条目类上使用的数据
     */
    protected abstract void onRender(T itemData);

    @Override
    public void showWaitDialog() {
        mWaitViewHost.getWaitViewController().getWaitIml().showWaitDialog(getActivity(), "", false);
    }

    @Override
    public void showWaitDialog(String msg) {
        mWaitViewHost.getWaitViewController().getWaitIml().showWaitDialog(getActivity(), msg, false);
    }

    @Override
    public void showWaitDialog(String msg, final boolean isTouchCancelable) {
        mWaitViewHost.getWaitViewController().getWaitIml().showWaitDialog(getActivity(), msg, isTouchCancelable);
    }

    @Override
    public void hideWaitDialog() {
        mWaitViewHost.getWaitViewController().getWaitIml().hideWaitDialog();
    }

    /**
     * 获取条目的类型
     */
    public int getItemViewType() {
        return mItemViewType;
    }

    /**
     * 获取当前列表的数据集对象
     */
    public IDataSource<? extends BaseItemData> getListDataSource() {
        return mListDataSource;
    }

    /**
     * 获取当前列表的数据集合
     */
    public List<? extends BaseItemData> getListData() {
        return mListData;
    }

    @Override
    public void onViewAttachedToWindow(View v) {
    }

    @Override
    public void onViewDetachedFromWindow(View v) {
    }

    /**
     * 当界面被强制内存回收前回调
     *
     * @param savedBundle 保存的信息的map
     */
    @CallSuper
    public void onSaveState(Bundle savedBundle) {

    }

    /**
     * 当用户重新打开app，界面已经被内存回收了，马上进行恢复时回调
     *
     * @param restoreBundle 马上进行恢复时，之前保存的信息的map
     */
    @CallSuper
    public void onRestoreState(Bundle restoreBundle) {
        if (mViewFinder == null) {
            mViewFinder = new ViewFinder(getActivity(), mRoot);
        } else {
            if (mViewFinder.getActivity() == null) {
                mViewFinder.setActivity(getActivity());
            }
            if (mViewFinder.getRootView() == null) {
                mViewFinder.setRootView(getRoot());
            }
        }
    }

    /**
     * RecyclerView从窗口移除时回调
     */
    @CallSuper
    public void onRecyclerViewDetachedFromWindow(View view) {
        onDestroy();
        if (getViewFinder() != null) {
            getViewFinder().recycle();
        }
        recyclerTpl();
    }

    /**
     * 回收Tpl中的引用
     */
    private void recyclerTpl() {
        if (mActivity != null) {
            mActivity = null;
        }
        if (mRecyclerViewHelper != null) {
            mRecyclerViewHelper = null;
        }
        if (mListScrollHelper != null) {
            mListScrollHelper = null;
        }
        if (mListAdapter != null) {
            mListAdapter = null;
        }
        if (mListDataSource != null) {
            mListDataSource = null;
        }
        if (mListData != null) {
            mListData = null;
        }
        if (mRecyclerView != null) {
            mRecyclerView = null;
        }
        if (mRoot != null) {
            mRoot = null;
        }
        if (mBean != null) {
            mBean = null;
        }
        if (mInnerViewHolder != null) {
            mInnerViewHolder = null;
        }
    }
}
