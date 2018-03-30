package oms.mmc.android.fast.framwork.widget.rv.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;

import oms.mmc.android.fast.framwork.R;
import oms.mmc.android.fast.framwork.base.IDataSource;
import oms.mmc.android.fast.framwork.base.IFragmentOperator;
import oms.mmc.android.fast.framwork.util.FragmentOperator;
import oms.mmc.android.fast.framwork.util.IToastOperator;
import oms.mmc.android.fast.framwork.util.ListHelper;
import oms.mmc.android.fast.framwork.util.ToastOperator;
import oms.mmc.android.fast.framwork.widget.list.ICommonListAdapter;
import oms.mmc.android.fast.framwork.widget.list.delegate.AdapterListenerDelegate;
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
     * rv帮助类
     */
    private ListHelper<BaseItemData> mListHelper;
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
    private AdapterListenerDelegate mListenerDelegate;

    View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (mListenerDelegate.getItemClickListeners() != null) {
                for (OnScrollableViewItemClickListener clickListener : mListenerDelegate.getItemClickListeners()) {
                    BaseTpl clickTpl = (BaseTpl) view.getTag(R.id.tag_tpl);
                    clickListener.onItemClick(view, clickTpl, clickTpl.getPosition());
                }
            }
        }
    };

    View.OnLongClickListener mOnLongClickListener = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View view) {
            if (mListenerDelegate.getItemLongClickListeners() != null) {
                BaseTpl longClickTpl = (BaseTpl) view.getTag(R.id.tag_tpl);
                for (OnScrollableViewItemLongClickListener longClickListener : mListenerDelegate.getItemLongClickListeners()) {
                    longClickListener.onItemLongClick(view, longClickTpl, longClickTpl.getPosition());
                }
            }
            return true;
        }
    };

    public CommonRecyclerViewAdapter(Activity activity, IDataSource<BaseItemData> dataSource,
                                     ScrollableRecyclerView scrollableView, HashMap<Integer, Class> itemViewClazzMap,
                                     IWaitViewHost waitViewHost, ListHelper listHelper, int stickySectionViewType) {
        this.mToastOperator = new ToastOperator(activity);
        this.mFragmentOperator = new FragmentOperator(activity);
        this.mScrollableView = scrollableView;
        this.mActivity = activity;
        this.mListDataSource = dataSource;
        this.mListData = dataSource.getListData();
        this.mWaitViewHost = waitViewHost;
        this.viewTypeClassMap = itemViewClazzMap;
        this.mListHelper = listHelper;
        this.mAdapterDelegate = new CommonListAdapterDelegate(dataSource.getListData(), itemViewClazzMap);
        this.stickySectionViewType = stickySectionViewType;
        this.mListenerDelegate = new AdapterListenerDelegate();
        //开始监听代理
        this.mListenerDelegate.startDelegateAdapterListener(mScrollableView, this);
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
        tpl.config(this, mListData, mListDataSource, mListHelper, mListScrollHelper);
        if (mListenerDelegate.hasItemClickListener()) {
            tpl.getRoot().setOnClickListener(mOnClickListener);
        }
        if (mListenerDelegate.hasItemLongClickListener()) {
            tpl.getRoot().setOnLongClickListener(mOnLongClickListener);
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
        this.mListenerDelegate.addOnItemClickListener(onItemClickListener);
    }

    @Override
    public void removeOnItemClickListener(OnScrollableViewItemClickListener onItemClickListener) {
        this.mListenerDelegate.removeOnItemClickListener(onItemClickListener);
    }

    @Override
    public void addOnItemLongClickListener(OnScrollableViewItemLongClickListener onItemLongClickListener) {
        this.mListenerDelegate.addOnItemLongClickListener(onItemLongClickListener);
    }

    @Override
    public void removeOnItemLongClickListener(OnScrollableViewItemLongClickListener onItemLongClickListener) {
        this.mListenerDelegate.removeOnItemLongClickListener(onItemLongClickListener);
    }

    @Override
    public RecyclerView getScrollableView() {
        return mScrollableView;
    }

    @Override
    public ListHelper<BaseItemData> getListHelper() {
        return mListHelper;
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

    @Override
    public int getListItemCount() {
        return getItemCount();
    }
}