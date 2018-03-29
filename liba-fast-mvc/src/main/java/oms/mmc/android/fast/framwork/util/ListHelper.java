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
 * List列表帮助类
 */
public class ListHelper<Model> implements IViewHelper<Model> {
    /**
     * 没有加载过数据的标志
     */
    private static final long NO_LOAD_DATA = -1;
    /**
     * 列表控件设置的适配器
     */
    private ICommonListAdapter<Model> mDataAdapter;
    /**
     * 下拉刷新布局包裹类
     */
    private IPullRefreshWrapper<?> mRefreshWrapper;
    /**
     * 列表数据集
     */
    private IDataSource<Model> mDataSource;
    /**
     * 滚动控件类
     */
    private IScrollableAdapterView mScrollableView;
    /**
     * 界面实现类
     */
    private Activity mActivity;
    /**
     * 加载状态监听器
     */
    private OnLoadStateChangeListener<Model> mOnLoadStateChangeListener;
    /**
     * 数据加载任务
     */
    private AsyncTask<Void, Void, ArrayList<Model>> mAsyncTask;
    /**
     * 最后一次加载数据的时间
     */
    private long lastLoadDataTime = NO_LOAD_DATA;
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
    /**
     * 是否是第一次刷新
     */
    private boolean isFirstRefresh = true;
    /**
     * 是否是第一次加载更多
     */
    private boolean isFistLoadMore = true;
    /**
     * 界面加载状态布局工厂
     */
    private ILoadViewFactory.ILoadView mLoadView;
    /**
     * 加载更多尾部工厂
     */
    private ILoadMoreViewFactory.ILoadMoreView mLoadMoreView;
    /**
     * 滚动帮助类
     */
    private ListScrollHelper listScrollHelper;

    public ListHelper(Activity activity, final IPullRefreshWrapper<?> refreshWrapper, final IScrollableAdapterView scrollableView) {
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
                if (mOnLoadStateChangeListener != null) {
                    mOnLoadStateChangeListener.onStartRefresh(mDataAdapter, isFirstRefresh, isReverse);
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
                    lastLoadDataTime = System.currentTimeMillis();
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
                if (mOnLoadStateChangeListener != null) {
                    mOnLoadStateChangeListener.onEndRefresh(mDataAdapter, result, isFirstRefresh, isReverse);
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
                if (mOnLoadStateChangeListener != null) {
                    mOnLoadStateChangeListener.onStartLoadMore(mDataAdapter, isFistLoadMore, isReverse);
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
                if (mOnLoadStateChangeListener != null) {
                    mOnLoadStateChangeListener.onEndLoadMore(mDataAdapter, result, isFistLoadMore, isReverse);
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
     */
    @Override
    public boolean isLoading() {
        return mAsyncTask != null && mAsyncTask.getStatus() != AsyncTask.Status.FINISHED;
    }

    @Override
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

    @Override
    public long getLastLoadTime() {
        return lastLoadDataTime;
    }

    /**
     * 是否加载过数据
     *
     * @return true代表成功加载过，false代表未加载成功过数据
     */
    @Override
    public boolean isLoaded() {
        return lastLoadDataTime != NO_LOAD_DATA;
    }

    public OnLoadStateChangeListener<Model> getOnLoadStateChangeListener() {
        return mOnLoadStateChangeListener;
    }

    /**
     * 设置状态监听，监听开始刷新，刷新成功，开始加载更多，加载更多成功
     */
    public void setOnLoadStateChangeListener(OnLoadStateChangeListener<Model> onLoadStateChangeListener) {
        this.mOnLoadStateChangeListener = onLoadStateChangeListener;
    }

    @Override
    public ICommonListAdapter<Model> getListAdapter() {
        return mDataAdapter;
    }

    /**
     * 设置适配器，用于显示数据
     *
     * @param adapter 适配器
     */
    @Override
    public void setListAdapter(ICommonListAdapter<Model> adapter) {
        getListScrollHelper().getScrollableViewWrapper().setAdapter(adapter);
        this.mDataAdapter = adapter;
    }

    /**
     * 设置数据源，用于加载数据
     */
    @Override
    public void setDataSource(IDataSource<Model> dataSource) {
        this.mDataSource = dataSource;
    }

    @Override
    public IDataSource<Model> getDataSource() {
        return mDataSource;
    }

    @Override
    public ListHelper getListHelper() {
        return this;
    }

    @Override
    public IPullRefreshWrapper<?> getRefreshWrapper() {
        return mRefreshWrapper;
    }

    @Override
    public IPullRefreshLayout getRefreshLayout() {
        return mRefreshWrapper.getPullRefreshAbleView();
    }

    /**
     * 开始刷新，同时让SwipeRefreshLayout显示刷新
     */
    @Override
    public void startRefreshWithRefreshLoading() {
        mRefreshWrapper.startRefreshWithAnimation();
    }

    @Override
    public boolean isRefreshing() {
        return mRefreshWrapper.isRefurbishing();
    }

    /**
     * 是否有下一页
     */
    @Override
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
    @Override
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
    @Override
    public boolean isCanPullToRefresh() {
        return getRefreshWrapper().isCanPullToRefresh();
    }

    /**
     * 设置是否反转布局
     */
    @Override
    public void setReverse(boolean reverse) {
        isReverse = reverse;
    }

    /**
     * 当前是否是反转布局
     */
    @Override
    public boolean isReverse() {
        return isReverse;
    }

    /**
     * 设置是否启用加载更多尾部
     */
    @Override
    public void setEnableLoadMoreFooter(boolean enableLoadMoreFooter) {
        this.enableLoadMoreFooter = enableLoadMoreFooter;
        this.mLoadMoreView.enableLoadMoreFooter(enableLoadMoreFooter);
    }

    /**
     * 是否存在加载更多尾部
     */
    @Override
    public boolean isEnableLoadMoreFooter() {
        return enableLoadMoreFooter;
    }

    @Override
    public ListScrollHelper getListScrollHelper() {
        return listScrollHelper;
    }
}