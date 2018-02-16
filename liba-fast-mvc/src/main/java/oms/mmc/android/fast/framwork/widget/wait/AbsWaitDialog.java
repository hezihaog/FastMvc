package oms.mmc.android.fast.framwork.widget.wait;

import android.app.Activity;
import android.app.Application;
import android.app.Dialog;
import android.content.DialogInterface;

import oms.mmc.android.fast.framwork.adapter.SimpleActivityLifecycleCallback;


/**
 * 对话框实现类抽象类
 */
public abstract class AbsWaitDialog implements IWait, IWaitHandler<Dialog> {
    private int hostHashCode;
    private Dialog dialog;

    @Override
    public void init(final Application application) {
        SimpleActivityLifecycleCallback lifecycleCallback = new SimpleActivityLifecycleCallback() {
            @Override
            public void onActivityDestroyed(Activity activity) {
                super.onActivityDestroyed(activity);
                if (isHost(activity)) {
                    destroyDialog();
                    application.unregisterActivityLifecycleCallbacks(this);
                }
            }
        };
        application.registerActivityLifecycleCallbacks(lifecycleCallback);
    }

    @Override
    public void showWaitDialog(final Activity activity) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                showWaitDialog(activity, null, true);
            }
        });
    }

    @Override
    public void showWaitDialog(final Activity activity, CharSequence msg, final boolean isTouchCancelable) {
        if (!(activity != null && !activity.isFinishing())) {
            return;
        }
        hostHashCode = activity.hashCode();
        if (dialog == null) {
            dialog = onCreateDialog(activity, msg);
        }
        if (isShowing()) {
            return;
        }
        dialog.setCanceledOnTouchOutside(isTouchCancelable);
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                //不能点击取消的话，按返回键取消时，关闭界面
                if (!isTouchCancelable) {
                    activity.finish();
                }
            }
        });
        if (!activity.isFinishing()) {
            dialog.show();
            onShowWaitDialog();
        }
    }

    @Override
    public void hideWaitDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
            onHideWaitDialog();
        }
    }

    @Override
    public boolean isShowing() {
        if (dialog == null && isShowing()) {
            return false;
        }
        return dialog.isShowing();
    }

    @Override
    public void destroyDialog() {
        if (dialog != null) {
            dialog.dismiss();
            dialog = null;
            onDestroyDialog();
        }
    }

    @Override
    public boolean isHost(Activity activity) {
        return hostHashCode == activity.hashCode();
    }
}