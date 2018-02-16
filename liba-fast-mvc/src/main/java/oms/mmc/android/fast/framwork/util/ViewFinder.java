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

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.HashMap;

public class ViewFinder {
    private HashMap<Integer, View> mViews;
    private View mRootView;

    public ViewFinder(LayoutInflater inflater, ViewGroup parent, int layoutId) {
        this.mViews = new HashMap<Integer, View>();
        mRootView = inflater.inflate(layoutId, parent, false);
    }

    public ViewFinder(View rootLayout) {
        this.mViews = new HashMap<Integer, View>();
        mRootView = rootLayout;
    }

    /**
     * 通过View的id来获取子View
     *
     * @param resId view的id
     * @param <T>   泛型
     * @return 子View
     */
    public <T extends View> T get(int resId) {
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
    public View getRootView() {
        return mRootView;
    }

    /**
     * 设置多个View显示
     */
    public static void setViewVisible(View... views) {
        for (View view : views) {
            view.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 设置多个View隐藏
     */
    public static void setViewGone(View... views) {
        for (View view : views) {
            view.setVisibility(View.GONE);
        }
    }

    /**
     * 为TextView设置文字，如果传入的Text为null，则设置为空字符串
     */
    public static void setText(CharSequence text, TextView view) {
        setTextWithDefault(text, "", view);
    }

    /**
     * 为Text设置文字，如果传入的Text为null，则设置为默认值
     */
    public static void setTextWithDefault(CharSequence text, String defaultText, TextView view) {
        if (view == null) {
            return;
        }
        view.setText(null == text ? defaultText : text);
    }

    /**
     * 查找并且设置监听器
     *
     * @param l  监听器
     * @param id view 的 id
     */
    public void findAndSetOnClick(int id, View.OnClickListener l) {
        if (id <= 0) {
            return;
        }
        get(id).setOnClickListener(l);
    }

    /**
     * 设置监听器
     *
     * @param l   监听器
     * @param ids view 的 id
     */
    public void setOnClickListener(View.OnClickListener l, int... ids) {
        if (ids == null) {
            return;
        }
        for (int id : ids) {
            get(id).setOnClickListener(l);
        }
    }

    public void setVisibility(int... ids) {
        for (int id : ids) {
            if (id <= 0) {
                continue;
            }
            get(id).setVisibility(View.VISIBLE);
        }
    }

    public void setGone(int... ids) {
        for (int id : ids) {
            if (id <= 0) {
                continue;
            }
            get(id).setVisibility(View.GONE);
        }
    }
}