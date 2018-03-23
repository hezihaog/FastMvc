package oms.mmc.android.fast.framwork.sample.ui.activity;

import android.app.Activity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.magiepooh.recycleritemdecoration.ItemDecorations;
import com.github.magiepooh.recycleritemdecoration.VerticalItemDecoration;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import oms.mmc.android.fast.framwork.base.BaseFastListActivity;
import oms.mmc.android.fast.framwork.base.BaseListDataSource;
import oms.mmc.android.fast.framwork.base.IDataSource;
import oms.mmc.android.fast.framwork.sample.R;
import oms.mmc.android.fast.framwork.sample.tpl.sample.SampleChooseTpl;
import oms.mmc.android.fast.framwork.sample.widget.SmartPullRefreshLayout;
import oms.mmc.android.fast.framwork.sample.widget.SmartPullRefreshWrapper;
import oms.mmc.android.fast.framwork.util.IViewFinder;
import oms.mmc.android.fast.framwork.widget.pull.IPullRefreshWrapper;
import oms.mmc.android.fast.framwork.widget.rv.base.BaseItemData;
import oms.mmc.android.fast.framwork.widget.rv.base.ItemDataWrapper;

public class SampleChooseActivity extends BaseFastListActivity<SmartPullRefreshLayout> {
    private static final int TPL_SAMPLE_MODULE = 1;

    private Toolbar mToolBar;
    private ArrayList<SampleModuleModel> mSampleModuleModelList;

    @Override
    public void onFindView(IViewFinder finder) {
        super.onFindView(finder);
        mToolBar = (Toolbar) findViewById(R.id.toolBar);
    }

    @Override
    public void onLayoutAfter() {
        super.onLayoutAfter();
        mToolBar.setTitle(R.string.app_name);
        mToolBar.setTitleTextColor(this.getResources().getColor(R.color.white));
    }

    @Override
    public View onLayoutView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.activity_sample_choose, container, false);
    }

    @Override
    public IDataSource onListDataSourceReady() {
        return new BaseListDataSource(getActivity()) {
            @Override
            protected ArrayList<BaseItemData> load(int page, boolean isRefresh) throws Exception {
                ArrayList<BaseItemData> models = new ArrayList<BaseItemData>();
                initSampleData();
                for (SampleModuleModel moduleModel : mSampleModuleModelList) {
                    models.add(new ItemDataWrapper(TPL_SAMPLE_MODULE, moduleModel));
                }
                return models;
            }
        };
    }

    @Override
    public HashMap<Integer, Class> onListTypeClassesReady() {
        HashMap<Integer, Class> tpls = new HashMap<Integer, Class>();
        tpls.put(TPL_SAMPLE_MODULE, SampleChooseTpl.class);
        return tpls;
    }

    @Override
    public IPullRefreshWrapper<SmartPullRefreshLayout> onInitPullRefreshWrapper(SmartPullRefreshLayout pullRefreshAbleView) {
        return new SmartPullRefreshWrapper(pullRefreshAbleView);
    }

    @Override
    public void onPullRefreshWrapperReady(IPullRefreshWrapper<SmartPullRefreshLayout> refreshWrapper, SmartPullRefreshLayout pullRefreshAbleView) {
        super.onPullRefreshWrapperReady(refreshWrapper, pullRefreshAbleView);
        pullRefreshAbleView.setEnableLoadmore(false);
    }

    @Override
    public void onListReady() {
        super.onListReady();
        //设置不可以下拉刷新
//        getListAbleDelegateHelper().getRecyclerViewHelper().setCanPullToRefresh(false);
        //添加分隔线
        VerticalItemDecoration decoration = ItemDecorations.vertical(getActivity())
                .type(TPL_SAMPLE_MODULE, R.drawable.shape_conversation_item_decoration)
                .create();
        getRecyclerView().addItemDecoration(decoration);
    }

    /**
     * 组装例子列表数据
     */
    private void initSampleData() {
        mSampleModuleModelList = new ArrayList<SampleModuleModel>();
        mSampleModuleModelList.add(new SampleModuleModel("综合运用", MainActivity.class));
        mSampleModuleModelList.add(new SampleModuleModel("Fragment常用操作", FragmentOperateActivity.class));
        mSampleModuleModelList.add(new SampleModuleModel("普通界面使用", ActivitySampleActivity.class));
        mSampleModuleModelList.add(new SampleModuleModel("列表界面使用", ListActivitySampleActivity.class));
        mSampleModuleModelList.add(new SampleModuleModel("Fragment使用", BaseFragmentSampleActivity.class));
        mSampleModuleModelList.add(new SampleModuleModel("列表Fragment使用", BaseListFragmentSampleActivity.class));
        mSampleModuleModelList.add(new SampleModuleModel("粘性列表", ListStickyActivitySampleActivity.class));
        mSampleModuleModelList.add(new SampleModuleModel("单选列表", ListActivitySingleCheckSampleActivity.class));
        mSampleModuleModelList.add(new SampleModuleModel("多选列表", ListActivityMultipleCheckSampleActivity.class));
        mSampleModuleModelList.add(new SampleModuleModel("列表编辑、普通模式", ListActivityModeSampleActivity.class));
        mSampleModuleModelList.add(new SampleModuleModel("SimpleLoadView手动控制切换布局", SimpleLoadViewHelperUseSampleActivity.class));
        mSampleModuleModelList.add(new SampleModuleModel("列表LoadViewFactory更换切换布局", LoadViewFactorySampleActivity.class));
    }

    public static class SampleModuleModel implements Serializable {
        private String moduleName;
        private Class mActivityClass;

        public SampleModuleModel(String moduleName, Class activityClass) {
            this.moduleName = moduleName;
            mActivityClass = activityClass;
        }

        public String getModuleName() {
            return moduleName;
        }

        public void setModuleName(String moduleName) {
            this.moduleName = moduleName;
        }

        public Class getActivityClass() {
            return mActivityClass;
        }

        public void setActivityClass(Class<Activity> activityClass) {
            mActivityClass = activityClass;
        }
    }
}