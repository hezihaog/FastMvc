package oms.mmc.android.fast.framwork.base;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import oms.mmc.lifecycle.dispatch.base.LifecycleActivity;

/**
 * Activity基类
 */
public abstract class BaseFastActivity extends LifecycleActivity implements LayoutCallback, IWaitViewHandler {
    private ViewFinder mViewFinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ScrollableViewFactory.create(this, new AppCompatScrollableReplaceAdapter()).install();
        super.onCreate(savedInstanceState);
        ActivityManager.getActivityManager().addActivity(this);
        onLayoutBefore();
        mViewFinder = new ViewFinder(onLayoutView(getLayoutInflater(), null));
        IWaitViewFactory waitViewFactory = onGetWaitDialogFactory();
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
        setupFragment(onSetupFragment());
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

    protected IWaitViewFactory onGetWaitDialogFactory() {
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

    public String intentStr(String key) {
        return getIntent().getStringExtra(key);
    }

    public final <E extends View> E findView(int id) {
        return (E) findViewById(id);
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
        Fragment fragment = FragmentFactory.newInstance(getActivity(), infoWrapper.getClazz()
                , infoWrapper.getArgs());
        getSupportFragmentManager()
                .beginTransaction()
                .add(android.R.id.content, fragment, fragment.getClass().getName())
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
}
