package oms.mmc.android.fast.framwork.base;

import android.support.v4.util.ArrayMap;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import oms.mmc.android.fast.framwork.R;
import oms.mmc.android.fast.framwork.bean.BaseItemData;
import oms.mmc.android.fast.framwork.recyclerview.sticky.StickyHeaders;
import oms.mmc.android.fast.framwork.tpl.LoadMoreFooterTpl;
import oms.mmc.android.fast.framwork.widget.pulltorefresh.helper.IDataAdapter;
import oms.mmc.android.fast.framwork.widget.pulltorefresh.helper.IDataSource;
import oms.mmc.android.fast.framwork.widget.pulltorefresh.helper.RecyclerViewViewHelper;

public class BaseListAdapter<T> extends RecyclerView.Adapter<BaseTpl.ViewHolder> implements IDataAdapter<ArrayList<T>>, Filterable, StickyHeaders, StickyHeaders.ViewSetup {
    /**
     * 不使用粘性头部
     */
    public static final int NOT_STICKY_SECTION = -1;
    /**
     * 粘性条目的类型，默认没有粘性头部
     */
    public int stickySectionViewType = NOT_STICKY_SECTION;
    /**
     * 加载更多尾部条目类型
     */
    public static final int TPL_LOAD_MORE_FOOTER = 1000;
    /**
     * 没有选中
     */
    public static final int NOT_CHECK = -1;
    /**
     * 普通模式
     */
    public static final int MODE_NORMAL = 0;
    /**
     * 管理模式
     */
    public static final int MODE_EDIT = 1;

    protected BaseActivity activity;
    protected RecyclerViewViewHelper<T> recyclerViewHelper;
    /**
     * 条目类的类型和条目类class的映射
     */
    protected HashMap<Integer, Class> viewTypeClassMap;
    /**
     * 数据集
     */
    protected IDataSource<T> listViewDataSource;
    /**
     * 列表数据
     */
    protected ArrayList<T> listViewData;
    /**
     * 原始的列表数据
     */
    protected ArrayList<T> originData;
    protected RecyclerView recyclerView;
    /**
     * 多选时使用
     */
    protected ArrayList<Integer> checkedItemPositions = new ArrayList<Integer>();
    /**
     * 单选时使用
     */
    protected int checkedItemPosition = NOT_CHECK;
    /**
     * 当前的选择模式
     */
    protected int mode = MODE_NORMAL;
    /**
     * 加载更多条目的位置
     */
    private int loadMoreTplPosition;
    /**
     * 点击事件
     */
    protected ArrayList<OnRecyclerViewItemClickListener> onItemClickListeners = new ArrayList<OnRecyclerViewItemClickListener>();
    protected ArrayList<OnRecyclerViewItemLongClickListener> onItemLongClickListener = new ArrayList<OnRecyclerViewItemLongClickListener>();
    /**
     * 是否存在头部和尾部
     */
    private boolean hasHeader = false;
    private boolean hasFooter = false;
    /**
     * 可用于保存临时数据的容器
     */
    protected Map<String, Object> tagList = new ArrayMap<String, Object>();
    /**
     * 过滤器
     */
    private Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            results.values = originData;
            results.count = originData.size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            listViewData = (ArrayList<T>) results.values;
            notifyDataSetChanged();
        }
    };

    public BaseListAdapter(RecyclerView recyclerView, BaseActivity activity, IDataSource<T> dataSource
            , HashMap<Integer, Class> itemViewClazzMap, RecyclerViewViewHelper recyclerViewHelper, int stickySectionViewType) {
        this.recyclerView = recyclerView;
        this.activity = activity;
        this.listViewDataSource = dataSource;
        this.listViewData = dataSource.getOriginListViewData();
        this.originData = this.listViewData;
        this.viewTypeClassMap = itemViewClazzMap;
        this.recyclerViewHelper = recyclerViewHelper;
        this.stickySectionViewType = stickySectionViewType;
        initListener();
    }

    @Override
    public BaseTpl.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        BaseTpl.ViewHolder viewHolder = null;
        try {
            //反射构造条目类
            BaseTpl tpl = (BaseTpl) viewTypeClassMap.get(viewType).getConstructor().newInstance();
            tpl.init(activity, recyclerView, viewType);
            viewHolder = tpl.getViewHolder();
            viewHolder.itemView.setTag(R.id.tag_tpl, tpl);
            tpl.config(this, listViewData, listViewDataSource, recyclerViewHelper);
            if (onItemClickListeners.size() > 0) {
                tpl.getRoot().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (onItemClickListeners != null) {
                            for (OnRecyclerViewItemClickListener clickListener : onItemClickListeners) {
                                clickListener.onItemClick(view);
                            }
                        }
                    }
                });
            }
            if (onItemLongClickListener.size() > 0) {
                tpl.getRoot().setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        if (onItemLongClickListener != null) {
                            for (OnRecyclerViewItemLongClickListener longClickListener : onItemLongClickListener) {
                                longClickListener.onItemLongClick(view);
                            }
                        }
                        return true;
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(BaseTpl.ViewHolder holder, int position) {
        BaseTpl view = (BaseTpl) holder.itemView.getTag(R.id.tag_tpl);
        view.setBeanPosition(listViewData, getItem(position), position);
        try {
            view.render();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public T getItem(int position) {
        return listViewData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return listViewData.size();
    }

    @Override
    public int getItemViewType(int position) {
        BaseItemData base = (BaseItemData) getItem(position);
        return base.getViewType();
    }

    @Override
    public void setListViewData(ArrayList<T> res, boolean isRefresh) {
        //每次刷新前，重新确认尾部位置
        if (listViewData.size() == 0) {
            loadMoreTplPosition = 0;
        } else {
            loadMoreTplPosition = listViewData.size() - 1;
        }
        if (isRefresh) {
            //第一次刷新
            if (listViewData.size() == 1 && isAddLoaderMoreItem()) {
                if (!hasFooter) {
                    this.addFooter(TPL_LOAD_MORE_FOOTER, LoadMoreFooterTpl.class,
                            new ItemDataWrapper(TPL_LOAD_MORE_FOOTER));
                }
                listViewData.addAll(0, res);
            } else {
                //不是第一次刷新
                T footLoaderMoreTpl = listViewData.get(loadMoreTplPosition);
                this.listViewData.clear();
                this.listViewData.addAll(res);
                this.listViewData.add(footLoaderMoreTpl);
            }
        } else {
            //上拉加载更多，插入在尾部之前
            this.listViewData.addAll(loadMoreTplPosition, res);
        }
    }

    @Override
    public boolean isEmpty() {
        if (this.getItemCount() == 1 && hasFooter) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean isAddLoaderMoreItem() {
        return findLoaderMoreFootTpl() != null;
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
    public void setupStickyHeaderView(View stickyHeader) {
        Object tag = stickyHeader.getTag(R.id.tag_tpl);
        if (tag != null && tag instanceof BaseStickyTpl) {
            BaseStickyTpl tpl = (BaseStickyTpl) tag;
            tpl.onAttachSticky();
        }
        ViewCompat.setElevation(stickyHeader, 10);
    }

    @Override
    public void teardownStickyHeaderView(View stickyHeader) {
        Object tag = stickyHeader.getTag(R.id.tag_tpl);
        if (tag != null && tag instanceof BaseStickyTpl) {
            BaseStickyTpl tpl = (BaseStickyTpl) tag;
            tpl.onDetachedSticky();
        }
        ViewCompat.setElevation(stickyHeader, 0);
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

    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view);
    }

    public interface OnRecyclerViewItemLongClickListener {
        boolean onItemLongClick(View view);
    }

    public void setListViewData(ArrayList<T> listViewData) {
        this.listViewData = listViewData;
    }

    public void setRecyclerViewHelper(RecyclerViewViewHelper recyclerViewHelper) {
        this.recyclerViewHelper = recyclerViewHelper;
    }

    /**
     * 初始化监听器
     */
    private void initListener() {
        addOnItemClickListener(new OnRecyclerViewItemClickListener() {

            @Override
            public void onItemClick(View view) {
                BaseTpl tpl = (BaseTpl) view.getTag(R.id.tag_tpl);
                tpl.onItemClick(view);
            }
        });
        addOnItemLongClickListener(new OnRecyclerViewItemLongClickListener() {

            @Override
            public boolean onItemLongClick(View view) {
                BaseTpl tpl = (BaseTpl) view.getTag(R.id.tag_tpl);
                tpl.onItemLongClick(view);
                return true;
            }
        });
    }

    /**
     * 添加条目点击事件
     */
    public void addOnItemClickListener(OnRecyclerViewItemClickListener onItemClickListeners) {
        this.onItemClickListeners.add(onItemClickListeners);
    }

    /**
     * 添加条目长按事件
     */
    public void addOnItemLongClickListener(OnRecyclerViewItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener.add(onItemLongClickListener);
    }

    /**
     * 获取所有数据，包括header和footer
     */
    @Override
    public ArrayList<T> getListViewData() {
        return listViewData;
    }

    /**
     * 获取纯数据 (不包含 Header 和 Footer)
     */
    public List<T> getDatas() {
        int startIndex = 0;
        int endIndex = listViewData.size();
        if (hasHeader) {
            startIndex++;
        }
        if (hasFooter) {
            endIndex--;
        }
        return listViewData.subList(startIndex, endIndex);
    }

    /**
     * 添加头部
     */
    public void addHeader(int viewType, Class headerTplClazz, BaseItemData headerData) {
        if (headerTplClazz == null) {
            throw new RuntimeException("headerTpl must not null");
        }
        if (viewTypeClassMap.get(viewType) != null) {
            return;
        }
        viewTypeClassMap.put(viewType, headerTplClazz);
        hasHeader = true;
        this.listViewData.add(0, (T) headerData);
        notifyDataSetChanged();
    }

    /**
     * 添加尾部
     */
    public void addFooter(int viewType, Class footerTplClazz, BaseItemData footerData) {
        if (footerTplClazz == null) {
            throw new RuntimeException("footerTpl must not null");
        }
        if (viewTypeClassMap.get(viewType) != null) {
            return;
        }
        viewTypeClassMap.put(viewType, footerTplClazz);
        if (listViewData.size() == 0) {
            this.listViewData.add(0, (T) footerData);
        } else {
            this.listViewData.add(listViewData.size(), (T) footerData);
        }
        hasFooter = true;
        notifyDataSetChanged();
    }

    public void addLoaderMoreFooterItem() {
        if (hasFooter) {
            return;
        }
        this.addFooter(TPL_LOAD_MORE_FOOTER, LoadMoreFooterTpl.class,
                new ItemDataWrapper(TPL_LOAD_MORE_FOOTER));
    }

    /**
     * 获取加载更多尾部条目
     */
    public BaseTpl getLoadMoreFooter() {
        return (BaseTpl) listViewData.get(loadMoreTplPosition);
    }

    /**
     * 移除加载更多尾部条目
     */
    public void removeLoadMoreFooter() {
        if (!hasFooter) {
            return;
        }
        this.listViewData.remove(loadMoreTplPosition);
        hasFooter = false;
        notifyDataSetChanged();
    }

    /**
     * 获取加载更多尾部的position
     */
    public int getLoadMoreFooterItemPosition() {
        return loadMoreTplPosition;
    }

    /**
     * 查找加载更多尾部条目
     */
    public LoadMoreFooterTpl findLoaderMoreFootTpl() {
        View itemView = recyclerView.getLayoutManager().findViewByPosition(loadMoreTplPosition);
        if (itemView != null) {
            BaseTpl tpl = (BaseTpl) itemView.getTag(R.id.tag_tpl);
            if (tpl.itemViewType == TPL_LOAD_MORE_FOOTER) {
                return (LoadMoreFooterTpl) tpl;
            }
        }
        return null;
    }

    /**
     * 用键值对，设置一个Tag
     */
    public void putTag(String key, Object value) {
        if (tagList == null) {
            tagList = new ArrayMap<String, Object>();
        }
        tagList.put(key, value);
    }

    /**
     * 取出一个Tag
     */
    public Object getTag(String key) {
        if (tagList == null) {
            return null;
        }
        return tagList.get(key);
    }

    /**
     * 移除一个Tag
     */
    public Object removeTag(String key) {
        if (tagList == null) {
            return null;
        }
        return tagList.remove(key);
    }

    /**
     * 获取所有的Tag
     */
    public Map<String, Object> getTagList() {
        return tagList;
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    public void setFilter(Filter filter) {
        this.filter = filter;
    }

    public ArrayList<T> getOriginData() {
        return originData;
    }

    public int getCheckedItemPosition() {
        return checkedItemPosition;
    }

    public void setCheckedItemPosition(int checkedItemPosition) {
        this.checkedItemPosition = checkedItemPosition;
    }

    public ArrayList<Integer> getCheckedItemPositions() {
        return checkedItemPositions;
    }

    public void setCheckedItemPositions(ArrayList<Integer> checkedItemPositions) {
        this.checkedItemPositions = checkedItemPositions;
    }

    /**
     * 设置模式
     */
    public void setMode(int mode) {
        this.mode = mode;
    }

    /**
     * 获取当前模式
     */
    public int getMode() {
        return mode;
    }

    public boolean isEditMode() {
        return getMode() == BaseListAdapter.MODE_EDIT;
    }

    public boolean isNormalMode() {
        return getMode() == BaseListAdapter.MODE_NORMAL;
    }
}
