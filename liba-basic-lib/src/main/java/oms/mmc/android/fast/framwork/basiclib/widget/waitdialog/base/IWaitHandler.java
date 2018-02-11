package oms.mmc.android.fast.framwork.basiclib.widget.waitdialog.base;

import android.app.Activity;

/**
 * WaitDialog实际处理者
 */
public interface IWaitHandler<T> {
    T onCreateDialog(Activity activity, CharSequence msg);

    void onShowWaitDialog();

    void onHideWaitDialog();

    void onDestroyDialog();
}