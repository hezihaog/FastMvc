package oms.mmc.android.fast.framwork.base;

/**
 * Package: oms.mmc.android.fast.framwork.base
 * FileName: IWaitViewOperation
 * Date: on 2018/3/4  上午11:25
 * Auther: zihe
 * Descirbe:WaitView操作者
 * Email: hezihao@linghit.com
 */

public interface IWaitViewHandler {
    /**
     * 显示WaitView
     */
    void showWaitDialog();

    /**
     * 显示WaitView，并设置文字
     */
    void showWaitDialog(String msg);

    /**
     * 显示WaitView，设置文字，是否可以点击关闭
     *
     * @param msg               文字
     * @param isTouchCancelable 是否可以点击关闭，当为false时，按返回键关闭Activity
     */
    void showWaitDialog(String msg, final boolean isTouchCancelable);

    /**
     * 隐藏WaitView
     */
    void hideWaitDialog();
}