package oms.mmc.android.fast.framwork.sample.ui.activity;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import oms.mmc.android.fast.framwork.base.BaseFastListActivity;
import oms.mmc.android.fast.framwork.base.BaseListDataSource;
import oms.mmc.android.fast.framwork.base.IDataSource;
import oms.mmc.android.fast.framwork.loadview.ILoadMoreViewFactory;
import oms.mmc.android.fast.framwork.sample.R;
import oms.mmc.android.fast.framwork.sample.loadview.SampleChooseLoadMoreViewFactory;
import oms.mmc.android.fast.framwork.sample.tpl.sample.SampleChooseTpl;
import oms.mmc.android.fast.framwork.sample.widget.SmartPullRefreshLayout;
import oms.mmc.android.fast.framwork.sample.widget.SmartPullRefreshWrapper;
import oms.mmc.android.fast.framwork.util.IViewFinder;
import oms.mmc.android.fast.framwork.util.TDevice;
import oms.mmc.android.fast.framwork.widget.list.ICommonListAdapter;
import oms.mmc.android.fast.framwork.widget.list.lv.CommonListViewAdapter;
import oms.mmc.android.fast.framwork.widget.pull.IPullRefreshWrapper;
import oms.mmc.android.fast.framwork.widget.rv.base.BaseItemData;
import oms.mmc.android.fast.framwork.widget.rv.base.ItemDataWrapper;
import oms.mmc.helper.ListScrollHelper;
import oms.mmc.helper.widget.ScrollableListView;
import oms.mmc.helper.wrapper.ScrollableListViewWrapper;

public class SampleChooseActivity extends BaseFastListActivity<SmartPullRefreshLayout, ScrollableListView> {
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
    public ICommonListAdapter<BaseItemData> onListAdapterReady() {
        return new CommonListViewAdapter(getActivity(), getListDataSource(), getScrollableView(), onListTypeClassesReady(), this, getRecyclerViewHelper(), onStickyTplViewTypeReady());
    }

    @Override
    public ListScrollHelper<ScrollableListView> onInitScrollHelper() {
        return new ListScrollHelper<ScrollableListView>(new ScrollableListViewWrapper(getScrollableView()));
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
    public ILoadMoreViewFactory onLoadMoreViewFactoryReady() {
        return new SampleChooseLoadMoreViewFactory();
    }

    @Override
    public void onListReady() {
        super.onListReady();
        //这个界面是用ListView的
        //设置分隔透明
        getScrollableView().setDivider(new ColorDrawable(Color.parseColor("#66909090")));
        //给ListView加分隔线间隔
        getScrollableView().setDividerHeight((int) TDevice.dpToPixel(getActivity(), 8f));
    }

    @Override
    public void onListReadyAfter() {
        super.onListReadyAfter();
        getRefreshLayoutWrapper().startRefreshWithAnimation();
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