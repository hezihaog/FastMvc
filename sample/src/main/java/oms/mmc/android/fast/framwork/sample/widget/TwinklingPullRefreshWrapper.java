package oms.mmc.android.fast.framwork.sample.widget;

import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;

import oms.mmc.android.fast.framwork.widget.pull.AbsPullRefreshWrapper;

/**
 * Package: oms.mmc.android.fast.framwork.sample.widget
 * FileName: TwinklingRefreshPullWrapper
 * Date: on 2018/3/21  下午11:05
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */

public class TwinklingPullRefreshWrapper extends AbsPullRefreshWrapper<TwinklingPullRefreshLayout> {
    public TwinklingPullRefreshWrapper(TwinklingPullRefreshLayout refreshLayout) {
        super(refreshLayout);
    }

    @Override
    public boolean isRefurbishing() {
        return getPullRefreshAbleView().isRefurbishing();
    }

    @Override
    public void startRefresh() {
        getPullRefreshAbleView().startRefresh();
    }

    @Override
    public void startRefreshWithAnimation() {
        getPullRefreshAbleView().startRefresh();
    }

    @Override
    public void completeRefresh() {
        getPullRefreshAbleView().finishRefreshing();
    }

    @Override
    public void setOnRefreshListener(final OnRefreshListener listener) {
        getPullRefreshAbleView().setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                super.onRefresh(refreshLayout);
                listener.onRefresh();
            }
        });
    }
}