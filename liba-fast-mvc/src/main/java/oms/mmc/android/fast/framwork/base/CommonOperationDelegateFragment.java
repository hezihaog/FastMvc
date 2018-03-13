package oms.mmc.android.fast.framwork.base;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.Serializable;

import mmc.image.ImageLoader;
import mmc.image.LoadImageCallback;
import oms.mmc.android.fast.framwork.lazy.ExtendLazyFragment;
import oms.mmc.android.fast.framwork.util.ArgumentsDelegateHelper;
import oms.mmc.android.fast.framwork.util.FragmentOperator;
import oms.mmc.android.fast.framwork.util.IArgumentsDelegate;
import oms.mmc.android.fast.framwork.util.IToastOperator;
import oms.mmc.android.fast.framwork.util.IViewFinderAction;
import oms.mmc.android.fast.framwork.util.ToastOperator;

/**
 * Package: oms.mmc.android.fast.framwork.base
 * FileName: ArgumentsDelegateFragment
 * Date: on 2018/3/5  上午11:40
 * Auther: zihe
 * Descirbe:常用的参数代理和控件设置
 * Email: hezihao@linghit.com
 */

public abstract class CommonOperationDelegateFragment extends ExtendLazyFragment implements IArgumentsDelegate
        , IWaitViewHandler, IViewFinderAction, IFragmentAction, IToastOperator {
    private IArgumentsDelegate mArgumentsDelegateHelper;
    private IToastOperator mToastOperator;
    private IFragmentOperator mFragmentOperator;

    /**
     * 确保参数代理初始化
     */
    public void ensureInitArgumentsDelegate() {
        if (mArgumentsDelegateHelper == null) {
            mArgumentsDelegateHelper = ArgumentsDelegateHelper.newInstance(getArguments());
        }
    }

    /**
     * 确保Toast执行器初始化
     */
    public void ensureInitToastOperator() {
        if (mToastOperator == null) {
            mToastOperator = new ToastOperator(getContext());
        }
    }

    /**
     * 确保Fragment操作执行器初始化
     */
    public void ensureInitFragmentOperator() {
        if (mFragmentOperator == null) {
            mFragmentOperator = new FragmentOperator(getContext());
            mFragmentOperator.setSupportFragmentManager(getChildFragmentManager());
        }
    }

    /**
     * 以资源id显示短Toast信息
     */
    @Override
    public void showToast(int message) {
        ensureInitToastOperator();
        mToastOperator.showToast(message);
    }

    /**
     * 以直接字符串显示短Toast信息
     */
    @Override
    public void showToast(String message) {
        ensureInitToastOperator();
        mToastOperator.showToast(message);
    }

    /**
     * 以资源id显示长Toast信息
     */
    @Override
    public void showLongToast(int message) {
        ensureInitToastOperator();
        mToastOperator.showLongToast(message);
    }

    /**
     * 以直接字符串显示长Toast信息
     */
    @Override
    public void showLongToast(String message) {
        ensureInitToastOperator();
        mToastOperator.showLongToast(message);
    }

    /**
     * 显示toast信息
     */
    @Override
    public void toast(final String message, final int duration) {
        ensureInitToastOperator();
        mToastOperator.toast(message, duration);
    }

    @Override
    public void setExtras(Bundle extras) {
        ensureInitArgumentsDelegate();
        mArgumentsDelegateHelper.setExtras(extras);
    }

    @Override
    public Bundle getExtras() {
        ensureInitArgumentsDelegate();
        return mArgumentsDelegateHelper.getExtras();
    }

    @Override
    public String intentStr(String key) {
        ensureInitArgumentsDelegate();
        return mArgumentsDelegateHelper.intentStr(key);
    }

    @Override
    public String intentStr(String key, String defaultValue) {
        ensureInitArgumentsDelegate();
        return mArgumentsDelegateHelper.intentStr(key, defaultValue);
    }

    @Override
    public int intentInt(String key) {
        ensureInitArgumentsDelegate();
        return mArgumentsDelegateHelper.intentInt(key);
    }

    @Override
    public int intentInt(String key, int defaultValue) {
        ensureInitArgumentsDelegate();
        return mArgumentsDelegateHelper.intentInt(key, defaultValue);
    }

    @Override
    public boolean intentBoolean(String key) {
        ensureInitArgumentsDelegate();
        return mArgumentsDelegateHelper.intentBoolean(key);
    }

    @Override
    public boolean intentBoolean(String key, boolean defaultValue) {
        ensureInitArgumentsDelegate();
        return mArgumentsDelegateHelper.intentBoolean(key, defaultValue);
    }

    @Override
    public Serializable intentSerializable(String key) {
        ensureInitArgumentsDelegate();
        return mArgumentsDelegateHelper.intentSerializable(key);
    }

    @Override
    public Serializable intentSerializable(String key, Serializable defaultValue) {
        ensureInitArgumentsDelegate();
        return mArgumentsDelegateHelper.intentSerializable(key, defaultValue);
    }

    @Override
    public float intentFloat(String key) {
        ensureInitArgumentsDelegate();
        return mArgumentsDelegateHelper.intentFloat(key);
    }

    @Override
    public float intentFloat(String key, float defaultValue) {
        ensureInitArgumentsDelegate();
        return mArgumentsDelegateHelper.intentFloat(key, defaultValue);
    }

    @Override
    public <T extends Parcelable> T intentParcelable(String key) {
        ensureInitArgumentsDelegate();
        return mArgumentsDelegateHelper.intentParcelable(key);
    }

    @Override
    public <T extends Parcelable> T intentParcelable(String key, Parcelable defaultValue) {
        ensureInitArgumentsDelegate();
        return mArgumentsDelegateHelper.intentParcelable(key, defaultValue);
    }

    //------------------------ 控件操作 ------------------------

    /**
     * 获取布局View
     *
     * @return 布局View
     */
    @Override
    public View getRootView() {
        return getViewFinder().getRootView();
    }

    @Override
    public boolean isEmpty(CharSequence str) {
        return getViewFinder().isEmpty(str);
    }

    @Override
    public boolean isNotEmpty(CharSequence str) {
        return getViewFinder().isNotEmpty(str);
    }

    /**
     * 判断View显示的文字是否为空
     */
    @Override
    public boolean viewTextIsEmpty(int viewId, boolean isFilterSpace) {
        return getViewFinder().viewTextIsEmpty(viewId, isFilterSpace);
    }

    /**
     * 判断View显示的文字是否为空
     */
    @Override
    public boolean viewTextIsEmpty(TextView view, boolean isFilterSpace) {
        return getViewFinder().viewTextIsEmpty(view, isFilterSpace);
    }

    /**
     * 判断View显示的文字是否不为空
     */
    @Override
    public boolean viewTextIsNotEmpty(int viewId) {
        return getViewFinder().viewTextIsNotEmpty(viewId);
    }

    /**
     * 判断View显示的文字是否不为空
     */
    @Override
    public boolean viewTextIsNotEmpty(TextView view) {
        return getViewFinder().viewTextIsNotEmpty(view);
    }

    @Override
    public boolean viewTextIsEmptyWithTrim(int viewId) {
        return getViewFinder().viewTextIsEmpty(viewId, true);
    }

    @Override
    public boolean viewTextIsEmptyWithTrim(TextView view) {
        return getViewFinder().viewTextIsEmpty(view, true);
    }

    //-------------------------------- 设置文字 --------------------------------

    /**
     * 直接控件id设置文字
     *
     * @param text   文字
     * @param viewId 控件id，必须是TextView以及子类
     */
    @Override
    public void setViewText(CharSequence text, @IdRes int viewId) {
        getViewFinder().setViewText(text, viewId);
    }

    /**
     * 直接控件id设置文字
     *
     * @param text        文字
     * @param defaultText 如果传入文字的值为null，设置的默认文字
     * @param viewId      控件id，必须是TextView以及子类
     */
    @Override
    public void setTextWithDefault(CharSequence text, CharSequence defaultText, @IdRes int viewId) {
        getViewFinder().setTextWithDefault(text, defaultText, viewId);
    }

    /**
     * 为TextView设置文字，如果传入的Text为null，则设置为空字符串
     */
    @Override
    public void setViewText(CharSequence text, TextView view) {
        getViewFinder().setViewText(text, view);
    }

    /**
     * 为Text设置文字，如果传入的Text为null，则设置为默认值
     */
    @Override
    public void setTextWithDefault(CharSequence text, CharSequence defaultText, TextView view) {
        getViewFinder().setTextWithDefault(text, defaultText, view);
    }

    //-------------------------------- 获取TextView及其子类的文字 --------------------------------

    /**
     * 使用TextView，获取View上的文字，当获取为null时，返回空字符串 ""
     *
     * @param view TextView对象
     * @return View上的文字
     */
    @Override
    public CharSequence getViewText(TextView view) {
        return getViewFinder().getViewText(view);
    }

    /**
     * 使用View id，获取View上的文字，当获取为null时，返回空字符串 ""
     *
     * @param viewId TextView的id
     * @return View上的文字
     */
    @Override
    public CharSequence getViewText(@IdRes int viewId) {
        return getViewFinder().getViewText(viewId);
    }

    /**
     * 使用View Id，获取文字，当获取为null是，可指定返回默认文字
     *
     * @param viewId      View id
     * @param defaultText 默认文字
     */
    @Override
    public CharSequence getTextWithDefault(@IdRes int viewId, CharSequence defaultText) {
        return getViewFinder().getTextWithDefault(viewId, defaultText);
    }

    /**
     * 使用View，获取文字，当获取为null是，可指定返回默认文字
     *
     * @param textView    View id
     * @param defaultText 默认文字
     */
    @Override
    public CharSequence getTextWithDefault(TextView textView, CharSequence defaultText) {
        return getViewFinder().getTextWithDefault(textView, defaultText);
    }

    @Override
    public CharSequence getViewTextWithTrim(@IdRes int viewId) {
        return getViewFinder().getViewText(viewId).toString().trim();
    }

    @Override
    public CharSequence getViewTextWithTrim(TextView view) {
        return getViewFinder().getViewText(view).toString().trim();
    }

    //-------------------------------- 单次设置监听器 --------------------------------

    /**
     * 查找并且设置点击监听器
     *
     * @param listener 监听器
     * @param id       view 的 id
     */
    @Override
    public View findAndSetOnClick(@IdRes int id, View.OnClickListener listener) {
        return getViewFinder().findAndSetOnClick(id, listener);
    }

    /**
     * 查找并设置长按监听器
     *
     * @param id       View的id
     * @param listener 监听器
     */
    @Override
    public View findAndSetOnLongClick(@IdRes int id, View.OnLongClickListener listener) {
        return findAndSetOnLongClick(id, listener);
    }

    //-------------------------------- 批量设置监听器 --------------------------------

    /**
     * 批量id，设置同一个点击监听器
     *
     * @param listener 点击监听器
     * @param ids      view 的 id
     */
    @Override
    public void setOnClickListener(View.OnClickListener listener, @IdRes int... ids) {
        getViewFinder().setOnClickListener(listener, ids);
    }

    /**
     * 批量View，设置同一个点击监听器
     *
     * @param listener 点击监听器
     * @param views    多个View对象
     */
    @Override
    public void setOnClickListener(View.OnClickListener listener, View... views) {
        getViewFinder().setOnClickListener(listener, views);
    }

    /**
     * 批量View id，设置同一个长按监听器
     *
     * @param listener 长按监听器
     * @param ids      View 的id
     */
    @Override
    public void setOnLongClickListener(View.OnLongClickListener listener, int... ids) {
        getViewFinder().setOnLongClickListener(listener, ids);
    }

    /**
     * 批量View，设置同一个长按监听器
     *
     * @param listener 长按监听器
     * @param views    多个View对象
     */
    @Override
    public void setOnLongClickListener(View.OnLongClickListener listener, View... views) {
        getViewFinder().setOnLongClickListener(listener, views);
    }

    //-------------------------------- 设置View的显示隐藏 --------------------------------

    /**
     * 以多个id的方式，批量设置View为显示
     *
     * @param ids 多个View的id
     */
    @Override
    public void setVisible(@IdRes int... ids) {
        getViewFinder().setVisible(ids);
    }

    @Override
    public void setInVisible(int... ids) {
        getViewFinder().setInVisible(ids);
    }

    /**
     * 以多个id的方式，批量设置View为隐藏
     *
     * @param ids 多个View的id
     */
    @Override
    public void setGone(@IdRes int... ids) {
        getViewFinder().setGone(ids);
    }

    /**
     * 设置多个View显示
     */
    @Override
    public void setVisible(View... views) {
        getViewFinder().setVisible(views);
    }

    @Override
    public void setInVisible(View... views) {
        getViewFinder().setInVisible(views);
    }

    /**
     * 设置多个View隐藏
     */
    @Override
    public void setGone(View... views) {
        getViewFinder().setGone(views);
    }

    //-------------------------------- 图片加载 --------------------------------

    @Override
    public ImageLoader getImageLoader() {
        return getViewFinder().getImageLoader();
    }


    /**
     * 加载网络图片
     */
    @Override
    public void loadUrlImage(String url, ImageView imageView, int defaultImage) {
        getViewFinder().loadUrlImage(url, imageView, defaultImage);
    }

    /**
     * 加载网络圆形图片
     */
    @Override
    public void loadUrlImageToRound(String url, ImageView imageView, int defaultImage) {
        getViewFinder().loadUrlImageToRound(url, imageView, defaultImage);
    }

    /**
     * 加载网络圆角图片
     */
    @Override
    public void loadUrlImageToCorner(String url, ImageView imageView, int defaultImage) {
        getViewFinder().loadUrlImageToCorner(url, imageView, defaultImage);
    }

    /**
     * 加载内存卡图片
     */
    @Override
    public void loadFileImage(String filePath, ImageView imageView, int defaultImage) {
        getViewFinder().loadFileImage(filePath, imageView, defaultImage);
    }

    /**
     * 加载图片bitmap
     */
    @Override
    public void loadImageToBitmap(String url, LoadImageCallback loadImageCallback) {
        getViewFinder().loadImageToBitmap(url, loadImageCallback);
    }

    @Override
    public void loadDrawableResId(@IdRes int imageViewId, @DrawableRes int resId) {
        getViewFinder().loadDrawableResId(imageViewId, resId);
    }

    @Override
    public void loadDrawableResId(ImageView imageView, @DrawableRes int resId) {
        getViewFinder().loadDrawableResId(imageView, resId);
    }

    /**
     * 清除图片缓存
     */
    @Override
    public void clearImageMemoryCache() {
        getViewFinder().clearImageMemoryCache();
    }

    //-------------------- Fragment操作 ----------------------

    /**
     * 创建一个Bundle，通常用于Fragment的setArguments()传递参数
     */
    @Override
    public Bundle createArgs() {
        ensureInitFragmentOperator();
        return mFragmentOperator.createArgs();
    }

    /**
     * 创建Fragment，不传入初始化参数
     */
    @Override
    public <T extends Fragment> T createFragment(Class<T> fragmentClass) {
        ensureInitFragmentOperator();
        return mFragmentOperator.createFragment(fragmentClass, null);
    }

    /**
     * 创建Fragment，可传入初始化参数
     */
    @Override
    public <T extends Fragment> T createFragment(Class<T> fragmentClass, Bundle args) {
        ensureInitFragmentOperator();
        return mFragmentOperator.createFragment(fragmentClass, args);
    }

    /**
     * 查找是否已经有绑定的fragment
     */
    @Override
    public boolean hasBindFragment() {
        ensureInitFragmentOperator();
        return mFragmentOperator.hasBindFragment();
    }

    /**
     * 查找Fragment
     *
     * @param fragmentClass Fragment的Class，这里Fragment的Tag都是以Fragment的全类名作为Tag
     */
    @Override
    public Fragment findFragment(Class<? extends Fragment> fragmentClass) {
        ensureInitFragmentOperator();
        return mFragmentOperator.findFragment(fragmentClass);
    }

    /**
     * 添加Fragment
     */
    @Override
    public Fragment addFragment(Fragment fragment, int containerId) {
        ensureInitFragmentOperator();
        return mFragmentOperator.addFragment(fragment, containerId);
    }

    /**
     * 替换Fragment
     */
    @Override
    public Fragment replaceFragment(Fragment fragment, @IdRes int containerViewId) {
        ensureInitFragmentOperator();
        return mFragmentOperator.replaceFragment(fragment, containerViewId);
    }

    /**
     * 显示Fragment
     */
    @Override
    public void showFragment(Fragment fragment) {
        ensureInitFragmentOperator();
        mFragmentOperator.showFragment(fragment);
    }

    /**
     * 隐藏Fragment
     */
    @Override
    public void hideFragment(Fragment fragment) {
        ensureInitFragmentOperator();
        mFragmentOperator.hideFragment(fragment);
    }

    /**
     * 移除指定的Fragment
     */
    @Override
    public void removeFragment(Fragment fragment) {
        ensureInitFragmentOperator();
        mFragmentOperator.removeFragment(fragment);
    }

    /**
     * 移除所有同级别的Fragment
     */
    @Override
    public void removeFragments() {
        ensureInitFragmentOperator();
        mFragmentOperator.removeFragments();
    }

    /**
     * 移除掉所有的Fragment
     */
    @Override
    public void removeAllFragments() {
        ensureInitFragmentOperator();
        mFragmentOperator.removeAllFragments();
    }

    /**
     * 隐藏所有fragment
     */
    @Override
    public void hideAllFragments() {
        ensureInitFragmentOperator();
        mFragmentOperator.hideAllFragments();
    }

    /**
     * 先隐藏指定的Fragment，在显示指定的fragment
     *
     * @param hideFragment 要先隐藏的Fragment
     * @param showFragment 要后显示的Fragment
     */
    @Override
    public void hideShowFragment(@NonNull Fragment hideFragment, @NonNull Fragment showFragment) {
        ensureInitFragmentOperator();
        mFragmentOperator.hideShowFragment(hideFragment, showFragment);
    }
}