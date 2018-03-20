package oms.mmc.android.fast.framwork.util;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.view.View;

/**
 * Package: oms.mmc.android.fast.framwork.util
 * FileName: IViewFinder
 * Date: on 2018/3/7  下午12:15
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */

public interface IViewFinder extends IViewFinderAction {
    /**
     * 保存状态
     */
    void saveInstance(Bundle bundle);

    /**
     * 恢复状态
     */
    void restoreInstance(Bundle bundle);

    /**
     * 获取自己
     */
    IViewFinder getFinder();

    /**
     * 回收内存，在生命周期销毁时调用
     */
    void recycle();

    /**
     * 通过View的id来获取子View
     *
     * @param resId view的id
     * @param <T>   泛型
     * @return View
     */
    <T extends View> T get(@IdRes int resId);
}