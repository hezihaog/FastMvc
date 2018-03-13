package oms.mmc.android.fast.framwork.base;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

/**
 * Package: oms.mmc.android.fast.framwork.base
 * FileName: IFragmentAction
 * Date: on 2018/3/13  上午11:11
 * Auther: zihe
 * Descirbe:Fragment的操作
 * Email: hezihao@linghit.com
 */

public interface IFragmentAction {
    /**
     * 创建一个Bundle，通常用于Fragment的setArguments()传递参数
     */
    Bundle createArgs();

    /**
     * 创建Fragment，不传入初始化参数
     */
    <T extends Fragment> T createFragment(Class<T> fragmentClass);

    /**
     * 创建Fragment，可传入初始化参数
     */
    <T extends Fragment> T createFragment(Class<T> fragmentClass, Bundle args);

    /**
     * 查找是否已经存在fragment
     */
    boolean isExistFragment();

    /**
     * 查找Fragment
     *
     * @param fragmentClass Fragment的Class，这里Fragment的Tag都是以Fragment的全类名作为Tag
     */
    Fragment findFragment(Class<? extends Fragment> fragmentClass);

    /**
     * 添加Fragment
     */
    Fragment addFragment(Fragment fragment, int containerId);

    /**
     * 替换Fragment
     */
    Fragment replaceFragment(Fragment fragment, @IdRes int containerViewId);

    /**
     * 显示Fragment
     */
    void showFragment(Fragment fragment);

    /**
     * 隐藏Fragment
     */
    void hideFragment(Fragment fragment);

    /**
     * 移除指定的Fragment
     */
    void removeFragment(Fragment fragment);

    /**
     * 移除所有同级别的Fragment
     */
    void removeFragments();

    /**
     * 移除掉所有的Fragment
     */
    void removeAllFragments();

    /**
     * 隐藏所有fragment
     */
    void hideAllFragments();

    /**
     * 先隐藏指定的Fragment，在显示指定的fragment
     *
     * @param hideFragment 要先隐藏的Fragment
     * @param showFragment 要后显示的Fragment
     */
    void hideShowFragment(@NonNull Fragment hideFragment, @NonNull Fragment showFragment);
}