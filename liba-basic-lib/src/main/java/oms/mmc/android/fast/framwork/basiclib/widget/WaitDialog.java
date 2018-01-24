package oms.mmc.android.fast.framwork.basiclib.widget;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.widget.TextView;

import oms.mmc.android.fast.framwork.basiclib.R;


public class WaitDialog extends Dialog {
    private static int DEFAULT_STYLE = R.style.dialog_wait;
    private static int DEFAULT_LAYOUT = R.layout.dialog_wait;
    private TextView message;

    public WaitDialog(Context context) {
        this(context, DEFAULT_LAYOUT, DEFAULT_STYLE);
    }

    public WaitDialog(Context context, int layout, int style) {
        super(context, style);
        setContentView(layout);
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

    public void setMessage(String msg) {
        if (TextUtils.isEmpty(msg)) {
            message.setVisibility(View.GONE);
        } else {
            message.setVisibility(View.VISIBLE);
            message.setText(msg);
        }
    }

    public void showMessage(String msg) {
        setMessage(msg);
        show();
    }
}
