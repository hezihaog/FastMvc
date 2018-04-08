package oms.mmc.android.fast.framwork.widget.block;

import android.support.v4.app.FragmentActivity;
import android.view.ViewGroup;
import android.widget.AbsListView;

import oms.mmc.factory.wait.inter.IWaitViewHost;

/**
 * Package: oms.mmc.android.fast.framwork.widget.block
 * FileName: BaseListViewBlock
 * Date: on 2018/4/6  下午9:46
 * Auther: zihe
 * Descirbe:给ListView添加布局拆分时使用，通常是给ListView添加头部
 * Email: hezihao@linghit.com
 */
public abstract class BaseListViewBlock extends BaseViewBlock {
    public BaseListViewBlock(FragmentActivity activity, IWaitViewHost waitViewHost) {
        super(activity, waitViewHost);
    }

    public BaseListViewBlock(FragmentActivity activity, IWaitViewHost waitViewHost, ViewGroup parent) {
        super(activity, waitViewHost, parent);
    }

    @Override
    public ViewGroup.LayoutParams onGetLayoutParams() {
        return new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT,
                AbsListView.LayoutParams.WRAP_CONTENT);
    }
}
