package oms.mmc.factory.load.base;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.View;

/**
 * Package: oms.mmc.factory.load.base
 * FileName: AbsSimpleLoadViewHelper
 * Date: on 2018/3/1  上午10:57
 * Auther: zihe
 * Descirbe:抽象的自己控制切换布局的Helper类，一般使用SimpleLoadViewHelper即可，如需更换切换布局，则重写SimpleLoadViewHelper对应的填充方法
 * Email: hezihao@linghit.com
 */

public abstract class AbsSimpleLoadViewHelper implements ISimpleLoadViewHelper {
    private Context mContext;
    private View.OnClickListener onClickRefreshListener;
    private VaryViewHelper mHelper;
    private ISimpleLoadViewHelper.OnStateUpdateListener mListener;
    private Handler mMainHandler;

    @Override
    public ISimpleLoadViewHelper init(View content, View.OnClickListener onClickRefreshListener) {
        this.mHelper = new VaryViewHelper(content);
        this.mContext = content.getContext().getApplicationContext();
        this.mMainHandler = new Handler(Looper.getMainLooper());
        this.onClickRefreshListener = onClickRefreshListener;
        return this;
    }

    @Override
    public void showLoading() {
        runOnMainThread(new Runnable() {
            @Override
            public void run() {
                View layout = onInflateLoadingLayout(mHelper, onClickRefreshListener);
                mHelper.showLayout(layout);
                if (mListener != null) {
                    mListener.onShowLoading();
                }
            }
        });
    }

    @Override
    public void showEmpty() {
        runOnMainThread(new Runnable() {
            @Override
            public void run() {
                View layout = onInflateEmptyLayout(mHelper, onClickRefreshListener);
                mHelper.showLayout(layout);
                if (mListener != null) {
                    mListener.onShowEmpty();
                }
            }
        });
    }

    @Override
    public void showError() {
        runOnMainThread(new Runnable() {
            @Override
            public void run() {
                View layout = onInflateErrorLayout(mHelper, onClickRefreshListener);
                mHelper.showLayout(layout);
                if (mListener != null) {
                    mListener.onShowError();
                }
            }
        });
    }

    @Override
    public void restore() {
        runOnMainThread(new Runnable() {
            @Override
            public void run() {
                mHelper.restoreView();
                if (mListener != null) {
                    mListener.onRestore();
                }
            }
        });
    }

    /**
     * 当填充加载布局时回调
     *
     * @param helper                 替换帮助类，填充可使用helper对象的inflate方法
     * @param onClickRefreshListener 点击刷新点击事件
     */
    protected abstract View onInflateLoadingLayout(IVaryViewHelper helper, View.OnClickListener onClickRefreshListener);

    /**
     * 当填充空布局时回调
     *
     * @param helper                 替换帮助类，填充可使用helper对象的inflate方法
     * @param onClickRefreshListener 点击刷新点击事件
     */
    protected abstract View onInflateEmptyLayout(IVaryViewHelper helper, View.OnClickListener onClickRefreshListener);

    /**
     * 当填充错误布局时回调
     *
     * @param helper                 替换帮助类，填充可使用helper对象的inflate方法
     * @param onClickRefreshListener 点击刷新点击事件
     */
    protected abstract View onInflateErrorLayout(IVaryViewHelper helper, View.OnClickListener onClickRefreshListener);

    @Override
    public IVaryViewHelper getHelper() {
        return mHelper;
    }

    @Override
    public Context getContext() {
        return mContext;
    }

    @Override
    public void setOnStateUpdateListener(OnStateUpdateListener listener) {
        this.mListener = listener;
    }

    @Override
    public boolean isOnMainThread() {
        return Looper.myLooper() == Looper.getMainLooper();
    }

    @Override
    public void runOnMainThread(Runnable runnable) {
        if (isOnMainThread()) {
            runnable.run();
        } else {
            mMainHandler.post(runnable);
        }
    }
}