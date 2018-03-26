package oms.mmc.android.fast.framwork.base;

import android.app.Activity;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;

import java.util.HashMap;

import oms.mmc.android.fast.framwork.R;
import oms.mmc.android.fast.framwork.util.RecyclerViewViewHelper;
import oms.mmc.android.fast.framwork.widget.rv.adapter.MultiTypeAdapter;
import oms.mmc.android.fast.framwork.widget.rv.base.BaseStickyTpl;
import oms.mmc.android.fast.framwork.widget.rv.base.BaseTpl;
import oms.mmc.android.fast.framwork.widget.rv.sticky.StickyHeaders;
import oms.mmc.factory.wait.inter.IWaitViewHost;
import oms.mmc.helper.widget.ScrollableRecyclerView;

public class BaseListAdapter extends MultiTypeAdapter implements StickyHeaders, StickyHeaders.ViewSetup {
    /**
     * 不使用粘性头部
     */
    public static final int NOT_STICKY_SECTION = -1;
    /**
     * 粘性条目的类型，默认没有粘性头部
     */
    private int stickySectionViewType = NOT_STICKY_SECTION;

    public BaseListAdapter(ScrollableRecyclerView scrollableView, Activity activity, IDataSource dataSource,
                           HashMap itemViewClazzMap, RecyclerViewViewHelper recyclerViewHelper, int stickySectionViewType, IWaitViewHost waitViewHost) {
        super(scrollableView, activity, dataSource, itemViewClazzMap, recyclerViewHelper, waitViewHost);
        this.stickySectionViewType = stickySectionViewType;
    }

    @Override
    public void onViewAttachedToWindow(BaseTpl.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
        if (lp != null && lp instanceof StaggeredGridLayoutManager.LayoutParams) {
            if (isStickyHeader(holder.getLayoutPosition())) {
                StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) lp;
                p.setFullSpan(true);
            }
        }
    }

    @Override
    public boolean isStickyHeader(int position) {
        //不使用粘性头部，则都返回false
        if (stickySectionViewType == NOT_STICKY_SECTION) {
            return false;
        }
        return getItemViewType(position) == stickySectionViewType;
    }

    @Override
    public boolean isStickyHeaderViewType(int viewType) {
        return stickySectionViewType == viewType;
    }

    @Override
    public void setupStickyHeaderView(View stickyHeader) {
        Object tag = stickyHeader.getTag(R.id.tag_tpl);
        if (tag != null && tag instanceof BaseStickyTpl) {
            BaseStickyTpl tpl = (BaseStickyTpl) tag;
            tpl.onAttachSticky();
        }
    }

    @Override
    public void teardownStickyHeaderView(View stickyHeader) {
        Object tag = stickyHeader.getTag(R.id.tag_tpl);
        if (tag != null && tag instanceof BaseStickyTpl) {
            BaseStickyTpl tpl = (BaseStickyTpl) tag;
            tpl.onDetachedSticky();
        }
    }
}
