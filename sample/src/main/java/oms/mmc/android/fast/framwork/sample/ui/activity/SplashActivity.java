package oms.mmc.android.fast.framwork.sample.ui.activity;

import oms.mmc.android.fast.framwork.base.BaseFastActivity;
import oms.mmc.android.fast.framwork.util.FragmentFactory;
import oms.mmc.android.fast.framwork.util.ViewFinder;
import oms.mmc.android.fast.framwork.sample.R;
import oms.mmc.android.fast.framwork.sample.ui.fragment.SplashFragment;

public class SplashActivity extends BaseFastActivity {

    @Override
    public int onLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    public void onFindView(ViewFinder finder) {
    }

    @Override
    protected FragmentFactory.FragmentInfoWrapper onGetFragmentInfo() {
        return new FragmentFactory.FragmentInfoWrapper(SplashFragment.class, null);
    }
}