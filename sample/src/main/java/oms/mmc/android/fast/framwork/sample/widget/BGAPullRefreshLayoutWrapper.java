package oms.mmc.android.fast.framwork.sample.widget;

import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import oms.mmc.android.fast.framwork.widget.pull.AbsPullRefreshWrapper;

/**
 * Package: oms.mmc.android.fast.framwork.sample.widget
 * FileName: BGAPullRrefreshLayoutWrapper
 * Date: on 2018/3/22  下午5:48
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */

public class BGAPullRefreshLayoutWrapper extends AbsPullRefreshWrapper<BGAPullRefreshLayout> {
    public BGAPullRefreshLayoutWrapper(BGAPullRefreshLayout refreshLayout) {
        super(refreshLayout);
    }

    @Override
    public void setOnRefreshListener(final OnRefreshListener listener) {
        getPullRefreshAbleView().setDelegate(new BGARefreshLayout.BGARefreshLayoutDelegate() {
            @Override
            public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout bgaRefreshLayout) {
                listener.onRefresh();
            }

            @Override
            public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout bgaRefreshLayout) {
                return false;
            }
        });
    }
}