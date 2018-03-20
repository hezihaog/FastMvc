package oms.mmc.android.fast.framwork.base;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import oms.mmc.android.fast.framwork.R;
import oms.mmc.android.fast.framwork.util.FastUIDelegate;
import oms.mmc.android.fast.framwork.util.IViewFinder;
import oms.mmc.factory.wait.factory.BaseWaitDialogFactory;
import oms.mmc.factory.wait.factory.IWaitViewFactory;
import oms.mmc.factory.wait.inter.IWaitViewController;


/**
 * Fragment基类
 */
public abstract class BaseFastFragment extends CommonOperationDelegateFragment implements IFastUIInterface {
    IFastUIDelegate mUIDelegate = new FastUIDelegate();

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mUIDelegate.attachUIIml(this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUIDelegate.onCreate(getArguments());
        mUIDelegate.performLayoutBefore();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mUIDelegate != null) {
            mUIDelegate.onDestroy();
        }
    }


    @Override
    public View onLazyCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mUIDelegate.performLayoutView(container);
        setRootView(mUIDelegate.getRootView());
        return mUIDelegate.getRootView();
    }

    @Override
    protected void onLazyViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mUIDelegate.performFindView();
        mUIDelegate.performLayoutAfter();
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
    @Override
    public IWaitViewFactory onWaitDialogFactoryReady() {
        return new BaseWaitDialogFactory();
    }

    @Override
    public IViewFinder getViewFinder() {
        return mUIDelegate.getViewFinder();
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
        return mUIDelegate.getWaitViewController();
    }

    @Override
    public Handler initHandler() {
        return mUIDelegate.initHandler();
    }

    @Override
    public void post(Runnable runnable) {
        if (mUIDelegate != null) {
            mUIDelegate.post(runnable);
        }
    }

    @Override
    public void postDelayed(Runnable runnable, long duration) {
        if (mUIDelegate != null) {
            mUIDelegate.postDelayed(runnable, duration);
        }
    }

    @Override
    public void onSaveState(Bundle stateBundle) {
        super.onSaveState(stateBundle);
        getViewFinder().saveInstance(stateBundle);
    }

    @Override
    public void onRestoreState(Bundle stateBundle) {
        super.onRestoreState(stateBundle);
        getViewFinder().restoreInstance(stateBundle);
    }

    @Override
    public BaseFastFragment getFragment() {
        return this;
    }
}
