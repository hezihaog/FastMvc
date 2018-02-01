package oms.mmc.android.fast.framwork.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import oms.mmc.android.fast.framwork.BaseMMCFastApplication;
import oms.mmc.android.fast.framwork.basiclib.lazy.ExtendLazyFragment;
import oms.mmc.android.fast.framwork.basiclib.util.ViewFinder;


/**
 * Fragment基类
 */
public abstract class BaseFragment extends ExtendLazyFragment implements LayoutCallback {
    protected FragmentManager fm;
    protected BaseMMCFastApplication ac;
    protected BaseActivity mActivity;
    protected Fragment mFragment;
    protected Bundle mArguments;
    private ViewFinder viewFinder;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = (BaseActivity) activity;
        ac = (BaseMMCFastApplication) mActivity.getApplication();
        fm = getChildFragmentManager();
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
    public View onInflaterRootView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewFinder = new ViewFinder(inflater, container, onLayoutId());
        setRootView(viewFinder.getRootView());
        return viewFinder.getRootView();
    }

    @Override
    protected void onLazyViewCreated(View view, @Nullable Bundle savedInstanceState) {
        onFindView(getViewFinder());
        onLayoutAfter();
    }

    @Override
    public void onLayoutBefore() {

    }

    @Override
    public void onLayoutAfter() {

    }

    public ViewFinder getViewFinder() {
        return viewFinder;
    }
}
