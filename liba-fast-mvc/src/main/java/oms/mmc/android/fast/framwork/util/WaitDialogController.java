package oms.mmc.android.fast.framwork.util;

import android.app.Activity;
import android.app.Application;

import java.lang.reflect.Constructor;

import oms.mmc.android.fast.framwork.widget.wait.IWait;

/**
 * Package: oms.mmc.android.fast.framwork.basiclib.util
 * FileName: WaitDialogHelper
 * Date: on 2018/2/5  下午12:20
 * Auther: zihe
 * Descirbe:等待对话框控制器
 * Email: hezihao@linghit.com
 */

public class WaitDialogController {
    private Application application;
    private IWait waitIml;

    public WaitDialogController(Activity activity) {
        this(activity, ProgressWaitDialogIml.class);
    }

    public WaitDialogController(Activity activity, Class<? extends IWait> waitImlClazz) {
        this.application = activity.getApplication();
        this.waitIml = parseIml(waitImlClazz);
    }

    /**
     * 解析实现类
     *
     * @param waitImlClazz 等待对话框执行实现类
     */
    private IWait parseIml(Class<? extends IWait> waitImlClazz) {
        if (waitImlClazz == null) {
            throw new IllegalArgumentException("waitIml must not null");
        }
        IWait waitIml = null;
        try {
            Constructor<? extends IWait> constructor = waitImlClazz.getDeclaredConstructor();
            constructor.setAccessible(true);
            waitIml = waitImlClazz.newInstance();
            waitIml.init(application);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return waitIml;
    }

    public Application getApplication() {
        return application;
    }

    /**
     * 设置对话框执行实现类
     *
     * @param waitIml 对话框执行实现类实例
     */
    public void setWaitIml(IWait waitIml) {
        this.waitIml = waitIml;
    }

    public IWait getWaitIml() {
        return waitIml;
    }
}