package oms.mmc.android.fast.framwork.sample.widget;

import android.content.Context;
import android.util.AttributeSet;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import oms.mmc.android.fast.framwork.widget.pull.IPullRefreshLayout;

/**
 * Package: oms.mmc.android.fast.framwork.sample.widget
 * FileName: SmartPullRefreshLayout
 * Date: on 2018/3/22  上午10:23
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */

public class SmartPullRefreshLayout extends SmartRefreshLayout implements IPullRefreshLayout {
    public SmartPullRefreshLayout(Context context) {
        super(context);
    }

    public SmartPullRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SmartPullRefreshLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
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
        finishRefresh();
    }

    @Override
    public void setRefreshEnable() {
        setEnableRefresh(true);
    }

    @Override
    public void setRefreshDisable() {
        setEnableRefresh(false);
    }

    @Override
    public boolean isRefreshEnable() {
        return isEnableRefresh();
    }

    @Override
    public boolean isRefreshDisable() {
        return !isEnableRefresh();
    }

    @Override
    public boolean isRefurbishing() {
        return isRefreshing();
    }
}