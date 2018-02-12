package oms.mmc.android.fast.framwork.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;

import oms.mmc.android.fast.framwork.R;
import oms.mmc.android.fast.framwork.bean.BaseItemData;
import oms.mmc.android.fast.framwork.recyclerview.sticky.StickyHeaders;
import oms.mmc.android.fast.framwork.util.LoadMoreHelper;
import oms.mmc.android.fast.framwork.widget.pulltorefresh.helper.IDataAdapter;
import oms.mmc.android.fast.framwork.widget.pulltorefresh.helper.IDataSource;
import oms.mmc.android.fast.framwork.widget.pulltorefresh.helper.RecyclerViewViewHelper;

public class BaseListAdapter<T extends BaseItemData> extends HeaderFooterAdapter<T> implements IDataAdapter<ArrayList<T>>, StickyHeaders, StickyHeaders.ViewSetup {
    /**
     * 不使用粘性头部
     */
    public static final int NOT_STICKY_SECTION = -1;
    /**
     * 粘性条目的类型，默认没有粘性头部
     */
    public int stickySectionViewType = NOT_STICKY_SECTION;
    private final LoadMoreHelper mLoadMoreHelper;

    public BaseListAdapter(RecyclerView recyclerView, BaseActivity activity, IDataSource dataSource,
                           HashMap itemViewClazzMap, RecyclerViewViewHelper recyclerViewHelper, int stickySectionViewType) {
        super(recyclerView, activity, dataSource, itemViewClazzMap, recyclerViewHelper);
        this.mLoadMoreHelper = new LoadMoreHelper(this);
        this.stickySectionViewType = stickySectionViewType;
    }

    @Override
    public void setRefreshListViewData(ArrayList<T> data, boolean isReverse, boolean isFirst) {
        ArrayList<T> listViewData = getListViewData();
        mLoadMoreHelper.setLoadMoreTplPosition(listViewData.size() - 1);
        //第一次刷新
        if (isFirst) {
            listViewData.addAll(0, data);
        } else {
            //不是第一次刷新
            T footLoaderMoreTpl = listViewData.get(mLoadMoreHelper.getLoadMoreTplPosition());
            if (!isReverse) {
                listViewData.clear();
                listViewData.addAll(data);
                listViewData.add(footLoaderMoreTpl);
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
