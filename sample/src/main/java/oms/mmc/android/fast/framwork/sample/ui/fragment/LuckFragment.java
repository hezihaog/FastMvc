package oms.mmc.android.fast.framwork.sample.ui.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import oms.mmc.android.fast.framwork.base.BaseFastFragment;
import oms.mmc.android.fast.framwork.sample.R;

/**
 * Package: oms.mmc.android.fast.framwork.sample.ui.fragment
 * FileName: LuckFragment
 * Date: on 2018/4/18  下午11:57
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */
public class LuckFragment extends BaseFastFragment {
    @Override
    public View onLayoutView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_luck, container, false);
    }
}
