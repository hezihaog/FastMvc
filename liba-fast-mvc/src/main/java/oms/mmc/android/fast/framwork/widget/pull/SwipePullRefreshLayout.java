package oms.mmc.android.fast.framwork.widget.pull;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;

/**
 * Package: oms.mmc.android.fast.framwork.widget.pull
 * FileName: SwipeRefreshPullLayout
 * Date: on 2018/3/21  下午3:16
 * Auther: zihe
 * Descirbe:继承SwipeRefreshLayout的刷新控件
 * Email: hezihao@linghit.com
 */

public class SwipePullRefreshLayout extends SwipeRefreshLayout implements IPullRefreshLayout {
    private boolean mRefreshEnabled;

    public SwipePullRefreshLayout(Context context) {
        super(context);
    }

    public SwipePullRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void startRefresh() {
        //不带动画，无需操作，直接Wrapper类去调用监听器的onRefresh()即可
    }

    @Override
    public void startRefreshWithAnimation() {
        setRefreshing(true);
    }

    @Override
    public void completeRefresh() {
        setRefreshing(false);
    }

    @Override
    public void setRefreshEnable() {
        this.mRefreshEnabled = true;
        setEnabled(true);
    }

    @Override
    public void setRefreshDisable() {
        this.mRefreshEnabled = false;
        setEnabled(false);
    }

    @Override
    public boolean isRefreshEnable() {
        return mRefreshEnabled;
    }

    @Override
    public boolean isRefreshDisable() {
        return mRefreshEnabled;
    }

    @Override
    public boolean isRefurbishing() {
        return isRefreshing();
    }
}