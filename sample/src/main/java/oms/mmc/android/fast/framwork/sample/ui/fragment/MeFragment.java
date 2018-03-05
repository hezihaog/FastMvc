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
import oms.mmc.android.fast.framwork.sample.wait.IOSWaitDialogFactory;
import oms.mmc.android.fast.framwork.util.ViewFinder;
import oms.mmc.factory.wait.factory.IWaitViewFactory;

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
        avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showWaitDialog("头像被点击啦", true);
            }
        });
    }

    /**
     * 修改懒加载进场布局，用于替换懒加载在显示之前的空白瞬间
     */
    @Override
    protected View onGetLazyLoadingView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.layout_loading_view_sample_loading, null);
    }

    //fragment重写该方法，如果activity已经复写了，则无效。一般项目都会加一个项目基类继承BaseFastActivity，也不会一套UI多种风格
    @Override
    protected IWaitViewFactory onWaitDialogFactoryReady() {
        return new IOSWaitDialogFactory();
    }
}
