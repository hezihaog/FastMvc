package oms.mmc.android.fast.framwork.widget.block;

import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import oms.mmc.android.fast.framwork.util.IViewFinder;
import oms.mmc.android.fast.framwork.util.ViewFinder;
import oms.mmc.factory.wait.inter.IWaitViewHost;
import oms.mmc.lifecycle.dispatch.BaseDelegateFragment;
import oms.mmc.lifecycle.dispatch.DelegateFragmentFinder;
import oms.mmc.lifecycle.dispatch.adapter.SimpleFragmentLifecycleAdapter;
import oms.mmc.lifecycle.dispatch.listener.FragmentLifecycleListener;

/**
 * Package: oms.mmc.android.fast.framwork.widget.block
 * FileName: BaseViewBlock
 * Date: on 2018/4/6  下午9:09
 * Auther: zihe
 * Descirbe:用户布局拆看使用
 * Email: hezihao@linghit.com
 */
public abstract class BaseViewBlock extends CommonOperationViewBlock implements IViewBlock, ActivityLifecycleObserver {
    private FragmentActivity mActivity;
    private IWaitViewHost mWaitViewHost;
    private Handler mUIHandler;
    private View mRootLayout;
    private ViewFinder mViewFinder;
    private View.OnAttachStateChangeListener mOnAttachStateChangeListener;

    /**
     * 直接构造，不指定要添加到的布局
     *
     * @param activity     界面activity
     * @param waitViewHost wait宿主，其实就是activity或者fragment
     */
    public BaseViewBlock(FragmentActivity activity, IWaitViewHost waitViewHost) {
        this.mActivity = activity;
        this.mWaitViewHost = waitViewHost;
        //通知创建
        onCreate();
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

    /**
     * 当创建时回调
     */
    protected void onCreate() {

    }

    /**
     * 当销毁时回调
     */
    protected void onDestroy() {

    }

    @Override
    public void initView() {
        //添加Activity生命周期监听
        addActivityLifecycleListener();
        onLayoutBefore();
        mRootLayout = onLayoutView(LayoutInflater.from(getActivity()), null);
        //添加View依附、解除依附监听
        addViewAttachStateChangeListener();
        mRootLayout.setLayoutParams(onGetLayoutParams());
        if (mViewFinder == null) {
            mViewFinder = new ViewFinder(getActivity(), getRoot());
        }
        onFindView(mViewFinder);
        onLayoutAfter();
    }

    /**
     * 添加View依附、解除依附监听
     */
    private void addViewAttachStateChangeListener() {
        //注意如果在列表控件中使用，在View移除到屏幕外时也会回调，所以不适用于滚动后进行广播的注册和注销，如果需要请在onCreate
        mOnAttachStateChangeListener = new View.OnAttachStateChangeListener() {
            @Override
            public void onViewAttachedToWindow(View view) {
                onAttachedToWindow(view);
            }

            @Override
            public void onViewDetachedFromWindow(View view) {
                onDetachedFromWindow(view);
            }
        };
        mRootLayout.addOnAttachStateChangeListener(mOnAttachStateChangeListener);
    }

    /**
     * 添加Activity生命周期监听
     */
    private void addActivityLifecycleListener() {
        BaseDelegateFragment delegateFragment = DelegateFragmentFinder.getInstance().startDelegate(getActivity(), ActivityLifecycleDelegateFragment.class);
        FragmentLifecycleListener lifecycleListener = new SimpleFragmentLifecycleAdapter() {

            @Override
            public void onStart() {
                onActivityStart();
            }

            @Override
            public void onResume() {
                onActivityResume();
            }

            @Override
            public void onPause() {
                onActivityPause();
            }

            @Override
            public void onStop() {
                onActivityStop();
            }

            @Override
            public void onDestroy() {
                if (mOnAttachStateChangeListener != null) {
                    getRoot().removeOnAttachStateChangeListener(mOnAttachStateChangeListener);
                }
                onActivityDestroy();
                //回调自身的销毁，进行内存回调
                BaseViewBlock.this.onDestroy();
            }
        };
        delegateFragment.getProxyLifecycle().addListener(lifecycleListener);
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

    @Override
    public Handler initUiHandler() {
        return new Handler(getActivity().getMainLooper());
    }

    @Override
    public Handler getUiHandler() {
        if (mUIHandler == null) {
            return initUiHandler();
        } else {
            return mUIHandler;
        }
    }

    @Override
    public void post(Runnable runnable) {
        if (mUIHandler == null) {
            mUIHandler = initUiHandler();
        }
        mUIHandler.post(runnable);
    }

    @Override
    public void postDelayed(Runnable runnable, long duration) {
        if (mUIHandler == null) {
            mUIHandler = initUiHandler();
        }
        mUIHandler.postDelayed(runnable, duration);
    }

    @Override
    public void removeUiHandlerMessage(Runnable runnable) {
        getUiHandler().removeCallbacks(runnable);
    }

    @Override
    public void removeUiHandlerAllMessage() {
        getUiHandler().removeCallbacksAndMessages(null);
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
    public void onActivityStart() {
    }

    @Override
    public void onActivityResume() {
    }

    @Override
    public void onActivityPause() {
    }

    @Override
    public void onActivityStop() {
    }

    @Override
    public void onActivityDestroy() {
    }

    /**
     * 依附在View树时回调
     *
     * @param view Block上的View
     */
    public void onAttachedToWindow(View view) {
    }

    /**
     * 从View上解除依附
     *
     * @param view Block上的View
     */
    public void onDetachedFromWindow(View view) {
    }
}