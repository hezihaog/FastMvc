package oms.mmc.android.fast.framwork.base;

import android.os.Bundle;

/**
 * Created by Hezihao on 2017/7/26.
 * Activity实例重建回调
 */

public interface InstanceStateCallback {
    /**
     * activity被回收时回调
     */
    void onSaveInstanceState(Bundle outState);

    /**
     * activity被重建时回调
     */
    void onRestoreInstanceState(Bundle savedInstanceState);
}