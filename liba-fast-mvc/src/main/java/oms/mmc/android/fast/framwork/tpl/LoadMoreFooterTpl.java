package oms.mmc.android.fast.framwork.tpl;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import oms.mmc.android.fast.framwork.R;
import oms.mmc.android.fast.framwork.adapter.SimpleRecyclerViewScrollAdapter;
import oms.mmc.android.fast.framwork.base.BaseTpl;
import oms.mmc.android.fast.framwork.bean.BaseItemData;
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
    protected View footView;
    protected TextView text;
    protected ProgressBar progressBar;
    protected View.OnClickListener onClickRefreshListener;

    //是否是第一次添加尾部，如果是第一次，就忽略了第一次的render方法，其实就是onBindView的回调，后续的回调就是拉到底部时回调的
    private boolean isFirstAddFooter = true;

    @Override
    public void onLayoutBefore() {
        super.onLayoutBefore();
        onClickRefreshListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadMore();
            }
        };
        /**
         * 滚动到底部自动加载更多数据
         */
        recyclerView.addOnScrollListener(new SimpleRecyclerViewScrollAdapter() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (recyclerViewHelper.getDataSource().hasMore()) {
                    if (!recyclerViewHelper.isRefreshing()) {//如果不是刷新状态
                        //如果滚动到最后一行，RecyclerView.canScrollVertically(1)的值表示是否能向上滚动，false表示已经滚动到底部
                        if (newState == RecyclerView.SCROLL_STATE_IDLE &&
                                !recyclerView.canScrollVertically(1)) {
                            //必须网络可用才能进行加载更多，没有网络直接显示失败了
                            if (recyclerViewHelper.hasNetwork(getActivity())) {
                                showLoading();
                                loadMore();
                            } else {
                                if (!recyclerViewHelper.isLoading()) {
                                    showFail();
                                }
                            }
                        }
                    }
                }
            }
        });
    }

    @Override
    public int onLayoutId() {
        return R.layout.base_list_footer;
    }

    @Override
    public void onLayoutAfter() {
        super.onLayoutAfter();
        footView = getRoot();
        text = (TextView) footView.findViewById(R.id.text);
        progressBar = (ProgressBar) footView.findViewById(R.id.progressBar);
    }

    @Override
    public void render() {
        //一开始都先显示空布局
        showNormal();
        //如果是因为条目比较少，一开始尾部就显示了
        if (isFirstAddFooter) {
            isFirstAddFooter = false;
            return;
        }
        //有更多数据，加载更多，并显示空布局
        if (recyclerViewHelper.isHasMoreData()) {
            this.showNormal();
            loadMore();
        } else {
            //没有更多，则显示没有更多数据
            this.showNoMore();
        }
    }

    private void loadMore() {
        recyclerViewHelper.loadMore(this);
    }

    @Override
    public void showNormal() {
        footView.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
        text.setVisibility(View.VISIBLE);
        text.setText("");
        footView.setOnClickListener(null);
    }

    @Override
    public void showLoading() {
        footView.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        text.setVisibility(View.VISIBLE);
        text.setText("正在加载中..");
        footView.setOnClickListener(null);
    }

    @Override
    public void showFail() {
        footView.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
        text.setVisibility(View.VISIBLE);
        text.setText("加载失败，点击重新加载");
        footView.setOnClickListener(onClickRefreshListener);
    }

    @Override
    public void showNoMore() {
        footView.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
        text.setVisibility(View.VISIBLE);
        text.setText("");
        footView.setOnClickListener(null);
    }
}