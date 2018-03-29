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

    }

    @Override
    public void smoothMoveToTop(boolean isReverse) {

    }

    @Override
    public void moveToTop(boolean isReverse) {

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
    protected void onSetupListWidget() {

    }
}
