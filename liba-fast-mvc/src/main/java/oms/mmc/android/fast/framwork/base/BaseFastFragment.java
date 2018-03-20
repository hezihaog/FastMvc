package oms.mmc.android.fast.framwork.base;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import oms.mmc.android.fast.framwork.BaseFastApplication;
import oms.mmc.android.fast.framwork.R;
import oms.mmc.android.fast.framwork.util.ViewFinder;
import oms.mmc.factory.wait.WaitDialogController;
import oms.mmc.factory.wait.factory.BaseWaitDialogFactory;
import oms.mmc.factory.wait.factory.IWaitViewFactory;
import oms.mmc.factory.wait.inter.IWaitViewController;
import oms.mmc.factory.wait.inter.IWaitViewHost;


/**
 * Fragment基类
 */
public abstract class BaseFastFragment extends CommonOperationDelegateFragment implements LayoutCallback
        , IWaitViewHandler, IHandlerDispatcher, IWaitViewHost {
    protected FragmentManager mFm;
    protected Fragment mFragment;
    private ViewFinder mViewFinder;
    private Handler mMainHandler;
    private WaitDialogController mWaitDialogController;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mFm = getChildFragmentManager();
        mFragment = this;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onLayoutBefore();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (getViewFinder() != null) {
            getViewFinder().recycle();
        }
    }


    @Override
    public View onLazyCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mViewFinder == null) {
            mViewFinder = new ViewFinder(getActivity(), onLayoutView(inflater, container));
        } else {
            mViewFinder.setActivity(getActivity());
            mViewFinder.setRootView(onLayoutView(inflater, container));
        }
        setRootView(mViewFinder.getRootView());
        mWaitDialogController = onWaitDialogFactoryReady().madeWaitDialogController(getActivity());
        return mViewFinder.getRootView();
    }

    @Override
    protected void onLazyViewCreated(View view, @Nullable Bundle savedInstanceState) {
        onFindView(getViewFinder());
        onLayoutAfter();
    }

    @Override
    protected View onGetLazyLoadingView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.base_list_loading, null);
    }

    @Override
    public void onLayoutBefore() {
    }

    @Override
    public void onLayoutAfter() {
    }

    /**
     * 返回等待弹窗样式工厂
     */
    protected IWaitViewFactory onWaitDialogFactoryReady() {
        return new BaseWaitDialogFactory();
    }

    @Override
    public ViewFinder getViewFinder() {
        if (mViewFinder == null) {
            mViewFinder = new ViewFinder(getActivity(), onLayoutView(LayoutInflater.from(getContext()), null));
        }
        return mViewFinder;
    }

    @Override
    public void showWaitDialog() {
        getWaitViewController().getWaitIml().showWaitDialog(getActivity(), "", false);
    }

    @Override
    public void showWaitDialog(String msg) {
        getWaitViewController().getWaitIml().showWaitDialog(getActivity(), msg, false);
    }

    @Override
    public void showWaitDialog(String msg, final boolean isTouchCancelable) {
        getWaitViewController().getWaitIml().showWaitDialog(getActivity(), msg, isTouchCancelable);
    }

    @Override
    public void hideWaitDialog() {
        getWaitViewController().getWaitIml().hideWaitDialog();
    }

    @Override
    public IWaitViewController getWaitViewController() {
        return mWaitDialogController;
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
    public void onSaveState(Bundle stateBundle) {
        super.onSaveState(stateBundle);
        getViewFinder().saveInstance(stateBundle);
    }

    @Override
    public void onRestoreState(Bundle stateBundle) {
        super.onRestoreState(stateBundle);
        if (mViewFinder == null) {
            mViewFinder = new ViewFinder(getActivity(), onLayoutView(LayoutInflater.from(getContext()), null));
        } else {
            mViewFinder.setActivity(getActivity());
            mViewFinder.setRootView(onLayoutView(LayoutInflater.from(getContext()), null));
        }
        getViewFinder().restoreInstance(stateBundle);
    }
}
