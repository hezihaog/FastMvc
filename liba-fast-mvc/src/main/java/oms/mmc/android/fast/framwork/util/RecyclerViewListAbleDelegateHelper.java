package oms.mmc.android.fast.framwork.util;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import oms.mmc.android.fast.framwork.R;
import oms.mmc.android.fast.framwork.base.IPullRefreshUi;
import oms.mmc.android.fast.framwork.base.ListLayoutCallback;
import oms.mmc.android.fast.framwork.widget.pull.IPullRefreshLayout;
import oms.mmc.android.fast.framwork.widget.rv.base.BaseItemData;
import oms.mmc.android.fast.framwork.widget.rv.base.BaseTpl;
import oms.mmc.android.fast.framwork.widget.rv.base.IListConfigCallback;
import oms.mmc.android.fast.framwork.widget.rv.base.RecyclerViewListConfigCallback;
import oms.mmc.helper.widget.ScrollableRecyclerView;

/**
 * Package: oms.mmc.android.fast.framwork.util
 * FileName: RecyclerViewListAbleDelegateHelper
 * Date: on 2018/3/29  上午10:03
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */

public class RecyclerViewListAbleDelegateHelper<P extends IPullRefreshLayout, V extends ScrollableRecyclerView> extends ListAbleDelegateHelper<P, V> implements IRecyclerViewListAbleDelegateHelper<P, V> {
    public RecyclerViewListAbleDelegateHelper(ListLayoutCallback<BaseItemData, V> listAble, IListConfigCallback listConfigCallback, IPullRefreshUi<P> pullRefreshUi) {
        super(listAble, listConfigCallback, pullRefreshUi);
    }

    /**
     * 反转列表布局，实现类似QQ聊天时使用，当时线性布局和网格时才可以使用
     */
    @Override
    public void reverseListLayout() {
        RecyclerView.LayoutManager manager = getScrollableView().getLayoutManager();
        if (manager instanceof LinearLayoutManager) {
            LinearLayoutManager layoutManager = (LinearLayoutManager) manager;
            //将helper类中的标志设置反转，这里很重要，不能省，否则返回的标志会不正确
            getListHelper().setReverse(true);
            //设置rv为倒转布局
            layoutManager.setReverseLayout(true);
            //当不是网格时才能使用元素添加顺序倒转，就是说只有线性布局
            if (!(manager instanceof GridLayoutManager)) {
                layoutManager.setStackFromEnd(true);
            }
        }
    }

    /**
     * 缓慢滚动到顶部
     */
    @Override
    public void smoothMoveToTop(boolean isReverse) {
        if (!isReverse) {
            getScrollHelper().smoothMoveToTop();
        } else {
            //由于是反转布局，顶部的position就是列表的总数
            getScrollableView().smoothScrollToPosition(getScrollableView().getAdapter().getItemCount());
        }
    }

    /**
     * 顺时滚动到顶部
     */
    @Override
    public void moveToTop(boolean isReverse) {
        if (!isReverse) {
            getScrollHelper().moveToTop();
        } else {
            //由于是反转布局，顶部的position就是列表的总数
            getScrollableView().scrollToPosition(getScrollableView().getAdapter().getItemCount());
        }
    }

    /**
     * 按position查找tpl
     *
     * @param position 条目的position
     */
    @Override
    public BaseTpl findTplByPosition(int position) {
        View itemView = getScrollableView().getViewByPosition(position);
        return (BaseTpl) itemView.getTag(R.id.tag_tpl);
    }

    /**
     * 嵌套NestedScrollView时在onListReady()时调用
     */
    @Override
    public void compatNestedScroll() {
        //放弃滚动，将滚动交给上层的NestedScrollView
        getScrollableView().setNestedScrollingEnabled(false);
    }

    @Override
    protected void onFindListWidgetAfter(IListConfigCallback listConfigCallback) {
        super.onFindListWidgetAfter(listConfigCallback);
        //设置LayoutManager
        RecyclerView.LayoutManager manager = ((RecyclerViewListConfigCallback) listConfigCallback).onGetListLayoutManager();
        getScrollableView().setLayoutManager(manager);
    }

    @Override
    protected void onSetupListWidget(IListConfigCallback listConfigCallback) {
        ScrollableRecyclerView scrollableRecyclerView = getScrollableView();
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
}