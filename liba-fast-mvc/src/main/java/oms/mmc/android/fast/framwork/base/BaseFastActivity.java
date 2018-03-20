package oms.mmc.android.fast.framwork.base;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import oms.mmc.android.fast.framwork.BaseFastApplication;
import oms.mmc.android.fast.framwork.util.ActivityManager;
import oms.mmc.android.fast.framwork.util.AppCompatScrollableReplaceAdapter;
import oms.mmc.android.fast.framwork.util.FragmentFactory;
import oms.mmc.android.fast.framwork.util.TDevice;
import oms.mmc.android.fast.framwork.util.ViewFinder;
import oms.mmc.factory.wait.WaitDialogController;
import oms.mmc.factory.wait.factory.BaseWaitDialogFactory;
import oms.mmc.factory.wait.factory.IWaitViewFactory;
import oms.mmc.factory.wait.inter.IWaitViewController;
import oms.mmc.factory.wait.inter.IWaitViewHost;
import oms.mmc.helper.base.ScrollableViewFactory;

/**
 * Activity基类
 */
public abstract class BaseFastActivity extends CommonOperationDelegateActivity implements LayoutCallback
        , IWaitViewHandler, IHandlerDispatcher, IWaitViewHost, IInstanceState {
    private ViewFinder mViewFinder;
    private Handler mMainHandler;
    private WaitDialogController mWaitDialogController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ScrollableViewFactory.create(this, new AppCompatScrollableReplaceAdapter()).install();
        super.onCreate(savedInstanceState);
        ActivityManager.getActivityManager().addActivity(this);
        onLayoutBefore();
        if (mViewFinder == null) {
            mViewFinder = new ViewFinder(getActivity(), onLayoutView(getLayoutInflater(), null));
        } else {
            mViewFinder.setActivity(this);
            mViewFinder.setRootView(getContentView());
        }
        mWaitDialogController = onWaitDialogFactoryReady().madeWaitDialogController(this);
        setContentView(mViewFinder.getRootView());
        if (hasTranslucentStatusBar()) {
            onStatusBarSet();
            onSetStatusBarBlack();
        }
        onFindView(mViewFinder);
        onLayoutAfter();
        setupFragment(onSetupFragment());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        TDevice.hideSoftKeyboard(getContentView());
        if (getViewFinder() != null) {
            getViewFinder().recycle();
        }
        ActivityManager.getActivityManager().removeActivity(this);
    }

    /**
     * 设置状态栏
     */
    protected void onStatusBarSet() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(Color.BLACK);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            //miui、flyme透明衔接，非miui、flyme加上阴影
            if (TDevice.isMiui() || TDevice.isMeizu()) {
                getWindow().setStatusBarColor(Color.TRANSPARENT);
            } else {
                getWindow().setStatusBarColor(Color.argb((int) (255 * 0.2f), 0, 0, 0));
            }
        }
    }

    /**
     * 设置状态栏文字黑色
     */
    protected void onSetStatusBarBlack() {
        TDevice.setStatusBarMode(getActivity(), true);
    }

    protected void hideStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            WindowManager.LayoutParams attrs = getWindow().getAttributes();
            attrs.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
            getWindow().setAttributes(attrs);
        }
    }

    protected void showStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            WindowManager.LayoutParams attrs = getWindow().getAttributes();
            attrs.flags &= ~WindowManager.LayoutParams.FLAG_FULLSCREEN;
            getWindow().setAttributes(attrs);
        }
    }

    /**
     * 返回等待弹窗样式工厂
     */
    protected IWaitViewFactory onWaitDialogFactoryReady() {
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
    public void onFindView(ViewFinder finder) {
    }

    public final <E extends View> E findView(int id) {
        return (E) findViewById(id);
    }

    @Override
    public ViewFinder getViewFinder() {
        if (mViewFinder == null) {
            mViewFinder = new ViewFinder(getActivity(), onLayoutView(getLayoutInflater(), null));
        }
        return mViewFinder;
    }

    @Override
    public void showWaitDialog() {
        mWaitDialogController.getWaitIml().showWaitDialog(getActivity(), "", false);
    }

    @Override
    public void showWaitDialog(String msg) {
        mWaitDialogController.getWaitIml().showWaitDialog(getActivity(), msg, false);
    }

    @Override
    public void showWaitDialog(String msg, final boolean isTouchCancelable) {
        mWaitDialogController.getWaitIml().showWaitDialog(getActivity(), msg, isTouchCancelable);
    }

    @Override
    public void hideWaitDialog() {
        mWaitDialogController.getWaitIml().hideWaitDialog();
    }

    @Override
    public IWaitViewController getWaitViewController() {
        return mWaitDialogController;
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
        Handler handler = null;
        if (getApplication() instanceof BaseFastApplication) {
            handler = ((BaseFastApplication) getApplication()).getMainHandler();
        }
        if (handler == null) {
            handler = new Handler(getMainLooper());
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

    /**
     * 获取设置的ContentView
     */
    public View getContentView() {
        return ((ViewGroup) this.findViewById(android.R.id.content)).getChildAt(0);
    }
}
