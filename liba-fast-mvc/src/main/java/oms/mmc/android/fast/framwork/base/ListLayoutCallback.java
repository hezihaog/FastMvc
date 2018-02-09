package oms.mmc.android.fast.framwork.base;

import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;

import oms.mmc.android.fast.framwork.widget.pulltorefresh.helper.IDataAdapter;
import oms.mmc.android.fast.framwork.widget.pulltorefresh.helper.IDataSource;
import oms.mmc.android.fast.framwork.widget.pulltorefresh.helper.ILoadViewFactory;

/**
 * 列表页面布局回调接口
 */
public interface ListLayoutCallback<T> {
    /**
     * 获取RecyclerView的LayoutManager
     */
    RecyclerView.LayoutManager getListLayoutManager();

    /**
     * 列表初始化完成回调
     */
    void onListReady();

    /**
     * 列表适配器初始化回调
     *
     * @return 列表需要的适配器
     */
    IDataAdapter<ArrayList<T>> onListAdapterReady();

    /**
     * 列表加载布局切换工厂初始化回调
     *
     * @return 列表加载布局切换工厂
     */
    ILoadViewFactory onLoadViewFactoryReady();

    /**
     * 列表数据源初始化回调
     *
     * @return 列表数据源
     */
    IDataSource<T> onListDataSourceReady();

    /**
     * 列表条目模板初花回调
     *
     * @return 列表条目模板
     */
    HashMap<Integer, Class> onListTypeClassesReady();

    /**
     * 需要设置粘性悬浮条目类时重写该方法返回粘性条目的类型
     */
    int onGetStickyTplViewType();

    /**
     * 获取滚动刷新帮助类对象
     */
    ListScrollHelper onGetScrollHelper();
}
