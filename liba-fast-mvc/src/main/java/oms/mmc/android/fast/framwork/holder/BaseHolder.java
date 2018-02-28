package oms.mmc.android.fast.framwork.holder;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewTreeObserver;

import java.io.Serializable;

import oms.mmc.android.fast.framwork.BaseFastApplication;
import oms.mmc.android.fast.framwork.base.BaseFastActivity;
import oms.mmc.android.fast.framwork.base.LayoutCallback;
import oms.mmc.android.fast.framwork.util.ViewFinder;
import oms.mmc.lifecycle.dispatch.lifecycle.ActivityLifecycle;
import oms.mmc.lifecycle.dispatch.listener.ActivityLifecycleListener;

/**
 * View容器
 */
public abstract class BaseHolder implements LayoutCallback, Serializable, View.OnAttachStateChangeListener {
    protected BaseFastActivity _activity;
    protected Intent _intent;
    protected Bundle _Bundle;
    protected BaseFastApplication ac;
    protected View root;
    private ViewFinder viewFinder;

    public BaseHolder(Context context) {
        this._activity = (BaseFastActivity) context;
        this._intent = _activity.getIntent();
        if (this._intent != null) {
            this._Bundle = _intent.getExtras();
        }
        ac = (BaseFastApplication) context.getApplicationContext();
        initView();
    }

    public BaseHolder(Context context, ViewGroup parent) {
        this(context);
        parent.addView(getRoot(), onGetAddViewIndex(parent));
    }

    @Override
    public void onViewAttachedToWindow(View v) {

    }

    @Override
    public void onViewDetachedFromWindow(View v) {

    }

    private void initView() {
        onLayoutBefore();
        root = onLayoutView(_activity.getLayoutInflater(), null);
        root.addOnAttachStateChangeListener(this);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN_MR2) {
            addOnWindowFocusChangeListener();
        }
        viewFinder = new ViewFinder(root);
        root.setLayoutParams(onGetLayoutParams());
        onLayoutAfter();
        registerActivityLife();
    }

    public <E extends View> E findView(int id) {
        return (E) root.findViewById(id);
    }

    protected void onActivitySaveInstanceState(Bundle state) {

    }

    protected void onActivityRestoreInstanceState(Bundle state) {

    }

    /**
     * 注册生命周期回调
     */
    private void registerActivityLife() {
        final ActivityLifecycle lifecycle = _activity.getLifecycle();
        lifecycle.addListener(new ActivityLifecycleListener() {
            @Override
            public void onCreate() {
                BaseHolder.this.onActivityCreate();
            }

            @Override
            public void onStart() {
                BaseHolder.this.onActivityStart();
            }

            @Override
            public void onResume() {
                BaseHolder.this.onActivityResume();
            }

            @Override
            public void onPause() {
                BaseHolder.this.onActivityPause();
            }

            @Override
            public void onStop() {
                BaseHolder.this.onActivityStop();
            }

            @Override
            public void onDestroy() {
                BaseHolder.this.onActivityDestroy();
                lifecycle.removeListener(this);
            }
        });
    }

    protected void onActivityCreate() {
    }

    protected void onActivityStart() {
    }

    protected void onActivityResume() {
    }

    protected void onActivityPause() {
    }

    protected void onActivityStop() {
    }

    protected void onActivityDestroy() {
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    private void addOnWindowFocusChangeListener() {
        root.getViewTreeObserver().addOnWindowFocusChangeListener(new ViewTreeObserver.OnWindowFocusChangeListener() {
            @Override
            public void onWindowFocusChanged(boolean hasFocus) {
                BaseHolder.this.onWindowFocusChanged(hasFocus);
            }
        });
    }

    protected int onGetAddViewIndex(ViewGroup parent) {
        return parent.getChildCount();
    }

    public View getRoot() {
        return root;
    }

    public final void removeSelf() {
        if (root != null) {
            ViewParent parent = root.getParent();
            if (parent != null && parent instanceof ViewGroup) {
                ViewGroup viewGroup = (ViewGroup) parent;
                viewGroup.removeView(root);
            }
        }
    }

    @Override
    public abstract View onLayoutView(LayoutInflater inflater, ViewGroup container);

    @Override
    public void onLayoutBefore() {

    }

    @Override
    public void onLayoutAfter() {

    }

    protected ViewGroup.LayoutParams onGetLayoutParams() {
        return new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    }

    protected void onApiError(String tag) {
    }

    public void onWindowFocusChanged(boolean hasFocus) {

    }
}
