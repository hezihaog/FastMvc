package oms.mmc.android.fast.framwork.widget.rv.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;

import oms.mmc.android.fast.framwork.R;
import oms.mmc.android.fast.framwork.adapter.SimpleAttachStateChangeListener;
import oms.mmc.android.fast.framwork.base.IDataSource;
import oms.mmc.android.fast.framwork.base.IFragmentOperator;
import oms.mmc.android.fast.framwork.util.FragmentOperator;
import oms.mmc.android.fast.framwork.util.IToastOperator;
import oms.mmc.android.fast.framwork.util.RecyclerViewViewHelper;
import oms.mmc.android.fast.framwork.util.ToastOperator;
import oms.mmc.android.fast.framwork.widget.list.ICommonListAdapter;
import oms.mmc.android.fast.framwork.widget.list.delegate.CommonListAdapterDelegate;
import oms.mmc.android.fast.framwork.widget.list.helper.IAssistHelper;
import oms.mmc.android.fast.framwork.widget.rv.base.BaseItemData;
import oms.mmc.android.fast.framwork.widget.rv.base.BaseStickyTpl;
import oms.mmc.android.fast.framwork.widget.rv.base.BaseTpl;
import oms.mmc.android.fast.framwork.widget.rv.sticky.StickyHeaders;
import oms.mmc.factory.wait.inter.IWaitViewHost;
import oms.mmc.helper.ListScrollHelper;
import oms.mmc.helper.widget.ScrollableRecyclerView;

/**
 * Package: oms.mmc.android.fast.framwork.base
 * FileName: MultiTypeAdapter
 * Date: on 2018/2/12  下午2:54
 * Auther: zihe
 * Descirbe:通用的RecyclerView适配器
 * Email: hezihao@linghit.com
 */

public class CommonRecyclerViewAdapter extends RecyclerView.Adapter<BaseTpl.ViewHolder> implements
        IMultiTypeAdapter, ICommonListAdapter<BaseItemData>, StickyHeaders, StickyHeaders.ViewSetup {

    private Activity mActivity;
    private ScrollableRecyclerView mScrollableView;
    /**
     * 条目类的类型和条目类class的映射
     */
    private HashMap<Integer, Class> viewTypeClassMap;
    /**
     * 数据集
     */
    private IDataSource<BaseItemData> mListDataSource;
    /**
     * 列表数据
     */
    private ArrayList<BaseItemData> mListData;
    /**
     * 等待弹窗依赖的宿主
     */
    private IWaitViewHost mWaitViewHost;
    /**
     * 点击事件
     */
    private ArrayList<OnScrollableViewItemClickListener> onItemClickListeners = new ArrayList<OnScrollableViewItemClickListener>();
    /**
     * 长按事件
     */
    private ArrayList<OnScrollableViewItemLongClickListener> onItemLongClickListeners = new ArrayList<OnScrollableViewItemLongClickListener>();
    /**
     * rv帮助类
     */
    private RecyclerViewViewHelper<BaseItemData> mRecyclerViewHelper;
    /**
     * 列表滚动帮助类
     */
    private ListScrollHelper mListScrollHelper;
    /**
     * Toast操作类
     */
    private final IToastOperator mToastOperator;
    /**
     * Fragment操作类
     */
    private final IFragmentOperator mFragmentOperator;
    private final CommonListAdapterDelegate mAdapterDelegate;
    private IAssistHelper mAssistHelper;
    /**
     * 不使用粘性头部
     */
    public static final int NOT_STICKY_SECTION = -1;
    /**
     * 粘性条目的类型，默认没有粘性头部
     */
    private int stickySectionViewType = NOT_STICKY_SECTION;

    public CommonRecyclerViewAdapter(ScrollableRecyclerView scrollableView, Activity activity, IDataSource<BaseItemData> dataSource
            , HashMap<Integer, Class> itemViewClazzMap, RecyclerViewViewHelper recyclerViewHelper, IWaitViewHost waitViewHost, int stickySectionViewType) {
        this.mToastOperator = new ToastOperator(activity);
        this.mFragmentOperator = new FragmentOperator(activity);
        this.mScrollableView = scrollableView;
        this.mActivity = activity;
        this.mListDataSource = dataSource;
        this.mListData = dataSource.getListData();
        this.mWaitViewHost = waitViewHost;
        this.viewTypeClassMap = itemViewClazzMap;
        this.mRecyclerViewHelper = recyclerViewHelper;
        this.mAdapterDelegate = new CommonListAdapterDelegate(dataSource.getListData(), viewTypeClassMap);
        this.stickySectionViewType = stickySectionViewType;
        initListener();
    }

    @Override
    public void onViewAttachedToWindow(BaseTpl.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
        if (lp != null && lp instanceof StaggeredGridLayoutManager.LayoutParams) {
            if (isStickyHeader(holder.getLayoutPosition())) {
                StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) lp;
                p.setFullSpan(true);
            }
        }
    }

    @Override
    public boolean isStickyHeader(int position) {
        //不使用粘性头部，则都返回false
        if (stickySectionViewType == NOT_STICKY_SECTION) {
            return false;
        }
        return getItemViewType(position) == stickySectionViewType;
    }

    @Override
    public boolean isStickyHeaderViewType(int viewType) {
        return stickySectionViewType == viewType;
    }

    @Override
    public void setupStickyHeaderView(View stickyHeader) {
        Object tag = stickyHeader.getTag(R.id.tag_tpl);
        if (tag != null && tag instanceof BaseStickyTpl) {
            BaseStickyTpl tpl = (BaseStickyTpl) tag;
            tpl.onAttachSticky();
        }
    }

    @Override
    public void teardownStickyHeaderView(View stickyHeader) {
        Object tag = stickyHeader.getTag(R.id.tag_tpl);
        if (tag != null && tag instanceof BaseStickyTpl) {
            BaseStickyTpl tpl = (BaseStickyTpl) tag;
            tpl.onDetachedSticky();
        }
    }

    /**
     * 初始化监听器
     */
    private void initListener() {
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
        mScrollableView.addOnAttachStateChangeListener(new SimpleAttachStateChangeListener() {
            @Override
            public void onViewDetachedFromWindow(View v) {
                int allItemCount = mScrollableView.getAdapter().getItemCount();
                for (int i = 0; i < allItemCount; i++) {
                    RecyclerView.ViewHolder holder = mScrollableView.findViewHolderForAdapterPosition(i);
                    if (holder != null && holder.itemView != null) {
                        BaseTpl tpl = (BaseTpl) holder.itemView.getTag(R.id.tag_tpl);
                        if (tpl != null) {
                            tpl.onRecyclerViewDetachedFromWindow(v);
                        }
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mAdapterDelegate.getListItemCount();
    }

    @Override
    public BaseTpl.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        BaseTpl.ViewHolder viewHolder;
        BaseTpl tpl = mAdapterDelegate.createTpl(viewType);
        tpl.init(mActivity, mScrollableView, mToastOperator, mWaitViewHost, mFragmentOperator, getAssistHelper(), viewType);
        viewHolder = tpl.getViewHolder();
        viewHolder.itemView.setTag(R.id.tag_tpl, tpl);
        tpl.config(this, mListData, mListDataSource, mRecyclerViewHelper, mListScrollHelper);
        if (onItemClickListeners.size() > 0) {
            tpl.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onItemClickListeners != null) {
                        for (OnScrollableViewItemClickListener clickListener : onItemClickListeners) {
                            BaseTpl clickTpl = (BaseTpl) view.getTag(R.id.tag_tpl);
                            clickListener.onItemClick(view, clickTpl, clickTpl.getPosition());
                        }
                    }
                }
            });
        }
        if (onItemLongClickListeners.size() > 0) {
            tpl.getRoot().setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    if (onItemLongClickListeners != null) {
                        BaseTpl longClickTpl = (BaseTpl) view.getTag(R.id.tag_tpl);
                        for (OnScrollableViewItemLongClickListener longClickListener : onItemLongClickListeners) {
                            longClickListener.onItemLongClick(view, longClickTpl, longClickTpl.getPosition());
                        }
                    }
                    return true;
                }
            });
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(BaseTpl.ViewHolder holder, int position) {
        BaseTpl<BaseItemData> tpl = (BaseTpl<BaseItemData>) holder.itemView.getTag(R.id.tag_tpl);
        mAdapterDelegate.updateTpl(tpl, mListData, position);
    }

    @Override
    public int getItemViewType(int position) {
        return mAdapterDelegate.getListItemViewType(position);
    }

    @Override
    public boolean isEmpty() {
        return getItemCount() == 0;
    }

    @Override
    public void setRefreshListData(ArrayList<BaseItemData> data, boolean isReverse, boolean isFirst) {
        ArrayList<BaseItemData> listData = getListData();
        //第一次刷新
        if (isFirst) {
            listData.addAll(0, data);
        } else {
            //不是第一次刷新
            if (!isReverse) {
                listData.clear();
                listData.addAll(data);
            } else {
                //如果是QQ聊天界面的在顶部下拉加载，直接将数据加上，rv设置布局反转即可
                listData.addAll(data);
            }
        }
    }

    @Override
    public void setLoadMoreListData(ArrayList<BaseItemData> data, boolean isReverse, boolean isFirst) {
        getListData().addAll(data);
    }

    @Override
    public void addOnItemClickListener(OnScrollableViewItemClickListener onItemClickListener) {
        this.onItemClickListeners.add(onItemClickListener);
    }

    @Override
    public void addOnItemLongClickListener(OnScrollableViewItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListeners.add(onItemLongClickListener);
    }

    @Override
    public void removeOnItemClickListener(OnScrollableViewItemClickListener onItemClickListener) {
        this.onItemClickListeners.remove(onItemClickListener);
    }

    @Override
    public void removeOnItemLongClickListener(OnScrollableViewItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListeners.remove(onItemLongClickListener);
    }

    @Override
    public RecyclerView getScrollableView() {
        return mScrollableView;
    }

    @Override
    public RecyclerViewViewHelper<BaseItemData> getRecyclerViewHelper() {
        return mRecyclerViewHelper;
    }

    @Override
    public void setListData(ArrayList listData) {
        this.mListData = listData;
    }

    @Override
    public ArrayList<BaseItemData> getListData() {
        return mListData;
    }

    @Override
    public void setListScrollHelper(ListScrollHelper listScrollHelper) {
        mListScrollHelper = listScrollHelper;
    }

    @Override
    public ListScrollHelper getListScrollHelper() {
        return mListScrollHelper;
    }

    @Override
    public void setAssistHelper(IAssistHelper assistHelper) {
        mAssistHelper = assistHelper;
    }

    @Override
    public IAssistHelper getAssistHelper() {
        return mAssistHelper;
    }
}