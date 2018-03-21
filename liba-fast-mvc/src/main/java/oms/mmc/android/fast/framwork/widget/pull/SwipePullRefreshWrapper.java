package oms.mmc.android.fast.framwork.widget.pull;

import android.support.v4.widget.SwipeRefreshLayout;

/**
 * Package: oms.mmc.android.fast.framwork.widget.pull
 * FileName: SwipeRefreshPullWrapper
 * Date: on 2018/3/21  下午3:10
 * Auther: zihe
 * Descirbe:SwipeRefreshLayout的包裹对象
 * Email: hezihao@linghit.com
 */

public class SwipePullRefreshWrapper extends AbsPullRefreshWrapper<SwipePullRefreshLayout> {
    public SwipePullRefreshWrapper(SwipePullRefreshLayout refreshLayout) {
        super(refreshLayout);
    }

    @Override
    public void startRefresh() {
        getPullRefreshAbleView().startRefresh();
        if (getRefreshListener() != null) {
            getRefreshListener().onRefresh();
        }
    }

    @Override
    public void startRefreshWithAnimation() {
        post(new Runnable() {
            @Override
            public void run() {
                getPullRefreshAbleView().startRefreshWithAnimation();
                if (getRefreshListener() != null) {
                    getRefreshListener().onRefresh();
                }
            }
        });
    }

    @Override
    public void completeRefresh() {
        getPullRefreshAbleView().completeRefresh();
    }

    @Override
    public void setOnRefreshListener(final OnRefreshListener listener) {
        bindRefreshListener(listener);
        SwipePullRefreshLayout refreshAbleView = getPullRefreshAbleView();
        refreshAbleView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                listener.onRefresh();
            }
        });
    }

    @Override
    public boolean isRefurbishing() {
        return getPullRefreshAbleView().isRefurbishing();
    }
}