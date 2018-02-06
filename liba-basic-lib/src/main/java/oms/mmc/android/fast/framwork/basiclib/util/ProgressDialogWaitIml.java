package oms.mmc.android.fast.framwork.basiclib.util;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;

/**
 * Package: oms.mmc.android.fast.framwork.basiclib.util
 * FileName: ProgressDialogWaitIml
 * Date: on 2018/2/5  下午12:32
 * Auther: zihe
 * Descirbe:ProgressDialog形式的等待覆盖
 * Email: hezihao@linghit.com
 */

public class ProgressDialogWaitIml extends WaitDialogHelper.AbsWait {
    private ProgressDialog dialog;
    private int hostHashCode;

    @Override
    public void showWaitDialog(Activity activity) {
        showWaitDialog(activity, null, true);
    }

    @Override
    public void showWaitDialog(final Activity activity, CharSequence msg, final boolean isTouchCancelable) {
        if (!(activity != null && !activity.isFinishing())) {
            return;
        }
        hostHashCode = activity.hashCode();
        if (dialog == null) {
            dialog = new ProgressDialog(activity);
        }
        if (isShowing()) {
            return;
        }
        if (msg != null) {
            dialog.setMessage(msg);
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
        if (activity != null && activity.isFinishing()) {
            dialog.show();
        }
    }

    @Override
    public void hideWaitDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
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
    public boolean isHost(Activity activity) {
        return hostHashCode == activity.hashCode();
    }

    @Override
    public void destroyDialog() {
        dialog.dismiss();
        dialog = null;
    }
}