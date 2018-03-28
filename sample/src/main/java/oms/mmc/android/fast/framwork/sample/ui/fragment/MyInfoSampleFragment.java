package oms.mmc.android.fast.framwork.sample.ui.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;

import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import oms.mmc.android.fast.framwork.base.BaseFastRecyclerViewListFragment;
import oms.mmc.android.fast.framwork.base.BaseListDataSource;
import oms.mmc.android.fast.framwork.base.IDataSource;
import oms.mmc.android.fast.framwork.sample.R;
import oms.mmc.android.fast.framwork.sample.tpl.sample.MyInfoSampleTextTpl;
import oms.mmc.android.fast.framwork.sample.widget.BGAPullRefreshLayout;
import oms.mmc.android.fast.framwork.sample.widget.BGAPullRefreshLayoutWrapper;
import oms.mmc.android.fast.framwork.widget.pull.IPullRefreshWrapper;
import oms.mmc.android.fast.framwork.widget.rv.base.BaseItemData;
import oms.mmc.android.fast.framwork.widget.rv.base.ItemDataWrapper;
import oms.mmc.helper.widget.ScrollableRecyclerView;

/**
 * Package: oms.mmc.android.fast.framwork.sample.ui.fragment
 * FileName: StoreSampleFragment
 * Date: on 2018/3/7  下午7:05
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */

public class MyInfoSampleFragment extends BaseFastRecyclerViewListFragment<BGAPullRefreshLayout, ScrollableRecyclerView> {
    private static final int TPL_TEXT = 1;

    @Override
    public View onLayoutView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_my_info_sample, container, false);
    }

    @Override
    public IDataSource onListDataSourceReady() {
        return new BaseListDataSource(getActivity()) {
            @Override
            protected ArrayList<BaseItemData> load(int page, boolean isRefresh) throws Exception {
                if (isRefresh) {
                    Thread.sleep(500);
                } else {
                    Thread.sleep(1500);
                }
                ArrayList<BaseItemData> models = new ArrayList<BaseItemData>();
                for (int i = 0; i < 15; i++) {
                    models.add(new ItemDataWrapper(TPL_TEXT, "BGAPullRefreshLayout item ::: " + i));
                }
                this.page = page;
                this.hasMore = true;
                return models;
            }
        };
    }

    @Override
    public IPullRefreshWrapper<BGAPullRefreshLayout> onInitPullRefreshWrapper(BGAPullRefreshLayout pullRefreshAbleView) {
        return new BGAPullRefreshLayoutWrapper(pullRefreshAbleView);
    }

    @Override
    public void onPullRefreshWrapperReady(IPullRefreshWrapper<BGAPullRefreshLayout> refreshWrapper, BGAPullRefreshLayout pullRefreshAbleView) {
        super.onPullRefreshWrapperReady(refreshWrapper, pullRefreshAbleView);
        //这里使用我们的加载更多，所以禁用掉
        pullRefreshAbleView.setIsShowLoadingMoreView(false);
        //这个下拉刷新库需要设置一个刷新头才能进行下拉刷新
        BGANormalRefreshViewHolder normalRefreshViewHolder = new BGANormalRefreshViewHolder(getActivity(), false);
        pullRefreshAbleView.setRefreshViewHolder(normalRefreshViewHolder);
    }

    @Override
    public HashMap<Integer, Class> onListTypeClassesReady() {
        HashMap<Integer, Class> tpls = new HashMap<Integer, Class>();
        tpls.put(TPL_TEXT, MyInfoSampleTextTpl.class);
        return tpls;
    }
}