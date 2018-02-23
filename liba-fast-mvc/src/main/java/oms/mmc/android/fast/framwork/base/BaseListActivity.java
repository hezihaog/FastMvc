package oms.mmc.android.fast.framwork.base;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;

import oms.mmc.android.fast.framwork.R;
import oms.mmc.android.fast.framwork.util.MethodCompat;
import oms.mmc.android.fast.framwork.widget.rv.base.BaseItemData;
import oms.mmc.android.fast.framwork.util.BaseLoadViewFactory;
import oms.mmc.android.fast.framwork.util.IDataAdapter;
import oms.mmc.android.fast.framwork.util.IDataSource;
import oms.mmc.android.fast.framwork.util.ILoadViewFactory;
import oms.mmc.android.fast.framwork.util.OnStateChangeListener;
import oms.mmc.android.fast.framwork.util.RecyclerViewViewHelper;
import oms.mmc.android.fast.framwork.widget.rv.base.BaseTpl;
import oms.mmc.android.fast.framwork.widget.view.ListScrollHelper;

public abstract class BaseListActivity<T extends BaseItemData> extends BaseActivity implements ListLayoutCallback<T>, OnStateChangeListener<ArrayList<T>>, BaseListAdapter.OnRecyclerViewItemClickListener, BaseListAdapter.OnRecyclerViewItemLongClickListener {
    /**
     * 下来刷新控件
     */
    protected SwipeRefreshLayout mRefreshLayout;
    /**
     * 列表
     */
    protected RecyclerView mRecyclerView;
    /**
     * 列表加载帮助类
     */
    protected RecyclerViewViewHelper<T> mRecyclerViewHelper;
    /**
     * 列表数据源
     */
    protected IDataSource<T> mListViewDataSource;
    /**
     * 列表数据
     */
    protected ArrayList<T> mListViewData;
    /**
     * 原始数据
     */
    protected ArrayList<T> mOriginData;
    /**
     * 列表适配器
     */
    protected BaseListAdapter<T> mListAdapter;
    /**
     * 滚动帮助类
     */
    private ListScrollHelper mListScrollHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRefreshLayout = (SwipeRefreshLayout) this.findViewById(R.id.fast_refresh_layout);
        mRefreshLayout.setId(MethodCompat.generateViewId());
        //初始化rv
        mRecyclerView = (RecyclerView) this.findViewById(R.id.base_list_view);
        mRecyclerView.setLayoutManager(getListLayoutManager());
        if (mListViewDataSource == null) {
            mListViewDataSource = onListDataSourceReady();
        }
        if (mListViewData == null) {
            mListViewData = mListViewDataSource.getOriginListViewData();
            mOriginData = mListViewDataSource.getOriginListViewData();
        }
        if (mListAdapter == null) {
            mListAdapter = (BaseListAdapter<T>) onListAdapterReady();
        }
        mListAdapter.addOnItemClickListener(this);
        mListAdapter.addOnItemLongClickListener(this);
        if (mRecyclerViewHelper == null) {
            mRecyclerViewHelper = new RecyclerViewViewHelper<T>(mRefreshLayout, mRecyclerView);
            mRecyclerViewHelper.setAdapter(mListAdapter);
        }
        mRecyclerViewHelper.init(onLoadViewFactoryReady());
        mRecyclerViewHelper.setDataSource(this.mListViewDataSource);
        mRecyclerViewHelper.setOnStateChangeListener(this);
        mListAdapter.setRecyclerViewHelper(mRecyclerViewHelper);
        //加入滚动监听
        ListScrollHelper scrollHelper = onGetScrollHelper();
        mRecyclerViewHelper.setupScrollHelper(scrollHelper);
        onListScrollHelperReady(mListScrollHelper);
        onListReady();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mRecyclerViewHelper != null) {
            mRecyclerViewHelper.destroy();
        }
    }

    @Override
    public void onListReady() {
        //rv在25版本加入了预缓冲，粘性头部在该功能上不兼容，用此开关关闭该功能
        try {
            mRecyclerView.getLayoutManager().setItemPrefetchEnabled(false);
        } catch (Exception e) {
            //这里try-catch是因为如果使用者使用排除进行替换低版本的rv时，调用该方法会可能找不到方法抛出异常
            e.printStackTrace();
        }
        RecyclerView.LayoutManager layoutManager = mRecyclerView.getLayoutManager();
        //自动测量
        layoutManager.setAutoMeasureEnabled(true);
        //优化，除了瀑布流外，rv的尺寸每次改变时，不重新requestLayout
        mRecyclerView.setHasFixedSize(true);
        //一开始先加一个尾部加载更多条目，然后刷新
        if (mListViewData.size() == 0) {
            mListAdapter.getLoadMoreHelper().addLoadMoreTpl();
            mRecyclerViewHelper.refresh();
        }
    }

    @Override
    public int onLayoutId() {
        return R.layout.base_list_activity;
    }

    @Override
    public IDataAdapter<ArrayList<T>> onListAdapterReady() {
        return new BaseListAdapter<T>(mRecyclerView, getActivity(), mListViewDataSource,
                onListTypeClassesReady(), mRecyclerViewHelper, onGetStickyTplViewType());
    }

    protected void onListScrollHelperReady(ListScrollHelper listScrollHelper) {

    }

    public BaseListAdapter<T> getRecyclerViewAdapter() {
        return (BaseListAdapter<T>) mRecyclerView.getAdapter();
    }

    public RecyclerView getRecyclerView() {
        return mRecyclerView;
    }

    @Override
    public ILoadViewFactory onLoadViewFactoryReady() {
        return new BaseLoadViewFactory();
    }

    @Override
    public void onStartRefresh(IDataAdapter<ArrayList<T>> adapter, boolean isFirst, boolean isReverse) {

    }

    @Override
    public void onEndRefresh(IDataAdapter<ArrayList<T>> adapter, ArrayList<T> result, boolean isFirst, boolean isReverse) {

    }

    @Override
    public void onStartLoadMore(IDataAdapter<ArrayList<T>> adapter, boolean isFirst, boolean isReverse) {

    }

    @Override
    public void onEndLoadMore(IDataAdapter<ArrayList<T>> adapter, ArrayList<T> result, boolean isFirst, boolean isReverse) {

    }

    @Override
    public void onItemClick(View view, BaseTpl clickTpl, int position) {

    }

    @Override
    public boolean onItemLongClick(View view, BaseTpl longClickTpl, int position) {
        return false;
    }

    @Override
    public int onGetStickyTplViewType() {
        return BaseListAdapter.NOT_STICKY_SECTION;
    }

    /**
     * 嵌套NestedScrollView时在onListReady()时调用
     */
    public void compatNestedScroll() {
        //放弃滚动，将滚动交给上层的NestedScrollView
        mRecyclerView.setNestedScrollingEnabled(false);
    }

    /**
     * 反转列表布局，实现类似QQ聊天时使用
     */
    public void reverseListLayout() {
        LinearLayoutManager layoutManager = (LinearLayoutManager) mRecyclerView.getLayoutManager();
        layoutManager.setStackFromEnd(true);
        layoutManager.setReverseLayout(true);
    }

    public ListScrollHelper getListScrollHelper() {
        return mListScrollHelper;
    }

    public void smoothMoveToTop() {
        getListScrollHelper().smoothMoveToTop();
    }

    public void moveToTop() {
        getListScrollHelper().moveToTop();
    }
}
