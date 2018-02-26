package oms.mmc.android.fast.framwork.util;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;

import oms.mmc.android.fast.framwork.R;
import oms.mmc.android.fast.framwork.loadview.ILoadViewFactory;

/**
 * 基础的界面切换加载器，子类继承复写方法即可
 */
public class BaseLoadViewHelper implements ILoadViewFactory.ILoadView {
    protected VaryViewHelper helper;
    protected OnClickListener onClickRefreshListener;
    protected Context context;

    @Override
    public void init(View view, OnClickListener onClickRefreshListener) {
        helper = new VaryViewHelper(view);
        this.context = view.getContext().getApplicationContext();
        this.onClickRefreshListener = onClickRefreshListener;
    }

    @Override
    public void restore() {
        helper.restoreView();
    }

    @Override
    public void showLoading() {
        View layout = helper.inflate(R.layout.base_list_loading);
        helper.showLayout(layout);
    }

    @Override
    public void tipFail() {
        ToastUtil.showToast(context, R.string.net_tip_net_load_error);
    }

    @Override
    public void showFail() {
        View layout = helper.inflate(R.layout.base_list_error);
        layout.findViewById(R.id.base_list_error_refresh).setOnClickListener(onClickRefreshListener);
        helper.showLayout(layout);
    }

    @Override
    public void showEmpty() {
        View layout = helper.inflate(R.layout.base_list_empty);
        layout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        helper.showLayout(layout);
    }
}