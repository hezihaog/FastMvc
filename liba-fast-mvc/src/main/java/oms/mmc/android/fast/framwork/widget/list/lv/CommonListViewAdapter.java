package oms.mmc.android.fast.framwork.widget.list.lv;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

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
import oms.mmc.android.fast.framwork.widget.lv.PinnedSectionListView;
import oms.mmc.android.fast.framwork.widget.rv.base.BaseItemData;
import oms.mmc.android.fast.framwork.widget.rv.base.BaseTpl;
import oms.mmc.factory.wait.inter.IWaitViewHost;
import oms.mmc.helper.ListScrollHelper;
import oms.mmc.helper.widget.ScrollableListView;

/**
 * Created by wally on 18/3/25.
 * ListView通用适配器
 */

public class CommonListViewAdapter extends BaseAdapter implements ICommonListAdapter<BaseItemData>, PinnedSectionListView.PinnedSectionListAdapter {
    private final CommonListAdapterDelegate mAdapterDelegate;
    private Activity mActivity;
    private ScrollableListView mScrollableView;
    /**
     * 等待弹窗依赖的宿主
     */
    private IWaitViewHost mWaitViewHost;
    /**
     * Fragment操作类
     */
    private final IFragmentOperator mFragmentOperator;
    /**
     * 数据集
     */
    private IDataSource<BaseItemData> mListDataSource;
    /**
     * 数据
     */
    private ArrayList<BaseItemData> mListData;
    /**
     *
     */
    private HashMap<Integer, Class> mViewTypeClassMap;
    /**
     * rv帮助类
     */
    RecyclerViewViewHelper mRecyclerViewHelper;
    /**
     * Toast操作类
     */
    private final IToastOperator mToastOperator;
    /**
     * 列表滚动帮助类
     */
    private ListScrollHelper mListScrollHelper;
    /**
     * 点击事件
     */
    private ArrayList<OnScrollableViewItemClickListener> onItemClickListeners = new ArrayList<OnScrollableViewItemClickListener>();
    /**
     * 长按事件
     */
    private ArrayList<OnScrollableViewItemLongClickListener> onItemLongClickListeners = new ArrayList<OnScrollableViewItemLongClickListener>();
    private IAssistHelper mAssistHelper;

    public CommonListViewAdapter(Activity activity, IDataSource<BaseItemData> dataSource, ScrollableListView scrollableView,
                                 HashMap<Integer, Class> viewTypeClassMap, IWaitViewHost waitViewHost,
                                 RecyclerViewViewHelper recyclerViewHelper) {
        this.mActivity = activity;
        this.mListDataSource = dataSource;
        this.mListData = dataSource.getListData();
        this.mScrollableView = scrollableView;
        this.mViewTypeClassMap = viewTypeClassMap;
        this.mAdapterDelegate = new CommonListAdapterDelegate(mListData, viewTypeClassMap);
        this.mToastOperator = new ToastOperator(activity);
        this.mWaitViewHost = waitViewHost;
        this.mFragmentOperator = new FragmentOperator(activity);
        this.mRecyclerViewHelper = recyclerViewHelper;
        initListener();
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
                int allItemCount = mScrollableView.getAdapter().getCount();
                for (int i = 0; i < allItemCount; i++) {
                    View view = mScrollableView.getViewByPosition(i, mScrollableView);
                    if (view != null) {
                        BaseTpl tpl = (BaseTpl) view.getTag(R.id.tag_tpl);
                        if (tpl != null) {
                            tpl.onRecyclerViewDetachedFromWindow(v);
                        }
                    }
                }
            }
        });
    }

    @Override
    public int getCount() {
        return mAdapterDelegate.getListItemCount();
    }

    @Override
    public Object getItem(int position) {
        return mAdapterDelegate.getItem(position);
    }

    @Override
    public long getItemId(int position) {
        return mAdapterDelegate.getListItemId(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BaseTpl<BaseItemData> tpl;
        if (convertView == null) {
            int viewType = mAdapterDelegate.getListItemViewType(position);
            tpl = mAdapterDelegate.createTpl(viewType);
            tpl.init(mActivity, mScrollableView, mToastOperator, mWaitViewHost, mFragmentOperator, getAssistHelper(), viewType);
            convertView.setTag(R.id.tag_tpl, tpl);
            tpl.config(this, mListData, mListDataSource, mRecyclerViewHelper, mListScrollHelper);
        } else {
            tpl = (BaseTpl<BaseItemData>) convertView.getTag(R.id.tag_tpl);
            tpl.setBeanPosition(mListData, (BaseItemData) getItem(position), position);
            try {
                tpl.render();
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
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
        return convertView;
    }

    @Override
    public int getViewTypeCount() {
        return mAdapterDelegate.getListItemViewTypeCount();
    }

    @Override
    public int getItemViewType(int position) {
        return mAdapterDelegate.getListItemViewType(position);
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
    public void addOnItemClickListener(OnScrollableViewItemClickListener onItemClickListener) {
        onItemClickListeners.add(onItemClickListener);
    }

    @Override
    public void removeOnItemClickListener(OnScrollableViewItemClickListener onItemClickListener) {
        onItemClickListeners.remove(onItemClickListener);
    }

    @Override
    public void addOnItemLongClickListener(OnScrollableViewItemLongClickListener onItemLongClickListener) {
        onItemLongClickListeners.add(onItemLongClickListener);
    }

    @Override
    public void removeOnItemLongClickListener(OnScrollableViewItemLongClickListener onItemLongClickListener) {
        onItemLongClickListeners.remove(onItemLongClickListener);
    }

    @Override
    public void setAssistHelper(IAssistHelper assistHelper) {
        this.mAssistHelper = assistHelper;
    }

    @Override
    public IAssistHelper getAssistHelper() {
        return mAssistHelper;
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
    public void setListData(ArrayList<BaseItemData> listData) {
        this.mListData = listData;
    }

    @Override
    public ArrayList<BaseItemData> getListData() {
        return mListData;
    }

    @Override
    public boolean isItemViewTypePinned(int viewType) {
        return false;
    }
}