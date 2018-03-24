package oms.mmc.android.fast.framwork.base;

import android.app.Activity;

import java.util.ArrayList;

public abstract class BaseListDataSource<T> implements IDataSource<T> {
    /**
     * 第一页
     */
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
    /**
     * 数据集
     */
    protected ArrayList<T> mListData = new ArrayList<T>();

    public BaseListDataSource(Activity activity) {
        this.mActivity = activity;
    }

    @Override
    public ArrayList<T> getListData() {
        return mListData;
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
        return load(curPage, true);
    }

    @Override
    public ArrayList<T> loadMore() throws Exception {
        return load(page + 1, false);
    }

    /**
     * 调用数据集中的加载数据，就是onListDataSourceReady()方法
     *
     * @param page      页码
     * @param isRefresh 是否是下拉刷新调用
     */
    protected abstract ArrayList<T> load(int page, boolean isRefresh) throws Exception;

    @Override
    public boolean hasMore() {
        return hasMore;
    }

    @Override
    public int getPage() {
        return page;
    }
}
