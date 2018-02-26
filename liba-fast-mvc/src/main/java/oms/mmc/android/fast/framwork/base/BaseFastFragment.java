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


/**
 * Fragment基类
 */
public abstract class BaseFastFragment extends ExtendLazyFragment implements LayoutCallback {
    protected FragmentManager mFm;
    protected BaseFastActivity mActivity;
    protected Fragment mFragment;
    protected Bundle mArguments;
    private ViewFinder mViewFinder;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = (BaseFastActivity) activity;
        mFm = getChildFragmentManager();
        mFragment = this;
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
    protected View onGetLazyLoadingView() {
        return LayoutInflater.from(getContext()).inflate(R.layout.base_list_loading, null);
    }

    @Override
    public void onLayoutBefore() {

    }

    @Override
    public void onLayoutAfter() {

    }

    public ViewFinder getViewFinder() {
        return mViewFinder;
    }


    public void showWaitDialog() {
        getBaseActivity().showWaitDialog("", false);
    }

    public void showWaitDialog(String msg) {
        getBaseActivity().showWaitDialog(msg, false);
    }

    public void showWaitDialog(String msg, final boolean isTouchCancelable) {
        getBaseActivity().showWaitDialog(msg, isTouchCancelable);
    }

    public void hideWaitDialog() {
        getBaseActivity().hideWaitDialog();
    }

    public BaseFastActivity getBaseActivity() {
        return (BaseFastActivity) getActivity();
    }
}
