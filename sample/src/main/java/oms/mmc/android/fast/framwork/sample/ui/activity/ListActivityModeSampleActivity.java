package oms.mmc.android.fast.framwork.sample.ui.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import oms.mmc.android.fast.framwork.base.BaseFastListActivity;
import oms.mmc.android.fast.framwork.base.BaseListAdapter;
import oms.mmc.android.fast.framwork.base.BaseListDataSource;
import oms.mmc.android.fast.framwork.base.IDataSource;
import oms.mmc.android.fast.framwork.sample.R;
import oms.mmc.android.fast.framwork.sample.event.ToggleModeEvent;
import oms.mmc.android.fast.framwork.sample.tpl.sample.ListModeSampleTpl;
import oms.mmc.android.fast.framwork.sample.util.EventBusUtil;
import oms.mmc.android.fast.framwork.util.ViewFinder;
import oms.mmc.android.fast.framwork.widget.rv.base.BaseItemData;
import oms.mmc.android.fast.framwork.widget.rv.base.ItemDataWrapper;

public class ListActivityModeSampleActivity extends BaseFastListActivity {
    public static final int TPL_ITEM = 1;

    private Button mModeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBusUtil.register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBusUtil.unregister(this);
    }

    @Override
    public void onFindView(ViewFinder finder) {
        super.onFindView(finder);
        mModeBtn = finder.get(R.id.modeBtn);
    }

    @Override
    public void onLayoutAfter() {
        super.onLayoutAfter();
        mModeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //查询出点击之前的状态
                boolean isNormalMode = getListAdapter().isNormalMode();
                //不是编辑状态，设置为编辑状态
                if (isNormalMode) {
                    getListAdapter().setEditMode();
                    EventBusUtil.sendEvent(new ToggleModeEvent(true));
                    getRecyclerView().getAdapter().notifyDataSetChanged();
                } else {
                    //之前是编辑状态，如果勾选了，则提示是否删除，否则切换回普通状态
                    final List<Integer> checkedItemPositions = getListAdapter().getCheckedItemPositions();
                    if (!checkedItemPositions.isEmpty()) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setTitle("提示");
                        builder.setMessage("是否要删除这" + checkedItemPositions.size() + "条信息？");
                        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                BaseListAdapter<BaseItemData> listAdapter = getListAdapter();
                                //用选择的位置，找出需要删除的条目
                                ArrayList<BaseItemData> needDeleteItemData = new ArrayList<BaseItemData>();
                                for (Integer checkedItemPosition : checkedItemPositions) {
                                    needDeleteItemData.add(getListData().get(checkedItemPosition));
                                }
                                //批量删除条目
                                getListData().removeAll(needDeleteItemData);
                                listAdapter.setNormalMode();
                                checkedItemPositions.clear();
                                EventBusUtil.sendEvent(new ToggleModeEvent(false));
                                getListAdapter().notifyDataSetChanged();
                            }
                        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        builder.show();
                    } else {
                        getListAdapter().setEditMode();
                        EventBusUtil.sendEvent(new ToggleModeEvent(false));
                    }
                }
            }
        });
    }

    @Override
    public View onLayoutView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.activity_list_mode_sample, container, false);
    }

    @Override
    public IDataSource<BaseItemData> onListDataSourceReady() {
        return new BaseListDataSource<BaseItemData>(getActivity()) {
            @Override
            protected ArrayList<BaseItemData> load(int page, boolean isRefresh) throws Exception {
                ArrayList<BaseItemData> models = new ArrayList<>();
                ArrayList<String> datas = getDatas();
                for (String data : datas) {
                    models.add(new ItemDataWrapper(TPL_ITEM, data));
                }
                return models;
            }
        };
    }

    private ArrayList<String> getDatas() {
        ArrayList<String> datas = new ArrayList<String>();
        for (int i = 0; i < 15; i++) {
            datas.add("item " + i);
        }
        return datas;
    }

    @Override
    public RecyclerView.LayoutManager onGetListLayoutManager() {
        return new LinearLayoutManager(getActivity());
    }

    @Override
    public HashMap<Integer, Class> onListTypeClassesReady() {
        HashMap<Integer, Class> tpls = new HashMap<Integer, Class>();
        tpls.put(TPL_ITEM, ListModeSampleTpl.class);
        return tpls;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(ToggleModeEvent event) {
        if (event.isEdit()) {
            mModeBtn.setText("完成");
        } else {
            mModeBtn.setText("编辑");
            getListAdapter().clearCheckedItemPositions();
        }
    }
}