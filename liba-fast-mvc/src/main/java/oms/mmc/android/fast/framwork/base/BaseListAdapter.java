package oms.mmc.android.fast.framwork.base;

import android.app.Activity;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;

import oms.mmc.android.fast.framwork.R;
import oms.mmc.android.fast.framwork.util.RecyclerViewViewHelper;
import oms.mmc.android.fast.framwork.widget.rv.adapter.MultiTypeAdapter;
import oms.mmc.android.fast.framwork.widget.rv.base.BaseItemData;
import oms.mmc.android.fast.framwork.widget.rv.base.BaseStickyTpl;
import oms.mmc.android.fast.framwork.widget.rv.base.BaseTpl;
import oms.mmc.android.fast.framwork.widget.rv.sticky.StickyHeaders;
import oms.mmc.factory.wait.inter.IWaitViewHost;
import oms.mmc.helper.widget.ScrollableRecyclerView;

public class BaseListAdapter extends MultiTypeAdapter implements IDataAdapter<BaseItemData>, StickyHeaders, StickyHeaders.ViewSetup {
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
    public void setRefreshListData(ArrayList<BaseItemData> data, boolean isReverse, boolean isFirst) {
        ArrayList<BaseItemData> listData = getListData();
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
    public void setLoadMoreListData(ArrayList<BaseItemData> data, boolean isReverse, boolean isFirst) {
        getListData().addAll(data);
    }

    @Override
    public void setListData(ArrayList listData) {
        super.setListData(listData);
    }

    @Override
    public ArrayList<BaseItemData> getListData() {
        return super.getListData();
    }

    @Override
    public boolean isEmpty() {
        return getItemCount() == 0;
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
