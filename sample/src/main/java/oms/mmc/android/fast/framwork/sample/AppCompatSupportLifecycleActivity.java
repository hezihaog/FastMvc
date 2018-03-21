package oms.mmc.android.fast.framwork.sample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import oms.mmc.lifecycle.dispatch.lifecycle.ActivityLifecycle;

/**
 * Package: oms.mmc.android.fast.framwork.sample
 * FileName: AppCompatSupportLifecycleActivity
 * Date: on 2018/3/21  上午11:39
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */

public class AppCompatSupportLifecycleActivity extends AppCompatActivity {
    private ActivityLifecycle lifecycle;

    public AppCompatSupportLifecycleActivity() {
        lifecycle = new ActivityLifecycle();
    }

    public ActivityLifecycle getLifecycle() {
        if (lifecycle == null) {
            lifecycle = new ActivityLifecycle();
        }
        return lifecycle;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (lifecycle != null) {
            lifecycle.onCreate();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (lifecycle != null) {
            lifecycle.onStart();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (lifecycle != null) {
            lifecycle.onResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (lifecycle != null) {
            lifecycle.onPause();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (lifecycle != null) {
            lifecycle.onStop();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (lifecycle != null) {
            lifecycle.onDestroy();
        }
    }
}
