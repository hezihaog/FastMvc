package oms.mmc.android.fast.framwork.sample.widget;

import android.app.Activity;

import oms.mmc.factory.wait.AbsWaitDialogIml;

/**
 * Package: oms.mmc.android.fast.framwork.sample.widget
 * FileName: IOSWaitDialogIml
 * Date: on 2018/2/5  下午6:28
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */

public class IOSWaitDialogIml extends AbsWaitDialogIml<IOSWaitDialog> {

    @Override
    public IOSWaitDialog onCreateDialog(Activity activity, CharSequence msg) {
        IOSWaitDialog dialog = new IOSWaitDialog(activity);
        dialog.setMessage(msg);
        return dialog;
    }
}