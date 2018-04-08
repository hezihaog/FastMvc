package oms.mmc.android.fast.framwork.sample.widget.block;

import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import oms.mmc.android.fast.framwork.util.TDevice;
import oms.mmc.android.fast.framwork.widget.block.BaseRecyclerViewBlock;
import oms.mmc.factory.wait.inter.IWaitViewHost;

/**
 * Package: oms.mmc.android.fast.framwork.sample.widget.block
 * FileName: ConversationVIewBlock
 * Date: on 2018/4/6  下午10:14
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */
public class ConversationViewBlock extends BaseRecyclerViewBlock {
    public ConversationViewBlock(FragmentActivity activity, IWaitViewHost waitViewHost) {
        super(activity, waitViewHost);
    }

    public ConversationViewBlock(FragmentActivity activity, IWaitViewHost waitViewHost, ViewGroup parent) {
        super(activity, waitViewHost, parent);
    }

    @Override
    public View onLayoutView(LayoutInflater inflater, ViewGroup container) {
        TextView headerView = new TextView(this.getActivity());
        headerView.setText("我是添加的头部布局视图");
        headerView.setGravity(Gravity.CENTER);
        headerView.setBackgroundColor(Color.parseColor("#66FF0000"));
        return headerView;
    }

    @Override
    public ViewGroup.LayoutParams onGetLayoutParams() {
        return new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT,
                (int) TDevice.dpToPixel(getActivity(), 70f));
    }

    @Override
    public void onLayoutAfter() {
        super.onLayoutAfter();
        getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toast("headerView onClick()");
            }
        });
    }
}
