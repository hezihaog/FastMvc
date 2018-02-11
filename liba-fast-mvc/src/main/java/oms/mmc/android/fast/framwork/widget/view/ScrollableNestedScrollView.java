package oms.mmc.android.fast.framwork.widget.view;

import android.content.Context;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;

import oms.mmc.android.fast.framwork.base.IScrollableView;

/**
 * Package: oms.mmc.android.fast.framwork.widget.view
 * FileName: ScrollableNestedScrollView
 * Date: on 2018/2/11  下午6:33
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */

public abstract class ScrollableNestedScrollView extends NestedScrollView implements IScrollableView {
    public ScrollableNestedScrollView(Context context) {
        super(context);
    }

    public ScrollableNestedScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ScrollableNestedScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
