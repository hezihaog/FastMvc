package oms.mmc.android.fast.framwork.base;

import java.util.HashMap;

import oms.mmc.android.fast.framwork.loadview.ILoadMoreViewFactory;
import oms.mmc.factory.load.factory.ILoadViewFactory;
import oms.mmc.helper.ListScrollHelper;

/**
 * 列表页面布局回调接口
 */
public interface ListLayoutCallback<T> {
    /**
     * 列表数据源初始化回调
     *
     * @return 列表数据源
     */
    IDataSource<T> onListDataSourceReady();

    /**
     * 列表适配器初始化回调
     *
     * @return 列表需要的适配器
     */
    IDataAdapter<T> onListAdapterReady();

    /**
     * 列表加载布局切换工厂初始化回调
     *
     * @return 列表加载布局切换工厂
     */
    ILoadViewFactory onLoadViewFactoryReady();

    /**
     * 列表接在更多布局切换工厂初始化回调
     *
     * @return 列表加载更多布局切换工厂
     */
    ILoadMoreViewFactory onLoadMoreViewFactoryReady();

    /**
     * 列表条目模板初花回调
     *
     * @return 列表条目模板
     */
    HashMap<Integer, Class> onListTypeClassesReady();

    /**
     * 需要设置粘性悬浮条目类时重写该方法返回粘性条目的类型
     */
    int onStickyTplViewTypeReady();

    /**
     * 获取滚动监听帮助类对象
     */
    ListScrollHelper onInitScrollHelper();

    /**
     * 滚动监听帮助类初始化完成
     */
    void onListScrollHelperReady(ListScrollHelper listScrollHelper);

    /**
     * 列表初始化完成回调
     */
    void onListReady();
}
