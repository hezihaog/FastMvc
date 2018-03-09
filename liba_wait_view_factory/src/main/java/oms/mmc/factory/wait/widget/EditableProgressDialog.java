package oms.mmc.factory.wait.widget;

import android.app.ProgressDialog;
import android.content.Context;

import oms.mmc.factory.wait.inter.IEditableDialog;

/**
 * Package: oms.mmc.factory.wait.widget
 * FileName: EditableProgressDialog
 * Date: on 2018/3/4  下午2:07
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */

public class EditableProgressDialog extends ProgressDialog implements IEditableDialog {
    public EditableProgressDialog(Context context) {
        super(context);
    }

    public EditableProgressDialog(Context context, int theme) {
        super(context, theme);
    }

    @Override
    public void setMessage(int messageResId) {
        super.setMessage(getContext().getResources().getString(messageResId));
    }
}