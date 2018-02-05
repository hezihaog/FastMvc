package oms.mmc.android.fast.framwork.widget.pulltorefresh.helper;

import android.annotation.SuppressLint;
import android.content.Context;
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

/**
 * ListView帮助类
 */
public class RecyclerViewViewHelper<Model> implements IViewHelper, SwipeRefreshLayout.OnRefreshListener {
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
     * 当前滚动状态
     */
    private int scrollState;
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

    public RecyclerViewViewHelper(final SwipeRefreshLayout refreshLayout, RecyclerView recyclerView) {
        super();
        this.context = refreshLayout.getContext().getApplicationContext();
        //刷新布局
        this.refreshLayout = refreshLayout;
        this.mRecyclerView = recyclerView;
        this.mRecyclerView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        this.refreshLayout.setOnRefreshListener(this);

        this.mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                RecyclerViewViewHelper.this.scrollState = newState;
                for (int i = 0; mScrollListeners != null && i < mScrollListeners.size(); i++) {
                    RecyclerView.OnScrollListener scrollListener = mScrollListeners.get(i);
                    if (scrollListener != null) {
                        scrollListener.onScrollStateChanged(recyclerView, scrollState);
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (isCanPullToRefresh) {
                    //RecyclerView.canScrollVertically(-1)的值表示是否能向下滚动，false表示已经滚动到顶部
                    if (!recyclerView.canScrollVertically(-1)) {
                        //已经滚动到了顶部，可以下拉刷新
                        refreshLayout.setEnabled(true);
                    } else {
                        //否则禁用下拉刷新
                        refreshLayout.setEnabled(false);
                    }
                } else {
                    //不允许下拉刷新，直接禁用
                    refreshLayout.setEnabled(false);
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

    public int getScrollState() {
        return scrollState;
    }

    public boolean isFlingState() {
        return scrollState == OnScrollListener.SCROLL_STATE_FLING;
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
    public void loadMore(final ILoadViewFactory.ILoadMoreView mLoadMoreView) {
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
                mLoadMoreView.showLoading();
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
                    mLoadMoreView.showFail();
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
                        mLoadMoreView.showNormal();
                    } else {
                        mLoadMoreView.showNoMore();
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

    @Override
    public void onRefresh() {
        refresh();
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