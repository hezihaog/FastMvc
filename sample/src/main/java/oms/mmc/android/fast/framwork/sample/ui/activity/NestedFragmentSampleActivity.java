package oms.mmc.android.fast.framwork.sample.ui.activity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import oms.mmc.android.fast.framwork.base.BaseFastActivity;
import oms.mmc.android.fast.framwork.sample.R;
import oms.mmc.android.fast.framwork.sample.ui.fragment.NestedFragmentContainerFragment;
import oms.mmc.android.fast.framwork.util.FragmentFactory;

/**
 * Package: oms.mmc.android.fast.framwork.sample.ui.activity
 * FileName: NestedFragmentSampleActivity
 * Date: on 2018/4/18  下午11:14
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */
public class NestedFragmentSampleActivity extends BaseFastActivity {

    @Override
    public View onLayoutView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.activity_nested_fragment_sample, container, false);
    }

    @Override
    protected FragmentFactory.FragmentInfoWrapper onSetupFragment() {
        return new FragmentFactory.FragmentInfoWrapper(R.id.root_layout, NestedFragmentContainerFragment.class);
    }
}