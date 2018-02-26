package oms.mmc.android.fast.framwork.widget.rv.adapter;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import java.util.ArrayList;

import oms.mmc.android.fast.framwork.base.BaseListAdapter;
import oms.mmc.android.fast.framwork.util.IDataAdapter;
import oms.mmc.android.fast.framwork.widget.rv.sticky.StickyHeaders;

/**
 * Package: oms.mmc.android.fast.framwork.base
 * FileName: HeaderFooterAdapter
 * Date: on 2018/2/12  下午2:37
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */

public class HeaderFooterAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements StickyHeaders, StickyHeaders.ViewSetup {
    private static final int TYPE_HEADER_VIEW = Integer.MIN_VALUE;
    private static final int TYPE_FOOTER_VIEW = TYPE_HEADER_VIEW + 1;

    /**
     * The real adapter for RecyclerView RecyclerView.Adapter<RecyclerView.ViewHolder>
     */
    private IDataAdapter mAdapter;

    private ArrayList<View> mHeaderViews = new ArrayList<View>();
    private ArrayList<View> mFooterViews = new ArrayList<View>();

    private ArrayList<Integer> mHeaderViewTypes = new ArrayList<Integer>();
    private ArrayList<Integer> mFooterViewTypes = new ArrayList<Integer>();
    private int mHeaderViewType;

    private OnItemClickListener mOnItemClickListener;
    private OnItemLongClickListener mOnItemLongClickListener;
    private RecyclerView.LayoutManager mLayoutManager;

    public HeaderFooterAdapter() {
    }

    public HeaderFooterAdapter(IDataAdapter adapter) {
        setAdapter(adapter);
    }

    /**
     * Set the adapter for RecyclerView
     *
     * @param adapter
     */
    public void setAdapter(IDataAdapter adapter) {
        if (null != adapter) {
            if (!(adapter instanceof RecyclerView.Adapter)) {
                throw new RuntimeException("A RecyclerView.Adapter is Need");
            }

            if (null != mAdapter) {
                notifyItemRangeRemoved(getHeadersCount(), mAdapter.getItemCount());
                mAdapter.unregisterAdapterDataObserver(mDataObserver);
            }

            mAdapter = adapter;
            mAdapter.registerAdapterDataObserver(mDataObserver);
            notifyItemRangeInserted(getHeadersCount(), mAdapter.getItemCount());
        }
    }

    /**
     * @return RecyclerView.Adapter
     */
    public IDataAdapter getAdapter() {
        return mAdapter;
    }

    /**
     * remove view From headerViews
     *
     * @param v
     * @return
     */
    public boolean removeHeader(View v) {
        if (mHeaderViews.contains(v)) {
            mHeaderViews.remove(v);
            notifyDataSetChanged();
            return true;
        }
        return false;
    }

    /**
     * 获取最后一个footer，用于判断当前是否处于LoadMore状态
     *
     * @return
     */
    public View getLastFooter() {
        return mFooterViews.get(mFooterViews.size() - 1);
    }

    /**
     * 获取第一个header，用于判断当前是否处于PullDown状态
     *
     * @return
     */
    public View getFirstHeader() {
        return mHeaderViews.get(0);
    }


    /**
     * remove view From footerViews
     *
     * @param v
     * @return
     */
    public boolean removeFooter(View v) {
        if (mFooterViews.contains(v)) {
            mFooterViews.remove(v);
            notifyDataSetChanged();
            return true;
        }
        return false;
    }

    @Override
    public int getItemCount() {
        if (null != mAdapter) {
            return getHeadersCount() + getFootersCount() + mAdapter.getItemCount();
        }
        return getHeadersCount() + getFootersCount();
    }

    @Override
    public long getItemId(int position) {
        int headersCount = getHeadersCount();
        if (null != mFooterViews && position >= headersCount) {
            int adjustPosition = position - headersCount;
            int adapterCount = mAdapter.getItemCount();
            if (adjustPosition < adapterCount) {
                return mAdapter.getItemId(adjustPosition);
            }
        }
        return -1;
    }

    @Override
    public int getItemViewType(int position) {
        int mHeadersCount = getHeadersCount();
        if (null != mAdapter) {
            int itemCount = mAdapter.getItemCount();
            if (position < mHeadersCount) {
                //current itemType is Header
                mHeaderViewType = TYPE_HEADER_VIEW + position;
                mHeaderViewTypes.add(mHeaderViewType);
                return mHeaderViewType;
            } else if (position >= mHeadersCount && position < mHeadersCount + itemCount) {
                //current itemType is item defined by user
                int itemViewType = mAdapter.getItemViewType(position - mHeadersCount);
                if (itemViewType <= TYPE_HEADER_VIEW + mHeadersCount) {
                    throw new IllegalArgumentException("your adapter's return value of " +
                            "getItemViewType() must > (Integer.MinValue + your headersCount)");
                }
                return itemViewType;
            } else {
                //current itemType is Footer
                int mFooterViewType = TYPE_FOOTER_VIEW + position - itemCount;
                mFooterViewTypes.add(mFooterViewType);
                return mFooterViewType;
            }
        } else {
            return AdapterView.ITEM_VIEW_TYPE_HEADER_OR_FOOTER;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (null != mAdapter) {
            if (mHeaderViewTypes.contains(viewType)) {
                //currentPosition in mHeaderViews is (viewType - TYPE_HEADER_VIEW)
                return new RecyclerHeaderViewHolder(mHeaderViews.get(viewType - TYPE_HEADER_VIEW));
            } else if (mFooterViewTypes.contains(viewType)) {
                //currentPosition in mFooterViews is (viewType - headersCount - TYPE_FOOTER_VIEW)
                return new RecyclerHeaderViewHolder(mFooterViews.get(viewType - getHeadersCount()
                        - TYPE_FOOTER_VIEW));
            } else {
                return mAdapter.onCreateViewHolder(parent, viewType);
            }
        }
        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (null != mAdapter) {
            if (position >= getHeadersCount() && position < getHeadersCount() + mAdapter.getItemCount()) {
                mAdapter.onBindViewHolder(holder, position - getHeadersCount());
                if (null != mOnItemClickListener) {
                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mOnItemClickListener.onItemClick(holder, position - getHeadersCount());
                        }
                    });
                }
                if (null != mOnItemLongClickListener) {
                    holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View v) {
                            return mOnItemLongClickListener.onItemLongCLick(holder, position - getHeadersCount());
                        }
                    });
                }
            } else {
                if (null != mLayoutManager && mLayoutManager instanceof StaggeredGridLayoutManager) {
                    StaggeredGridLayoutManager.LayoutParams params = new StaggeredGridLayoutManager.LayoutParams(
                            StaggeredGridLayoutManager.LayoutParams.MATCH_PARENT,
                            150);
                    params.setFullSpan(true);
                    holder.itemView.setLayoutParams(params);
                }
            }
        }
    }

    /**
     * @return headerView's counts
     */
    public Integer getHeadersCount() {
        if (null != mHeaderViews) {
            return mHeaderViews.size();
        }
        return 0;
    }

    public ArrayList<View> getHeaderViews() {
        return mHeaderViews;
    }

    /**
     * @return footerView's counts
     */
    public Integer getFootersCount() {
        if (null != mFooterViews) {
            return mFooterViews.size();
        }
        return 0;
    }

    public ArrayList<View> getFooterViews() {
        return mFooterViews;
    }

    public void addHeaderView(View v, int position) {
        if (null != v) {
            if (mHeaderViews.contains(v)) {
                mHeaderViews.remove(v);
            }
            if (position > mHeaderViews.size()) {
                position = mHeaderViews.size();
            }
            mHeaderViews.add(position, v);
            notifyDataSetChanged();
        }
    }

    /**
     * Add a header for RefreshRecyclerView
     *
     * @param v
     */
    public void addHeaderView(View v) {
        if (null != v) {
            if (mHeaderViews.contains(v)) {
                removeHeader(v);
            }
            mHeaderViews.add(v);
            notifyDataSetChanged();
        }
    }

    /**
     * Add a footer for RefreshRecyclerView
     *
     * @param v
     */
    public void addFooterView(View v) {
        if (null != v) {
            if (mFooterViews.contains(v)) {
                removeFooter(v);
            }
            mFooterViews.add(v);
            notifyDataSetChanged();
        }
    }

    /**
     * 判断当前是否是header
     *
     * @param position
     * @return
     */
    public boolean isHeader(int position) {
        return getHeadersCount() > 0 && position <= getHeadersCount() - 1;
    }

    /**
     * 判断当前是否是footer
     *
     * @param position
     * @return
     */
    public boolean isFooter(int position) {
        int lastPosition = getItemCount() - getFootersCount();
        return getFootersCount() > 0 && position >= lastPosition;
    }

    public void putLayoutManager(RecyclerView.LayoutManager layoutManager) {
        this.mLayoutManager = layoutManager;
    }

    private RecyclerView.AdapterDataObserver mDataObserver = new RecyclerView.AdapterDataObserver() {

        @Override
        public void onChanged() {
            super.onChanged();
            notifyDataSetChanged();
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount) {
            super.onItemRangeChanged(positionStart, itemCount);
            notifyItemRangeChanged(positionStart + getHeadersCount(), itemCount);
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            super.onItemRangeInserted(positionStart, itemCount);
            notifyItemRangeInserted(positionStart + getHeadersCount(), itemCount);
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            super.onItemRangeRemoved(positionStart, itemCount);
            notifyItemRangeRemoved(positionStart + getHeadersCount(), itemCount);
        }

        @Override
        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            super.onItemRangeMoved(fromPosition, toPosition, itemCount);
            notifyItemRangeChanged(fromPosition + getHeadersCount(), toPosition + getHeadersCount() + itemCount);
        }
    };

    @Override
    public boolean isStickyHeader(int position) {
        if ((getAdapter()).isEmpty()) {
            return false;
        }
        //过滤掉头部和尾部，他们不需要粘性
        if (filterHeaderFooterViewType(position)) {
            return false;
        }
        return ((StickyHeaders) getAdapter()).isStickyHeader(position);
    }

    @Override
    public void setupStickyHeaderView(View stickyHeader) {
        if ((getAdapter()).isEmpty()) {
            return;
        }
        ((BaseListAdapter) getAdapter()).setupStickyHeaderView(stickyHeader);
    }

    @Override
    public void teardownStickyHeaderView(View stickyHeader) {
        if ((getAdapter()).isEmpty()) {
            return;
        }
        ((BaseListAdapter) getAdapter()).teardownStickyHeaderView(stickyHeader);
    }

    /**
     * 粘性条女过滤掉头部和尾部
     *
     * @param position 条目位置
     */
    public boolean filterHeaderFooterViewType(int position) {
        int itemViewType = getItemViewType(position);
        if (itemViewType == TYPE_HEADER_VIEW || itemViewType == TYPE_FOOTER_VIEW) {
            return false;
        }
        return true;
    }

    public interface OnItemClickListener {
        void onItemClick(RecyclerView.ViewHolder holder, int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public interface OnItemLongClickListener {
        boolean onItemLongCLick(RecyclerView.ViewHolder holder, int position);
    }

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.mOnItemLongClickListener = onItemLongClickListener;
    }

    public class RecyclerHeaderViewHolder extends RecyclerView.ViewHolder {

        public RecyclerHeaderViewHolder(View itemView) {
            super(itemView);
        }
    }
}
