package oms.mmc.android.fast.framwork.sample.loadview;

import android.view.View;

import oms.mmc.android.fast.framwork.sample.R;
import oms.mmc.factory.load.base.BaseLoadViewFactory;
import oms.mmc.factory.load.base.BaseLoadViewHelper;

/**
 * Package: oms.mmc.android.fast.framwork.sample.loadview
 * FileName: SampleLoadViewFactory
 * Date: on 2018/2/28  下午3:55
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */

public class SampleLoadViewFactory extends BaseLoadViewFactory {
    @Override
    public ILoadView madeLoadView() {
        return new BaseLoadViewHelper() {
            @Override
            protected View onInflateLoadingLayout() {
                return getHelper().inflate(R.layout.layout_loading_view_sample_loading);
            }

            @Override
            protected View onInflateErrorLayout() {
                return super.onInflateErrorLayout();
            }

            @Override
            protected View onInflateEmptyLayout() {
                return super.onInflateEmptyLayout();
            }
        };
    }
}