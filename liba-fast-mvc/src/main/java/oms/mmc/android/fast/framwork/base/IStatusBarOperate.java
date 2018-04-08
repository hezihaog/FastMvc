package oms.mmc.android.fast.framwork.base;

/**
 * Package: oms.mmc.android.fast.framwork.base
 * FileName: IStatusBarOperate
 * Date: on 2018/3/20  下午10:15
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */

public interface IStatusBarOperate {
    /**
     * 设置透明状态栏
     */
    void setTranslucentStatusBar();

    /**
     * 设置状态栏黑色字体
     */
    void setBlackStatusBar();

    /**
     * 隐藏状态栏
     */
    void hideStatusBar();

    /**
     * 显示状态栏
     */
    void showStatusBar();
}