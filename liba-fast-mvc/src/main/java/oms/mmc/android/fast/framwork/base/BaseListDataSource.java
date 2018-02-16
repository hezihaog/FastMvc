package oms.mmc.android.fast.framwork.base;

import android.app.Activity;

import java.util.ArrayList;

import oms.mmc.android.fast.framwork.BaseMMCFastApplication;
import oms.mmc.android.fast.framwork.util.IDataSource;

public abstract class BaseListDataSource<T> implements IDataSource<T> {
    public final static int FIRST_PAGE_NUM = 1;

    protected BaseMMCFastApplication ac;
    protected Activity _activity;

    protected int page = 0;
    protected boolean hasMore = false;

    protected ArrayList<T> orginListViewData = new ArrayList<T>();

    public BaseListDataSource(Activity activity) {
        this.ac = (BaseMMCFastApplication) activity.getApplicationContext();
        this._activity = activity;
    }

    @Override
    public ArrayList<T> getOriginListViewData() {
        return orginListViewData;
    }

    @Override
    public ArrayList<T> refresh(boolean isReverse) throws Exception {
        if (!isReverse) {
            return load(FIRST_PAGE_NUM);
        } else {
            return load(page + 1);
        }
    }

    @Override
    public ArrayList<T> loadMore() throws Exception {
        return load(page + 1);
    }

    protected abstract ArrayList<T> load(int page) throws Exception;

    @Override
    public boolean hasMore() {
        return hasMore;
    }

    @Override
    public int getPage() {
        return page;
    }
}
