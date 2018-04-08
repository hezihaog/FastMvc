package oms.mmc.android.fast.framwork.widget.block;

import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import oms.mmc.factory.wait.inter.IWaitViewHost;

/**
 * Package: oms.mmc.android.fast.framwork.widget.block
 * FileName: BaseRecyclerViewBlock
 * Date: on 2018/4/6  下午9:48
 * Auther: zihe
 * Descirbe:给RecyclerView添加布局拆分时使用，
 * Email: hezihao@linghit.com
 */
public abstract class BaseRecyclerViewBlock extends BaseViewBlock {
    public BaseRecyclerViewBlock(FragmentActivity activity, IWaitViewHost waitViewHost) {
        super(activity, waitViewHost);
    }

    public BaseRecyclerViewBlock(FragmentActivity activity, IWaitViewHost waitViewHost, ViewGroup parent) {
        super(activity, waitViewHost, parent);
    }

    @Override
    public ViewGroup.LayoutParams onGetLayoutParams() {
        return new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT,
                RecyclerView.LayoutParams.WRAP_CONTENT);
    }
}