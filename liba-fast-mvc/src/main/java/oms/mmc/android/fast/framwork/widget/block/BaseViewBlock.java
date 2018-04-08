package oms.mmc.android.fast.framwork.widget.block;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewTreeObserver;

import oms.mmc.android.fast.framwork.BaseFastApplication;
import oms.mmc.android.fast.framwork.util.IViewFinder;
import oms.mmc.android.fast.framwork.util.ViewFinder;
import oms.mmc.factory.wait.inter.IWaitViewHost;

/**
 * Package: oms.mmc.android.fast.framwork.widget.block
 * FileName: BaseViewBlock
 * Date: on 2018/4/6  下午9:09
 * Auther: zihe
 * Descirbe:用户布局拆看使用
 * Email: hezihao@linghit.com
 */
public abstract class BaseViewBlock extends CommonOperationViewBlock implements IViewBlock, View.OnAttachStateChangeListener {
    private FragmentActivity mActivity;
    private IWaitViewHost mWaitViewHost;
    private Handler mMainHandler;
    private View mRootLayout;
    private ViewFinder mViewFinder;

    /**
     * 直接构造，不指定要添加到的布局
     *
     * @param activity     界面activity
     * @param waitViewHost wait宿主，其实就是activity或者fragment
     */
    public BaseViewBlock(FragmentActivity activity, IWaitViewHost waitViewHost) {
        this.mActivity = activity;
        this.mWaitViewHost = waitViewHost;
        initView();
    }

    /**
     * 指定要添加到的布局来构造，一开始就直接添加进布局
     *
     * @param activity     界面activity
     * @param waitViewHost wait宿主，其实就是activity或者fragment
     * @param parent       要添加见的布局
     */
    public BaseViewBlock(FragmentActivity activity, IWaitViewHost waitViewHost, ViewGroup parent) {
        this(activity, waitViewHost);
        parent.addView(getRoot(), onGetAddViewIndex(parent));
    }

    @Override
    public void initView() {
        onLayoutBefore();
        mRootLayout = onLayoutView(LayoutInflater.from(getActivity()), null);
        mRootLayout.addOnAttachStateChangeListener(this);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN_MR2) {
            addOnWindowFocusChangeListener();
        }
        mRootLayout.setLayoutParams(onGetLayoutParams());
        if (mViewFinder == null) {
            mViewFinder = new ViewFinder(getActivity(), getRoot());
        }
        onFindView(mViewFinder);
        onLayoutAfter();
    }

    @Override
    public ViewFinder getViewFinder() {
        return mViewFinder;
    }

    @Override
    public View getRoot() {
        return mRootLayout;
    }

    @Override
    public void removeSelf() {
        if (mRootLayout != null) {
            ViewParent parent = mRootLayout.getParent();
            if (parent != null && parent instanceof ViewGroup) {
                ViewGroup viewGroup = (ViewGroup) parent;
                viewGroup.removeView(mRootLayout);
            }
        }
    }

    @Override
    public int onGetAddViewIndex(ViewGroup parent) {
        return parent.getChildCount();
    }

    @Override
    public ViewGroup.LayoutParams onGetLayoutParams() {
        return new ViewGroup.MarginLayoutParams(ViewGroup.MarginLayoutParams.MATCH_PARENT,
                ViewGroup.MarginLayoutParams.WRAP_CONTENT);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    private void addOnWindowFocusChangeListener() {
        mRootLayout.getViewTreeObserver().addOnWindowFocusChangeListener(new ViewTreeObserver.OnWindowFocusChangeListener() {
            @Override
            public void onWindowFocusChanged(boolean hasFocus) {
                BaseViewBlock.this.onWindowFocusChanged(hasFocus);
            }
        });
    }

    public void onWindowFocusChanged(boolean hasFocus) {

    }

    @Override
    public Handler initHandler() {
        Handler handler = null;
        if (getActivity().getApplication() instanceof BaseFastApplication) {
            handler = ((BaseFastApplication) getActivity().getApplication()).getMainHandler();
        }
        if (handler == null) {
            handler = new Handler(getActivity().getMainLooper());
        }
        return handler;
    }

    @Override
    public void post(Runnable runnable) {
        if (mMainHandler == null) {
            mMainHandler = initHandler();
        }
        mMainHandler.post(runnable);
    }

    @Override
    public void postDelayed(Runnable runnable, long duration) {
        if (mMainHandler == null) {
            mMainHandler = initHandler();
        }
        mMainHandler.postDelayed(runnable, duration);
    }

    @Override
    public void showWaitDialog() {
        mWaitViewHost.getWaitViewController().getWaitIml().showWaitDialog(getActivity());
    }

    @Override
    public void showWaitDialog(String msg) {
        mWaitViewHost.getWaitViewController().getWaitIml().showWaitDialog(getActivity(), msg, false);
    }

    @Override
    public void showWaitDialog(String msg, boolean isTouchCancelable) {
        mWaitViewHost.getWaitViewController().getWaitIml().showWaitDialog(getActivity(), msg, isTouchCancelable);
    }

    @Override
    public void hideWaitDialog() {
        mWaitViewHost.getWaitViewController().getWaitIml().hideWaitDialog();
    }

    @Override
    public void onLayoutBefore() {
    }

    @Override
    public void onFindView(IViewFinder finder) {
    }

    @Override
    public void onLayoutAfter() {
    }

    @Override
    public FragmentActivity getActivity() {
        return mActivity;
    }

    @Override
    public void onViewAttachedToWindow(View v) {
    }

    @Override
    public void onViewDetachedFromWindow(View v) {
    }
}