package oms.mmc.android.fast.framwork.util;

import android.app.Activity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;

import oms.mmc.android.fast.framwork.R;
import oms.mmc.android.fast.framwork.base.BaseListAdapter;
import oms.mmc.android.fast.framwork.base.IDataSource;
import oms.mmc.android.fast.framwork.base.IPullRefreshUi;
import oms.mmc.android.fast.framwork.base.ListLayoutCallback;
import oms.mmc.android.fast.framwork.loadview.ILoadMoreViewFactory;
import oms.mmc.android.fast.framwork.widget.list.ICommonListAdapter;
import oms.mmc.android.fast.framwork.widget.list.helper.AssistHelper;
import oms.mmc.android.fast.framwork.widget.pull.IPullRefreshLayout;
import oms.mmc.android.fast.framwork.widget.pull.IPullRefreshWrapper;
import oms.mmc.android.fast.framwork.widget.rv.adapter.HeaderFooterDataAdapter;
import oms.mmc.android.fast.framwork.widget.rv.base.BaseItemData;
import oms.mmc.android.fast.framwork.widget.rv.base.BaseTpl;
import oms.mmc.android.fast.framwork.widget.rv.base.RecyclerViewListConfigCallback;
import oms.mmc.factory.load.factory.ILoadViewFactory;
import oms.mmc.helper.ListScrollHelper;
import oms.mmc.helper.base.IScrollableView;
import oms.mmc.helper.base.IScrollableViewWrapper;
import oms.mmc.helper.widget.ScrollableRecyclerView;

/**
 * Package: oms.mmc.android.fast.framwork.util
 * FileName: ListDelegateHelper
 * Date: on 2018/2/26  下午1:42
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */

public class ListAbleDelegateHelper<P extends IPullRefreshLayout> {
    /**
     * 下拉刷新控件
     */
    protected IPullRefreshWrapper<P> mRefreshWrapper;
    /**
     * 列表
     */
    protected IScrollableView mScrollableView;
    /**
     * 列表加载帮助类
     */
    protected RecyclerViewViewHelper<BaseItemData> mRecyclerViewHelper;
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
    private ListLayoutCallback<BaseItemData> mListAble;
    private RecyclerViewListConfigCallback mRecyclerViewListConfigCallback;
    /**
     * 下拉刷新界面
     */
    private IPullRefreshUi<P> mPullRefreshUi;
    /**
     * 滚动监听帮助类
     */
    private ListScrollHelper mScrollHelper;
    private IScrollableViewWrapper mScrollableViewWrapper;
    private AssistHelper mAssistHelper;

    public ListAbleDelegateHelper(ListLayoutCallback<BaseItemData> listAble, RecyclerViewListConfigCallback recyclerViewListConfigCallback, IPullRefreshUi<P> pullRefreshUi) {
        this.mListAble = listAble;
        this.mRecyclerViewListConfigCallback = recyclerViewListConfigCallback;
        this.mPullRefreshUi = pullRefreshUi;
        mAssistHelper = new AssistHelper();
    }

    /**
     * 开始代理
     */
    public void startDelegate(Activity activity, View rootLayout) {
        setup(activity, rootLayout);
    }

    /**
     * 开始设置
     */
    private void setup(Activity activity, View rootLayout) {
        //初始化下拉刷新控件，并且回调获取包裹类
        IPullRefreshLayout pullToRefreshLayout = (IPullRefreshLayout) rootLayout.findViewById(R.id.fast_refresh_layout);
        mRefreshWrapper = mPullRefreshUi.onInitPullRefreshWrapper((P) pullToRefreshLayout);
        mPullRefreshUi.onPullRefreshWrapperReady(mRefreshWrapper, mRefreshWrapper.getPullRefreshAbleView());
        //初始化列表控件
        mScrollableView = (IScrollableView) rootLayout.findViewById(R.id.fast_recycler_view);
        if (mScrollableView instanceof ScrollableRecyclerView) {
            ((ScrollableRecyclerView) mScrollableView).setLayoutManager(mRecyclerViewListConfigCallback.onGetListLayoutManager());
        }
        //初始化列表帮助类
        if (mRecyclerViewHelper == null) {
            mRecyclerViewHelper = new RecyclerViewViewHelper<BaseItemData>(activity, mRefreshWrapper, mScrollableView);
        }
        //初始化数据源
        if (mListDataSource == null) {
            mListDataSource = mListAble.onListDataSourceReady();
        }
        mRecyclerViewHelper.setDataSource(this.mListDataSource);
        if (mListData == null) {
            mListData = mListDataSource.getListData();
        }
        //初始化列表适配器
        if (mListAdapter == null) {
            mListAdapter = mListAble.onListAdapterReady();
        }
        mListAdapter.setAssistHelper(mAssistHelper);
        mRecyclerViewHelper.setAdapter(mListAdapter);
        //初始化视图切换工厂
        mLoadViewFactory = mListAble.onLoadViewFactoryReady();
        //初始化列表加载更多视图工厂
        mLoadMoreViewFactory = mListAble.onLoadMoreViewFactoryReady();
        mRecyclerViewHelper.init(mLoadViewFactory, mLoadMoreViewFactory);
    }

    public void notifyListReady() {
        mListAble.onListReady();
    }

    public IPullRefreshWrapper<?> getRefreshWrapper() {
        return mRefreshWrapper;
    }

    public IPullRefreshLayout getRefreshLayout() {
        return mRefreshWrapper.getPullRefreshAbleView();
    }

    public IScrollableView getScrollableView() {
        return mScrollableView;
    }

    public IScrollableViewWrapper getScrollableViewWrapper() {
        return mScrollableViewWrapper;
    }

    public RecyclerViewViewHelper<BaseItemData> getRecyclerViewHelper() {
        return mRecyclerViewHelper;
    }

    public IDataSource<BaseItemData> getListDataSource() {
        return mListDataSource;
    }

    public ArrayList<BaseItemData> getListData() {
        return mListData;
    }

    public ICommonListAdapter<BaseItemData> getListAdapter() {
        return mListAdapter;
    }

    public AssistHelper getAssistHelper() {
        return mAssistHelper;
    }

    public ILoadViewFactory getLoadViewFactory() {
        return mLoadViewFactory;
    }

    public ILoadMoreViewFactory getLoadMoreViewFactory() {
        return mLoadMoreViewFactory;
    }

    public ListLayoutCallback<BaseItemData> getListAble() {
        return mListAble;
    }

    public ListScrollHelper getScrollHelper() {
        return mScrollHelper;
    }

    /**
     * 设置列表控件相关配置
     */
    public void setupRecyclerView() {
        if (getScrollableView() instanceof ScrollableRecyclerView) {
            ScrollableRecyclerView scrollableRecyclerView = (ScrollableRecyclerView) getScrollableView();
            //rv在25版本加入了预缓冲，粘性头部在该功能上不兼容，用此开关关闭该功能
            try {
                scrollableRecyclerView.getLayoutManager().setItemPrefetchEnabled(false);
            } catch (Throwable e) {
                //这里try-catch是因为如果使用者使用排除进行替换低版本的rv时，调用该方法会可能找不到方法抛出异常
                //e.printStackTrace();
            }
            RecyclerView.LayoutManager layoutManager = scrollableRecyclerView.getLayoutManager();
            //自动测量
            layoutManager.setAutoMeasureEnabled(true);
            //优化，除了瀑布流外，rv的尺寸每次改变时，不重新requestLayout
            scrollableRecyclerView.setHasFixedSize(true);
        }
        //设置结束，开始刷新
        ArrayList<BaseItemData> listData = getListData();
        if (listData.size() == 0) {
            //第一次刷新开始
            getRecyclerViewHelper().refresh();
        }
        mListAble.onListReadyAfter();
    }

    /**
     * 销毁RecyclerViewHelper
     */
    public void destroyRecyclerViewHelper() {
        if (mRecyclerViewHelper != null) {
            mRecyclerViewHelper.destroy();
        }
    }

    /**
     * 设置滚动帮助类
     */
    public void setupScrollHelper() {
        //加入滚动监听
        mScrollHelper = mListAble.onInitScrollHelper();
        mScrollableViewWrapper = mScrollHelper.getScrollableViewWrapper();
        mRecyclerViewHelper.setupScrollHelper(mScrollHelper);
        ((BaseListAdapter) ((HeaderFooterDataAdapter) mListAdapter).getAdapter()).setListScrollHelper(mScrollHelper);
        mListAble.onListScrollHelperReady(mScrollHelper);
    }

    /**
     * 嵌套NestedScrollView时在onListReady()时调用
     */
    public void compatNestedScroll() {
        //放弃滚动，将滚动交给上层的NestedScrollView
        IScrollableView scrollableView = getScrollableView();
        if (scrollableView instanceof ScrollableRecyclerView) {
            ((ScrollableRecyclerView) scrollableView).setNestedScrollingEnabled(false);
        }
    }

    /**
     * 反转列表布局，实现类似QQ聊天时使用，当时线性布局和网格时才可以使用
     */
    public void reverseListLayout() {
        IScrollableView scrollableView = getScrollableView();
        if (scrollableView instanceof ScrollableRecyclerView) {
            RecyclerView.LayoutManager manager = ((ScrollableRecyclerView) scrollableView).getLayoutManager();
            if (manager instanceof LinearLayoutManager) {
                LinearLayoutManager layoutManager = (LinearLayoutManager) manager;
                //将helper类中的标志设置反转，这里很重要，不能省，否则返回的标志会不正确
                getRecyclerViewHelper().setReverse(true);
                //设置rv为倒转布局
                layoutManager.setReverseLayout(true);
                //当不是网格时才能使用元素添加顺序倒转，就是说只有线性布局
                if (!(manager instanceof GridLayoutManager)) {
                    layoutManager.setStackFromEnd(true);
                }
            }
        }
    }

    /**
     * 缓慢滚动到顶部
     */
    public void smoothMoveToTop(boolean isReverse) {
        if (!isReverse) {
            getScrollHelper().smoothMoveToTop();
        } else {
            //由于是反转布局，顶部的position就是列表的总数
            if (getScrollableView() instanceof ScrollableRecyclerView) {
                ScrollableRecyclerView scrollableRecyclerView = (ScrollableRecyclerView) getScrollableView();
                scrollableRecyclerView.smoothScrollToPosition(scrollableRecyclerView.getAdapter().getItemCount());
            }
        }
    }

    /**
     * 顺时滚动到顶部
     */
    public void moveToTop(boolean isReverse) {
        if (!isReverse) {
            getScrollHelper().moveToTop();
        } else {
            if (getScrollableView() instanceof ScrollableRecyclerView) {
                ScrollableRecyclerView scrollableRecyclerView = (ScrollableRecyclerView) getScrollableView();
                //由于是反转布局，顶部的position就是列表的总数
                scrollableRecyclerView.scrollToPosition(scrollableRecyclerView.getAdapter().getItemCount());
            }
        }
    }

    /**
     * 按position查找tpl
     *
     * @param position 条目的position
     */
    public BaseTpl findTplByPosition(int position) {
        if (getScrollableView() instanceof ScrollableRecyclerView) {
            ScrollableRecyclerView scrollableRecyclerView = (ScrollableRecyclerView) getScrollableView();
            RecyclerView.ViewHolder viewHolder = scrollableRecyclerView.findViewHolderForAdapterPosition(position);
            return (BaseTpl) viewHolder.itemView.getTag(R.id.tag_tpl);
        }
        return null;
    }
}