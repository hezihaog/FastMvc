package oms.mmc.android.fast.framwork.sample.ui.activity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import oms.mmc.android.fast.framwork.base.BaseFastActivity;
import oms.mmc.android.fast.framwork.sample.R;
import oms.mmc.android.fast.framwork.sample.ui.fragment.BaseFragmentSampleFragment;
import oms.mmc.android.fast.framwork.util.FragmentFactory;

public class BaseFragmentSampleActivity extends BaseFastActivity {
    public static final String BUNDLE_KEY_AGE = "key_age";

    @Override
    public View onLayoutView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.activity_base_fragment_sample, container, false);
    }

    //简单使用：如果activity只是一个容器，直接复写该方法，传入fragment的class和需要传递数据Bundle即可，不用写重复的代码
    @Override
    protected FragmentFactory.FragmentInfoWrapper onSetupFragment() {
        //transformActivityData()这个函数，会将activity的intent的数据转移到fragment的mArguments上。
        return new FragmentFactory.FragmentInfoWrapper(BaseFragmentSampleFragment.class, transformActivityData());
    }
}