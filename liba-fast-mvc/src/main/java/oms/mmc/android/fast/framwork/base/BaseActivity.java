package oms.mmc.android.fast.framwork.base;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.WindowManager;

import com.hzh.lifecycle.dispatch.base.LifecycleActivity;

import oms.mmc.android.fast.framwork.R;
import oms.mmc.android.fast.framwork.adapter.ScrollableLayoutFactory;
import oms.mmc.android.fast.framwork.basiclib.util.ActivityManager;
import oms.mmc.android.fast.framwork.basiclib.util.FragmentFactory;
import oms.mmc.android.fast.framwork.basiclib.util.TDevice;
import oms.mmc.android.fast.framwork.basiclib.util.ToastUtil;
import oms.mmc.android.fast.framwork.basiclib.util.ViewFinder;
import oms.mmc.android.fast.framwork.basiclib.util.WaitDialogController;
import oms.mmc.android.fast.framwork.bean.IResult;

/**
 * Activity基类
 */
public abstract class BaseActivity extends LifecycleActivity implements ApiCallback, LayoutCallback {
    private ViewFinder viewFinder;
    private WaitDialogController mWaitController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        installLayoutInflaterFactory();
        super.onCreate(savedInstanceState);
        ActivityManager.getActivityManager().addActivity(this);
        onLayoutBefore();
        viewFinder = new ViewFinder(getLayoutInflater(), null, onLayoutId());
        mWaitController = onGetWaitDialogController();
        setContentView(viewFinder.getRootView());
        //onStatusBarSet();
        //onSetStatusBarBlack();
        onFindView(viewFinder);
        onLayoutAfter();
        setupFragment(onGetFragmentInfo());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
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

    protected WaitDialogController onGetWaitDialogController() {
        return new WaitDialogController(this);
    }

    /**
     * 添加Scrollable控件替换工厂
     */
    private void installLayoutInflaterFactory() {
        ScrollableLayoutFactory.create(getActivity()).install();
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
        return viewFinder;
    }

    public void showWaitDialog() {
        mWaitController.getWaitIml().showWaitDialog(getActivity(), "", false);
    }

    public void showWaitDialog(String msg) {
        mWaitController.getWaitIml().showWaitDialog(getActivity(), msg, false);
    }

    public void showWaitDialog(String msg, final boolean isTouchCancelable) {
        mWaitController.getWaitIml().showWaitDialog(getActivity(), msg, isTouchCancelable);
    }

    public void hideWaitDialog() {
        mWaitController.getWaitIml().hideWaitDialog();
    }

    protected WaitDialogController getWaitController() {
        return mWaitController;
    }

    protected void setResult(int resultCode, Bundle bundle) {
        Intent intent = new Intent();
        intent.putExtras(bundle);
        setResult(resultCode, intent);
    }

    public BaseActivity getActivity() {
        return this;
    }

    @Override
    public void onApiStart(String tag) {
    }

    @Override
    public void onApiLoading(long count, long current, String tag) {
    }

    @Override
    public void onApiSuccess(IResult res, String tag) {
    }

    @Override
    public void finish() {
        super.finish();
        TDevice.hideSoftKeyboard(getWindow().getDecorView());
    }

    @Override
    public void onApiFailure(Throwable t, int errorNo, String strMsg, String tag) {
        ToastUtil.showToast(getActivity(), R.string.net_tip_net_request_error);
        t.printStackTrace();
        onApiError(tag);
    }

    @Override
    public void onParseError(String tag) {
        ToastUtil.showToast(getActivity(), R.string.net_tip_net_parse_data_error);
        onApiError(tag);
    }

    protected void onApiError(String tag) {
    }

    protected FragmentFactory.FragmentInfoWrapper onGetFragmentInfo() {
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
