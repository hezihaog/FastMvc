/*
 * Copyright 2017 GcsSloop
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Last modified 2017-03-11 22:24:54
 *
 * GitHub:  https://github.com/GcsSloop
 * Website: http://www.gcssloop.com
 * Weibo:   http://weibo.com/GcsSloop
 */

package oms.mmc.android.fast.framwork.util;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import mmc.image.ImageLoader;
import mmc.image.LoadImageCallback;
import mmc.image.MMCImageLoader;

/**
 * View查找器，封装一些常用方法
 *
 * @author 子和
 */
public class ViewFinder implements IViewFinder {
    private EasySparseArrayCompat<View> mViews;
    private View mRootView;
    private Activity mActivity;

    public ViewFinder(Activity activity, LayoutInflater inflater, ViewGroup parent, int layoutId) {
        this.mActivity = activity;
        this.mViews = createCacheMap();
        this.mRootView = inflater.inflate(layoutId, parent, false);
    }

    public ViewFinder(Activity activity, View rootLayout) {
        this.mActivity = activity;
        this.mViews = createCacheMap();
        this.mRootView = rootLayout;
    }

    private EasySparseArrayCompat<View> createCacheMap() {
        return new EasySparseArrayCompat(5);
    }

    @Override
    public IViewFinder getViewFinder() {
        return this;
    }

    public void setRootView(View rootView) {
        mRootView = rootView;
    }

    public void setActivity(Activity activity) {
        mActivity = activity;
    }

    private void ensureInit() {
        if (mViews == null) {
            mViews = createCacheMap();
        }
    }

    @Override
    public void saveInstance(Bundle bundle) {
        onSaveInstance(bundle);
    }

    @Override
    public void restoreInstance(Bundle bundle) {
        onRestoreInstance(bundle);
    }

    /**
     * 帮保存状态时回调
     */
    protected void onSaveInstance(Bundle bundle) {

    }

    /**
     * 当恢复状态时回调
     */
    protected void onRestoreInstance(Bundle bundle) {

    }

    @Override
    public IViewFinder getFinder() {
        return this;
    }

    /**
     * 回收内存，在生命周期销毁时调用
     */
    @Override
    public void recycle() {
        if (mViews != null) {
            mViews.clear();
        }
        mRootView = null;
        mActivity = null;
    }

    /**
     * 获取Activity
     */
    public Activity getActivity() {
        return mActivity;
    }

    /**
     * 通过View的id来获取子View
     *
     * @param resId view的id
     * @param <T>   泛型
     * @return View
     */
    @Override
    public <T extends View> T get(@IdRes int resId) {
        ensureInit();
        View view = mViews.get(resId);
        //如果该View没有缓存过，则查找View并缓存
        if (view == null) {
            view = mRootView.findViewById(resId);
            mViews.put(resId, view);
        }
        return (T) view;
    }

    /**
     * 获取布局View
     *
     * @return 布局View
     */
    @Override
    public View getRootView() {
        return mRootView;
    }

    /**
     * 判断字符串是否为空
     *
     * @param str 要判断的字符串
     */
    @Override
    public boolean isEmpty(CharSequence str) {
        return str == null || str.length() == 0;
    }

    /**
     * 判断字符串是否不为空
     *
     * @param str 要判断的字符串
     */
    @Override
    public boolean isNotEmpty(CharSequence str) {
        return !isEmpty(str);
    }

    /**
     * 判断View显示的文字是否为空
     */
    @Override
    public boolean viewTextIsEmpty(int viewId, boolean isFilterSpace) {
        return isFilterSpace ? isEmpty(getViewTextWithTrim(viewId)) : isEmpty(getViewText(viewId));
    }

    /**
     * 判断View显示的文字是否为空
     */
    @Override
    public boolean viewTextIsEmpty(TextView view, boolean isFilterSpace) {
        return isFilterSpace ? isEmpty(getViewTextWithTrim(view)) : isEmpty(getViewText(view));
    }

    /**
     * 判断View显示的文字是否不为空，包含Trim
     *
     * @param viewId 控件id
     */
    @Override
    public boolean viewTextIsEmptyWithTrim(int viewId) {
        return viewTextIsEmpty(viewId, true);
    }

    /**
     * 判断View显示的文字是否不为空，包含Trim
     *
     * @param view 控件
     */
    @Override
    public boolean viewTextIsEmptyWithTrim(TextView view) {
        return viewTextIsEmpty(view, true);
    }

    /**
     * 判断View显示的文字是否不为空
     */
    @Override
    public boolean viewTextIsNotEmpty(int viewId) {
        return isNotEmpty(getViewTextWithTrim(viewId));
    }

    /**
     * 判断View显示的文字是否不为空
     */
    @Override
    public boolean viewTextIsNotEmpty(TextView view) {
        return isNotEmpty(getViewTextWithTrim(view));
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
        View view = get(viewId);
        if (view instanceof TextView) {
            setViewText(text, (TextView) view);
        }
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
        View view = get(viewId);
        if (view instanceof TextView) {
            setTextWithDefault(text, defaultText, (TextView) view);
        }
    }

    /**
     * 为TextView设置文字，如果传入的Text为null，则设置为空字符串
     */
    @Override
    public void setViewText(CharSequence text, TextView view) {
        setTextWithDefault(text, "", view);
    }

    /**
     * 为Text设置文字，如果传入的Text为null，则设置为默认值
     */
    @Override
    public void setTextWithDefault(CharSequence text, CharSequence defaultText, TextView view) {
        if (view == null) {
            return;
        }
        view.setText(null == text ? defaultText : text);
    }

    //-------------------------------- 获取TextView及其子类的文字 --------------------------------

    /**
     * 使用TextView，获取View上的文字，当获取为""时，返回空字符串 ""
     *
     * @param view TextView对象
     * @return View上的文字
     */
    @Override
    public CharSequence getViewText(TextView view) {
        return getTextWithDefault(view, "");
    }

    /**
     * 使用View id，获取View上的文字，当获取为""时，返回空字符串 ""
     *
     * @param viewId TextView的id
     * @return View上的文字
     */
    @Override
    public CharSequence getViewText(@IdRes int viewId) {
        return getTextWithDefault(viewId, "");
    }

    /**
     * 使用View Id，获取文字，当获取为""时，可指定返回默认文字
     *
     * @param viewId      View id
     * @param defaultText 默认文字
     */
    @Override
    public CharSequence getTextWithDefault(@IdRes int viewId, CharSequence defaultText) {
        View view = get(viewId);
        if (view instanceof TextView) {
            return getTextWithDefault((TextView) view, defaultText);
        }
        return defaultText;
    }

    /**
     * 使用View，获取文字，当获取为""时，可指定返回默认文字
     *
     * @param textView    View id
     * @param defaultText 默认文字
     */
    @Override
    public CharSequence getTextWithDefault(TextView textView, CharSequence defaultText) {
        if (textView == null) {
            return defaultText;
        }
        CharSequence text = textView.getText();
        if (isEmpty(text.toString().trim())) {
            return defaultText;
        } else {
            return text;
        }
    }

    @Override
    public CharSequence getViewTextWithTrim(@IdRes int viewId) {
        return getTextWithDefault(viewId, "").toString().trim();
    }

    @Override
    public CharSequence getViewTextWithTrim(TextView view) {
        return getTextWithDefault(view, "").toString().trim();
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
        if (id <= 0) {
            return null;
        }
        get(id).setOnClickListener(listener);
        return get(id);
    }

    /**
     * 查找并设置长按监听器
     *
     * @param id       View的id
     * @param listener 监听器
     */
    @Override
    public View findAndSetOnLongClick(@IdRes int id, View.OnLongClickListener listener) {
        if (id <= 0) {
            return null;
        }
        get(id).setOnLongClickListener(listener);
        return get(id);
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
        if (ids == null) {
            return;
        }
        if (ids.length == 0) {
            return;
        }
        for (int id : ids) {
            if (id <= 0) {
                continue;
            }
            get(id).setOnClickListener(listener);
        }
    }

    /**
     * 批量View，设置同一个点击监听器
     *
     * @param listener 点击监听器
     * @param views    多个View对象
     */
    @Override
    public void setOnClickListener(View.OnClickListener listener, View... views) {
        if (views == null) {
            return;
        }
        for (View view : views) {
            if (view == null) {
                continue;
            }
            view.setOnClickListener(listener);
        }
    }

    /**
     * 批量View id，设置同一个长按监听器
     *
     * @param listener 长按监听器
     * @param ids      View 的id
     */
    @Override
    public void setOnLongClickListener(View.OnLongClickListener listener, @IdRes int... ids) {
        if (ids == null) {
            return;
        }
        if (ids.length == 0) {
            return;
        }
        for (int id : ids) {
            get(id).setOnLongClickListener(listener);
        }
    }

    /**
     * 批量View，设置同一个长按监听器
     *
     * @param listener 长按监听器
     * @param views    多个View对象
     */
    @Override
    public void setOnLongClickListener(View.OnLongClickListener listener, View... views) {
        if (views == null) {
            return;
        }
        if (views.length == 0) {
            return;
        }
        for (View view : views) {
            view.setOnLongClickListener(listener);
        }
    }

    //-------------------------------- 设置View的显示隐藏 --------------------------------

    /**
     * 以多个id的方式，批量设置View为显示
     *
     * @param ids 多个View的id
     */
    @Override
    public void setVisible(@IdRes int... ids) {
        for (int id : ids) {
            if (id <= 0) {
                continue;
            }
            get(id).setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void setInVisible(int... ids) {
        for (int id : ids) {
            if (id <= 0) {
                continue;
            }
            get(id).setVisibility(View.INVISIBLE);
        }
    }

    /**
     * 以多个id的方式，批量设置View为隐藏
     *
     * @param ids 多个View的id
     */
    @Override
    public void setGone(@IdRes int... ids) {
        for (int id : ids) {
            if (id <= 0) {
                continue;
            }
            get(id).setVisibility(View.GONE);
        }
    }

    /**
     * 设置多个View显示
     */
    @Override
    public void setVisible(View... views) {
        for (View view : views) {
            if (view == null) {
                continue;
            }
            view.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void setInVisible(View... views) {
        for (View view : views) {
            if (view == null) {
                continue;
            }
            view.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * 设置多个View隐藏
     */
    @Override
    public void setGone(View... views) {
        for (View view : views) {
            view.setVisibility(View.GONE);
        }
    }

    //-------------------------------- 图片加载 --------------------------------

    @Override
    public ImageLoader getImageLoader() {
        return MMCImageLoader.getInstance().getmImageLoader();
    }

    /**
     * 加载网络图片
     */
    @Override
    public void loadUrlImage(String url, ImageView imageView, @IdRes int defaultImage) {
        getImageLoader().loadUrlImage(getActivity(), imageView, url, defaultImage);
    }

    /**
     * 加载网络圆形图片
     */
    @Override
    public void loadUrlImageToRound(String url, ImageView imageView, @IdRes int defaultImage) {
        getImageLoader().loadUrlImageToRound(getActivity(), imageView, url, defaultImage);
    }

    /**
     * 加载网络圆角图片
     */
    @Override
    public void loadUrlImageToCorner(String url, ImageView imageView, @IdRes int defaultImage) {
        getImageLoader().loadUrlImageToCorner(getActivity(), imageView, url, defaultImage);
    }

    /**
     * 加载内存卡图片
     */
    @Override
    public void loadFileImage(String filePath, ImageView imageView, @IdRes int defaultImage) {
        getImageLoader().loadFileImage(getActivity(), imageView, filePath, defaultImage);
    }

    /**
     * 加载图片bitmap
     */
    @Override
    public void loadImageToBitmap(String url, LoadImageCallback loadImageCallback) {
        getImageLoader().loadImageToBitmap(getActivity(), url, loadImageCallback);
    }

    @Override
    public void loadDrawableResId(@IdRes int imageViewId, @DrawableRes int resId) {
        View view = get(imageViewId);
        if (view instanceof ImageView) {
            loadDrawableResId((ImageView) view, resId);
        }
    }

    @Override
    public void loadDrawableResId(ImageView imageView, @DrawableRes int resId) {
        getImageLoader().loadDrawableResId(getActivity(), imageView, resId);
    }

    /**
     * 清除图片缓存
     */
    @Override
    public void clearImageMemoryCache() {
        getImageLoader().clearMemoryCache(getActivity());
    }
}