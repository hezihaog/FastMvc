package oms.mmc.android.fast.framwork.sample.ui.activity;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import oms.mmc.android.fast.framwork.base.BaseFastListViewActivity;
import oms.mmc.android.fast.framwork.base.BaseListDataSource;
import oms.mmc.android.fast.framwork.base.IDataSource;
import oms.mmc.android.fast.framwork.loadview.ILoadMoreViewFactory;
import oms.mmc.android.fast.framwork.sample.R;
import oms.mmc.android.fast.framwork.sample.loadview.SampleChooseLoadMoreViewFactory;
import oms.mmc.android.fast.framwork.sample.loadview.SampleLoadViewFactory;
import oms.mmc.android.fast.framwork.sample.tpl.sample.SampleChooseTpl;
import oms.mmc.android.fast.framwork.util.IViewFinder;
import oms.mmc.android.fast.framwork.util.TDevice;
import oms.mmc.android.fast.framwork.widget.pull.IPullRefreshWrapper;
import oms.mmc.android.fast.framwork.widget.pull.SwipePullRefreshLayout;
import oms.mmc.android.fast.framwork.widget.pull.SwipePullRefreshWrapper;
import oms.mmc.android.fast.framwork.widget.rv.base.BaseItemData;
import oms.mmc.android.fast.framwork.widget.rv.base.ItemDataWrapper;
import oms.mmc.factory.load.factory.ILoadViewFactory;
import oms.mmc.helper.ListScrollHelper;
import oms.mmc.helper.widget.ScrollableListView;
import oms.mmc.helper.wrapper.ScrollableListViewWrapper;

public class SampleChooseActivity extends BaseFastListViewActivity<SwipePullRefreshLayout, ScrollableListView> {
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
                Thread.sleep(1500);
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
    public ListScrollHelper<ScrollableListView> onInitScrollHelper() {
        return new ListScrollHelper<ScrollableListView>(new ScrollableListViewWrapper(getScrollableView()));
    }

    @Override
    public IPullRefreshWrapper<SwipePullRefreshLayout> onInitPullRefreshWrapper(SwipePullRefreshLayout pullRefreshAbleView) {
        return new SwipePullRefreshWrapper(pullRefreshAbleView);
    }

    @Override
    public void onPullRefreshWrapperReady(IPullRefreshWrapper<SwipePullRefreshLayout> refreshWrapper, SwipePullRefreshLayout pullRefreshAbleView) {
        super.onPullRefreshWrapperReady(refreshWrapper, pullRefreshAbleView);
        //给SwipeRefreshLayout加点颜色吧~
        pullRefreshAbleView.setColorSchemeResources(R.color.orange, R.color.green, R.color.blue);
    }

    @Override
    public ILoadMoreViewFactory onLoadMoreViewFactoryReady() {
        return new SampleChooseLoadMoreViewFactory();
    }

    @Override
    public void onListReady() {
        super.onListReady();
        //这个界面是用ListView的
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFFFFF")));
        //设置分隔线颜色
        getScrollableView().setDivider(new ColorDrawable(Color.parseColor("#F3F5F7")));
        //给ListView分隔线设置高度
        getScrollableView().setDividerHeight((int) TDevice.dpToPixel(getActivity(), 8f));
        //可以给lv添加一个头部
        TextView headerView = new TextView(getActivity());
        headerView.setText("~我是ListView的头部喔~");
        int padding = (int) TDevice.dpToPixel(getActivity(), 15f);
        headerView.setPadding(padding, padding, padding, padding);
        //默认直接横向填满，可以不传，如有特殊需求，则在调用addHeaderView之前设置即可
//        headerView.setLayoutParams(new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.WRAP_CONTENT));
        headerView.setGravity(Gravity.CENTER);
        getListAbleDelegateHelper().addHeaderView(headerView);
    }

    //重写该函数，切换页面切换时的样式（加载中、异常、空）
    @Override
    public ILoadViewFactory onLoadViewFactoryReady() {
        return new SampleLoadViewFactory();
    }

    @Override
    public void onListReadyAfter() {
        super.onListReadyAfter();
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
        mSampleModuleModelList.add(new SampleModuleModel("ViewPager嵌套Fragment", NestedFragmentSampleActivity.class));
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