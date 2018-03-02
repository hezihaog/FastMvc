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
        return inflater.inflate(R.layout.layout_default_load_more_footer, list, false);
    }

    @Override
    protected void onInflateFooterViewAfter(View footerView) {
        mProgressBar = (ProgressBar) footerView.findViewById(R.id.progressBar);
        mTipText = (TextView) footerView.findViewById(R.id.base_list_error_tip);
    }

    @Override
    protected AfterAction onShowNormal(View footerView) {
        footerView.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.GONE);
        mTipText.setVisibility(View.VISIBLE);
        mTipText.setText("");
        footerView.setOnClickListener(null);
        return AfterAction.RESTORE_HEIGHT;
    }

    @Override
    protected AfterAction onShowNoMore(View footerView) {
        //没有更多直接隐藏布局
        footerView.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.GONE);
        mTipText.setVisibility(View.VISIBLE);
        mTipText.setText(R.string.base_list_load_more_no_more_tip_text);
        footerView.setOnClickListener(null);
        return AfterAction.COMPRESS_HEIGHT;
    }

    @Override
    protected AfterAction onShowLoading(View footerView) {
        footerView.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.VISIBLE);
        mTipText.setVisibility(View.VISIBLE);
        mTipText.setText(R.string.base_list_load_more_loading_tip_text);
        footerView.setOnClickListener(null);
        return AfterAction.RESTORE_HEIGHT;
    }

    @Override
    protected AfterAction onShowError(View footerView) {
        footerView.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.GONE);
        mTipText.setVisibility(View.VISIBLE);
        mTipText.setText(R.string.base_list_load_more_load_error);
        footerView.setOnClickListener(getOnClickRefreshListener());
        return AfterAction.RESTORE_HEIGHT;
    }
}