package oms.mmc.android.fast.framwork.base;

import android.support.v4.util.ArrayMap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ListView;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import oms.mmc.android.fast.framwork.bean.BaseItemData;
import oms.mmc.android.fast.framwork.widget.pulltorefresh.PinnedSectionListView;
import oms.mmc.android.fast.framwork.widget.pulltorefresh.helper.IDataAdapter;
import oms.mmc.android.fast.framwork.widget.pulltorefresh.helper.IDataSource;
import oms.mmc.android.fast.framwork.widget.pulltorefresh.helper.ListViewHelper;

public class BaseListAdapter<T> extends BaseAdapter implements IDataAdapter<ArrayList<T>>, PinnedSectionListView.PinnedSectionListAdapter, Filterable {
    //粘性条目的类型
    public int stickySectionViewType = 0;
    //管理状态
    public static final int MODE_NORMAL = 0;
    public static final int MODE_EDIT = 1;

    protected BaseActivity _activity;
    protected ListViewHelper<T> listViewHelper;
    protected HashMap<Integer, Class> viewTypeClasseMap;
    protected IDataSource<T> listViewDataSource;
    protected ArrayList<T> listViewData;
    protected ArrayList<T> originData;
    protected AbsListView listView;
    protected int checkedItemPosition = -1;
    protected ArrayList<Integer> checkedItemPositions = new ArrayList<Integer>();
    protected int mode = MODE_NORMAL;
    protected Runnable notifyRunnable;
    protected OnItemClickListener onItemClickListener;
    protected OnItemLongClickListener onItemLongClickListener;

    /**
     * 可用于保存临时数据的容器
     */
    protected Map<String, Object> tags;
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

    public BaseListAdapter(AbsListView absListView, BaseActivity activity, IDataSource<T> dataSource
            , HashMap<Integer, Class> itemViewClazzMap, ListViewHelper listViewHelper, int stickySectionViewType) {
        this.listView = absListView;
        this._activity = activity;
        this.listViewDataSource = dataSource;
        this.listViewData = dataSource.getOriginListViewData();
        this.originData = this.listViewData;
        this.viewTypeClasseMap = itemViewClazzMap;
        this.listViewHelper = listViewHelper;
        this.stickySectionViewType = stickySectionViewType;
        initListener();
    }

    @Override
    public int getCount() {
        return listViewData.size();
    }

    @Override
    public T getItem(int position) {
        return listViewData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        return viewTypeClasseMap.size();
    }

    @Override
    public int getItemViewType(int position) {
        BaseItemData base = (BaseItemData) getItem(position);
        return base.getViewType();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            try {
                //取出当前条目的类型
                int itemViewType = getItemViewType(position);
                //反射构造条目类
                BaseTpl baseTpl = (BaseTpl) viewTypeClasseMap.get(itemViewType).getConstructor().newInstance();
                baseTpl.init(_activity, getItemViewType(position));
                convertView = baseTpl.getRoot();
                convertView.setTag(baseTpl);
                baseTpl.config(this, listViewData, listViewDataSource, listView, listViewHelper);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (convertView != null) {
            BaseTpl view = (BaseTpl) convertView.getTag();
            view.setBeanPosition(listViewData, getItem(position), position);
            try {
                view.render();
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
        return convertView;
    }

    @Override
    public void setListViewData(ArrayList<T> res, boolean isRefresh, boolean isReverse) {
        if (isRefresh) {
            this.listViewData.clear();
        }
        if (isReverse) {
            this.listViewData.addAll(0, res);
        } else {
            this.listViewData.addAll(res);
        }
    }

    @Override
    public ArrayList<T> getListViewData() {
        return listViewData;
    }

    public void setListViewData(ArrayList<T> listViewData) {
        this.listViewData = listViewData;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        if (listViewHelper != null) {
            if (getCount() == 0) {
                if (listView != null && ((ListView) listView).getHeaderViewsCount() > 0) {
                    listViewHelper.getLoadView().restore();
                } else {
                    listViewHelper.getLoadView().showEmpty();
                }
            } else {
                listViewHelper.getLoadView().restore();
            }
        }
        if (notifyRunnable != null) {
            notifyRunnable.run();
        }
    }

    public Runnable getNotifyRunnable() {
        return notifyRunnable;
    }

    public void setNotifyRunnable(Runnable runnable) {
        this.notifyRunnable = runnable;
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

    private void initListener() {
        listView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(parent, view, position, id);
                }
                try {
                    Method method = Class.forName(BaseTpl.class.getName()).getDeclaredMethod("onItemClick");
                    method.setAccessible(true);
                    method.invoke(view.getTag());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        listView.setOnItemLongClickListener(new OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if (onItemLongClickListener != null) {
                    onItemLongClickListener.onItemLongClick(parent, view, position, id);
                }
                try {
                    Method method = Class.forName(BaseTpl.class.getName()).getDeclaredMethod("onItemLongClick");
                    method.setAccessible(true);
                    method.invoke(view.getTag());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return true;
            }
        });
    }

    public OnItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public OnItemLongClickListener getOnItemLongClickListener() {
        return onItemLongClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
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
    public boolean isItemViewTypePinned(int viewType) {
        return viewType == stickySectionViewType;
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
}
