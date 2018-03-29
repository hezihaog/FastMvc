package oms.mmc.factory.load.base;

import android.view.View;

import oms.mmc.factory.load.R;

/**
 * Package: oms.mmc.factory.load
 * FileName: BaseLoadViewHelper
 * Date: on 2018/2/23  下午8:56
 * Auther: zihe
 * Descirbe:基础的界面切换加载器，子类继承复写方法即可
 * Email: hezihao@linghit.com
 */

public class BaseLoadViewHelper extends AbsLoadViewHelper {

    @Override
    protected View onInflateLoadingLayout(IVaryViewHelper helper, View.OnClickListener onClickRefreshListener) {
        return this.helper.inflate(R.layout.layout_load_view_loading);
    }

    @Override
    protected View onInflateErrorLayout(IVaryViewHelper helper, View.OnClickListener onClickRefreshListener) {
        View layout = helper.inflate(R.layout.layout_load_view_error);
        layout.findViewById(R.id.base_list_error_refresh).setOnClickListener(onClickRefreshListener);
        return layout;
    }

    @Override
    protected View onInflateEmptyLayout(IVaryViewHelper helper, View.OnClickListener onClickRefreshListener) {
        View layout = helper.inflate(R.layout.layout_load_view_empty);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return layout;
    }
}