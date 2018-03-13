package oms.mmc.android.fast.framwork.util;

/**
 * Package: oms.mmc.android.fast.framwork.util
 * FileName: IToast
 * Date: on 2018/3/7  下午4:23
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */

public interface IToastOperator {
    /**
     * 以资源id显示短Toast信息
     */
    void showToast(int message);

    /**
     * 以直接字符串显示短Toast信息
     */
    void showToast(String message);

    /**
     * 以资源id显示长Toast信息
     */
    void showLongToast(int message);

    /**
     * 以直接字符串显示长Toast信息
     */
    void showLongToast(String message);

    /**
     * 显示toast信息
     */
    void toast(String message, int duration);
}