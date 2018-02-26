package oms.mmc.android.fast.framwork.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView.OnScrollListener;

import java.util.ArrayList;

import oms.mmc.android.fast.framwork.adapter.SimpleAttachStateChangeListener;
import oms.mmc.android.fast.framwork.base.BaseListAdapter;
import oms.mmc.android.fast.framwork.widget.rv.base.BaseTpl;
import oms.mmc.android.fast.framwork.widget.view.ListScrollHelper;
import oms.mmc.android.fast.framwork.widget.view.adapter.SimpleListScrollAdapter;

/**
 * ListView帮助类
 */
public class RecyclerViewViewHelper<Model> implements IViewHelper {
    private IDataAdapter<ArrayList<Model>, BaseTpl.ViewHolder> dataAdapter;
    private SwipeRefreshLayout refreshLayout;
    private IDataSource<Model> dataSource;
    private RecyclerView mRecyclerView;
    private Context context;
    private OnStateChangeListener<ArrayList<Model>> onStateChangeListener;
    private AsyncTask<Void, Void, ArrayList<Model>> asyncTask;
    private static final long NO_LOAD_DATA = -1;
    private long loadDataTime = NO_LOAD_DATA;
    private ArrayList<RecyclerView.OnScrollListener> mScrollListeners;
    /**
     * 是否可以下拉刷新
     */
    private boolean isCanPullToRefresh = true;
    private boolean isReverse;
    /**
     * 是否还有更多数据。如果服务器返回的数据为空的话，就说明没有更多数据了，也就没必要自动加载更多数据
     */
    private boolean hasMoreData = true;
    private boolean isFirstRefresh = true;
    private boolean isFistLoadMore = true;
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
    private ListScrollHelper listScrollHelper;
    private final Handler mUiHandler;
    private ILoadViewFactory.ILoadMoreView mLoadMoreView;

    public RecyclerViewViewHelper(final SwipeRefreshLayout refreshLayout, final RecyclerView recyclerView) {
        super();
        helperHashCode = getRecyclerViewViewHelper().hashCode();
        this.context = refreshLayout.getContext().getApplicationContext();
        mUiHandler = new Handler(Looper.getMainLooper());
        //刷新布局
        this.refreshLayout = refreshLayout;
        this.mRecyclerView = recyclerView;
        this.refreshLayout.setEnabled(false);
        this.mRecyclerView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        this.refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });
        mRecyclerView.addOnAttachStateChangeListener(new SimpleAttachStateChangeListener() {
            @Override
            public void onViewDetachedFromWindow(View v) {

            }
        });
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (mScrollListeners != null) {
                    for (RecyclerView.OnScrollListener listener : mScrollListeners) {
                        listener.onScrollStateChanged(recyclerView, newState);
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (mScrollListeners != null) {
                    for (RecyclerView.OnScrollListener listener : mScrollListeners) {
                        listener.onScrolled(recyclerView, dx, dy);
                    }
                }
            }
        });
    }

    public void setupScrollHelper(ListScrollHelper scrollHelper) {
        this.listScrollHelper = scrollHelper;
        listScrollHelper.addListScrollListener(new SimpleListScrollAdapter() {
            @Override
            public void onScrollTop() {
                if (isCanPullToRefresh) {
                    if (!refreshLayoutIsEnabled) {
                        //已经滚动到了顶部，可以下拉刷新
                        refreshLayout.setEnabled(true);
                    }
                }
            }

            @Override
            public void onScrollBottom() {
                //必须有数据先
                if (getDataSource().hasMore()) {
                    //必须不是刷新状态
                    if (!isRefreshing()) {
                        //必须网络可用才能进行加载更多，没有网络直接显示失败了
                        if (RecyclerViewViewHelper.hasNetwork(context)) {
                            mLoadMoreView.showLoading();
                            loadMore();
                        } else {
                            if (!isLoading()) {
                                mLoadMoreView.showFail();
                            }
                        }
                    }
                }
            }
        });
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
        mLoadMoreView = loadViewFactory.madeLoadMoreView();
        mLoadView.init(getRefreshLayout(), onClickRefreshListener);
        mLoadMoreView.init(getRecyclerView(), onClickRefreshListener);
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
                    onStateChangeListener.onStartRefresh(dataAdapter, isFirstRefresh, isReverse);
                }
            }

            @Override
            protected ArrayList<Model> doInBackground(Void... params) {
                try {
                    return dataSource.refresh(isReverse);
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
                    dataAdapter.setRefreshListViewData(result, isReverse, isFirstRefresh);
                    dataAdapter.notifyDataSetChanged();
                    if (dataAdapter.isEmpty()) {
                        mLoadView.showEmpty();
                    } else {
                        mLoadView.restore();
                    }
                    hasMoreData = dataSource.hasMore();
                }
                if (onStateChangeListener != null) {
                    onStateChangeListener.onEndRefresh(dataAdapter, result, isFirstRefresh, isReverse);
                }
                //刷新结束
                if (isCanPullToRefresh) {
                    refreshLayout.setEnabled(true);
                }
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
                    onStateChangeListener.onStartLoadMore(dataAdapter, isFistLoadMore, isReverse);
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
                    dataAdapter.setLoadMoreListViewData(result, isReverse, isFistLoadMore);
                    dataAdapter.notifyDataSetChanged();
                    if (dataAdapter.isEmpty()) {
                        mLoadView.showEmpty();
                    } else {
                        mLoadView.restore();
                    }
                    hasMoreData = dataSource.hasMore();
                    if (hasMoreData) {
                        mLoadMoreView.showNoMore();
                    } else {
                        mLoadMoreView.showNoMore();
                    }
                }
                if (onStateChangeListener != null) {
                    onStateChangeListener.onEndLoadMore(dataAdapter, result, isFistLoadMore, isReverse);
                }
                if (isFistLoadMore) {
                    isFistLoadMore = false;
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

    /**
     * 是否加载过数据
     *
     * @return true代表成功加载过，false代表未加载成功过数据
     */
    public boolean isLoadDataed() {
        return loadDataTime != NO_LOAD_DATA;
    }

    public OnStateChangeListener<ArrayList<Model>> getOnStateChangeListener() {
        return onStateChangeListener;
    }

    /**
     * 设置状态监听，监听开始刷新，刷新成功，开始加载更多，加载更多成功
     */
    public void setOnStateChangeListener(OnStateChangeListener<ArrayList<Model>> onStateChangeListener) {
        this.onStateChangeListener = onStateChangeListener;
    }

    public IDataAdapter<ArrayList<Model>, BaseTpl.ViewHolder> getAdapter() {
        return dataAdapter;
    }

    /**
     * 设置适配器，用于显示数据
     *
     * @param adapter
     */
    public void setAdapter(IDataAdapter<ArrayList<Model>, BaseTpl.ViewHolder> adapter) {
        mRecyclerView.setAdapter(((BaseListAdapter) adapter).getHeaderFooterAdapter());
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

    public void startRefresh() {
        mUiHandler.post(new Runnable() {
            @Override
            public void run() {
                getRefreshLayout().setRefreshing(true);
                refresh();
            }
        });
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

    /**
     * 是否有下一页
     */
    public boolean isHasMoreData() {
        return hasMoreData;
    }

    /**
     * 设置是否可以下拉刷新
     */
    public void setCanPullToRefresh(boolean canPullToRefresh) {
        isCanPullToRefresh = canPullToRefresh;
        if (isCanPullToRefresh) {
            refreshLayoutIsEnabled = true;
            refreshLayout.setEnabled(true);
        } else {
            refreshLayoutIsEnabled = false;
            //不允许下拉刷新，直接禁用
            refreshLayout.setEnabled(false);
        }
    }

    public boolean isReverse() {
        return isReverse;
    }

    public void setReverse(boolean reverse) {
        isReverse = reverse;
    }
}