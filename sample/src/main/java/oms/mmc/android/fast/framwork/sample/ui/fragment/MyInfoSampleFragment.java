package oms.mmc.android.fast.framwork.sample.ui.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import oms.mmc.android.fast.framwork.base.BaseFastFragment;
import oms.mmc.android.fast.framwork.sample.R;
import oms.mmc.android.fast.framwork.util.IViewFinder;

/**
 * Package: oms.mmc.android.fast.framwork.sample.ui.fragment
 * FileName: StoreSampleFragment
 * Date: on 2018/3/7  下午7:05
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */

public class MyInfoSampleFragment extends BaseFastFragment {
    @Override
    public View onLayoutView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_my_info_sample, container, false);
    }

    @Override
    public void onFindView(IViewFinder finder) {

    }
}