package oms.mmc.factory.wait.iml;

import android.app.Activity;

import oms.mmc.factory.wait.AbsWaitDialogIml;
import oms.mmc.factory.wait.widget.EditableProgressDialog;


/**
 * Package: oms.mmc.android.fast.framwork.basiclib.util
 * FileName: ProgressDialogWaitIml
 * Date: on 2018/2/5  下午12:32
 * Auther: zihe
 * Descirbe:ProgressDialog形式的等待覆盖
 * Email: hezihao@linghit.com
 */

public class ProgressWaitDialogIml extends AbsWaitDialogIml<EditableProgressDialog> {

    @Override
    public EditableProgressDialog onCreateDialog(Activity activity, CharSequence msg) {
        EditableProgressDialog dialog = new EditableProgressDialog(activity);
        if (msg != null) {
            dialog.setMessage(msg);
        }
        return dialog;
    }
}