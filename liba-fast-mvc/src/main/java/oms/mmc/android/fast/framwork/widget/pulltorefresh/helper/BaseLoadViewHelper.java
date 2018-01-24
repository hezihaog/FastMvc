package oms.mmc.android.fast.framwork.widget.pulltorefresh.helper;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;

import oms.mmc.android.fast.framwork.BaseMMCFastApplication;
import oms.mmc.android.fast.framwork.R;

public class BaseLoadViewHelper implements ILoadViewFactory.ILoadView {

    protected VaryViewHelper helper;
    protected OnClickListener onClickRefreshListener;
    protected Context context;

    @Override
    public void init(AbsListView mListView, OnClickListener onClickRefreshListener) {
        helper = new VaryViewHelper(mListView);
        this.context = mListView.getContext().getApplicationContext();
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
        BaseMMCFastApplication.showToast("网络出错，加载失败");
    }

    @Override
    public void showFail() {
        View layout = helper.inflate(R.layout.base_list_error);
        layout.findViewById(R.id.refresh).setOnClickListener(onClickRefreshListener);
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