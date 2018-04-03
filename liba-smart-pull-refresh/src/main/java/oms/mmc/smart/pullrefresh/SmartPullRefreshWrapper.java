package oms.mmc.smart.pullrefresh;

import com.scwang.smartrefresh.layout.api.RefreshLayout;

import oms.mmc.android.fast.framwork.widget.pull.AbsPullRefreshWrapper;

/**
 * Package: oms.mmc.android.fast.framwork.sample.widget
 * FileName: SmartPullRefreshWrapper
 * Date: on 2018/3/22  上午10:23
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */

public class SmartPullRefreshWrapper extends AbsPullRefreshWrapper<SmartPullRefreshLayout> {
    public SmartPullRefreshWrapper(SmartPullRefreshLayout refreshLayout) {
        super(refreshLayout);
    }

    @Override
    public void setOnRefreshListener(final OnRefreshListener listener) {
        getPullRefreshAbleView().setOnRefreshListener(new com.scwang.smartrefresh.layout.listener.OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                listener.onRefresh();
            }
        });
    }
}