package oms.mmc.android.fast.framwork.sample.ui.fragment;

import android.widget.FrameLayout;

import oms.mmc.android.fast.framwork.base.BaseFragment;
import oms.mmc.android.fast.framwork.util.ViewFinder;
import oms.mmc.android.fast.framwork.sample.R;
import oms.mmc.android.fast.framwork.sample.util.MMCUIHelper;

/**
 * Package: oms.mmc.android.fast.framwork.sample.ui.fragment
 * FileName: SplashFragment
 * Date: on 2018/2/9  下午6:53
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */

public class SplashFragment extends BaseFragment {
    private FrameLayout animationLayout;

    @Override
    public int onLayoutId() {
        return R.layout.fragment_splash;
    }

    @Override
    public void onFindView(ViewFinder finder) {
    }

    @Override
    public void onLayoutAfter() {
        super.onLayoutAfter();
        goMain();
    }

    private void goMain() {
        MMCUIHelper.showMain(getActivity());
//        startActivity(new Intent(getActivity(), TestListActivity.class));
        getActivity().finish();
    }
}