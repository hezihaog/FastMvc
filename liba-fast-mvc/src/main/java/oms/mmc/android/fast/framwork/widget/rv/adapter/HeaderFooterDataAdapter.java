package oms.mmc.android.fast.framwork.widget.rv.adapter;

import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import oms.mmc.android.fast.framwork.widget.list.ICommonListAdapter;
import oms.mmc.android.fast.framwork.widget.list.helper.IAssistHelper;
import oms.mmc.android.fast.framwork.widget.rv.base.BaseItemData;
import oms.mmc.helper.ListScrollHelper;

/**
 * Package: oms.mmc.android.fast.framwork.widget.rv.adapter
 * FileName: HeaderFooterDataAdapter
 * Date: on 2018/3/23  下午8:24
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */

public class HeaderFooterDataAdapter<T extends BaseItemData> extends HeaderFooterAdapter implements ICommonListAdapter<T> {
    private ICommonListAdapter<T> mAdapter;

    public HeaderFooterDataAdapter(ICommonListAdapter<T> adapter) {
        super((RecyclerView.Adapter) adapter);
        mAdapter = adapter;
    }

    @Override
    public void setAdapter(RecyclerView.Adapter adapter) {
        super.setAdapter(adapter);
        mAdapter = (ICommonListAdapter<T>) adapter;
    }

    @Override
    public void setRefreshListData(ArrayList<T> data, boolean isReverse, boolean isFirst) {
        mAdapter.setRefreshListData(data, isReverse, isFirst);
    }

    @Override
    public void setLoadMoreListData(ArrayList<T> data, boolean isReverse, boolean isFirst) {
        mAdapter.setLoadMoreListData(data, isReverse, isFirst);
    }

    @Override
    public void setListData(ArrayList<T> listData) {
        mAdapter.setListData(listData);
    }

    @Override
    public ArrayList<T> getListData() {
        return mAdapter.getListData();
    }

    @Override
    public boolean isEmpty() {
        return mAdapter.isEmpty();
    }

    @Override
    public void addOnItemClickListener(OnScrollableViewItemClickListener onItemClickListener) {
        mAdapter.addOnItemClickListener(onItemClickListener);
    }

    @Override
    public void removeOnItemClickListener(OnScrollableViewItemClickListener onItemClickListener) {
        mAdapter.removeOnItemClickListener(onItemClickListener);
    }

    @Override
    public void addOnItemLongClickListener(OnScrollableViewItemLongClickListener onItemLongClickListener) {
        mAdapter.addOnItemLongClickListener(onItemLongClickListener);
    }

    @Override
    public void removeOnItemLongClickListener(OnScrollableViewItemLongClickListener onItemLongClickListener) {
        mAdapter.removeOnItemLongClickListener(onItemLongClickListener);
    }

    @Override
    public void setAssistHelper(IAssistHelper assistHelper) {
        mAdapter.setAssistHelper(assistHelper);
    }

    @Override
    public IAssistHelper getAssistHelper() {
        return mAdapter.getAssistHelper();
    }

    @Override
    public void setListScrollHelper(ListScrollHelper listScrollHelper) {
        mAdapter.setListScrollHelper(listScrollHelper);
    }

    @Override
    public ListScrollHelper getListScrollHelper() {
        return mAdapter.getListScrollHelper();
    }
}
