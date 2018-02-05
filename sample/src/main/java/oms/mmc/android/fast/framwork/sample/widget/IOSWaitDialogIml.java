package oms.mmc.android.fast.framwork.sample.widget;

import android.app.Activity;
import android.content.DialogInterface;

import oms.mmc.android.fast.framwork.basiclib.util.WaitDialogHelper;

/**
 * Package: oms.mmc.android.fast.framwork.sample.widget
 * FileName: IOSWaitDialogIml
 * Date: on 2018/2/5  下午6:28
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */

public class IOSWaitDialogIml extends WaitDialogHelper.AbsWait {
    private IOSWaitDialog dialog;
    private int hostHashCode;

    @Override
    public void showWaitDialog(Activity activity) {
        showWaitDialog(activity);
    }

    @Override
    public void showWaitDialog(final Activity activity, CharSequence msg, final boolean isTouchCancelable) {
        hostHashCode = activity.hashCode();
        dialog = new IOSWaitDialog(activity);
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
        dialog.show();
    }

    @Override
    public void hideWaitDialog() {
        if (dialog != null && isShowing()) {
            dialog.dismiss();
        }
    }

    @Override
    public boolean isShowing() {
        return dialog != null && dialog.isShowing();
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
