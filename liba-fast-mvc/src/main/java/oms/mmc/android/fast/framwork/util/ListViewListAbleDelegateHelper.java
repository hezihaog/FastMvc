package oms.mmc.android.fast.framwork.util;

import android.view.View;

import oms.mmc.android.fast.framwork.R;
import oms.mmc.android.fast.framwork.base.IPullRefreshUi;
import oms.mmc.android.fast.framwork.base.ListLayoutCallback;
import oms.mmc.android.fast.framwork.widget.pull.IPullRefreshLayout;
import oms.mmc.android.fast.framwork.widget.rv.base.BaseItemData;
import oms.mmc.android.fast.framwork.widget.rv.base.BaseTpl;
import oms.mmc.android.fast.framwork.widget.rv.base.IListConfigCallback;
import oms.mmc.helper.widget.ScrollableListView;

/**
 * Package: oms.mmc.android.fast.framwork.util
 * FileName: ListViewListAbleDelegateHelper
 * Date: on 2018/3/29  上午10:59
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */

public class ListViewListAbleDelegateHelper<P extends IPullRefreshLayout, V extends ScrollableListView> extends ListAbleDelegateHelper<P, V> implements IListViewListAbleDelegateHelper<P, V> {
    public ListViewListAbleDelegateHelper(ListLayoutCallback<BaseItemData, V> listAble, IListConfigCallback listConfigCallback, IPullRefreshUi<P> pullRefreshUi) {
        super(listAble, listConfigCallback, pullRefreshUi);
    }

    @Override
    public void reverseListLayout() {
        //将helper类中的标志设置反转，这里很重要，不能省，否则返回的标志会不正确
        getListHelper().setReverse(true);
        //ListView倒序只能颠倒数据集，例如以下代码
//        Collections.reverse(getListData());
//        getListAdapter().notifyDataSetChanged();
    }

    @Override
    public void smoothMoveToTop(boolean isReverse) {
        if (!isReverse) {
            getScrollableViewWrapper().smoothMoveToTop();
        } else {
            getScrollableView().smoothScrollToPosition(getScrollableView().getBottom());
        }
    }

    @Override
    public void moveToTop(boolean isReverse) {
        if (!isReverse) {
            getScrollableViewWrapper().moveToTop();
        } else {
            getScrollableView().setSelection(getScrollableView().getBottom());
        }
    }

    @Override
    public BaseTpl findTplByPosition(int position) {
        View itemView = getScrollableView().getViewByPosition(position);
        if (itemView != null) {
            return (BaseTpl) itemView.getTag(R.id.tag_tpl);
        }
        return null;
    }

    @Override
    protected void onSetupListWidget(IListConfigCallback listConfigCallback) {
    }
}
