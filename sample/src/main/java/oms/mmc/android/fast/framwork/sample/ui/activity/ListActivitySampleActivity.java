package oms.mmc.android.fast.framwork.sample.ui.activity;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.magiepooh.recycleritemdecoration.ItemDecorations;
import com.github.magiepooh.recycleritemdecoration.VerticalItemDecoration;

import java.util.ArrayList;
import java.util.HashMap;

import oms.mmc.android.fast.framwork.base.BaseFastListActivity;
import oms.mmc.android.fast.framwork.base.BaseListAdapter;
import oms.mmc.android.fast.framwork.base.BaseListDataSource;
import oms.mmc.android.fast.framwork.base.IDataAdapter;
import oms.mmc.android.fast.framwork.base.IDataSource;
import oms.mmc.android.fast.framwork.sample.R;
import oms.mmc.android.fast.framwork.sample.tpl.sample.BaseListImageTplSample;
import oms.mmc.android.fast.framwork.sample.tpl.sample.BaseListTextTplSample;
import oms.mmc.android.fast.framwork.util.ToastUtil;
import oms.mmc.android.fast.framwork.util.ViewFinder;
import oms.mmc.android.fast.framwork.widget.rv.base.BaseItemData;
import oms.mmc.android.fast.framwork.widget.rv.base.BaseTpl;
import oms.mmc.android.fast.framwork.widget.rv.base.ItemDataWrapper;
import oms.mmc.android.fast.framwork.widget.rv.sticky.StickyHeadersLinearLayoutManager;
import oms.mmc.helper.ListScrollHelper;
import oms.mmc.helper.widget.ScrollableRecyclerView;
import oms.mmc.helper.wrapper.ScrollableRecyclerViewWrapper;

public class ListActivitySampleActivity extends BaseFastListActivity {
    public static final String BUNDLE_KEY_HAS_STICKY = "key_has_sticky";

    public static final int TPL_TEXT = 1;
    public static final int TPL_IMAGE = 2;
    private boolean mHasSticky;

    @Override
    public void onLayoutBefore() {
        super.onLayoutBefore();
        //这里拿取前面传递过来的数据
        mHasSticky = getIntent().getBooleanExtra(ListActivitySampleActivity.BUNDLE_KEY_HAS_STICKY, false);
    }

    //如果是普通的布局，不需要添加其他控件，可以直接使用父类中的布局
    @Override
    public View onLayoutView(LayoutInflater inflater, ViewGroup container) {
        return super.onLayoutView(inflater, container);
    }

    @Override
    public void onFindView(ViewFinder finder) {
        super.onFindView(finder);
        //这里查找其他控件
    }

    @Override
    public void onLayoutAfter() {
        super.onLayoutAfter();
        //给其他控件进行设置设置数据
    }

    /**
     * 这里请求网络拿列表数据
     */
    @Override
    public IDataSource<BaseItemData> onListDataSourceReady() {
        return new BaseListDataSource<BaseItemData>(getActivity()) {
            @Override
            protected ArrayList<BaseItemData> load(int page) throws Exception {
                ArrayList<BaseItemData> models = new ArrayList<>();
                //模拟请求数据
                ArrayList<String> datas = getData();
                for (int i = 0; i < datas.size(); i++) {
                    String data = datas.get(i);
                    if (i % 2 == 0) {
                        models.add(new ItemDataWrapper(TPL_IMAGE, data));
                    } else {
                        models.add(new ItemDataWrapper(TPL_TEXT, data));
                    }
                }
                this.page = page;
                this.hasMore = true;
                return models;
            }
        };
    }

    /**
     * 这里返回需要使用的LayoutManager
     */
    @Override
    public RecyclerView.LayoutManager onGetListLayoutManager() {
        return new StickyHeadersLinearLayoutManager<BaseListAdapter>(getActivity());
    }

    @Override
    public int onGetStickyTplViewType() {
        if (mHasSticky) {
            return TPL_TEXT;
        } else {
            return super.onGetStickyTplViewType();
        }
    }

    /**
     * 这里组装条目类型和tpl映射关系的map
     */
    @Override
    public HashMap<Integer, Class> onListTypeClassesReady() {
        HashMap<Integer, Class> tpls = new HashMap<Integer, Class>();
        tpls.put(TPL_TEXT, BaseListTextTplSample.class);
        tpls.put(TPL_IMAGE, BaseListImageTplSample.class);
        return tpls;
    }

    /**
     * 这里返回对应的滚动帮助类，要和使用的一样，基本都是rv的
     */
    @Override
    public ListScrollHelper onGetScrollHelper() {
        return new ListScrollHelper(new ScrollableRecyclerViewWrapper((ScrollableRecyclerView) getRecyclerView()));
    }

    @Override
    public void onListReady() {
        super.onListReady();
        //这里可以增加一个分隔线
        //添加分隔线
        VerticalItemDecoration decoration = ItemDecorations.vertical(getActivity())
                .first(R.drawable.shape_conversation_item_decoration)
                .type(TPL_TEXT, R.drawable.shape_conversation_item_decoration)
                .type(TPL_IMAGE, R.drawable.shape_conversation_item_decoration)
                .last(R.drawable.shape_conversation_item_decoration)
                .create();
        getRecyclerView().addItemDecoration(decoration);
    }

    @Override
    public void onStartRefresh(IDataAdapter<ArrayList<BaseItemData>, BaseTpl.ViewHolder> adapter, boolean isFirst, boolean isReverse) {
        super.onStartRefresh(adapter, isFirst, isReverse);
        ToastUtil.showToast(getActivity(), "刷新开始...");
    }

    @Override
    public void onEndRefresh(IDataAdapter<ArrayList<BaseItemData>, BaseTpl.ViewHolder> adapter, ArrayList<BaseItemData> result, boolean isFirst, boolean isReverse) {
        super.onEndRefresh(adapter, result, isFirst, isReverse);
        ToastUtil.showToast(getActivity(), "刷新结束...");
    }

    @Override
    public void onStartLoadMore(IDataAdapter<ArrayList<BaseItemData>, BaseTpl.ViewHolder> adapter, boolean isFirst, boolean isReverse) {
        super.onStartLoadMore(adapter, isFirst, isReverse);
        ToastUtil.showToast(getActivity(), "上拉加载开始...");
    }

    @Override
    public void onEndLoadMore(IDataAdapter<ArrayList<BaseItemData>, BaseTpl.ViewHolder> adapter, ArrayList<BaseItemData> result, boolean isFirst, boolean isReverse) {
        super.onEndLoadMore(adapter, result, isFirst, isReverse);
        ToastUtil.showToast(getActivity(), "上拉加载结束...");
    }

    /**
     * 模拟获取数据
     */
    private ArrayList<String> getData() {
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ArrayList<String> datas = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            datas.add("item" + i);
        }
        return datas;
    }
}