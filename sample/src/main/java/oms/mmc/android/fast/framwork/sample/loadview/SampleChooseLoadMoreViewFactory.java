package oms.mmc.android.fast.framwork.sample.loadview;

import android.view.LayoutInflater;
import android.view.View;

import oms.mmc.android.fast.framwork.loadview.ILoadMoreViewFactory;
import oms.mmc.android.fast.framwork.sample.R;
import oms.mmc.android.fast.framwork.util.DefaultLoadMoreHelper;

/**
 * Package: oms.mmc.android.fast.framwork.sample.loadview
 * FileName: SampleChooseLoadMoreViewFactory
 * Date: on 2018/3/23  下午1:58
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */

public class SampleChooseLoadMoreViewFactory implements ILoadMoreViewFactory {
    @Override
    public ILoadMoreView madeLoadMoreView() {
        return new DefaultLoadMoreHelper() {
            @Override
            protected View onSwitchNoMoreLayout(LayoutInflater inflater) {
                return inflater.inflate(R.layout.layout_sample_choose_load_more_footer_nomal, null);
            }

            @Override
            protected AfterAction onShowNoMore(View footerView) {
                return AfterAction.RESTORE_HEIGHT;
            }
        };
    }
}