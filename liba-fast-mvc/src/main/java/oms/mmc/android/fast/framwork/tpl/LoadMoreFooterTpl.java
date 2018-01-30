package oms.mmc.android.fast.framwork.tpl;

import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import oms.mmc.android.fast.framwork.R;
import oms.mmc.android.fast.framwork.base.BaseTpl;
import oms.mmc.android.fast.framwork.bean.BaseItemData;

/**
 * Package: oms.mmc.android.fast.framwork.sample.tpl.base
 * FileName: LoadMoreFooterTpl
 * Date: on 2018/1/29  下午11:12
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */

public class LoadMoreFooterTpl extends BaseTpl<BaseItemData> {
    protected View footView;
    protected TextView text;
    protected ProgressBar progressBar;
    protected View.OnClickListener onClickRefreshListener;

    @Override
    public void onLayoutBefore() {
        super.onLayoutBefore();
        onClickRefreshListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listViewHelper.loadMore();
            }
        };
    }

    @Override
    public int onLayoutId() {
        return R.layout.base_list_footer;
    }

    @Override
    public void onLayoutAfter() {
        super.onLayoutAfter();
        footView = getRoot();
        text = (TextView) footView.findViewById(R.id.text);
        progressBar = (ProgressBar) footView.findViewById(R.id.progressBar);
    }

    @Override
    public void render() {

    }

    public void showNormal() {
        footView.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
        text.setVisibility(View.VISIBLE);
        text.setText("");
        footView.setOnClickListener(null);
    }

    public void showLoading() {
        footView.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        text.setVisibility(View.VISIBLE);
        text.setText("正在加载中..");
        footView.setOnClickListener(null);
    }

    public void showFail() {
        footView.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
        text.setVisibility(View.VISIBLE);
        text.setText("加载失败，点击重新加载");
        footView.setOnClickListener(onClickRefreshListener);
    }

    public void showNoMore() {
        footView.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
        text.setVisibility(View.VISIBLE);
        text.setText("");
        footView.setOnClickListener(null);
    }
}
