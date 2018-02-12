package oms.mmc.android.fast.framwork.util;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import oms.mmc.android.fast.framwork.R;
import oms.mmc.android.fast.framwork.base.BaseTpl;
import oms.mmc.android.fast.framwork.base.HeaderFooterAdapter;
import oms.mmc.android.fast.framwork.base.ItemDataWrapper;
import oms.mmc.android.fast.framwork.bean.BaseItemData;
import oms.mmc.android.fast.framwork.tpl.LoadMoreFooterTpl;

/**
 * Package: oms.mmc.android.fast.framwork.util
 * FileName: LoadMoreHelper
 * Date: on 2018/2/12  下午2:28
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */

public class LoadMoreHelper implements ILoadMoreHelper {
    /**
     * 加载更多尾部条目类型
     */
    public static final int TPL_LOAD_MORE_FOOTER = 1000;
    /**
     * 加载更多条目的位置
     */
    private int loadMoreTplPosition;
    /**
     * 适配器
     */
    private HeaderFooterAdapter<BaseItemData> mAdapter;

    public LoadMoreHelper(HeaderFooterAdapter adapter) {
        mAdapter = adapter;
    }

    @Override
    public void addLoadMoreTpl() {
        mAdapter.registerFooter(TPL_LOAD_MORE_FOOTER, LoadMoreFooterTpl.class,
                new ItemDataWrapper(TPL_LOAD_MORE_FOOTER));
    }

    @Override
    public void removeLoadMoreTpl() {
        mAdapter.unRegisterFooter(TPL_LOAD_MORE_FOOTER);
    }

    @Override
    public int getLoadMoreTplPosition() {
        return loadMoreTplPosition;
    }

    @Override
    public void setLoadMoreTplPosition(int position) {
        this.loadMoreTplPosition = position;
    }

    @Override
    public boolean isAddLoadMoreItem() {
        return findLoaderMoreFootTpl() != null;
    }

    @Override
    public LoadMoreFooterTpl findLoaderMoreFootTpl() {
        RecyclerView recyclerView = mAdapter.getRecyclerView();
        View itemView = recyclerView.getLayoutManager().findViewByPosition(loadMoreTplPosition);
        if (itemView != null) {
            BaseTpl tpl = (BaseTpl) itemView.getTag(R.id.tag_tpl);
            if (tpl.getItemViewType() == TPL_LOAD_MORE_FOOTER) {
                return (LoadMoreFooterTpl) tpl;
            }
        }
        return null;
    }
}