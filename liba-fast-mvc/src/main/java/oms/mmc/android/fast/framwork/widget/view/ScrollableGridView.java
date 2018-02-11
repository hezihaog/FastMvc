package oms.mmc.android.fast.framwork.widget.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

import oms.mmc.android.fast.framwork.base.IScrollableView;

/**
 * Package: oms.mmc.android.fast.framwork.base
 * FileName: ScrollableGridView
 * Date: on 2018/2/11  下午6:11
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */

public abstract class ScrollableGridView extends GridView implements IScrollableView {
    public ScrollableGridView(Context context) {
        super(context);
    }

    public ScrollableGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ScrollableGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
