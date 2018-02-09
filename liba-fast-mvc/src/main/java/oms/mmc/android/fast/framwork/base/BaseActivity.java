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
import oms.mmc.android.fast.framwork.basiclib.util.ActivityManager;
import oms.mmc.android.fast.framwork.basiclib.util.FragmentFactory;
import oms.mmc.android.fast.framwork.basiclib.util.TDevice;
import oms.mmc.android.fast.framwork.basiclib.util.ToastUtil;
import oms.mmc.android.fast.framwork.basiclib.util.ViewFinder;
import oms.mmc.android.fast.framwork.basiclib.util.WaitDialogHelper;
import oms.mmc.android.fast.framwork.bean.IResult;

/**
 * Activity基类
 */
public abstract class BaseActivity extends LifecycleActivity implements ApiCallback, LayoutCallback {
    private ViewFinder viewFinder;
    private WaitDialogHelper.WaitAction waitAction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityManager.getActivityManager().addActivity(this);
        onLayoutBefore();
        viewFinder = new ViewFinder(getLayoutInflater(), null, onLayoutId());
        waitAction = WaitDialogHelper.getInstance().newAction();
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

    @Override
    public void onLayoutBefore() {

    }

    @Override
    public void onLayoutAfter() {

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
        waitAction.getWaitIml().showWaitDialog(getActivity(), "", false);
    }

    public void showWaitDialog(String msg) {
        waitAction.getWaitIml().showWaitDialog(getActivity(), msg, false);
    }

    public void showWaitDialog(String msg, final boolean isTouchCancelable) {
        waitAction.getWaitIml().showWaitDialog(getActivity(), msg, isTouchCancelable);
    }

    public void hideWiatDialog() {
        waitAction.getWaitIml().hideWaitDialog();
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
}
