package oms.mmc.android.fast.framwork.basiclib.lazy.inter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Package: com.hzh.lazy.fragment.inter
 * FileName: FragmentCallback
 * Date: on 2017/11/24  上午11:05
 * Auther: zihe
 * Descirbe: Fragment相关操作模板方法定义接口
 * Email: hezihao@linghit.com
 */

public interface FragmentOperationCallback {
    /**
     * 请务必在此方法中返回此Fragment提供的根视图，返回结果不可为空
     *
     * @param inflater           用于实例化layout文件的Inflater
     * @param container          父容器
     * @param savedInstanceState 有可能为空，使用之前请先进行判断
     * @return 不可为空
     */
    View onInflaterRootView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

    /**
     * 查找View的操作
     *
     * @param mRootView Fragment的View视图
     */
    void onFindViews(View mRootView);

    /**
     * 控件已经查找完毕，可以将数据与视图控件进行绑定
     */
    void onBindContent();
}
