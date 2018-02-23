package oms.mmc.android.fast.framwork.base;

import android.app.Activity;

import java.util.ArrayList;

import oms.mmc.android.fast.framwork.util.IDataSource;

public abstract class BaseListDataSource<T> implements IDataSource<T> {
    protected final static int FIRST_PAGE_NUM = 1;

    /**
     * 当前页码
     */
    protected int page = 0;
    /**
     * 是否有加载更多
     */
    protected boolean hasMore = false;

    protected Activity mActivity;
    protected ArrayList<T> mOriginListViewData = new ArrayList<T>();

    public BaseListDataSource(Activity activity) {
        this.mActivity = activity;
    }

    @Override
    public ArrayList<T> getOriginListViewData() {
        return mOriginListViewData;
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
