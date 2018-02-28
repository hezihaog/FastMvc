package oms.mmc.android.fast.framwork.loadview;

import oms.mmc.android.fast.framwork.util.DefaultLoadMoreHelper;

/**
 * Package: oms.mmc.android.fast.framwork.util
 * FileName: BaseLoadMoreViewFactory
 * Date: on 2018/2/26  下午5:51
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */

public class BaseLoadMoreViewFactory implements ILoadMoreViewFactory {
    @Override
    public ILoadMoreView madeLoadMoreView() {
        return new DefaultLoadMoreHelper();
    }
}