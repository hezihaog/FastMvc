package oms.mmc.android.fast.framwork.widget.rv.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import java.util.List;

import oms.mmc.android.fast.framwork.base.BaseFastActivity;
import oms.mmc.android.fast.framwork.base.IDataSource;
import oms.mmc.android.fast.framwork.base.LayoutCallback;
import oms.mmc.android.fast.framwork.util.RecyclerViewViewHelper;
import oms.mmc.android.fast.framwork.util.ViewFinder;
import oms.mmc.android.fast.framwork.widget.rv.adapter.IAssistRecyclerAdapter;

/**
 * 列表条目基础模板，条目类
 */
public abstract class BaseTpl<T> implements LayoutCallback, View.OnAttachStateChangeListener {
    private BaseFastActivity mActivity;
    private Intent mIntent;
    private Bundle mBundle;
    private RecyclerViewViewHelper mRecyclerViewHelper;
    private IAssistRecyclerAdapter mListAdapter;
    private IDataSource<? extends BaseItemData> mListDataSource;
    private List<? extends BaseItemData> mListViewData;
    private RecyclerView mRecyclerView;
    private int mItemViewType = -1;
    private View mRoot;
    private int mPosition;
    private T mBean;
    private BaseTpl.ViewHolder mViewHolder;
    private ViewFinder mViewFinder;

    public void init(BaseFastActivity activity, RecyclerView recyclerView, int itemViewType) {
        this.mItemViewType = itemViewType;
        this.mActivity = activity;
        this.mRecyclerView = recyclerView;
        this.mIntent = mActivity.getIntent();
        if (this.mIntent != null) {
            this.mBundle = mIntent.getExtras();
        }
        initView();
    }

    public void config(IAssistRecyclerAdapter adapter, List<? extends BaseItemData> data
            , IDataSource<? extends BaseItemData> dataSource, RecyclerViewViewHelper recyclerViewHelper) {
        this.mListAdapter = adapter;
        this.mListDataSource = dataSource;
        this.mListViewData = data;
        this.mRecyclerViewHelper = recyclerViewHelper;
    }

    protected void initView() {
        onLayoutBefore();
        mRoot = onLayoutView(LayoutInflater.from(mActivity), mRecyclerView);
        mRoot.addOnAttachStateChangeListener(this);
        mViewHolder = new ViewHolder(mRoot);
        mViewFinder = new ViewFinder(mRoot);
        onFindView(mViewFinder);
        onBindContent();
        onLayoutAfter();
    }

    public BaseTpl.ViewHolder getViewHolder() {
        return mViewHolder;
    }

    public View getRoot() {
        return mRoot;
    }

    protected ViewFinder getViewFinder() {
        return mViewFinder;
    }

    @Override
    public void onLayoutBefore() {
    }

    @Override
    public void onLayoutAfter() {
    }

    public BaseFastActivity getActivity() {
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

    public T getBean() {
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

    public IAssistRecyclerAdapter getListAdapter() {
        return mListAdapter;
    }

    public void setBeanPosition(List<? extends BaseItemData> listViewData, T item, int position) {
        this.mListViewData = listViewData;
        this.mBean = item;
        this.mPosition = position;
    }

    /**
     * 每次条目onBindView时，重新设置数据
     */
    public final void render() {
        T bean = getBean();
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

    protected abstract void onRender(T itemData);

    public String intentStr(String key) {
        return mIntent.getStringExtra(key);
    }

    public int getItemViewType() {
        return mItemViewType;
    }

    public List<? extends BaseItemData> getListViewData() {
        return mListViewData;
    }

    @Override
    public void onViewAttachedToWindow(View v) {

    }

    @Override
    public void onViewDetachedFromWindow(View v) {

    }

    /**
     * RecyclerView从窗口移除时回调
     */
    public void onRecyclerViewDetachedFromWindow(View view) {

    }
}
