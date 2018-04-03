package oms.mmc.android.fast.framwork.sample.ui.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;

import oms.mmc.android.fast.framwork.base.BaseFastRecyclerViewListFragment;
import oms.mmc.android.fast.framwork.base.BaseListDataSource;
import oms.mmc.android.fast.framwork.base.IDataSource;
import oms.mmc.android.fast.framwork.sample.R;
import oms.mmc.android.fast.framwork.sample.tpl.sample.StoreTextTpl;
import oms.mmc.android.fast.framwork.widget.pull.IPullRefreshWrapper;
import oms.mmc.android.fast.framwork.widget.rv.base.BaseItemData;
import oms.mmc.android.fast.framwork.widget.rv.base.ItemDataWrapper;
import oms.mmc.helper.widget.ScrollableRecyclerView;
import oms.mmc.smart.pullrefresh.SmartPullRefreshLayout;
import oms.mmc.smart.pullrefresh.SmartPullRefreshWrapper;

/**
 * Package: oms.mmc.android.fast.framwork.sample.ui.fragment
 * FileName: StoreSampleFragment
 * Date: on 2018/3/7  下午7:05
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */

public class StoreSampleFragment extends BaseFastRecyclerViewListFragment<SmartPullRefreshLayout, ScrollableRecyclerView> {
    private static final int TPL_TEXT = 1;

    @Override
    public View onLayoutView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_store_sample, container, false);
    }

    @Override
    public IPullRefreshWrapper<SmartPullRefreshLayout> onInitPullRefreshWrapper(SmartPullRefreshLayout pullRefreshAbleView) {
        return new SmartPullRefreshWrapper(pullRefreshAbleView);
    }

    @Override
    public void onPullRefreshWrapperReady(IPullRefreshWrapper<SmartPullRefreshLayout> refreshWrapper, SmartPullRefreshLayout pullRefreshAbleView) {
        super.onPullRefreshWrapperReady(refreshWrapper, pullRefreshAbleView);
        //我们使用自己的加载更多，所以禁用掉刷新库中的
        pullRefreshAbleView.setEnableLoadmore(false);
    }

    @Override
    public IDataSource<BaseItemData> onListDataSourceReady() {
        return new BaseListDataSource<BaseItemData>(getActivity()) {
            @Override
            protected ArrayList<BaseItemData> load(int page, boolean isRefresh) throws Exception {
                if (isRefresh) {
                    Thread.sleep(500);
                } else {
                    Thread.sleep(1500);
                }
                ArrayList<BaseItemData> models = new ArrayList<BaseItemData>();
                for (int i = 0; i < 15; i++) {
                    models.add(new ItemDataWrapper(TPL_TEXT, "SmartPullRefreshLayout item ::: " + i));
                }
                this.page = page;
                this.hasMore = true;
                return models;
            }
        };
    }

    @Override
    public HashMap<Integer, Class> onListTypeClassesReady() {
        HashMap<Integer, Class> tpls = new HashMap<Integer, Class>();
        tpls.put(TPL_TEXT, StoreTextTpl.class);
        return tpls;
    }
}
