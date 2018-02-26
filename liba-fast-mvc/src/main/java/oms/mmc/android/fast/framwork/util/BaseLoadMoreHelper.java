package oms.mmc.android.fast.framwork.util;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ProgressBar;
import android.widget.TextView;

import oms.mmc.android.fast.framwork.R;
import oms.mmc.android.fast.framwork.widget.rv.adapter.HeaderFooterAdapter;


public class BaseLoadMoreHelper implements ILoadViewFactory.ILoadMoreView {
    private View mFootView;
    private TextView mTipText;
    private ProgressBar mProgressBar;

    protected OnClickListener onClickRefreshListener;

    @Override
    public void init(RecyclerView list, OnClickListener onClickRefreshListener) {
        mFootView = LayoutInflater.from(list.getContext()).inflate(R.layout.item_load_more_footer, list, false);
        mTipText = (TextView) mFootView.findViewById(R.id.base_list_error_tip);
        mProgressBar = (ProgressBar) mFootView.findViewById(R.id.progressBar);
        if (list instanceof RecyclerView) {
            ((HeaderFooterAdapter) list.getAdapter()).addFooterView(mFootView);
        }
        this.onClickRefreshListener = onClickRefreshListener;
        showNormal();
    }

    @Override
    public void showNormal() {
        mFootView.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.GONE);
        mTipText.setVisibility(View.VISIBLE);
        mTipText.setText("");
        mFootView.setOnClickListener(null);
    }

    @Override
    public void showLoading() {
        mFootView.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.VISIBLE);
        mTipText.setVisibility(View.VISIBLE);
        mTipText.setText(R.string.base_list_load_more_loading_tip_text);
        mFootView.setOnClickListener(null);
    }

    @Override
    public void showFail() {
        mFootView.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.GONE);
        mTipText.setVisibility(View.VISIBLE);
        mTipText.setText(R.string.base_list_load_more_load_error);
        mFootView.setOnClickListener(onClickRefreshListener);
    }

    @Override
    public void showNoMore() {
        mFootView.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.GONE);
        mTipText.setVisibility(View.VISIBLE);
        mTipText.setText("");
        mFootView.setOnClickListener(null);
    }

    @Override
    public View getFootView() {
        return mFootView;
    }
}