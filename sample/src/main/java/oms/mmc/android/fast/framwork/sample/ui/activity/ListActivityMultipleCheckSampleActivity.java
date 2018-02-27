package oms.mmc.android.fast.framwork.sample.ui.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.github.magiepooh.recycleritemdecoration.ItemDecorations;
import com.github.magiepooh.recycleritemdecoration.VerticalItemDecoration;

import java.util.ArrayList;
import java.util.HashMap;

import oms.mmc.android.fast.framwork.base.BaseFastListActivity;
import oms.mmc.android.fast.framwork.base.BaseListDataSource;
import oms.mmc.android.fast.framwork.base.IDataSource;
import oms.mmc.android.fast.framwork.sample.R;
import oms.mmc.android.fast.framwork.sample.tpl.sample.ListMultipleCheckSampleTpl;
import oms.mmc.android.fast.framwork.widget.rv.base.BaseItemData;

public class ListActivityMultipleCheckSampleActivity extends BaseFastListActivity {
    public static final int TPL_MULTIPLE_CHECK = 2;

    @Override
    public IDataSource<BaseItemData> onListDataSourceReady() {
        return new BaseListDataSource<BaseItemData>(getActivity()) {
            @Override
            protected ArrayList<BaseItemData> load(int page) throws Exception {
                ArrayList<BaseItemData> models = new ArrayList<>();
                for (int i = 0; i < 30; i++) {
                    models.add(new BaseItemData(TPL_MULTIPLE_CHECK));
                }
                return models;
            }
        };
    }

    @Override
    public RecyclerView.LayoutManager onGetListLayoutManager() {
        return new LinearLayoutManager(getActivity());
    }

    @Override
    public HashMap<Integer, Class> onListTypeClassesReady() {
        HashMap<Integer, Class> tpls = new HashMap<>();
        tpls.put(TPL_MULTIPLE_CHECK, ListMultipleCheckSampleTpl.class);
        return tpls;
    }

    @Override
    public void onListReady() {
        super.onListReady();
        //添加分隔线
        VerticalItemDecoration decoration = ItemDecorations.vertical(getActivity())
                .first(R.drawable.shape_conversation_item_decoration)
                .type(TPL_MULTIPLE_CHECK, R.drawable.shape_conversation_item_decoration)
                .last(R.drawable.shape_conversation_item_decoration)
                .create();
    }
}