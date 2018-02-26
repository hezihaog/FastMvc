package oms.mmc.android.fast.framwork.sample.widget;

import android.app.Activity;
import android.app.Dialog;

import oms.mmc.factory.wait.AbsWaitDialog;

/**
 * Package: oms.mmc.android.fast.framwork.sample.widget
 * FileName: IOSWaitDialogIml
 * Date: on 2018/2/5  下午6:28
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */

public class IOSWaitDialogIml extends AbsWaitDialog {

    @Override
    public Dialog onCreateDialog(Activity activity, CharSequence msg) {
        IOSWaitDialog dialog = new IOSWaitDialog(activity);
        dialog.setMessage(msg);
        return dialog;
    }
}
