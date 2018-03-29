package oms.mmc.android.fast.framwork.sample.loadview;

import android.view.View;

import oms.mmc.android.fast.framwork.sample.R;
import oms.mmc.factory.load.base.BaseLoadViewFactory;
import oms.mmc.factory.load.base.BaseLoadViewHelper;
import oms.mmc.factory.load.base.IVaryViewHelper;

/**
 * Package: oms.mmc.android.fast.framwork.loadview
 * FileName: TextLoadViewFactory
 * Date: on 2018/1/24  下午2:50
 * Auther: zihe
 * Descirbe:我的联系人界面切换加载器
 * Email: hezihao@linghit.com
 */

public class MyContactLoadViewFactory extends BaseLoadViewFactory {

    @Override
    public ILoadView madeLoadView() {
        return new BaseLoadViewHelper() {
            @Override
            protected View onInflateLoadingLayout(IVaryViewHelper helper, View.OnClickListener onClickRefreshListener) {
                //替换加载中状态布局，直接只有一个进度条
                return this.helper.inflate(R.layout.layout_my_contact_load_loading);
            }

            @Override
            public void showEmpty() {
                //替换数据为空布局
                View layout = helper.inflate(R.layout.layout_my_contact_load_empty);
                layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //去掉点击布局时的事件
                    }
                });
                helper.showLayout(layout);
            }
        };
    }
}
