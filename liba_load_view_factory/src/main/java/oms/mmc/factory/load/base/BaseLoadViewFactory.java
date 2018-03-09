package oms.mmc.factory.load.base;

import oms.mmc.factory.load.factory.ILoadViewFactory;

/**
 * Package: oms.mmc.factory.load
 * FileName: BaseLoadViewFactory
 * Date: on 2018/2/23  下午8:55
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */

public class BaseLoadViewFactory implements ILoadViewFactory {

    @Override
    public ILoadView madeLoadView() {
        return new BaseLoadViewHelper();
    }
}