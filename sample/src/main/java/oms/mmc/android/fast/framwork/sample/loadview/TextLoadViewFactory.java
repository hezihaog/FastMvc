package oms.mmc.android.fast.framwork.sample.loadview;

import android.view.View;

import oms.mmc.android.fast.framwork.sample.R;
import oms.mmc.android.fast.framwork.widget.pulltorefresh.helper.BaseLoadMoreHelper;
import oms.mmc.android.fast.framwork.widget.pulltorefresh.helper.BaseLoadViewFactory;
import oms.mmc.android.fast.framwork.widget.pulltorefresh.helper.BaseLoadViewHelper;

/**
 * Package: oms.mmc.android.fast.framwork.loadview
 * FileName: TextLoadViewFactory
 * Date: on 2018/1/24  下午2:50
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */

public class TextLoadViewFactory extends BaseLoadViewFactory {
    @Override
    public ILoadMoreView madeLoadMoreView() {
        return new BaseLoadMoreHelper();
    }

    @Override
    public ILoadView madeLoadView() {
        return new BaseLoadViewHelper() {
            @Override
            public void showEmpty() {
                View layout = helper.inflate(R.layout.load_empty_text);
                layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
                helper.showLayout(layout);
            }
        };
    }
}
