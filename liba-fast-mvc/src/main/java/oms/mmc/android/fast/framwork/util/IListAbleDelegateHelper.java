package oms.mmc.android.fast.framwork.util;

import android.app.Activity;
import android.view.View;

import java.util.ArrayList;

import oms.mmc.android.fast.framwork.base.IDataSource;
import oms.mmc.android.fast.framwork.base.ListLayoutCallback;
import oms.mmc.android.fast.framwork.loadview.ILoadMoreViewFactory;
import oms.mmc.android.fast.framwork.widget.list.ICommonListAdapter;
import oms.mmc.android.fast.framwork.widget.list.helper.IAssistHelper;
import oms.mmc.android.fast.framwork.widget.pull.IPullRefreshLayout;
import oms.mmc.android.fast.framwork.widget.pull.IPullRefreshWrapper;
import oms.mmc.android.fast.framwork.widget.rv.base.BaseItemData;
import oms.mmc.android.fast.framwork.widget.rv.base.BaseTpl;
import oms.mmc.factory.load.factory.ILoadViewFactory;
import oms.mmc.helper.ListScrollHelper;
import oms.mmc.helper.base.IScrollableAdapterView;
import oms.mmc.helper.base.IScrollableViewWrapper;

/**
 * Package: oms.mmc.android.fast.framwork.util
 * FileName: IListAbleDelegateHelper
 * Date: on 2018/3/29  上午9:55
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */

public interface IListAbleDelegateHelper<P extends IPullRefreshLayout, V extends IScrollableAdapterView> {
    /**
     * 开始代理
     *
     * @param activity   Activity
     * @param rootLayout 界面上的根布局
     */
    void startDelegate(Activity activity, View rootLayout);

    /**
     * 设置列表控件相关设置
     */
    void setupListWidget();

    /**
     * 设置滚动控件
     */
    void setupScrollHelper();

    /**
     * 销毁列表帮助类
     */
    void destroyListHelper();

    /**
     * 交给外部调用界面类的onListReady()
     */
    void notifyListReady();

    /**
     * 获取设置的下拉刷新布局包裹类
     */
    IPullRefreshWrapper<P> getRefreshWrapper();

    /**
     * 获取下拉刷新布局
     */
    P getRefreshLayout();

    /**
     * 获取滚动控件包裹类
     */
    IScrollableViewWrapper<V> getScrollableViewWrapper();

    /**
     * 获取列表数据集
     */
    IDataSource<BaseItemData> getListDataSource();

    /**
     * 获取列表数据
     */
    ArrayList<BaseItemData> getListData();

    /**
     * 获取列表适配器
     */
    ICommonListAdapter<BaseItemData> getListAdapter();

    /**
     * 获取资源帮助类
     */
    IAssistHelper getAssistHelper();

    /**
     * 获取列表界面状态布局切换工厂
     */
    ILoadViewFactory getLoadViewFactory();

    /**
     * 获取加载更多条目状态布局工厂
     */
    ILoadMoreViewFactory getLoadMoreViewFactory();

    /**
     * 获取滚动帮助类
     */
    ListScrollHelper<V> getScrollHelper();

    /**
     * 获取可滚动界面，其实就是ListActivity、ListFragment
     */
    ListLayoutCallback<BaseItemData, V> getListAble();

    /**
     * 获取滚动控件
     */
    V getScrollableView();

    /**
     * 反转布局
     */
    void reverseListLayout();

    /**
     * 缓慢滚动到顶部
     *
     * @param isReverse 是否反转布局
     */
    void smoothMoveToTop(boolean isReverse);

    /**
     * 顺时滚动到顶部
     *
     * @param isReverse 是否是反转布局
     */
    void moveToTop(boolean isReverse);

    /**
     * 使用position查找条目Tpl
     *
     * @param position 条目位置
     */
    BaseTpl findTplByPosition(int position);
}