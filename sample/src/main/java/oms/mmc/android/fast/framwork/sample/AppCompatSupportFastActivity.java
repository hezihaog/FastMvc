package oms.mmc.android.fast.framwork.sample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.View;

import oms.mmc.android.fast.framwork.base.IFastUIDelegate;
import oms.mmc.android.fast.framwork.base.IFastUIInterface;
import oms.mmc.android.fast.framwork.base.IStatusBarHost;
import oms.mmc.android.fast.framwork.util.ActivityManager;
import oms.mmc.android.fast.framwork.util.FastUIDelegate;
import oms.mmc.android.fast.framwork.util.FragmentFactory;
import oms.mmc.android.fast.framwork.util.IViewFinder;
import oms.mmc.android.fast.framwork.util.TDevice;
import oms.mmc.factory.wait.factory.BaseWaitDialogFactory;
import oms.mmc.factory.wait.factory.IWaitViewFactory;
import oms.mmc.factory.wait.inter.IWaitViewController;

/**
 * Package: oms.mmc.android.fast.framwork.sample
 * FileName: AppCompatSupportFastActivity
 * Date: on 2018/3/21  上午11:37
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */

public abstract class AppCompatSupportFastActivity extends AppCompatCommonOperationDelegateActivity implements IFastUIInterface, IStatusBarHost {
    IFastUIDelegate mUIDelegate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ActivityManager.getActivityManager().addActivity(this);
        mUIDelegate = createFastUIDelegate();
        mUIDelegate.onCreate(savedInstanceState);
        super.onCreate(savedInstanceState);
        mUIDelegate.performLayoutBefore();
        mUIDelegate.performLayoutView(null);
        setContentView(mUIDelegate.getRootView());
        mUIDelegate.configStatusBar();
        mUIDelegate.performFindView();
        mUIDelegate.performLayoutAfter();
        setupFragment(onSetupFragment());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mUIDelegate != null) {
            mUIDelegate.onDestroy();
        }
        ActivityManager.getActivityManager().removeActivity(this);
    }

    /**
     * 是否需要透明状态栏，默认为false，需要则子类重写并返回true；
     * 注意，透明后布局会上移，建议顶部的ToolBar的高度加上状态栏的高度
     */
    @Override
    public boolean hasTranslucentStatusBar() {
        return false;
    }

    /**
     * 设置透明状态栏
     */
    @Override
    public void setTranslucentStatusBar() {
        mUIDelegate.setTranslucentStatusBar();
    }

    /**
     * 设置状态栏文字黑色，暂只支持小米、魅族
     */
    @Override
    public void setBlackStatusBar() {
        mUIDelegate.setBlackStatusBar();
    }

    @Override
    public void hideStatusBar() {
        mUIDelegate.hideStatusBar();
    }

    @Override
    public void showStatusBar() {
        mUIDelegate.showStatusBar();
    }

    /**
     * 返回等待弹窗样式工厂
     */
    @Override
    public IWaitViewFactory onWaitDialogFactoryReady() {
        return new BaseWaitDialogFactory();
    }

    @Override
    public void onLayoutBefore() {
    }

    @Override
    public void onLayoutAfter() {
    }

    @Override
    public void onFindView(IViewFinder finder) {
    }

    public final <E extends View> E findView(int id) {
        return (E) findViewById(id);
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
        IWaitViewController controller = getFastUIDelegate().getWaitViewController();
        if (controller == null) {
            controller = getFastUIDelegate().getWaitViewController();
        }
        return controller;
    }

    protected void setResult(int resultCode, Bundle bundle) {
        Intent intent = new Intent();
        intent.putExtras(bundle);
        setResult(resultCode, intent);
    }

    @Override
    public IFastUIDelegate createFastUIDelegate() {
        FastUIDelegate delegate = new FastUIDelegate();
        delegate.attachUIIml(this);
        return delegate;
    }

    @Override
    public IFastUIDelegate getFastUIDelegate() {
        if (mUIDelegate == null) {
            mUIDelegate = createFastUIDelegate();
        }
        return mUIDelegate;
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public void finish() {
        super.finish();
        TDevice.hideSoftKeyboard(getWindow().getDecorView());
    }

    protected FragmentFactory.FragmentInfoWrapper onSetupFragment() {
        return null;
    }

    /**
     * 初始化对应的的fragment，当界面很简单，只有一个activity对应一个fragment时使用
     *
     * @param infoWrapper fragment和跳转数据的包装
     */
    private void setupFragment(FragmentFactory.FragmentInfoWrapper infoWrapper) {
        if (infoWrapper == null) {
            return;
        }
        if (infoWrapper.getClazz() == null) {
            return;
        }
        if (infoWrapper.getContainerViewId() == 0) {
            infoWrapper.setContainerViewId(android.R.id.content);
        }
        Fragment fragment = createFragment(infoWrapper.getClazz(), infoWrapper.getArgs());
        addFragment(fragment, infoWrapper.getContainerViewId());
        if (!fragment.isVisible()) {
            showFragment(fragment);
        }
    }

    /**
     * 将Intent的数据转移到Fragment
     */
    public Bundle transformActivityData() {
        Intent intent = getIntent();
        Bundle bundle = new Bundle();
        if (intent.getExtras() != null && !intent.getExtras().isEmpty()) {
            bundle.putAll(intent.getExtras());
        }
        return bundle;
    }

    @Override
    public Handler initHandler() {
        return getFastUIDelegate().initHandler();
    }

    @Override
    public void post(Runnable runnable) {
        getFastUIDelegate().post(runnable);
    }

    @Override
    public void postDelayed(Runnable runnable, long duration) {
        getFastUIDelegate().postDelayed(runnable, duration);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        onSaveState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        onRestoreState(savedInstanceState);
    }

    @Override
    public void onSaveState(Bundle stateBundle) {
        getViewFinder().saveInstance(stateBundle);
    }

    @Override
    public void onRestoreState(Bundle stateBundle) {
        getViewFinder().restoreInstance(stateBundle);
    }
}