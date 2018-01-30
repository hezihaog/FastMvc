package oms.mmc.android.fast.framwork.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import java.io.Serializable;
import java.util.ArrayList;

import butterknife.ButterKnife;
import oms.mmc.android.fast.framwork.BaseMMCFastApplication;
import oms.mmc.android.fast.framwork.bean.BaseItemData;
import oms.mmc.android.fast.framwork.bean.IResult;
import oms.mmc.android.fast.framwork.widget.pulltorefresh.helper.IDataSource;
import oms.mmc.android.fast.framwork.widget.pulltorefresh.helper.ListViewHelper;

/**
 * 列表条目基础模板，条目类
 */
public abstract class BaseTpl<T> implements ApiCallback, LayoutCallback, Serializable, View.OnAttachStateChangeListener {
    protected BaseMMCFastApplication ac;
    protected BaseActivity mActivity;
    protected Intent _intent;
    protected Bundle _Bundle;

    protected ListViewHelper listViewHelper;
    protected BaseListAdapter<? extends BaseItemData> listViewAdapter;
    protected IDataSource<? extends BaseItemData> listViewDataSource;
    protected ArrayList<? extends BaseItemData> listViewData;
    protected RecyclerView recyclerView;
    protected int itemViewType = -1;
    protected View root;
    protected int position;
    protected T bean;
    protected BaseTpl.ViewHolder viewHolder;
    private LayoutInflater mInflater;

    public void init(Context context, RecyclerView recyclerView, int itemViewType) {
        this.itemViewType = itemViewType;
        this.mActivity = (BaseActivity) context;
        this.recyclerView = recyclerView;
        this._intent = mActivity.getIntent();
        if (this._intent != null) {
            this._Bundle = _intent.getExtras();
        }
        ac = (BaseMMCFastApplication) context.getApplicationContext();
        mInflater = LayoutInflater.from(context);
        initView();
    }

    public void config(BaseListAdapter<? extends BaseItemData> adapter, ArrayList<? extends BaseItemData> data
            , IDataSource<? extends BaseItemData> dataSource, ListViewHelper listViewHelper) {
        this.listViewAdapter = adapter;
        this.listViewDataSource = dataSource;
        this.listViewData = data;
        this.listViewHelper = listViewHelper;
    }

    protected void initView() {
        onLayoutBefore();
        root = mInflater.inflate(onLayoutId(), recyclerView, false);
        root.addOnAttachStateChangeListener(this);
        viewHolder = new ViewHolder(root);
        ButterKnife.bind(this, root);
        onLayoutAfter();
    }

    public BaseTpl.ViewHolder getViewHolder() {
        return viewHolder;
    }

    public View getRoot() {
        return root;
    }

    @Override
    public void onLayoutBefore() {

    }

    public BaseActivity getActivity() {
        return mActivity;
    }

    @Override
    public abstract int onLayoutId();

    @Override
    public void onLayoutAfter() {

    }

    protected void onItemClick() {

    }

    protected void onItemLongClick() {
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

    public void setBeanPosition(ArrayList<? extends BaseItemData> listViewData, T item, int position) {
        this.listViewData = listViewData;
        this.bean = item;
        this.position = position;
    }

    public abstract void render();

    public String intentStr(String key) {
        return _intent.getStringExtra(key);
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
        BaseMMCFastApplication.showToast("网络请求错误");
        t.printStackTrace();
        onApiError(tag);
    }

    @Override
    public void onParseError(String tag) {
        BaseMMCFastApplication.showToast("数据解析错误");
        onApiError(tag);
    }

    protected void onApiError(String tag) {

    }

    @Override
    public void onViewAttachedToWindow(View v) {

    }

    @Override
    public void onViewDetachedFromWindow(View v) {

    }
}
