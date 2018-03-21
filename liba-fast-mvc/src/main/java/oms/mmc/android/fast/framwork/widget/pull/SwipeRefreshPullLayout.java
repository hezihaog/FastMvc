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

public class SwipeRefreshPullLayout extends SwipeRefreshLayout implements IPullRefreshLayout {
    private boolean mRefreshEnabled;

    public SwipeRefreshPullLayout(Context context) {
        super(context);
    }

    public SwipeRefreshPullLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
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
    public void setRefreshed(boolean isRefreshing) {
        setRefreshing(isRefreshing);
    }

    @Override
    public boolean isRefreshed() {
        return isRefreshing();
    }
}