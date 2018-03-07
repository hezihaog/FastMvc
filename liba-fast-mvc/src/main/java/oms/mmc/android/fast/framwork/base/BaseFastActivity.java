package oms.mmc.android.fast.framwork.base;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;

import oms.mmc.android.fast.framwork.util.ActivityManager;
import oms.mmc.android.fast.framwork.util.AppCompatScrollableReplaceAdapter;
import oms.mmc.android.fast.framwork.util.FragmentFactory;
import oms.mmc.android.fast.framwork.util.TDevice;
import oms.mmc.android.fast.framwork.util.ViewFinder;
import oms.mmc.android.fast.framwork.util.WaitViewManager;
import oms.mmc.factory.wait.factory.BaseWaitDialogFactory;
import oms.mmc.factory.wait.factory.IWaitViewFactory;
import oms.mmc.factory.wait.inter.IWaitViewController;
import oms.mmc.helper.base.ScrollableViewFactory;

/**
 * Activity基类
 */
public abstract class BaseFastActivity extends CommonOperationDelegateActivity implements LayoutCallback, IWaitViewHandler, IHandlerDispatcher {
    private ViewFinder mViewFinder;
    private String bindFragmentTag;
    private Handler mMainHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ScrollableViewFactory.create(this, new AppCompatScrollableReplaceAdapter()).install();
        super.onCreate(savedInstanceState);
        mMainHandler = initHandler();
        ActivityManager.getActivityManager().addActivity(this);
        onLayoutBefore();
        mViewFinder = new ViewFinder(getActivity(), onLayoutView(getLayoutInflater(), null));
        IWaitViewFactory waitViewFactory = onWaitDialogFactoryReady();
        if (waitViewFactory != null && waitViewFactory.getWaitDialogController(getActivity()) != null) {
            WaitViewManager.getInstnace().add(this, waitViewFactory.getWaitDialogController(getActivity()));
        }
        setContentView(mViewFinder.getRootView());
        if (hasTranslucentStatusBar()) {
            onStatusBarSet();
            onSetStatusBarBlack();
        }
        onFindView(mViewFinder);
        onLayoutAfter();
        //没有绑定fragment时，才调用
        if (!hasBindFragment()) {
            setupFragment(onSetupFragment());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        WaitViewManager.getInstnace().remove(this);
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
        WaitViewManager.getInstnace().hideWaitDialog(this);
    }

    @Override
    public IWaitViewController getWaitController() {
        return WaitViewManager.getInstnace().find(this);
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
    protected void setupFragment(FragmentFactory.FragmentInfoWrapper infoWrapper) {
        if (infoWrapper == null) {
            return;
        }
        if (infoWrapper.getClazz() == null) {
            return;
        }
        if (infoWrapper.getContainerViewId() == 0) {
            infoWrapper.setContainerViewId(android.R.id.content);
        }
        Fragment fragment = FragmentFactory.newInstance(getActivity(), infoWrapper.getClazz()
                , infoWrapper.getArgs());
        bindFragmentTag = fragment.getClass().getName();
        getSupportFragmentManager()
                .beginTransaction()
                .add(infoWrapper.getContainerViewId(), fragment, bindFragmentTag)
                .commit();
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

    /**
     * 查找是否已经有绑定的fragment，用于内存重启时，会自动恢复fragment的实例，不重复添加fragment
     */
    private boolean hasBindFragment() {
        if (TextUtils.isEmpty(bindFragmentTag)) {
            return false;
        }
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(bindFragmentTag);
        if (fragment == null) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public Handler initHandler() {
        return new Handler(getMainLooper());
    }

    @Override
    public void post(Runnable runnable) {
        mMainHandler.post(runnable);
    }

    @Override
    public void postDelayed(Runnable runnable, long duration) {
        mMainHandler.postDelayed(runnable, duration);
    }
}
