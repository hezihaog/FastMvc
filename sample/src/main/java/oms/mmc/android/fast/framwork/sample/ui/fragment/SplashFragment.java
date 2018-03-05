package oms.mmc.android.fast.framwork.sample.ui.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

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
    private TextView mCountDownTv;
    private int count = 4;
    private Timer mTimer;

    @Override
    public View onLayoutView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_splash, container, false);
    }

    @Override
    public void onFindView(ViewFinder finder) {
        mCountDownTv = finder.get(R.id.countDown);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mTimer.cancel();
    }

    @Override
    public void onLayoutAfter() {
        super.onLayoutAfter();
        mTimer = new Timer();
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                post(new Runnable() {
                    @Override
                    public void run() {
                        count--;
                        mCountDownTv.setText(String.valueOf(count));
                        if (count == 1) {
                            jumpNext();
                            mTimer.cancel();
                        }
                    }
                });
            }
        }, 300, 1000);
    }

    private void jumpNext() {
        MMCUIHelper.showSampleChoose(getActivity());
        getActivity().finish();
    }
}