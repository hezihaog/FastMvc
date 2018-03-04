package oms.mmc.android.fast.framwork.sample.loadview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.pnikosis.materialishprogress.ProgressWheel;

import oms.mmc.android.fast.framwork.loadview.ILoadMoreViewFactory;
import oms.mmc.android.fast.framwork.sample.R;
import oms.mmc.android.fast.framwork.util.AbsLoadMoreHelper;

/**
 * Package: oms.mmc.android.fast.framwork.sample.loadview
 * FileName: SampleLoadMoreViewFactory
 * Date: on 2018/2/28  下午6:26
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */

public class SampleLoadMoreViewFactory implements ILoadMoreViewFactory {
    private TextView mTipText;
    private ProgressWheel mProgressWheel;

    @Override
    public ILoadMoreView madeLoadMoreView() {
        return new AbsLoadMoreHelper() {
            @Override
            protected View onInflateFooterView(LayoutInflater inflater, RecyclerView list, View.OnClickListener onClickLoadMoreListener) {
                return inflater.inflate(R.layout.layout_sample_load_more_footer, list, false);
            }

            @Override
            protected void onInflateFooterViewAfter(View footerView) {
                mProgressWheel = (ProgressWheel) footerView.findViewById(R.id.progressBar);
                mTipText = (TextView) footerView.findViewById(R.id.base_list_error_tip);
            }

            @Override
            protected AfterAction onShowNormal(View footerView) {
                footerView.setVisibility(View.VISIBLE);
                mProgressWheel.setVisibility(View.GONE);
                mTipText.setVisibility(View.VISIBLE);
                mTipText.setText("");
                footerView.setOnClickListener(null);
                return AfterAction.RESTORE_HEIGHT;
            }

            @Override
            protected AfterAction onShowNoMore(View footerView) {
                //当时统一的尾部视图时，直接对返回的footerView做控件操作，如果是单独一个布局，则不需要做操作了，重写onSwitchNoMoreLayout()方法返回特定的布局即可

//                footerView.setVisibility(View.VISIBLE);
//                mProgressWheel.setVisibility(View.GONE);
//                mTipText.setVisibility(View.VISIBLE);
//                mTipText.setText("没有更多了呢");
//                footerView.setOnClickListener(null);
                //这里返回不压缩高度，让尾部item显示，并且显示一条"没有更多了呢"的提示
                return AfterAction.RESTORE_HEIGHT;
            }

            @Override
            protected View onSwitchNoMoreLayout(LayoutInflater inflater) {
                return inflater.inflate(R.layout.layout_sample_load_more_footer_no_more, null);
            }

            @Override
            protected AfterAction onShowLoading(View footerView) {
                footerView.setVisibility(View.VISIBLE);
                mProgressWheel.setVisibility(View.VISIBLE);
                mTipText.setVisibility(View.VISIBLE);
                mTipText.setText("正在努力赶来喔...");
                footerView.setOnClickListener(null);
                return AfterAction.RESTORE_HEIGHT;
            }

            @Override
            protected AfterAction onShowError(View footerView) {
                footerView.setVisibility(View.VISIBLE);
                mProgressWheel.setVisibility(View.GONE);
                mTipText.setVisibility(View.VISIBLE);
                mTipText.setText("发生错误啦，刷新一下吧...");
                footerView.setOnClickListener(getOnClickRefreshListener());
                return AfterAction.RESTORE_HEIGHT;
            }
        };
    }
}
