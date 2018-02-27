package oms.mmc.android.fast.framwork.base;

import android.app.Activity;

import java.util.ArrayList;

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
        int curPage;
        if (!isReverse) {
            //正常不是反转布局，每次刷新都是拿第一页
            curPage = FIRST_PAGE_NUM;
        } else {
            //如果是反转布局，则下拉刷新是下一页
            curPage = page + 1;
        }
        return load(curPage);
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
