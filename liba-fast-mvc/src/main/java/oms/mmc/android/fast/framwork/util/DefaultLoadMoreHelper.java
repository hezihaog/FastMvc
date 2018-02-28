package oms.mmc.android.fast.framwork.util;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import oms.mmc.android.fast.framwork.R;


public class DefaultLoadMoreHelper extends AbsLoadMoreHelper {
    private TextView mTipText;
    private ProgressBar mProgressBar;

    @Override
    protected View onInflateFooterView(LayoutInflater inflater, RecyclerView list, View.OnClickListener onClickLoadMoreListener) {
        View footView = inflater.inflate(R.layout.layout_default_load_more_footer, list, false);
        return footView;
    }

    @Override
    protected void onInflateFooterViewAfter(View footerView) {
        mProgressBar = (ProgressBar) footerView.findViewById(R.id.progressBar);
        mTipText = (TextView) footerView.findViewById(R.id.base_list_error_tip);
    }

    @Override
    protected void onShowNormal(View footerView) {
        footerView.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.GONE);
        mTipText.setVisibility(View.VISIBLE);
        mTipText.setText("");
        footerView.setOnClickListener(null);
    }

    @Override
    protected void onShowNoMore(View footerView) {
        footerView.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.GONE);
        mTipText.setVisibility(View.VISIBLE);
        mTipText.setText("");
        footerView.setOnClickListener(null);
    }

    @Override
    protected void onShowLoading(View footerView) {
        footerView.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.VISIBLE);
        mTipText.setVisibility(View.VISIBLE);
        mTipText.setText(R.string.base_list_load_more_loading_tip_text);
        footerView.setOnClickListener(null);
    }

    @Override
    protected void onShowError(View footerView) {
        footerView.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.GONE);
        mTipText.setVisibility(View.VISIBLE);
        mTipText.setText(R.string.base_list_load_more_load_error);
        footerView.setOnClickListener(getOnClickRefreshListener());
    }
}