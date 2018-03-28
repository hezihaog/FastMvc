package oms.mmc.android.fast.framwork.sample.ui.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;

import oms.mmc.android.fast.framwork.base.BaseFastRecyclerViewListFragment;
import oms.mmc.android.fast.framwork.base.BaseListDataSource;
import oms.mmc.android.fast.framwork.base.IDataSource;
import oms.mmc.android.fast.framwork.sample.R;
import oms.mmc.android.fast.framwork.sample.tpl.sample.MainSampleTextTpl;
import oms.mmc.android.fast.framwork.sample.widget.TwinklingPullRefreshLayout;
import oms.mmc.android.fast.framwork.sample.widget.TwinklingPullRefreshWrapper;
import oms.mmc.android.fast.framwork.widget.pull.IPullRefreshWrapper;
import oms.mmc.android.fast.framwork.widget.rv.base.BaseItemData;
import oms.mmc.android.fast.framwork.widget.rv.base.ItemDataWrapper;
import oms.mmc.helper.widget.ScrollableRecyclerView;

/**
 * Package: oms.mmc.android.fast.framwork.sample.ui.fragment
 * FileName: MainFragment
 * Date: on 2018/3/7  下午6:59
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */

public class MainSampleFragment extends BaseFastRecyclerViewListFragment<TwinklingPullRefreshLayout, ScrollableRecyclerView> {
    public static final int TPL_SAMPLE_TEXT = 1;

    @Override
    public View onLayoutView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_main_sample, container, false);
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
                ArrayList<BaseItemData> models = new ArrayList<>();
                for (int i = 0; i < 15; i++) {
                    models.add(new ItemDataWrapper(TPL_SAMPLE_TEXT, "TwinklingPullRefreshLayout item ::: " + i));
                }
                this.page = page;
                this.hasMore = true;
                return models;
            }
        };
    }

    @Override
    public IPullRefreshWrapper<TwinklingPullRefreshLayout> onInitPullRefreshWrapper(TwinklingPullRefreshLayout pullRefreshAbleView) {
        return new TwinklingPullRefreshWrapper(pullRefreshAbleView);
    }

    @Override
    public void onPullRefreshWrapperReady(IPullRefreshWrapper<TwinklingPullRefreshLayout> refreshWrapper, TwinklingPullRefreshLayout pullRefreshAbleView) {
        super.onPullRefreshWrapperReady(refreshWrapper, pullRefreshAbleView);
        //关闭加载更多
        pullRefreshAbleView.setEnableLoadmore(false);
        //开启越界回弹
        pullRefreshAbleView.setEnableOverScroll(true);
    }

    @Override
    public RecyclerView.LayoutManager onGetListLayoutManager() {
        return new LinearLayoutManager(getContext());
    }

    @Override
    public HashMap<Integer, Class> onListTypeClassesReady() {
        HashMap<Integer, Class> tpls = new HashMap<Integer, Class>();
        tpls.put(TPL_SAMPLE_TEXT, MainSampleTextTpl.class);
        return tpls;
    }
}