package oms.mmc.android.fast.framwork.widget.pulltorefresh.helper;

import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ListView;

import java.util.ArrayList;

import oms.mmc.android.fast.framwork.widget.pulltorefresh.PullToRefreshBase;

public class ListViewHelper<Model> implements PullToRefreshBase.OnRefreshListener<ListView>, IViewHelper {
    private IDataAdapter<ArrayList<Model>> dataAdapter;
    private PullToRefreshBase<ListView> plv;
    private IDataSource<Model> dataSource;
    private ListView mListView;
    private Context context;
    private OnStateChangeListener<ArrayList<Model>> onStateChangeListener;
    private AsyncTask<Void, Void, ArrayList<Model>> asyncTask;
    private long loadDataTime = -1;
    //    private OnScrollListener mScrollListener;
    private ArrayList<OnScrollListener> mScrollListeners;
    private boolean isReverse;
    //当前滚动状态
    private int scrollState;

    /**
     * 是否还有更多数据。如果服务器返回的数据为空的话，就说明没有更多数据了，也就没必要自动加载更多数据
     */
    private boolean hasMoreData = true;
    private ILoadViewFactory.ILoadView mLoadView;
    private ILoadViewFactory.ILoadMoreView mLoadMoreView;
    private boolean autoLoadMore = true;
    private OnClickListener onClickLoadMoreListener = new OnClickListener() {

        @Override
        public void onClick(View v) {
            loadMore();
        }
    };
    private OnClickListener onClickRefresListener = new OnClickListener() {

        @Override
        public void onClick(View v) {
            refresh();
        }
    };

    public ListViewHelper(PullToRefreshBase pullToRefreshAdapterViewBase) {
        super();
        this.context = pullToRefreshAdapterViewBase.getContext().getApplicationContext();
        this.autoLoadMore = true;
        this.plv = pullToRefreshAdapterViewBase;
        mListView = plv.getRefreshableView();
        mListView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        mListView.setCacheColorHint(Color.TRANSPARENT);
        plv.setScrollLoadEnabled(true);
        plv.setOnRefreshListener(this);
        /*
      滚动到底部自动加载更多数据
     */
        OnScrollListener onScrollListener = new OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView listView, int scrollState) {
                ListViewHelper.this.scrollState = scrollState;
                //fling时取消请求
                if (scrollState == SCROLL_STATE_FLING) {
                    //ImageLoader.getActualLoader().pause();
                } else {
                    //ImageLoader.getActualLoader().resume();
                }

                if (autoLoadMore) {
                    if (hasMoreData && !isReverse) {
                        if (!plv.isPullRefreshing()) {// 如果不是刷新状态

                            if (scrollState == OnScrollListener.SCROLL_STATE_IDLE && listView.getLastVisiblePosition() + 1 == listView.getCount()) {// 如果滚动到最后一行

                                // 如果网络可以用

                                if (hasNetwork(context)) {
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
                }
                for (int i = 0; mScrollListeners != null && i < mScrollListeners.size(); i++) {
                    OnScrollListener mScrollListener = mScrollListeners.get(i);
                    if (mScrollListener != null) {
                        mScrollListener.onScrollStateChanged(listView, scrollState);
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                for (int i = 0; mScrollListeners != null && i < mScrollListeners.size(); i++) {
                    OnScrollListener mScrollListener = mScrollListeners.get(i);
                    if (mScrollListener != null) {
                        mScrollListener.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
                    }
                }
            }
        };
        mListView.setOnScrollListener(onScrollListener);
        /*
      针对于电视 选择到了底部项的时候自动加载更多数据
     */
        OnItemSelectedListener onItemSelectedListener = new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> listView, View view, int position, long id) {
                if (autoLoadMore) {
                    if (hasMoreData) {
                        if (listView.getLastVisiblePosition() + 1 == listView.getCount()) {// 如果滚动到最后一行
                            // 如果网络可以用
                            if (hasNetwork(context)) {
                                loadMore();
                            }
                        }
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        };
        mListView.setOnItemSelectedListener(onItemSelectedListener);
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

    /**
     * {@link android.Manifest.permission#ACCESS_NETWORK_STATE}.
     *
     * @param context
     * @return
     */
    public static boolean isWifi(Context context) {
        ConnectivityManager connectMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connectMgr.getActiveNetworkInfo();
        return info != null && info.getType() == ConnectivityManager.TYPE_WIFI;
    }

    public void init(ILoadViewFactory loadViewFactory) {
        this.mLoadView = loadViewFactory.madeLoadView();
        this.mLoadMoreView = loadViewFactory.madeLoadMoreView();
        mLoadView.init(mListView, onClickRefresListener);
        mLoadMoreView.init(mListView, onClickLoadMoreListener);
    }

    /**
     * 刷新，开启异步线程，并且显示加载中的界面，当数据加载完成自动还原成加载完成的布局，并且刷新列表数据
     */
    public void refresh() {
        if (dataAdapter == null || dataSource == null) {
            if (plv != null) {
                plv.onPullUpRefreshComplete();
            }
            return;
        }
        if (asyncTask != null && asyncTask.getStatus() != AsyncTask.Status.FINISHED) {
            asyncTask.cancel(true);
        }
        asyncTask = new AsyncTask<Void, Void, ArrayList<Model>>() {
            protected void onPreExecute() {
                mLoadMoreView.showNormal();
                if (dataAdapter.isEmpty() && mListView.getHeaderViewsCount() == 0) {
                    mLoadView.showLoading();
                    plv.onPullUpRefreshComplete();
                } else {
                    mLoadView.restore();
                }
                if (onStateChangeListener != null) {
                    onStateChangeListener.onStartRefresh(dataAdapter);
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

            protected void onPostExecute(ArrayList<Model> result) {
                if (result == null) {
                    if (dataAdapter.isEmpty() && mListView.getHeaderViewsCount() == 0) {
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
                    dataAdapter.setListViewData(result, true, false);
                    dataAdapter.notifyDataSetChanged();
                    if (dataAdapter.isEmpty() && mListView.getHeaderViewsCount() == 0) {
                        mLoadView.showEmpty();
                    } else {
                        mLoadView.restore();

                    }
                    hasMoreData = dataSource.hasMore();
                    if (hasMoreData) {
                        mLoadMoreView.showNormal();
                    } else {
                        mLoadMoreView.showNomore();
                    }
                }
                if (onStateChangeListener != null) {
                    onStateChangeListener.onEndRefresh(dataAdapter, result);
                }

                plv.onPullDownRefreshComplete();
                plv.onPullUpRefreshComplete();
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
            if (plv != null) {
                plv.onPullUpRefreshComplete();
            }
            return;
        }
        if (asyncTask != null && asyncTask.getStatus() != AsyncTask.Status.FINISHED) {
            asyncTask.cancel(true);
        }
        asyncTask = new AsyncTask<Void, Void, ArrayList<Model>>() {
            protected void onPreExecute() {
                if (onStateChangeListener != null) {
                    onStateChangeListener.onStartLoadMore(dataAdapter);
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

            protected void onPostExecute(ArrayList<Model> result) {
                if (result == null) {
                    mLoadView.tipFail();
                    mLoadMoreView.showFail();
                } else {
                    dataAdapter.setListViewData(result, false, isReverse);
                    dataAdapter.notifyDataSetChanged();
                    if (dataAdapter.isEmpty() && mListView.getHeaderViewsCount() == 0) {
                        mLoadView.showEmpty();
                    } else {
                        mLoadView.restore();
                    }
                    hasMoreData = dataSource.hasMore();
                    if (hasMoreData && !isReverse) {
                        mLoadMoreView.showNormal();
                    } else {
                        mLoadMoreView.showNomore();
                    }
                }
                if (onStateChangeListener != null) {
                    onStateChangeListener.onEndLoadMore(dataAdapter, result);
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
    public void destory() {
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

    public ListView getListView() {
        return plv.getRefreshableView();
    }

    /**
     * 获取上次刷新数据的时间（数据成功的加载），如果数据没有加载成功过，那么返回-1
     *
     * @return
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
        mListView.setAdapter(adapter);
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

    public PullToRefreshBase<ListView> getPullToRefreshAdapterViewBase() {
        return plv;
    }

    @Override
    public ILoadViewFactory.ILoadView getLoadView() {
        return mLoadView;
    }

    @Override
    public ILoadViewFactory.ILoadMoreView getLoadMoreView() {
        return mLoadMoreView;
    }

    public boolean isAutoLoadMore() {
        return autoLoadMore;
    }

    public void setAutoLoadMore(boolean autoLoadMore) {
        this.autoLoadMore = autoLoadMore;
        if (!isLoading()) {
            mLoadMoreView.showNormal();
        }
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
        refresh();
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
        loadMore();
    }

    public void doPullRefreshing(final boolean smoothScroll, final long delayMillis) {
        plv.doPullRefreshing(smoothScroll, delayMillis);
    }

    public void doPullRefreshing() {
        plv.doPullRefreshing(true, 300);
    }

    public void addOnScrollListener(OnScrollListener onScrollListener) {
        if (mScrollListeners == null) {
            mScrollListeners = new ArrayList<OnScrollListener>();
        }
        mScrollListeners.add(onScrollListener);
    }

    public void removeOnScrollListener(OnScrollListener onScrollListener) {
        if (mScrollListeners != null && onScrollListener != null) {
            mScrollListeners.remove(onScrollListener);
        }
    }

    public boolean isReverse() {
        return isReverse;
    }

    public void setReverse(boolean reverse) {
        isReverse = reverse;
    }
}