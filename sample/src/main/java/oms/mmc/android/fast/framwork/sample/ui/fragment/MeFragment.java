package oms.mmc.android.fast.framwork.sample.ui.fragment;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import oms.mmc.android.fast.framwork.base.BaseFragment;
import oms.mmc.android.fast.framwork.basiclib.util.ViewFinder;
import oms.mmc.android.fast.framwork.sample.R;
import oms.mmc.android.fast.framwork.sample.util.FakeUtil;

/**
 * Package: oms.mmc.android.fast.framwork.sample.ui.fragment
 * FileName: MeFragment
 * Date: on 2018/1/25  上午11:06
 * Auther: zihe
 * Descirbe:个人主页fragment
 * Email: hezihao@linghit.com
 */

public class MeFragment extends BaseFragment {
    ImageView avatar;

    @Override
    public int onLayoutId() {
        return R.layout.fragment_me;
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
