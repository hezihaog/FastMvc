package oms.mmc.android.fast.framwork.widget.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

import oms.mmc.android.fast.framwork.base.IScrollableView;

/**
 * Package: oms.mmc.android.fast.framwork.widget.view
 * FileName: ScrollableScrollView
 * Date: on 2018/2/11  下午6:12
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */

public abstract class ScrollableScrollView extends ScrollView implements IScrollableView {
    public ScrollableScrollView(Context context) {
        super(context);
    }

    public ScrollableScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ScrollableScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
