package oms.mmc.android.fast.framwork.widget.rv.base;

import android.app.Activity;
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
import oms.mmc.helper.ListScrollHelper;

/**
 * 列表条目基础模板，条目类
 */
public abstract class BaseTpl<T> implements LayoutCallback, View.OnAttachStateChangeListener {
    private Activity mActivity;
    private Intent mIntent;
    private Bundle mBundle;
    private RecyclerViewViewHelper mRecyclerViewHelper;
    private ListScrollHelper mListScrollHelper;
    private IAssistRecyclerAdapter mListAdapter;
    private IDataSource<? extends BaseItemData> mListDataSource;
    private List<? extends BaseItemData> mListData;
    private RecyclerView mRecyclerView;
    private int mItemViewType = -1;
    private View mRoot;
    private int mPosition;
    private T mBean;
    private BaseTpl.ViewHolder mViewHolder;
    private ViewFinder mViewFinder;

    public BaseTpl() {
    }

    public void init(Activity activity, RecyclerView recyclerView, int itemViewType) {
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
            , IDataSource<? extends BaseItemData> dataSource, RecyclerViewViewHelper recyclerViewHelper, ListScrollHelper listScrollHelper) {
        this.mListAdapter = adapter;
        this.mListDataSource = dataSource;
        this.mListData = data;
        this.mRecyclerViewHelper = recyclerViewHelper;
        this.mListScrollHelper = listScrollHelper;
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

    public Activity getActivity() {
        return mActivity;
    }

    /**
     * 当Activity是BaseFastActivity时，可以直接调用该方法拿到BaseFastActivity类型
     */
    public BaseFastActivity getBaseActivity() {
        return (BaseFastActivity) mActivity;
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

    public String intentStr(String key) {
        return mIntent.getStringExtra(key);
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
     * RecyclerView从窗口移除时回调
     */
    public void onRecyclerViewDetachedFromWindow(View view) {

    }
}
