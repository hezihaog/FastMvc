package oms.mmc.android.fast.framwork.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;

import java.io.Serializable;
import java.util.ArrayList;

import butterknife.ButterKnife;
import oms.mmc.android.fast.framwork.BaseMMCFastApplication;
import oms.mmc.android.fast.framwork.bean.IResult;
import oms.mmc.android.fast.framwork.bean.BaseItemData;
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
    protected AbsListView listView;
    protected int itemViewType = -1;
    protected View root;
    protected int position;
    protected T bean;

    public void config(BaseListAdapter<? extends BaseItemData> adapter, ArrayList<? extends BaseItemData> data, IDataSource<? extends BaseItemData> dataSource, AbsListView absListView, ListViewHelper listViewHelper) {
        this.listViewAdapter = adapter;
        this.listViewDataSource = dataSource;
        this.listViewData = data;
        this.listView = absListView;
        this.listViewHelper = listViewHelper;
    }

    public void init(Context context, int itemViewType) {
        this.itemViewType = itemViewType;
        this.mActivity = (BaseActivity) context;
        this._intent = mActivity.getIntent();
        if (this._intent != null) {
            this._Bundle = _intent.getExtras();
        }
        ac = (BaseMMCFastApplication) context.getApplicationContext();
        initView();
    }

    protected void initView() {
        onLayoutBefore();
        root = View.inflate(mActivity, onLayoutId(), null);
        root.addOnAttachStateChangeListener(this);
        ButterKnife.bind(this, root);
        onLayoutAfter();
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
