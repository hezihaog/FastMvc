package oms.mmc.android.fast.framwork.sample.widget;

import android.content.Context;
import android.util.AttributeSet;

import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import oms.mmc.android.fast.framwork.widget.pull.IPullRefreshLayout;

/**
 * Package: oms.mmc.android.fast.framwork.sample.widget
 * FileName: BGAPullRrefreshLayout
 * Date: on 2018/3/22  下午5:48
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */

public class BGAPullRefreshLayout extends BGARefreshLayout implements IPullRefreshLayout {
    public BGAPullRefreshLayout(Context context) {
        super(context);
    }

    public BGAPullRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void startRefresh() {
        beginRefreshing();
    }

    @Override
    public void startRefreshWithAnimation() {
        beginRefreshing();
    }

    @Override
    public void completeRefresh() {
        endRefreshing();
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
        return isLoadingMore();
    }
}