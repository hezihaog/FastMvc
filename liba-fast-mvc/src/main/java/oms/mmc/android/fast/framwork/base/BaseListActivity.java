package oms.mmc.android.fast.framwork.base;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;

import oms.mmc.android.fast.framwork.R;
import oms.mmc.android.fast.framwork.util.BaseLoadViewFactory;
import oms.mmc.android.fast.framwork.util.IDataAdapter;
import oms.mmc.android.fast.framwork.util.IDataSource;
import oms.mmc.android.fast.framwork.util.ILoadViewFactory;
import oms.mmc.android.fast.framwork.util.MethodCompat;
import oms.mmc.android.fast.framwork.util.OnStateChangeListener;
import oms.mmc.android.fast.framwork.util.RecyclerViewViewHelper;
import oms.mmc.android.fast.framwork.widget.rv.adapter.HeaderFooterAdapter;
import oms.mmc.android.fast.framwork.widget.rv.base.BaseItemData;
import oms.mmc.android.fast.framwork.widget.rv.base.BaseTpl;
import oms.mmc.android.fast.framwork.widget.view.ListScrollHelper;

public abstract class BaseListActivity extends BaseActivity implements ListLayoutCallback<BaseItemData, BaseTpl.ViewHolder>, OnStateChangeListener<ArrayList<BaseItemData>>, BaseListAdapter.OnRecyclerViewItemClickListener, BaseListAdapter.OnRecyclerViewItemLongClickListener {
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
    protected RecyclerViewViewHelper<BaseItemData> mRecyclerViewHelper;
    /**
     * 列表数据源
     */
    protected IDataSource<BaseItemData> mListViewSource;
    /**
     * 列表数据
     */
    protected ArrayList<BaseItemData> mListViewData;
    /**
     * 原始数据
     */
    protected ArrayList<BaseItemData> mOriginData;
    /**
     * 列表适配器
     */
    protected BaseListAdapter<BaseItemData> mListAdapter;
    /**
     * 滚动帮助类
     */
    private ListScrollHelper mListScrollHelper;
    /**
     * 加载状态切换布局工厂
     */
    private ILoadViewFactory mLoadViewFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //初始化布局控件
        mRefreshLayout = (SwipeRefreshLayout) this.findViewById(R.id.fast_refresh_layout);
        mRefreshLayout.setId(MethodCompat.generateViewId());
        //初始化列表控件
        mRecyclerView = (RecyclerView) this.findViewById(R.id.base_list_view);
        mRecyclerView.setLayoutManager(getListLayoutManager());
        //初始化列表帮助类
        if (mRecyclerViewHelper == null) {
            mRecyclerViewHelper = new RecyclerViewViewHelper<BaseItemData>(mRefreshLayout, mRecyclerView);
        }
        //初始化数据源
        if (mListViewSource == null) {
            mListViewSource = onListDataSourceReady();
        }
        mRecyclerViewHelper.setDataSource(this.mListViewSource);
        if (mListViewData == null) {
            mListViewData = mListViewSource.getOriginListViewData();
            mOriginData = mListViewSource.getOriginListViewData();
        }
        //初始化列表适配器
        if (mListAdapter == null) {
            mListAdapter = (BaseListAdapter<BaseItemData>) onListAdapterReady();
        }
        mRecyclerViewHelper.setAdapter(mListAdapter);
        //初始化视图切换工厂
        mLoadViewFactory = onLoadViewFactoryReady();
        mRecyclerViewHelper.init(mLoadViewFactory);
        //初始化监听
        mListAdapter.addOnItemClickListener(this);
        mListAdapter.addOnItemLongClickListener(this);
        mRecyclerViewHelper.setOnStateChangeListener(this);
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
            mRecyclerViewHelper.refresh();
        }
    }

    @Override
    public int onLayoutId() {
        return R.layout.base_list_activity;
    }

    @Override
    public IDataAdapter<ArrayList<BaseItemData>, BaseTpl.ViewHolder> onListAdapterReady() {
        return new BaseListAdapter<BaseItemData>(mRecyclerView, getActivity(), mListViewSource,
                onListTypeClassesReady(), mRecyclerViewHelper, onGetStickyTplViewType());
    }

    protected void onListScrollHelperReady(ListScrollHelper listScrollHelper) {

    }

    public RecyclerView getRecyclerView() {
        return mRecyclerView;
    }

    public HeaderFooterAdapter getRecyclerViewAdapter() {
        return (HeaderFooterAdapter) mRecyclerView.getAdapter();
    }

    @Override
    public ILoadViewFactory onLoadViewFactoryReady() {
        return new BaseLoadViewFactory();
    }

    @Override
    public void onStartRefresh(IDataAdapter<ArrayList<BaseItemData>, BaseTpl.ViewHolder> adapter, boolean isFirst, boolean isReverse) {

    }

    @Override
    public void onEndRefresh(IDataAdapter<ArrayList<BaseItemData>, BaseTpl.ViewHolder> adapter, ArrayList<BaseItemData> result, boolean isFirst, boolean isReverse) {

    }

    @Override
    public void onStartLoadMore(IDataAdapter<ArrayList<BaseItemData>, BaseTpl.ViewHolder> adapter, boolean isFirst, boolean isReverse) {

    }

    @Override
    public void onEndLoadMore(IDataAdapter<ArrayList<BaseItemData>, BaseTpl.ViewHolder> adapter, ArrayList<BaseItemData> result, boolean isFirst, boolean isReverse) {

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
