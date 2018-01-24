package oms.mmc.android.fast.framwork.widget.pulltorefresh;

import android.content.Context;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Adapter;
import android.widget.ListView;

import oms.mmc.android.fast.framwork.R;


/**
 * 这个类实现了ListView下拉刷新，上加载更多和滑到底部自动加载
 *
 * @author Li Hong
 * @since 2013-8-15
 */
public class PullToRefreshPinnedListView extends PullToRefreshBase<ListView> {
    /**
     * ListView
     */
    private PinnedSectionListView mListView;

    /**
     * 构造方法
     *
     * @param context _activity
     */
    public PullToRefreshPinnedListView(Context context) {
        this(context, null);
    }

    /**
     * 构造方法
     *
     * @param context _activity
     * @param attrs   attrs
     */
    public PullToRefreshPinnedListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    /**
     * 构造方法
     *
     * @param context  _activity
     * @param attrs    attrs
     * @param defStyle defStyle
     */
    public PullToRefreshPinnedListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        setPullLoadEnabled(false);
    }

    @Override
    protected ListView createRefreshableView(Context context, AttributeSet attrs) {
        PinnedSectionListView listView = new PinnedSectionListView(context, attrs);
        mListView = listView;
        mListView.setScrollBarStyle(SCROLLBARS_OUTSIDE_OVERLAY);
        mListView.setSelector(R.drawable.base_item_selector);
        mListView.setDivider(getResources().getDrawable(R.drawable.base_divider_line));
        mListView.setHeaderDividersEnabled(false);
        mListView.setFooterDividersEnabled(false);
        mListView.setShadowVisible(false);
        return listView;
    }

    @Override
    public boolean isReadyForPullUp() {
        return isLastItemVisible();
    }

    @Override
    public boolean isReadyForPullDown() {
        return isFirstItemVisible();
    }

    /**
     * 判断第一个child是否完全显示出来
     *
     * @return true完全显示出来，否则false
     */
    private boolean isFirstItemVisible() {
        final Adapter adapter = mListView.getAdapter();

        if (null == adapter || adapter.isEmpty()) {
            return true;
        }

        int mostTop = (mListView.getChildCount() > 0) ? mListView.getChildAt(0).getTop() : 0;
        return mostTop >= 0;

    }

    /**
     * 判断最后一个child是否完全显示出来
     *
     * @return true完全显示出来，否则false
     */
    private boolean isLastItemVisible() {
        final Adapter adapter = mListView.getAdapter();

        if (null == adapter || adapter.isEmpty()) {
            return true;
        }

        final int lastItemPosition = adapter.getCount() - 1;
        final int lastVisiblePosition = mListView.getLastVisiblePosition();

        /**
         * This check should really just be: lastVisiblePosition ==
         * lastItemPosition, but ListView internally uses a FooterView which
         * messes the positions up. For me we'll just subtract one to account
         * for it and rely on the inner condition which checks getBottom().
         */
        if (lastVisiblePosition >= lastItemPosition - 1) {
            final int childIndex = lastVisiblePosition - mListView.getFirstVisiblePosition();
            final int childCount = mListView.getChildCount();
            final int index = Math.min(childIndex, childCount - 1);
            final View lastVisibleChild = mListView.getChildAt(index);
            if (lastVisibleChild != null) {
                return lastVisibleChild.getBottom() <= mListView.getBottom();
            }
        }

        return false;
    }

    public void setMessage(Message msg) {
        mListView.setTag(R.id.listview_msg, msg);
    }
}
