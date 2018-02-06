package oms.mmc.android.fast.framwork.widget.pulltorefresh.helper;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView.OnScrollListener;

import java.util.ArrayList;

import oms.mmc.android.fast.framwork.util.LocalBroadcastHelper;

/**
 * ListView帮助类
 */
public class RecyclerViewViewHelper<Model> implements IViewHelper {
    private IDataAdapter<ArrayList<Model>> dataAdapter;
    private SwipeRefreshLayout refreshLayout;
    private IDataSource<Model> dataSource;
    private RecyclerView mRecyclerView;
    private Context context;
    private OnStateChangeListener<ArrayList<Model>> onStateChangeListener;
    private AsyncTask<Void, Void, ArrayList<Model>> asyncTask;
    private long loadDataTime = -1;
    private ArrayList<RecyclerView.OnScrollListener> mScrollListeners;
    /**
     * 是否可以下拉刷新
     */
    private boolean isCanPullToRefresh = true;
    /**
     * 是否还有更多数据。如果服务器返回的数据为空的话，就说明没有更多数据了，也就没必要自动加载更多数据
     */
    private boolean hasMoreData = true;
    private boolean isFirstRefresh = false;
    private boolean isFistLoaderMore = false;
    private ILoadViewFactory.ILoadView mLoadView;
    private OnClickListener onClickRefreshListener = new OnClickListener() {

        @Override
        public void onClick(View v) {
            refresh();
        }
    };
    //refreshLayout当前是否可用，用于监听rv的Scroll滚动时不重复调用swipe的setEnabled
    private boolean refreshLayoutIsEnabled = true;
    //RecyclerViewViewHelper的哈希值，用于加载更多时发送广播的判断标志
    private final int helperHashCode;

    public RecyclerViewViewHelper(final SwipeRefreshLayout refreshLayout, RecyclerView recyclerView) {
        super();
        helperHashCode = getRecyclerViewViewHelper().hashCode();
        this.context = refreshLayout.getContext().getApplicationContext();
        //刷新布局
        this.refreshLayout = refreshLayout;
        this.mRecyclerView = recyclerView;
        this.mRecyclerView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        this.refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });
        //滚动到底部自动加载更多数据
        this.mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (getDataSource().hasMore()) {
                    if (!isRefreshing()) {//如果不是刷新状态
                        //如果滚动到最后一行，RecyclerView.canScrollVertically(1)的值表示是否能向上滚动，false表示已经滚动到底部
                        if (newState == RecyclerView.SCROLL_STATE_IDLE &&
                                !recyclerView.canScrollVertically(1)) {
                            //必须网络可用才能进行加载更多，没有网络直接显示失败了
                            if (RecyclerViewViewHelper.hasNetwork(context)) {
                                sendLoadMoreState(ILoadViewFactory.ILoadMoreView.LOADING);
                                loadMore();
                            } else {
                                if (!isLoading()) {
                                    sendLoadMoreState(ILoadViewFactory.ILoadMoreView.FAIL);
                                }
                            }
                        }
                    }
                }
                for (int i = 0; mScrollListeners != null && i < mScrollListeners.size(); i++) {
                    RecyclerView.OnScrollListener scrollListener = mScrollListeners.get(i);
                    if (scrollListener != null) {
                        scrollListener.onScrollStateChanged(recyclerView, newState);
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (isCanPullToRefresh) {
                    //RecyclerView.canScrollVertically(-1)的值表示是否能向下滚动，false表示已经滚动到顶部
                    if (!recyclerView.canScrollVertically(-1)) {
                        if (!refreshLayoutIsEnabled) {
                            //已经滚动到了顶部，可以下拉刷新
                            refreshLayout.setEnabled(true);
                        }
                    } else {
                        if (!refreshLayoutIsEnabled) {
                            //否则禁用下拉刷新
                            refreshLayout.setEnabled(false);
                            refreshLayoutIsEnabled = false;
                        }
                    }
                } else {
                    refreshLayoutIsEnabled = false;
                    if (!refreshLayoutIsEnabled) {
                        //不允许下拉刷新，直接禁用
                        refreshLayout.setEnabled(false);
                    }
                }
                for (int i = 0; mScrollListeners != null && i < mScrollListeners.size(); i++) {
                    RecyclerView.OnScrollListener scrollListener = mScrollListeners.get(i);
                    if (scrollListener != null) {
                        scrollListener.onScrolled(recyclerView, dx, dy);
                    }
                }
            }
        });
    }

    private void sendLoadMoreState(int state) {
        Intent intent = new Intent();
        intent.putExtra(ILoadViewFactory.ILoadMoreView.BUNDLE_KEY_HELPER_HASH, helperHashCode);
        intent.putExtra(ILoadViewFactory.ILoadMoreView.BUNDLE_KEY_STATE, state);
        LocalBroadcastHelper.sendLoadMore(context, intent);
    }

    public int getScrollState() {
        return mRecyclerView.getScrollState();
    }

    public boolean isFlingState() {
        return mRecyclerView.getScrollState() == OnScrollListener.SCROLL_STATE_FLING;
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
            if ((localNetworkInfo != null) && (localNetworkInfo.isAvailable()))
                return true;
        } catch (Throwable localThrowable) {
            localThrowable.printStackTrace();
        }
        return false;
    }

    public void init(ILoadViewFactory loadViewFactory) {
        this.mLoadView = loadViewFactory.madeLoadView();
        mLoadView.init(mRecyclerView, onClickRefreshListener);
    }

    /**
     * 刷新，开启异步线程，并且显示加载中的界面，当数据加载完成自动还原成加载完成的布局，并且刷新列表数据
     */
    @Override
    public void refresh() {
        if (dataAdapter == null || dataSource == null) {
            if (refreshLayout != null) {
                refreshLayout.setRefreshing(false);
            }
            return;
        }
        if (asyncTask != null && asyncTask.getStatus() != AsyncTask.Status.FINISHED) {
            asyncTask.cancel(true);
        }
        asyncTask = new AsyncTask<Void, Void, ArrayList<Model>>() {

            @Override
            protected void onPreExecute() {
                if (dataAdapter.isEmpty()) {
                    mLoadView.showLoading();
                    refreshLayout.setRefreshing(false);
                } else {
                    mLoadView.restore();
                }
                if (onStateChangeListener != null) {
                    onStateChangeListener.onStartRefresh(dataAdapter, isFirstRefresh);
                }
            }

            @Override
            protected ArrayList<Model> doInBackground(Void... params) {
                try {
                    return dataSource.refresh();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(ArrayList<Model> result) {
                if (result == null) {
                    if (dataAdapter.isEmpty()) {
                        if (hasNetwork(context)) {
                            mLoadView.showEmpty();
                        } else {
                            mLoadView.showFail();
                        }
                    } else {
                        mLoadView.tipFail();
                    }
                } else {
                    loadDataTime = System.currentTimeMillis();
                    dataAdapter.setListViewData(result, true);
                    dataAdapter.notifyDataSetChanged();
                    if (dataAdapter.isEmpty()) {
                        mLoadView.showEmpty();
                    } else {
                        mLoadView.restore();
                    }
                    hasMoreData = dataSource.hasMore();
                }
                if (onStateChangeListener != null) {
                    onStateChangeListener.onEndRefresh(dataAdapter, result, isFirstRefresh);
                }
                //刷新结束
                refreshLayout.setRefreshing(false);
                if (isFirstRefresh) {
                    isFirstRefresh = false;
                }
            }

        };
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            asyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        } else {
            asyncTask.execute();
        }
    }

    /**
     * 加载更多，开启异步线程，并且显示加载中的界面，当数据加载完成自动还原成加载完成的布局，并且刷新列表数据
     */
    public void loadMore() {
        if (isLoading()) {
            return;
        }
        if (dataAdapter.isEmpty()) {
            refresh();
            return;
        }
        if (dataAdapter == null || dataSource == null) {
            if (refreshLayout != null) {
                refreshLayout.setRefreshing(false);
            }
            return;
        }
        if (asyncTask != null && asyncTask.getStatus() != AsyncTask.Status.FINISHED) {
            asyncTask.cancel(true);
        }
        asyncTask = new AsyncTask<Void, Void, ArrayList<Model>>() {

            @Override
            protected void onPreExecute() {
                if (onStateChangeListener != null) {
                    onStateChangeListener.onStartLoadMore(dataAdapter, isFistLoaderMore);
                }
                sendLoadMoreState(ILoadViewFactory.ILoadMoreView.LOADING);
            }

            @Override
            protected ArrayList<Model> doInBackground(Void... params) {
                try {
                    return dataSource.loadMore();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(ArrayList<Model> result) {
                if (result == null) {
                    mLoadView.tipFail();
                    sendLoadMoreState(ILoadViewFactory.ILoadMoreView.FAIL);
                } else {
                    dataAdapter.setListViewData(result, false);
                    dataAdapter.notifyDataSetChanged();
                    if (dataAdapter.isEmpty()) {
                        mLoadView.showEmpty();
                    } else {
                        mLoadView.restore();
                    }
                    hasMoreData = dataSource.hasMore();
                    if (hasMoreData) {
                        sendLoadMoreState(ILoadViewFactory.ILoadMoreView.NO_MORE);
                    } else {
                        sendLoadMoreState(ILoadViewFactory.ILoadMoreView.NO_MORE);
                    }
                }
                if (onStateChangeListener != null) {
                    onStateChangeListener.onEndLoadMore(dataAdapter, result, isFistLoaderMore);
                }
                if (isFistLoaderMore) {
                    isFistLoaderMore = false;
                }
            }
        };

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            asyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        } else {
            asyncTask.execute();
        }
    }

    /**
     * 做销毁操作，比如关闭正在加载数据的异步线程等
     */
    public void destroy() {
        if (asyncTask != null && asyncTask.getStatus() != AsyncTask.Status.FINISHED) {
            asyncTask.cancel(true);
            asyncTask = null;
        }
    }

    /**
     * 是否正在加载中
     *
     * @return
     */
    public boolean isLoading() {
        return asyncTask != null && asyncTask.getStatus() != AsyncTask.Status.FINISHED;
    }

    public RecyclerView getRecyclerView() {
        return mRecyclerView;
    }

    @Override
    public ILoadViewFactory.ILoadView getLoadView() {
        return mLoadView;
    }

    /**
     * 获取上次刷新数据的时间（数据成功的加载），如果数据没有加载成功过，那么返回-1
     */
    public long getLoadDataTime() {
        return loadDataTime;
    }

    public OnStateChangeListener<ArrayList<Model>> getOnStateChangeListener() {
        return onStateChangeListener;
    }

    /**
     * 设置状态监听，监听开始刷新，刷新成功，开始加载更多，加载更多成功
     *
     * @param onStateChangeListener
     */
    public void setOnStateChangeListener(OnStateChangeListener<ArrayList<Model>> onStateChangeListener) {
        this.onStateChangeListener = onStateChangeListener;
    }

    public IDataAdapter<ArrayList<Model>> getAdapter() {
        return dataAdapter;
    }

    /**
     * 设置适配器，用于显示数据
     *
     * @param adapter
     */
    public void setAdapter(IDataAdapter<ArrayList<Model>> adapter) {
        mRecyclerView.setAdapter((RecyclerView.Adapter) adapter);
        this.dataAdapter = adapter;
    }

    public IDataSource<Model> getDataSource() {
        return dataSource;
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
        this.dataSource = dataSource;
    }

    public SwipeRefreshLayout getRefreshLayout() {
        return refreshLayout;
    }

    public boolean isRefreshing() {
        return refreshLayout.isRefreshing();
    }

    public void addOnScrollListener(RecyclerView.OnScrollListener onScrollListener) {
        if (mScrollListeners == null) {
            mScrollListeners = new ArrayList<RecyclerView.OnScrollListener>();
        }
        mScrollListeners.add(onScrollListener);
    }

    public void removeOnScrollListener(RecyclerView.OnScrollListener onScrollListener) {
        if (mScrollListeners != null && onScrollListener != null) {
            mScrollListeners.remove(onScrollListener);
        }
    }

    public boolean isHasMoreData() {
        return hasMoreData;
    }

    /**
     * 设置是否可以下拉刷新
     */
    public void setCanPullToRefresh(boolean canPullToRefresh) {
        isCanPullToRefresh = canPullToRefresh;
    }
}