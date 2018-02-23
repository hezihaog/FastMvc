package oms.mmc.android.fast.framwork.base;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;

import oms.mmc.android.fast.framwork.R;
import oms.mmc.android.fast.framwork.widget.rv.base.BaseItemData;
import oms.mmc.android.fast.framwork.widget.rv.adapter.HeaderFooterAdapter;
import oms.mmc.android.fast.framwork.widget.rv.base.BaseStickyTpl;
import oms.mmc.android.fast.framwork.widget.rv.base.BaseTpl;
import oms.mmc.android.fast.framwork.widget.rv.sticky.StickyHeaders;
import oms.mmc.android.fast.framwork.util.LoadMoreHelper;
import oms.mmc.android.fast.framwork.util.IDataAdapter;
import oms.mmc.android.fast.framwork.util.IDataSource;
import oms.mmc.android.fast.framwork.util.RecyclerViewViewHelper;

public class BaseListAdapter<T extends BaseItemData> extends HeaderFooterAdapter<T> implements IDataAdapter<ArrayList<T>>, StickyHeaders, StickyHeaders.ViewSetup {
    /**
     * 不使用粘性头部
     */
    public static final int NOT_STICKY_SECTION = -1;
    /**
     * 粘性条目的类型，默认没有粘性头部
     */
    protected int stickySectionViewType = NOT_STICKY_SECTION;
    private final LoadMoreHelper mLoadMoreHelper;

    public BaseListAdapter(RecyclerView recyclerView, BaseActivity activity, IDataSource dataSource,
                           HashMap itemViewClazzMap, RecyclerViewViewHelper recyclerViewHelper, int stickySectionViewType) {
        super(recyclerView, activity, dataSource, itemViewClazzMap, recyclerViewHelper);
        this.mLoadMoreHelper = new LoadMoreHelper(this);
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
    public void setRefreshListViewData(ArrayList<T> data, boolean isReverse, boolean isFirst) {
        ArrayList<T> listViewData = getListViewData();
        mLoadMoreHelper.setLoadMoreTplPosition(listViewData.size() - 1);
        //第一次刷新
        if (isFirst) {
            listViewData.addAll(listViewData.size() - 1, data);
        } else {
            //不是第一次刷新
            if (!isReverse) {
                ArrayList<Integer> headerTypeList = getHeaderTypeList();
                ArrayList<Integer> footerTypeList = getFooterTypeList();
                ArrayList<T> headers = new ArrayList<T>();
                ArrayList<T> footers = new ArrayList<T>();
                for (int i = 0; i < listViewData.size(); i++) {
                    T viewData = listViewData.get(i);
                    if (headerTypeList.contains(viewData.getViewType())) {
                        headers.add(viewData);
                    }
                    if (footerTypeList.contains(viewData.getViewType())) {
                        footers.add(viewData);
                    }
                }
                listViewData.clear();
                listViewData.addAll(headers);
                listViewData.addAll(data);
                listViewData.addAll(footers);
            } else {
                //如果是QQ聊天界面的在顶部下拉加载，直接将数据加上，rv设置布局反转即可
                listViewData.addAll(data);
            }
        }
    }

    @Override
    public void setLoadMoreListViewData(ArrayList<T> data, boolean isReverse, boolean isFirst) {
        ArrayList<T> listViewData = getListViewData();
        mLoadMoreHelper.setLoadMoreTplPosition(listViewData.size() - 1);
        //上拉加载更多，插入在尾部之前
        listViewData.addAll(mLoadMoreHelper.getLoadMoreTplPosition(), data);
    }

    @Override
    public void setListViewData(ArrayList<T> data) {
        ArrayList<T> listViewData = getListViewData();
        mLoadMoreHelper.setLoadMoreTplPosition(listViewData.size() - 1);
        setListData(data);
    }

    @Override
    public boolean isEmpty() {
        if (this.getItemCount() == 1 && hasFooter()) {
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

    public LoadMoreHelper getLoadMoreHelper() {
        return mLoadMoreHelper;
    }
}
