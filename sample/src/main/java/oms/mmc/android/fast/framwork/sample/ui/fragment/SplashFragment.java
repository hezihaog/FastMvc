package oms.mmc.android.fast.framwork.sample.ui.fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.FrameLayout;

import oms.mmc.android.fast.framwork.base.BaseFragment;
import oms.mmc.android.fast.framwork.basiclib.util.ViewFinder;
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
        animationLayout = finder.get(R.id.animationLayout);
    }

    @Override
    public void onLayoutAfter() {
        super.onLayoutAfter();
        ValueAnimator animator = ValueAnimator.ofFloat(0f, 12f);
        animator.setStartDelay(200);
        animator.setDuration(1000);
        animator.setRepeatMode(ValueAnimator.REVERSE);
        animator.setRepeatCount(1);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Float cValue = (Float) animation.getAnimatedValue();
                animationLayout.setScaleX(cValue);
                animationLayout.setScaleY(cValue);
            }
        });
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                MMCUIHelper.showMain(getActivity());
                getActivity().finish();
            }
        });
        animator.start();
    }
}