package oms.mmc.android.fast.framwork.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.ButterKnife;
import oms.mmc.android.fast.framwork.BaseMMCFastApplication;
import oms.mmc.android.fast.framwork.basiclib.util.ViewUtil;
import oms.mmc.android.fast.framwork.lazy.ExtendLazyFragment;


/**
 * Fragment基类
 */
public abstract class BaseFragment extends ExtendLazyFragment implements LayoutCallback {
    protected FragmentManager fm;
    protected BaseMMCFastApplication ac;
    protected BaseActivity mActivity;
    protected Fragment mFragment;
    protected Bundle mArguments;

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
        View root = inflater.inflate(onLayoutId(), null);
        setRootView(root);
        ButterKnife.bind(this, root);
        return root;
    }

    @Override
    protected void onLazyViewCreated(View view, @Nullable Bundle savedInstanceState) {
        onLayoutAfter();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onLayoutBefore() {

    }

    @Override
    public void onLayoutAfter() {

    }

    /**
     * Fragment是否需要加入统计
     */
    protected boolean isNeedStat() {
        return true;
    }


    protected String getTitle() {
        return null;
    }

    public static void setText(String text, TextView view) {
        ViewUtil.setText(text, view);
    }
}
