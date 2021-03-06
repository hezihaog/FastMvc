package oms.mmc.android.fast.framwork.widget.rv.manager;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import oms.mmc.android.fast.framwork.widget.rv.manager.delegate.FastScrollLayoutManagerDelegate;

/**
 * Package: oms.mmc.android.fast.framwork.widget.rv.manager
 * FileName: FastScrollGridLayoutManager
 * Date: on 2018/4/2  下午8:05
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */
public class FastScrollGridLayoutManager extends GridLayoutManager {
    public FastScrollGridLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public FastScrollGridLayoutManager(Context context, int spanCount) {
        super(context, spanCount);
    }

    public FastScrollGridLayoutManager(Context context, int spanCount, int orientation, boolean reverseLayout) {
        super(context, spanCount, orientation, reverseLayout);
    }

    @Override
    public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int position) {
        FastScrollLayoutManagerDelegate delegate = new FastScrollLayoutManagerDelegate();
        delegate.smoothScrollToPosition(recyclerView, this, position);
    }
}
