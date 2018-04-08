package oms.mmc.android.fast.framwork.widget.block;

/**
 * Package: oms.mmc.android.fast.framwork.widget.block
 * FileName: ActivityLifecycleObserver
 * Date: on 2018/4/8  下午9:52
 * Auther: zihe
 * Descirbe:Activity生命周期监听回调
 * Email: hezihao@linghit.com
 */
public interface ActivityLifecycleObserver {
    /**
     * Activity onStart时回调
     */
    void onActivityStart();

    /**
     * Activity onResume时回调
     */
    void onActivityResume();

    /**
     * Activity onPause时回调
     */
    void onActivityPause();

    /**
     * Activity onStop时回调
     */
    void onActivityStop();

    /**
     * Activity onDestroy回调
     */
    void onActivityDestroy();
}