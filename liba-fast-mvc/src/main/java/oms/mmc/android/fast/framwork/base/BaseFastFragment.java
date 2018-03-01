package oms.mmc.android.fast.framwork.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import oms.mmc.android.fast.framwork.R;
import oms.mmc.android.fast.framwork.lazy.ExtendLazyFragment;
import oms.mmc.android.fast.framwork.util.ViewFinder;
import oms.mmc.android.fast.framwork.util.WaitViewManager;
import oms.mmc.factory.wait.WaitDialogController;


/**
 * Fragment基类
 */
public abstract class BaseFastFragment extends ExtendLazyFragment implements LayoutCallback {
    protected FragmentManager mFm;
    protected Fragment mFragment;
    protected Bundle mArguments;
    private ViewFinder mViewFinder;

    private WaitDialogController mWaitController;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mFm = getChildFragmentManager();
        mFragment = this;
        mWaitController = onGetWaitDialogController();
    }

    public static Bundle createArgs() {
        return new Bundle();
    }

    public String intentStr(String key) {
        Bundle args = mArguments;
        if (args != null) {
            return args.getString(key);
        }
        return "";
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mArguments = getArguments();
        onLayoutBefore();
    }

    @Override
    public View onLazyCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mViewFinder = new ViewFinder(onLayoutView(inflater, container));
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

    protected WaitDialogController onGetWaitDialogController() {
        return new WaitDialogController(getActivity());
    }

    public ViewFinder getViewFinder() {
        return mViewFinder;
    }

    public void showWaitDialog() {
        if (WaitViewManager.getInstnace().find(getActivity()) != null) {
            WaitViewManager.getInstnace().find(getActivity()).getWaitIml().showWaitDialog(getActivity());
        } else {
            mWaitController.getWaitIml().showWaitDialog(getActivity(), "", false);
        }
    }

    public void showWaitDialog(String msg) {
        if (WaitViewManager.getInstnace().find(getActivity()) != null) {
            WaitViewManager.getInstnace().find(getActivity()).getWaitIml().showWaitDialog(getActivity(), msg, false);
        } else {
            mWaitController.getWaitIml().showWaitDialog(getActivity(), msg, false);
        }
    }

    public void showWaitDialog(String msg, final boolean isTouchCancelable) {
        if (WaitViewManager.getInstnace().find(getActivity()) != null) {
            WaitViewManager.getInstnace().find(getActivity()).getWaitIml().showWaitDialog(getActivity(), msg, isTouchCancelable);
        } else {
            mWaitController.getWaitIml().showWaitDialog(getActivity(), msg, isTouchCancelable);
        }
    }

    public void hideWaitDialog() {
        if (WaitViewManager.getInstnace().find(getActivity()) != null) {
            WaitViewManager.getInstnace().find(getActivity()).getWaitIml().hideWaitDialog();
        } else {
            mWaitController.getWaitIml().hideWaitDialog();
        }
    }

    protected WaitDialogController getWaitController() {
        if (WaitViewManager.getInstnace().find(getActivity()) != null) {
            WaitViewManager.getInstnace().find(getActivity());
        } else {
            return mWaitController;
        }
        return mWaitController;
    }

    public BaseFastActivity getBaseActivity() {
        return (BaseFastActivity) getActivity();
    }
}
