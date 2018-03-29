package oms.mmc.android.fast.framwork.sample.loadview;

import android.view.View;
import android.widget.TextView;

import oms.mmc.android.fast.framwork.sample.R;
import oms.mmc.factory.load.base.BaseLoadViewFactory;
import oms.mmc.factory.load.base.BaseLoadViewHelper;
import oms.mmc.factory.load.base.IVaryViewHelper;

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
            protected View onInflateLoadingLayout(IVaryViewHelper helper, View.OnClickListener onClickRefreshListener) {
                return helper.inflate(R.layout.layout_loading_view_sample_loading);
            }

            @Override
            protected View onInflateErrorLayout(IVaryViewHelper helper, View.OnClickListener onClickRefreshListener) {
                View layout = helper.inflate(R.layout.layout_sample_load_view_error);
                TextView refreshTv = (TextView) layout.findViewById(R.id.base_list_error_refresh);
                refreshTv.setOnClickListener(onClickRefreshListener);
                return layout;
            }

            @Override
            protected View onInflateEmptyLayout(IVaryViewHelper helper, View.OnClickListener onClickRefreshListener) {
                View layout = helper.inflate(R.layout.layout_sample_load_view_empty);
                TextView refreshTv = (TextView) layout.findViewById(R.id.base_list_empty_refresh);
                refreshTv.setOnClickListener(onClickRefreshListener);
                return layout;
            }
        };
    }
}