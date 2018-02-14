package oms.mmc.android.fast.framwork.tpl;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import oms.mmc.android.fast.framwork.R;
import oms.mmc.android.fast.framwork.base.BaseTpl;
import oms.mmc.android.fast.framwork.basiclib.util.ViewFinder;
import oms.mmc.android.fast.framwork.bean.BaseItemData;
import oms.mmc.android.fast.framwork.broadcast.LoadMoreBroadcast;
import oms.mmc.android.fast.framwork.util.BroadcastHelper;
import oms.mmc.android.fast.framwork.widget.pulltorefresh.helper.ILoadViewFactory;

/**
 * Package: oms.mmc.android.fast.framwork.sample.tpl.base
 * FileName: LoadMoreFooterTpl
 * Date: on 2018/1/29  下午11:12
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */

public class LoadMoreFooterTpl extends BaseTpl<BaseItemData> implements ILoadViewFactory.ILoadMoreView {
    private View footView;
    private TextView tipText;
    private ProgressBar progressBar;

    private BroadcastReceiver receiver;

    @Override
    public void onLayoutBefore() {
        super.onLayoutBefore();
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent != null) {
                    int helperHash = intent.getIntExtra(LoadMoreBroadcast.BUNDLE_KEY_HELPER_HASH, -1);
                    if (helperHash == getRecyclerViewHelper().hashCode()) {
                        int state = intent.getIntExtra(LoadMoreBroadcast.BUNDLE_KEY_STATE, LoadMoreBroadcast.NOMAL);
                        switch (state) {
                            case LoadMoreBroadcast.NOMAL:
                                showNormal();
                                break;
                            case LoadMoreBroadcast.LOADING:
                                showLoading();
                                break;
                            case LoadMoreBroadcast.FAIL:
                                showFail();
                                break;
                            case LoadMoreBroadcast.NO_MORE:
                                showNoMore();
                                break;
                        }
                    }
                }
            }
        };
        BroadcastHelper.register(getActivity(), LoadMoreBroadcast.class.getName(), receiver);
    }

    @Override
    public void onRecyclerViewDetachedFromWindow(View view) {
        super.onRecyclerViewDetachedFromWindow(view);
        BroadcastHelper.unRegister(getActivity(), receiver);
    }

    @Override
    public int onLayoutId() {
        return R.layout.item_load_more_footer;
    }

    @Override
    public void onFindView(ViewFinder finder) {
        footView = getRoot();
        tipText = (TextView) footView.findViewById(R.id.base_list_error_tip);
        progressBar = (ProgressBar) footView.findViewById(R.id.progressBar);
    }

    @Override
    protected void onRender(BaseItemData itemData) {
        showNormal();
    }

    private void loadMore() {
        getRecyclerViewHelper().loadMore();
    }

    @Override
    public void showNormal() {
        footView.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
        tipText.setVisibility(View.VISIBLE);
        tipText.setText("");
        footView.setOnClickListener(null);
    }

    @Override
    public void showLoading() {
        footView.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        tipText.setVisibility(View.VISIBLE);
        tipText.setText(R.string.base_list_load_more_loading_tip_text);
        footView.setOnClickListener(null);
    }

    @Override
    public void showFail() {
        footView.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
        tipText.setVisibility(View.VISIBLE);
        tipText.setText(R.string.base_list_load_more_load_error);
        footView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadMore();
            }
        });
    }

    @Override
    public void showNoMore() {
        footView.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
        tipText.setVisibility(View.VISIBLE);
        tipText.setText("");
        footView.setOnClickListener(null);
    }
}
