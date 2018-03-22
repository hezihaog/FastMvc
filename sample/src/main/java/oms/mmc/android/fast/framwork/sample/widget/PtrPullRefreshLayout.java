package oms.mmc.android.fast.framwork.sample.widget;

import android.content.Context;
import android.util.AttributeSet;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import oms.mmc.android.fast.framwork.widget.pull.IPullRefreshLayout;

/**
 * Package: oms.mmc.android.fast.framwork.sample.widget
 * FileName: PtrPullRefreshLayout
 * Date: on 2018/3/22  上午11:24
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */

public class PtrPullRefreshLayout extends PtrClassicFrameLayout implements IPullRefreshLayout {
    public PtrPullRefreshLayout(Context context) {
        super(context);
    }

    public PtrPullRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PtrPullRefreshLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void startRefresh() {
        autoRefresh();
    }

    @Override
    public void startRefreshWithAnimation() {
        autoRefresh();
    }

    @Override
    public void completeRefresh() {
        refreshComplete();
    }

    @Override
    public void setRefreshEnable() {
        setEnabled(true);
    }

    @Override
    public void setRefreshDisable() {
        setEnabled(false);
    }

    @Override
    public boolean isRefreshEnable() {
        return isEnabled();
    }

    @Override
    public boolean isRefreshDisable() {
        return !isEnabled();
    }

    @Override
    public boolean isRefurbishing() {
        return isRefreshing();
    }
}