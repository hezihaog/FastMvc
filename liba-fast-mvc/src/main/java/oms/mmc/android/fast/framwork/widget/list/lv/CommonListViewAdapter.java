package oms.mmc.android.fast.framwork.widget.list.lv;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

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
import oms.mmc.android.fast.framwork.widget.lv.ScrollablePinnedSectionListView;
import oms.mmc.android.fast.framwork.widget.rv.base.BaseItemData;
import oms.mmc.android.fast.framwork.widget.rv.base.BaseTpl;
import oms.mmc.factory.wait.inter.IWaitViewHost;
import oms.mmc.helper.ListScrollHelper;
import oms.mmc.helper.base.IScrollableListAdapterView;

/**
 * Created by wally on 18/3/25.
 * ListView通用适配器
 */

public class CommonListViewAdapter extends BaseAdapter implements ICommonListAdapter<BaseItemData>
        , ScrollablePinnedSectionListView.PinnedSectionListAdapter {
    private final CommonListAdapterDelegate mAdapterDelegate;
    private Activity mActivity;
    private IScrollableListAdapterView mScrollableView;
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
    ListHelper mListHelper;
    /**
     * Toast操作类
     */
    private final IToastOperator mToastOperator;
    /**
     * 列表滚动帮助类
     */
    private ListScrollHelper mListScrollHelper;
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

    public CommonListViewAdapter(Activity activity, IDataSource<BaseItemData> dataSource, IScrollableListAdapterView scrollableView, HashMap<Integer, Class> viewTypeClassMap, IWaitViewHost waitViewHost, ListHelper listHelper, int stickySectionViewType) {
        this.mActivity = activity;
        this.mListDataSource = dataSource;
        this.mListData = dataSource.getListData();
        this.mScrollableView = scrollableView;
        this.mViewTypeClassMap = viewTypeClassMap;
        this.mAdapterDelegate = new CommonListAdapterDelegate(mListData, viewTypeClassMap);
        this.mToastOperator = new ToastOperator(activity);
        this.mWaitViewHost = waitViewHost;
        this.mFragmentOperator = new FragmentOperator(activity);
        this.mListHelper = listHelper;
        this.stickySectionViewType = stickySectionViewType;
        this.mListenerDelegate = new AdapterListenerDelegate();
        //开始监听代理
        this.mListenerDelegate.startDelegateAdapterListener(mScrollableView, this);
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
        BaseTpl<BaseItemData> tpl = null;
        if (convertView == null) {
            int viewType = mAdapterDelegate.getListItemViewType(position);
            tpl = mAdapterDelegate.createTpl(viewType);
            tpl.init(mActivity, mScrollableView, mToastOperator, mWaitViewHost, mFragmentOperator, getAssistHelper(), viewType);
            tpl.config(this, mListData, mListDataSource, mListHelper, mListScrollHelper);
            convertView = tpl.getRootView();
            convertView.setTag(R.id.tag_tpl, tpl);
        }
        tpl = (BaseTpl<BaseItemData>) convertView.getTag(R.id.tag_tpl);
        tpl.setBeanPosition(mListData, (BaseItemData) getItem(position), position);
        try {
            tpl.render();
        } catch (Throwable e) {
            e.printStackTrace();
        }
        if (mListenerDelegate.hasItemClickListener()) {
            tpl.getRoot().setOnClickListener(mOnClickListener);
        }
        if (mListenerDelegate.hasItemLongClickListener()) {
            tpl.getRoot().setOnLongClickListener(mOnLongClickListener);
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
        return viewType == stickySectionViewType;
    }

    @Override
    public int getListItemCount() {
        return getCount();
    }
}