package oms.mmc.android.fast.framwork.util;

import android.app.Activity;
import android.view.View;

import java.util.ArrayList;

import oms.mmc.android.fast.framwork.R;
import oms.mmc.android.fast.framwork.base.IDataSource;
import oms.mmc.android.fast.framwork.base.IPullRefreshUi;
import oms.mmc.android.fast.framwork.base.ListLayoutCallback;
import oms.mmc.android.fast.framwork.loadview.ILoadMoreViewFactory;
import oms.mmc.android.fast.framwork.widget.list.ICommonListAdapter;
import oms.mmc.android.fast.framwork.widget.list.helper.AssistHelper;
import oms.mmc.android.fast.framwork.widget.list.helper.IAssistHelper;
import oms.mmc.android.fast.framwork.widget.pull.IPullRefreshLayout;
import oms.mmc.android.fast.framwork.widget.pull.IPullRefreshWrapper;
import oms.mmc.android.fast.framwork.widget.rv.base.BaseItemData;
import oms.mmc.android.fast.framwork.widget.rv.base.IListConfigCallback;
import oms.mmc.factory.load.factory.ILoadViewFactory;
import oms.mmc.helper.ListScrollHelper;
import oms.mmc.helper.base.IScrollableAdapterView;
import oms.mmc.helper.base.IScrollableViewWrapper;

/**
 * Package: oms.mmc.android.fast.framwork.util
 * FileName: ListDelegateHelper
 * Date: on 2018/2/26  下午1:42
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */

public abstract class ListAbleDelegateHelper<P extends IPullRefreshLayout, V extends IScrollableAdapterView> implements IListAbleDelegateHelper<P, V> {
    /**
     * 下拉刷新控件
     */
    protected IPullRefreshWrapper<P> mRefreshWrapper;
    /**
     * 列表
     */
    protected V mScrollableView;
    /**
     * 列表加载帮助类
     */
    protected ListHelper<BaseItemData> mListHelper;
    /**
     * 列表数据源
     */
    protected IDataSource<BaseItemData> mListDataSource;
    /**
     * 列表数据
     */
    protected ArrayList<BaseItemData> mListData;
    /**
     * 列表适配器
     */
    protected ICommonListAdapter<BaseItemData> mListAdapter;
    /**
     * 加载状态切换布局工厂
     */
    private ILoadViewFactory mLoadViewFactory;
    /**
     * 加载更多布局工厂
     */
    private ILoadMoreViewFactory mLoadMoreViewFactory;
    /**
     * 列表界面，ListActivity或者ListFragment
     */
    private ListLayoutCallback<BaseItemData, V> mListAble;
    private IListConfigCallback mListConfigCallback;
    /**
     * 下拉刷新界面
     */
    private IPullRefreshUi<P> mPullRefreshUi;
    /**
     * 滚动监听帮助类
     */
    private ListScrollHelper<V> mScrollHelper;
    private IScrollableViewWrapper<V> mScrollableViewWrapper;
    private AssistHelper mAssistHelper;

    public ListAbleDelegateHelper(ListLayoutCallback<BaseItemData, V> listAble, IListConfigCallback listConfigCallback, IPullRefreshUi<P> pullRefreshUi) {
        this.mListAble = listAble;
        this.mListConfigCallback = listConfigCallback;
        this.mPullRefreshUi = pullRefreshUi;
        this.mAssistHelper = new AssistHelper();
    }

    /**
     * 开始代理
     */
    @Override
    public void startDelegate(Activity activity, View rootLayout) {
        //初始化下拉刷新控件，并且回调获取包裹类
        IPullRefreshLayout pullToRefreshLayout = (IPullRefreshLayout) rootLayout.findViewById(R.id.fast_refresh_layout);
        mRefreshWrapper = mPullRefreshUi.onInitPullRefreshWrapper((P) pullToRefreshLayout);
        mPullRefreshUi.onPullRefreshWrapperReady(mRefreshWrapper, mRefreshWrapper.getPullRefreshAbleView());
        //初始化列表控件
        mScrollableView = (V) rootLayout.findViewById(R.id.fast_list);
        //回调子类，给查找到控件后一个回调设置，例如rv会设置LayoutManager
        onFindListWidgetAfter(mListConfigCallback);
        //初始化列表帮助类
        if (mListHelper == null) {
            mListHelper = new ListHelper<BaseItemData>(activity, mRefreshWrapper, mScrollableView);
        }
        //初始化数据源
        if (mListDataSource == null) {
            mListDataSource = mListAble.onListDataSourceReady();
        }
        mListHelper.setDataSource(this.mListDataSource);
        if (mListData == null) {
            mListData = mListDataSource.getListData();
        }
        //初始化列表适配器
        if (mListAdapter == null) {
            mListAdapter = mListAble.onListAdapterReady();
        }
        mListAdapter.setAssistHelper(mAssistHelper);
        //设置滚动帮助类
        setupScrollHelper();
        mListHelper.setListAdapter(mListAdapter);
        //初始化视图切换工厂
        mLoadViewFactory = mListAble.onLoadViewFactoryReady();
        //初始化列表加载更多视图工厂
        mLoadMoreViewFactory = mListAble.onLoadMoreViewFactoryReady();
        mListHelper.init(mLoadViewFactory, mLoadMoreViewFactory);
    }

    /**
     * 查找到列表控件后回调
     *
     * @param listConfigCallback 列表配置回调对象
     */
    protected void onFindListWidgetAfter(IListConfigCallback listConfigCallback) {

    }

    /**
     * 设置列表控件相关配置
     */
    @Override
    public void setupListWidget() {
        onSetupListWidget(mListConfigCallback);
        //设置结束，开始刷新
        ArrayList<BaseItemData> listData = getListData();
        if (listData.size() == 0) {
            //第一次刷新开始
            getListHelper().refresh();
        }
        mListAble.onListReadyAfter();
    }

    /**
     * 销毁RecyclerViewHelper
     */
    @Override
    public void destroyListHelper() {
        if (mListHelper != null) {
            mListHelper.destroy();
        }
    }

    /**
     * 设置滚动帮助类
     */
    @Override
    public void setupScrollHelper() {
        //加入滚动监听
        mScrollHelper = mListAble.onInitScrollHelper();
        mScrollableViewWrapper = mScrollHelper.getScrollableViewWrapper();
        mListHelper.setupScrollHelper(mScrollHelper);
        mListAdapter.setListScrollHelper(mScrollHelper);
        mListAble.onListScrollHelperReady(mScrollHelper);
    }

    /**
     * 当设置List列表控件时的回调，给子类重写进行对应的控件初始化
     *
     * @param listConfigCallback 列表配置回调对象，通常是activity或者fragment
     */
    protected abstract void onSetupListWidget(IListConfigCallback listConfigCallback);

    @Override
    public void notifyListReady() {
        mListAble.onListReady();
    }

    @Override
    public IPullRefreshWrapper<P> getRefreshWrapper() {
        return mRefreshWrapper;
    }

    @Override
    public P getRefreshLayout() {
        return mRefreshWrapper.getPullRefreshAbleView();
    }

    @Override
    public V getScrollableView() {
        return mScrollableView;
    }

    @Override
    public IScrollableViewWrapper<V> getScrollableViewWrapper() {
        return mScrollableViewWrapper;
    }

    public ListHelper<BaseItemData> getListHelper() {
        return mListHelper;
    }

    @Override
    public IDataSource<BaseItemData> getListDataSource() {
        return mListDataSource;
    }

    @Override
    public ArrayList<BaseItemData> getListData() {
        return mListData;
    }

    @Override
    public ICommonListAdapter<BaseItemData> getListAdapter() {
        return mListAdapter;
    }

    @Override
    public IAssistHelper getAssistHelper() {
        return mAssistHelper;
    }

    @Override
    public ILoadViewFactory getLoadViewFactory() {
        return mLoadViewFactory;
    }

    @Override
    public ILoadMoreViewFactory getLoadMoreViewFactory() {
        return mLoadMoreViewFactory;
    }

    @Override
    public ListScrollHelper<V> getScrollHelper() {
        return mScrollHelper;
    }

    @Override
    public ListLayoutCallback<BaseItemData, V> getListAble() {
        return mListAble;
    }
}