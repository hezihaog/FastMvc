package oms.mmc.android.fast.framwork.widget.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

import oms.mmc.android.fast.framwork.base.IScrollableView;

/**
 * Package: oms.mmc.android.fast.framwork.widget.view
 * FileName: ScrollableListView
 * Date: on 2018/2/11  下午6:10
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */

public abstract class ScrollableListView extends ListView implements IScrollableView {
    public ScrollableListView(Context context) {
        super(context);
    }

    public ScrollableListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ScrollableListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
