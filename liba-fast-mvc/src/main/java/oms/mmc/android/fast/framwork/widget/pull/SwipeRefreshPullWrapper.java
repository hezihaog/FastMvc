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

public class SwipeRefreshPullWrapper extends AbsPullRefreshWrapper<SwipeRefreshPullLayout> {
    public SwipeRefreshPullWrapper(SwipeRefreshPullLayout refreshLayout) {
        super(refreshLayout);
    }

    @Override
    public void startRefresh() {
        if (getRefreshListener() != null) {
            getRefreshListener().onRefresh();
        }
    }

    @Override
    public void startRefreshAnimation() {
        this.setRefreshed(true);
    }

    @Override
    public void stopRefreshAnimation() {
        this.setRefreshed(false);
    }

    @Override
    public void startRefreshWithAnimation() {
        post(new Runnable() {
            @Override
            public void run() {
                startRefreshAnimation();
                if (getRefreshListener() != null) {
                    getRefreshListener().onRefresh();
                }
            }
        });
    }

    @Override
    public void setRefreshComplete() {
        stopRefreshAnimation();
    }

    @Override
    public void setOnRefreshListener(final OnRefreshListener listener) {
        bindRefreshListener(listener);
        SwipeRefreshPullLayout refreshAbleView = getPullRefreshAbleView();
        refreshAbleView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                listener.onRefresh();
            }
        });
    }

    @Override
    public boolean isRefreshed() {
        return getPullRefreshAbleView().isRefreshing();
    }
}