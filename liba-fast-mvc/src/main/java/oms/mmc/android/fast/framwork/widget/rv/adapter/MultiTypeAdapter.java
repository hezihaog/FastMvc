package oms.mmc.android.fast.framwork.widget.rv.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import oms.mmc.android.fast.framwork.R;
import oms.mmc.android.fast.framwork.adapter.SimpleAttachStateChangeListener;
import oms.mmc.android.fast.framwork.base.BaseActivity;
import oms.mmc.android.fast.framwork.util.IDataSource;
import oms.mmc.android.fast.framwork.util.RecyclerViewViewHelper;
import oms.mmc.android.fast.framwork.widget.rv.base.BaseItemData;
import oms.mmc.android.fast.framwork.widget.rv.base.BaseTpl;

/**
 * Package: oms.mmc.android.fast.framwork.base
 * FileName: MultiTypeAdapter
 * Date: on 2018/2/12  下午2:54
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */

public abstract class MultiTypeAdapter<T extends BaseItemData> extends AssistRecyclerAdapter<BaseTpl.ViewHolder> implements IMultiTypeAdapter {
    private RecyclerView mRecyclerView;
    private BaseActivity mActivity;
    /**
     * 条目类的类型和条目类class的映射
     */
    private HashMap<Integer, Class> viewTypeClassMap;
    /**
     * 数据集
     */
    private IDataSource<T> listViewDataSource;
    /**
     * 列表数据
     */
    private ArrayList<T> mListViewData;
    /**
     * 原始的列表数据
     */
    private ArrayList<T> originData;
    /**
     * 点击事件
     */
    private ArrayList<OnRecyclerViewItemClickListener> onItemClickListeners = new ArrayList<OnRecyclerViewItemClickListener>();
    private ArrayList<OnRecyclerViewItemLongClickListener> onItemLongClickListener = new ArrayList<OnRecyclerViewItemLongClickListener>();

    private RecyclerViewViewHelper<T> recyclerViewHelper;

    public MultiTypeAdapter(RecyclerView recyclerView, BaseActivity activity, IDataSource<T> dataSource
            , HashMap<Integer, Class> itemViewClazzMap, RecyclerViewViewHelper recyclerViewHelper) {
        this.mRecyclerView = recyclerView;
        this.mActivity = activity;
        this.listViewDataSource = dataSource;
        this.mListViewData = dataSource.getOriginListViewData();
        this.originData = this.mListViewData;
        this.viewTypeClassMap = itemViewClazzMap;
        this.recyclerViewHelper = recyclerViewHelper;
        initListener();
    }

    /**
     * 初始化监听器
     */
    private void initListener() {
        addOnItemClickListener(new OnRecyclerViewItemClickListener() {

            @Override
            public void onItemClick(View view, BaseTpl clickTpl, int position) {
                BaseTpl tpl = (BaseTpl) view.getTag(R.id.tag_tpl);
                tpl.onItemClick(view, position);
            }
        });
        addOnItemLongClickListener(new OnRecyclerViewItemLongClickListener() {

            @Override
            public boolean onItemLongClick(View view, BaseTpl longClickTpl, int position) {
                BaseTpl tpl = (BaseTpl) view.getTag(R.id.tag_tpl);
                tpl.onItemLongClick(view, position);
                return true;
            }
        });
        mRecyclerView.addOnAttachStateChangeListener(new SimpleAttachStateChangeListener() {
            @Override
            public void onViewDetachedFromWindow(View v) {
                int allItemCount = mRecyclerView.getAdapter().getItemCount();
                for (int i = 0; i < allItemCount; i++) {
                    RecyclerView.ViewHolder holder = mRecyclerView.findViewHolderForAdapterPosition(i);
                    if (holder != null && holder.itemView != null) {
                        BaseTpl tpl = (BaseTpl) holder.itemView.getTag(R.id.tag_tpl);
                        tpl.onRecyclerViewDetachedFromWindow(v);
                    }
                }
            }
        });
    }

    @Override
    public BaseTpl.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        BaseTpl.ViewHolder viewHolder = null;
        try {
            //反射构造条目类
            BaseTpl tpl = (BaseTpl) viewTypeClassMap.get(viewType).getConstructor().newInstance();
            tpl.init(mActivity, mRecyclerView, viewType);
            viewHolder = tpl.getViewHolder();
            viewHolder.itemView.setTag(R.id.tag_tpl, tpl);
            tpl.config(this, mListViewData, listViewDataSource, recyclerViewHelper);
            if (onItemClickListeners.size() > 0) {
                tpl.getRoot().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (onItemClickListeners != null) {
                            for (OnRecyclerViewItemClickListener clickListener : onItemClickListeners) {
                                BaseTpl clickTpl = (BaseTpl) view.getTag(R.id.tag_tpl);
                                clickListener.onItemClick(view, clickTpl, clickTpl.getPosition());
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
                            BaseTpl longClickTpl = (BaseTpl) view.getTag(R.id.tag_tpl);
                            for (OnRecyclerViewItemLongClickListener longClickListener : onItemLongClickListener) {
                                longClickListener.onItemLongClick(view, longClickTpl, longClickTpl.getPosition());
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
        BaseTpl tpl = (BaseTpl) holder.itemView.getTag(R.id.tag_tpl);
        tpl.setBeanPosition(mListViewData, getItem(position), position);
        try {
            tpl.render();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public T getItem(int position) {
        return mListViewData.get(position);
    }

    @Override
    public int getItemCount() {
        return mListViewData.size();
    }

    @Override
    public int getItemViewType(int position) {
        BaseItemData base = (BaseItemData) getItem(position);
        return base.getViewType();
    }

    @Override
    public List<T> getOriginData() {
        return originData;
    }

    @Override
    public void addOnItemClickListener(OnRecyclerViewItemClickListener onItemClickListeners) {
        this.onItemClickListeners.add(onItemClickListeners);
    }

    @Override
    public void addOnItemLongClickListener(OnRecyclerViewItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener.add(onItemLongClickListener);
    }

    @Override
    public RecyclerView getRecyclerView() {
        return mRecyclerView;
    }

    @Override
    public RecyclerViewViewHelper<T> getRecyclerViewHelper() {
        return recyclerViewHelper;
    }

    @Override
    public IDataSource<T> getListViewDataSource() {
        return listViewDataSource;
    }

    @Override
    public HashMap<Integer, Class> getViewTypeClassMap() {
        return viewTypeClassMap;
    }

    @Override
    public void setListData(ArrayList listData) {
        this.mListViewData = listData;
    }

    @Override
    public ArrayList<T> getListData() {
        return mListViewData;
    }

    @Override
    public void setRecyclerViewHelper(RecyclerViewViewHelper helper) {
        this.recyclerViewHelper = helper;
    }
}