package oms.mmc.android.fast.framwork.widget.rv.adapter;

import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import oms.mmc.android.fast.framwork.base.IDataAdapter;
import oms.mmc.android.fast.framwork.widget.list.ICommonListAdapter;
import oms.mmc.android.fast.framwork.widget.list.helper.IAssistHelper;
import oms.mmc.android.fast.framwork.widget.rv.base.BaseItemData;

/**
 * Package: oms.mmc.android.fast.framwork.widget.rv.adapter
 * FileName: HeaderFooterDataAdapter
 * Date: on 2018/3/23  下午8:24
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */

public class HeaderFooterDataAdapter<T extends BaseItemData> extends HeaderFooterAdapter implements IDataAdapter<T>, ICommonListAdapter {
    private IDataAdapter<T> mAdapter;

    public HeaderFooterDataAdapter(IDataAdapter<T> adapter) {
        super((RecyclerView.Adapter) adapter);
        mAdapter = adapter;
    }

    @Override
    public void setAdapter(RecyclerView.Adapter adapter) {
        super.setAdapter(adapter);
        mAdapter = (IDataAdapter<T>) adapter;
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
    public void setListData(ArrayList<T> data) {
        mAdapter.setListData(data);
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
        ((ICommonListAdapter)mAdapter).addOnItemClickListener(onItemClickListener);
    }

    @Override
    public void removeOnItemClickListener(OnScrollableViewItemClickListener onItemClickListener) {
        ((ICommonListAdapter)mAdapter).removeOnItemClickListener(onItemClickListener);
    }

    @Override
    public void addOnItemLongClickListener(OnScrollableViewItemLongClickListener onItemLongClickListener) {
        ((ICommonListAdapter)mAdapter).addOnItemLongClickListener(onItemLongClickListener);
    }

    @Override
    public void removeOnItemLongClickListener(OnScrollableViewItemLongClickListener onItemLongClickListener) {
        ((ICommonListAdapter)mAdapter).removeOnItemLongClickListener(onItemLongClickListener);
    }

    @Override
    public void setAssistHelper(IAssistHelper assistHelper) {
        ((ICommonListAdapter)mAdapter).setAssistHelper(assistHelper);
    }

    @Override
    public IAssistHelper getAssistHelper() {
        return ((ICommonListAdapter)mAdapter).getAssistHelper();
    }
}
