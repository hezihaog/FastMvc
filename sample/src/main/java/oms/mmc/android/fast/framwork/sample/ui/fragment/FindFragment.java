package oms.mmc.android.fast.framwork.sample.ui.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import oms.mmc.android.fast.framwork.base.BaseFastFragment;
import oms.mmc.android.fast.framwork.sample.R;
import oms.mmc.android.fast.framwork.util.IViewFinder;

/**
 * Package: oms.mmc.android.fast.framwork.sample.ui.fragment
 * FileName: FriendCircleFragment
 * Date: on 2018/1/25  上午11:05
 * Auther: zihe
 * Descirbe:发现fragment
 * Email: hezihao@linghit.com
 */

public class FindFragment extends BaseFastFragment {
    @Override
    public View onLayoutView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_find, container, false);
    }

    @Override
    public void onFindView(IViewFinder finder) {
    }
}