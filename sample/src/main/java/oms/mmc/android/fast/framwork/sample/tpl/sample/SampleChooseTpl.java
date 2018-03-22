package oms.mmc.android.fast.framwork.sample.tpl.sample;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import oms.mmc.android.fast.framwork.sample.R;
import oms.mmc.android.fast.framwork.sample.ui.activity.ActivitySampleActivity;
import oms.mmc.android.fast.framwork.sample.ui.activity.BaseFragmentSampleActivity;
import oms.mmc.android.fast.framwork.sample.ui.activity.BaseListFragmentSampleActivity;
import oms.mmc.android.fast.framwork.sample.ui.activity.FragmentOperateActivity;
import oms.mmc.android.fast.framwork.sample.ui.activity.ListActivityModeSampleActivity;
import oms.mmc.android.fast.framwork.sample.ui.activity.ListActivityMultipleCheckSampleActivity;
import oms.mmc.android.fast.framwork.sample.ui.activity.ListActivitySampleActivity;
import oms.mmc.android.fast.framwork.sample.ui.activity.ListActivitySingleCheckSampleActivity;
import oms.mmc.android.fast.framwork.sample.ui.activity.ListStickyActivitySampleActivity;
import oms.mmc.android.fast.framwork.sample.ui.activity.LoadViewFactorySampleActivity;
import oms.mmc.android.fast.framwork.sample.ui.activity.MainActivity;
import oms.mmc.android.fast.framwork.sample.ui.activity.SampleChooseActivity;
import oms.mmc.android.fast.framwork.sample.ui.activity.SimpleLoadViewHelperUseSampleActivity;
import oms.mmc.android.fast.framwork.sample.util.MMCUIHelper;
import oms.mmc.android.fast.framwork.widget.rv.base.BaseTpl;
import oms.mmc.android.fast.framwork.widget.rv.base.ItemDataWrapper;

/**
 * Package: oms.mmc.android.fast.framwork.sample.tpl.sample
 * FileName: SampleChooseTpl
 * Date: on 2018/3/22  下午7:49
 * Auther: zihe
 * Descirbe:模块选择Tpl
 * Email: hezihao@linghit.com
 */

public class SampleChooseTpl extends BaseTpl<ItemDataWrapper> {
    private SampleChooseActivity.SampleModuleModel mModuleModel;

    @Override
    public View onLayoutView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.tpl_sample_choose, container, false);
    }

    @Override
    protected void onRender(ItemDataWrapper itemData) {
        mModuleModel = (SampleChooseActivity.SampleModuleModel) itemData.getDatas().get(0);
        TextView sampleChooseNameTv = getViewFinder().get(R.id.sampleChooseNameTv);
        setViewText(mModuleModel.getModuleName(), sampleChooseNameTv);
    }

    @Override
    public void onItemClick(View view, int position) {
        super.onItemClick(view, position);
        Class activityClass = mModuleModel.getActivityClass();
        if (activityClass.getName().equals(MainActivity.class.getName())) {
            //综合使用
            MMCUIHelper.showMain(getActivity());
        } else if (activityClass.getName().equals(ActivitySampleActivity.class.getName())) {
            //简单界面使用
            MMCUIHelper.showActivitySample(getActivity(), "10086");
        } else if (activityClass.getName().equals(ListActivitySampleActivity.class.getName())) {
            //ListActivity使用
            MMCUIHelper.showListActivitySample(getActivity());
        } else if (activityClass.getName().equals(ListStickyActivitySampleActivity.class.getName())) {
            //粘性ListActivity
            MMCUIHelper.showListActivitySampleWithSticky(getActivity());
        } else if (activityClass.getName().equals(ListActivitySingleCheckSampleActivity.class.getName())) {
            //列表实现单选
            MMCUIHelper.showListActivitySingleCheckSample(getActivity());
        } else if (activityClass.getName().equals(ListActivityMultipleCheckSampleActivity.class.getName())) {
            //列表实现多选
            MMCUIHelper.showListActivityMultipleCheckSample(getActivity());
        } else if (activityClass.getName().equals(ListActivityModeSampleActivity.class.getName())) {
            //列表实现编辑模式、普通模式
            MMCUIHelper.showListActivityModeSample(getActivity());
        } else if (activityClass.getName().equals(BaseFragmentSampleActivity.class.getName())) {
            //BaseFragment使用
            MMCUIHelper.showBaseFragmentSample(getActivity(), "18");
        } else if (activityClass.getName().equals(BaseListFragmentSampleActivity.class.getName())) {
            MMCUIHelper.showBaseListFragmentSample(getActivity());
        } else if (activityClass.getName().equals(SimpleLoadViewHelperUseSampleActivity.class.getName())) {
            //普通界面使用手动进行视图切换工厂使用
            MMCUIHelper.showSimpleLoadViewHelperSample(getActivity());
        } else if (activityClass.getName().equals(LoadViewFactorySampleActivity.class.getName())) {
            //列表界面视图切换工厂使用
            MMCUIHelper.showLoadViewFactorySample(getActivity());
        } else if (activityClass.getName().equals(FragmentOperateActivity.class.getName())) {
            //Fragment操作例子
            MMCUIHelper.showFragmentOperate(getActivity());
        }
    }
}
