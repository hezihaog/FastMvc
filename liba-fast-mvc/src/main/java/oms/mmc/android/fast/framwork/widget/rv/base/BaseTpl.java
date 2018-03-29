package oms.mmc.android.fast.framwork.widget.rv.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.FrameLayout;

import java.util.List;

import oms.mmc.android.fast.framwork.base.IDataSource;
import oms.mmc.android.fast.framwork.base.IFragmentOperator;
import oms.mmc.android.fast.framwork.base.IInstanceState;
import oms.mmc.android.fast.framwork.base.IWaitViewHandler;
import oms.mmc.android.fast.framwork.base.LayoutCallback;
import oms.mmc.android.fast.framwork.util.IToastOperator;
import oms.mmc.android.fast.framwork.util.IViewFinder;
import oms.mmc.android.fast.framwork.util.MethodCompat;
import oms.mmc.android.fast.framwork.util.ListHelper;
import oms.mmc.android.fast.framwork.util.ViewFinder;
import oms.mmc.android.fast.framwork.widget.TemplateItemWrapper;
import oms.mmc.android.fast.framwork.widget.list.ICommonListAdapter;
import oms.mmc.android.fast.framwork.widget.list.helper.IAssistHelper;
import oms.mmc.factory.wait.inter.IWaitViewHost;
import oms.mmc.helper.ListScrollHelper;
import oms.mmc.helper.base.IScrollableListAdapterView;
import oms.mmc.helper.base.IScrollableView;

/**
 * 列表条目基础模板，条目类
 *
 * @author 子和
 */
public abstract class BaseTpl<T> extends CommonOperationDelegateTpl implements LayoutCallback,
        IWaitViewHandler, View.OnAttachStateChangeListener, IInstanceState {
    private Activity mActivity;
    private ListHelper mListHelper;
    private ListScrollHelper mListScrollHelper;
    private ICommonListAdapter mListAdapter;
    private IDataSource<? extends BaseItemData> mListDataSource;
    private List<? extends BaseItemData> mListData;
    private IScrollableView mScrollableView;
    private IWaitViewHost mWaitViewHost;
    private IAssistHelper mAssistHelper;
    private int mItemViewType = -1;
    private ViewGroup mRoot;
    private int mPosition;
    private T mBean;
    private BaseTpl.ViewHolder mInnerViewHolder;
    private ViewFinder mViewFinder;

    public BaseTpl() {
    }

    /**
     * 初始化
     *
     * @param activity         activity对象
     * @param scrollableView   rv列表
     * @param toastOperator    Toast执行器
     * @param waitViewHost     WaitView依赖的宿主，BaseFastActivity或者BaseFastFragment
     * @param fragmentOperator Fragment操作器，所有的fragment操作都封装到这里
     * @param assistHelper
     * @param itemViewType     当前Tpl的ViewType
     */
    public void init(Activity activity, IScrollableView scrollableView, IToastOperator toastOperator
            , IWaitViewHost waitViewHost, IFragmentOperator fragmentOperator, IAssistHelper assistHelper, int itemViewType) {
        this.mWaitViewHost = waitViewHost;
        this.mAssistHelper = assistHelper;
        this.mItemViewType = itemViewType;
        this.mActivity = activity;
        this.mScrollableView = scrollableView;
        setToastOperator(toastOperator);
        setFragmentOperator(fragmentOperator);
        setBundle(mActivity.getIntent().getExtras());
        initView();
    }

    /**
     * 刷新配置
     *
     * @param adapter            列表适配器
     * @param data               数据集
     * @param dataSource         数据源
     * @param recyclerViewHelper rv帮助类
     * @param listScrollHelper   列表滚动帮助类
     */
    public void config(ICommonListAdapter adapter, List<? extends BaseItemData> data
            , IDataSource<? extends BaseItemData> dataSource, ListHelper recyclerViewHelper, ListScrollHelper listScrollHelper) {
        this.mListAdapter = adapter;
        this.mListDataSource = dataSource;
        this.mListData = data;
        this.mListHelper = recyclerViewHelper;
        this.mListScrollHelper = listScrollHelper;
    }

    /**
     * 初始化条目View
     */
    private void initView() {
        onCreate();
        onLayoutBefore();
        generateItemLayoutWrapper();
        View itemView = onLayoutView(LayoutInflater.from(mActivity), (ViewGroup) mScrollableView);
        mRoot.addView(itemView, new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT));
        mRoot.addOnAttachStateChangeListener(this);
        mViewFinder = new ViewFinder(getActivity(), mRoot);
        onFindView(mViewFinder);
        onBindContent();
        onLayoutAfter();
    }

    private void generateItemLayoutWrapper() {
        mRoot = new TemplateItemWrapper(getActivity());
        mRoot.setId(MethodCompat.generateViewId());
        //ListView、GridView上，item条目的LayoutParams必须是AbsListView的
        if (mScrollableView instanceof IScrollableListAdapterView) {
            mRoot.setLayoutParams(new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.WRAP_CONTENT));
        } else {
            //其他，例如rv就可以直接用ViewGroup的
            mRoot.setLayoutParams(new ViewGroup.MarginLayoutParams(ViewGroup.MarginLayoutParams.MATCH_PARENT, ViewGroup.MarginLayoutParams.WRAP_CONTENT));
        }
    }

    /**
     * 获取内部的ViewHolder
     */
    public BaseTpl.ViewHolder getViewHolder() {
        if (mInnerViewHolder == null) {
            mInnerViewHolder = new ViewHolder(getRootView());
        }
        return mInnerViewHolder;
    }

    /**
     * 获取条目的ItemView视图
     */
    public View getRoot() {
        if (mRoot == null) {
            generateItemLayoutWrapper();
            View itemView = onLayoutView(LayoutInflater.from(mActivity), (ViewGroup) mScrollableView);
            mRoot.addView(itemView, new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT));
        }
        return mRoot;
    }

    @Override
    public IViewFinder getViewFinder() {
        if (mViewFinder == null) {
            mViewFinder = new ViewFinder(getActivity(), getRoot());
        }
        return mViewFinder;
    }

    /**
     * 当创建时回调
     */
    protected void onCreate() {
    }

    /**
     * 销毁时回调
     */
    protected void onDestroy() {

    }

    /**
     * 在创建布局前回调
     */
    @Override
    public void onLayoutBefore() {
    }

    /**
     * 在创建布局后回调
     */
    @Override
    public void onLayoutAfter() {
    }

    /**
     * 获取依赖的Activity
     */
    @Override
    public FragmentActivity getActivity() {
        return (FragmentActivity) mActivity;
    }

    /**
     * 条目点击事件
     *
     * @param view     条目的ItemView
     * @param position 当前条目的position
     */
    public void onItemClick(View view, int position) {

    }

    /**
     * 条目长按事件
     *
     * @param view     条目的ItemView
     * @param position 当前条目的position
     */
    public void onItemLongClick(View view, int position) {

    }

    /**
     * 内部持有Rv的ViewHolder
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

    /**
     * 获取当前条目拥有的条目bean
     *
     * @return
     */
    public T getItemDataBean() {
        return mBean;
    }

    /**
     * 获取当前条目的位置
     */
    public int getPosition() {
        return mPosition;
    }

    /**
     * 获取条目当前依附的的滚动列表
     */
    public IScrollableView getScrollableView() {
        return mScrollableView;
    }

    /**
     * 获取rv帮助类，需要调用刷新、加载更多等函数时，调用该方法获取对象
     *
     * @return
     */
    public ListHelper getListHelper() {
        return mListHelper;
    }

    /**
     * 获取滚动帮助类
     *
     * @return
     */
    public ListScrollHelper getListScrollHelper() {
        return mListScrollHelper;
    }

    public ICommonListAdapter getListAdapter() {
        return mListAdapter;
    }

    /**
     * 设置条目信息，每次adapter的onBindViewHolder时回调
     *
     * @param listData 条目数据
     * @param itemBean 条目bean
     * @param position 条目位置
     */
    public void setBeanPosition(List<? extends BaseItemData> listData, T itemBean, int position) {
        this.mListData = listData;
        this.mBean = itemBean;
        this.mPosition = position;
    }

    /**
     * 每次条目onBindView时，重新设置数据
     */
    public final void render() {
        T bean = getItemDataBean();
        onRender(bean);
    }

    @Override
    public void onFindView(IViewFinder finder) {

    }

    /**
     * onFindView查找控件后，初始化控件时使用，例如ViewPager的adapter，如条目类不需要，则不需要重写
     */
    protected void onBindContent() {

    }

    /**
     * 渲染函数，rv的onBindView时会调用
     *
     * @param itemData 条目类上使用的数据
     */
    protected abstract void onRender(T itemData);

    @Override
    public void showWaitDialog() {
        mWaitViewHost.getWaitViewController().getWaitIml().showWaitDialog(getActivity(), "", false);
    }

    @Override
    public void showWaitDialog(String msg) {
        mWaitViewHost.getWaitViewController().getWaitIml().showWaitDialog(getActivity(), msg, false);
    }

    @Override
    public void showWaitDialog(String msg, final boolean isTouchCancelable) {
        mWaitViewHost.getWaitViewController().getWaitIml().showWaitDialog(getActivity(), msg, isTouchCancelable);
    }

    @Override
    public void hideWaitDialog() {
        mWaitViewHost.getWaitViewController().getWaitIml().hideWaitDialog();
    }

    /**
     * 获取条目的类型
     */
    public int getItemViewType() {
        return mItemViewType;
    }

    /**
     * 获取当前列表的数据集对象
     */
    public IDataSource<? extends BaseItemData> getListDataSource() {
        return mListDataSource;
    }

    /**
     * 获取当前列表的数据集合
     */
    public List<? extends BaseItemData> getListData() {
        return mListData;
    }

    @Override
    public void onViewAttachedToWindow(View v) {
    }

    @Override
    public void onViewDetachedFromWindow(View v) {
    }

    /**
     * 当界面被强制内存回收前回调
     *
     * @param stateBundle 保存的信息的map
     */
    @Override
    public void onSaveState(Bundle stateBundle) {

    }

    /**
     * 当用户重新打开app，界面已经被内存回收了，马上进行恢复时回调
     *
     * @param stateBundle 马上进行恢复时，之前保存的信息的map
     */
    @Override
    public void onRestoreState(Bundle stateBundle) {
        if (mViewFinder == null) {
            mViewFinder = new ViewFinder(getActivity(), mRoot);
        } else {
            if (mViewFinder.getActivity() == null) {
                mViewFinder.setActivity(getActivity());
            }
            if (mViewFinder.getRootView() == null) {
                mViewFinder.setRootView(getRoot());
            }
        }
    }

    /**
     * RecyclerView从窗口移除时回调
     */
    @CallSuper
    public void onRecyclerViewDetachedFromWindow(View view) {
        onDestroy();
        if (getViewFinder() != null) {
            getViewFinder().recycle();
        }
        recyclerTpl();
    }

    public IAssistHelper getAssistHelper() {
        return mAssistHelper;
    }

    /**
     * 回收Tpl中的引用
     */
    private void recyclerTpl() {
        if (mActivity != null) {
            mActivity = null;
        }
        if (mListHelper != null) {
            mListHelper = null;
        }
        if (mListScrollHelper != null) {
            mListScrollHelper = null;
        }
        if (mListAdapter != null) {
            mListAdapter = null;
        }
        if (mListDataSource != null) {
            mListDataSource = null;
        }
        if (mListData != null) {
            mListData = null;
        }
        if (mScrollableView != null) {
            mScrollableView = null;
        }
        if (mRoot != null) {
            mRoot = null;
        }
        if (mBean != null) {
            mBean = null;
        }
        if (mInnerViewHolder != null) {
            mInnerViewHolder = null;
        }
    }
}
