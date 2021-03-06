package oms.mmc.android.fast.framwork.util;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;


/**
 * Created by Hezihao on 2017/7/6.
 * Toast 工具类
 */

public class ToastOperator implements IToastOperator {
    private static final String TAG = ToastOperator.class.getSimpleName();
    private Context mContext;
    private final Handler mMainHandler;

    public ToastOperator(Context context) {
        mContext = context.getApplicationContext();
        mMainHandler = new Handler(Looper.getMainLooper());
    }

    public Context getContext() {
        return mContext;
    }

    /**
     * 获取资源id对应的字符串
     */
    private String getString(int id) {
        return getContext().getApplicationContext().getResources().getString(id);
    }

    /**
     * 以资源id显示短Toast信息
     */
    @Override
    public void toast(int message) {
        toast(getString(message), android.widget.Toast.LENGTH_SHORT);
    }

    /**
     * 以直接字符串显示短Toast信息
     */
    @Override
    public void toast(CharSequence message) {
        toast(message, android.widget.Toast.LENGTH_SHORT);
    }

    /**
     * 以资源id显示长Toast信息
     */
    @Override
    public void toastLong(int message) {
        toast(getString(message), Toast.LENGTH_LONG);
    }

    /**
     * 以直接字符串显示长Toast信息
     */
    @Override
    public void toastLong(CharSequence message) {
        toast(message, Toast.LENGTH_LONG);
    }

    @Override
    public void toast(int message, int duration) {
        toast(getString(message), duration);
    }

    /**
     * 显示toast信息
     */
    @Override
    public void toast(final CharSequence message, final int duration) {
        if (TextUtils.isEmpty(message)) {
            return;
        }
        Runnable toastTask = new Runnable() {
            @Override
            public void run() {
                if (!TextUtils.isEmpty(message)) {
                    Log.d(TAG, message.toString());
                    Toast.makeText(getContext(), Html.fromHtml(message.toString()), duration).show();
                }
            }
        };
        if (Thread.currentThread() == mMainHandler.getLooper().getThread()) {
            toastTask.run();
        } else {
            mMainHandler.post(toastTask);
        }
    }
}
