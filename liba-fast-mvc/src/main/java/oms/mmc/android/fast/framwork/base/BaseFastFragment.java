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
import oms.mmc.factory.wait.factory.BaseWaitDialogFactory;
import oms.mmc.factory.wait.factory.IWaitViewFactory;
import oms.mmc.factory.wait.inter.IWaitViewController;


/**
 * Fragment基类
 */
public abstract class BaseFastFragment extends ExtendLazyFragment implements LayoutCallback, IWaitViewHandler {
    protected FragmentManager mFm;
    protected Fragment mFragment;
    protected Bundle mArguments;
    private ViewFinder mViewFinder;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mFm = getChildFragmentManager();
        mFragment = this;
        //这里的操作是，当activity不是BaseFastActivity或者宿主activity的onGetWaitDialogFactory()方法返回null时，使用fragment上定义的waitView
        IWaitViewController controller = WaitViewManager.getInstnace().find(activity);
        if (controller == null) {
            IWaitViewFactory waitViewFactory = onGetWaitDialogFactory();
            if (waitViewFactory != null && waitViewFactory.getWaitDialogController(activity) != null) {
                controller = waitViewFactory.getWaitDialogController(activity);
                if (controller != null) {
                    WaitViewManager.getInstnace().add(activity, controller);
                }
            }
        }
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

    protected IWaitViewFactory onGetWaitDialogFactory() {
        return new BaseWaitDialogFactory();
    }

    public ViewFinder getViewFinder() {
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
}
