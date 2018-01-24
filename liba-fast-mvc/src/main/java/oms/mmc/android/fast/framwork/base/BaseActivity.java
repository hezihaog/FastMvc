package oms.mmc.android.fast.framwork.base;

import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.ButterKnife;
import oms.mmc.android.fast.framwork.BaseMMCFastApplication;
import oms.mmc.android.fast.framwork.basiclib.util.ActivityManager;
import oms.mmc.android.fast.framwork.basiclib.util.TDevice;
import oms.mmc.android.fast.framwork.basiclib.util.ViewUtil;
import oms.mmc.android.fast.framwork.basiclib.widget.WaitDialog;
import oms.mmc.android.fast.framwork.bean.IResult;
import oms.mmc.android.fast.framwork.manager.factory.ManagerFactory;
import oms.mmc.android.lifecycle.dispatch.base.LifecycleActivity;

/**
 * Activity基类
 */
public abstract class BaseActivity extends LifecycleActivity implements ApiCallback, LayoutCallback {
    protected FragmentManager fm;
    protected BaseMMCFastApplication ac;
    protected BaseActivity mActivity;
    protected Intent mIntent;
    protected Bundle mBundle;
    protected WaitDialog mWaitDialog;
    private ImageView disableView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityManager.getActivityManager().addActivity(this);
        fm = getSupportFragmentManager();
        ac = (BaseMMCFastApplication) getApplication();
        mActivity = this;
        mIntent = getIntent();
        if (mIntent != null) {
            mBundle = mIntent.getExtras();
        }
        onLayoutBefore();
        setContentView(onLayoutId());
        //onStatusBarSet();
        //onSetStatusBarBlack();
        onBindView();
        onReady(hasReqBase());
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
     * 是否请求基础权限，需要返回true，会回调请求，只在appStart使用
     */
    protected boolean hasReqBase() {
        return false;
    }

    protected void onReady(boolean isRead) {
        onLayoutAfter();
        if (isRead) {
            ac.postDelayed(new Runnable() {
                @Override
                public void run() {
                    requestBasePermission();
                }
            }, 150);
        }
    }

    protected void requestBasePermission() {
//        final PermissionManager permissionManager = ac.getManagerFactory().getPermissionManager();
//        permissionManager.requestBase(_activity, new PermissionCallback() {
//            @Override
//            public void onGranted() {
//                onBasePermissionRequestSuccess();
//            }
//
//            @Override
//            public void onDenied(List<String> perms) {
//                new UltimatumPermDialog(_activity).show();
//            }
//        });
    }

    @CallSuper
    protected void onBasePermissionRequestSuccess() {
        ManagerFactory factory = ac.getManagerFactory();
    }

    protected void onBindView() {
        ButterKnife.bind(this);
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
        TDevice.setStatusBarMode(mActivity, true);
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
     * 页面是否需要加入统计
     */
    protected boolean isNeedStat() {
        return true;
    }

    @Override
    public void onLayoutBefore() {

    }

    @Override
    public void onLayoutAfter() {

    }

    public String intentStr(String key) {
        return mIntent.getStringExtra(key);
    }

    public final <E extends View> E findView(int id) {
        return (E) findViewById(id);
    }


    public static void setText(String text, TextView view) {
        ViewUtil.setText(text, view);
    }

    public static void setTextWithDefault(String text, String defaultText, TextView view) {
        ViewUtil.setTextWithDefault(text, defaultText, view);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void showWaitDialog() {
        showWaitDialog("", false);
    }

    public void showWaitDialog(String message) {
        showWaitDialog(message, false);
    }

    public void showWaitDialog(String msg, final boolean isNotBackFinish) {
        if (mActivity != null && !mActivity.isFinishing()) {
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

    public void hideWaitDialog() {
        if (mWaitDialog != null) {
            mWaitDialog.dismiss();
        }
    }

    public void showDisableView() {
        FrameLayout decorView = (FrameLayout) getWindow().getDecorView();
        disableView = new ImageView(mActivity);
        disableView.setImageResource(android.R.color.black);
        disableView.setAlpha(0f);
        disableView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        decorView.addView(disableView, params);
    }

    public void hideDisableView() {
        if (disableView != null) {
            FrameLayout decorView = (FrameLayout) getWindow().getDecorView();
            decorView.removeView(disableView);
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
        BaseMMCFastApplication.showToast("网络请求错误");
        t.printStackTrace();
        onApiError(tag);
    }

    @Override
    public void onParseError(String tag) {
        BaseMMCFastApplication.showToast("数据解析错误");
        onApiError(tag);
    }

    protected void onApiError(String tag) {
    }
}
