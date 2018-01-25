package oms.mmc.android.fast.framwork.sample.ui.activity;

import oms.mmc.android.fast.framwork.base.BaseActivity;
import oms.mmc.android.fast.framwork.sample.R;
import oms.mmc.android.fast.framwork.sample.util.MMCUIHelper;

public class SplashActivity extends BaseActivity {

    @Override
    public int onLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    public void onLayoutAfter() {
        super.onLayoutAfter();
        MMCUIHelper.showMain(this);
        finish();
    }
}