package oms.mmc.android.fast.framwork.basiclib.util;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import java.lang.reflect.Constructor;

/**
 * Package: oms.mmc.android.fast.framwork.basiclib.util
 * FileName: WaitDialogHelper
 * Date: on 2018/2/5  下午12:20
 * Auther: zihe
 * Descirbe:等待对话框帮助类
 * Email: hezihao@linghit.com
 */

public class WaitDialogHelper {
    private Application application;
    private IWait waitIml;

    private WaitDialogHelper() {
    }

    public static class SingleHolder {
        private static final WaitDialogHelper instance = new WaitDialogHelper();
    }

    public static void init(Application application) {
        init(application, ProgressDialogWaitIml.class);
    }

    /**
     * 初始化
     *
     * @param waitImlClazz 等待对话框执行实现类
     */
    public static void init(Application application, Class<? extends IWait> waitImlClazz) {
        if (application == null) {
            throw new IllegalArgumentException("application must not null");
        }
        if (waitImlClazz == null) {
            throw new IllegalArgumentException("waitIml must not null");
        }
        WaitDialogHelper helper = getInstance();
        helper.application = application;
        try {
            Constructor<? extends IWait> constructor = waitImlClazz.getDeclaredConstructor();
            constructor.setAccessible(true);
            helper.waitIml = waitImlClazz.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取等待对话框帮助类单例实例
     */
    public static WaitDialogHelper getInstance() {
        return SingleHolder.instance;
    }

    /**
     * 设置对话框执行实现类
     *
     * @param waitIml 对话框执行实现类实例
     */
    public void setWaitIml(IWait waitIml) {
        this.waitIml = waitIml;
    }

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }

    public IWait getWaitIml() {
        return waitIml;
    }

    /**
     * 创建执行类实例
     */
    public WaitAction newAction() {
        if (waitIml == null) {
            throw new RuntimeException("must call init()");
        }
        return new WaitAction(waitIml);
    }

    /**
     *
     */
    public static class WaitAction {
        private IWait waitIml;

        public WaitAction(IWait waitIml) {
            this.waitIml = waitIml;
        }

        public IWait getWaitIml() {
            return waitIml;
        }
    }

    /**
     * 对话框实现类要实现的接口
     */
    public interface IWait {
        /**
         * 显示等待对话框
         *
         * @param activity activity
         */
        void showWaitDialog(Activity activity);

        /**
         * 显示等待对话框
         *
         * @param activity          activity
         * @param msg               附带的信息
         * @param isTouchCancelable 是否可以点击窗口外取消，并且关闭界面
         */
        void showWaitDialog(Activity activity, CharSequence msg, boolean isTouchCancelable);

        /**
         * 隐藏等待对话框
         */
        void hideWaitDialog();

        /**
         * 等待对话框是否展示
         */
        boolean isShowing();

        /**
         * 传入的activity是否是等待对话框的宿主，这里用来判断当前结束的activity是否是该对话框的
         *
         * @param activity activity
         */
        boolean isHost(Activity activity);

        /**
         * 销毁Dialog，通常是activity要销毁了
         */
        void destroyDialog();
    }

    /**
     * 对话框实现类抽象类
     */
    public static abstract class AbsWait implements IWait {
        public static class SimpleActivityLifecycleCallback implements Application.ActivityLifecycleCallbacks {

            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
            }

            @Override
            public void onActivityStarted(Activity activity) {
            }

            @Override
            public void onActivityResumed(Activity activity) {
            }

            @Override
            public void onActivityPaused(Activity activity) {
            }

            @Override
            public void onActivityStopped(Activity activity) {
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
            }

            @Override
            public void onActivityDestroyed(Activity activity) {
            }
        }

        public AbsWait() {
            final WaitDialogHelper helper = getInstance();
            SimpleActivityLifecycleCallback lifecycleCallback = new SimpleActivityLifecycleCallback() {
                @Override
                public void onActivityDestroyed(Activity activity) {
                    super.onActivityDestroyed(activity);
                    if (isHost(activity)) {
                        destroyDialog();
                        helper.getApplication().unregisterActivityLifecycleCallbacks(this);
                    }
                }
            };
            helper.getApplication().registerActivityLifecycleCallbacks(lifecycleCallback);
        }
    }
}