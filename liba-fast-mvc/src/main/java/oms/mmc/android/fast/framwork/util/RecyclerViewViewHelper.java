package oms.mmc.android.fast.framwork.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.view.View.OnClickListener;

import java.util.ArrayList;

import oms.mmc.android.fast.framwork.async.AsyncTask;
import oms.mmc.android.fast.framwork.base.IDataSource;
import oms.mmc.android.fast.framwork.loadview.ILoadMoreViewFactory;
import oms.mmc.android.fast.framwork.widget.list.ICommonListAdapter;
import oms.mmc.android.fast.framwork.widget.pull.IPullRefreshLayout;
import oms.mmc.android.fast.framwork.widget.pull.IPullRefreshWrapper;
import oms.mmc.factory.load.factory.ILoadViewFactory;
import oms.mmc.helper.ListScrollHelper;
import oms.mmc.helper.adapter.SimpleListScrollAdapter;
import oms.mmc.helper.base.IScrollableAdapterView;

/**
 * RecyclerView帮助类
 */
public class RecyclerViewViewHelper<Model> implements IViewHelper {
    private ICommonListAdapter<Model> mDataAdapter;
    private IPullRefreshWrapper<?> mRefreshWrapper;
    private IDataSource<Model> mDataSource;
    private IScrollableAdapterView mScrollableView;
    private Activity mActivity;
    private OnStateChangeListener<Model> mOnStateChangeListener;
    private AsyncTask<Void, Void, ArrayList<Model>> mAsyncTask;
    private static final long NO_LOAD_DATA = -1;
    private long loadDataTime = NO_LOAD_DATA;
    /**
     * 是否是反转布局，默认为false
     */
    private boolean isReverse = false;
    /**
     * 是否启用加载更多尾部条目
     */
    private boolean enableLoadMoreFooter = true;
    /**
     * 是否还有更多数据。如果服务器返回的数据为空的话，就说明没有更多数据了，也就没必要自动加载更多数据
     */
    private boolean hasMoreData = true;
    private boolean isFirstRefresh = true;
    private boolean isFistLoadMore = true;
    private ILoadViewFactory.ILoadView mLoadView;
    private ILoadMoreViewFactory.ILoadMoreView mLoadMoreView;
    //滚动帮助类
    private ListScrollHelper listScrollHelper;

    public RecyclerViewViewHelper(Activity activity, final IPullRefreshWrapper<?> refreshWrapper, final IScrollableAdapterView scrollableView) {
        this.mActivity = activity;
        this.mRefreshWrapper = refreshWrapper;
        this.mScrollableView = scrollableView;
        this.mRefreshWrapper.setOnRefreshListener(new IPullRefreshWrapper.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });
    }

    public <V extends IScrollableAdapterView> void setupScrollHelper(ListScrollHelper<V> scrollHelper) {
        this.listScrollHelper = scrollHelper;
        listScrollHelper.addListScrollListener(new SimpleListScrollAdapter() {
            @Override
            public void onScrolledUp() {
                super.onScrolledUp();
                TDevice.hideSoftKeyboard((View) getScrollableView());
            }

            @Override
            public void onScrolledDown() {
                super.onScrolledDown();
                TDevice.hideSoftKeyboard((View) getScrollableView());
            }

            @Override
            public void onScrollTop() {
                if (getRefreshWrapper().isCanPullToRefresh() &&
                        getRefreshWrapper().getPullRefreshAbleView().isRefreshEnable()) {
                    //已经滚动到了顶部，可以下拉刷新
                    mRefreshWrapper.setRefreshEnable();
                    //如果是反转的布局（例如是QQ聊天页面），则刷新下一页
                    if (isReverse) {
                        startRefreshWithRefreshLoading();
                    }
                }
            }

            @Override
            public void onScrollBottom() {
                //必须有数据先
                if (getDataSource().hasMore()) {
                    //必须不是刷新状态
                    if (!isRefreshing()) {
                        //正常的布局，滚动到底部自动加载更多，如果是反转布局，是到顶部自动刷新的，是反转布局到底部的时候不做处理
                        if (!isReverse) {
                            mLoadMoreView.showLoading();
                            loadMore();
                        }
                    }
                }
            }
        });
    }

    /**
     * 是否有网络连接
     *
     * @param paramContext
     * @return
     */
    @SuppressLint("MissingPermission")
    public static boolean hasNetwork(Context paramContext) {
        try {
            ConnectivityManager localConnectivityManager = (ConnectivityManager) paramContext.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo localNetworkInfo = localConnectivityManager.getActiveNetworkInfo();
            if ((localNetworkInfo != null) && (localNetworkInfo.isAvailable())) {
                return true;
            }
        } catch (Throwable localThrowable) {
            localThrowable.printStackTrace();
        }
        return false;
    }

    public void init(ILoadViewFactory loadViewFactory, ILoadMoreViewFactory loadMoreViewFactory) {
        this.mLoadView = loadViewFactory.madeLoadView();
        this.mLoadMoreView = loadMoreViewFactory.madeLoadMoreView();
        OnClickListener onClickRefreshListener = new OnClickListener() {

            @Override
            public void onClick(View v) {
                refresh();
            }
        };
        this.mLoadView.init((View) getRefreshWrapper().getPullRefreshAbleView(), onClickRefreshListener);
        this.mLoadMoreView.init(getScrollableView(), onClickRefreshListener, enableLoadMoreFooter);
    }

    /**
     * 刷新，开启异步线程，并且显示加载中的界面，当数据加载完成自动还原成加载完成的布局，并且刷新列表数据
     */
    @Override
    public void refresh() {
        if (mDataAdapter == null || mDataSource == null) {
            if (mRefreshWrapper != null) {
                mRefreshWrapper.completeRefresh();
            }
            return;
        }
        if (mAsyncTask != null && mAsyncTask.getStatus() != AsyncTask.Status.FINISHED) {
            mAsyncTask.cancel(true);
        }
        mAsyncTask = new AsyncTask<Void, Void, ArrayList<Model>>() {

            @Override
            protected void onPreExecute() {
                if (mDataAdapter.isEmpty()) {
                    mLoadView.showLoading();
                } else {
                    mLoadView.restore();
                }
                if (mOnStateChangeListener != null) {
                    mOnStateChangeListener.onStartRefresh(mDataAdapter, isFirstRefresh, isReverse);
                }
            }

            @Override
            protected ArrayList<Model> doInBackground(Void... params) {
                try {
                    return mDataSource.refresh(isReverse);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(ArrayList<Model> result) {
                //返回的数据集为空，异常情况
                if (result == null) {
                    //本次加载之前，列表也没有数据，则显示错误布局
                    if (mDataAdapter.isEmpty()) {
                        mLoadView.showError();
                    } else {
                        //之前有过数据，Toast提示错误
                        mLoadView.tipFail();
                    }
                } else {
                    loadDataTime = System.currentTimeMillis();
                    mDataAdapter.setRefreshListData(result, isReverse, isFirstRefresh);
                    mDataAdapter.notifyDataSetChanged();
                    if (mDataAdapter.isEmpty()) {
                        mLoadView.showEmpty();
                    } else {
                        mLoadView.restore();
                    }
                    hasMoreData = mDataSource.hasMore();
                    if (hasMoreData) {
                        mLoadMoreView.showNormal();
                    } else {
                        //一开始刷新，就没有更多了，直接显示没有更多布局，正常应该是隐藏布局
                        mLoadMoreView.showNoMore();
                    }
                }
                if (mOnStateChangeListener != null) {
                    mOnStateChangeListener.onEndRefresh(mDataAdapter, result, isFirstRefresh, isReverse);
                }
                //刷新结束
                if (getRefreshWrapper().isCanPullToRefresh()) {
                    mRefreshWrapper.setRefreshEnable();
                }
                mRefreshWrapper.completeRefresh();
                if (isFirstRefresh) {
                    isFirstRefresh = false;
                }
            }

        };
        mAsyncTask.execute();
    }

    /**
     * 加载更多，开启异步线程，并且显示加载中的界面，当数据加载完成自动还原成加载完成的布局，并且刷新列表数据
     */
    @Override
    public void loadMore() {
        if (isLoading()) {
            return;
        }
        if (mDataAdapter.isEmpty()) {
            refresh();
            return;
        }
        if (mDataAdapter == null || mDataSource == null) {
            if (mRefreshWrapper != null) {
                mRefreshWrapper.completeRefresh();
            }
            return;
        }
        if (mAsyncTask != null && mAsyncTask.getStatus() != AsyncTask.Status.FINISHED) {
            mAsyncTask.cancel(true);
        }
        mAsyncTask = new AsyncTask<Void, Void, ArrayList<Model>>() {

            @Override
            protected void onPreExecute() {
                if (mOnStateChangeListener != null) {
                    mOnStateChangeListener.onStartLoadMore(mDataAdapter, isFistLoadMore, isReverse);
                }
                mLoadMoreView.showLoading();
            }

            @Override
            protected ArrayList<Model> doInBackground(Void... params) {
                try {
                    return mDataSource.loadMore();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(ArrayList<Model> result) {
                if (result == null) {
                    mLoadView.tipFail();
                    mLoadMoreView.showError();
                } else {
                    mDataAdapter.setLoadMoreListData(result, isReverse, isFistLoadMore);
                    mDataAdapter.notifyDataSetChanged();
                    if (mDataAdapter.isEmpty()) {
                        mLoadView.showEmpty();
                    } else {
                        mLoadView.restore();
                    }
                    hasMoreData = mDataSource.hasMore();
                    if (hasMoreData) {
                        mLoadMoreView.showNormal();
                    } else {
                        mLoadMoreView.showNoMore();
                    }
                }
                if (mOnStateChangeListener != null) {
                    mOnStateChangeListener.onEndLoadMore(mDataAdapter, result, isFistLoadMore, isReverse);
                }
                if (isFistLoadMore) {
                    isFistLoadMore = false;
                }
            }
        };
        mAsyncTask.execute();
    }

    /**
     * 做销毁操作，比如关闭正在加载数据的异步线程等
     */
    public void destroy() {
        if (mAsyncTask != null && mAsyncTask.getStatus() != AsyncTask.Status.FINISHED) {
            mAsyncTask.cancel(true);
            mAsyncTask = null;
        }
    }

    /**
     * 是否正在加载中
     *
     * @return
     */
    public boolean isLoading() {
        return mAsyncTask != null && mAsyncTask.getStatus() != AsyncTask.Status.FINISHED;
    }

    public IScrollableAdapterView getScrollableView() {
        return mScrollableView;
    }

    @Override
    public ILoadViewFactory.ILoadView getLoadView() {
        return mLoadView;
    }

    @Override
    public ILoadMoreViewFactory.ILoadMoreView getLoadMoreView() {
        return mLoadMoreView;
    }

    /**
     * 获取上次刷新数据的时间（数据成功的加载），如果数据没有加载成功过，那么返回-1
     */
    public long getLoadDataTime() {
        return loadDataTime;
    }

    /**
     * 是否加载过数据
     *
     * @return true代表成功加载过，false代表未加载成功过数据
     */
    public boolean isLoadDataed() {
        return loadDataTime != NO_LOAD_DATA;
    }

    public OnStateChangeListener<Model> getOnStateChangeListener() {
        return mOnStateChangeListener;
    }

    /**
     * 设置状态监听，监听开始刷新，刷新成功，开始加载更多，加载更多成功
     */
    public void setOnStateChangeListener(OnStateChangeListener<Model> onStateChangeListener) {
        this.mOnStateChangeListener = onStateChangeListener;
    }

    public ICommonListAdapter<Model> getAdapter() {
        return mDataAdapter;
    }

    /**
     * 设置适配器，用于显示数据
     *
     * @param adapter
     */
    public void setAdapter(ICommonListAdapter<Model> adapter) {
        getListScrollHelper().getScrollableViewWrapper().setAdapter(adapter);
        this.mDataAdapter = adapter;
    }

    public IDataSource<Model> getDataSource() {
        return mDataSource;
    }

    public RecyclerViewViewHelper getRecyclerViewViewHelper() {
        return this;
    }

    /**
     * 设置数据源，用于加载数据
     *
     * @param dataSource
     */
    public void setDataSource(IDataSource<Model> dataSource) {
        this.mDataSource = dataSource;
    }

    public IPullRefreshWrapper<?> getRefreshWrapper() {
        return mRefreshWrapper;
    }

    public IPullRefreshLayout getRefreshLayout() {
        return mRefreshWrapper.getPullRefreshAbleView();
    }

    /**
     * 开始刷新，同时让SwipeRefreshLayout显示刷新
     */
    public void startRefreshWithRefreshLoading() {
        mRefreshWrapper.startRefreshWithAnimation();
    }

    public boolean isRefreshing() {
        return mRefreshWrapper.isRefurbishing();
    }

    /**
     * 是否有下一页
     */
    public boolean isHasMoreData() {
        return hasMoreData;
    }

    @Override
    public Activity getActivity() {
        return mActivity;
    }

    /**
     * 设置是否可以下拉刷新
     */
    public void setCanPullToRefresh(boolean canPullToRefresh) {
        if (canPullToRefresh) {
            getRefreshWrapper().setCanPullToRefresh();
        } else {
            getRefreshWrapper().setNotPullToRefresh();
        }
    }

    /**
     * 是否可以下拉刷新
     */
    public boolean isCanPullToRefresh() {
        return getRefreshWrapper().isCanPullToRefresh();
    }

    /**
     * 当前是否是反转布局
     */
    public boolean isReverse() {
        return isReverse;
    }

    /**
     * 设置是否反转布局
     */
    public void setReverse(boolean reverse) {
        isReverse = reverse;
    }

    /**
     * 是否存在加载更多尾部
     *
     * @return
     */
    public boolean isEnableLoadMoreFooter() {
        return enableLoadMoreFooter;
    }

    /**
     * 设置是否启用加载更多尾部
     */
    public void setEnableLoadMoreFooter(boolean enableLoadMoreFooter) {
        this.enableLoadMoreFooter = enableLoadMoreFooter;
        mLoadMoreView.enableLoadMoreFooter(enableLoadMoreFooter);
    }

    public ListScrollHelper getListScrollHelper() {
        return listScrollHelper;
    }
}