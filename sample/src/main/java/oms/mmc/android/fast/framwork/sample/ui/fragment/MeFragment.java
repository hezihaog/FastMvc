package oms.mmc.android.fast.framwork.sample.ui.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import oms.mmc.android.fast.framwork.base.BaseFastFragment;
import oms.mmc.android.fast.framwork.sample.R;
import oms.mmc.android.fast.framwork.sample.util.FakeUtil;
import oms.mmc.android.fast.framwork.util.ViewFinder;

/**
 * Package: oms.mmc.android.fast.framwork.sample.ui.fragment
 * FileName: MeFragment
 * Date: on 2018/1/25  上午11:06
 * Auther: zihe
 * Descirbe:个人主页fragment
 * Email: hezihao@linghit.com
 */

public class MeFragment extends BaseFastFragment {
    ImageView avatar;

    @Override
    public View onLayoutView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_me, container, false);
    }

    @Override
    public void onFindView(ViewFinder finder) {
        avatar = finder.get(R.id.avatar);
    }

    @Override
    public void onLayoutAfter() {
        super.onLayoutAfter();
        Glide.with(getActivity()).load(FakeUtil.getRandomAvatar()).
                diskCacheStrategy(DiskCacheStrategy.ALL).into(avatar);
    }
}
