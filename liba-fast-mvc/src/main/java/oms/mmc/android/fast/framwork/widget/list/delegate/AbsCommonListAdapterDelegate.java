package oms.mmc.android.fast.framwork.widget.list.delegate;

import java.util.ArrayList;
import java.util.HashMap;

import oms.mmc.android.fast.framwork.widget.rv.base.BaseItemData;


/**
 * Created by wally on 18/3/25.
 */

public abstract class AbsCommonListAdapterDelegate<T extends BaseItemData, I> implements ICommonListAdapterDelegate<T, I> {
    private ArrayList<T> mListData;
    private HashMap<Integer, Class> viewTypeClassMap;

    public AbsCommonListAdapterDelegate(ArrayList<T> mListData, HashMap<Integer, Class> viewTypeClassMap) {
        this.mListData = mListData;
        this.viewTypeClassMap = viewTypeClassMap;
    }

    @Override
    public int getListCount() {
        return mListData.size();
    }

    @Override
    public long getListItemId(int position) {
        return position;
    }

    @Override
    public int getListViewTypeCount() {
        return viewTypeClassMap.size();
    }

    @Override
    public int getListItemViewType(int position) {
        return getItem(position).getViewType();
    }

    @Override
    public T getItem(int position) {
        return mListData.get(position);
    }
}
