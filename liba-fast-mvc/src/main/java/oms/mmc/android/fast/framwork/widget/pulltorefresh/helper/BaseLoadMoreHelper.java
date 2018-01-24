package oms.mmc.android.fast.framwork.widget.pulltorefresh.helper;

import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import oms.mmc.android.fast.framwork.R;


public class BaseLoadMoreHelper implements ILoadViewFactory.ILoadMoreView {

    protected View footView;
    protected TextView text;
    protected ProgressBar progressBar;

    protected OnClickListener onClickRefreshListener;

    @Override
    public void init(AbsListView listView, OnClickListener onClickRefreshListener) {
        footView = LayoutInflater.from(listView.getContext()).inflate(R.layout.base_list_footer, listView, false);
        text = (TextView) footView.findViewById(R.id.text);
        progressBar = (ProgressBar) footView.findViewById(R.id.progressBar);
        if (listView instanceof ListView) {
            ((ListView) listView).addFooterView(footView);
        }
        this.onClickRefreshListener = onClickRefreshListener;
        showNormal();
    }

    @Override
    public void showNormal() {
        footView.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
        text.setVisibility(View.VISIBLE);
        text.setText("");
        footView.setOnClickListener(null);
    }

    @Override
    public void showLoading() {
        footView.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        text.setVisibility(View.VISIBLE);

        text.setText("正在加载中..");
        footView.setOnClickListener(null);
    }

    @Override
    public void showFail() {
        footView.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
        text.setVisibility(View.VISIBLE);

        text.setText("加载失败，点击重新加载");
        footView.setOnClickListener(onClickRefreshListener);
    }

    @Override
    public void showNomore() {
        footView.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
        text.setVisibility(View.VISIBLE);

        text.setText("");
        footView.setOnClickListener(null);
    }

}