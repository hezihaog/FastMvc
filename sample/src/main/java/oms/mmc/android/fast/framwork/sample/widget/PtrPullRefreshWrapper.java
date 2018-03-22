package oms.mmc.android.fast.framwork.sample.widget;

import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import oms.mmc.android.fast.framwork.widget.pull.AbsPullRefreshWrapper;

/**
 * Package: oms.mmc.android.fast.framwork.sample.widget
 * FileName: PtrPullRefreshWrapper
 * Date: on 2018/3/22  上午11:25
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */

public class PtrPullRefreshWrapper extends AbsPullRefreshWrapper<PtrPullRefreshLayout> {
    public PtrPullRefreshWrapper(PtrPullRefreshLayout refreshLayout) {
        super(refreshLayout);
    }

    @Override
    public void setOnRefreshListener(final OnRefreshListener listener) {
        getPullRefreshAbleView().setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                listener.onRefresh();
            }
        });
    }
}