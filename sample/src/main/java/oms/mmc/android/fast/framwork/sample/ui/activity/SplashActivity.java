package oms.mmc.android.fast.framwork.sample.ui.activity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import oms.mmc.android.fast.framwork.base.BaseFastActivity;
import oms.mmc.android.fast.framwork.sample.R;
import oms.mmc.android.fast.framwork.sample.ui.fragment.SplashFragment;
import oms.mmc.android.fast.framwork.util.FragmentFactory;
import oms.mmc.android.fast.framwork.util.IViewFinder;

public class SplashActivity extends BaseFastActivity {

    @Override
    public View onLayoutView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.activity_splash, container, false);
    }

    @Override
    public void onFindView(IViewFinder finder) {
    }

    @Override
    protected FragmentFactory.FragmentInfoWrapper onSetupFragment() {
        return new FragmentFactory.FragmentInfoWrapper(SplashFragment.class);
    }
}