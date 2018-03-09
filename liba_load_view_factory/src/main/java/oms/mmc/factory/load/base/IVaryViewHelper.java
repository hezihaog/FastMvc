package oms.mmc.factory.load.base;

import android.content.Context;
import android.view.View;

/**
 * Package: oms.mmc.factory.load
 * FileName: IVaryViewHelper
 * Date: on 2018/2/23  下午8:57
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */

public interface IVaryViewHelper {
    /**
     * 获取当前布局
     */
    View getCurrentLayout();

    /**
     * 恢复原来的View
     */
    void restoreView();

    /**
     * 用View的形式,开始展示覆盖布局（例如loading布局，错误布局等）
     */
    void showLayout(View view);

    /**
     * 用布局id的形式,开始展示布局（例如loading布局，错误布局等）
     */
    void showLayout(int layoutId);

    /**
     * 填充布局
     */
    View inflate(int layoutId);

    /**
     * 获取上下文
     */
    Context getContext();

    /**
     * 获取要替换的View
     */
    View getView();
}