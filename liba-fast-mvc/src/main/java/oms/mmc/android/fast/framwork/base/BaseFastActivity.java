package oms.mmc.android.fast.framwork.base;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.View;

import oms.mmc.android.fast.framwork.util.ActivityManager;
import oms.mmc.android.fast.framwork.util.FastUIDelegate;
import oms.mmc.android.fast.framwork.util.FragmentFactory;
import oms.mmc.android.fast.framwork.util.IViewFinder;
import oms.mmc.android.fast.framwork.util.TDevice;
import oms.mmc.factory.wait.factory.BaseWaitDialogFactory;
import oms.mmc.factory.wait.factory.IWaitViewFactory;
import oms.mmc.factory.wait.inter.IWaitViewController;

/**
 * Activity基类
 */
public abstract class BaseFastActivity extends CommonOperationDelegateActivity implements IFastUIInterface {
    IFastUIDelegate mUIDelegate = new FastUIDelegate();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mUIDelegate.attachUIIml(this);
        mUIDelegate.onCreate(savedInstanceState);
        super.onCreate(savedInstanceState);
        ActivityManager.getActivityManager().addActivity(this);
        mUIDelegate.performLayoutBefore();
        mUIDelegate.performLayoutView(null);
        setContentView(mUIDelegate.getRootView());
        if (hasTranslucentStatusBar()) {
            setTranslucentStatusBar();
            setBlackStatusBar();
        }
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
     * 设置透明状态栏
     */
    protected void setTranslucentStatusBar() {
        TDevice.setTranslucentStatusBar(getActivity());
    }

    /**
     * 设置状态栏文字黑色，暂只支持小米、魅族
     */
    protected void setBlackStatusBar() {
        TDevice.setStatusBarMode(getActivity(), true);
    }

    protected void hideStatusBar() {
        TDevice.hideStatusBar(getActivity());
    }

    protected void showStatusBar() {
        TDevice.showStatusBar(getActivity());
    }

    /**
     * 返回等待弹窗样式工厂
     */
    @Override
    public IWaitViewFactory onWaitDialogFactoryReady() {
        return new BaseWaitDialogFactory();
    }

    /**
     * 是否需要透明状态栏，默认为false，需要则子类重写并返回true；
     * 注意，透明后布局会上移，建议顶部的ToolBar的高度加上状态栏的高度
     */
    protected boolean hasTranslucentStatusBar() {
        return false;
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
        return mUIDelegate.getWaitViewController();
    }

    protected void setResult(int resultCode, Bundle bundle) {
        Intent intent = new Intent();
        intent.putExtras(bundle);
        setResult(resultCode, intent);
    }

    @Override
    public BaseFastActivity getActivity() {
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
        return mUIDelegate.initHandler();
    }

    @Override
    public void post(Runnable runnable) {
        mUIDelegate.post(runnable);
    }

    @Override
    public void postDelayed(Runnable runnable, long duration) {
        mUIDelegate.postDelayed(runnable, duration);
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
