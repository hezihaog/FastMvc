package oms.mmc.android.fast.framwork.sample.ui.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;

import oms.mmc.android.fast.framwork.base.BaseFastListFragment;
import oms.mmc.android.fast.framwork.base.BaseListDataSource;
import oms.mmc.android.fast.framwork.base.IDataSource;
import oms.mmc.android.fast.framwork.sample.R;
import oms.mmc.android.fast.framwork.sample.tpl.sample.ShoppingCartSampleTextTpl;
import oms.mmc.android.fast.framwork.sample.widget.PtrPullRefreshLayout;
import oms.mmc.android.fast.framwork.sample.widget.PtrPullRefreshWrapper;
import oms.mmc.android.fast.framwork.widget.pull.IPullRefreshWrapper;
import oms.mmc.android.fast.framwork.widget.rv.base.BaseItemData;
import oms.mmc.android.fast.framwork.widget.rv.base.ItemDataWrapper;

/**
 * Package: oms.mmc.android.fast.framwork.sample.ui.fragment
 * FileName: StoreSampleFragment
 * Date: on 2018/3/7  下午7:05
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */

public class ShoppingCartSampleFragment extends BaseFastListFragment<PtrPullRefreshLayout> {
    public static final int TPL_TEXT = 1;

    @Override
    public View onLayoutView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_shopping_cart_sample, container, false);
    }

    @Override
    public IDataSource<BaseItemData> onListDataSourceReady() {
        return new BaseListDataSource<BaseItemData>(getActivity()) {
            @Override
            protected ArrayList<BaseItemData> load(int page, boolean isRefresh) throws Exception {
                ArrayList<BaseItemData> models = new ArrayList<BaseItemData>();
                Thread.sleep(1500);
                for (int i = 0; i < 15; i++) {
                    models.add(new ItemDataWrapper(TPL_TEXT, "PtrPullRefreshLayout item ::: " + i));
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
        tpls.put(TPL_TEXT, ShoppingCartSampleTextTpl.class);
        return tpls;
    }

    @Override
    public IPullRefreshWrapper<PtrPullRefreshLayout> onInitPullRefreshWrapper(PtrPullRefreshLayout pullRefreshAbleView) {
        return new PtrPullRefreshWrapper(pullRefreshAbleView);
    }

    @Override
    public void onPullRefreshWrapperReady(IPullRefreshWrapper<PtrPullRefreshLayout> refreshWrapper, PtrPullRefreshLayout pullRefreshAbleView) {
        super.onPullRefreshWrapperReady(refreshWrapper, pullRefreshAbleView);
        //可以更换头部，默认是经典类型的头部
//        StoreHouseHeader header = new StoreHouseHeader(getActivity());
//        pullRefreshAbleView.setHeaderView(header);
//        header.initWithString("Alibaba");
//        header.setTextColor(Color.parseColor("#909090"));
    }
}