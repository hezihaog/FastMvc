package oms.mmc.android.fast.framwork.widget.pull;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.View;

/**
 * Package: oms.mmc.android.fast.framwork.widget.pull
 * FileName: AbsPullRefreshWrapper
 * Date: on 2018/3/21  下午3:12
 * Auther: zihe
 * Descirbe:下拉刷新包裹类抽象类
 * Email: hezihao@linghit.com
 */

public abstract class AbsPullRefreshWrapper<T extends IPullRefreshLayout> implements IPullRefreshWrapper<T> {
    private T mRefreshLayout;
    private Handler mUiHandler;
    private OnRefreshListener mRefreshListener;
    /**
     * 是否可以下拉刷新
     */
    private boolean isCanPullToRefresh = true;

    public AbsPullRefreshWrapper(T refreshLayout) {
        attachRefreshLayout(refreshLayout);
    }

    @Override
    public void attachRefreshLayout(T refreshLayout) {
        if (!(refreshLayout instanceof View)) {
            throw new IllegalArgumentException("请实现IPullRefreshLayout接口，并且必须是View的子类控件进行实现");
        }
        this.mRefreshLayout = refreshLayout;
    }

    @Override
    public T getPullRefreshAbleView() {
        return mRefreshLayout;
    }

    @Override
    public Context getContext() {
        return getPullRefreshAbleView().getContext();
    }

    @Override
    public void setCanPullToRefresh() {
        isCanPullToRefresh = true;
        getPullRefreshAbleView().setRefreshEnable();
    }

    @Override
    public boolean isCanPullToRefresh() {
        return isCanPullToRefresh;
    }

    @Override
    public void setNotPullToRefresh() {
        isCanPullToRefresh = false;
        getPullRefreshAbleView().setRefreshDisable();
    }

    @Override
    public boolean isNotPullToRefresh() {
        return isCanPullToRefresh;
    }

    @Override
    public void setRefreshEnable() {
        if (isCanPullToRefresh) {
            getPullRefreshAbleView().setRefreshEnable();
        }
    }

    @Override
    public void setRefreshDisable() {
        if (isCanPullToRefresh) {
            getPullRefreshAbleView().setRefreshDisable();
        }
    }

    @Override
    public boolean isRefreshEnable() {
        return getPullRefreshAbleView().isRefreshEnable();
    }

    @Override
    public boolean isRefreshDisable() {
        return getPullRefreshAbleView().isRefreshDisable();
    }

    /**
     * 延时初始化Handler
     */
    private void insureInitHandler() {
        if (mUiHandler == null) {
            mUiHandler = new Handler(Looper.getMainLooper());
        }
    }

    @Override
    public void post(Runnable runnable) {
        insureInitHandler();
        mUiHandler.post(runnable);
    }

    @Override
    public void postDelayed(Runnable runnable, long delayMillis) {
        insureInitHandler();
        mUiHandler.postDelayed(runnable, delayMillis);
    }

    /**
     * 绑定刷新监听，用于内部使用
     */
    protected void bindRefreshListener(OnRefreshListener listener) {
        this.mRefreshListener = listener;
    }

    @Override
    public OnRefreshListener getRefreshListener() {
        return mRefreshListener;
    }

    @Override
    public void startRefresh() {
        startRefresh(0);
    }

    @Override
    public void startRefresh(long delayMillis) {
        final Runnable task = new Runnable() {
            @Override
            public void run() {
                getPullRefreshAbleView().startRefresh();
                if (getRefreshListener() != null) {
                    getRefreshListener().onRefresh();
                }
            }
        };
        if (delayMillis <= 0) {
            post(task);
        } else {
            postDelayed(task, delayMillis);
        }
    }

    @Override
    public void startRefreshWithAnimation() {
        startRefreshWithAnimation(0);
    }

    @Override
    public void startRefreshWithAnimation(long delayMillis) {
        Runnable task = new Runnable() {
            @Override
            public void run() {
                getPullRefreshAbleView().startRefreshWithAnimation();
                if (getRefreshListener() != null) {
                    getRefreshListener().onRefresh();
                }
            }
        };
        if (delayMillis <= 0) {
            post(task);
        } else {
            postDelayed(task, delayMillis);
        }
    }

    @Override
    public void completeRefresh() {
        completeRefresh(0);
    }


    @Override
    public void completeRefresh(long delayMillis) {
        Runnable task = new Runnable() {
            @Override
            public void run() {
                getPullRefreshAbleView().completeRefresh();
            }
        };
        if (delayMillis <= 0) {
            post(task);
        } else {
            postDelayed(task, delayMillis);
        }
    }

    @Override
    public boolean isRefurbishing() {
        return getPullRefreshAbleView().isRefurbishing();
    }
}
