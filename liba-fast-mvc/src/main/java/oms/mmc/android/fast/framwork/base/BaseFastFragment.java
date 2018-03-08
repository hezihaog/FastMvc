package oms.mmc.android.fast.framwork.base;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.concurrent.CopyOnWriteArrayList;

import oms.mmc.android.fast.framwork.R;
import oms.mmc.android.fast.framwork.util.ViewFinder;
import oms.mmc.android.fast.framwork.util.WaitViewManager;
import oms.mmc.factory.wait.factory.BaseWaitDialogFactory;
import oms.mmc.factory.wait.factory.IWaitViewFactory;
import oms.mmc.factory.wait.inter.IWaitViewController;


/**
 * Fragment基类
 */
public abstract class BaseFastFragment extends CommonOperationDelegateFragment implements LayoutCallback
        , IWaitViewHandler, IHandlerDispatcher, IStateDispatch {
    protected FragmentManager mFm;
    protected Fragment mFragment;
    private ViewFinder mViewFinder;
    private Handler mMainHandler;
    private CopyOnWriteArrayList<InstanceStateCallback> stateCallbacks = new CopyOnWriteArrayList<InstanceStateCallback>();

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mFm = getChildFragmentManager();
        mFragment = this;
        //这里的操作是，当activity不是BaseFastActivity或者宿主activity的onGetWaitDialogFactory()方法返回null时，使用fragment上定义的waitView
        IWaitViewController controller = WaitViewManager.getInstnace().find(activity);
        if (controller == null) {
            IWaitViewFactory waitViewFactory = onWaitDialogFactoryReady();
            if (waitViewFactory != null && waitViewFactory.getWaitDialogController(activity) != null) {
                controller = waitViewFactory.getWaitDialogController(activity);
                if (controller != null) {
                    WaitViewManager.getInstnace().add(activity, controller);
                }
            }
        }
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
        WaitViewManager.getInstnace().showWaitDialog(getActivity(), "", false);
    }

    @Override
    public void showWaitDialog(String msg) {
        WaitViewManager.getInstnace().showWaitDialog(getActivity(), msg, false);
    }

    @Override
    public void showWaitDialog(String msg, final boolean isTouchCancelable) {
        WaitViewManager.getInstnace().showWaitDialog(getActivity(), msg, isTouchCancelable);
    }

    @Override
    public void hideWaitDialog() {
        WaitViewManager.getInstnace().hideWaitDialog(getActivity());
    }

    @Override
    public IWaitViewController getWaitController() {
        return WaitViewManager.getInstnace().find(getActivity());
    }

    @Override
    public Handler initHandler() {
        return new Handler(Looper.getMainLooper());
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

    public static Bundle createArgs() {
        return new Bundle();
    }

    @Override
    protected void onSaveState(Bundle outState) {
        super.onSaveState(outState);
        for (InstanceStateCallback callback : stateCallbacks) {
            callback.onSaveInstanceState(outState);
        }
    }

    @Override
    protected void onRestoreState(Bundle savedInstanceState) {
        super.onRestoreState(savedInstanceState);
        if (mViewFinder == null) {
            mViewFinder = new ViewFinder(getActivity(), onLayoutView(LayoutInflater.from(getContext()), null));
        } else {
            mViewFinder.setActivity(getActivity());
            mViewFinder.setRootView(onLayoutView(LayoutInflater.from(getContext()), null));
        }
        for (InstanceStateCallback callback : stateCallbacks) {
            callback.onRestoreInstanceState(savedInstanceState);
        }
    }

    @Override
    public void addStateListener(InstanceStateCallback callback) {
        if (callback != null) {
            stateCallbacks.add(callback);
        }
    }

    @Override
    public void removeStateListener(InstanceStateCallback callback) {
        if (callback != null) {
            stateCallbacks.remove(callback);
        }
    }
}
