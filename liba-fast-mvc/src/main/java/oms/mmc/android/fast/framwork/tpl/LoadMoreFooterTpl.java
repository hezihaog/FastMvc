package oms.mmc.android.fast.framwork.tpl;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import oms.mmc.android.fast.framwork.R;
import oms.mmc.android.fast.framwork.widget.rv.base.BaseTpl;
import oms.mmc.android.fast.framwork.util.ViewFinder;
import oms.mmc.android.fast.framwork.widget.rv.base.BaseItemData;
import oms.mmc.android.fast.framwork.broadcast.LoadMoreBroadcast;
import oms.mmc.android.fast.framwork.util.BroadcastHelper;
import oms.mmc.android.fast.framwork.util.ILoadViewFactory;

/**
 * Package: oms.mmc.android.fast.framwork.sample.tpl.base
 * FileName: LoadMoreFooterTpl
 * Date: on 2018/1/29  下午11:12
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */

public class LoadMoreFooterTpl extends BaseTpl<BaseItemData> implements ILoadViewFactory.ILoadMoreView {
    private View mFootView;
    private TextView mTipText;
    private ProgressBar mProgressBar;

    private BroadcastReceiver mReceiver;

    @Override
    public void onLayoutBefore() {
        super.onLayoutBefore();
        mReceiver = new BroadcastReceiver() {
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
        BroadcastHelper.register(getActivity(), LoadMoreBroadcast.class.getName(), mReceiver);
    }

    @Override
    public void onRecyclerViewDetachedFromWindow(View view) {
        super.onRecyclerViewDetachedFromWindow(view);
        BroadcastHelper.unRegister(getActivity(), mReceiver);
    }

    @Override
    public int onLayoutId() {
        return R.layout.item_load_more_footer;
    }

    @Override
    public void onFindView(ViewFinder finder) {
        super.onFindView(finder);
        mFootView = getRoot();
        mTipText = (TextView) mFootView.findViewById(R.id.base_list_error_tip);
        mProgressBar = (ProgressBar) mFootView.findViewById(R.id.progressBar);
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
        mFootView.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.GONE);
        mTipText.setVisibility(View.VISIBLE);
        mTipText.setText("");
        mFootView.setOnClickListener(null);
    }

    @Override
    public void showLoading() {
        mFootView.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.VISIBLE);
        mTipText.setVisibility(View.VISIBLE);
        mTipText.setText(R.string.base_list_load_more_loading_tip_text);
        mFootView.setOnClickListener(null);
    }

    @Override
    public void showFail() {
        mFootView.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.GONE);
        mTipText.setVisibility(View.VISIBLE);
        mTipText.setText(R.string.base_list_load_more_load_error);
        mFootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadMore();
            }
        });
    }

    @Override
    public void showNoMore() {
        mFootView.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.GONE);
        mTipText.setVisibility(View.VISIBLE);
        mTipText.setText("");
        mFootView.setOnClickListener(null);
    }
}
