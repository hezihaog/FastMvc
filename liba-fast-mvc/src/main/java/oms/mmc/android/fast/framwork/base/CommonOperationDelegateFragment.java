package oms.mmc.android.fast.framwork.base;

import android.app.Activity;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.Serializable;

import mmc.image.ImageLoader;
import mmc.image.LoadImageCallback;
import oms.mmc.android.fast.framwork.lazy.ExtendLazyFragment;
import oms.mmc.android.fast.framwork.util.ArgumentsDelegateHelper;
import oms.mmc.android.fast.framwork.util.IArgumentsDelegate;
import oms.mmc.android.fast.framwork.util.IViewFinder;

/**
 * Package: oms.mmc.android.fast.framwork.base
 * FileName: ArgumentsDelegateFragment
 * Date: on 2018/3/5  上午11:40
 * Auther: zihe
 * Descirbe:参数代理
 * Email: hezihao@linghit.com
 */

public abstract class CommonOperationDelegateFragment extends ExtendLazyFragment implements IArgumentsDelegate, IWaitViewHandler, IViewFinder {
    private ArgumentsDelegateHelper mArgumentsDelegateHelper;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mArgumentsDelegateHelper = ensureInit();
    }

    /**
     * 确保初始化
     */
    public ArgumentsDelegateHelper ensureInit() {
        if (mArgumentsDelegateHelper == null) {
            mArgumentsDelegateHelper = ArgumentsDelegateHelper.newInstance(getArguments());
        }
        return mArgumentsDelegateHelper;
    }

    @Override
    public void setExtras(Bundle extras) {
        ensureInit();
        mArgumentsDelegateHelper.setExtras(extras);
    }

    @Override
    public Bundle getExtras() {
        ensureInit();
        return mArgumentsDelegateHelper.getExtras();
    }

    @Override
    public String intentStr(String key) {
        ensureInit();
        return mArgumentsDelegateHelper.intentStr(key);
    }

    @Override
    public String intentStr(String key, String defaultValue) {
        ensureInit();
        return mArgumentsDelegateHelper.intentStr(key, defaultValue);
    }

    @Override
    public int intentInt(String key) {
        ensureInit();
        return mArgumentsDelegateHelper.intentInt(key);
    }

    @Override
    public int intentInt(String key, int defaultValue) {
        ensureInit();
        return mArgumentsDelegateHelper.intentInt(key, defaultValue);
    }

    @Override
    public boolean intentBoolean(String key) {
        ensureInit();
        return mArgumentsDelegateHelper.intentBoolean(key);
    }

    @Override
    public boolean intentBoolean(String key, boolean defaultValue) {
        ensureInit();
        return mArgumentsDelegateHelper.intentBoolean(key, defaultValue);
    }

    @Override
    public Serializable intentSerializable(String key) {
        ensureInit();
        return mArgumentsDelegateHelper.intentSerializable(key);
    }

    @Override
    public Serializable intentSerializable(String key, Serializable defaultValue) {
        ensureInit();
        return mArgumentsDelegateHelper.intentSerializable(key, defaultValue);
    }

    @Override
    public float intentFloat(String key) {
        ensureInit();
        return mArgumentsDelegateHelper.intentFloat(key);
    }

    @Override
    public float intentFloat(String key, float defaultValue) {
        ensureInit();
        return mArgumentsDelegateHelper.intentFloat(key, defaultValue);
    }

    @Override
    public <T extends Parcelable> T intentParcelable(String key) {
        ensureInit();
        return mArgumentsDelegateHelper.intentParcelable(key);
    }

    @Override
    public <T extends Parcelable> T intentParcelable(String key, Parcelable defaultValue) {
        ensureInit();
        return mArgumentsDelegateHelper.intentParcelable(key, defaultValue);
    }

    //------------------------ 控件操作 ------------------------

    /**
     * 通过View的id来获取子View
     *
     * @param resId view的id
     * @param <T>   泛型
     * @return View
     */
    @Override
    public <T extends View> T get(int resId) {
        return getViewFinder().get(resId);
    }

    /**
     * 获取布局View
     *
     * @return 布局View
     */
    @Override
    public View getRootView() {
        return getViewFinder().getRootView();
    }

    //-------------------------------- 设置文字 --------------------------------

    /**
     * 直接控件id设置文字
     *
     * @param text   文字
     * @param viewId 控件id，必须是TextView以及子类
     */
    @Override
    public void setViewText(CharSequence text, int viewId) {
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
    public void setTextWithDefault(CharSequence text, CharSequence defaultText, int viewId) {
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
    public CharSequence getViewText(int viewId) {
        return getViewFinder().getViewText(viewId);
    }

    /**
     * 使用View Id，获取文字，当获取为null是，可指定返回默认文字
     *
     * @param viewId      View id
     * @param defaultText 默认文字
     */
    @Override
    public CharSequence getTextWithDefault(int viewId, CharSequence defaultText) {
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

    //-------------------------------- 单次设置监听器 --------------------------------

    /**
     * 查找并且设置点击监听器
     *
     * @param listener 监听器
     * @param id       view 的 id
     */
    @Override
    public View findAndSetOnClick(int id, View.OnClickListener listener) {
        return getViewFinder().findAndSetOnClick(id, listener);
    }

    /**
     * 查找并设置长按监听器
     *
     * @param id       View的id
     * @param listener 监听器
     */
    @Override
    public View findAndSetOnLongClick(int id, View.OnLongClickListener listener) {
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
    public void setOnClickListener(View.OnClickListener listener, int... ids) {
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
    public void setVisible(int... ids) {
        getViewFinder().setVisible(ids);
    }

    /**
     * 以多个id的方式，批量设置View为隐藏
     *
     * @param ids 多个View的id
     */
    @Override
    public void setGone(int... ids) {
        getViewFinder().setGone(ids);
    }

    /**
     * 设置多个View显示
     */
    @Override
    public void setVisible(View... views) {
        getViewFinder().setVisible(views);
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
    public void loadUrlImage(Activity activity, String url, ImageView imageView, int defaultImage) {
        getViewFinder().loadUrlImage(activity, url, imageView, defaultImage);
    }

    /**
     * 加载网络圆形图片
     */
    @Override
    public void loadUrlImageToRound(Activity activity, String url, ImageView imageView, int defaultImage) {
        getViewFinder().loadUrlImageToRound(activity, url, imageView, defaultImage);
    }

    /**
     * 加载网络圆角图片
     */
    @Override
    public void loadUrlImageToCorner(Activity activity, String url, ImageView imageView, int defaultImage) {
        getViewFinder().loadUrlImageToCorner(activity, url, imageView, defaultImage);
    }

    /**
     * 加载内存卡图片
     */
    @Override
    public void loadFileImage(Activity activity, String filePath, ImageView imageView, int defaultImage) {
        getViewFinder().loadFileImage(activity, filePath, imageView, defaultImage);
    }

    /**
     * 加载图片bitmap
     */
    @Override
    public void loadImageToBitmap(Activity activity, String url, LoadImageCallback loadImageCallback) {
        getViewFinder().loadImageToBitmap(activity, url, loadImageCallback);
    }

    /**
     * 清除图片缓存
     */
    @Override
    public void clearMemoryCache(Activity activity) {
        getViewFinder().clearMemoryCache(activity);
    }
}