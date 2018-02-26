package oms.mmc.android.fast.framwork.base;

import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;

import oms.mmc.android.fast.framwork.util.IDataAdapter;
import oms.mmc.android.fast.framwork.util.IDataSource;
import oms.mmc.android.fast.framwork.util.ILoadViewFactory;
import oms.mmc.android.fast.framwork.widget.view.ListScrollHelper;

/**
 * 列表页面布局回调接口
 */
public interface ListLayoutCallback<T, VH extends RecyclerView.ViewHolder> {
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
    IDataAdapter<ArrayList<T>, VH> onListAdapterReady();

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
     * 获取滚动监听帮助类对象
     */
    ListScrollHelper onGetScrollHelper();

    /**
     * 滚动监听帮助类初始化完成
     */
    void onListScrollHelperReady(ListScrollHelper listScrollHelper);
}
