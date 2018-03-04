package oms.mmc.android.fast.framwork.sample.widget;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.widget.TextView;

import oms.mmc.android.fast.framwork.sample.R;
import oms.mmc.factory.wait.inter.IEditableDialog;


public class IOSWaitDialog extends Dialog implements IEditableDialog {
    private TextView message;

    public IOSWaitDialog(Context context) {
        this(context, R.layout.view_ios_wait_dialog, R.style.ios_dialog_wait);
    }

    public IOSWaitDialog(Context context, int layout, int style) {
        super(context, style);
        View rootLayout = LayoutInflater.from(context).inflate(layout, null);
        setContentView(rootLayout);
        Window window = getWindow();
        LayoutParams params = window.getAttributes();
        params.gravity = Gravity.CENTER;
        params.width = LayoutParams.WRAP_CONTENT;
        params.height = LayoutParams.WRAP_CONTENT;
        params.dimAmount = 0.0f;
        window.setAttributes(params);
        message = (TextView) findViewById(R.id.message);
        this.setCancelable(true);
        this.setCanceledOnTouchOutside(true);
    }

    public void setMessage(CharSequence msg) {
        if (TextUtils.isEmpty(msg)) {
            message.setVisibility(View.GONE);
        } else {
            message.setVisibility(View.VISIBLE);
            message.setText(msg);
        }
    }

    @Override
    public void setMessage(int messageResId) {
        setMessage(getContext().getResources().getString(messageResId));
    }

    public void showMessage(CharSequence msg) {
        setMessage(msg);
        show();
    }
}
