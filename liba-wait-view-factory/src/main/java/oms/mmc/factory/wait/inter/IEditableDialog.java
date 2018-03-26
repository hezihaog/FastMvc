package oms.mmc.factory.wait.inter;

import android.content.DialogInterface;

/**
 * Package: oms.mmc.factory.wait.inter
 * FileName: IEditableDialog
 * Date: on 2018/3/4  下午1:48
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */

public interface IEditableDialog extends DialogInterface {
    void setMessage(CharSequence message);

    void setMessage(int messageResId);

    void setTitle(CharSequence title);

    void setTitle(int titleResId);
}