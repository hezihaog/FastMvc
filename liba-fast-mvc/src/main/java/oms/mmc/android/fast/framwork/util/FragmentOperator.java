package oms.mmc.android.fast.framwork.util;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import oms.mmc.android.fast.framwork.base.IFragmentAction;
import oms.mmc.android.fast.framwork.base.IFragmentOperator;

/**
 * Package: oms.mmc.android.fast.framwork.util
 * FileName: FragmentOperator
 * Date: on 2018/3/13  上午11:46
 * Auther: zihe
 * Descirbe:Fragment操作执行器
 * Email: hezihao@linghit.com
 */

public class FragmentOperator implements IFragmentOperator {
    private Context mContext;
    private FragmentManager mSupportFragmentManager;

    public FragmentOperator(Context context) {
        this.mContext = context;
    }

    public Context getContext() {
        return mContext;
    }

    @Override
    public void setSupportFragmentManager(FragmentManager supportFragmentManager) {
        mSupportFragmentManager = supportFragmentManager;
    }

    @Override
    public FragmentManager getSupportFragmentManager() {
        return mSupportFragmentManager;
    }

    @Override
    public IFragmentOperator getFragmentOperator() {
        return this;
    }

    /**
     * 创建一个Bundle，通常用于Fragment的setArguments()传递参数
     */
    @Override
    public Bundle createArgs() {
        return new Bundle();
    }

    /**
     * 创建Fragment，不传入初始化参数
     */
    @Override
    public <T extends Fragment> T createFragment(Class<T> fragmentClass) {
        return createFragment(fragmentClass, null);
    }

    /**
     * 创建Fragment，可传入初始化参数
     */
    @Override
    public <T extends Fragment> T createFragment(Class<T> fragmentClass, Bundle args) {
        return FragmentFactory.newInstance(getContext(), fragmentClass, args);
    }

    /**
     * 查找是否已经有绑定的fragment
     */
    @Override
    public boolean isExistFragment() {
        return FragmentUtil.hasFragment(getSupportFragmentManager());
    }

    /**
     * 查找Fragment
     *
     * @param fragmentClass Fragment的Class，这里Fragment的Tag都是以Fragment的全类名作为Tag
     */
    @Override
    public Fragment findFragment(Class<? extends Fragment> fragmentClass) {
        return FragmentUtil.findFragment(getSupportFragmentManager(), fragmentClass);
    }

    /**
     * 添加Fragment
     */
    @Override
    public Fragment addFragment(Fragment fragment, int containerId) {
        return FragmentUtil.addFragment(getSupportFragmentManager(), fragment, containerId);
    }

    /**
     * 替换Fragment
     */
    @Override
    public Fragment replaceFragment(Fragment fragment, @IdRes int containerViewId) {
        return FragmentUtil.replaceFragment(getSupportFragmentManager(), fragment, containerViewId, false);
    }

    /**
     * 显示Fragment
     */
    @Override
    public void showFragment(Fragment fragment) {
        FragmentUtil.showFragment(fragment);
    }

    /**
     * 隐藏Fragment
     */
    @Override
    public void hideFragment(Fragment fragment) {
        FragmentUtil.hideFragment(fragment);
    }

    /**
     * 移除指定的Fragment
     */
    @Override
    public void removeFragment(Fragment fragment) {
        FragmentUtil.removeFragment(fragment);
    }

    /**
     * 移除所有同级别的Fragment
     */
    @Override
    public void removeFragments() {
        FragmentUtil.removeFragments(getSupportFragmentManager());
    }

    /**
     * 移除掉所有的Fragment
     */
    @Override
    public void removeAllFragments() {
        FragmentUtil.removeAllFragments(getSupportFragmentManager());
    }

    /**
     * 隐藏所有fragment
     */
    @Override
    public void hideAllFragments() {
        FragmentUtil.hideFragments(getSupportFragmentManager());
    }

    /**
     * 先隐藏指定的Fragment，在显示指定的fragment
     *
     * @param hideFragment 要先隐藏的Fragment
     * @param showFragment 要后显示的Fragment
     */
    @Override
    public void hideShowFragment(@NonNull Fragment hideFragment, @NonNull Fragment showFragment) {
        FragmentUtil.hideShowFragment(hideFragment, showFragment);
    }
}