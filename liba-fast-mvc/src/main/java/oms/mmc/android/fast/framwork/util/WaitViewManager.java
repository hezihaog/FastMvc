package oms.mmc.android.fast.framwork.util;

import android.app.Activity;

import java.util.HashMap;

import oms.mmc.factory.wait.adapter.SimpleWaitDialogAdapter;
import oms.mmc.factory.wait.inter.IWaitViewController;

/**
 * Package: oms.mmc.android.fast.framwork.util
 * FileName: WaitViewManager
 * Date: on 2018/3/1  下午7:16
 * Auther: zihe
 * Descirbe:WaitView管理器，用于Fragment和Activity的WaitView共享
 * Email: hezihao@linghit.com
 */

public class WaitViewManager {
    private HashMap<Integer, IWaitViewController> mWaitViewList;

    private WaitViewManager() {
        ensureList();
    }

    private void ensureList() {
        if (mWaitViewList == null) {
            mWaitViewList = new HashMap<Integer, IWaitViewController>();
        }
    }

    private static final class SingleHolder {
        private static final WaitViewManager instance = new WaitViewManager();
    }

    public static WaitViewManager getInstnace() {
        return SingleHolder.instance;
    }

    /**
     * 添加
     */
    public void add(final Activity activity, final IWaitViewController controller) {
        ensureList();
        //添加监听，双重保证，如果activity异常退出，remove方法未调用时，这个弹窗窗口监听也能回调去移除
        controller.getWaitIml().addListener(new SimpleWaitDialogAdapter() {
            @Override
            public void onDestroyDialog() {
                super.onDestroyDialog();
                mWaitViewList.remove(activity.hashCode());
                controller.getWaitIml().removeListener(this);
            }
        });
        mWaitViewList.put(activity.hashCode(), controller);
    }

    /**
     * 移除
     */
    public void remove(Activity activity) {
        ensureList();
        mWaitViewList.remove(activity.hashCode());
    }

    /**
     * 用Activity查找waitDialog
     */
    public IWaitViewController find(Activity activity) {
        ensureList();
        return mWaitViewList.get(activity.hashCode());
    }
}