package oms.mmc.android.fast.framwork.sample.widget;

import android.content.Context;
import android.util.AttributeSet;

import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;

import oms.mmc.android.fast.framwork.widget.pull.IPullRefreshLayout;

/**
 * Package: oms.mmc.android.fast.framwork.sample.widget
 * FileName: TwinklingPullRefreshLayout
 * Date: on 2018/3/21  下午11:06
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */

public class TwinklingPullRefreshLayout extends TwinklingRefreshLayout implements IPullRefreshLayout {
    public TwinklingPullRefreshLayout(Context context) {
        super(context);
    }

    public TwinklingPullRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TwinklingPullRefreshLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void startRefreshWithAnimation() {
        startRefresh();
    }

    @Override
    public void completeRefresh() {
        finishRefreshing();
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
        return enableRefresh;
    }

    @Override
    public boolean isRefreshDisable() {
        return !enableRefresh;
    }

    @Override
    public boolean isRefurbishing() {
        return isRefreshing;
    }
}
