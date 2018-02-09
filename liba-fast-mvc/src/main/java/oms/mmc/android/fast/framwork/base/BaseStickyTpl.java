package oms.mmc.android.fast.framwork.base;

/**
 * Package: oms.mmc.android.fast.framwork.base
 * FileName: BaseStickyTpl
 * Date: on 2018/2/9  上午11:17
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */

public abstract class BaseStickyTpl<T> extends BaseTpl<T> {
    public abstract void onAttachSticky();

    public abstract void onDetachedSticky();
}
