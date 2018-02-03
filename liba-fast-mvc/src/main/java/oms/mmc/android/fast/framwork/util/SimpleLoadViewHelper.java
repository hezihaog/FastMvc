package oms.mmc.android.fast.framwork.util;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.View.OnClickListener;

import oms.mmc.android.fast.framwork.R;
import oms.mmc.android.fast.framwork.basiclib.util.ToastUtil;
import oms.mmc.android.fast.framwork.widget.pulltorefresh.helper.VaryViewHelper;


public class SimpleLoadViewHelper {
    private VaryViewHelper helper;
    private OnClickListener onClickRefreshListener;
    private OnStateUpdateListener listener;
    private Handler mainHandler;
    private Context context;

    public SimpleLoadViewHelper init(View content, OnClickListener onClickRefreshListener) {
        helper = new VaryViewHelper(content);
        context = content.getContext().getApplicationContext();
        mainHandler = new Handler(Looper.getMainLooper());
        this.onClickRefreshListener = onClickRefreshListener;
        return this;
    }

    public void restore() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                helper.restoreView();
                if (listener != null) {
                    listener.onRestore();
                }
            }
        };
        if (isOnMainThread()) {
            runnable.run();
        } else {
            mainHandler.post(runnable);
        }
    }

    public void showLoading() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                View layout = helper.inflate(R.layout.base_list_loading);
                helper.showLayout(layout);
                if (listener != null) {
                    listener.onShowLoading();
                }
            }
        };
        if (isOnMainThread()) {
            runnable.run();
        } else {
            mainHandler.post(runnable);
        }
    }

    public void tipFail() {
        ToastUtil.showToast(context, R.string.net_tip_net_load_error);
    }

    public void showEmpty() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                View layout = helper.inflate(R.layout.base_list_empty);
                layout.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
                helper.showLayout(layout);
                if (listener != null) {
                    listener.onShowEmpty();
                }
            }
        };
        if (isOnMainThread()) {
            runnable.run();
        } else {
            mainHandler.post(runnable);
        }
    }

    public void showFail() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                View layout = helper.inflate(R.layout.base_list_error);
                layout.findViewById(R.id.refresh).setOnClickListener(onClickRefreshListener);
                helper.showLayout(layout);
                if (listener != null) {
                    listener.onShowFail();
                }
            }
        };
        if (isOnMainThread()) {
            runnable.run();
        } else {
            mainHandler.post(runnable);
        }
    }

    public VaryViewHelper getHelper() {
        return helper;
    }

    public interface OnStateUpdateListener {
        void onShowLoading();

        void onShowFail();

        void onShowEmpty();

        void onRestore();
    }

    public static class OnStateUpdateAdapter implements OnStateUpdateListener {

        @Override
        public void onShowLoading() {

        }

        @Override
        public void onShowFail() {

        }

        @Override
        public void onShowEmpty() {

        }

        @Override
        public void onRestore() {

        }
    }

    public void setOnStateUpdateListener(OnStateUpdateListener listener) {
        this.listener = listener;
    }

    private static boolean isOnMainThread() {
        return Looper.myLooper() == Looper.getMainLooper();
    }
}