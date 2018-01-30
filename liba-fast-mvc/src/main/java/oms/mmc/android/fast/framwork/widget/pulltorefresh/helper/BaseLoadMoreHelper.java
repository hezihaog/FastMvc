package oms.mmc.android.fast.framwork.widget.pulltorefresh.helper;

import android.support.v7.widget.RecyclerView;
import android.view.View.OnClickListener;

import oms.mmc.android.fast.framwork.base.BaseListAdapter;
import oms.mmc.android.fast.framwork.tpl.LoadMoreFooterTpl;


public class BaseLoadMoreHelper implements ILoadViewFactory.ILoadMoreView {
    private RecyclerView recyclerView;

    @Override
    public void init(RecyclerView recyclerView, OnClickListener onClickRefreshListener) {
        this.recyclerView = recyclerView;
        showNormal();
    }

    private LoadMoreFooterTpl findLoaderMoreFootTpl() {
        BaseListAdapter adapter = (BaseListAdapter) recyclerView.getAdapter();
        return adapter.findLoaderMoreFootTpl();
    }

    @Override
    public void showNormal() {
        LoadMoreFooterTpl loaderMoreTpl = findLoaderMoreFootTpl();
        if (loaderMoreTpl != null) {
            loaderMoreTpl.showNormal();
        }
    }

    @Override
    public void showLoading() {
        LoadMoreFooterTpl loaderMoreTpl = findLoaderMoreFootTpl();
        if (loaderMoreTpl != null) {
            loaderMoreTpl.showLoading();
        }
//        footView.setVisibility(View.VISIBLE);
//        progressBar.setVisibility(View.VISIBLE);
//        text.setVisibility(View.VISIBLE);
//        text.setText("正在加载中..");
//        footView.setOnClickListener(null);
    }

    @Override
    public void showFail() {
        LoadMoreFooterTpl loaderMoreTpl = findLoaderMoreFootTpl();
        if (loaderMoreTpl != null) {
            loaderMoreTpl.showFail();
        }
    }

    @Override
    public void showNoMore() {
        LoadMoreFooterTpl loaderMoreTpl = findLoaderMoreFootTpl();
        if (loaderMoreTpl != null) {
            loaderMoreTpl.showNoMore();
        }
    }
}