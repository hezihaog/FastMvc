package oms.mmc.android.fast.framwork.widget.rv.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Constructor;
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
import oms.mmc.android.fast.framwork.widget.rv.base.BaseItemData;
import oms.mmc.android.fast.framwork.widget.rv.base.BaseTpl;
import oms.mmc.factory.wait.inter.IWaitViewHost;
import oms.mmc.helper.ListScrollHelper;

/**
 * Package: oms.mmc.android.fast.framwork.base
 * FileName: MultiTypeAdapter
 * Date: on 2018/2/12  下午2:54
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */

public abstract class MultiTypeAdapter<T extends BaseItemData> extends AssistRecyclerAdapter<BaseTpl.ViewHolder> implements IMultiTypeAdapter {
    private static final String TAG = MultiTypeAdapter.class.getSimpleName();
    private Activity mActivity;
    private RecyclerView mRecyclerView;
    /**
     * 条目类的类型和条目类class的映射
     */
    private HashMap<Integer, Class> viewTypeClassMap;
    /**
     * 数据集
     */
    private IDataSource<T> mListViewDataSource;
    /**
     * 列表数据
     */
    private ArrayList<T> mListViewData;
    /**
     * 等待弹窗依赖的宿主
     */
    private IWaitViewHost mWaitViewHost;
    /**
     * 点击事件
     */
    private ArrayList<OnRecyclerViewItemClickListener> onItemClickListeners = new ArrayList<OnRecyclerViewItemClickListener>();
    /**
     * 长按事件
     */
    private ArrayList<OnRecyclerViewItemLongClickListener> onItemLongClickListener = new ArrayList<OnRecyclerViewItemLongClickListener>();
    /**
     * rv帮助类
     */
    private RecyclerViewViewHelper<T> mRecyclerViewHelper;
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

    public MultiTypeAdapter(RecyclerView recyclerView, Activity activity, IDataSource<T> dataSource
            , HashMap<Integer, Class> itemViewClazzMap, RecyclerViewViewHelper recyclerViewHelper, IWaitViewHost waitViewHost) {
        this.mToastOperator = new ToastOperator(activity);
        this.mFragmentOperator = new FragmentOperator(activity);
        this.mRecyclerView = recyclerView;
        this.mActivity = activity;
        this.mListViewDataSource = dataSource;
        this.mListViewData = dataSource.getListData();
        this.mWaitViewHost = waitViewHost;
        this.viewTypeClassMap = itemViewClazzMap;
        this.mRecyclerViewHelper = recyclerViewHelper;
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
                        if (tpl != null) {
                            tpl.onRecyclerViewDetachedFromWindow(v);
                        }
                    }
                }
            }
        });
    }

    @Override
    public BaseTpl.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        BaseTpl.ViewHolder viewHolder;
        //反射构造条目类
        Constructor constructor = null;
        try {
            constructor = viewTypeClassMap.get(viewType).getConstructor();
            constructor.setAccessible(true);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            Log.e(TAG, "无法获取Tpl构造方法，请注意不能修改省略无参构造方法");
        }
        BaseTpl tpl = null;
        try {
            tpl = (BaseTpl) constructor.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "反射实例化Tpl出错，请查看Tpl构造方法是否是无参");
        }
        if (tpl == null) {
            throw new NullPointerException("反射实例化Tpl失败，请检查Tpl构造方法是否公开，并且不是非静态匿名内部类");
        }
        tpl.init(mActivity, mRecyclerView, mToastOperator, mWaitViewHost, mFragmentOperator, viewType);
        viewHolder = tpl.getViewHolder();
        viewHolder.itemView.setTag(R.id.tag_tpl, tpl);
        tpl.config(this, mListViewData, mListViewDataSource, mRecyclerViewHelper, mListScrollHelper);
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
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(BaseTpl.ViewHolder holder, int position) {
        BaseTpl<T> tpl = (BaseTpl<T>) holder.itemView.getTag(R.id.tag_tpl);
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
        return getItem(position).getViewType();
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
        return mRecyclerViewHelper;
    }

    @Override
    public IDataSource<T> getListViewDataSource() {
        return mListViewDataSource;
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
    public void setListScrollHelper(ListScrollHelper listScrollHelper) {
        mListScrollHelper = listScrollHelper;
    }
}