package oms.mmc.factory.load.base;

import android.view.View;
import android.view.View.OnClickListener;

import oms.mmc.factory.load.R;


/**
 * 自己控制切换布局的Helper类
 */
public class SimpleLoadViewHelper extends AbsSimpleLoadViewHelper {

    @Override
    protected View onInflateLoadingLayout(IVaryViewHelper helper, OnClickListener onClickRefreshListener) {
        return helper.inflate(R.layout.layout_load_view_loading);
    }

    @Override
    protected View onInflateEmptyLayout(IVaryViewHelper helper, OnClickListener onClickRefreshListener) {
        View layout = helper.inflate(R.layout.layout_load_view_empty);
        layout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return layout;
    }

    @Override
    protected View onInflateErrorLayout(IVaryViewHelper helper, OnClickListener onClickRefreshListener) {
        View layout = helper.inflate(R.layout.layout_load_view_error);
        layout.findViewById(R.id.base_list_error_refresh).setOnClickListener(onClickRefreshListener);
        return layout;
    }
}