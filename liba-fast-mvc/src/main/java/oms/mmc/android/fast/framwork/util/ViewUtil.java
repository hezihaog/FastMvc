package oms.mmc.android.fast.framwork.util;

import android.view.View;
import android.widget.TextView;

/**
 * Created by Hezihao on 2017/9/6.
 */

public class ViewUtil {
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
}
