package oms.mmc.android.fast.framwork.sample.ui.activity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import oms.mmc.android.fast.framwork.base.BaseFastActivity;
import oms.mmc.android.fast.framwork.sample.R;
import oms.mmc.android.fast.framwork.sample.ui.fragment.BaseListFragmentSampleFragment;
import oms.mmc.android.fast.framwork.util.FragmentFactory;

public class BaseListFragmentSampleActivity extends BaseFastActivity {

    @Override
    public View onLayoutView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.activity_base_list_fragment_sample, container, false);
    }

    @Override
    protected FragmentFactory.FragmentInfoWrapper onSetupFragment() {
        //可以指定fragment添加到哪个容器，如果不传，则默认为android.R.id.content
        return new FragmentFactory.FragmentInfoWrapper(R.id.fragmentContainer, BaseListFragmentSampleFragment.class);
    }
}