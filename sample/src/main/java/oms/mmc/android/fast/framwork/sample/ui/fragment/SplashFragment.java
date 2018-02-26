package oms.mmc.android.fast.framwork.sample.ui.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import oms.mmc.android.fast.framwork.base.BaseFastFragment;
import oms.mmc.android.fast.framwork.sample.R;
import oms.mmc.android.fast.framwork.sample.util.MMCUIHelper;
import oms.mmc.android.fast.framwork.util.ViewFinder;

/**
 * Package: oms.mmc.android.fast.framwork.sample.ui.fragment
 * FileName: SplashFragment
 * Date: on 2018/2/9  下午6:53
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */

public class SplashFragment extends BaseFastFragment {
    private FrameLayout animationLayout;

    @Override
    public View onLayoutView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_splash, container, false);
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