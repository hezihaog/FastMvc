package oms.mmc.android.fast.framwork.sample.ui.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import oms.mmc.android.fast.framwork.base.BaseFastFragment;
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

public class SplashFragment extends BaseFastFragment {
    @Override
    public View onLayoutView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_splash, container, false);
    }

    @Override
    public void onLayoutAfter() {
        super.onLayoutAfter();
        jumpNext();
    }

    private void jumpNext() {
        MMCUIHelper.showSampleChoose(getActivity());
        getActivity().finish();
    }
}