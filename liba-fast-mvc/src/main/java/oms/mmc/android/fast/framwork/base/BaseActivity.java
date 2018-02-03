package oms.mmc.android.fast.framwork.base;

import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.hzh.lifecycle.dispatch.base.LifecycleActivity;

import oms.mmc.android.fast.framwork.R;
import oms.mmc.android.fast.framwork.basiclib.util.ActivityManager;
import oms.mmc.android.fast.framwork.basiclib.util.TDevice;
import oms.mmc.android.fast.framwork.basiclib.util.ToastUtil;
import oms.mmc.android.fast.framwork.basiclib.util.ViewFinder;
import oms.mmc.android.fast.framwork.basiclib.widget.WaitDialog;
import oms.mmc.android.fast.framwork.bean.IResult;

/**
 * Activity基类
 */
public abstract class BaseActivity extends LifecycleActivity implements ApiCallback, LayoutCallback {
    protected WaitDialog mWaitDialog;
    private ViewFinder viewFinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityManager.getActivityManager().addActivity(this);
        onLayoutBefore();
        viewFinder = new ViewFinder(getLayoutInflater(), null, onLayoutId());
        setContentView(viewFinder.getRootView());
        //onStatusBarSet();
        //onSetStatusBarBlack();
        onFindView(viewFinder);
        onLayoutAfter();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityManager.getActivityManager().removeActivity(this);
        if (mWaitDialog != null) {
            mWaitDialog.dismiss();
        }
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
        showWaitDialog("", false);
    }

    public void showWaitDialog(String message) {
        showWaitDialog(message, false);
    }

    public void showWaitDialog(String msg, final boolean isNotBackFinish) {
        if (getActivity() != null && !getActivity().isFinishing()) {
            if (mWaitDialog != null && mWaitDialog.isShowing()) {
                return;
            }
            if (mWaitDialog == null) {
                mWaitDialog = new WaitDialog(this);
            }
            mWaitDialog.setCanceledOnTouchOutside(isNotBackFinish);
            mWaitDialog.setOnCancelListener(new OnCancelListener() {

                @Override
                public void onCancel(DialogInterface dialog) {
                    if (!isNotBackFinish) {
                        finish();
                    }
                }
            });
            mWaitDialog.showMessage(msg);
        }
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
}
