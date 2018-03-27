package oms.mmc.helper.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import oms.mmc.helper.adapter.IListScrollViewAdapter;
import oms.mmc.helper.base.IScrollableAdapterView;
import oms.mmc.helper.util.ListScrollViewAdapterUtil;


/**
 * Package: oms.mmc.android.fast.framwork.widget.view
 * FileName: ScrollableRecyclerView
 * Date: on 2018/2/11  下午6:32
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */

public class ScrollableRecyclerView extends RecyclerView implements IScrollableAdapterView {
    public ScrollableRecyclerView(Context context) {
        super(context);
    }

    public ScrollableRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ScrollableRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void setListAdapter(IListScrollViewAdapter adapter) {
        ListScrollViewAdapterUtil.isValidListAdapter(adapter);
        super.setAdapter((Adapter) adapter);
    }

    @Override
    public IListScrollViewAdapter getListAdapter() {
        return (IListScrollViewAdapter) super.getAdapter();
    }
}
