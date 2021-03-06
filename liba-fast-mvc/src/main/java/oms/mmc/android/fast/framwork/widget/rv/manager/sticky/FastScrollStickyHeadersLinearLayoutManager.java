package oms.mmc.android.fast.framwork.widget.rv.manager.sticky;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import oms.mmc.android.fast.framwork.widget.rv.manager.delegate.FastScrollLayoutManagerDelegate;
import oms.mmc.android.fast.framwork.widget.rv.sticky.StickyHeaders;
import oms.mmc.android.fast.framwork.widget.rv.sticky.StickyHeadersLinearLayoutManager;

/**
 * Package: oms.mmc.android.fast.framwork.widget.rv.manager
 * FileName: FastScrollStickyHeadersLinearLayoutManager
 * Date: on 2018/4/2  下午8:02
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */
public class FastScrollStickyHeadersLinearLayoutManager<T extends RecyclerView.Adapter & StickyHeaders> extends StickyHeadersLinearLayoutManager<T> {

    public FastScrollStickyHeadersLinearLayoutManager(Context context) {
        super(context);
    }

    public FastScrollStickyHeadersLinearLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    @Override
    public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int position) {
        FastScrollLayoutManagerDelegate delegate = new FastScrollLayoutManagerDelegate();
        delegate.smoothScrollToPosition(recyclerView, this, position);
    }
}