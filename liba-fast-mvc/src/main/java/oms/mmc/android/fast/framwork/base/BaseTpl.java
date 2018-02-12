package oms.mmc.android.fast.framwork.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import java.io.Serializable;
import java.util.List;

import oms.mmc.android.fast.framwork.BaseMMCFastApplication;
import oms.mmc.android.fast.framwork.R;
import oms.mmc.android.fast.framwork.basiclib.util.ToastUtil;
import oms.mmc.android.fast.framwork.basiclib.util.ViewFinder;
import oms.mmc.android.fast.framwork.bean.BaseItemData;
import oms.mmc.android.fast.framwork.bean.IResult;
import oms.mmc.android.fast.framwork.widget.pulltorefresh.helper.IDataSource;
import oms.mmc.android.fast.framwork.widget.pulltorefresh.helper.RecyclerViewViewHelper;

/**
 * 列表条目基础模板，条目类
 */
public abstract class BaseTpl<T> implements ApiCallback, LayoutCallback, Serializable, View.OnAttachStateChangeListener {
    protected BaseMMCFastApplication ac;
    protected BaseActivity mActivity;
    protected Intent intent;
    protected Bundle bundle;

    protected RecyclerViewViewHelper recyclerViewHelper;
    protected IAssistRecyclerAdapter listViewAdapter;
    protected IDataSource<? extends BaseItemData> listViewDataSource;
    protected List<? extends BaseItemData> listViewData;
    protected RecyclerView recyclerView;
    protected int itemViewType = -1;
    protected View root;
    protected int position;
    private T bean;
    protected BaseTpl.ViewHolder viewHolder;
    private ViewFinder viewFinder;

    public void init(Context context, RecyclerView recyclerView, int itemViewType) {
        this.itemViewType = itemViewType;
        this.mActivity = (BaseActivity) context;
        this.recyclerView = recyclerView;
        this.intent = mActivity.getIntent();
        if (this.intent != null) {
            this.bundle = intent.getExtras();
        }
        ac = (BaseMMCFastApplication) context.getApplicationContext();
        initView();
    }

    public void config(IAssistRecyclerAdapter adapter, List<? extends BaseItemData> data
            , IDataSource<? extends BaseItemData> dataSource, RecyclerViewViewHelper recyclerViewHelper) {
        this.listViewAdapter = adapter;
        this.listViewDataSource = dataSource;
        this.listViewData = data;
        this.recyclerViewHelper = recyclerViewHelper;
    }

    protected void initView() {
        onLayoutBefore();
        root = LayoutInflater.from(mActivity).inflate(onLayoutId(), recyclerView, false);
        root.addOnAttachStateChangeListener(this);
        viewHolder = new ViewHolder(root);
        viewFinder = new ViewFinder(root);
        onFindView(viewFinder);
        onLayoutAfter();
    }

    public BaseTpl.ViewHolder getViewHolder() {
        return viewHolder;
    }

    public View getRoot() {
        return root;
    }

    protected ViewFinder getViewFinder() {
        return viewFinder;
    }

    @Override
    public void onLayoutBefore() {

    }

    public BaseActivity getActivity() {
        return mActivity;
    }

    @Override
    public void onLayoutAfter() {

    }

    protected void onItemClick(View view, int position) {

    }

    protected void onItemLongClick(View view, int position) {

    }

    /**
     * 内部持有Rv的ViewHolder
     */
    protected static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

    public T getBean() {
        return bean;
    }

    public int getPosition() {
        return position;
    }

    public void setBeanPosition(List<? extends BaseItemData> listViewData, T item, int position) {
        this.listViewData = listViewData;
        this.bean = item;
        this.position = position;
    }

    public final void render() {
        T bean = getBean();
        onRender(bean);
    }

    protected abstract void onRender(T itemData);

    public String intentStr(String key) {
        return intent.getStringExtra(key);
    }

    @Override
    public void onApiStart(String tag) {
    }

    @Override
    public void onApiLoading(long count, long current, String tag) {
    }

    @Override
    public void onApiSuccess(IResult res, String tag) {
    }

    @Override
    public void onApiFailure(Throwable t, int errorNo, String strMsg, String tag) {
        ToastUtil.showToast(getRoot().getContext(), R.string.net_tip_net_request_error);
        t.printStackTrace();
        onApiError(tag);
    }

    @Override
    public void onParseError(String tag) {
        ToastUtil.showToast(getRoot().getContext(), R.string.net_tip_net_parse_data_error);
        onApiError(tag);
    }

    protected void onApiError(String tag) {

    }

    public int getItemViewType() {
        return itemViewType;
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
