package oms.mmc.android.fast.framwork.base;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;

import oms.mmc.android.fast.framwork.R;
import oms.mmc.android.fast.framwork.util.IDataAdapter;
import oms.mmc.android.fast.framwork.util.IDataSource;
import oms.mmc.android.fast.framwork.util.RecyclerViewViewHelper;
import oms.mmc.android.fast.framwork.widget.rv.adapter.HeaderFooterAdapter;
import oms.mmc.android.fast.framwork.widget.rv.adapter.MultiTypeAdapter;
import oms.mmc.android.fast.framwork.widget.rv.base.BaseItemData;
import oms.mmc.android.fast.framwork.widget.rv.base.BaseStickyTpl;
import oms.mmc.android.fast.framwork.widget.rv.base.BaseTpl;
import oms.mmc.android.fast.framwork.widget.rv.sticky.StickyHeaders;

public class BaseListAdapter<T extends BaseItemData> extends MultiTypeAdapter<T> implements IDataAdapter<ArrayList<T>, BaseTpl.ViewHolder>, StickyHeaders, StickyHeaders.ViewSetup {
    /**
     * 不使用粘性头部
     */
    public static final int NOT_STICKY_SECTION = -1;
    /**
     * 粘性条目的类型，默认没有粘性头部
     */
    private int stickySectionViewType = NOT_STICKY_SECTION;
    /**
     * 支持头部尾部的适配器装饰类，最终设置给rv的是这个适配器，而不是BaseAdapter
     */
    private final HeaderFooterAdapter mHeaderFooterAdapter;

    public BaseListAdapter(RecyclerView recyclerView, BaseActivity activity, IDataSource dataSource,
                           HashMap itemViewClazzMap, RecyclerViewViewHelper recyclerViewHelper, int stickySectionViewType) {
        super(recyclerView, activity, dataSource, itemViewClazzMap, recyclerViewHelper);
        this.stickySectionViewType = stickySectionViewType;
        mHeaderFooterAdapter = new HeaderFooterAdapter(this);
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
    public void setRefreshListViewData(ArrayList<T> data, boolean isReverse, boolean isFirst) {
        ArrayList<T> listData = getListData();
        //第一次刷新
        if (isFirst) {
            listData.addAll(0, data);
        } else {
            //不是第一次刷新
            if (!isReverse) {
                listData.clear();
                listData.addAll(data);
            } else {
                //如果是QQ聊天界面的在顶部下拉加载，直接将数据加上，rv设置布局反转即可
                listData.addAll(data);
            }
        }
    }

    @Override
    public void setLoadMoreListViewData(ArrayList<T> data, boolean isReverse, boolean isFirst) {
        getListData().addAll(data);
    }

    @Override
    public void setListViewData(ArrayList<T> data) {
        ArrayList<T> listViewData = getListData();
        setListData(data);
    }

    @Override
    public boolean isEmpty() {
        if (this.getItemCount() == 0) {
            return true;
        } else {
            return false;
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

    /**
     * 添加Header View
     *
     * @param headerView Header View
     */
    public void addHeaderView(View headerView) {
        mHeaderFooterAdapter.addHeaderView(headerView);
    }

    /**
     * 添加Footer View
     *
     * @param footerView Footer View
     */
    public void addFooterView(View footerView) {
        mHeaderFooterAdapter.addFooterView(footerView);
    }

    /**
     * 移除Header View
     *
     * @param footerView Header View
     */
    public void removeFooterView(View footerView) {
        mHeaderFooterAdapter.removeFooter(footerView);
    }

    /**
     * 移除Footer View
     *
     * @param headerView Footer View
     */
    public void removeHeaderView(View headerView) {
        mHeaderFooterAdapter.removeHeader(headerView);
    }

    public HeaderFooterAdapter getHeaderFooterAdapter() {
        return mHeaderFooterAdapter;
    }
}
