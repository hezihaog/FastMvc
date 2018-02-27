package oms.mmc.android.fast.framwork.widget.rv.base;

/**
 * Package: oms.mmc.android.fast.framwork.base
 * FileName: BaseStickyTpl
 * Date: on 2018/2/9  上午11:17
 * Auther: zihe
 * Descirbe:粘性Tpl
 * Email: hezihao@linghit.com
 */

public abstract class BaseStickyTpl<T> extends BaseTpl<T> {
    /**
     * 当条目被吸顶时回调
     */
    public abstract void onAttachSticky();

    /**
     * 当条目从吸顶改变为取消吸顶时回调
     */
    public abstract void onDetachedSticky();
}