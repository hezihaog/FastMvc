package oms.mmc.android.fast.framwork.util;

import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import oms.mmc.android.fast.framwork.imageloader.ImageLoader;
import oms.mmc.android.fast.framwork.imageloader.LoadImageCallback;

/**
 * Package: oms.mmc.android.fast.framwork.util
 * FileName: IViewFinderAction
 * Date: on 2018/3/8  上午10:14
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */

public interface IViewFinderAction {
    //------------------------ 控件操作 ------------------------

    /**
     * 抽象方法，子类实现返回ViewFinder
     */
    IViewFinder getViewFinder();

    /**
     * 获取布局View
     *
     * @return 布局View
     */
    View getRootView();

    /**
     * 字符串是否是空的
     *
     * @param str 要判断的文字
     */
    boolean isEmpty(CharSequence str);

    /**
     * 字符串是否不是空的
     */
    boolean isNotEmpty(CharSequence str);

    /**
     * 判断View显示的文字是否为空
     *
     * @param viewId        控件id
     * @param isFilterSpace 是否调用trim，就是如果是空格符的话，则不当是有文字内容
     */
    boolean viewTextIsEmpty(@IdRes int viewId, boolean isFilterSpace);

    /**
     * 判断View显示的文字是否为空
     *
     * @param view          控件对象
     * @param isFilterSpace 是否调用trim，就是如果是空格符的话，则不当是有文字内容
     */
    boolean viewTextIsEmpty(TextView view, boolean isFilterSpace);

    /**
     * 判断View显示的文字是否为空，该方法是包含过滤前后空格
     *
     * @param viewId 控件id
     */
    boolean viewTextIsEmptyWithTrim(@IdRes int viewId);

    /**
     * 判断View显示的文字是否为空，该方法是包含过滤前后空格
     *
     * @param view 控件对象
     */
    boolean viewTextIsEmptyWithTrim(TextView view);

    /**
     * 判断View显示的文字是否不为空
     *
     * @param viewId 控件id
     */
    boolean viewTextIsNotEmpty(@IdRes int viewId);

    /**
     * 判断View显示的文字是否不为空
     *
     * @param view 控件对象
     */
    boolean viewTextIsNotEmpty(TextView view);

    //-------------------------------- 设置文字 --------------------------------

    /**
     * 直接控件id设置文字
     *
     * @param text   文字
     * @param viewId 控件id，必须是TextView以及子类
     */
    void setViewText(CharSequence text, @IdRes int viewId);

    /**
     * 直接控件id设置文字
     *
     * @param text        文字
     * @param defaultText 如果传入文字的值为null，设置的默认文字
     * @param viewId      控件id，必须是TextView以及子类
     */
    void setTextWithDefault(CharSequence text, CharSequence defaultText, @IdRes int viewId);

    /**
     * 为TextView设置文字，如果传入的Text为null，则设置为空字符串
     *
     * @param text 要设置的文字
     * @param view TextView对象
     */
    void setViewText(CharSequence text, TextView view);

    /**
     * 为Text设置文字，如果传入的Text为null，则设置为默认值
     *
     * @param text        要设置的问题
     * @param defaultText 传入的文字为null时，设置的默认文本
     * @param view        TextView
     */
    void setTextWithDefault(CharSequence text, CharSequence defaultText, TextView view);

    //-------------------------------- 获取TextView及其子类的文字 --------------------------------

    /**
     * 使用TextView，获取View上的文字，当获取为""时，返回空字符串 ""
     *
     * @param view TextView对象
     * @return View上的文字
     */
    CharSequence getViewText(TextView view);

    /**
     * 使用View id，获取View上的文字，当获取为""时，返回空字符串 ""
     *
     * @param viewId TextView的id
     * @return View上的文字
     */
    CharSequence getViewText(@IdRes int viewId);

    /**
     * 使用View Id，获取文字，当获取为""时，可指定返回默认文字
     *
     * @param viewId      View id
     * @param defaultText 默认文字
     */
    CharSequence getTextWithDefault(@IdRes int viewId, CharSequence defaultText);

    /**
     * 使用View，获取文字，当获取为""时，可指定返回默认文字
     *
     * @param textView    View id
     * @param defaultText 默认文字
     */
    CharSequence getTextWithDefault(TextView textView, CharSequence defaultText);

    /**
     * 获取View上的文字，并且toString和trim去掉前后空格
     *
     * @param viewId View的id
     */
    CharSequence getViewTextWithTrim(@IdRes int viewId);

    /**
     * 获取View上的文字，并且toString和trim去掉前后空格
     *
     * @param view View对象
     */
    CharSequence getViewTextWithTrim(TextView view);

    //-------------------------------- 单次设置监听器 --------------------------------

    /**
     * 查找并且设置点击监听器
     *
     * @param listener 监听器
     * @param id       view 的 id
     */
    View findAndSetOnClick(@IdRes int id, View.OnClickListener listener);

    /**
     * 查找并设置长按监听器
     *
     * @param id       View的id
     * @param listener 监听器
     */
    View findAndSetOnLongClick(@IdRes int id, View.OnLongClickListener listener);

    //-------------------------------- 批量设置监听器 --------------------------------

    /**
     * 批量id，设置同一个点击监听器
     *
     * @param listener 点击监听器
     * @param ids      view 的 id
     */
    void setOnClickListener(View.OnClickListener listener, @IdRes int... ids);

    /**
     * 批量View，设置同一个点击监听器
     *
     * @param listener 点击监听器
     * @param views    多个View对象
     */
    void setOnClickListener(View.OnClickListener listener, View... views);

    /**
     * 批量View id，设置同一个长按监听器
     *
     * @param listener 长按监听器
     * @param ids      View 的id
     */
    void setOnLongClickListener(View.OnLongClickListener listener, @IdRes int... ids);

    /**
     * 批量View，设置同一个长按监听器
     *
     * @param listener 长按监听器
     * @param views    多个View对象
     */
    void setOnLongClickListener(View.OnLongClickListener listener, View... views);

    //-------------------------------- 设置View的显示隐藏 --------------------------------

    /**
     * 以多个id的方式，批量设置View为显示
     *
     * @param ids 多个View的id
     */
    void setVisible(@IdRes int... ids);

    /**
     * 以多个id的方式，批量设置View为隐藏占位
     *
     * @param ids 多个View的id
     */
    void setInVisible(@IdRes int... ids);

    /**
     * 以多个id的方式，批量设置View为隐藏
     *
     * @param ids 多个View的id
     */
    void setGone(@IdRes int... ids);

    /**
     * 设置多个View显示
     */
    void setVisible(View... views);

    /**
     * 设置多个View为隐藏占位
     *
     * @param views 多个View对象
     */
    void setInVisible(View... views);

    /**
     * 设置多个View隐藏
     */
    void setGone(View... views);

    //-------------------------------- 图片加载 --------------------------------

    /**
     * 获取图片加载器
     */
    ImageLoader getImageLoader();

    /**
     * 加载网络图片
     */
    void loadUrlImage(String url, ImageView imageView, @IdRes int defaultImage);

    /**
     * 加载网络圆形图片
     */
    void loadUrlImageToRound(String url, ImageView imageView, @IdRes int defaultImage);

    /**
     * 加载网络圆角图片
     */
    void loadUrlImageToCorner(String url, ImageView imageView, @IdRes int defaultImage);

    /**
     * 加载内存卡图片
     */
    void loadFileImage(String filePath, ImageView imageView, @IdRes int defaultImage);

    /**
     * 加载图片bitmap
     */
    void loadImageToBitmap(String url, LoadImageCallback loadImageCallback);

    /**
     * 加载本地Res图片
     */
    void loadDrawableResId(@IdRes int imageViewId, @DrawableRes int resId);

    /**
     * 加载本地Res图片
     */
    void loadDrawableResId(ImageView imageView, @DrawableRes int resId);

    /**
     * 清除图片缓存
     */
    void clearImageMemoryCache();
}
