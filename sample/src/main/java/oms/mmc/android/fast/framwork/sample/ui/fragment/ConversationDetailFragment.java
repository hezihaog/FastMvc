package oms.mmc.android.fast.framwork.sample.ui.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;

import oms.mmc.android.fast.framwork.base.BaseListDataSource;
import oms.mmc.android.fast.framwork.base.BaseListFragment;
import oms.mmc.android.fast.framwork.base.ItemDataWrapper;
import oms.mmc.android.fast.framwork.base.ListScrollHelper;
import oms.mmc.android.fast.framwork.base.RecyclerViewScrollableViewWrapper;
import oms.mmc.android.fast.framwork.basiclib.util.ViewFinder;
import oms.mmc.android.fast.framwork.widget.pulltorefresh.helper.IDataSource;

/**
 * Package: oms.mmc.android.fast.framwork.sample.ui.fragment
 * FileName: ConversationDetailFragment
 * Date: on 2018/2/9  下午7:23
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */

public class ConversationDetailFragment extends BaseListFragment<ItemDataWrapper> {
    @Override
    public void onFindView(ViewFinder finder) {
    }

    @Override
    public RecyclerView.LayoutManager getListLayoutManager() {
        return new LinearLayoutManager(getActivity());
    }

    @Override
    public IDataSource<ItemDataWrapper> onListDataSourceReady() {
        return new BaseListDataSource<ItemDataWrapper>(getActivity()) {
            @Override
            protected ArrayList<ItemDataWrapper> load(int page) throws Exception {
                ArrayList<ItemDataWrapper> model = new ArrayList<>();
                return model;
            }
        };
    }

    @Override
    public HashMap<Integer, Class> onListTypeClassesReady() {
        HashMap<Integer, Class> tpls = new HashMap<Integer, Class>();
        return tpls;
    }

    @Override
    public ListScrollHelper onGetScrollHelper() {
        return new ListScrollHelper(new RecyclerViewScrollableViewWrapper(recyclerView));
    }
}