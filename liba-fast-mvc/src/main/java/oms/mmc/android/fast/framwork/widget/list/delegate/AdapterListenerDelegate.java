package oms.mmc.android.fast.framwork.widget.list.delegate;

import android.view.View;

import java.util.ArrayList;

import oms.mmc.android.fast.framwork.R;
import oms.mmc.android.fast.framwork.adapter.SimpleAttachStateChangeListener;
import oms.mmc.android.fast.framwork.widget.list.ICommonListAdapter;
import oms.mmc.android.fast.framwork.widget.rv.base.BaseTpl;
import oms.mmc.helper.base.IScrollableAdapterView;

/**
 * Package: oms.mmc.android.fast.framwork.widget.list.delegate
 * FileName: AdapterListenerDelegater
 * Date: on 2018/3/28  下午5:42
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */

public class AdapterListenerDelegate implements IAdapterListenerDelegate {
    /**
     * 点击事件
     */
    private ArrayList<OnScrollableViewItemClickListener> onItemClickListeners = new ArrayList<OnScrollableViewItemClickListener>();
    /**
     * 长按事件
     */
    private ArrayList<OnScrollableViewItemLongClickListener> onItemLongClickListeners = new ArrayList<OnScrollableViewItemLongClickListener>();

    @Override
    public void startDelegateAdapterListener(final IScrollableAdapterView scrollableAdapterView, ICommonListAdapter adapter) {
        addOnItemClickListener(new OnScrollableViewItemClickListener() {

            @Override
            public void onItemClick(View view, BaseTpl clickTpl, int position) {
                BaseTpl tpl = (BaseTpl) view.getTag(R.id.tag_tpl);
                tpl.onItemClick(view, position);
            }
        });
        addOnItemLongClickListener(new OnScrollableViewItemLongClickListener() {

            @Override
            public boolean onItemLongClick(View view, BaseTpl longClickTpl, int position) {
                BaseTpl tpl = (BaseTpl) view.getTag(R.id.tag_tpl);
                tpl.onItemLongClick(view, position);
                return true;
            }
        });
        scrollableAdapterView.addOnAttachStateChangeListener(new SimpleAttachStateChangeListener() {
            @Override
            public void onViewDetachedFromWindow(View v) {
                int allItemCount = scrollableAdapterView.getListAdapter().getListItemCount();
                for (int i = 0; i < allItemCount; i++) {
                    View itemView = scrollableAdapterView.getViewByPosition(i);
                    if (itemView != null) {
                        BaseTpl tpl = (BaseTpl) itemView.getTag(R.id.tag_tpl);
                        if (tpl != null) {
                            tpl.onRecyclerViewDetachedFromWindow(v);
                        }
                    }
                }
            }
        });
    }

    @Override
    public ArrayList<OnScrollableViewItemClickListener> getItemClickListeners() {
        return this.onItemClickListeners;
    }

    @Override
    public ArrayList<OnScrollableViewItemLongClickListener> getItemLongClickListeners() {
        return this.onItemLongClickListeners;
    }

    @Override
    public boolean hasItemClickListener() {
        return this.onItemClickListeners.size() > 0;
    }

    @Override
    public boolean hasItemLongClickListener() {
        return this.onItemLongClickListeners.size() > 0;
    }

    @Override
    public void addOnItemClickListener(OnScrollableViewItemClickListener onItemClickListener) {
        this.onItemClickListeners.add(onItemClickListener);
    }

    @Override
    public void removeOnItemClickListener(OnScrollableViewItemClickListener onItemClickListener) {
        this.onItemClickListeners.remove(onItemClickListener);
    }

    @Override
    public void addOnItemLongClickListener(OnScrollableViewItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListeners.add(onItemLongClickListener);
    }

    @Override
    public void removeOnItemLongClickListener(OnScrollableViewItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListeners.remove(onItemLongClickListener);
    }
}