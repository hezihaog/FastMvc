package oms.mmc.android.fast.framwork.util;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import mmc.image.ImageLoader;
import mmc.image.LoadImageCallback;

/**
 * Package: oms.mmc.android.fast.framwork.util
 * FileName: IViewFinder
 * Date: on 2018/3/7  下午12:15
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */

public interface IViewFinder {
    //------------------------ 控件操作 ------------------------

    /**
     * 抽象方法，子类实现返回ViewFinder
     */
    ViewFinder getViewFinder();

    /**
     * 通过View的id来获取子View
     *
     * @param resId view的id
     * @param <T>   泛型
     * @return View
     */
    <T extends View> T get(int resId);

    /**
     * 获取布局View
     *
     * @return 布局View
     */
    View getRootView();

    //-------------------------------- 设置文字 --------------------------------

    /**
     * 直接控件id设置文字
     *
     * @param text   文字
     * @param viewId 控件id，必须是TextView以及子类
     */
    void setViewText(CharSequence text, int viewId);

    /**
     * 直接控件id设置文字
     *
     * @param text        文字
     * @param defaultText 如果传入文字的值为null，设置的默认文字
     * @param viewId      控件id，必须是TextView以及子类
     */
    void setTextWithDefault(CharSequence text, CharSequence defaultText, int viewId);

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
     * 使用TextView，获取View上的文字，当获取为null时，返回空字符串 ""
     *
     * @param view TextView对象
     * @return View上的文字
     */
    CharSequence getViewText(TextView view);

    /**
     * 使用View id，获取View上的文字，当获取为null时，返回空字符串 ""
     *
     * @param viewId TextView的id
     * @return View上的文字
     */
    CharSequence getViewText(int viewId);

    /**
     * 使用View Id，获取文字，当获取为null是，可指定返回默认文字
     *
     * @param viewId      View id
     * @param defaultText 默认文字
     */
    CharSequence getTextWithDefault(int viewId, CharSequence defaultText);

    /**
     * 使用View，获取文字，当获取为null是，可指定返回默认文字
     *
     * @param textView    View id
     * @param defaultText 默认文字
     */
    CharSequence getTextWithDefault(TextView textView, CharSequence defaultText);

    //-------------------------------- 单次设置监听器 --------------------------------

    /**
     * 查找并且设置点击监听器
     *
     * @param listener 监听器
     * @param id       view 的 id
     */
    View findAndSetOnClick(int id, View.OnClickListener listener);

    /**
     * 查找并设置长按监听器
     *
     * @param id       View的id
     * @param listener 监听器
     */
    View findAndSetOnLongClick(int id, View.OnLongClickListener listener);

    //-------------------------------- 批量设置监听器 --------------------------------

    /**
     * 批量id，设置同一个点击监听器
     *
     * @param listener 点击监听器
     * @param ids      view 的 id
     */
    void setOnClickListener(View.OnClickListener listener, int... ids);

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
    void setOnLongClickListener(View.OnLongClickListener listener, int... ids);

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
    void setVisible(int... ids);

    /**
     * 以多个id的方式，批量设置View为隐藏
     *
     * @param ids 多个View的id
     */
    void setGone(int... ids);

    /**
     * 设置多个View显示
     */
    void setVisible(View... views);

    /**
     * 设置多个View隐藏
     */
    void setGone(View... views);

    //-------------------------------- 图片加载 --------------------------------

    ImageLoader getImageLoader();

    /**
     * 加载网络图片
     */
    void loadUrlImage(Activity activity, String url, ImageView imageView, int defaultImage);

    /**
     * 加载网络圆形图片
     */
    void loadUrlImageToRound(Activity activity, String url, ImageView imageView, int defaultImage);

    /**
     * 加载网络圆角图片
     */
    void loadUrlImageToCorner(Activity activity, String url, ImageView imageView, int defaultImage);

    /**
     * 加载内存卡图片
     */
    void loadFileImage(Activity activity, String filePath, ImageView imageView, int defaultImage);

    /**
     * 加载图片bitmap
     */
    void loadImageToBitmap(Activity activity, String url, LoadImageCallback loadImageCallback);

    /**
     * 清除图片缓存
     */
    void clearMemoryCache(Activity activity);
}