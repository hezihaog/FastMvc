package oms.mmc.android.fast.framwork.widget.rv.manager;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;

import oms.mmc.android.fast.framwork.widget.rv.manager.delegate.FastScrollLayoutManagerDelegate;

/**
 * Package: oms.mmc.android.fast.framwork.widget.rv.manager
 * FileName: FastScrollStaggeredGridLayoutManager
 * Date: on 2018/4/2  下午8:08
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */
public class FastScrollStaggeredGridLayoutManager extends StaggeredGridLayoutManager {
    public FastScrollStaggeredGridLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public FastScrollStaggeredGridLayoutManager(int spanCount, int orientation) {
        super(spanCount, orientation);
    }

    @Override
    public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int position) {
        FastScrollLayoutManagerDelegate delegate = new FastScrollLayoutManagerDelegate();
        delegate.smoothScrollToPosition(recyclerView, this, position);
    }
}
