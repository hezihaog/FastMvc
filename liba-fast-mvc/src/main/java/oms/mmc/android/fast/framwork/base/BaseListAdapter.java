package oms.mmc.android.fast.framwork.base;

import android.support.v4.util.ArrayMap;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import java.lang.reflect.Method;
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
    //不使用粘性头部
    public static final int NOT_STICKY_SECTION = -1;
    //粘性条目的类型
    public int stickySectionViewType = NOT_STICKY_SECTION;
    //加载更多尾部条目类型
    public static final int TPL_LOAD_MORE_FOOTER = 1000;

    //管理、普通状态
    public static final int MODE_NORMAL = 0;
    public static final int MODE_EDIT = 1;

    protected RecyclerView recyclerView;

    protected BaseActivity _activity;
    protected RecyclerViewViewHelper<T> recyclerViewHelper;
    protected HashMap<Integer, Class> viewTypeClassMap;
    protected IDataSource<T> listViewDataSource;
    protected ArrayList<T> listViewData;
    protected ArrayList<T> originData;

    protected ArrayList<Integer> checkedItemPositions = new ArrayList<Integer>();
    protected int checkedItemPosition = -1;
    protected int mode = MODE_NORMAL;
    private int footerTplPosition;

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
    protected Map<String, Object> tags = new ArrayMap<String, Object>();

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
        this._activity = activity;
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
            BaseTpl baseTpl = (BaseTpl) viewTypeClassMap.get(viewType).getConstructor().newInstance();
            baseTpl.init(_activity, recyclerView, viewType);
            viewHolder = baseTpl.getViewHolder();
            viewHolder.itemView.setTag(R.id.tag_tpl, baseTpl);
            baseTpl.config(this, listViewData, listViewDataSource, recyclerViewHelper);
            if (onItemClickListeners.size() > 0) {
                baseTpl.getRoot().setOnClickListener(new View.OnClickListener() {
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
                baseTpl.getRoot().setOnLongClickListener(new View.OnLongClickListener() {
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
//        if (view.itemViewType == TPL_LOAD_MORE_FOOTER) {
//            footerTplPosition = position;
//        }
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
            footerTplPosition = 0;
        } else {
            footerTplPosition = listViewData.size() - 1;
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
                T footLoaderMoreTpl = listViewData.get(footerTplPosition);
                this.listViewData.clear();
                this.listViewData.addAll(res);
                this.listViewData.add(footLoaderMoreTpl);
            }
        } else {
            //上拉加载更多，插入在尾部之前
            this.listViewData.addAll(footerTplPosition, res);
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
        ViewCompat.setElevation(stickyHeader, 10);
    }

    @Override
    public void teardownStickyHeaderView(View stickyHeader) {
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
        addOnItemClickListeners(new OnRecyclerViewItemClickListener() {

            @Override
            public void onItemClick(View view) {
                try {
                    Method method = Class.forName(BaseTpl.class.getName()).getDeclaredMethod("onItemClick");
                    method.setAccessible(true);
                    method.invoke(view.getTag(R.id.tag_tpl));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        addOnItemLongClickListener(new OnRecyclerViewItemLongClickListener() {

            @Override
            public boolean onItemLongClick(View view) {
                try {
                    Method method = Class.forName(BaseTpl.class.getName()).getDeclaredMethod("onItemLongClick");
                    method.setAccessible(true);
                    method.invoke(view.getTag(R.id.tag_tpl));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return true;
            }
        });
    }

    /**
     * 添加条目点击事件
     */
    public void addOnItemClickListeners(OnRecyclerViewItemClickListener onItemClickListeners) {
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
     * 获取尾部
     */
    public BaseTpl getFooter() {
        return (BaseTpl) listViewData.get(footerTplPosition);
    }

    /**
     * 移除尾部
     */
    public void removeFooter() {
        if (!hasFooter) {
            return;
        }
        this.listViewData.remove(footerTplPosition);
        hasFooter = false;
        notifyDataSetChanged();
    }

    /**
     * 获取加载更多尾部的position
     */
    public int getLoadMoreFooterItemPosition() {
        return footerTplPosition;
    }

    /**
     * 查找加载更多尾部条目
     */
    public LoadMoreFooterTpl findLoaderMoreFootTpl() {
        View itemView = recyclerView.getLayoutManager().findViewByPosition(footerTplPosition);
        if (itemView != null) {
            BaseTpl tpl = (BaseTpl) itemView.getTag(R.id.tag_tpl);
            if (tpl.itemViewType == TPL_LOAD_MORE_FOOTER) {
                return (LoadMoreFooterTpl) tpl;
            }
        }
        return null;
    }

    public void putTag(String key, Object value) {
        if (tags == null) {
            tags = new ArrayMap<String, Object>();
        }
        tags.put(key, value);
    }

    public Object getTag(String key) {
        if (tags == null) {
            return null;
        }
        return tags.get(key);
    }

    public Object removeTag(String key) {
        if (tags == null) {
            return null;
        }
        return tags.remove(key);
    }

    public Map<String, Object> getTags() {
        return tags;
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

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }
}
